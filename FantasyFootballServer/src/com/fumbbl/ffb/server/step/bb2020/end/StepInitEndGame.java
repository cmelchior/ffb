package com.fumbbl.ffb.server.step.bb2020.end;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.fumbbl.ffb.RulesCollection;
import com.fumbbl.ffb.TurnMode;
import com.fumbbl.ffb.factory.IFactorySource;
import com.fumbbl.ffb.json.UtilJson;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.model.GameResult;
import com.fumbbl.ffb.model.TeamResult;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.IServerJsonOption;
import com.fumbbl.ffb.server.step.AbstractStep;
import com.fumbbl.ffb.server.step.StepAction;
import com.fumbbl.ffb.server.step.StepException;
import com.fumbbl.ffb.server.step.StepId;
import com.fumbbl.ffb.server.step.StepParameter;
import com.fumbbl.ffb.server.step.StepParameterKey;
import com.fumbbl.ffb.server.step.StepParameterSet;
import com.fumbbl.ffb.util.StringTool;

/**
 * Initialization step in end game sequence.
 *
 * Needs to be initialized with stepParameter GOTO_LABEL_ON_END. May be
 * initialized with stepParameter ADMIN_MODE.
 *
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.BB2020)
public final class StepInitEndGame extends AbstractStep {

	private String fGotoLabelOnEnd;
	private boolean fAdminMode;

	public StepInitEndGame(GameState pGameState) {
		super(pGameState);
	}

	public StepId getId() {
		return StepId.INIT_END_GAME;
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
				// optional
				case ADMIN_MODE:
					fAdminMode = (parameter.getValue() != null) ? (Boolean) parameter.getValue() : false;
					break;
				default:
					break;
				}
			}
		}
		if (!StringTool.isProvided(fGotoLabelOnEnd)) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_END + " is not initialized.");
		}
	}

	@Override
	public void start() {
		super.start();
		executeStep();
	}

	private void executeStep() {
		Game game = getGameState().getGame();
		if (game.getFinished() != null) {
			getResult().setNextAction(StepAction.GOTO_LABEL, fGotoLabelOnEnd);
			return;
		}
		GameResult gameResult = game.getGameResult();
		if (gameResult.getTeamResultHome().hasConceded()) {
			adjustScore(gameResult.getTeamResultAway(), gameResult.getTeamResultHome());
		} else if (gameResult.getTeamResultAway().hasConceded()) {
			adjustScore(gameResult.getTeamResultHome(), gameResult.getTeamResultAway());
		}
		game.setTurnMode(TurnMode.END_GAME);
		game.setConcessionPossible(false);
		game.setAdminMode(fAdminMode);
		getResult().setNextAction(StepAction.NEXT_STEP);
	}

	private void adjustScore(TeamResult winnerResult, TeamResult concedingResult) {
		winnerResult.setScore(winnerResult.getScore() + concedingResult.getScore() + 1);
		concedingResult.setScore(0);
	}

	// JSON serialization

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IServerJsonOption.ADMIN_MODE.addTo(jsonObject, fAdminMode);
		IServerJsonOption.GOTO_LABEL_ON_END.addTo(jsonObject, fGotoLabelOnEnd);
		return jsonObject;
	}

	@Override
	public StepInitEndGame initFrom(IFactorySource game, JsonValue pJsonValue) {
		super.initFrom(game, pJsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		Boolean adminMode = IServerJsonOption.ADMIN_MODE.getFrom(game, jsonObject);
		fAdminMode = (adminMode != null) ? adminMode : false;
		fGotoLabelOnEnd = IServerJsonOption.GOTO_LABEL_ON_END.getFrom(game, jsonObject);
		return this;
	}

}
