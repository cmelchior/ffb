package com.fumbbl.ffb.client.state;

import com.fumbbl.ffb.ClientStateId;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.IIconProperty;
import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.RangeRuler;
import com.fumbbl.ffb.Weather;
import com.fumbbl.ffb.client.ActionKey;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.FieldComponent;
import com.fumbbl.ffb.client.IconCache;
import com.fumbbl.ffb.client.UserInterface;
import com.fumbbl.ffb.client.net.ClientCommunication;
import com.fumbbl.ffb.client.util.UtilClientCursor;
import com.fumbbl.ffb.model.ActingPlayer;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.property.NamedProperties;
import com.fumbbl.ffb.net.NetCommand;
import com.fumbbl.ffb.util.UtilPlayer;
import com.fumbbl.ffb.util.UtilRangeRuler;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kalimar
 */
public class ClientStatePass extends ClientStateMove {

	private boolean fShowRangeRuler;
	private final RangeGridHandler fRangeGridHandler;

	protected ClientStatePass(FantasyFootballClient pClient) {
		super(pClient);
		fRangeGridHandler = new RangeGridHandler(pClient, false);
	}

	public ClientStateId getId() {
		return ClientStateId.PASS;
	}

	public void enterState() {
		super.enterState();
		setSelectable(true);
		fShowRangeRuler = true;
		fRangeGridHandler.refreshSettings();
	}

	protected void clickOnPlayer(Player<?> pPlayer) {
		Game game = getClient().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		UserInterface userInterface = getClient().getUserInterface();
		if (pPlayer == actingPlayer.getPlayer()) {
			super.clickOnPlayer(pPlayer);
		} else {
			if ((PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction())
					|| (UtilPlayer.hasBall(game, actingPlayer.getPlayer())
							&& ((PlayerAction.PASS == actingPlayer.getPlayerAction()) || canPlayerGetPass(pPlayer)))) {
				game.setPassCoordinate(game.getFieldModel().getPlayerCoordinate(pPlayer));
				getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), game.getPassCoordinate());
				game.getFieldModel().setRangeRuler(null);
				userInterface.getFieldComponent().refresh();
			}
		}
	}

	protected void clickOnField(FieldCoordinate pCoordinate) {
		Game game = getClient().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		UserInterface userInterface = getClient().getUserInterface();
		if (actingPlayer.getPlayerAction() == PlayerAction.PASS_MOVE) {
			super.clickOnField(pCoordinate);
		} else {
			if ((PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction())
					|| UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
				game.setPassCoordinate(pCoordinate);
				getClient().getCommunication().sendPass(actingPlayer.getPlayerId(), game.getPassCoordinate());
				game.getFieldModel().setRangeRuler(null);
				userInterface.getFieldComponent().refresh();
			}
		}
	}

	protected boolean mouseOverPlayer(Player<?> pPlayer) {
		boolean selectable = false;
		Game game = getClient().getGame();
		UserInterface userInterface = getClient().getUserInterface();
		ActingPlayer actingPlayer = game.getActingPlayer();
		if ((PlayerAction.HAIL_MARY_PASS != actingPlayer.getPlayerAction())
				&& UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
			FieldCoordinate catcherCoordinate = game.getFieldModel().getPlayerCoordinate(pPlayer);
			if ((PlayerAction.PASS == actingPlayer.getPlayerAction()) || canPlayerGetPass(pPlayer)) {
				drawRangeRuler(catcherCoordinate);
			}
		} else {
			game.getFieldModel().setRangeRuler(null);
			FieldComponent fieldComponent = userInterface.getFieldComponent();
			fieldComponent.getLayerUnderPlayers().clearMovePath();
			fieldComponent.refresh();
			selectable = true;
			if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
				UtilClientCursor.setCustomCursor(userInterface, IIconProperty.CURSOR_PASS);
			} else {
				UtilClientCursor.setDefaultCursor(userInterface);
			}
		}
		getClient().getClientData().setSelectedPlayer(pPlayer);
		userInterface.refreshSideBars();
		return selectable;
	}

	protected boolean mouseOverField(FieldCoordinate pCoordinate) {
		Game game = getClient().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		UserInterface userInterface = getClient().getUserInterface();
		boolean selectable = false;
		if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
			game.getFieldModel().setRangeRuler(null);
			userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
			userInterface.getFieldComponent().refresh();
			selectable = true;
			UtilClientCursor.setCustomCursor(userInterface, IIconProperty.CURSOR_PASS);
		} else if (actingPlayer.getPlayerAction() == PlayerAction.PASS_MOVE) {
			game.getFieldModel().setRangeRuler(null);
			userInterface.getFieldComponent().refresh();
			selectable = super.mouseOverField(pCoordinate);
		} else {
			drawRangeRuler(pCoordinate);
		}
		return selectable;
	}

	private void drawRangeRuler(FieldCoordinate pCoordinate) {
		RangeRuler rangeRuler = null;
		Game game = getClient().getGame();
		if (fShowRangeRuler && (game.getPassCoordinate() == null)) {
			ActingPlayer actingPlayer = game.getActingPlayer();
			UserInterface userInterface = getClient().getUserInterface();
			FieldComponent fieldComponent = userInterface.getFieldComponent();
			rangeRuler = UtilRangeRuler.createRangeRuler(game, actingPlayer.getPlayer(), pCoordinate, false);
			game.getFieldModel().setRangeRuler(rangeRuler);
			if (rangeRuler != null) {
				UtilClientCursor.setCustomCursor(userInterface, IIconProperty.CURSOR_PASS);
			} else {
				UtilClientCursor.setDefaultCursor(userInterface);
			}
			fieldComponent.getLayerUnderPlayers().clearMovePath();
			fieldComponent.refresh();
		}
	}

	public boolean canPlayerGetPass(Player<?> pCatcher) {
		boolean canGetPass = false;
		Game game = getClient().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		if ((pCatcher != null) && (actingPlayer.getPlayer() != null)) {
			PlayerState catcherState = game.getFieldModel().getPlayerState(pCatcher);
			canGetPass = ((catcherState != null)
					&& catcherState.hasTacklezones() && (game.getTeamHome() == pCatcher.getTeam())
					&& (!actingPlayer.isSufferingAnimosity() || actingPlayer.getRace().equals(pCatcher.getRace())));
		}
		return canGetPass;
	}

	@Override
	public void handleCommand(NetCommand pNetCommand) {
		fRangeGridHandler.refreshRangeGrid();
		super.handleCommand(pNetCommand);
	}

	@Override
	public void leaveState() {
		fRangeGridHandler.setShowRangeGrid(false);
		fRangeGridHandler.refreshRangeGrid();
	}

	protected void createAndShowPopupMenuForActingPlayer() {

		Game game = getClient().getGame();
		UserInterface userInterface = getClient().getUserInterface();
		IconCache iconCache = userInterface.getIconCache();
		userInterface.getFieldComponent().getLayerUnderPlayers().clearMovePath();
		List<JMenuItem> menuItemList = new ArrayList<>();
		ActingPlayer actingPlayer = game.getActingPlayer();

		if ((PlayerAction.PASS_MOVE == actingPlayer.getPlayerAction())
				&& UtilPlayer.hasBall(game, actingPlayer.getPlayer())) {
			JMenuItem passAction = new JMenuItem("Pass Ball (any square)",
					new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_PASS)));
			passAction.setMnemonic(IPlayerPopupMenuKeys.KEY_PASS);
			passAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_PASS, 0));
			menuItemList.add(passAction);
		}

		if (actingPlayer.getPlayer().hasSkillProperty(NamedProperties.canPassToAnySquare)
				&& UtilPlayer.hasBall(game, actingPlayer.getPlayer())
				&& !game.getFieldModel().getWeather().equals(Weather.BLIZZARD)) {
			String text = (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) ? "Don't use Hail Mary Pass"
					: "Use Hail Mary Pass";
			JMenuItem hailMaryPassAction = new JMenuItem(text,
					new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_TOGGLE_HAIL_MARY_PASS)));
			hailMaryPassAction.setMnemonic(IPlayerPopupMenuKeys.KEY_HAIL_MARY_PASS);
			hailMaryPassAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_HAIL_MARY_PASS, 0));
			menuItemList.add(hailMaryPassAction);
		}

		if (isJumpAvailableAsNextMove(game, actingPlayer,false)) {
			if (actingPlayer.isJumping()) {
				JMenuItem jumpAction = new JMenuItem("Don't Jump",
						new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_MOVE)));
				jumpAction.setMnemonic(IPlayerPopupMenuKeys.KEY_JUMP);
				jumpAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_JUMP, 0));
				menuItemList.add(jumpAction);
			} else {
				JMenuItem jumpAction = new JMenuItem("Jump",
					new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_JUMP)));
				jumpAction.setMnemonic(IPlayerPopupMenuKeys.KEY_JUMP);
				jumpAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_JUMP, 0));
				menuItemList.add(jumpAction);
			}
		}

		if (!actingPlayer.hasPassed()) {
			JMenuItem toggleRangeGridAction = new JMenuItem("Range Grid on/off",
				new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_TOGGLE_RANGE_GRID)));
			toggleRangeGridAction.setMnemonic(IPlayerPopupMenuKeys.KEY_RANGE_GRID);
			toggleRangeGridAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_RANGE_GRID, 0));
			menuItemList.add(toggleRangeGridAction);
		}

		if (!actingPlayer.hasPassed()) {
			if (!actingPlayer.isSufferingAnimosity()) {
				JMenuItem moveAction = new JMenuItem("Move",
					new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_MOVE)));
				moveAction.setMnemonic(IPlayerPopupMenuKeys.KEY_MOVE);
				moveAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_MOVE, 0));
				menuItemList.add(moveAction);
			}
		}

		String endMoveActionLabel = actingPlayer.hasActed() ? "End Move" : "Deselect Player";
		JMenuItem endMoveAction = new JMenuItem(endMoveActionLabel,
			new ImageIcon(iconCache.getIconByProperty(IIconProperty.ACTION_END_MOVE)));
		endMoveAction.setMnemonic(IPlayerPopupMenuKeys.KEY_END_MOVE);
		endMoveAction.setAccelerator(KeyStroke.getKeyStroke(IPlayerPopupMenuKeys.KEY_END_MOVE, 0));
		menuItemList.add(endMoveAction);

		createPopupMenu(menuItemList.toArray(new JMenuItem[menuItemList.size()]));
		showPopupMenuForPlayer(actingPlayer.getPlayer());

	}

	protected void menuItemSelected(Player<?> pPlayer, int pMenuKey) {
		Game game = getClient().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		ClientCommunication communication = getClient().getCommunication();
		if (pMenuKey == IPlayerPopupMenuKeys.KEY_RANGE_GRID) {
			fRangeGridHandler.setShowRangeGrid(!fRangeGridHandler.isShowRangeGrid());
			fRangeGridHandler.refreshRangeGrid();
		} else if (pMenuKey == IPlayerPopupMenuKeys.KEY_HAIL_MARY_PASS) {
			if (game.getActingPlayer().getPlayer().hasSkillProperty(NamedProperties.canPassToAnySquare)) {
				if (PlayerAction.HAIL_MARY_PASS == actingPlayer.getPlayerAction()) {
					communication.sendActingPlayer(pPlayer, PlayerAction.PASS, actingPlayer.isJumping());
					fShowRangeRuler = true;
				} else {
					communication.sendActingPlayer(pPlayer, PlayerAction.HAIL_MARY_PASS, actingPlayer.isJumping());
					fShowRangeRuler = false;
				}
				if (!fShowRangeRuler && (game.getFieldModel().getRangeRuler() != null)) {
					game.getFieldModel().setRangeRuler(null);
				}
			}

		} else {
			super.menuItemSelected(pPlayer, pMenuKey);
		}
	}

	public boolean actionKeyPressed(ActionKey pActionKey) {
		if (pActionKey == ActionKey.PLAYER_ACTION_RANGE_GRID) {
			menuItemSelected(null, IPlayerPopupMenuKeys.KEY_RANGE_GRID);
			return true;
		} else if (pActionKey == ActionKey.PLAYER_ACTION_HAIL_MARY_PASS) {
			menuItemSelected(null, IPlayerPopupMenuKeys.KEY_HAIL_MARY_PASS);
			return true;
		} else {
			return super.actionKeyPressed(pActionKey);
		}
	}

}
