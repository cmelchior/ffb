package com.fumbbl.ffb.server.step.bb2020.pass;

import com.fumbbl.ffb.*;
import com.fumbbl.ffb.mechanics.PassResult;
import com.fumbbl.ffb.model.Game;
import com.fumbbl.ffb.report.ReportPassDeviate;
import com.fumbbl.ffb.report.ReportScatterBall;
import com.fumbbl.ffb.server.DiceInterpreter;
import com.fumbbl.ffb.server.GameState;
import com.fumbbl.ffb.server.net.ReceivedCommand;
import com.fumbbl.ffb.server.step.AbstractStep;
import com.fumbbl.ffb.server.step.StepAction;
import com.fumbbl.ffb.server.step.StepCommandStatus;
import com.fumbbl.ffb.server.step.StepId;
import com.fumbbl.ffb.server.step.bb2020.pass.state.PassState;
import com.fumbbl.ffb.server.util.UtilServerCatchScatterThrowIn;

import java.util.ArrayList;
import java.util.List;

/**
 * Step of the pass sequence to handle a missed pass.
 * <p>
 * Sets stepParameter CATCH_SCATTER_THROWIN_MODE for all steps on the stack.
 * Sets stepParameter THROWIN_COORDINATE for all steps on the stack.
 *
 * @author Kalimar
 */
@RulesCollection(RulesCollection.Rules.BB2020)
public class StepMissedPass extends AbstractStep {

	public StepMissedPass(GameState pGameState) {
		super(pGameState);
	}

	public StepId getId() {
		return StepId.MISSED_PASS;
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
		PassState state = getGameState().getPassState();
		FieldCoordinate coordinateEnd = null;
		FieldCoordinate lastValidCoordinate = null;
		List<Integer> rollList = new ArrayList<>();
		List<Direction> directionList = new ArrayList<>();
		FieldCoordinate coordinateStart;
		FieldCoordinate throwerCoordinate = game.getFieldModel().getPlayerCoordinate(game.getThrower());
		if (state.getResult().equals(PassResult.WILDLY_INACCURATE)) {
			coordinateStart = throwerCoordinate;
			int directionRoll = getGameState().getDiceRoller().rollScatterDirection();
			int distanceRoll = getGameState().getDiceRoller().rollScatterDistance();
			Direction direction = DiceInterpreter.getInstance().interpretScatterDirectionRoll(game, directionRoll);
			coordinateEnd = UtilServerCatchScatterThrowIn.findScatterCoordinate(coordinateStart, direction, distanceRoll);
			lastValidCoordinate = coordinateEnd;
			int validDistance = distanceRoll;
			while (!FieldCoordinateBounds.FIELD.isInBounds(lastValidCoordinate) && validDistance > 0) {
				validDistance--;
				lastValidCoordinate = UtilServerCatchScatterThrowIn.findScatterCoordinate(coordinateStart, direction, validDistance);
			}
			getResult().addReport(new ReportPassDeviate(coordinateEnd, direction, directionRoll, distanceRoll, false));
		} else {
			coordinateStart = game.getPassCoordinate();
			while (FieldCoordinateBounds.FIELD.isInBounds(coordinateStart) && (rollList.size() < 3)) {
				int roll = getGameState().getDiceRoller().rollScatterDirection();
				rollList.add(roll);
				Direction direction = DiceInterpreter.getInstance().interpretScatterDirectionRoll(game, roll);
				directionList.add(direction);
				coordinateEnd = UtilServerCatchScatterThrowIn.findScatterCoordinate(coordinateStart, direction, 1);
				lastValidCoordinate = FieldCoordinateBounds.FIELD.isInBounds(coordinateEnd) ? coordinateEnd : coordinateStart;
				coordinateStart = coordinateEnd;
			}
			int[] rolls = new int[rollList.size()];
			for (int i = 0; i < rolls.length; i++) {
				rolls[i] = rollList.get(i);
			}

			Direction[] directions = directionList.toArray(new Direction[0]);
			getResult().addReport(new ReportScatterBall(directions, rolls, false));
		}
		game.setPassCoordinate(lastValidCoordinate);
		state.setLandingOutOfBounds(lastValidCoordinate != coordinateEnd);
		RangeRuler rangeRuler = new RangeRuler(game.getThrowerId(), lastValidCoordinate, -1, false);

		game.getFieldModel().setRangeRuler(rangeRuler);
		if (PlayerAction.THROW_BOMB == game.getThrowerAction() || PlayerAction.HAIL_MARY_BOMB == game.getThrowerAction()) {
			game.getFieldModel().setBombCoordinate(lastValidCoordinate);
			game.getFieldModel().setBombMoving(true);

		} else {
			game.getFieldModel().setBallCoordinate(lastValidCoordinate);
			game.getFieldModel().setBallMoving(true);
		}
		getResult().setNextAction(StepAction.NEXT_STEP);

	}

}
