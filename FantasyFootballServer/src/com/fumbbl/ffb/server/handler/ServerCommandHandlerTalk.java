package com.fumbbl.ffb.server.handler;

import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.net.commands.ClientCommandTalk;
import com.fumbbl.ffb.net.commands.ServerCommandTalk;
import com.fumbbl.ffb.server.FantasyFootballServer;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.handler.talk.TalkHandler;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.net.ServerCommunication;
import com.fumbbl.ffb.server.net.SessionManager;
import com.fumbbl.ffb.util.Scanner;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kalimar
 */
public class ServerCommandHandlerTalk extends ServerCommandHandler {

	private final Set<TalkHandler> handlers = new HashSet<>();

	protected ServerCommandHandlerTalk(FantasyFootballServer server) {
		super(server);
		handlers.addAll(new Scanner<>(TalkHandler.class).getSubclassInstances());
	}

	public NetCommandId getId() {
		return NetCommandId.CLIENT_TALK;
	}

	public boolean handleCommand(ReceivedCommand receivedCommand) {

		ClientCommandTalk talkCommand = (ClientCommandTalk) receivedCommand.getCommand();
		String talk = talkCommand.getTalk();

		if (talk != null) {

			if (handlers.stream().anyMatch(handler -> handler.handle(getServer(), talkCommand, receivedCommand.getSession()))) {
				return true;
			}

			SessionManager sessionManager = getServer().getSessionManager();
			ServerCommunication communication = getServer().getCommunication();
			long gameId = sessionManager.getGameIdForSession(receivedCommand.getSession());
			GameState gameState = getServer().getGameCache().getGameStateById(gameId);
			String coach = sessionManager.getCoachForSession(receivedCommand.getSession());

			// Spectator chat
			if ((gameState != null) && (sessionManager.getSessionOfHomeCoach(gameId) == receivedCommand.getSession())
				|| (sessionManager.getSessionOfAwayCoach(gameId) == receivedCommand.getSession())) {
				communication.sendPlayerTalk(gameState, coach, talk);
			} else {
				ServerCommandTalk.Mode mode = ServerCommandTalk.Mode.REGULAR;
				if (sessionManager.isSessionAdmin(receivedCommand.getSession()) && ServerCommandTalk.Mode.STAFF.findIndicator(talk)) {
					mode = ServerCommandTalk.Mode.STAFF; // takes precedence
				} else if (sessionManager.isSessionDev(receivedCommand.getSession()) && ServerCommandTalk.Mode.DEV.findIndicator(talk)) {
					mode = ServerCommandTalk.Mode.DEV;
				}

				getServer().getCommunication().sendSpectatorTalk(gameState, coach, talk, mode);
			}

		}

		return true;

	}


}
