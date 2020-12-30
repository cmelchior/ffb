package com.balancedbytes.games.ffb.server.step.phase.kickoff;

import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.commands.ClientCommandSetupPlayer;
import com.balancedbytes.games.ffb.server.ActionStatus;
import com.balancedbytes.games.ffb.server.GameState;
import com.balancedbytes.games.ffb.server.IServerJsonOption;
import com.balancedbytes.games.ffb.server.net.ReceivedCommand;
import com.balancedbytes.games.ffb.server.step.AbstractStep;
import com.balancedbytes.games.ffb.server.step.StepCommandStatus;
import com.balancedbytes.games.ffb.server.step.StepId;
import com.balancedbytes.games.ffb.server.step.StepParameter;
import com.balancedbytes.games.ffb.server.step.StepParameterKey;
import com.balancedbytes.games.ffb.server.step.StepParameterSet;
import com.balancedbytes.games.ffb.server.util.UtilServerSetup;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

public class StepSwarming extends AbstractStep {

	public class StepState {
		public ActionStatus status;
		public boolean endTurn;
		public boolean handleReceivingTeam;
		public int allowedAmount;
		public String teamId;
	}

	private StepState state;

	public StepSwarming(GameState pGameState) {
		super(pGameState);

		state = new StepState();
	}

	@Override
	public void start() {
		executeStep();
	}

	@Override
	public StepId getId() {
		return StepId.SWARMING;
	}

	@Override
	public void init(StepParameterSet pParameterSet) {
		super.init(pParameterSet);
		if (pParameterSet != null) {
			for (StepParameter parameter : pParameterSet.values()) {
				if (parameter.getKey() == StepParameterKey.HANDLE_RECEIVING_TEAM) {
					state.handleReceivingTeam = (boolean) parameter.getValue();
				}
			}
		}
	}

	@Override
	public StepCommandStatus handleCommand(ReceivedCommand pReceivedCommand) {
		StepCommandStatus commandStatus = super.handleCommand(pReceivedCommand);

		switch (pReceivedCommand.getId()) {
		case CLIENT_END_TURN:
			state.endTurn = true;
			executeStep();
			break;

		case CLIENT_SETUP_PLAYER:
			ClientCommandSetupPlayer setupPlayerCommand = (ClientCommandSetupPlayer) pReceivedCommand.getCommand();
			UtilServerSetup.setupPlayer(getGameState(), setupPlayerCommand.getPlayerId(), setupPlayerCommand.getCoordinate());
			break;
		default:
			break;
		}
		return commandStatus;
	}

	private void executeStep() {
		getGameState().executeStepHooks(this, state);
	}

	@Override
	public JsonObject toJsonValue() {
		JsonObject jsonObject = super.toJsonValue();
		IServerJsonOption.END_TURN.addTo(jsonObject, state.endTurn);
		IServerJsonOption.HANDLE_RECEIVING_TEAM.addTo(jsonObject, state.handleReceivingTeam);
		IServerJsonOption.SWARMING_PLAYER_AMOUT.addTo(jsonObject, state.allowedAmount);
		IServerJsonOption.TEAM_ID.addTo(jsonObject, state.teamId);
		return jsonObject;
	}

	@Override
	public StepSwarming initFrom(IFactorySource game, JsonValue pJsonValue) {
		super.initFrom(game, pJsonValue);
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		state.endTurn = IServerJsonOption.END_TURN.getFrom(game, jsonObject);
		state.handleReceivingTeam = IServerJsonOption.HANDLE_RECEIVING_TEAM.getFrom(game, jsonObject);
		state.allowedAmount = IServerJsonOption.SWARMING_PLAYER_AMOUT.getFrom(game, jsonObject);
		state.teamId = IServerJsonOption.TEAM_ID.getFrom(game, jsonObject);
		return this;
	}

}
