package com.fumbbl.ffb.report.bb2016;

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
@RulesCollection(RulesCollection.Rules.BB2016)
public class ReportCardsBought implements IReport {

	private String fTeamId;
	private int fNrOfCards;
	private int fGold;

	public ReportCardsBought() {
		super();
	}

	public ReportCardsBought(String pTeamId, int pNrOfCards, int pGold) {
		fTeamId = pTeamId;
		fNrOfCards = pNrOfCards;
		fGold = pGold;
	}

	public ReportId getId() {
		return ReportId.CARDS_BOUGHT;
	}

	public String getTeamId() {
		return fTeamId;
	}

	public int getNrOfCards() {
		return fNrOfCards;
	}

	public int getGold() {
		return fGold;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportCardsBought(getTeamId(), getNrOfCards(), getGold());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.TEAM_ID.addTo(jsonObject, fTeamId);
		IJsonOption.NR_OF_CARDS.addTo(jsonObject, fNrOfCards);
		IJsonOption.GOLD.addTo(jsonObject, fGold);
		return jsonObject;
	}

	public ReportCardsBought initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fTeamId = IJsonOption.TEAM_ID.getFrom(game, jsonObject);
		fNrOfCards = IJsonOption.NR_OF_CARDS.getFrom(game, jsonObject);
		fGold = IJsonOption.GOLD.getFrom(game, jsonObject);
		return this;
	}

}
