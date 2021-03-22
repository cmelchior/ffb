package com.balancedbytes.games.ffb.mechanics.bb2020;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.FieldCoordinateBounds;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.property.NamedProperties;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPlayer;

import java.util.Arrays;

@RulesCollection(RulesCollection.Rules.BB2020)
public class JumpMechanic extends com.balancedbytes.games.ffb.mechanics.JumpMechanic {
	@Override
	public boolean isAvailableAsNextMove(Game game, ActingPlayer actingPlayer, boolean jumping) {
		return canStillJump(game, actingPlayer) && UtilPlayer.isNextMovePossible(game, jumping);
	}

	@Override
	public boolean canStillJump(Game game, ActingPlayer actingPlayer) {
		return UtilCards.hasUnusedSkillWithProperty(actingPlayer, NamedProperties.canLeap)
			|| hasProneOrStunnedPlayersAdjacent(game, game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer()));
	}

	@Override
	public boolean canJump(Game game, Player<?> player, FieldCoordinate coordinate) {
		return player.hasSkillProperty(NamedProperties.canLeap) || hasProneOrStunnedPlayersAdjacent(game, coordinate);
	}

	@Override
	public boolean isValidJump(Game game, FieldCoordinate from, FieldCoordinate to) {
		return !to.equals(from) && to.distanceInSteps(from) < 3;
	}

	private boolean hasProneOrStunnedPlayersAdjacent(Game game, FieldCoordinate coordinate) {
		return Arrays.stream(game.getFieldModel().findAdjacentCoordinates(coordinate, FieldCoordinateBounds.FIELD, 1, false))
			.map(game.getFieldModel()::getPlayer)
			.map(game.getFieldModel()::getPlayerState)
			.anyMatch(state -> state.isStunned() || state.isStunned());
	}
}
