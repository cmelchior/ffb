package com.fumbbl.ffb.server.inducements.bb2020.prayers;

import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.inducement.bb2020.Prayer;
import com.fumbbl.ffb.server.GameState;

@RulesCollection(RulesCollection.Rules.BB2020)
public class BadHabitsHandler extends RandomSelectionPrayerHandler {
	@Override
	Prayer handledPrayer() {
		return Prayer.BAD_HABITS;
	}

	@Override
	protected int affectedPlayers(GameState gameState) {
		return gameState.getDiceRoller().rollDice(3);
	}

	@Override
	protected PlayerSelector selector() {
		return OpponentPlayerSelector.INSTANCE;
	}
}
