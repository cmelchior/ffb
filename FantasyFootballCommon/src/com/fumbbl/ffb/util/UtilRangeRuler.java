package com.fumbbl.ffb.util;

import java.util.Optional;
import java.util.Set;

import com.fumbbl.ffb.FactoryType;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.PassingDistance;
import com.fumbbl.ffb.RangeRuler;
import com.fumbbl.ffb.factory.PassModifierFactory;
import com.fumbbl.ffb.mechanics.Mechanic;
import com.fumbbl.ffb.mechanics.PassMechanic;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.modifiers.PassContext;
import com.fumbbl.ffb.modifiers.PassModifier;

/**
 * 
 * @author Kalimar
 */
public class UtilRangeRuler {

	public static RangeRuler createRangeRuler(Game pGame, Player<?> pThrower, FieldCoordinate pTargetCoordinate,
			boolean pThrowTeamMate) {
		RangeRuler rangeRuler = null;
		if ((pGame != null) && (pThrower != null) && (pTargetCoordinate != null)) {
			PassMechanic mechanic = (PassMechanic) pGame.getRules().getFactory(FactoryType.Factory.MECHANIC).forName(Mechanic.Type.PASS.name());
			FieldCoordinate throwerCoordinate = pGame.getFieldModel().getPlayerCoordinate(pThrower);
			PassingDistance passingDistance = mechanic.findPassingDistance(pGame, throwerCoordinate, pTargetCoordinate,
					pThrowTeamMate);
			if (passingDistance != null) {
				Optional<Integer> minimumRoll;
				PassModifierFactory factory = pGame.getFactory(FactoryType.Factory.PASS_MODIFIER);
				Set<PassModifier> passModifiers = factory.findModifiers(new PassContext(pGame, pThrower, passingDistance,
					pThrowTeamMate));
				if (pThrowTeamMate) {
					minimumRoll = Optional.of(minimumRollThrowTeamMate(passingDistance, passModifiers));
				} else {
					minimumRoll = mechanic.minimumRoll(pThrower, passingDistance, passModifiers);
				}
				rangeRuler = new RangeRuler(pThrower.getId(), pTargetCoordinate, minimumRoll.orElse(0), pThrowTeamMate);
			}
		}
		return rangeRuler;
	}

	public static int minimumRollThrowTeamMate(PassingDistance pPassingDistance,
	                                           Set<PassModifier> pPassModifiers) {
		int modifierTotal = 0;
		for (PassModifier passModifier : pPassModifiers) {
			modifierTotal += passModifier.getModifier();
		}
		return Math.max(2, 2 - pPassingDistance.getModifier2016() + modifierTotal);
	}

}