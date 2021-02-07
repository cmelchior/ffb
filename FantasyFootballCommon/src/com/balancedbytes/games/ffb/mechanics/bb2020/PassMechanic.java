package com.balancedbytes.games.ffb.mechanics.bb2020;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.PassingModifiers;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.ReRolledActions;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.factory.PassModifierFactory;
import com.balancedbytes.games.ffb.mechanics.PassResult;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;

import java.util.Collection;
import java.util.Optional;

@RulesCollection(RulesCollection.Rules.BB2020)
public class PassMechanic extends com.balancedbytes.games.ffb.mechanics.PassMechanic {

	@Override
	protected String[] throwingRangeTable() {
		return new String[] {
			"T Q Q Q S S S L L L L B B B",
			"Q Q Q Q S S S L L L L B B B",
			"Q Q Q S S S S L L L L B B B",
			"Q Q S S S S S L L L B B B  ",
			"S S S S S S L L L L B B B  ",
			"S S S S S L L L L B B B    ",
			"S S S S L L L L L B B B    ",
			"L L L L L L L L B B B      ",
			"L L L L L L L B B B B      ",
			"L L L L L B B B B B        ",
			"L L L B B B B B B          ",
			"B B B B B B B              ",
			"B B B B B                  ",
			"B B B                      " };

	}

	@Override
	public Optional<Integer> minimumRoll(Player<?> thrower, PassingDistance distance, Collection<PassModifier> modifiers) {
		if (thrower.getPassing() > 0) {
			return Optional.of(minimumRollInternal(thrower, distance, modifiers));
		} else {
			return Optional.empty();
		}
	}

	private int minimumRollInternal(Player<?> thrower, PassingDistance distance, Collection<PassModifier> modifiers) {
		int roll = thrower.getPassing() + distance.getModifier2020() + modifiers.stream().mapToInt(PassModifier::getModifier).sum();
		return Math.max(roll, 2);
	}

	@Override
	public PassResult evaluatePass(Player<?> thrower, int roll, PassingDistance distance, Collection<PassModifier> modifiers, boolean bombAction) {
		if (thrower.getPassing() <= 0) {
			return PassResult.FUMBLE;
		}
		int minimumRoll = minimumRollInternal(thrower, distance, modifiers);
		if (roll == 1) {
			if (thrower.hasSkillWithProperty(NamedProperties.dontDropFumbles) && !bombAction) {
				return PassResult.SAVED_FUMBLE;
			} else {
				return PassResult.FUMBLE;
			}
		} else if (roll == 6) {
			return PassResult.ACCURATE;
		} else if (isModifiedFumble(roll, distance, modifiers)) {
			return PassResult.WILDLY_INACCURATE;
		} else if (roll < minimumRoll) {
			return PassResult.INACCURATE;
		} else {
			return PassResult.ACCURATE;
		}
	}

	@Override
	public String formatRollRequirement(PassingDistance distance, String formattedModifiers, Player<?> thrower) {
		if (thrower.getPassing() <= 0) {
			return "";
		}
		return " (Roll - " + distance.getModifier2020() + " " + distance.getName() + formattedModifiers +
			" >= PA " + thrower.getPassing() + "+).";
	}

	@Override
	public boolean eligibleToReRoll(ReRolledAction reRolledAction, Player<?> thrower) {
		return reRolledAction != ReRolledActions.PASS && thrower.getPassing() > 0;
	}

	public boolean isModifiedFumble(int roll, PassingDistance pPassingDistance, Collection<PassModifier> pPassModifiers) {
		int modifierTotal = calculateModifiers(pPassModifiers);
		return roll - modifierTotal - pPassingDistance.getModifier2020() <= 1;
	}

	@Override
	public String formatReportRoll(int roll, Player<?> thrower) {
		if (thrower.getPassing() > 0) {
			return "Pass Roll [ " + roll + " ]";
		}
		return "Pass fumbled automatically as " + thrower.getName() + " has no Passing Ability";
	}

	@Override
	public PassingDistance findPassingDistance(Game pGame, FieldCoordinate pFromCoordinate,
	                                           FieldCoordinate pToCoordinate, boolean pThrowTeamMate) {
		PassingDistance passingDistance = null;
		if ((pFromCoordinate != null) && (pToCoordinate != null)) {
			int deltaY = Math.abs(pToCoordinate.getY() - pFromCoordinate.getY());
			int deltaX = Math.abs(pToCoordinate.getX() - pFromCoordinate.getX());
			if ((deltaY < 14) && (deltaX < 14)) {
				passingDistance = PASSING_DISTANCES_TABLE[deltaY][deltaX];
			}
			if ((pThrowTeamMate || new PassModifierFactory().activeModifiers(pGame, PassModifier.class).contains(PassingModifiers.BLIZZARD))
				&& ((passingDistance == PassingDistance.LONG_BOMB) || (passingDistance == PassingDistance.LONG_PASS))) {
				passingDistance = null;
			}
		}
		return passingDistance;
	}
}