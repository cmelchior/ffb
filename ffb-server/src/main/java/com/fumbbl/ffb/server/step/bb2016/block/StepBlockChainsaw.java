package com.fumbbl.ffb.server.step.bb2016.block;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.*;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.UtilJson;
import com.fumbbl.ffb.model.ActingPlayer;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.property.NamedProperties;
import com.fumbbl.ffb.report.ReportChainsawRoll;
import com.fumbbl.ffb.server.DiceInterpreter;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.IServerJsonOption;
import com.fumbbl.ffb.server.InjuryResult;
import com.fumbbl.ffb.server.injury.injuryType.InjuryTypeChainsaw;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.step.*;
import com.fumbbl.ffb.server.util.UtilServerInjury;
import com.fumbbl.ffb.server.util.UtilServerReRoll;
import com.fumbbl.ffb.util.StringTool;

/**
 * Step in block sequence to handle skill CHAINSAW.
 * 
 * Needs to be initialized with stepParameter GOTO_LABEL_ON_FAILURE. Needs to be
 * initialized with stepParameter GOTO_LABEL_ON_SUCCESS.
 * 
 * Sets stepParameter CATCH_SCATTER_THROWIN_MODE for all steps on the stack.
 * Sets stepParameter END_TURN for all steps on the stack. Sets stepParameter
 * INJURY_RESULT for all steps on the stack.
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.BB2016)
public class StepBlockChainsaw extends AbstractStepWithReRoll {

	private String fGotoLabelOnSuccess;
	private String fGotoLabelOnFailure;

	public StepBlockChainsaw(GameState pGameState) {
		super(pGameState);
	}

	public StepId getId() {
		return StepId.BLOCK_CHAINSAW;
	}

	@Override
	public void init(StepParameterSet pParameterSet) {
		if (pParameterSet != null) {
			for (StepParameter parameter : pParameterSet.values()) {
				switch (parameter.getKey()) {
				case GOTO_LABEL_ON_FAILURE:
					fGotoLabelOnFailure = (String) parameter.getValue();
					break;
				case GOTO_LABEL_ON_SUCCESS:
					fGotoLabelOnSuccess = (String) parameter.getValue();
					break;
				default:
					break;
				}
			}
		}
		if (!StringTool.isProvided(fGotoLabelOnFailure)) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_FAILURE + " is not initialized.");
		}
		if (!StringTool.isProvided(fGotoLabelOnSuccess)) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_SUCCESS + " is not initialized.");
		}
	}

	@Override
	public void start() {
		super.start();
		executeStep();
	}

	@Override
	public StepCommandStatus handleCommand(ReceivedCommand pReceivedCommand) {
		StepCommandStatus commandStatus = super.handleCommand(pReceivedCommand);
		if (commandStatus == StepCommandStatus.EXECUTE_STEP) {
			executeStep();
		}
		return commandStatus;
	}

	private void executeStep() {
		Game game = getGameState().getGame();
		ActingPlayer actingPlayer = game.getActingPlayer();
		if (actingPlayer.getPlayer().hasSkillProperty(NamedProperties.blocksLikeChainsaw)) {
			boolean dropChainsawPlayer = false;
			if (ReRolledActions.CHAINSAW == getReRolledAction()) {
				if ((getReRollSource() == null)
						|| !UtilServerReRoll.useReRoll(this, getReRollSource(), actingPlayer.getPlayer())) {
					dropChainsawPlayer = true;
				}
			}
			if (!dropChainsawPlayer) {
				boolean reRolled = ((getReRolledAction() == ReRolledActions.CHAINSAW) && (getReRollSource() != null));
				if (!reRolled) {
					getResult().setSound(SoundId.CHAINSAW);
				}
				int roll = getGameState().getDiceRoller().rollChainsaw();
				int minimumRoll = DiceInterpreter.getInstance().minimumRollChainsaw();
				boolean successful = (roll >= minimumRoll);
				getResult().addReport(new ReportChainsawRoll(actingPlayer.getPlayerId(), successful, roll,
						minimumRoll, reRolled, null));
				if (successful) {
					FieldCoordinate defenderCoordinate = game.getFieldModel().getPlayerCoordinate(game.getDefender());
					InjuryResult injuryResultDefender = UtilServerInjury.handleInjury(this, new InjuryTypeChainsaw(),
							actingPlayer.getPlayer(), game.getDefender(), defenderCoordinate, null, null, ApothecaryMode.DEFENDER);
					if (injuryResultDefender.injuryContext().isArmorBroken()) {
						publishParameters(UtilServerInjury.dropPlayer(this, game.getDefender(), ApothecaryMode.DEFENDER));
					}
					publishParameter(new StepParameter(StepParameterKey.INJURY_RESULT, injuryResultDefender));
					getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnSuccess);
				} else {
					if (!UtilServerReRoll.askForReRollIfAvailable(getGameState(), actingPlayer.getPlayer(),
							ReRolledActions.CHAINSAW, minimumRoll, false)) {
						dropChainsawPlayer = true;
					}
				}
			}
			if (dropChainsawPlayer) {
				FieldCoordinate attackerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
				InjuryResult injuryResultAttacker = UtilServerInjury.handleInjury(this, new InjuryTypeChainsaw(), null,
						actingPlayer.getPlayer(), attackerCoordinate, null, null, ApothecaryMode.ATTACKER);
				if (injuryResultAttacker.injuryContext().isArmorBroken()) {
					publishParameters(UtilServerInjury.dropPlayer(this, actingPlayer.getPlayer(), ApothecaryMode.ATTACKER));
					publishParameter(new StepParameter(StepParameterKey.END_TURN, true));
				}
				publishParameter(new StepParameter(StepParameterKey.INJURY_RESULT, injuryResultAttacker));
				getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnFailure);
			}
		} else {
			getResult().setNextAction(StepAction.NEXT_STEP);
		}
	}

	// JSON serialization

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IServerJsonOption.GOTO_LABEL_ON_SUCCESS.addTo(jsonObject, fGotoLabelOnSuccess);
		IServerJsonOption.GOTO_LABEL_ON_FAILURE.addTo(jsonObject, fGotoLabelOnFailure);
		return jsonObject;
	}

	@Override
	public StepBlockChainsaw initFrom(IFactorySource source, JsonValue jsonValue) {
		super.initFrom(source, jsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(jsonValue);
		fGotoLabelOnSuccess = IServerJsonOption.GOTO_LABEL_ON_SUCCESS.getFrom(source, jsonObject);
		fGotoLabelOnFailure = IServerJsonOption.GOTO_LABEL_ON_FAILURE.getFrom(source, jsonObject);
		return this;
	}

}
