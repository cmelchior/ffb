package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.Direction;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class ReportThrowIn implements IReport {

	private Direction fDirection;
	private int fDirectionRoll;
	private int[] fDistanceRoll;

	public ReportThrowIn() {
		super();
	}

	public ReportThrowIn(Direction pDirection, int pDirectionRoll, int[] pDistanceRoll) {
		fDirection = pDirection;
		fDirectionRoll = pDirectionRoll;
		fDistanceRoll = pDistanceRoll;
	}

	public ReportId getId() {
		return ReportId.THROW_IN;
	}

	public Direction getDirection() {
		return fDirection;
	}

	public int getDirectionRoll() {
		return fDirectionRoll;
	}

	public int[] getDistanceRoll() {
		return fDistanceRoll;
	}

	// transformation

	public IReport transform(Game game) {
		return new ReportThrowIn(getDirection().transform(), getDirectionRoll(), getDistanceRoll());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, getId());
		IJsonOption.DIRECTION.addTo(jsonObject, fDirection);
		IJsonOption.DIRECTION_ROLL.addTo(jsonObject, fDirectionRoll);
		IJsonOption.DISTANCE_ROLL.addTo(jsonObject, fDistanceRoll);
		return jsonObject;
	}

	public ReportThrowIn initFrom(Game game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(game, jsonObject));
		fDirection = (Direction) IJsonOption.DIRECTION.getFrom(game, jsonObject);
		fDirectionRoll = IJsonOption.DIRECTION_ROLL.getFrom(game, jsonObject);
		fDistanceRoll = IJsonOption.DISTANCE_ROLL.getFrom(game, jsonObject);
		return this;
	}

}
