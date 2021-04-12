package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.BlockRoll;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DialogReRollBlockForTargetsParameter implements IDialogParameter {

	private String playerId;
	private List<String> reRollAvailableAgainst = new ArrayList<>();
	private boolean proReRollAvailable, teamReRollAvailable;
	private List<BlockRoll> blockRolls;

	public DialogReRollBlockForTargetsParameter() {
		super();
	}

	public DialogReRollBlockForTargetsParameter(String playerId, List<BlockRoll> blockRolls, List<String> reRollAvailableAgainst, boolean proReRollAvailable, boolean teamReRollAvailable) {
		this.reRollAvailableAgainst = reRollAvailableAgainst;
		this.proReRollAvailable = proReRollAvailable;
		this.playerId = playerId;
		this.teamReRollAvailable = teamReRollAvailable;
		this.blockRolls = blockRolls;
	}

	public DialogId getId() {
		return DialogId.RE_ROLL_BLOCK_FOR_TARGETS;
	}

	public List<String> getReRollAvailableAgainst() {
		return reRollAvailableAgainst;
	}

	public boolean isProReRollAvailable() {
		return proReRollAvailable;
	}

	public List<BlockRoll> getBlockRolls() {
		return blockRolls;
	}

	public String getPlayerId() {
		return playerId;
	}

	public boolean isTeamReRollAvailable() {
		return teamReRollAvailable;
	}

	// transformation

	public IDialogParameter transform() {
		return new DialogReRollBlockForTargetsParameter(getPlayerId(), getBlockRolls(), getReRollAvailableAgainst(), isProReRollAvailable(), teamReRollAvailable);
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.DIALOG_ID.addTo(jsonObject, getId());
		IJsonOption.RE_ROLL_AVAILABLE_AGAINST.addTo(jsonObject, reRollAvailableAgainst);
		IJsonOption.PRO_RE_ROLL_OPTION.addTo(jsonObject, proReRollAvailable);
		IJsonOption.TEAM_RE_ROLL_OPTION.addTo(jsonObject, teamReRollAvailable);
		JsonArray array = new JsonArray();
		blockRolls.forEach(roll -> array.add(roll.toJsonValue()));
		IJsonOption.BLOCK_ROLLS.addTo(jsonObject, array);
		IJsonOption.PLAYER_ID.addTo(jsonObject, playerId);
		return jsonObject;
	}

	public DialogReRollBlockForTargetsParameter initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilDialogParameter.validateDialogId(this, (DialogId) IJsonOption.DIALOG_ID.getFrom(game, jsonObject));
		reRollAvailableAgainst = Arrays.asList(IJsonOption.RE_ROLL_AVAILABLE_AGAINST.getFrom(game, jsonObject));
		proReRollAvailable = IJsonOption.PRO_RE_ROLL_OPTION.getFrom(game, jsonObject);
		teamReRollAvailable = IJsonOption.TEAM_RE_ROLL_OPTION.getFrom(game, jsonObject);
		JsonArray array =  IJsonOption.BLOCK_ROLLS.getFrom(game, jsonObject);
		blockRolls = array.values().stream().map(roll -> new BlockRoll().initFrom(game, roll)).collect(Collectors.toList());
		playerId = IJsonOption.PLAYER_ID.getFrom(game, jsonObject);
		return this;
	}

}
