package com.fumbbl.ffb.report;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.KickoffResult;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.UtilJson;

/**
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.COMMON)
public class ReportKickoffExtraReRoll implements IReport {

	private KickoffResult fKickoffResult;
	private int fRollHome;
	private boolean fHomeGainsReRoll;
	private int fRollAway;
	private boolean fAwayGainsReRoll;

	public ReportKickoffExtraReRoll() {
		super();
	}

	public ReportKickoffExtraReRoll(KickoffResult pKickoffResult, int pRollHome, boolean pHomeGainsReRoll, int pRollAway,
			boolean pAwayGainsReRoll) {
		fKickoffResult = pKickoffResult;
		fRollHome = pRollHome;
		fHomeGainsReRoll = pHomeGainsReRoll;
		fRollAway = pRollAway;
		fAwayGainsReRoll = pAwayGainsReRoll;
	}

	public ReportId getId() {
		return ReportId.KICKOFF_EXTRA_REROLL;
	}

	public KickoffResult getKickoffResult() {
		return fKickoffResult;
	}

	public int getRollHome() {
		return fRollHome;
	}

	public boolean isHomeGainsReRoll() {
		return fHomeGainsReRoll;
	}

	public int getRollAway() {
		return fRollAway;
	}

	public boolean isAwayGainsReRoll() {
		return fAwayGainsReRoll;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportKickoffExtraReRoll(getKickoffResult(), getRollAway(), isAwayGainsReRoll(), getRollHome(),
				isHomeGainsReRoll());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.KICKOFF_RESULT.addTo(jsonObject, fKickoffResult);
		IJsonOption.ROLL_HOME.addTo(jsonObject, fRollHome);
		IJsonOption.HOME_GAINS_RE_ROLL.addTo(jsonObject, fHomeGainsReRoll);
		IJsonOption.ROLL_AWAY.addTo(jsonObject, fRollAway);
		IJsonOption.AWAY_GAINS_RE_ROLL.addTo(jsonObject, fAwayGainsReRoll);
		return jsonObject;
	}

	public ReportKickoffExtraReRoll initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fKickoffResult = (KickoffResult) IJsonOption.KICKOFF_RESULT.getFrom(game, jsonObject);
		fRollHome = IJsonOption.ROLL_HOME.getFrom(game, jsonObject);
		fHomeGainsReRoll = IJsonOption.HOME_GAINS_RE_ROLL.getFrom(game, jsonObject);
		fRollAway = IJsonOption.ROLL_AWAY.getFrom(game, jsonObject);
		fAwayGainsReRoll = IJsonOption.AWAY_GAINS_RE_ROLL.getFrom(game, jsonObject);
		return this;
	}

}