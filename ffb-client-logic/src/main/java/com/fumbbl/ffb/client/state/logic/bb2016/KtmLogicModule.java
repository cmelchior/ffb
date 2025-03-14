package com.fumbbl.ffb.client.state.logic.bb2016;

import com.fumbbl.ffb.ClientStateId;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.PlayerAction;
import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.client.FantasyFootballClient;
import com.fumbbl.ffb.client.net.ClientCommunication;
import com.fumbbl.ffb.client.state.logic.ClientAction;
import com.fumbbl.ffb.client.state.logic.MoveLogicModule;
import com.fumbbl.ffb.client.state.logic.interaction.ActionContext;
import com.fumbbl.ffb.client.state.logic.interaction.InteractionResult;
import com.fumbbl.ffb.model.ActingPlayer;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.property.NamedProperties;

import java.util.HashSet;
import java.util.Set;

public class KtmLogicModule extends MoveLogicModule {
	public KtmLogicModule(FantasyFootballClient client) {
		super(client);
	}

	@Override
	public ClientStateId getId() {
		return ClientStateId.KICK_TEAM_MATE;
	}

	public boolean canBeKicked(Player<?> player) {
		Game game = client.getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		PlayerState catcherState = game.getFieldModel().getPlayerState(player);
		FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
		FieldCoordinate catcherCoordinate = game.getFieldModel().getPlayerCoordinate(player);
		// added a check so you could not throw the opponents players, maybe this should
		// be in the server-check?
		return (actingPlayer.getPlayer().hasSkillProperty(NamedProperties.canKickTeamMates)
			&& player.hasSkillProperty(NamedProperties.canBeKicked) && catcherState.hasTacklezones()
			&& catcherCoordinate.isAdjacent(throwerCoordinate)
			&& (actingPlayer.getPlayer().getTeam() == player.getTeam()));
	}

	@Override
	public InteractionResult playerInteraction(Player<?> player) {
		Game game = client.getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		if (player == actingPlayer.getPlayer()) {
			return super.playerInteraction(player);
		} else {
			if ((game.getDefender() == null) && canBeKicked(player)) {
				return InteractionResult.selectAction(ktmActionContext());
			}
			return InteractionResult.ignore();
		}
	}

	@Override
	public InteractionResult fieldInteraction(FieldCoordinate ignoredCoordinate) {
		Game game = client.getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		if (actingPlayer.getPlayerAction() == PlayerAction.KICK_TEAM_MATE_MOVE) {
			return InteractionResult.perform();
		}
		return InteractionResult.ignore();
	}

	@Override
	public InteractionResult playerPeek(Player<?> player) {
		Game game = client.getGame();
		client.getClientData().setSelectedPlayer(player);
		if ((game.getDefender() == null) && (game.getPassCoordinate() == null)) {
			if (canBeKicked(player)) {
				return InteractionResult.perform();
			} else {
				return InteractionResult.reset();
			}
		}
		return InteractionResult.ignore();
	}

	@Override
	public Set<ClientAction> availableActions() {
		return new HashSet<ClientAction>() {{
			add(ClientAction.PASS_SHORT);
			add(ClientAction.PASS_LONG);
			add(ClientAction.END_MOVE);
		}};
	}

	@Override
	protected void performAvailableAction(Player<?> player, ClientAction action) {
		ActingPlayer actingPlayer = client.getGame().getActingPlayer();
		ClientCommunication communication = client.getCommunication();
		switch (action) {
			case PASS_SHORT:
				communication.sendKickTeamMate(actingPlayer.getPlayerId(), player.getId(), 1);
				break;
			case PASS_LONG:
				communication.sendKickTeamMate(actingPlayer.getPlayerId(), player.getId(), 2);
				break;
			default:
				super.performAvailableAction(player, action);
		}
	}

	private ActionContext ktmActionContext() {
		ActionContext actionContext = new ActionContext();
		actionContext.add(ClientAction.PASS_SHORT);
		actionContext.add(ClientAction.PASS_LONG);
		return actionContext;
	}
}
