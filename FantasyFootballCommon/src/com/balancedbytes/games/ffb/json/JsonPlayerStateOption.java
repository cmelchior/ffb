package com.balancedbytes.games.ffb.json;

import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.eclipsesource.json.JsonObject;

/**
 * 
 * @author Kalimar
 */
public class JsonPlayerStateOption extends JsonAbstractOption {

	public JsonPlayerStateOption(String pKey) {
		super(pKey);
	}

	public PlayerState getFrom(IFactorySource game, JsonObject pJsonObject) {
		return UtilJson.toPlayerState(getValueFrom(pJsonObject));
	}

	public void addTo(JsonObject pJsonObject, PlayerState pValue) {
		addValueTo(pJsonObject, UtilJson.toJsonValue(pValue));
	}

}
