package com.fumbbl.ffb.report;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.SeriousInjury;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.UtilJson;

/**
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.COMMON)
public class ReportApothecaryChoice implements IReport {

	private String fPlayerId;
	private PlayerState fPlayerState;
	private SeriousInjury fSeriousInjury;

	public ReportApothecaryChoice() {
		super();
	}

	public ReportApothecaryChoice(String pPlayerId, PlayerState pPlayerState, SeriousInjury pSeriousInjury) {
		fPlayerId = pPlayerId;
		fPlayerState = pPlayerState;
		fSeriousInjury = pSeriousInjury;
	}

	public ReportId getId() {
		return ReportId.APOTHECARY_CHOICE;
	}

	public String getPlayerId() {
		return fPlayerId;
	}

	public PlayerState getPlayerState() {
		return fPlayerState;
	}

	public SeriousInjury getSeriousInjury() {
		return fSeriousInjury;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportApothecaryChoice(getPlayerId(), getPlayerState(), getSeriousInjury());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.PLAYER_ID.addTo(jsonObject, fPlayerId);
		IJsonOption.PLAYER_STATE.addTo(jsonObject, fPlayerState);
		IJsonOption.SERIOUS_INJURY.addTo(jsonObject, fSeriousInjury);
		return jsonObject;
	}

	public ReportApothecaryChoice initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fPlayerId = IJsonOption.PLAYER_ID.getFrom(game, jsonObject);
		fPlayerState = IJsonOption.PLAYER_STATE.getFrom(game, jsonObject);
		fSeriousInjury = (SeriousInjury) IJsonOption.SERIOUS_INJURY.getFrom(game, jsonObject);
		return this;
	}

}