package com.fumbbl.ffb.server.injury.injuryType;

import com.fumbbl.ffb.ApothecaryMode;
import com.fumbbl.ffb.FactoryType;
import com.fumbbl.ffb.FieldCoordinate;
import com.fumbbl.ffb.factory.InjuryModifierFactory;
import com.fumbbl.ffb.factory.SkillFactory;
import com.fumbbl.ffb.injury.Chainsaw;
import com.fumbbl.ffb.injury.context.IInjuryContextModification;
import com.fumbbl.ffb.injury.context.InjuryContext;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.model.property.NamedProperties;
import com.fumbbl.ffb.server.DiceInterpreter;
import com.fumbbl.ffb.server.DiceRoller;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.injury.modification.InjuryContextModification;
import com.fumbbl.ffb.server.step.IStep;

import java.util.Optional;

public class InjuryTypeChainsaw extends InjuryTypeServer<Chainsaw> {
	public InjuryTypeChainsaw() {
		super(new Chainsaw());
		super.setFailedArmourPlacesProne(false);
	}

	@Override
	public InjuryContext handleInjury(IStep step, Game game, GameState gameState, DiceRoller diceRoller,
	                                  Player<?> pAttacker, Player<?> pDefender, FieldCoordinate pDefenderCoordinate, FieldCoordinate fromCoordinate, InjuryContext pOldInjuryContext,
	                                  ApothecaryMode pApothecaryMode) {
		Optional<IInjuryContextModification> modification = pAttacker.getUnusedInjuryModification(injuryType);

		DiceInterpreter diceInterpreter = DiceInterpreter.getInstance();

		if (!injuryContext.isArmorBroken()) {
			injuryContext.setArmorRoll(diceRoller.rollArmour());
			SkillFactory factory = game.getFactory(FactoryType.Factory.SKILL);
			factory.getSkills().stream()
				.filter(skill -> skill.hasSkillProperty(NamedProperties.blocksLikeChainsaw))
				.flatMap(skill -> skill.getArmorModifiers().stream())
				.forEach(injuryContext::addArmorModifier);
			injuryContext.setArmorBroken(diceInterpreter.isArmourBroken(gameState, injuryContext));
		}

		modification.ifPresent(injuryContextModification -> ((InjuryContextModification) injuryContextModification).modifyArmour(injuryContext, gameState));

		if (injuryContext.isArmorBroken()) {
			injuryContext.setInjuryRoll(diceRoller.rollInjury());
			InjuryModifierFactory factory = game.getFactory(FactoryType.Factory.INJURY_MODIFIER);
			factory.findInjuryModifiers(game, injuryContext, pAttacker,
				pDefender, isStab(), isFoul(), isVomit()).forEach(injuryModifier -> injuryContext.addInjuryModifier(injuryModifier));

			modification.ifPresent(injuryContextModification -> ((InjuryContextModification) injuryContextModification).modifyInjury(injuryContext, gameState));

			setInjury(pDefender, gameState, diceRoller);
		} else {
			injuryContext.setInjury(null);
		}
		return injuryContext;
	}
}