package com.balancedbytes.games.ffb.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardEffect;
import com.balancedbytes.games.ffb.DodgeModifier;
import com.balancedbytes.games.ffb.DodgeModifiers.DodgeContext;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassingModifiers.PassContext;
import com.balancedbytes.games.ffb.PickupModifier;
import com.balancedbytes.games.ffb.PickupModifiers.PickupContext;
import com.balancedbytes.games.ffb.ReRollSource;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.ISkillProperty;
import com.balancedbytes.games.ffb.model.InducementSet;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.Skill;
import com.balancedbytes.games.ffb.model.SkillConstants;

/**
 * 
 * @author Kalimar
 */
public final class UtilCards {

  public static boolean hasSkill(Game pGame, Player pPlayer, Skill pSkill) {
    if ((pGame == null) || (pPlayer == null) || (pSkill == null)) {
      return false;
    }
    Set<Skill> cardSkills = findSkillsProvidedByCardsAndEffects(pGame, pPlayer);
    return (pPlayer.hasSkill(pSkill) || cardSkills.contains(pSkill));
  }

  public static boolean hasSkill(Game pGame, ActingPlayer pActingPlayer, Skill pSkill) {
    if (pActingPlayer == null) {
      return false;
    }
    return hasSkill(pGame, pActingPlayer.getPlayer(), pSkill);
  }

  public static boolean hasUnusedSkill(Game pGame, ActingPlayer pActingPlayer, Skill pSkill) {
    if (pActingPlayer == null) {
      return false;
    }
    return (hasSkill(pGame, pActingPlayer.getPlayer(), pSkill) && !pActingPlayer.isSkillUsed(pSkill));
  }

  public static Collection<PassModifier> getPassModifiers(Player thrower, PassContext context) {
    Set<PassModifier> result = new HashSet<PassModifier>();
    
    for (Skill skill : thrower.getSkills()) {
      for (PassModifier modifier : skill.getPassModifiers()) {
        if (modifier.appliesToContext(context)) {
          result.add(modifier);
        }
      }
    }
    
    return result;
  }

  public static Collection<PickupModifier> getPickupModifiers(Player player, PickupContext context) {
    Set<PickupModifier> result = new HashSet<PickupModifier>();
    
    for (Skill skill : player.getSkills()) {
      for (PickupModifier modifier : skill.getPickupModifiers()) {
        if (modifier.appliesToContext(context)) {
          result.add(modifier);
        }
      }
    }
    
    return result;
  }
  
  public static Collection<DodgeModifier> getDodgeModifiers(ActingPlayer player, DodgeContext context) {
    Set<DodgeModifier> result = new HashSet<DodgeModifier>();
    
    for (Skill skill : player.getPlayer().getSkills()) {
      for (DodgeModifier modifier : skill.getDodgeModifiers()) {
        if (modifier.appliesToContext(skill, context)) {
          result.add(modifier);
        }
      }
    }
    
    return result;
  }
  
  private static Set<Skill> findSkillsProvidedByCardsAndEffects(Game pGame, Player pPlayer) {
    Set<Skill> cardSkills = new HashSet<Skill>();
    if ((pGame == null) || (pPlayer == null)) {
      return cardSkills;
    }
    Card[] cards = pGame.getFieldModel().getCards(pPlayer);
    for (Card card : cards) {
      switch (card) {
        case BEGUILING_BRACERS:
          cardSkills.add(SkillConstants.BONE_HEAD);
          cardSkills.add(SkillConstants.HYPNOTIC_GAZE);
          cardSkills.add(SkillConstants.SIDE_STEP);
          break;
        case FAWNDOUGHS_HEADBAND:
          cardSkills.add(SkillConstants.ACCURATE);
          cardSkills.add(SkillConstants.PASS);
          break;
        case FORCE_SHIELD:
          cardSkills.add(SkillConstants.FEND);
          cardSkills.add(SkillConstants.SURE_HANDS);
          break;
        case GLOVES_OF_HOLDING:
          cardSkills.add(SkillConstants.CATCH);
          cardSkills.add(SkillConstants.SURE_HANDS);
          break;
        case MAGIC_GLOVES_OF_JARK_LONGARM:
          cardSkills.add(SkillConstants.PASS_BLOCK);
          break;
        case RABBITS_FOOT:
          cardSkills.add(SkillConstants.PRO);
          break;
        case WAND_OF_SMASHING:
          cardSkills.add(SkillConstants.MIGHTY_BLOW);
          break;
        case GROMSKULLS_EXPLODING_RUNES:
          cardSkills.add(SkillConstants.BOMBARDIER);
          cardSkills.add(SkillConstants.NO_HANDS);
          cardSkills.add(SkillConstants.SECRET_WEAPON);
          break;
        case DISTRACT:
          cardSkills.add(SkillConstants.DISTURBING_PRESENCE);
          break;
        case STOLEN_PLAYBOOK:
          cardSkills.add(SkillConstants.PASS_BLOCK);
          cardSkills.add(SkillConstants.SHADOWING);
          break;
        case KICKING_BOOTS:
          cardSkills.add(SkillConstants.KICK);
          cardSkills.add(SkillConstants.DIRTY_PLAYER);
        default:
          break;
      }
    }
    CardEffect[] cardEffects = pGame.getFieldModel().getCardEffects(pPlayer);
    for (CardEffect cardEffect : cardEffects) {
      switch (cardEffect) {
        case DISTRACTED:
          cardSkills.add(SkillConstants.BONE_HEAD);
          break;
        case SEDATIVE:
          cardSkills.add(SkillConstants.REALLY_STUPID);
          break;
        case MAD_CAP_MUSHROOM_POTION:
          cardSkills.add(SkillConstants.JUMP_UP);
          cardSkills.add(SkillConstants.NO_HANDS);
          break;
        default:
          break;
      }
    }
    return cardSkills;
  }

  public static int getPlayerStrength(Game pGame, Player pPlayer) {
    if ((pGame == null) || (pPlayer == null)) {
      return 0;
    }
    int strength = pPlayer.getStrength();
    InducementSet inducementSet = (pPlayer.getTeam() == pGame.getTeamHome()) ? pGame.getTurnDataHome().getInducementSet() : pGame.getTurnDataAway()
        .getInducementSet();
    for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
      switch (card) {
        case GIKTAS_STRENGTH_OF_DA_BEAR:
          if (inducementSet.isActive(card)) {
            strength += 1;
          } else {
            strength -= 1;
          }
          break;
        case WAND_OF_SMASHING:
          strength += 1;
          break;
        default:
          break;
      }
    }
    return strength;
  }

  public static int getPlayerMovement(Game pGame, Player pPlayer) {
    if ((pGame == null) || (pPlayer == null)) {
      return 0;
    }
    int movement = pPlayer.getMovement();
    InducementSet inducementSet = (pPlayer.getTeam() == pGame.getTeamHome()) ?
      pGame.getTurnDataHome().getInducementSet() : pGame.getTurnDataAway().getInducementSet();
    for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
      switch (card) {
        case KICKING_BOOTS:
          if (inducementSet.isActive(card)) {
            movement -= 1;
          }
          break;
        default:
          break;
      }
    }
    return movement;
  }

  public static Skill[] findAllSkills(Game pGame, Player pPlayer) {
    Set<Skill> allSkills = findSkillsProvidedByCardsAndEffects(pGame, pPlayer);
    for (Skill skill : pPlayer.getSkills()) {
      allSkills.add(skill);
    }
    return allSkills.toArray(new Skill[allSkills.size()]);
  }

  public static Card[] findAllActiveCards(Game pGame) {
    List<Card> allActiveCards = new ArrayList<Card>();
    for (Card card : pGame.getTurnDataHome().getInducementSet().getActiveCards()) {
      allActiveCards.add(card);
    }
    for (Card card : pGame.getTurnDataAway().getInducementSet().getActiveCards()) {
      allActiveCards.add(card);
    }
    return allActiveCards.toArray(new Card[allActiveCards.size()]);
  }

  public static boolean isCardActive(Game pGame, Card pCard) {
    for (Card card : findAllActiveCards(pGame)) {
      if (card == pCard) {
        return true;
      }
    }
    return false;
  }

  public static boolean hasCard(Game pGame, Player pPlayer, Card pCard) {
    if ((pGame == null) || (pPlayer == null) || (pCard == null)) {
      return false;
    }
    for (Card card : pGame.getFieldModel().getCards(pPlayer)) {
      if (card == pCard) {
        return true;
      }
    }
    return false;
  }
  
  public static Skill getSkillWithProperty(Player player, ISkillProperty property) {
    for (Skill playerSkill : player.getSkills()) {
      if (playerSkill.hasSkillProperty(property)) {
        return playerSkill;
      }
    }
    return null;
  }
  
  public static boolean hasSkillWithProperty(Player player, ISkillProperty property) {
    return getSkillWithProperty(player, property) != null;
  }
  
  public static Skill getSkillCancelling(Player player, Skill skill) {
    for (Skill playerSkill : player.getSkills()) {
      if (playerSkill.canCancel(skill)) {
        return skill;
      }
    }
    return null;
  }

  public static boolean cancelsSkill(Player player, Skill skill) {
    return getSkillCancelling(player, skill) != null;
  }

  public static boolean hasUnusedSkillWithProperty(ActingPlayer actingPlayer, ISkillProperty property) {
    for (Skill playerSkill : actingPlayer.getPlayer().getSkills()) {
      if (playerSkill.hasSkillProperty(property) && !actingPlayer.isSkillUsed(playerSkill)) {
        return true;
      }
    }
    return false;
  }

  public static ReRollSource getRerollSource(Player player, ReRolledAction action) {
    for (Skill playerSkill : player.getSkills()) {
      ReRollSource source = playerSkill.getRerollSource(action);
      if (source != null) {
        return source;
      }
    }    
    return null;
  }

  public static ReRollSource getUnusedRerollSource(ActingPlayer actingPlayer, ReRolledAction action) {
    for (Skill playerSkill : actingPlayer.getPlayer().getSkills()) {
      ReRollSource source = playerSkill.getRerollSource(action);
      if (source != null && !actingPlayer.isSkillUsed(playerSkill)) {
        return source;
      }
    }    
    return null;
  }
}
