package com.fumbbl.ffb.server.handler.talk;

import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.SeriousInjury;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.PlayerResult;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.net.commands.ClientCommandTalk;
import com.fumbbl.ffb.server.FantasyFootballServer;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.net.ServerCommunication;
import com.fumbbl.ffb.server.net.SessionManager;
import com.fumbbl.ffb.util.ArrayTool;
import com.fumbbl.ffb.util.UtilBox;
import org.eclipse.jetty.websocket.api.Session;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class TalkHandler {

	private final String command;
	private final int minimumCommandParts;
	private final Set<TalkRequirements.Privilege> requiresOnePrivilegeOf = new HashSet<>();
	private final TalkRequirements.Client requiredClientMode;
	private final TalkRequirements.Environment requiredEnvironment;

	public TalkHandler(String command, int minimumCommandParts, TalkRequirements.Client requiredClientMode, TalkRequirements.Environment requiredEnvironment, TalkRequirements.Privilege... requiresOnePrivilegeOf) {
		this.command = command;
		this.minimumCommandParts = minimumCommandParts;
		this.requiredClientMode = requiredClientMode;
		this.requiredEnvironment = requiredEnvironment;
		if (requiresOnePrivilegeOf != null) {
			this.requiresOnePrivilegeOf.addAll(Arrays.asList(requiresOnePrivilegeOf));
		}
	}

	public boolean handle(FantasyFootballServer server, ClientCommandTalk talkCommand, Session session) {
		SessionManager sessionManager = server.getSessionManager();
		long gameId = sessionManager.getGameIdForSession(session);
		GameState gameState = server.getGameCache().getGameStateById(gameId);

		Game game = gameState.getGame();
		String talk = talkCommand.getTalk();
		String[] commands = talk.split(" +");
		if (commands.length <= minimumCommandParts) {
			return false;
		}

		if (!handles(server, commands[0], session)) {
			return false;
		}

		Team team = (sessionManager.getSessionOfHomeCoach(game.getId()) == session) ? game.getTeamHome()
			: game.getTeamAway();

		handle(server, gameState, commands, team);

		return true;
	}

	private boolean handles(FantasyFootballServer server, String command, Session session) {
		SessionManager sessionManager = server.getSessionManager();
		long gameId = sessionManager.getGameIdForSession(session);
		GameState gameState = server.getGameCache().getGameStateById(gameId);

		return gameState != null && this.command.equals(command)
			&& requiredClientMode.isMet(sessionManager, gameId, session)
			&& requiredEnvironment.isMet(server, gameState)
			&& (requiresOnePrivilegeOf.isEmpty() || requiresOnePrivilegeOf.stream().anyMatch(requirement -> requirement.isMet(sessionManager, session)));
	}

	public abstract void handle(FantasyFootballServer server, GameState gameState, String[] commands, Team team);

	protected Player<?>[] findPlayersInCommand(Team pTeam, String[] pCommands) {
		Set<Player<?>> players = new HashSet<>();
		if (ArrayTool.isProvided(pCommands) && (minimumCommandParts < pCommands.length)) {
			if ("all".equalsIgnoreCase(pCommands[minimumCommandParts])) {
				Collections.addAll(players, pTeam.getPlayers());
			} else {
				for (int i = minimumCommandParts; i < pCommands.length; i++) {
					try {
						Player<?> player = pTeam.getPlayerByNr(Integer.parseInt(pCommands[i]));
						if (player != null) {
							players.add(player);
						}
					} catch (NumberFormatException ignored) {
					}
				}
			}
		}
		return players.toArray(new Player[0]);
	}

	protected void putPlayerIntoBox(GameState pGameState, ServerCommunication communication, Player<?> pPlayer, PlayerState pPlayerState, String pBoxName,
																	SeriousInjury pSeriousInjury) {
		Game game = pGameState.getGame();
		PlayerResult playerResult = game.getGameResult().getPlayerResult(pPlayer);
		playerResult.setSeriousInjury(pSeriousInjury);
		playerResult.setSeriousInjuryDecay(null);
		game.getFieldModel().setPlayerState(pPlayer, pPlayerState);
		UtilBox.putPlayerIntoBox(game, pPlayer);
		communication.sendPlayerTalk(pGameState, null, "Player " + pPlayer.getName() + " moved into box " + pBoxName + ".");
	}

}
