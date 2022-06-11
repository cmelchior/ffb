package com.fumbbl.ffb.inducement.bb2020;

import com.fumbbl.ffb.IIconProperty;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.inducement.InducementType;
import com.fumbbl.ffb.inducement.Usage;
import com.fumbbl.ffb.model.GameOptions;
import com.fumbbl.ffb.model.SpecialRule;
import com.fumbbl.ffb.model.Team;
import com.fumbbl.ffb.option.GameOptionId;
import com.fumbbl.ffb.option.GameOptionInt;

import java.util.HashSet;
import java.util.Set;

@RulesCollection(RulesCollection.Rules.BB2020)
public class InducementCollection extends com.fumbbl.ffb.inducement.InducementCollection {
	private final Set<InducementType> types = new HashSet<InducementType>() {{
		add(new InducementType("bribes", "Bribes", "Bribe", "Bribes", GameOptionId.INDUCEMENT_BRIBES_MAX, GameOptionId.INDUCEMENT_BRIBES_COST,
			GameOptionId.INDUCEMENT_BRIBES_REDUCED_COST, true, IIconProperty.RESOURCE_BRIBE, Usage.AVOID_BAN) {

			@Override
			protected boolean useReducedCostId(Team team) {
				return team.getSpecialRules().contains(SpecialRule.BRIBERY_AND_CORRUPTION);
			}
		});
		add(new InducementType("prayers", "Prayers", "Prayer", "Prayers", GameOptionId.INDUCEMENT_PRAYERS_MAX, GameOptionId.INDUCEMENT_PRAYERS_COST,
			GameOptionId.INDUCEMENT_PRAYERS_COST, false, IIconProperty.RESOURCE_PRAYER, Usage.GAME_MODIFICATION));

		add(new InducementType("briberyAndCorruption", "Bribery and Corruption ReRoll", "Bribery and Corruption ReRoll", "Bribery and Corruption ReRolls",
			GameOptionId.INDUCEMENT_PRAYERS_MAX, GameOptionId.INDUCEMENT_PRAYERS_COST,
			GameOptionId.INDUCEMENT_PRAYERS_COST, true, IIconProperty.RESOURCE_RE_ROLL_ARGUE, Usage.REROLL_ARGUE));

		add(new InducementType("halflingMasterChef", "Halfling Master Chef", "Halfling Master Chef", "Halfling Master Chefs",
			GameOptionId.INDUCEMENT_CHEFS_MAX, GameOptionId.INDUCEMENT_CHEFS_COST,
			GameOptionId.INDUCEMENT_CHEFS_REDUCED_COST, true, IIconProperty.RESOURCE_MASTER_CHEF, Usage.STEAL_REROLL) {
			@Override
			protected boolean useReducedCostId(Team team) {
				return team.getSpecialRules().contains(SpecialRule.HALFLING_THIMBLE_CUP);
			}
		});

		add(new InducementType("mortuaryAssistant", "Mortuary Assistant", "Mortuary Assistant", "Mortuary Assistants", GameOptionId.INDUCEMENT_IGORS_MAX,
			GameOptionId.INDUCEMENT_IGORS_COST, IIconProperty.RESOURCE_IGOR, Usage.REGENERATION) {
			@Override
			public int availability(Team team, GameOptions options) {
				if (!team.getSpecialRules().contains(SpecialRule.SYLVANIAN_SPOTLIGHT)) {
					return 0;
				}
				return super.availability(team, options);
			}
		});

		add(new InducementType("riotousRookies", "Riotous Rookies", "Riotous Rookies", "Riotous Rookies",
			GameOptionId.INDUCEMENT_RIOTOUS_ROOKIES_MAX, GameOptionId.INDUCEMENT_RIOTOUS_ROOKIES_COST, Usage.ADD_LINEMEN) {
			@Override
			public int availability(Team team, GameOptions options) {
				if (!team.getSpecialRules().contains(SpecialRule.LOW_COST_LINEMEN)) {
					return 0;
				}
				return super.availability(team, options);
			}
		});
		add(new InducementType("tempCheerleader", "Temp Agency Cheerleaders", "Temp Agency Cheerleader", "Temp Agency Cheerleaders",
			GameOptionId.INDUCEMENT_TEMP_CHEERLEADER_MAX, GameOptionId.INDUCEMENT_TEMP_CHEERLEADER_COST, Usage.ADD_CHEERLEADER) {
			@Override
			public int availability(Team team, GameOptions options) {
				int availability = super.availability(team, options);

				if (availability > 0) {
					int max = ((GameOptionInt) options.getOptionWithDefault(GameOptionId.INDUCEMENT_TEMP_CHEERLEADER_TOTAL_MAX)).getValue() - team.getCheerleaders();

					availability = Math.min(Math.max(max, 0), availability);
				}

				return availability;
			}
		});
	}};

	protected Set<InducementType> getSubTypes() {
		return types;
	}
}
