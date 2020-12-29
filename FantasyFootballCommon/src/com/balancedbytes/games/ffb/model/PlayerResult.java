package com.balancedbytes.games.ffb.model;

import com.balancedbytes.games.ffb.SendToBoxReason;
import com.balancedbytes.games.ffb.SeriousInjury;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.IJsonSerializable;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.change.ModelChange;
import com.balancedbytes.games.ffb.model.change.ModelChangeId;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class PlayerResult implements IJsonSerializable {

	private int fCompletions;
	private int fTouchdowns;
	private int fInterceptions;
	private int fCasualties;
	private int fPlayerAwards;
	private int fBlocks;
	private int fFouls;
	private int fRushing;
	private int fPassing;
	private int fTurnsPlayed;
	private int fCurrentSpps;

	private boolean fDefecting;
	private SeriousInjury fSeriousInjury;
	private SeriousInjury fSeriousInjuryDecay;
	private SendToBoxReason fSendToBoxReason;
	private int fSendToBoxTurn;
	private int fSendToBoxHalf;
	private String fSendToBoxByPlayerId;
	private boolean fHasUsedSecretWeapon;

	private transient TeamResult fTeamResult;
	private transient Player<?> fPlayer;

	public PlayerResult(TeamResult pTeamResult) {
		this(pTeamResult, null);
	}

	public PlayerResult(TeamResult pTeamResult, Player<?> pPlayer) {
		fTeamResult = pTeamResult;
		fPlayer = pPlayer;
	}

	public TeamResult getTeamResult() {
		return fTeamResult;
	}

	public Player<?> getPlayer() {
		return fPlayer;
	}

	public String getPlayerId() {
		return ((getPlayer() != null) ? getPlayer().getId() : null);
	}

	public SeriousInjury getSeriousInjury() {
		return fSeriousInjury;
	}

	public void setSeriousInjury(SeriousInjury pSeriousInjury) {
		if (pSeriousInjury == fSeriousInjury) {
			return;
		}
		fSeriousInjury = pSeriousInjury;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SERIOUS_INJURY, fSeriousInjury);
	}

	public SeriousInjury getSeriousInjuryDecay() {
		return fSeriousInjuryDecay;
	}

	public void setSeriousInjuryDecay(SeriousInjury pSeriousInjuryDecay) {
		if (pSeriousInjuryDecay == fSeriousInjuryDecay) {
			return;
		}
		fSeriousInjuryDecay = pSeriousInjuryDecay;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SERIOUS_INJURY_DECAY, fSeriousInjuryDecay);
	}

	public SendToBoxReason getSendToBoxReason() {
		return fSendToBoxReason;
	}

	public void setSendToBoxReason(SendToBoxReason pSendToBoxReason) {
		if (pSendToBoxReason == fSendToBoxReason) {
			return;
		}
		fSendToBoxReason = pSendToBoxReason;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SEND_TO_BOX_REASON, fSendToBoxReason);
	}

	public int getSendToBoxTurn() {
		return fSendToBoxTurn;
	}

	public void setSendToBoxTurn(int pSendToBoxTurn) {
		if (pSendToBoxTurn == fSendToBoxTurn) {
			return;
		}
		fSendToBoxTurn = pSendToBoxTurn;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SEND_TO_BOX_TURN, fSendToBoxTurn);
	}

	public int getSendToBoxHalf() {
		return fSendToBoxHalf;
	}

	public void setSendToBoxHalf(int pSendToBoxHalf) {
		if (pSendToBoxHalf == fSendToBoxHalf) {
			return;
		}
		fSendToBoxHalf = pSendToBoxHalf;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SEND_TO_BOX_HALF, fSendToBoxHalf);
	}

	public int getTurnsPlayed() {
		return fTurnsPlayed;
	}

	public void setSendToBoxByPlayerId(String pSendToBoxByPlayerId) {
		if (StringTool.isEqual(pSendToBoxByPlayerId, fSendToBoxByPlayerId)) {
			return;
		}
		fSendToBoxByPlayerId = pSendToBoxByPlayerId;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_SEND_TO_BOX_BY_PLAYER_ID, fSendToBoxByPlayerId);
	}

	public String getSendToBoxByPlayerId() {
		return fSendToBoxByPlayerId;
	}

	public void setTurnsPlayed(int pTurnsPlayed) {
		if (pTurnsPlayed == fTurnsPlayed) {
			return;
		}
		fTurnsPlayed = pTurnsPlayed;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_TURNS_PLAYED, fTurnsPlayed);
	}

	public void setHasUsedSecretWeapon(boolean pHasUsedSecretWeapon) {
		if (pHasUsedSecretWeapon == fHasUsedSecretWeapon) {
			return;
		}
		fHasUsedSecretWeapon = pHasUsedSecretWeapon;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_HAS_USED_SECRET_WEAPON, fHasUsedSecretWeapon);
	}

	public boolean hasUsedSecretWeapon() {
		return fHasUsedSecretWeapon;
	}

	public int getCompletions() {
		return fCompletions;
	}

	public void setCompletions(int pCompletions) {
		if (pCompletions == fCompletions) {
			return;
		}
		fCompletions = pCompletions;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_COMPLETIONS, fCompletions);
	}

	public int getTouchdowns() {
		return fTouchdowns;
	}

	public void setTouchdowns(int pTouchdowns) {
		if (pTouchdowns == fTouchdowns) {
			return;
		}
		fTouchdowns = pTouchdowns;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_TOUCHDOWNS, fTouchdowns);
	}

	public int getInterceptions() {
		return fInterceptions;
	}

	public void setInterceptions(int pInterceptions) {
		if (pInterceptions == fInterceptions) {
			return;
		}
		fInterceptions = pInterceptions;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_INTERCEPTIONS, fInterceptions);
	}

	public int getCasualties() {
		return fCasualties;
	}

	public void setCasualties(int pCasualties) {
		if (pCasualties == fCasualties) {
			return;
		}
		fCasualties = pCasualties;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_CASUALTIES, fCasualties);
	}

	public int getPlayerAwards() {
		return fPlayerAwards;
	}

	public void setPlayerAwards(int pPlayerAwards) {
		if (pPlayerAwards == fPlayerAwards) {
			return;
		}
		fPlayerAwards = pPlayerAwards;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_PLAYER_AWARDS, fPlayerAwards);
	}

	public int getBlocks() {
		return fBlocks;
	}

	public void setBlocks(int pBlocks) {
		if (pBlocks == fBlocks) {
			return;
		}
		fBlocks = pBlocks;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_BLOCKS, fBlocks);
	}

	public int getFouls() {
		return fFouls;
	}

	public void setFouls(int pFouls) {
		if (pFouls == fFouls) {
			return;
		}
		fFouls = pFouls;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_FOULS, fFouls);
	}

	public int getRushing() {
		return fRushing;
	}

	public void setRushing(int pRushing) {
		if (pRushing == fRushing) {
			return;
		}
		fRushing = pRushing;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_RUSHING, fRushing);
	}

	public int getPassing() {
		return fPassing;
	}

	public void setPassing(int pPassing) {
		if (pPassing == fPassing) {
			return;
		}
		fPassing = pPassing;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_PASSING, fPassing);
	}

	public int getCurrentSpps() {
		return fCurrentSpps;
	}

	public void setCurrentSpps(int pCurrentSpps) {
		if (pCurrentSpps == fCurrentSpps) {
			return;
		}
		fCurrentSpps = pCurrentSpps;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_CURRENT_SPPS, fCurrentSpps);
	}

	public boolean isDefecting() {
		return fDefecting;
	}

	public void setDefecting(boolean pDefecting) {
		if (pDefecting == fDefecting) {
			return;
		}
		fDefecting = pDefecting;
		notifyObservers(ModelChangeId.PLAYER_RESULT_SET_DEFECTING, fDefecting);
	}

	public int totalEarnedSpps() {
		return ((getPlayerAwards() * 5) + (getTouchdowns() * 3) + (getCasualties() * 2) + (getInterceptions() * 2)
				+ getCompletions());
	}

	public Game getGame() {
		return getTeamResult().getGame();
	}

	public void init(PlayerResult pPlayerResult) {
		if (pPlayerResult != null) {
			fPlayer = pPlayerResult.getPlayer();
			fCompletions = pPlayerResult.getCompletions();
			fTouchdowns = pPlayerResult.getTouchdowns();
			fInterceptions = pPlayerResult.getInterceptions();
			fCasualties = pPlayerResult.getCasualties();
			fPlayerAwards = pPlayerResult.getPlayerAwards();
			fBlocks = pPlayerResult.getBlocks();
			fFouls = pPlayerResult.getFouls();
			fRushing = pPlayerResult.getRushing();
			fPassing = pPlayerResult.getPassing();
			fTurnsPlayed = pPlayerResult.getTurnsPlayed();
			fCurrentSpps = pPlayerResult.getCurrentSpps();
			fDefecting = pPlayerResult.isDefecting();
			fSeriousInjury = pPlayerResult.getSeriousInjury();
			fSendToBoxReason = pPlayerResult.getSendToBoxReason();
			fSendToBoxTurn = pPlayerResult.getSendToBoxTurn();
			fSendToBoxHalf = pPlayerResult.getSendToBoxHalf();
			fSendToBoxByPlayerId = pPlayerResult.getSendToBoxByPlayerId();
			fHasUsedSecretWeapon = pPlayerResult.hasUsedSecretWeapon();
		}
	}

	// change tracking

	private void notifyObservers(ModelChangeId pModelChangeId, Object pValue) {
		if ((getGame() == null) || (pModelChangeId == null) || !StringTool.isProvided(getPlayerId())) {
			return;
		}
		ModelChange modelChange = new ModelChange(pModelChangeId, getPlayerId(), pValue);
		getGame().notifyObservers(modelChange);
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.PLAYER_ID.addTo(jsonObject, getPlayerId());
		IJsonOption.COMPLETIONS.addTo(jsonObject, fCompletions);
		IJsonOption.TOUCHDOWNS.addTo(jsonObject, fTouchdowns);
		IJsonOption.INTERCEPTIONS.addTo(jsonObject, fInterceptions);
		IJsonOption.CASUALTIES.addTo(jsonObject, fCasualties);
		IJsonOption.PLAYER_AWARDS.addTo(jsonObject, fPlayerAwards);
		IJsonOption.BLOCKS.addTo(jsonObject, fBlocks);
		IJsonOption.FOULS.addTo(jsonObject, fFouls);
		IJsonOption.RUSHING.addTo(jsonObject, fRushing);
		IJsonOption.PASSING.addTo(jsonObject, fPassing);
		IJsonOption.CURRENT_SPPS.addTo(jsonObject, fCurrentSpps);
		IJsonOption.SERIOUS_INJURY.addTo(jsonObject, fSeriousInjury);
		IJsonOption.SEND_TO_BOX_REASON.addTo(jsonObject, fSendToBoxReason);
		IJsonOption.SEND_TO_BOX_TURN.addTo(jsonObject, fSendToBoxTurn);
		IJsonOption.SEND_TO_BOX_HALF.addTo(jsonObject, fSendToBoxHalf);
		IJsonOption.SEND_TO_BOX_BY_PLAYER_ID.addTo(jsonObject, fSendToBoxByPlayerId);
		IJsonOption.TURNS_PLAYED.addTo(jsonObject, fTurnsPlayed);
		IJsonOption.HAS_USED_SECRET_WEAPON.addTo(jsonObject, fHasUsedSecretWeapon);
		IJsonOption.DEFECTING.addTo(jsonObject, fDefecting);
		return jsonObject;
	}

	public PlayerResult initFrom(Game game, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		String playerId = IJsonOption.PLAYER_ID.getFrom(game, jsonObject);
		fPlayer = getTeamResult().getTeam().getPlayerById(playerId);
		fCompletions = IJsonOption.COMPLETIONS.getFrom(game, jsonObject);
		fTouchdowns = IJsonOption.TOUCHDOWNS.getFrom(game, jsonObject);
		fInterceptions = IJsonOption.INTERCEPTIONS.getFrom(game, jsonObject);
		fCasualties = IJsonOption.CASUALTIES.getFrom(game, jsonObject);
		fPlayerAwards = IJsonOption.PLAYER_AWARDS.getFrom(game, jsonObject);
		fBlocks = IJsonOption.BLOCKS.getFrom(game, jsonObject);
		fFouls = IJsonOption.FOULS.getFrom(game, jsonObject);
		fRushing = IJsonOption.RUSHING.getFrom(game, jsonObject);
		fPassing = IJsonOption.PASSING.getFrom(game, jsonObject);
		fCurrentSpps = IJsonOption.CURRENT_SPPS.getFrom(game, jsonObject);
		fSeriousInjury = (SeriousInjury) IJsonOption.SERIOUS_INJURY.getFrom(game, jsonObject);
		fSendToBoxReason = (SendToBoxReason) IJsonOption.SEND_TO_BOX_REASON.getFrom(game, jsonObject);
		fSendToBoxTurn = IJsonOption.SEND_TO_BOX_TURN.getFrom(game, jsonObject);
		fSendToBoxHalf = IJsonOption.SEND_TO_BOX_HALF.getFrom(game, jsonObject);
		fSendToBoxByPlayerId = IJsonOption.SEND_TO_BOX_BY_PLAYER_ID.getFrom(game, jsonObject);
		fTurnsPlayed = IJsonOption.TURNS_PLAYED.getFrom(game, jsonObject);
		fHasUsedSecretWeapon = IJsonOption.HAS_USED_SECRET_WEAPON.getFrom(game, jsonObject);
		fDefecting = IJsonOption.DEFECTING.getFrom(game, jsonObject);
		return this;
	}

}
