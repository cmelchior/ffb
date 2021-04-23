package com.fumbbl.ffb.report;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.UtilJson;

/**
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.COMMON)
public class ReportBlockRoll implements IReport {

	private int[] blockRoll;
	private String choosingTeamId;
	private String defenderId;

	public ReportBlockRoll() {
		super();
	}

	public ReportBlockRoll(String choosingTeamId, int[] blockRoll) {
		this(choosingTeamId, blockRoll, null);
	}

	public ReportBlockRoll(String choosingTeamId, int[] blockRoll, String defenderId) {
		this.choosingTeamId = choosingTeamId;
		this.blockRoll = blockRoll;
		this.defenderId = defenderId;
	}

	public ReportId getId() {
		return ReportId.BLOCK_ROLL;
	}

	public String getChoosingTeamId() {
		return choosingTeamId;
	}

	public int[] getBlockRoll() {
		return blockRoll;
	}

	public String getDefenderId() {
		return defenderId;
	}
// transformation

	public IReport transform(IFactorySource source) {
		return new ReportBlockRoll(getChoosingTeamId(), getBlockRoll(), defenderId);
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.CHOOSING_TEAM_ID.addTo(jsonObject, choosingTeamId);
		IJsonOption.BLOCK_ROLL.addTo(jsonObject, blockRoll);
		IJsonOption.DEFENDER_ID.addTo(jsonObject, defenderId);
		return jsonObject;
	}

	public ReportBlockRoll initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		choosingTeamId = IJsonOption.CHOOSING_TEAM_ID.getFrom(game, jsonObject);
		blockRoll = IJsonOption.BLOCK_ROLL.getFrom(game, jsonObject);
		defenderId = IJsonOption.DEFENDER_ID.getFrom(game, jsonObject);
		return this;
	}

}