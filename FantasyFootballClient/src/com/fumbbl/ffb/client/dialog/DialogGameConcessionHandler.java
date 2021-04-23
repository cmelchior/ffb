package com.fumbbl.ffb.client.dialog;

import com.fumbbl.ffb.ClientMode;
import com.fumbbl.ffb.ConcedeGameStatus;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.dialog.DialogId;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.util.UtilPlayer;

/**
 * 
 * @author Kalimar
 */
public class DialogGameConcessionHandler extends DialogHandler {

	public DialogGameConcessionHandler(FantasyFootballClient pClient) {
		super(pClient);
	}

	public void showDialog() {

		Game game = getClient().getGame();

		if ((ClientMode.PLAYER == getClient().getMode()) && (game.isHomePlaying())) {
			boolean legalConcession = UtilPlayer.findPlayersInReserveOrField(game, game.getTeamHome()).length <= 2;
			setDialog(new DialogConcedeGame(getClient(), legalConcession));
			getDialog().showDialog(this);
		}

	}

	public void dialogClosed(IDialog pDialog) {
		hideDialog();
		if (testDialogHasId(pDialog, DialogId.CONCEDE_GAME)) {
			DialogConcedeGame concessionDialog = (DialogConcedeGame) pDialog;
			if (concessionDialog.isChoiceYes()) {
				getClient().getCommunication().sendConcedeGame(ConcedeGameStatus.CONFIRMED);
			} else {
				getClient().getCommunication().sendConcedeGame(ConcedeGameStatus.DENIED);
			}
			Game game = getClient().getGame();
			game.setDialogParameter(null);
		}
	}

}