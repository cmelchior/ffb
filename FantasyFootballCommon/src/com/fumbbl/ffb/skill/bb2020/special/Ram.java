package com.fumbbl.ffb.skill.bb2020.special;

import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.RulesCollection.Rules;
import com.fumbbl.ffb.SkillCategory;
import com.fumbbl.ffb.model.skill.Skill;
import com.fumbbl.ffb.model.skill.SkillUsageType;

/**
 * Once per game, when an opposition player is Knocked Down as the result of a Block action performed by Rumbelow,
 * you may apply an additional +1 modifier to either the Armour roll or Injury Roll. This modifier may be applied after the roll has been made
 */

@RulesCollection(Rules.BB2020)
public class Ram extends Skill {
	public Ram() {
		super("Ram", SkillCategory.TRAIT, SkillUsageType.ONCE_PER_GAME);
	}
}