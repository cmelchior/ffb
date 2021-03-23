package com.balancedbytes.games.ffb.mechanics.bb2020;

import com.balancedbytes.games.ffb.FantasyFootballException;
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
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public boolean isValidJump(Game game, Player<?> player, FieldCoordinate from, FieldCoordinate to) {
		return !to.equals(from) && to.distanceInSteps(from) == 2 && ( player.hasSkillProperty(NamedProperties.canLeap) || hasProneOrStunnedPlayerOnPath(game, from, to));
	}

	private boolean hasProneOrStunnedPlayerOnPath(Game game, FieldCoordinate from, FieldCoordinate to) {
		return hasProneOrStunnedPlayers(game, findPossiblePathSquares(from, to).stream());
	}

	private Set<FieldCoordinate> findPossiblePathSquares(FieldCoordinate from, FieldCoordinate to) {
		return combineVariances(dimensionVariance(to.getX() - from.getX()), dimensionVariance(to.getY() - from.getY()), from).stream()
			.filter(FieldCoordinateBounds.FIELD::isInBounds).collect(Collectors.toSet());
	}

	private Set<FieldCoordinate> combineVariances(int[] xVariances, int[] yVariances, FieldCoordinate startCoordinate) {
		Set<FieldCoordinate> coordinates = new HashSet<>();
		for (int xVariance : xVariances) {
			for (int yVariance : yVariances) {
				coordinates.add(new FieldCoordinate(startCoordinate.getX() + xVariance, startCoordinate.getY() + yVariance));
			}
		}
		return coordinates;
	}

	private int[] dimensionVariance(int diff) {
		if (Math.abs(diff) == 2) {
			return new int[]{ diff/2 };
		} else if (Math.abs(diff) == 1) {
			return new int[] {diff, 0};
		} else if (diff == 0) {
			return new int[] {0};
		} else {
			throw new FantasyFootballException("Received illegal dimension difference of " + diff);
		}
	}

	private boolean hasProneOrStunnedPlayersAdjacent(Game game, FieldCoordinate coordinate) {
		FieldCoordinate[] coordinates = game.getFieldModel().findAdjacentCoordinates(coordinate, FieldCoordinateBounds.FIELD, 1, false);
		return hasProneOrStunnedPlayers(game, Arrays.stream(coordinates));
	}

	private boolean hasProneOrStunnedPlayers(Game game, Stream<FieldCoordinate> coordinates) {
		return coordinates
			.map(game.getFieldModel()::getPlayer)
			.filter(Objects::nonNull)
			.map(game.getFieldModel()::getPlayerState)
			.filter(Objects::nonNull)
			.anyMatch(state -> state.isStunned() || state.isProne());
	}
}
