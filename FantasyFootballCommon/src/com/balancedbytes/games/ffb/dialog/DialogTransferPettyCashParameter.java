package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Game;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class DialogTransferPettyCashParameter extends DialogWithoutParameter {

	public DialogTransferPettyCashParameter() {
		super();
	}

	public DialogId getId() {
		return DialogId.TRANSFER_PETTY_CASH;
	}

	// transformation

	public IDialogParameter transform() {
		return new DialogTransferPettyCashParameter();
	}

	// JSON serialization

	public DialogTransferPettyCashParameter initFrom(Game game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilDialogParameter.validateDialogId(this, (DialogId) IJsonOption.DIALOG_ID.getFrom(game, jsonObject));
		return this;
	}

}
