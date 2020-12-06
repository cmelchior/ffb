package com.balancedbytes.games.ffb.net.commands;

import com.balancedbytes.games.ffb.ClientMode;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class ServerCommandLeave extends ServerCommand {

	private String fCoach;
	private ClientMode fClientMode;
	private int fSpectators;

	public ServerCommandLeave() {
		super();
	}

	public ServerCommandLeave(String pCoach, ClientMode pClientMode, int pSpectators) {
		fCoach = pCoach;
		fClientMode = pClientMode;
		fSpectators = pSpectators;
	}

	public NetCommandId getId() {
		return NetCommandId.SERVER_LEAVE;
	}

	public String getCoach() {
		return fCoach;
	}

	public ClientMode getClientMode() {
		return fClientMode;
	}

	public int getSpectators() {
		return fSpectators;
	}

	public boolean isReplayable() {
		return false;
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.NET_COMMAND_ID.addTo(jsonObject, getId());
		IJsonOption.COMMAND_NR.addTo(jsonObject, getCommandNr());
		IJsonOption.COACH.addTo(jsonObject, fCoach);
		IJsonOption.CLIENT_MODE.addTo(jsonObject, fClientMode);
		IJsonOption.SPECTATORS.addTo(jsonObject, fSpectators);
		return jsonObject;
	}

	public ServerCommandLeave initFrom(JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilNetCommand.validateCommandId(this, (NetCommandId) IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
		setCommandNr(IJsonOption.COMMAND_NR.getFrom(jsonObject));
		fCoach = IJsonOption.COACH.getFrom(jsonObject);
		fClientMode = (ClientMode) IJsonOption.CLIENT_MODE.getFrom(jsonObject);
		fSpectators = IJsonOption.SPECTATORS.getFrom(jsonObject);
		return this;
	}

}
