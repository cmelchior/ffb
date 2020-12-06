package com.balancedbytes.games.ffb.server.step.action.ttm;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerAction;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Animation;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.net.commands.ClientCommandSwoop;
import com.balancedbytes.games.ffb.server.ActionStatus;
import com.balancedbytes.games.ffb.server.GameState;
import com.balancedbytes.games.ffb.server.IServerJsonOption;
import com.balancedbytes.games.ffb.server.net.ReceivedCommand;
import com.balancedbytes.games.ffb.server.step.AbstractStep;
import com.balancedbytes.games.ffb.server.step.StepAction;
import com.balancedbytes.games.ffb.server.step.StepCommandStatus;
import com.balancedbytes.games.ffb.server.step.StepException;
import com.balancedbytes.games.ffb.server.step.StepId;
import com.balancedbytes.games.ffb.server.step.StepParameter;
import com.balancedbytes.games.ffb.server.step.StepParameterKey;
import com.balancedbytes.games.ffb.server.step.StepParameterSet;
import com.balancedbytes.games.ffb.server.step.UtilServerSteps;
import com.balancedbytes.games.ffb.server.util.UtilServerGame;
import com.balancedbytes.games.ffb.server.util.UtilServerPlayerSwoop;
import com.balancedbytes.games.ffb.util.UtilActingPlayer;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Step in the move sequence to handle skill SWOOP.
 * 
 * Needs to be initialized with stepParameter THROWN_PLAYER_ID. Needs to be
 * initialized with stepParameter THROWN_PLAYER_STATE. Needs to be initialized
 * with stepParameter THROWN_PLAYER_HAS_BALL. Needs to be initialized with
 * stepParameter THROWN_PLAYER_COORDINATE. Needs to be initialized with
 * stepParameter THROW_SCATTER.
 * 
 * Sets stepParameter CATCH_SCATTER_THROW_IN_MODE for all steps on the stack.
 * Sets stepParameter DROP_TTM_PLAYER for all steps on the stack. Sets
 * stepParameter END_TURN for all steps on the stack. Sets stepParameter
 * INJURY_RESULT for all steps on the stack. Sets stepParameter
 * THROWIN_COORDINATE for all steps on the stack. Sets stepParameter
 * THROWN_PLAYER_ID for all steps on the stack. Sets stepParameter
 * THROWN_PLAYER_STATE for all steps on the stack. Sets stepParameter
 * THROWN_PLAYER_HAS_BALL for all steps on the stack. Sets stepParameter
 * THROWN_PLAYER_COORDINATE for all steps on the stack.
 * 
 * @author Kalimar
 */
public class StepSwoop extends AbstractStep {

	public class StepState {
		public ActionStatus status;

		public String thrownPlayerId;
		public PlayerState thrownPlayerState;
		public boolean thrownPlayerHasBall;
		public FieldCoordinate thrownPlayerCoordinate;
		public boolean throwScatter;
		public FieldCoordinate coordinateFrom;
		public FieldCoordinate coordinateTo;
		public String goToLabelOnFallDown;
	}

	private StepState state;

	public StepSwoop(GameState pGameState) {
		super(pGameState);
		state = new StepState();
	}

	public StepId getId() {
		return StepId.SWOOP;
	}

	@Override
	public void init(StepParameterSet pParameterSet) {
		if (pParameterSet != null) {
			for (StepParameter parameter : pParameterSet.values()) {
				switch (parameter.getKey()) {
				// mandatory
				case THROWN_PLAYER_ID:
					state.thrownPlayerId = (String) parameter.getValue();
					break;
				// mandatory
				case THROWN_PLAYER_HAS_BALL:
					state.thrownPlayerHasBall = (parameter.getValue() != null) ? (Boolean) parameter.getValue() : false;
					break;
				// mandatory
				case THROWN_PLAYER_COORDINATE:
					state.thrownPlayerCoordinate = (FieldCoordinate) parameter.getValue();
					break;
				// mandatory
				case THROWN_PLAYER_STATE:
					state.thrownPlayerState = (PlayerState) parameter.getValue();
					break;
				// mandatory
				case THROW_SCATTER:
					state.throwScatter = (parameter.getValue() != null) ? (Boolean) parameter.getValue() : false;
					break;
				case GOTO_LABEL_ON_FALL_DOWN:
					state.goToLabelOnFallDown = (String) parameter.getValue();
					break;
				default:
					break;
				}
			}
		}
		if (state.thrownPlayerState == null) {
			throw new StepException("StepParameter " + StepParameterKey.THROWN_PLAYER_STATE + " is not initialized.");
		}
		if (state.thrownPlayerId == null) {
			throw new StepException("StepParameter " + StepParameterKey.THROWN_PLAYER_ID + " is not initialized.");
		}
		if (state.thrownPlayerCoordinate == null) {
			throw new StepException("StepParameter " + StepParameterKey.THROWN_PLAYER_COORDINATE + " is not initialized.");
		}
		if (state.goToLabelOnFallDown == null) {
			throw new StepException("StepParameter " + StepParameterKey.GOTO_LABEL_ON_FALL_DOWN + " is not initialized.");
		}
	}

	@Override
	public boolean setParameter(StepParameter pParameter) {
		if ((pParameter != null) && !super.setParameter(pParameter)) {
			switch (pParameter.getKey()) {
			case COORDINATE_FROM:
				state.coordinateFrom = (FieldCoordinate) pParameter.getValue();
				return true;
			case COORDINATE_TO:
				state.coordinateTo = (FieldCoordinate) pParameter.getValue();
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
		if (commandStatus == StepCommandStatus.UNHANDLED_COMMAND) {
			switch (pReceivedCommand.getId()) {
			case CLIENT_SWOOP:
				ClientCommandSwoop swoopCommand = (ClientCommandSwoop) pReceivedCommand.getCommand();
				state.coordinateTo = swoopCommand.getTargetCoordinate();
				if (!UtilServerSteps.checkCommandIsFromHomePlayer(getGameState(), pReceivedCommand)) {
					state.coordinateTo = state.coordinateTo.transform();
				}
				executeSwoop();
				break;
			default:
				break;
			}
		} else if (commandStatus == StepCommandStatus.EXECUTE_STEP) {
			executeStep();
		}
		return commandStatus;
	}

	private void executeSwoop() {
		getGameState().executeStepHooks(this, state);
	}

	private void executeStep() {
		GameState gameState = getGameState();
		Game game = gameState.getGame();

		Player thrownPlayer = game.getPlayerById(state.thrownPlayerId);
		if ((thrownPlayer == null) || (state.thrownPlayerCoordinate == null)) {
			getResult().setNextAction(StepAction.NEXT_STEP);
			return;
		}
		FieldCoordinate passCoordinate = state.thrownPlayerCoordinate;
		if (state.throwScatter) {
			game.getFieldModel().setRangeRuler(null);
			game.getFieldModel().clearMoveSquares();
			passCoordinate = game.getPassCoordinate();

			// Render flying animation
			getResult().setAnimation(
					new Animation(state.thrownPlayerCoordinate, passCoordinate, state.thrownPlayerId, state.thrownPlayerHasBall));
			UtilServerGame.syncGameModel(this);

			// Move player
			game.getFieldModel().setPlayerCoordinate(thrownPlayer, passCoordinate);
			UtilActingPlayer.changeActingPlayer(game, state.thrownPlayerId, PlayerAction.SWOOP, false);
			if (state.thrownPlayerHasBall) {
				game.getFieldModel().setBallCoordinate(passCoordinate);
			}
			game.getActingPlayer().setCurrentMove(thrownPlayer.getMovement() - 3);

			publishParameter(new StepParameter(StepParameterKey.THROWN_PLAYER_ID, state.thrownPlayerId));
			publishParameter(new StepParameter(StepParameterKey.THROWN_PLAYER_STATE, state.thrownPlayerState));
			publishParameter(new StepParameter(StepParameterKey.THROWN_PLAYER_HAS_BALL, state.thrownPlayerHasBall));

			UtilServerGame.syncGameModel(this);
		}

		if (state.coordinateTo == null) {
			UtilServerPlayerSwoop.updateSwoopSquares(gameState, thrownPlayer);
		}
		// getResult().setNextAction(StepAction.NEXT_STEP);
	}

	// JSON serialization

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IServerJsonOption.THROWN_PLAYER_ID.addTo(jsonObject, state.thrownPlayerId);
		IServerJsonOption.THROWN_PLAYER_STATE.addTo(jsonObject, state.thrownPlayerState);
		IServerJsonOption.THROWN_PLAYER_HAS_BALL.addTo(jsonObject, state.thrownPlayerHasBall);
		IServerJsonOption.THROWN_PLAYER_COORDINATE.addTo(jsonObject, state.thrownPlayerCoordinate);
		IServerJsonOption.THROW_SCATTER.addTo(jsonObject, state.throwScatter);
		IServerJsonOption.COORDINATE_FROM.addTo(jsonObject, state.coordinateFrom);
		IServerJsonOption.COORDINATE_TO.addTo(jsonObject, state.coordinateTo);
		return jsonObject;
	}

	@Override
	public StepSwoop initFrom(JsonValue pJsonValue) {
		super.initFrom(pJsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		state.thrownPlayerId = IServerJsonOption.THROWN_PLAYER_ID.getFrom(jsonObject);
		state.thrownPlayerState = IServerJsonOption.THROWN_PLAYER_STATE.getFrom(jsonObject);
		state.thrownPlayerHasBall = IServerJsonOption.THROWN_PLAYER_HAS_BALL.getFrom(jsonObject);
		state.thrownPlayerCoordinate = IServerJsonOption.THROWN_PLAYER_COORDINATE.getFrom(jsonObject);
		state.throwScatter = IServerJsonOption.THROW_SCATTER.getFrom(jsonObject);
		state.coordinateFrom = IServerJsonOption.COORDINATE_FROM.getFrom(jsonObject);
		state.coordinateTo = IServerJsonOption.COORDINATE_TO.getFrom(jsonObject);
		return this;
	}

}
