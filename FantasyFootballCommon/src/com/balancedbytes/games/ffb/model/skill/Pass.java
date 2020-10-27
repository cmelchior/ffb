package com.balancedbytes.games.ffb.model.skill;

import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;

/**
* A player with the Pass skill is allowed to re-roll the D6 if he throws an
* inaccurate pass or fumbles.
*/
public class Pass extends Skill {

  public Pass() {
    super("Pass", SkillCategory.PASSING);
  }

}
