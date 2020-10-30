package com.balancedbytes.games.ffb.skill;

import com.balancedbytes.games.ffb.SkillCategory;
import com.balancedbytes.games.ffb.model.Skill;

public class StrengthDecrease extends Skill {

  public StrengthDecrease() {
    super("-ST", SkillCategory.STAT_DECREASE);
  }

}