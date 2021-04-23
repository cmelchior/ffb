package com.fumbbl.ffb.server.step.action.pass;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.UtilJson;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.IServerJsonOption;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.step.AbstractStep;
import com.fumbbl.ffb.server.step.StepAction;
import com.fumbbl.ffb.server.step.StepCommandStatus;
import com.fumbbl.ffb.server.step.StepException;
import com.fumbbl.ffb.server.step.StepId;
import com.fumbbl.ffb.server.step.StepParameter;
import com.fumbbl.ffb.server.step.StepParameterKey;
import com.fumbbl.ffb.server.step.StepParameterSet;
import com.fumbbl.ffb.util.StringTool;

/**
 * Step in the pass sequence dispatching according to different types of
 * passing.
 * 
 * Needs to be initialized with stepParameter GOTO_LABEL_ON_END. Needs to be
 * initialized with stepParameter GOTO_LABEL_ON_HAIL_MARY_PASS. Needs to be
 * initialized with stepParameter GOTO_LABEL_ON_HAND_OVER.
 * 
 * Expects stepParameter CATCHER_ID to be set by a preceding step.
 * 
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.COMMON)
public final class StepDispatchPassing extends AbstractStep {

	private String fGotoLabelOnEnd;
	private String fGotoLabelOnHailMaryPass;
	private String fGotoLabelOnHandOver;
	private String fCatcherId;

	public StepDispatchPassing(GameState pGameState) {
		super(pGameState);
	}

	public StepId getId() {
		return StepId.DISPATCH_PASSING;
	}

	@Override
	public void init(StepParameterSet pParameterSet) {
		if (pParameterSet != null) {
			for (StepParameter parameter : pParameterSet.values()) {
				switch (parameter.getKey()) {
				// mandatory
				case GOTO_LABEL_ON_END:
					fGotoLabelOnEnd = (String) parameter.getValue();
					break;
				// mandatory
				case GOTO_LABEL_ON_HAIL_MARY_PASS:
					fGotoLabelOnHailMaryPass = (String) parameter.getValue();
					break;
				// mandatory
				case GOTO_LABEL_ON_HAND_OVER:
					fGotoLabelOnHandOver = (String) parameter.getValue();
					break;
				default:
					break;
				}
			}
		}
		if (!StringTool.isProvided(fGotoLabelOnEnd)) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_END + " is not initialized.");
		}
		if (!StringTool.isProvided(fGotoLabelOnHailMaryPass)) {
			throw new StepException(
					"StepParameter " + StepParameterKey.GOTO_LABEL_ON_HAIL_MARY_PASS + " is not initialized.");
		}
		if (!StringTool.isProvided(fGotoLabelOnHandOver)) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_HAND_OVER + " is not initialized.");
		}
	}

	@Override
	public boolean setParameter(StepParameter pParameter) {
		if ((pParameter != null) && !super.setParameter(pParameter)) {
			switch (pParameter.getKey()) {
			case CATCHER_ID:
				fCatcherId = (String) pParameter.getValue();
				return true;
			default:
				break;
			}
		}
		return false;
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
		if ((game.getThrower() == null) || (game.getThrowerAction() == null)) {
			return;
		}
		switch (game.getThrowerAction()) {
		case PASS:
		case THROW_BOMB:
		case DUMP_OFF:
			getResult().setNextAction(StepAction.NEXT_STEP);
			return;
		case HAIL_MARY_PASS:
		case HAIL_MARY_BOMB:
			getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnHailMaryPass);
			return;
		case HAND_OVER:
			getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnHandOver);
			return;
		default:
			getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnEnd);
			return;
		}
	}

	// JSON serialization

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IServerJsonOption.GOTO_LABEL_ON_END.addTo(jsonObject, fGotoLabelOnEnd);
		IServerJsonOption.GOTO_LABEL_ON_HAIL_MARY_PASS.addTo(jsonObject, fGotoLabelOnHailMaryPass);
		IServerJsonOption.GOTO_LABEL_ON_HAND_OVER.addTo(jsonObject, fGotoLabelOnHandOver);
		IServerJsonOption.CATCHER_ID.addTo(jsonObject, fCatcherId);
		return jsonObject;
	}

	@Override
	public StepDispatchPassing initFrom(IFactorySource game, JsonValue pJsonValue) {
		super.initFrom(game, pJsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		fGotoLabelOnEnd = IServerJsonOption.GOTO_LABEL_ON_END.getFrom(game, jsonObject);
		fGotoLabelOnHailMaryPass = IServerJsonOption.GOTO_LABEL_ON_HAIL_MARY_PASS.getFrom(game, jsonObject);
		fGotoLabelOnHandOver = IServerJsonOption.GOTO_LABEL_ON_HAND_OVER.getFrom(game, jsonObject);
		fCatcherId = IServerJsonOption.CATCHER_ID.getFrom(game, jsonObject);
		return this;
	}

}