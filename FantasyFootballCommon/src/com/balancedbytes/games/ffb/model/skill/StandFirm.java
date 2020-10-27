package com.balancedbytes.games.ffb.model.skill;

import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;

/**
* A player with this skill may choose to not be pushed back as the result of
* a block. He may choose to ignore being pushed by "Pushed" results, and
* to have 'Knock-down' results knock the player down in the square where
* he started. If a player is pushed back into a player with using Stand Firm
* then neither player moves.
*/
public class StandFirm extends Skill {

  public StandFirm() {
    super("Stand Firm", SkillCategory.STRENGTH);
  }

}
