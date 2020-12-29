package com.balancedbytes.games.ffb.factory;

import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.FactoryType;
import com.balancedbytes.games.ffb.RulesCollection.Rules;
import com.balancedbytes.games.ffb.model.GameOptions;

/**
 * 
 * @author Kalimar
 */
@FactoryType(FactoryType.Factory.cardEffect)
public class CardEffectFactory implements INamedObjectFactory {

	public CardEffect forName(String pName) {
		for (CardEffect effect : CardEffect.values()) {
			if (effect.getName().equalsIgnoreCase(pName)) {
				return effect;
			}
		}
		return null;
	}

	@Override
	public void initialize(Rules rules, GameOptions options) {
		// TODO Auto-generated method stub
		
	}

}
