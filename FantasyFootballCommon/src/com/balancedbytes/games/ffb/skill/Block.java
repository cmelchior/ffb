package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;

/**
 * A player with the Block skill is proficient at knocking opponents down. The
 * Block skill, if used, affects the results rolled with the Block dice, as
 * explained in the Blocking rules.
 */
public class Block extends Skill {

	public Block() {
		super("Block", SkillCategory.GENERAL);

		registerProperty(NamedProperties.preventFallOnBothDown);
	}

}
