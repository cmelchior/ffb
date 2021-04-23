package com.fumbbl.ffb.mechanics.bb2020;

import java.util.Collections;
import java.util.Set;

import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.mechanics.Wording;
import com.fumbbl.ffb.model.ActingPlayer;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.Player;
import com.fumbbl.ffb.modifiers.CatchModifier;
import com.fumbbl.ffb.modifiers.DodgeModifier;
import com.fumbbl.ffb.modifiers.GazeModifier;
import com.fumbbl.ffb.modifiers.InterceptionModifier;
import com.fumbbl.ffb.modifiers.JumpModifier;
import com.fumbbl.ffb.modifiers.PickupModifier;
import com.fumbbl.ffb.modifiers.RightStuffModifier;
import com.fumbbl.ffb.modifiers.RollModifier;
import com.fumbbl.ffb.report.ReportSkillRoll;

@RulesCollection(RulesCollection.Rules.BB2020)
public class AgilityMechanic extends com.fumbbl.ffb.mechanics.AgilityMechanic {

	@Override
	public int minimumRollJumpUp(Player<?> pPlayer) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), Collections.emptySet());
	}

	@Override
	public int minimumRollDodge(Game pGame, Player<?> pPlayer, Set<DodgeModifier> pDodgeModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pDodgeModifiers);
	}

	@Override
	public int minimumRollPickup(Player<?> pPlayer, Set<PickupModifier> pPickupModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pPickupModifiers);
	}

	@Override
	public int minimumRollInterception(Player<?> pPlayer, Set<InterceptionModifier> pInterceptionModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pInterceptionModifiers);
	}

	@Override
	public int minimumRollJump(Player<?> pPlayer, Set<JumpModifier> pJumpModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pJumpModifiers);
	}

	@Override
	public int minimumRollHypnoticGaze(Player<?> pPlayer, Set<GazeModifier> pGazeModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pGazeModifiers);
	}

	@Override
	public int minimumRollCatch(Player<?> pPlayer, Set<CatchModifier> pCatchModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pCatchModifiers);
	}

	@Override
	public int minimumRollRightStuff(Player<?> pPlayer, Set<RightStuffModifier> pRightStuffModifiers) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), pRightStuffModifiers);
	}

	@Override
	public int minimumRollSafeThrow(Player<?> pPlayer) {
		return minimumRoll(pPlayer.getAgilityWithModifiers(), Collections.emptySet());
	}

	@Override
	public String formatDodgeResult(ReportSkillRoll report, ActingPlayer player) {
		return formatResult(player.getPlayer().getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatJumpResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatJumpUpResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatSafeThrowResult(Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), new RollModifier[0]);
	}

	@Override
	public String formatRightStuffResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatCatchResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatInterceptionResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatHypnoticGazeResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public String formatPickupResult(ReportSkillRoll report, Player<?> player) {
		return formatResult(player.getAgilityWithModifiers(), report.getRollModifiers());
	}

	@Override
	public Wording interceptionWording() {
		return new Wording("Interference", "deflect", "deflects", "interfering player");
	}

	private int minimumRoll(int agility, Set<? extends RollModifier<?>> modifiers) {
		return Math.max(2, agility + modifiers.stream().mapToInt(RollModifier::getModifier).sum());
	}

	private String formatResult(int agility, RollModifier<?>[] modifiers) {
		return " (Roll" + formatRollModifiers(modifiers) + " >= " + Math.max(2, agility) + "+)";
	}
}