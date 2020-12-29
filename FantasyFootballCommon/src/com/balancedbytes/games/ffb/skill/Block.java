package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;

/**
 * A player with the Block skill is proficient at knocking opponents down. The
 * Block skill, if used, affects the results rolled with the Block dice, as
 * explained in the Blocking rules.
 */
@RulesCollection(Rules.All)
public class Block extends Skill {

	public Block() {
		super("Block", SkillCategory.GENERAL);
	}

	@Override
	public void postConstruct() {
		registerProperty(NamedProperties.preventFallOnBothDown);
	}

}
