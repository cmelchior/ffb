package com.fumbbl.ffb.report.bb2020;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.UtilJson;
import com.fumbbl.ffb.report.IReport;
import com.fumbbl.ffb.report.ReportId;
import com.fumbbl.ffb.report.UtilReport;

/**
 *
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.BB2020)
public class ReportDedicatedFans implements IReport {

	private int rollHome;
	private int modifierHome;
	private int rollAway;
	private int modifierAway;
	private String concededTeam;
	private boolean conceded;

	public ReportDedicatedFans() {
		super();
	}

	public ReportDedicatedFans(int rollHome, int modifierHome, int rollAway, int modifierAway, String concededTeam, boolean conceded) {
		this.rollHome = rollHome;
		this.modifierHome = modifierHome;
		this.rollAway = rollAway;
		this.modifierAway = modifierAway;
		this.concededTeam = concededTeam;
		this.conceded = conceded;
	}

	public ReportId getId() {
		return ReportId.DEDICATED_FANS;
	}

	public int getRollHome() {
		return rollHome;
	}

	public int getModifierHome() {
		return modifierHome;
	}

	public int getRollAway() {
		return rollAway;
	}

	public int getModifierAway() {
		return modifierAway;
	}

	public String getConcededTeam() {
		return concededTeam;
	}

	public boolean isConceded() {
		return conceded;
	}
// transformation

	public IReport transform(IFactorySource source) {
		return new ReportDedicatedFans(rollAway, modifierAway, rollHome, modifierHome, concededTeam, conceded);
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.ROLL_HOME.addTo(jsonObject, rollHome);
		IJsonOption.DEDICATED_FANS_MODIFIER_HOME.addTo(jsonObject, modifierHome);
		IJsonOption.ROLL_AWAY.addTo(jsonObject, rollAway);
		IJsonOption.DEDICATED_FANS_MODIFIER_AWAY.addTo(jsonObject, modifierAway);
		IJsonOption.TEAM_ID.addTo(jsonObject, concededTeam);
		IJsonOption.CONCEDED.addTo(jsonObject, conceded);
		return jsonObject;
	}

	public ReportDedicatedFans initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		rollHome = IJsonOption.ROLL_HOME.getFrom(game, jsonObject);
		modifierHome = IJsonOption.DEDICATED_FANS_MODIFIER_HOME.getFrom(game, jsonObject);
		rollAway = IJsonOption.ROLL_AWAY.getFrom(game, jsonObject);
		modifierAway = IJsonOption.DEDICATED_FANS_MODIFIER_AWAY.getFrom(game, jsonObject);
		concededTeam = IJsonOption.TEAM_ID.getFrom(game, jsonObject);
		conceded = IJsonOption.CONCEDED.getFrom(game, jsonObject);
		return this;
	}

}