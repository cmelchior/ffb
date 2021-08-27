package com.fumbbl.ffb.server.handler;

import com.fumbbl.ffb.ClientMode;
import com.fumbbl.ffb.GameStatus;
import com.fumbbl.ffb.TeamList;
import com.fumbbl.ffb.TeamListEntry;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.net.ServerStatus;
import com.fumbbl.ffb.server.FantasyFootballServer;
import com.fumbbl.ffb.server.GameCache;
import com.fumbbl.ffb.server.GameStartMode;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.ServerMode;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.net.ServerCommunication;
import com.fumbbl.ffb.server.net.SessionManager;
import com.fumbbl.ffb.server.net.commands.InternalServerCommandJoinApproved;
import com.fumbbl.ffb.server.request.fumbbl.FumbblRequestCheckGamestate;
import com.fumbbl.ffb.server.request.fumbbl.FumbblRequestLoadTeam;
import com.fumbbl.ffb.server.request.fumbbl.FumbblRequestLoadTeamList;
import com.fumbbl.ffb.server.util.UtilServerStartGame;
import com.fumbbl.ffb.server.util.UtilServerTimer;
import com.fumbbl.ffb.server.util.UtilSkillBehaviours;
import com.fumbbl.ffb.util.StringTool;

import org.eclipse.jetty.websocket.api.Session;

/**
 * 
 * @author Kalimar
 */
public class ServerCommandHandlerJoinApproved extends ServerCommandHandler {

	private static final String _TEST_PREFIX = "test:";

	protected ServerCommandHandlerJoinApproved(FantasyFootballServer pServer) {
		super(pServer);
	}

	public NetCommandId getId() {
		return NetCommandId.INTERNAL_SERVER_JOIN_APPROVED;
	}

	public boolean handleCommand(ReceivedCommand receivedCommand) {

		InternalServerCommandJoinApproved joinApprovedCommand = (InternalServerCommandJoinApproved) receivedCommand
				.getCommand();
		ServerCommunication communication = getServer().getCommunication();
		SessionManager sessionManager = getServer().getSessionManager();
		GameCache gameCache = getServer().getGameCache();
		GameState gameState = null;
		Session session = receivedCommand.getSession();

		if (joinApprovedCommand.getGameId() > 0) {
			gameState = loadGameStateById(joinApprovedCommand, session);

		} else if (StringTool.isProvided(joinApprovedCommand.getGameName())) {
			gameState = gameCache.getGameStateByName(joinApprovedCommand.getGameName(), true);
			if ((gameState == null) && !getServer().isBlockingNewGames()) {
				boolean testing = (joinApprovedCommand.getGameName().startsWith(_TEST_PREFIX)
						|| getServer().getMode() == ServerMode.STANDALONE);
				gameState = gameCache.createGameState(testing ? GameStartMode.START_TEST_GAME : GameStartMode.START_GAME);
				gameCache.mapGameNameToId(joinApprovedCommand.getGameName(), gameState.getId());
			}
		}

		if (gameState != null) {

			Game game = gameState.getGame();

			if (joinApprovedCommand.getClientMode() == ClientMode.PLAYER) {

				if (joinApprovedCommand.getCoach().equalsIgnoreCase(game.getTeamHome().getCoach())
						|| joinApprovedCommand.getCoach().equalsIgnoreCase(game.getTeamAway().getCoach())) {
					if ((gameState.getStatus() == GameStatus.SCHEDULED) || (game.getStarted() != null)) {
						joinWithoutTeam(gameState, joinApprovedCommand, session);
					} else {
						if (StringTool.isProvided(joinApprovedCommand.getTeamId())) {
							joinWithTeam(gameState, joinApprovedCommand, session);
						} else {
							sendTeamList(gameState, joinApprovedCommand, session);
						}
					}

				} else if (game.getStarted() != null) {
					communication.sendStatus(session, ServerStatus.ERROR_GAME_IN_USE, null);

				} else if (!StringTool.isProvided(joinApprovedCommand.getTeamId())) {
					sendTeamList(gameState, joinApprovedCommand, session);

				} else {
					joinWithTeam(gameState, joinApprovedCommand, session);
				}

				// ClientMode.SPECTATOR
			} else {

				closeOtherSessionWithThisCoach(gameState, joinApprovedCommand.getCoach(), session);
				sessionManager.addSession(session, gameState.getId(), joinApprovedCommand.getCoach(),
						joinApprovedCommand.getClientMode(), false, joinApprovedCommand.getAccountProperties());
				UtilServerStartGame.sendServerJoin(gameState, session, joinApprovedCommand.getCoach(), false,
						ClientMode.SPECTATOR, joinApprovedCommand.getAccountProperties());
				if (gameState.getGame().getStarted() != null) {
					UtilServerTimer.syncTime(gameState, System.currentTimeMillis());
					communication.sendGameState(session, gameState);
				}

			}

		}

		return true;

	}

	private void joinWithoutTeam(GameState pGameState, InternalServerCommandJoinApproved pJoinApprovedCommand,
			Session pSession) {
		Game game = pGameState.getGame();
		if (pJoinApprovedCommand.getCoach().equalsIgnoreCase(game.getTeamHome().getCoach())
				|| pJoinApprovedCommand.getCoach().equalsIgnoreCase(game.getTeamAway().getCoach())) {
			if (!game.isTesting()) {
				closeOtherSessionWithThisCoach(pGameState, pJoinApprovedCommand.getCoach(), pSession);
			}
			boolean homeTeam = pJoinApprovedCommand.getCoach().equalsIgnoreCase(game.getTeamHome().getCoach());
			if (UtilServerStartGame.joinGameAsPlayerAndCheckIfReadyToStart(pGameState, pSession,
					pJoinApprovedCommand.getCoach(), homeTeam, pJoinApprovedCommand.getAccountProperties())) {
				if (getServer().getMode() == ServerMode.FUMBBL) {
					if (game.getStarted() != null) {
						// Game is already initialized, so we just need to kickstart it
						UtilSkillBehaviours.registerBehaviours(pGameState.getGame(), getServer().getDebugLog());
						UtilServerStartGame.startGame(pGameState);
					} else {
						// This is a new game and we need to get options from FUMBBL
						getServer().getRequestProcessor().add(new FumbblRequestCheckGamestate(pGameState));
					}
				} else {
					UtilServerStartGame.addDefaultGameOptions(pGameState);
					UtilSkillBehaviours.registerBehaviours(pGameState.getGame(), getServer().getDebugLog());
					UtilServerStartGame.startGame(pGameState);
				}
			}
		}
	}

	private void joinWithTeam(GameState pGameState, InternalServerCommandJoinApproved pJoinApprovedCommand,
			Session pSession) {
		if ((pGameState != null) && StringTool.isProvided(pJoinApprovedCommand.getTeamId())) {
			Game game = pGameState.getGame();
			if (!game.isTesting()) {
				closeOtherSessionWithThisCoach(pGameState, pJoinApprovedCommand.getCoach(), pSession);
			}
			boolean homeTeam = (!StringTool.isProvided(game.getTeamHome().getId())
					|| pJoinApprovedCommand.getTeamId().equals(game.getTeamHome().getId()));
			if (getServer().getMode() == ServerMode.FUMBBL) {
				getServer().getRequestProcessor().add(new FumbblRequestLoadTeam(pGameState, pJoinApprovedCommand.getCoach(),
						pJoinApprovedCommand.getTeamId(), homeTeam, pSession, pJoinApprovedCommand.getAccountProperties()));
			} else {
				Team teamSkeleton = getServer().getGameCache().getTeamSkeleton(pJoinApprovedCommand.getTeamId());
				getServer().getGameCache().addTeamToGame(pGameState, teamSkeleton, homeTeam);
				if (UtilServerStartGame.joinGameAsPlayerAndCheckIfReadyToStart(pGameState, pSession,
						pJoinApprovedCommand.getCoach(), homeTeam, pJoinApprovedCommand.getAccountProperties())) {
					UtilServerStartGame.addDefaultGameOptions(pGameState);
					pGameState.initRulesDependentMembers();
					pGameState.getGame().initializeRules();
					UtilSkillBehaviours.registerBehaviours(pGameState.getGame(), getServer().getDebugLog());
					Team teamHome = getServer().getGameCache().getTeamById(game.getTeamHome().getId(), game);
					getServer().getGameCache().addTeamToGame(pGameState, teamHome, true);
					Team teamAway = getServer().getGameCache().getTeamById(game.getTeamAway().getId(), game);
					getServer().getGameCache().addTeamToGame(pGameState, teamAway, false);
					UtilServerStartGame.startGame(pGameState);
				}
			}
		}
	}

	private void closeOtherSessionWithThisCoach(GameState gameState, String coach, Session session) {
		SessionManager sessionManager = getServer().getSessionManager();
		Session[] allSessions = sessionManager.getSessionsForGameId(gameState.getId());
		for (int i = 0; i < allSessions.length; i++) {
			if ((session != allSessions[i]) && coach.equalsIgnoreCase(sessionManager.getCoachForSession(allSessions[i]))) {
				getServer().getCommunication().close(allSessions[i]);
				break;
			}
		}
	}

	private GameState loadGameStateById(InternalServerCommandJoinApproved pJoinApprovedCommand, Session pSession) {
		GameCache gameCache = getServer().getGameCache();
		GameState gameState = gameCache.getGameStateById(pJoinApprovedCommand.getGameId());
		if (gameState != null) {
			return gameState;
		}
		gameState = gameCache.queryFromDb(pJoinApprovedCommand.getGameId());
		if (gameState == null) {
			return null;
		}
		gameCache.addGame(gameState);
		gameCache.queueDbUpdate(gameState, true); // persist status update
		return gameState;
	}

	private void sendTeamList(GameState pGameState, InternalServerCommandJoinApproved pJoinApprovedCommand,
			Session pSession) {
		if (getServer().getMode() == ServerMode.FUMBBL) {
			getServer().getRequestProcessor()
					.add(new FumbblRequestLoadTeamList(pGameState, pJoinApprovedCommand.getCoach(), pSession));
		} else {
			TeamList teamList = new TeamList();
			Team[] teams = getServer().getGameCache().getTeamsForCoach(pJoinApprovedCommand.getCoach(), pGameState.getGame());
			for (Team team : teams) {
				TeamListEntry teamEntry = new TeamListEntry();
				teamEntry.init(team);
				teamList.add(teamEntry);
			}
			getServer().getCommunication().sendTeamList(pSession, teamList);
		}
	}

}
