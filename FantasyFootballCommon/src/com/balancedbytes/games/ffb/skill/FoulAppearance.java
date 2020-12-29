package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.model.Skill;

/**
 * The player's appearance is so horrible that any opposing player that wants to
 * block the player (or use a special attack that takes the place of a block)
 * must first roll a D6 and score 2 or more. If the opposing player rolls a 1 he
 * is too revolted to make the block and it is wasted (though the opposing team
 * does not suffer a turnover).
 */
@RulesCollection(Rules.All)
public class FoulAppearance extends Skill {

	public FoulAppearance() {
		super("Foul Appearance", SkillCategory.MUTATION);
	}

}
