package com.balancedbytes.games.ffb.modifiers.bb2020;

import com.balancedbytes.games.ffb.CatchScatterThrowInMode;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.modifiers.CatchContext;
import com.balancedbytes.games.ffb.modifiers.CatchModifier;

import java.util.HashSet;
import java.util.Set;

@RulesCollection(RulesCollection.Rules.BB2020)
public class CatchModifierCollection extends com.balancedbytes.games.ffb.modifiers.CatchModifierCollection {

	public CatchModifierCollection() {
		super();

		add(new CatchModifier("Inaccurate Pass", 1, false, false) {
			private final Set<CatchScatterThrowInMode> scatter = new HashSet<CatchScatterThrowInMode>() {
				private static final long serialVersionUID = 6752365907656902172L;
				{
					add(CatchScatterThrowInMode.CATCH_BOMB);
					add(CatchScatterThrowInMode.CATCH_SCATTER);
				}
			};
			@Override
			public boolean appliesToContext(CatchContext context) {
				return super.appliesToContext(context) && scatter.contains(context.getCatchMode());
			}
		});

		add(new CatchModifier("Deflected Pass", 1, false, false) {
			private final Set<CatchScatterThrowInMode> deflected = new HashSet<CatchScatterThrowInMode>() {
				private static final long serialVersionUID = 5356216477217665429L;
				{
					add(CatchScatterThrowInMode.DEFLECTED);
					add(CatchScatterThrowInMode.DEFLECTED_BOMB);
				}
			};
			@Override
			public boolean appliesToContext(CatchContext context) {
				return super.appliesToContext(context) && deflected.contains(context.getCatchMode());
			}
		});
	}
}