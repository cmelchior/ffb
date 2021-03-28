package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.modifiers.ModifierType;
import com.balancedbytes.games.ffb.modifiers.PassModifier;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.skill.Skill;
import com.balancedbytes.games.ffb.model.property.NamedProperties;
import com.balancedbytes.games.ffb.modifiers.CatchModifier;
import com.balancedbytes.games.ffb.modifiers.InterceptionModifier;

/**
 * The player ignores modifiers for enemy tackle zones when he attempts to pass,
 * catch or intercept.
 */
@RulesCollection(Rules.COMMON)
public class NervesOfSteel extends Skill {

	public NervesOfSteel() {
		super("Nerves of Steel", SkillCategory.PASSING);
	}

	@Override
	public void postConstruct() {
		registerModifier(new PassModifier("Nerves of Steel", "0 tackle zones due to Nerves of Steel",0, ModifierType.REGULAR) {
			@Override
			public boolean isModifierIncluded() {
				return true;
			}
		});
		registerModifier(new InterceptionModifier("Nerves of Steel", "0 tackle zones due to Nerves of Steel", 0, ModifierType.REGULAR) {
			@Override
			public boolean isModifierIncluded() {
				return true;
			}
		});
		registerModifier(new CatchModifier("Nerves of Steel","0 for tackle zones due to Nerves of Steel",0, ModifierType.REGULAR) {
			@Override
			public boolean isModifierIncluded() {
				return true;
			}
		});

		registerProperty(NamedProperties.ignoreTacklezonesWhenPassing);
		registerProperty(NamedProperties.ignoreTacklezonesWhenCatching);
	}

}
