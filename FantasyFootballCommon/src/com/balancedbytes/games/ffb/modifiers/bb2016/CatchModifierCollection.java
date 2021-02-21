package com.balancedbytes.games.ffb.modifiers.bb2016;

import com.balancedbytes.games.ffb.CatchScatterThrowInMode;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;
import com.balancedbytes.games.ffb.modifiers.CatchContext;
import com.balancedbytes.games.ffb.modifiers.CatchModifier;

import java.util.HashSet;
import java.util.Set;

@RulesCollection(RulesCollection.Rules.BB2016)
public class CatchModifierCollection extends com.balancedbytes.games.ffb.modifiers.CatchModifierCollection {

	public CatchModifierCollection() {
		super();

		add(new CatchModifier("Accurate Pass", -1, false, false) {
			private final Set<CatchScatterThrowInMode> accurate = new HashSet<CatchScatterThrowInMode>() {
				private static final long serialVersionUID = -9147805080834490230L;
				{
					add(CatchScatterThrowInMode.CATCH_ACCURATE_BOMB);
					add(CatchScatterThrowInMode.CATCH_ACCURATE_PASS);
				}
			};

			private final Set<CatchScatterThrowInMode> accurateAdjacent = new HashSet<CatchScatterThrowInMode>() {
				private static final long serialVersionUID = 3746699772690521692L;
				{
					add(CatchScatterThrowInMode.CATCH_ACCURATE_BOMB_EMPTY_SQUARE);
					add(CatchScatterThrowInMode.CATCH_ACCURATE_PASS_EMPTY_SQUARE);
				}
			};

			@Override
			public boolean appliesToContext(CatchContext context) {
				return
					super.appliesToContext(context) &&
						(accurate.contains(context.getCatchMode()) ||
							(context.getPlayer() != null && context.getPlayer().hasSkillWithProperty(NamedProperties.addBonusForAccuratePass) &&
								accurateAdjacent.contains(context.getCatchMode())));
			}
		});
		add(new CatchModifier("Hand Off", -1, false, false) {
			@Override
			public boolean appliesToContext(CatchContext context) {
				return super.appliesToContext(context) && CatchScatterThrowInMode.CATCH_HAND_OFF == context.getCatchMode();
			}
		});

	}
}