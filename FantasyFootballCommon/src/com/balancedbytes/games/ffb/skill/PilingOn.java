package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.skill.Skill;
import com.balancedbytes.games.ffb.model.property.CancelSkillProperty;
import com.balancedbytes.games.ffb.model.property.NamedProperties;

/**
 * The player may use this skill after he has made a block as part of one of his
 * Block or Blitz Actions, but only if the Piling On player is currently
 * standing adjacent to the victim and the victim was Knocked Down. You may
 * re-roll the Armour roll or Injury roll for the victim. The Piling On player
 * is Placed Prone in his own square - it is assumed that he rolls back there
 * after flattening his opponent (do not make an Armour roll for him as he has
 * been cushioned by the other player!). Piling On does not cause a turnover
 * unless the Piling On player is carrying the ball. Piling On cannot be used
 * with the Stab or Chainsaw skills.
 */
// This should be moved to 2016 rules but at the moment PilingOnBehavior is used to handle block knockdowns
// (e.g. for BothDown results). So this needs to be untangled first
@RulesCollection(Rules.COMMON)
public class PilingOn extends Skill {

	public PilingOn() {
		super("Piling On", SkillCategory.STRENGTH);
	}

	@Override
	public void postConstruct() {
		registerProperty(new CancelSkillProperty(NamedProperties.canPileOnOpponent));
	}
}
