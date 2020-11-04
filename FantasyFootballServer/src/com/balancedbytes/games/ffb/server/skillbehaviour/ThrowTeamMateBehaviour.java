package com.balancedbytes.games.ffb.server.skillbehaviour;

import java.util.Set;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PassModifier;
import com.balancedbytes.games.ffb.PassModifierFactory;
import com.balancedbytes.games.ffb.PassingDistance;
import com.balancedbytes.games.ffb.ReRolledAction;
import com.balancedbytes.games.ffb.dialog.DialogSkillUseParameter;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.model.modifier.NamedProperties;
import com.balancedbytes.games.ffb.net.commands.ClientCommandUseSkill;
import com.balancedbytes.games.ffb.report.ReportThrowTeamMateRoll;
import com.balancedbytes.games.ffb.server.DiceInterpreter;
import com.balancedbytes.games.ffb.server.model.ServerSkill;
import com.balancedbytes.games.ffb.server.model.SkillBehaviour;
import com.balancedbytes.games.ffb.server.model.StepModifier;
import com.balancedbytes.games.ffb.server.step.SequenceGenerator;
import com.balancedbytes.games.ffb.server.step.StepAction;
import com.balancedbytes.games.ffb.server.step.StepCommandStatus;
import com.balancedbytes.games.ffb.server.step.action.block.StepWrestle;
import com.balancedbytes.games.ffb.server.step.action.ttm.StepThrowTeamMate;
import com.balancedbytes.games.ffb.server.step.action.ttm.StepThrowTeamMate.StepState;
import com.balancedbytes.games.ffb.server.util.UtilServerDialog;
import com.balancedbytes.games.ffb.server.util.UtilServerReRoll;
import com.balancedbytes.games.ffb.skill.ThrowTeamMate;
import com.balancedbytes.games.ffb.skill.Wrestle;
import com.balancedbytes.games.ffb.util.UtilCards;
import com.balancedbytes.games.ffb.util.UtilPassing;

public class ThrowTeamMateBehaviour extends SkillBehaviour<ThrowTeamMate> {
	public ThrowTeamMateBehaviour() {
		super();

		registerModifier(new StepModifier<StepThrowTeamMate, StepThrowTeamMate.StepState>() {

			@Override
			public StepCommandStatus handleCommandHook(StepThrowTeamMate step, StepState state,
					ClientCommandUseSkill useSkillCommand) {
				return StepCommandStatus.EXECUTE_STEP;
			}

			@Override
			public boolean handleExecuteStepHook(StepThrowTeamMate step, StepState state) {
				  Game game = step.getGameState().getGame();
				    ActingPlayer actingPlayer = game.getActingPlayer();
				    actingPlayer.setHasPassed(true);
				    game.setConcessionPossible(false);
				    game.getTurnData().setPassUsed(true);
				    UtilServerDialog.hideDialog(step.getGameState());
				    Player thrower = game.getActingPlayer().getPlayer();
				    boolean doRoll = true;
				    if (ReRolledAction.THROW_TEAM_MATE == step.getReRolledAction()) {
				      if ((step.getReRollSource() == null) || !UtilServerReRoll.useReRoll(step, step.getReRollSource(), thrower)) {
				    	  step.getResult().setNextAction(StepAction.GOTO_LABEL, state.goToLabelOnFailure);
				        doRoll = false;
				      }
				    }
				    if (doRoll) {
				      PassModifierFactory passModifierFactory = new PassModifierFactory();
				      FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(thrower);
				      PassingDistance passingDistance = UtilPassing.findPassingDistance(game, throwerCoordinate, game.getPassCoordinate(), true);
				      Set<PassModifier> passModifiers = passModifierFactory.findPassModifiers(game, thrower, passingDistance, true);
				      int minimumRoll = DiceInterpreter.getInstance().minimumRollThrowTeamMate(thrower, passingDistance, passModifiers);
				      int roll = step.getGameState().getDiceRoller().rollSkill();
				      boolean successful = !DiceInterpreter.getInstance().isPassFumble(roll, actingPlayer.getPlayer(), passingDistance, passModifiers);
				      PassModifier[] passModifierArray = passModifierFactory.toArray(passModifiers);
				      boolean reRolled = ((step.getReRolledAction() == ReRolledAction.THROW_TEAM_MATE) && (step.getReRollSource() != null));
				      step.getResult().addReport(new ReportThrowTeamMateRoll(thrower.getId(), successful, roll, minimumRoll, reRolled, passModifierArray, passingDistance, state.thrownPlayerId));
				      if (successful) {
				        Player thrownPlayer = game.getPlayerById(state.thrownPlayerId);
				        boolean scattersSingleDirection =thrownPlayer != null &&  UtilCards.hasSkillWithProperty(thrownPlayer, NamedProperties.ttmScattersInSingleDirection);
				      	SequenceGenerator.getInstance().pushScatterPlayerSequence(step.getGameState(), state.thrownPlayerId, state.thrownPlayerState, state.thrownPlayerHasBall, throwerCoordinate, scattersSingleDirection, true);
				      	step.getResult().setNextAction(StepAction.NEXT_STEP);
				      } else {
				        if (step.getReRolledAction() != ReRolledAction.THROW_TEAM_MATE) {
				        	step.setReRolledAction(ReRolledAction.THROW_TEAM_MATE);
				        	
				          if (UtilCards.hasSkillWithProperty(thrower, NamedProperties.canRerollFailedPass)) {
				            UtilServerDialog.showDialog(step.getGameState(), new DialogSkillUseParameter(thrower.getId(), ServerSkill.PASS, minimumRoll), false);
				          } else {
				            if (!UtilServerReRoll.askForReRollIfAvailable(step.getGameState(), actingPlayer.getPlayer(), ReRolledAction.THROW_TEAM_MATE, minimumRoll, false)) {
				            	step.getResult().setNextAction(StepAction.GOTO_LABEL, state.goToLabelOnFailure);
				            }
				          }
				        } else {
				        	step.getResult().setNextAction(StepAction.GOTO_LABEL, state.goToLabelOnFailure);
				        }
				      }
				    }
				    
				return false;
			}
			
		});
	}
}
