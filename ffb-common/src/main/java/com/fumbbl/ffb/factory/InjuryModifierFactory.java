package com.fumbbl.ffb.factory;

import com.fumbbl.ffb.FactoryType;
import com.fumbbl.ffb.InjuryAttribute;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.RulesCollection.Rules;
import com.fumbbl.ffb.SpecialEffect;
import com.fumbbl.ffb.injury.context.InjuryContext;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.modifiers.InjuryModifier;
import com.fumbbl.ffb.modifiers.InjuryModifierContext;
import com.fumbbl.ffb.modifiers.ModifierAggregator;
import com.fumbbl.ffb.modifiers.SpecialEffectInjuryModifier;
import com.fumbbl.ffb.option.GameOptionBoolean;
import com.fumbbl.ffb.option.GameOptionId;
import com.fumbbl.ffb.util.Scanner;
import com.fumbbl.ffb.util.UtilCards;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kalimar
 */
@FactoryType(FactoryType.Factory.INJURY_MODIFIER)
@RulesCollection(Rules.COMMON)
public class InjuryModifierFactory implements INamedObjectFactory<InjuryModifier> {

	private ModifierAggregator modifierAggregator;

	private InjuryModifiers injuryModifiers;

	public InjuryModifier forName(String name) {
		return Stream.concat(injuryModifiers.allValues(), modifierAggregator.getInjuryModifiers().stream())
			.filter(modifier -> modifier.getName().equals(name))
			.findFirst()
			.orElse(null);
	}

	public Set<InjuryModifier> findInjuryModifiersWithoutNiggling(Game game, InjuryContext injuryContext, Player<?> attacker,
	                                                              Player<?> defender, boolean isStab, boolean isFoul, boolean isVomitLike) {

		InjuryModifierContext context = new InjuryModifierContext(game, injuryContext, attacker, defender, isStab, isFoul, isVomitLike);

		return getInjuryModifiers(context);
	}

	public Set<InjuryModifier> findInjuryModifiers(Game game, InjuryContext injuryContext, Player<?> attacker,
	                                               Player<?> defender, boolean isStab, boolean isFoul, boolean isVomitLike) {
		Set<InjuryModifier> modifiers = findInjuryModifiersWithoutNiggling(game, injuryContext, attacker, defender, isStab, isFoul, isVomitLike);

		getNigglingInjuryModifier(defender).ifPresent(modifiers::add);

		return modifiers;
	}

	public Optional<? extends InjuryModifier> getNigglingInjuryModifier(Player<?> pPlayer) {
		if (pPlayer != null) {
			long nigglingInjuries = Arrays.stream(pPlayer.getLastingInjuries()).filter(seriousInjury -> seriousInjury.getInjuryAttribute() == InjuryAttribute.NI).count();

			return injuryModifiers.values().filter(modifier -> modifier.isNigglingInjuryModifier()
				&& (modifier.getModifier(null, null) == nigglingInjuries)).findFirst();
		}
		return Optional.empty();
	}

	public Set<SpecialEffectInjuryModifier> specialEffectInjuryModifiers(SpecialEffect specialEffect) {
		return injuryModifiers.values().filter(modifier -> modifier instanceof SpecialEffectInjuryModifier)
			.map(modifier -> (SpecialEffectInjuryModifier) modifier)
			.filter(modifier -> modifier.getEffect() == specialEffect)
			.collect(Collectors.toSet());
	}

	private Set<InjuryModifier> getInjuryModifiers(InjuryModifierContext context) {

		Set<InjuryModifier> modifiers = Arrays.stream(UtilCards.findAllSkills(context.getAttacker()))
			.flatMap(skill -> skill.getInjuryModifiers().stream())
			.filter(modifier -> modifier.appliesToContext(context))
			.collect(Collectors.toSet());

		context.setDefenderMode();

		Arrays.stream(UtilCards.findAllSkills(context.getDefender()))
			.flatMap(skill -> skill.getInjuryModifiers().stream())
			.filter(modifier -> modifier.appliesToContext(context))
			.forEach(modifiers::add);

		return modifiers;
	}

	@Override
	public void initialize(Game game) {
		this.modifierAggregator = game.getModifierAggregator();
		injuryModifiers = new Scanner<>(InjuryModifiers.class).getInstancesImplementing(game.getOptions()).stream().findFirst().orElse(null);
		if (injuryModifiers != null) {
			injuryModifiers.setUseAll(((GameOptionBoolean) game.getOptions().getOptionWithDefault(GameOptionId.BOMB_USES_MB)).isEnabled());
		}
	}

}
