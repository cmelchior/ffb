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
public class ReportBombOutOfBounds implements IReport {

	public ReportBombOutOfBounds() {
		super();
	}

	public ReportId getId() {
		return ReportId.BOMB_OUT_OF_BOUNDS;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportBombOutOfBounds();
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		return jsonObject;
	}

	public ReportBombOutOfBounds initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		return this;
	}

}
