package com.balancedbytes.games.ffb.report;

import com.balancedbytes.games.ffb.FactoryType.Factory;
import com.balancedbytes.games.ffb.factory.CatchModifierFactory;
import com.balancedbytes.games.ffb.factory.DodgeModifierFactory;
import com.balancedbytes.games.ffb.factory.GazeModifierFactory;
import com.balancedbytes.games.ffb.factory.GoForItModifierFactory;
import com.balancedbytes.games.ffb.factory.IFactorySource;
import com.balancedbytes.games.ffb.factory.IRollModifierFactory;
import com.balancedbytes.games.ffb.factory.InterceptionModifierFactory;
import com.balancedbytes.games.ffb.factory.JumpModifierFactory;
import com.balancedbytes.games.ffb.factory.PassModifierFactory;
import com.balancedbytes.games.ffb.factory.PickupModifierFactory;
import com.balancedbytes.games.ffb.factory.RightStuffModifierFactory;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.modifiers.RollModifier;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author Kalimar
 */
public class ReportSkillRoll implements IReport {

	private final ReportId fId;
	private String fPlayerId;
	private boolean fSuccessful;
	private int fRoll;
	private int fMinimumRoll;
	private boolean fReRolled;
	private List<RollModifier<?>> fRollModifierList;

	public ReportSkillRoll(ReportId pId) {
		fId = pId;
		initRollModifiers(null);
	}

	public ReportSkillRoll(ReportId pId, String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll,
			boolean pReRolled) {
		this(pId, pPlayerId, pSuccessful, pRoll, pMinimumRoll, pReRolled, null);
	}

	public ReportSkillRoll(ReportId pId, String pPlayerId, boolean pSuccessful, int pRoll, int pMinimumRoll,
			boolean pReRolled, RollModifier<?>[] pRollModifiers) {
		fId = pId;
		fPlayerId = pPlayerId;
		fSuccessful = pSuccessful;
		fRoll = pRoll;
		fMinimumRoll = pMinimumRoll;
		fReRolled = pReRolled;
		initRollModifiers(pRollModifiers);
	}

	private void initRollModifiers(RollModifier<?>[] pRollModifiers) {
		fRollModifierList = new ArrayList<>();
		if (ArrayTool.isProvided(pRollModifiers)) {
			for (RollModifier<?> rollModifier : pRollModifiers) {
				addRollModifier(rollModifier);
			}
		}
		fRollModifierList.sort(Comparator.comparing(RollModifier::getName));
	}

	public void addRollModifier(RollModifier<?> pRollModifier) {
		if (pRollModifier != null) {
			fRollModifierList.add(pRollModifier);
		}
	}

	public RollModifier<?>[] getRollModifiers() {
		return fRollModifierList.toArray(new RollModifier[0]);
	}

	public boolean hasRollModifier(RollModifier<?> pRollModifier) {
		return fRollModifierList.contains(pRollModifier);
	}

	protected List<RollModifier<?>> getRollModifierList() {
		return fRollModifierList;
	}

	public ReportId getId() {
		return fId;
	}

	public String getPlayerId() {
		return fPlayerId;
	}

	public boolean isSuccessful() {
		return fSuccessful;
	}

	public int getRoll() {
		return fRoll;
	}

	public int getMinimumRoll() {
		return fMinimumRoll;
	}

	public boolean isReRolled() {
		return fReRolled;
	}

	// transformation

	public IReport transform(IFactorySource source) {
		return new ReportSkillRoll(getId(), getPlayerId(), isSuccessful(), getRoll(), getMinimumRoll(), isReRolled(),
				getRollModifiers());
	}

	// JSON serialization

	public JsonObject toJsonValue() {
		JsonObject jsonObject = new JsonObject();
		IJsonOption.REPORT_ID.addTo(jsonObject, fId);
		IJsonOption.PLAYER_ID.addTo(jsonObject, fPlayerId);
		IJsonOption.SUCCESSFUL.addTo(jsonObject, fSuccessful);
		IJsonOption.ROLL.addTo(jsonObject, fRoll);
		IJsonOption.MINIMUM_ROLL.addTo(jsonObject, fMinimumRoll);
		IJsonOption.RE_ROLLED.addTo(jsonObject, fReRolled);
		if (fRollModifierList.size() > 0) {
			JsonArray modifierArray = new JsonArray();
			for (RollModifier<?> modifier : fRollModifierList) {
				modifierArray.add(UtilJson.toJsonValue(modifier));
			}
			IJsonOption.ROLL_MODIFIERS.addTo(jsonObject, modifierArray);
		}
		return jsonObject;
	}

	public ReportSkillRoll initFrom(IFactorySource source, JsonValue pJsonValue) {
		JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
		UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(source, jsonObject));
		fPlayerId = IJsonOption.PLAYER_ID.getFrom(source, jsonObject);
		fSuccessful = IJsonOption.SUCCESSFUL.getFrom(source, jsonObject);
		fRoll = IJsonOption.ROLL.getFrom(source, jsonObject);
		fMinimumRoll = IJsonOption.MINIMUM_ROLL.getFrom(source, jsonObject);
		fReRolled = IJsonOption.RE_ROLLED.getFrom(source, jsonObject);
		JsonArray modifierArray = IJsonOption.ROLL_MODIFIERS.getFrom(source, jsonObject);
		if (modifierArray != null) {
			IRollModifierFactory<?> modifierFactory = createRollModifierFactory(source);
			if (modifierFactory != null) {
				for (int i = 0; i < modifierArray.size(); i++) {
					fRollModifierList.add((RollModifier<?>) UtilJson.toEnumWithName(modifierFactory, modifierArray.get(i)));
				}
			}
		}
		return this;
	}

	private IRollModifierFactory<?> createRollModifierFactory(IFactorySource source) {
		switch (getId()) {
		case CATCH_ROLL:
			return source.<CatchModifierFactory>getFactory(Factory.CATCH_MODIFIER);
		case DODGE_ROLL:
			return source.<DodgeModifierFactory>getFactory(Factory.DODGE_MODIFIER);
		case GO_FOR_IT_ROLL:
			return source.<GoForItModifierFactory>getFactory(Factory.GO_FOR_IT_MODIFIER);
		case INTERCEPTION_ROLL:
			return source.<InterceptionModifierFactory>getFactory(Factory.INTERCEPTION_MODIFIER);
		case JUMP_ROLL:
			return source.<JumpModifierFactory>getFactory(Factory.JUMP_MODIFIER);
		case PASS_ROLL:
		case THROW_TEAM_MATE_ROLL:
			return source.<PassModifierFactory>getFactory(Factory.PASS_MODIFIER);
		case PICK_UP_ROLL:
			return source.<PickupModifierFactory>getFactory(Factory.PICKUP_MODIFIER);
		case RIGHT_STUFF_ROLL:
			return source.<RightStuffModifierFactory>getFactory(Factory.RIGHT_STUFF_MODIFIER);
		case HYPNOTIC_GAZE_ROLL:
			return source.<GazeModifierFactory>getFactory(Factory.GAZE_MODIFIER);
		default:
			return null;
		}
	}

}
