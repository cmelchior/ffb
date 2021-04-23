package com.fumbbl.ffb.server.step.generator;

import com.fumbbl.ffb.INamedObject;
import com.fumbbl.ffb.server.GameState;

public abstract class SequenceGenerator<T extends SequenceGenerator.SequenceParams> implements INamedObject {

	private final Type type;

	protected SequenceGenerator(Type type) {
		this.type = type;
	}

	@Override
	public String getName() {
		return type.name();
	};

	public abstract void pushSequence(T params);

	public enum Type {
		BlitzBlock, BlitzMove, Block, Bomb, Card, EndGame, EndPlayerAction, EndTurn, Foul, Inducement, KickTeamMate, Kickoff,
		MultiBlock, Move, Pass, PileDriver, RiotousRookies, ScatterPlayer, Select, SelectBlitzTarget,
		SpecialEffect, SpikedBallApo, StartGame, ThrowTeamMate, Wizard
	}

	public static class SequenceParams {
		private final GameState gameState;

		public SequenceParams(GameState gameState) {
			this.gameState = gameState;
		}

		public GameState getGameState() {
			return gameState;
		}
	}
}