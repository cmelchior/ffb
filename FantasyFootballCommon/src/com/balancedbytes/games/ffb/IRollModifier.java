package com.balancedbytes.games.ffb;

import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.modifiers.InterceptionContext;
import com.balancedbytes.games.ffb.modifiers.ModifierContext;
import com.balancedbytes.games.ffb.modifiers.ModifierKey;

/**
 * 
 * @author Kalimar
 */
public interface IRollModifier<T extends ModifierKey, C extends ModifierContext> extends INamedObject {

	int getModifier();

	boolean isModifierIncluded();

	String getReportString();

	T getModifierKey();

	default boolean isDisturbingPresenceModifier() {
		return false;
	}

	default int getMultiplier() {
		return getModifier();
	}

	default boolean isTacklezoneModifier() {
		return false;
	}

	default boolean appliesToContext(Skill skill, C context) {
		return true;
	}

}
