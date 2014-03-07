package com.balancedbytes.games.ffb;

/**
 * 
 * @author Kalimar
 */
public class CardEffectFactory implements IEnumWithNameFactory {

  public CardEffect forName(String pName) {
    for (CardEffect effect : CardEffect.values()) {
      if (effect.getName().equalsIgnoreCase(pName)) {
        return effect;
      }
    }
    return null;
  }

}
