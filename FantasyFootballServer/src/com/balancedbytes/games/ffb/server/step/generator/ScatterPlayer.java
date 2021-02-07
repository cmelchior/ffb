package com.balancedbytes.games.ffb.server.step.generator;

import com.balancedbytes.games.ffb.ApothecaryMode;
import com.balancedbytes.games.ffb.FieldCoordinate;
import com.balancedbytes.games.ffb.PlayerState;
import com.balancedbytes.games.ffb.RulesCollection;
import com.balancedbytes.games.ffb.server.GameState;
import com.balancedbytes.games.ffb.server.IServerLogLevel;
import com.balancedbytes.games.ffb.server.step.IStepLabel;
import com.balancedbytes.games.ffb.server.step.StepId;
import com.balancedbytes.games.ffb.server.step.StepParameterKey;

import static com.balancedbytes.games.ffb.server.step.StepParameter.from;

@RulesCollection(RulesCollection.Rules.COMMON)
public class ScatterPlayer extends SequenceGenerator<ScatterPlayer.SequenceParams> {

	protected ScatterPlayer() {
		super(Type.ScatterPlayer);
	}

	@Override
	public void pushSequence(SequenceParams params) {
		GameState gameState = params.getGameState();
		gameState.getServer().getDebugLog().log(IServerLogLevel.DEBUG, gameState.getId(),
			"push scatterPlayerSequence onto stack");

		Sequence sequence = new Sequence(gameState);

		if (params.hasSwoop) {
			sequence.add(StepId.SWOOP, from(StepParameterKey.THROWN_PLAYER_ID, params.thrownPlayerId),
				from(StepParameterKey.THROWN_PLAYER_STATE, params.thrownPlayerState),
				from(StepParameterKey.THROWN_PLAYER_HAS_BALL, params.thrownPlayerHasBall),
				from(StepParameterKey.THROWN_PLAYER_COORDINATE, params.thrownPlayerCoordinate),
				from(StepParameterKey.THROW_SCATTER, params.throwScatter),
				from(StepParameterKey.GOTO_LABEL_ON_FALL_DOWN, IStepLabel.APOTHECARY_HIT_PLAYER));
		} else {
			sequence.add(StepId.INIT_SCATTER_PLAYER, from(StepParameterKey.THROWN_PLAYER_ID, params.thrownPlayerId),
				from(StepParameterKey.THROWN_PLAYER_STATE, params.thrownPlayerState),
				from(StepParameterKey.THROWN_PLAYER_HAS_BALL, params.thrownPlayerHasBall),
				from(StepParameterKey.THROWN_PLAYER_COORDINATE, params.thrownPlayerCoordinate),
				from(StepParameterKey.THROW_SCATTER, params.throwScatter));
		}
		sequence.add(StepId.APOTHECARY, IStepLabel.APOTHECARY_HIT_PLAYER,
			from(StepParameterKey.APOTHECARY_MODE, ApothecaryMode.HIT_PLAYER));
		sequence.add(StepId.CATCH_SCATTER_THROW_IN);
		sequence.add(StepId.END_SCATTER_PLAYER);
		// may insert a new scatterPlayerSequence at this point

		gameState.getStepStack().push(sequence.getSequence());

	}

	public static class SequenceParams extends SequenceGenerator.SequenceParams {
		private final String thrownPlayerId;
		private final PlayerState thrownPlayerState;
		private final boolean thrownPlayerHasBall;
		private final FieldCoordinate thrownPlayerCoordinate;
		private final boolean hasSwoop, throwScatter;

		public SequenceParams(GameState gameState, String thrownPlayerId, PlayerState thrownPlayerState, boolean thrownPlayerHasBall, FieldCoordinate thrownPlayerCoordinate, boolean hasSwoop, boolean throwScatter) {
			super(gameState);
			this.thrownPlayerId = thrownPlayerId;
			this.thrownPlayerState = thrownPlayerState;
			this.thrownPlayerHasBall = thrownPlayerHasBall;
			this.thrownPlayerCoordinate = thrownPlayerCoordinate;
			this.hasSwoop = hasSwoop;
			this.throwScatter = throwScatter;
		}
	}
}