package com.fumbbl.ffb.client.state;

import com.fumbbl.ffb.ClientStateId;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.IIconProperty;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.util.UtilClientCursor;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.util.UtilPassing;

/**
 *
 * @author Kalimar
 */
public class ClientStateInterception extends ClientState {

	protected ClientStateInterception(FantasyFootballClient pClient) {
		super(pClient);
	}

	public ClientStateId getId() {
		return ClientStateId.INTERCEPTION;
	}

	public void enterState() {
		super.enterState();
		setSelectable(true);
		setClickable(true);
	}

	protected void clickOnPlayer(Player<?> pPlayer) {
		if (isInterceptor(pPlayer)) {
			getClient().getCommunication().sendInterceptorChoice(pPlayer);
		}
	}

	protected boolean mouseOverPlayer(Player<?> pPlayer) {
		super.mouseOverPlayer(pPlayer);
		if (isInterceptor(pPlayer)) {
			UtilClientCursor.setCustomCursor(getClient().getUserInterface(), IIconProperty.CURSOR_PASS);
		} else {
			UtilClientCursor.setDefaultCursor(getClient().getUserInterface());
		}
		return true;
	}

	protected boolean mouseOverField(FieldCoordinate pCoordinate) {
		super.mouseOverField(pCoordinate);
		UtilClientCursor.setDefaultCursor(getClient().getUserInterface());
		return true;
	}

	private boolean isInterceptor(Player<?> pPlayer) {
		boolean isInterceptor = false;
		Game game = getClient().getGame();
		Player<?>[] interceptors = UtilPassing.findInterceptors(game, game.getThrower(), game.getPassCoordinate());
		for (int i = 0; i < interceptors.length; i++) {
			if (interceptors[i] == pPlayer) {
				isInterceptor = true;
				break;
			}
		}
		return isInterceptor;
	}

}