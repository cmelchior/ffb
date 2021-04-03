package com.balancedbytes.games.ffb.modifiers.bb2020;

import com.balancedbytes.games.ffb.FactoryType;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.factory.INamedObjectFactory;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.modifiers.ModifierAggregator;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@FactoryType(FactoryType.Factory.CASUALTY_MODIFIER)
@RulesCollection(RulesCollection.Rules.BB2020)
public class CasualtyModifierFactory implements INamedObjectFactory<CasualtyModifier> {

	private ModifierAggregator modifierAggregator;

	private final Set<CasualtyNigglingModifier> nigglingModifiers = new HashSet<CasualtyNigglingModifier>() {{
		add(new CasualtyNigglingModifier("1 Niggling Injury", 1));
		add(new CasualtyNigglingModifier("2 Niggling Injuries", 2));
		add(new CasualtyNigglingModifier("3 Niggling Injuries", 3));
		add(new CasualtyNigglingModifier("4 Niggling Injuries", 4));
		add(new CasualtyNigglingModifier("5 Niggling Injuries", 5));
	}};

	public Set<CasualtyModifier> findModifiers(Player<?> player) {
		return Stream.concat(
			nigglingModifiers.stream(),
			player.getSkillsIncludingTemporaryOnes().stream().flatMap(s -> s.getCasualtyModifiers().stream())
		).filter(modifier -> modifier.appliesToContext(player)).collect(Collectors.toSet());
	}

	@Override
	public CasualtyModifier forName(String name) {
		return Stream.concat(
			nigglingModifiers.stream(),
			modifierAggregator.getCasualtyModifiers().stream())
			.filter(modifier -> modifier.getName().equals(name))
			.findFirst()
			.orElse(null);
	}

	@Override
	public void initialize(Game game) {
		this.modifierAggregator = game.getModifierAggregator();
	}
}
