package com.fumbbl.ffb.server.injury.injuryType;

import com.fumbbl.ffb.ApothecaryMode;
import com.fumbbl.ffb.FactoryType;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.PlayerState;
import com.fumbbl.ffb.factory.InjuryModifierFactory;
import com.fumbbl.ffb.injury.ThrowARock;
import com.fumbbl.ffb.injury.context.InjuryContext;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.server.DiceRoller;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.step.IStep;

public class InjuryTypeThrowARock extends InjuryTypeServer<ThrowARock> {
	public InjuryTypeThrowARock() {
		super(new ThrowARock());
	}

	@Override
	public void handleInjury(IStep step, Game game, GameState gameState, DiceRoller diceRoller,
	                         Player<?> pAttacker, Player<?> pDefender, FieldCoordinate pDefenderCoordinate, FieldCoordinate fromCoordinate, InjuryContext pOldInjuryContext,
	                         ApothecaryMode pApothecaryMode) {

		if (!injuryContext.isArmorBroken()) {
			injuryContext.setArmorBroken(true);
		}

		if (injuryContext.isArmorBroken()) {
			injuryContext.setInjuryRoll(diceRoller.rollInjury());
			InjuryModifierFactory factory = game.getFactory(FactoryType.Factory.INJURY_MODIFIER);
			factory.findInjuryModifiers(game, injuryContext, pAttacker,
				pDefender, isStab(), isFoul(), isVomitLike()).forEach(injuryModifier -> injuryContext.addInjuryModifier(injuryModifier));

			setInjury(pDefender, gameState, diceRoller);

		} else {
			injuryContext.setInjury(new PlayerState(PlayerState.PRONE));
		}

	}
}