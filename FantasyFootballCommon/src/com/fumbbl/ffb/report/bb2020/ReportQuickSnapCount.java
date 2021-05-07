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

@RulesCollection(RulesCollection.Rules.BB2020)
public class ReportQuickSnapCount implements IReport {

	private int amount;
	private int available;
	private int limit;

	public ReportQuickSnapCount() {
	}

	public ReportQuickSnapCount(int available, int amount, int limit) {
		this.amount = amount;
		this.available = available;
		this.limit = limit;
	}

	@Override
	public ReportId getId() {
		return ReportId.QUICK_SNAP_COUNT;
	}

	public int getAmount() {
		return amount;
	}

	public int getAvailable() {
		return available;
	}

	public int getLimit() {
		return limit;
	}

	@Override
	public IReport transform(IFactorySource source) {
		return new ReportQuickSnapCount(available, amount, limit);
	}

	@Override
	public Object initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		amount = IJsonOption.NR_OF_PLAYERS.getFrom(game, jsonObject);
		limit = IJsonOption.NR_OF_PLAYERS_ALLOWED.getFrom(game, jsonObject);
		available = IJsonOption.NUMBER.getFrom(game, jsonObject);
		return this;
	}

	@Override
	public JsonValue toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.NR_OF_PLAYERS.addTo(jsonObject, amount);
		IJsonOption.NUMBER.addTo(jsonObject, available);
		IJsonOption.NR_OF_PLAYERS_ALLOWED.addTo(jsonObject, limit);
		return jsonObject;
	}
}
