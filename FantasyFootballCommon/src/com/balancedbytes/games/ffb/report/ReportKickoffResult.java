package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.KickoffResult;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class ReportKickoffResult implements IReport {

	private KickoffResult fKickoffResult;
	private int[] fKickoffRoll;

	public ReportKickoffResult() {
		super();
	}

	public ReportKickoffResult(KickoffResult pKickoffResult, int[] pKickoffRoll) {
		fKickoffResult = pKickoffResult;
		fKickoffRoll = pKickoffRoll;
	}

	public ReportId getId() {
		return ReportId.KICKOFF_RESULT;
	}

	public KickoffResult getKickoffResult() {
		return fKickoffResult;
	}

	public int[] getKickoffRoll() {
		return fKickoffRoll;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportKickoffResult(getKickoffResult(), getKickoffRoll());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.KICKOFF_RESULT.addTo(jsonObject, fKickoffResult);
		IJsonOption.KICKOFF_ROLL.addTo(jsonObject, fKickoffRoll);
		return jsonObject;
	}

	public ReportKickoffResult initFrom(IFactorySource source, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(source, jsonObject));
		fKickoffResult = (KickoffResult) IJsonOption.KICKOFF_RESULT.getFrom(source, jsonObject);
		fKickoffRoll = IJsonOption.KICKOFF_ROLL.getFrom(source, jsonObject);
		return this;
	}

}
