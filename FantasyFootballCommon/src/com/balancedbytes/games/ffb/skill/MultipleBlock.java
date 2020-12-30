package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;

/**
 * At the start of a Block Action a player who is adjacent to at least two
 * opponents may choose to throw blocks against two of them. Make each block in
 * turn as normal except that each defender's strength is increased by 2. The
 * player cannot follow up either block when using this skill, so Multiple Block
 * can be used instead of Frenzy, but both skills cannot be used together. To
 * have the option to throw the second block the player must still be on his
 * feet after the first block.
 */
@RulesCollection(Rules.COMMON)
public class MultipleBlock extends Skill {

	public MultipleBlock() {
		super("Multiple Block", SkillCategory.STRENGTH);
	}

	@Override
	public void postConstruct() {
		registerProperty(NamedProperties.canBlockMoreThanOnce);

	}

}
