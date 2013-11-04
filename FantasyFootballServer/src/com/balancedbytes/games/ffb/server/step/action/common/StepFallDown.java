package com.balancedbytes.games.ffb.server.step.action.common;

import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.InjuryType;
import com.balancedbytes.games.ffb.InjuryTypeFactory;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.TurnMode;
import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.model.ActingPlayer;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.server.GameState;
import com.balancedbytes.games.ffb.server.InjuryResult;
import com.balancedbytes.games.ffb.server.step.AbstractStep;
import com.balancedbytes.games.ffb.server.step.StepAction;
import com.balancedbytes.games.ffb.server.step.StepCommandStatus;
import com.balancedbytes.games.ffb.server.step.StepId;
import com.balancedbytes.games.ffb.server.step.StepParameter;
import com.balancedbytes.games.ffb.server.step.StepParameterKey;
import com.balancedbytes.games.ffb.server.util.UtilInjury;
import com.balancedbytes.games.ffb.util.UtilBox;

/**
 * Step in move sequence to drop the acting player.
 * 
 * Expects stepParameter INJURY_TYPE to be set by a preceding step.
 * 
 * Sets stepParameter END_TURN for all steps on the stack.
 * Sets stepParameter INJURY_RESULT for all steps on the stack.
 * 
 * @author Kalimar
 */
public class StepFallDown extends AbstractStep {

	private InjuryType fInjuryType;

	public StepFallDown(GameState pGameState) {
		super(pGameState);
	}

	public StepId getId() {
		return StepId.FALL_DOWN;
	}

	@Override
	public boolean setParameter(StepParameter pParameter) {
		if ((pParameter != null) && !super.setParameter(pParameter)) {
			switch (pParameter.getKey()) {
				case INJURY_TYPE:
					fInjuryType = (InjuryType) pParameter.getValue();
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
	public StepCommandStatus handleNetCommand(NetCommand pNetCommand) {
		StepCommandStatus commandStatus = super.handleNetCommand(pNetCommand);
		if (commandStatus == StepCommandStatus.EXECUTE_STEP) {
			executeStep();
		}
		return commandStatus;
	}

	private void executeStep() {
		Game game = getGameState().getGame();
    ActingPlayer actingPlayer = game.getActingPlayer();
    FieldCoordinate playerCoordinate = game.getFieldModel().getPlayerCoordinate(actingPlayer.getPlayer());
    InjuryResult injuryResultAttacker = UtilInjury.handleInjury(this, fInjuryType, null, actingPlayer.getPlayer(), playerCoordinate, null, ApothecaryMode.ATTACKER);
    publishParameters(UtilInjury.dropPlayer(this, actingPlayer.getPlayer()));
    if (actingPlayer.isSufferingBloodLust()) {
      game.getFieldModel().clearMoveSquares();
      PlayerState playerState = game.getFieldModel().getPlayerState(actingPlayer.getPlayer());
      game.getFieldModel().setPlayerState(actingPlayer.getPlayer(), playerState.changeBase(PlayerState.RESERVE));
      UtilBox.putPlayerIntoBox(game, actingPlayer.getPlayer());
    }
    publishParameter(new StepParameter(StepParameterKey.INJURY_RESULT, injuryResultAttacker));
    if ((fInjuryType != InjuryType.CROWDPUSH) && (game.getTurnMode() != TurnMode.PASS_BLOCK)) {
    	publishParameter(new StepParameter(StepParameterKey.END_TURN, true));
    }
    getResult().setNextAction(StepAction.NEXT_STEP);
	}

	public int getByteArraySerializationVersion() {
		return 1;
	}

	@Override
	public void addTo(ByteList pByteList) {
		super.addTo(pByteList);
		pByteList.addByte((byte) ((fInjuryType != null) ? fInjuryType.getId() : 0));
	}

	@Override
	public int initFrom(ByteArray pByteArray) {
		int byteArraySerializationVersion = super.initFrom(pByteArray);
		fInjuryType = new InjuryTypeFactory().forId(pByteArray.getByte());
		return byteArraySerializationVersion;
	}

}
