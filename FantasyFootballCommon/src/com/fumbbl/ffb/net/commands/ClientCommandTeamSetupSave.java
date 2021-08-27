package com.fumbbl.ffb.net.commands;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.UtilJson;
import com.fumbbl.ffb.net.NetCommandId;
import com.fumbbl.ffb.util.ArrayTool;

/**
 * 
 * @author Kalimar
 */
public class ClientCommandTeamSetupSave extends ClientCommand {

	private String fSetupName;
	private List<Integer> fPlayerNumbers;
	private List<FieldCoordinate> fPlayerCoordinates;

	public ClientCommandTeamSetupSave() {
		fPlayerNumbers = new ArrayList<>();
		fPlayerCoordinates = new ArrayList<>();
	}

	public ClientCommandTeamSetupSave(String pSetupName, int[] pPlayerNumbers, FieldCoordinate[] pPlayerCoordinates) {
		this();
		fSetupName = pSetupName;
		addPlayerNumbers(pPlayerNumbers);
		addPlayerCoordinates(pPlayerCoordinates);
	}

	public NetCommandId getId() {
		return NetCommandId.CLIENT_TEAM_SETUP_SAVE;
	}

	public String getSetupName() {
		return fSetupName;
	}

	public int[] getPlayerNumbers() {
		int[] playerNumbers = new int[fPlayerNumbers.size()];
		for (int i = 0; i < playerNumbers.length; i++) {
			playerNumbers[i] = fPlayerNumbers.get(i);
		}
		return playerNumbers;
	}

	private void addPlayerNumber(int pPlayerNumber) {
		fPlayerNumbers.add(pPlayerNumber);
	}

	private void addPlayerNumbers(int[] pPlayerNumbers) {
		if (ArrayTool.isProvided(pPlayerNumbers)) {
			for (int i = 0; i < pPlayerNumbers.length; i++) {
				addPlayerNumber(pPlayerNumbers[i]);
			}
		}
	}

	public FieldCoordinate[] getPlayerCoordinates() {
		return fPlayerCoordinates.toArray(new FieldCoordinate[fPlayerCoordinates.size()]);
	}

	private void addPlayerCoordinate(FieldCoordinate pPlayerCoordinate) {
		if (pPlayerCoordinate != null) {
			fPlayerCoordinates.add(pPlayerCoordinate);
		}
	}

	private void addPlayerCoordinates(FieldCoordinate[] pPlayerCoordinates) {
		if (ArrayTool.isProvided(pPlayerCoordinates)) {
			for (FieldCoordinate playerCoordinate : pPlayerCoordinates) {
				addPlayerCoordinate(playerCoordinate);
			}
		}
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IJsonOption.SETUP_NAME.addTo(jsonObject, fSetupName);
		IJsonOption.PLAYER_NUMBERS.addTo(jsonObject, fPlayerNumbers);
		IJsonOption.PLAYER_COORDINATES.addTo(jsonObject, fPlayerCoordinates);
		return jsonObject;
	}

	public ClientCommandTeamSetupSave initFrom(IFactorySource game, JsonValue jsonValue) {
		super.initFrom(game, jsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
		fSetupName = IJsonOption.SETUP_NAME.getFrom(game, jsonObject);
		addPlayerNumbers(IJsonOption.PLAYER_NUMBERS.getFrom(game, jsonObject));
		addPlayerCoordinates(IJsonOption.PLAYER_COORDINATES.getFrom(game, jsonObject));
		return this;
	}

}
