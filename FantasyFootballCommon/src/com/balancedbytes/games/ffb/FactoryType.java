package com.balancedbytes.games.ffb;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface FactoryType {

	public enum FactoryContext {
		APPLICATION, GAME
	}
	
	public enum Factory {
		NET_COMMAND_ID(FactoryContext.APPLICATION),
		CLIENT_MODE(FactoryContext.APPLICATION), 
		
		ANIMATION_TYPE, APOTHECARY_MODE, APOTHECARY_STATUS, BLOCK_RESULT, CARD, CARD_EFFECT,
		CARD_TYPE, CATCH_MODIFIER, CATCH_SCATTER_THROWIN_MODE, CLIENT_STATE_ID,
		CONCEDE_GAME_STATUS, DIALOG_ID, DIRECTION, DODGE_MODIFIER, GAME_OPTION_ID, GAME_STATUS,
		GAZE_MODIFIER, GO_FOR_IT_MODIFIER, INDUCEMENT_PHASE, INDUCEMENT_TYPE, INJURY_MODIFIER,
		INJURY_TYPE, INTERCEPTION_MODIFIER, KICKOFF_RESULT, LEADER_STATE, LEAP_MODIFIER,
		MODEL_CHANGE_DATA_TYPE, MODEL_CHANGE_ID, PASSING_DISTANCE, PASS_MODIFIER, PICKUP_MODIFIER,
		PLAYER_ACTION, PLAYER_CHOICE_MODE, PLAYER_GENDER, PLAYER_TYPE, PUSHBACK_MODE,
		REPORT_ID, RE_ROLLED_ACTION, RE_ROLL_SOURCE, RIGHT_STUFF_MODIFIER, SEND_TO_BOX_REASON, SERIOUS_INJURY, SERVER_STATUS,
		SKILL, SKILL_CATEGORY, SKILL_USE, SOUND_ID, SPECIAL_EFFECT, STEP_ACTION, STEP_ID,
		TEAM_STATUS, TURN_MODE, WEATHER;
		
		public FactoryContext context;
		
		private Factory() {
			this(FactoryContext.GAME);
		}
		private Factory(FactoryContext context) {
			this.context = context;
		}
	}
	
	Factory value();
}
