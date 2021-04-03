package com.balancedbytes.games.ffb.report.bb2016;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.report.IReport;
import com.balancedbytes.games.ffb.report.ReportId;
import com.balancedbytes.games.ffb.report.UtilReport;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.BB2016)
public class ReportInducementsBought implements IReport {

	private String fTeamId;
	private int fNrOfInducements;
	private int fNrOfStars;
	private int fNrOfMercenaries;
	private int fGold;

	public ReportInducementsBought() {
		super();
	}

	public ReportInducementsBought(String pTeamId, int pInducements, int pStars, int pMercenaries, int pGold) {
		fTeamId = pTeamId;
		fNrOfInducements = pInducements;
		fNrOfStars = pStars;
		fNrOfMercenaries = pMercenaries;
		fGold = pGold;
	}

	public ReportId getId() {
		return ReportId.INDUCEMENTS_BOUGHT;
	}

	public String getTeamId() {
		return fTeamId;
	}

	public int getNrOfInducements() {
		return fNrOfInducements;
	}

	public int getNrOfStars() {
		return fNrOfStars;
	}

	public int getNrOfMercenaries() {
		return fNrOfMercenaries;
	}

	public int getGold() {
		return fGold;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportInducementsBought(getTeamId(), getNrOfInducements(), getNrOfStars(), getNrOfMercenaries(),
				getGold());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.TEAM_ID.addTo(jsonObject, fTeamId);
		IJsonOption.NR_OF_INDUCEMENTS.addTo(jsonObject, fNrOfInducements);
		IJsonOption.NR_OF_STARS.addTo(jsonObject, fNrOfStars);
		IJsonOption.NR_OF_MERCENARIES.addTo(jsonObject, fNrOfMercenaries);
		IJsonOption.GOLD.addTo(jsonObject, fGold);
		return jsonObject;
	}

	public ReportInducementsBought initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fTeamId = IJsonOption.TEAM_ID.getFrom(game, jsonObject);
		fNrOfInducements = IJsonOption.NR_OF_INDUCEMENTS.getFrom(game, jsonObject);
		fNrOfStars = IJsonOption.NR_OF_STARS.getFrom(game, jsonObject);
		fNrOfMercenaries = IJsonOption.NR_OF_MERCENARIES.getFrom(game, jsonObject);
		fGold = IJsonOption.GOLD.getFrom(game, jsonObject);
		return this;
	}

}