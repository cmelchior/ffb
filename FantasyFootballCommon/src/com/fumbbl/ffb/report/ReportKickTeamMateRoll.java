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
public class ReportKickTeamMateRoll implements IReport {

	private String fKickingPlayerId;
	private String fKickedPlayerId;
	private int fKickDistance;
	private boolean fSuccessful;
	private boolean fReRolled;
	private int[] fRoll;

	public ReportKickTeamMateRoll() {
		fRoll = new int[2];
	}

	@Override
	public ReportId getId() {
		return ReportId.KICK_TEAM_MATE_ROLL;
	}

	public ReportKickTeamMateRoll(String pKickingPlayerId, String pKickedPlayerId, boolean pSuccessful, int[] pRoll,
			boolean pReRolled, int pKickDistance) {
		fKickingPlayerId = pKickingPlayerId;
		fKickedPlayerId = pKickedPlayerId;
		fKickDistance = pKickDistance;
		fSuccessful = pSuccessful;
		fReRolled = pReRolled;
		fRoll = pRoll;
	}

	public String getKickingPlayerId() {
		return fKickingPlayerId;
	}

	public String getKickedPlayerId() {
		return fKickedPlayerId;
	}

	public int getKickDistance() {
		return fKickDistance;
	}

	public boolean isSuccessful() {
		return fSuccessful;
	}

	public int[] getRoll() {
		return fRoll;
	}

	public boolean isReRolled() {
		return fReRolled;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportKickTeamMateRoll(getKickingPlayerId(), getKickedPlayerId(), isSuccessful(), getRoll(),
				isReRolled(), getKickDistance());
	}

	// JSON serialization

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.PLAYER_ID.addTo(jsonObject, fKickingPlayerId);
		IJsonOption.DEFENDER_ID.addTo(jsonObject, fKickedPlayerId);
		IJsonOption.DISTANCE.addTo(jsonObject, fKickDistance);
		IJsonOption.SUCCESSFUL.addTo(jsonObject, fSuccessful);
		IJsonOption.ROLLS.addTo(jsonObject, fRoll);
		IJsonOption.RE_ROLLED.addTo(jsonObject, fReRolled);
		return jsonObject;
	}

	@Override
	public ReportKickTeamMateRoll initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fKickingPlayerId = IJsonOption.PLAYER_ID.getFrom(game, jsonObject);
		fKickedPlayerId = IJsonOption.DEFENDER_ID.getFrom(game, jsonObject);
		fKickDistance = IJsonOption.DISTANCE.getFrom(game, jsonObject);
		fSuccessful = IJsonOption.SUCCESSFUL.getFrom(game, jsonObject);
		fRoll = IJsonOption.ROLLS.getFrom(game, jsonObject);
		fReRolled = IJsonOption.RE_ROLLED.getFrom(game, jsonObject);
		return this;
	}

}