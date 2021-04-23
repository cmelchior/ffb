package com.fumbbl.ffb;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.IJsonOption;
import com.fumbbl.ffb.json.IJsonSerializable;
import com.fumbbl.ffb.json.UtilJson;

/**
 * 
 * @author Kalimar
 */
public class FieldMarker implements IJsonSerializable {

	private FieldCoordinate fCoordinate;
	private String fHomeText;
	private String fAwayText;

	public FieldMarker() {
		super();
	}

	public FieldMarker(FieldCoordinate pCoordinate) {
		fCoordinate = pCoordinate;
	}

	public FieldCoordinate getCoordinate() {
		return fCoordinate;
	}

	public void setHomeText(String pHomeText) {
		fHomeText = pHomeText;
	}

	public String getHomeText() {
		return fHomeText;
	}

	public void setAwayText(String pAwayText) {
		fAwayText = pAwayText;
	}

	public String getAwayText() {
		return fAwayText;
	}

	public int hashCode() {
		return getCoordinate().hashCode();
	}

	public boolean equals(Object pObj) {
		return ((pObj instanceof FieldMarker) && getCoordinate().equals(((FieldMarker) pObj).getCoordinate()));
	}

	// Transformation

	public FieldMarker transform() {
		FieldMarker transformedMarker = new FieldMarker(getCoordinate().transform());
		transformedMarker.setAwayText(getHomeText());
		transformedMarker.setHomeText(getAwayText());
		return transformedMarker;
	}

	public static FieldMarker transform(FieldMarker pFieldMarker) {
		return (pFieldMarker != null) ? pFieldMarker.transform() : null;
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.COORDINATE.addTo(jsonObject, fCoordinate);
		IJsonOption.HOME_TEXT.addTo(jsonObject, fHomeText);
		IJsonOption.AWAY_TEXT.addTo(jsonObject, fAwayText);
		return jsonObject;
	}

	public FieldMarker initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		fCoordinate = IJsonOption.COORDINATE.getFrom(game, jsonObject);
		fHomeText = IJsonOption.HOME_TEXT.getFrom(game, jsonObject);
		fAwayText = IJsonOption.AWAY_TEXT.getFrom(game, jsonObject);
		return this;
	}

}