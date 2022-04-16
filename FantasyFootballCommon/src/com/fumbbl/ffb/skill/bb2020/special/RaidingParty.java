package com.fumbbl.ffb.skill.bb2020.special;

import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.SkillCategory;
import com.fumbbl.ffb.model.property.NamedProperties;
import com.fumbbl.ffb.model.skill.Skill;
import com.fumbbl.ffb.model.skill.SkillUsageType;

@RulesCollection(RulesCollection.Rules.BB2020)
public class RaidingParty extends Skill {
	public RaidingParty() {
		super("Raiding Party", SkillCategory.TRAIT, SkillUsageType.ONCE_PER_DRIVE);
	}

	@Override
	public void postConstruct() {
		registerProperty(NamedProperties.canMoveOpenTeamMate);
	}
}
