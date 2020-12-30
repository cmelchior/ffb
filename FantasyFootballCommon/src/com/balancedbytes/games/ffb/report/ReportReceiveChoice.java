package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class ReportReceiveChoice implements IReport {

	private String fTeamId;
	private boolean fReceiveChoice;

	public ReportReceiveChoice() {
		super();
	}

	public ReportReceiveChoice(String pTeamId, boolean pChoiceReceive) {
		fTeamId = pTeamId;
		fReceiveChoice = pChoiceReceive;
	}

	public ReportId getId() {
		return ReportId.RECEIVE_CHOICE;
	}

	public String getTeamId() {
		return fTeamId;
	}

	public boolean isReceiveChoice() {
		return fReceiveChoice;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportReceiveChoice(getTeamId(), isReceiveChoice());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.TEAM_ID.addTo(jsonObject, fTeamId);
		IJsonOption.RECEIVE_CHOICE.addTo(jsonObject, fReceiveChoice);
		return jsonObject;
	}

	public ReportReceiveChoice initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fTeamId = IJsonOption.TEAM_ID.getFrom(game, jsonObject);
		fReceiveChoice = IJsonOption.RECEIVE_CHOICE.getFrom(game, jsonObject);
		return this;
	}

}
