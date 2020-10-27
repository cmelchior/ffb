package com.balancedbytes.games.ffb.model.skill;

import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;

/**
* A player with one or more extra arms may add 1 to any attempt to pick
* up, catch or intercept.
*/
public class ExtraArms extends Skill {

  public ExtraArms() {
    super("Extra Arms", SkillCategory.MUTATION);
  }

}
