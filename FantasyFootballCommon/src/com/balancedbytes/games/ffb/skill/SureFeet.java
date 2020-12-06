package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.ReRollSources;
import com.balancedbytes.games.ffb.ReRolledActions;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;

/**
 * The player may re-roll the D6 if he is Knocked Down when trying to Go For It
 * (see page 20). A player may only use the Sure Feet skill once per turn.
 */
public class SureFeet extends Skill {

	public SureFeet() {
		super("Sure Feet", SkillCategory.AGILITY);

		registerRerollSource(ReRolledActions.GO_FOR_IT, ReRollSources.SURE_FEET);
	}

}
