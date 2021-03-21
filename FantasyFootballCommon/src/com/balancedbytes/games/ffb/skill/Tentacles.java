package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.model.property.NamedProperties;

/**
 * The player may attempt to use this skill when an opposing player attempts to
 * dodge or jump out of any of his tackle zones. The opposing player rolls 2D6
 * adding their own player's ST and subtracting the Tentacles player's ST from
 * the score. If the final result is 5 or less, then the moving player is held
 * firm, and his action ends immediately. If a player attempts to leave the
 * tackle zone of several players that have the Tentacles ability, then only one
 * of the opposing players may at tempt to grab him with the tentacles.
 */
@RulesCollection(Rules.COMMON)
public class Tentacles extends Skill {

	public Tentacles() {
		super("Tentacles", SkillCategory.MUTATION);
	}

	@Override
	public void postConstruct() {
		registerProperty(NamedProperties.canHoldPlayersLeavingTacklezones);
	}
}
