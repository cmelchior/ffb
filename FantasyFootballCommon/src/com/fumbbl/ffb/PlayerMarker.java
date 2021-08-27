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
public class PlayerMarker implements IJsonSerializable {

	private String fPlayerId;
	private String fHomeText;
	private String fAwayText;

	public PlayerMarker() {
		super();
	}

	public PlayerMarker(String pPlayerId) {
		fPlayerId = pPlayerId;
	}

	public String getPlayerId() {
		return fPlayerId;
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
		return getPlayerId().hashCode();
	}

	public boolean equals(Object pObj) {
		return ((pObj instanceof PlayerMarker) && getPlayerId().equals(((PlayerMarker) pObj).getPlayerId()));
	}

	// Transformation

	public PlayerMarker transform() {
		PlayerMarker transformedMarker = new PlayerMarker(getPlayerId());
		transformedMarker.setAwayText(getHomeText());
		transformedMarker.setHomeText(getAwayText());
		return transformedMarker;
	}

	public static PlayerMarker transform(PlayerMarker pFieldMarker) {
		return (pFieldMarker != null) ? pFieldMarker.transform() : null;
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.PLAYER_ID.addTo(jsonObject, fPlayerId);
		IJsonOption.HOME_TEXT.addTo(jsonObject, fHomeText);
		IJsonOption.AWAY_TEXT.addTo(jsonObject, fAwayText);
		return jsonObject;
	}

	public PlayerMarker initFrom(IFactorySource game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		fPlayerId = IJsonOption.PLAYER_ID.getFrom(game, jsonObject);
		fHomeText = IJsonOption.HOME_TEXT.getFrom(game, jsonObject);
		fAwayText = IJsonOption.AWAY_TEXT.getFrom(game, jsonObject);
		return this;
	}

}
