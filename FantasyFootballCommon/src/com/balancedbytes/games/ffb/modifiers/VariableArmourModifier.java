package com.balancedbytes.games.ffb.modifiers;

import com.balancedbytes.games.ffb.model.Player;

public class VariableArmourModifier extends RegistrationAwareModifier implements ArmorModifier {

	private final String fName;
	private final boolean fFoulAssistModifier;

	public VariableArmourModifier(String pName, boolean pFoulAssistModifier) {
		fName = pName;
		fFoulAssistModifier = pFoulAssistModifier;
	}

	public int getModifier(Player<?> player) {
		return player.getSkillIntValue(registeredTo);
	}

	public String getName() {
		return fName;
	}

	public boolean isFoulAssistModifier() {
		return fFoulAssistModifier;
	}

	public boolean appliesToContext(ArmorModifierContext context) {
		return true;
	}
}