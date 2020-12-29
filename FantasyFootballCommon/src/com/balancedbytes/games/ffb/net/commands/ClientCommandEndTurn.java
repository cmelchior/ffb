package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class ClientCommandEndTurn extends ClientCommand {

	public ClientCommandEndTurn() {
		super();
	}

	public NetCommandId getId() {
		return NetCommandId.CLIENT_END_TURN;
	}

	// JSON serialization

	public ClientCommandEndTurn initFrom(Game game, JsonValue jsonValue) {
		super.initFrom(game, jsonValue);
		return this;
	}

}
