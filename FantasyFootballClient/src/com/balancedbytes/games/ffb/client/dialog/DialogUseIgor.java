package com.balancedbytes.games.ffb.client.dialog;

import com.balancedbytes.games.ffb.client.FantasyFootballClient;
import com.balancedbytes.games.ffb.IIconProperty;
import com.balancedbytes.games.ffb.dialog.DialogId;
import com.balancedbytes.games.ffb.dialog.DialogUseIgorParameter;

/**
 * 
 * @author Kalimar
 */
public class DialogUseIgor extends DialogYesOrNoQuestion {

	private DialogUseIgorParameter fDialogParameter;

	public DialogUseIgor(FantasyFootballClient pClient, DialogUseIgorParameter pDialogParameter) {
		super(pClient, "Use Igor", createMessages(pClient, pDialogParameter), IIconProperty.RESOURCE_IGOR);
		fDialogParameter = pDialogParameter;
	}

	public DialogId getId() {
		return DialogId.USE_IGOR;
	}

	public String getPlayerId() {
		return fDialogParameter.getPlayerId();
	}

	public DialogUseIgorParameter getDialogParameter() {
		return fDialogParameter;
	}

	private static String[] createMessages(FantasyFootballClient pClient, DialogUseIgorParameter pDialogParameter) {
		String playerName = pClient.getGame().getPlayerById(pDialogParameter.getPlayerId()).getName();
		String[] messages = new String[2];
		messages[0] = "Do you want to use your Igor for " + playerName +"?";
		messages[1] = "Using the Igor will re-roll the failed Regeneration.";
		return messages;
	}
}
