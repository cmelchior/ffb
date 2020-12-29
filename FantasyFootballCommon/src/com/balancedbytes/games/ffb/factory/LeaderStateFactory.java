package com.balancedbytes.games.ffb.factory;

import com.balancedbytes.games.ffb.FactoryType;
import com.balancedbytes.games.ffb.LeaderState;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.model.GameOptions;

/**
 * 
 * @author Kalimar
 */
@FactoryType(FactoryType.Factory.leaderState)
public class LeaderStateFactory implements INamedObjectFactory {

	public LeaderState forName(String pName) {
		for (LeaderState state : LeaderState.values()) {
			if (state.getName().equalsIgnoreCase(pName)) {
				return state;
			}
		}
		return null;
	}

	@Override
	public void initialize(Rules rules, GameOptions options) {
		// TODO Auto-generated method stub
		
	}

}
