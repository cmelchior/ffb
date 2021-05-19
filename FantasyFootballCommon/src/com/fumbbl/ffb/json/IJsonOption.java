package com.fumbbl.ffb.json;

import com.fumbbl.ffb.FactoryType.Factory;

/**
 *
 * @author Kalimar
 */
public interface IJsonOption {

	JsonObjectOption ACTING_PLAYER = new JsonObjectOption("actingPlayer");
	JsonStringOption ACTING_PLAYER_ID = new JsonStringOption("actingPlayerId");
	JsonIntOption AGILITY = new JsonIntOption("agility");
	JsonObjectOption ANIMATION = new JsonObjectOption("animation");
	JsonEnumWithNameOption ANIMATION_TYPE = new JsonEnumWithNameOption("animationType", Factory.ANIMATION_TYPE);
	JsonIntOption APOTHECARIES = new JsonIntOption("apothecaries");
	JsonBooleanOption APOTHECARY = new JsonBooleanOption("apothecary");
	JsonBooleanOption APOTHECARY_USED = new JsonBooleanOption("apothecaryUsed");
	JsonBooleanMapOption ARE_OWN_CHOICES = new JsonBooleanMapOption("areOwnChoices");
	JsonBooleanOption ARGUE_THE_CALL = new JsonBooleanOption("argueTheCall");
	JsonBooleanOption ARGUE_THE_CALL_SUCCESSFUL = new JsonBooleanOption("argueTheCallSuccessful");
	JsonBooleanOption ARMOR_BROKEN = new JsonBooleanOption("armorBroken");
	JsonArrayOption ARMOR_MODIFIERS = new JsonArrayOption("armorModifiers");
	JsonIntArrayOption ARMOR_ROLL = new JsonIntArrayOption("armorRoll");
	JsonIntOption ARMOUR = new JsonIntOption("armour");
	JsonIntOption ASSISTANT_COACHES = new JsonIntOption("assistantCoaches");
	JsonStringOption ATTACKER_ID = new JsonStringOption("attackerId");
	JsonBooleanOption ATTACKER_SELECTS = new JsonBooleanOption("attackerSelects");
	JsonIntOption AVAILABLE_CARDS = new JsonIntOption("availableCards");
	JsonIntOption AVAILABLE_GOLD = new JsonIntOption("availableGold");
	JsonBooleanOption AWAY_GAINS_RE_ROLL = new JsonBooleanOption("awayGainsReRoll");
	JsonStringArrayOption AWAY_PLAYERS_MVP = new JsonStringArrayOption("awayPlayersMvp");
	JsonStringArrayOption AWAY_PLAYERS_NOMINATED = new JsonStringArrayOption("awayPlayersNominated");
	JsonStringOption AWAY_TEXT = new JsonStringOption("awayText");
	JsonIntOption BADLY_HURT_SUFFERED = new JsonIntOption("badlyHurtSuffered");
	JsonFieldCoordinateOption BALL_COORDINATE = new JsonFieldCoordinateOption("ballCoordinate");
	JsonFieldCoordinateOption BALL_COORDINATE_END = new JsonFieldCoordinateOption("ballCoordinateEnd");
	JsonFieldCoordinateOption BALL_COORDINATE_WITH_KICK = new JsonFieldCoordinateOption("ballCoordinateWithKick");
	JsonBooleanOption BALL_IN_PLAY = new JsonBooleanOption("ballInPlay");
	JsonBooleanOption BALL_MOVING = new JsonBooleanOption("ballMoving");
	JsonBooleanArrayOption BAN_ARRAY = new JsonBooleanArrayOption("banArray");
	JsonStringOption BALL_ACTION = new JsonStringOption("ballAction");
	JsonStringOption BASE_ICON_PATH = new JsonStringOption("baseIconPath");
	JsonObjectOption BLITZ_STATE = new JsonObjectOption("blitzState");
	JsonStringOption BLITZ_STATUS = new JsonStringOption("blitzStatus");
	JsonObjectOption BLITZ_TURN_STATE = new JsonObjectOption("blitzTurnState");
	JsonBooleanOption BLITZ_USED = new JsonBooleanOption("blitzUsed");
	JsonStringOption BLOCK_KIND = new JsonStringOption("blockKind");
	JsonEnumWithNameOption BLOCK_RESULT = new JsonEnumWithNameOption("blockResult", Factory.BLOCK_RESULT);
	JsonIntArrayOption BLOCK_ROLL = new JsonIntArrayOption("blockRoll");
	JsonIntOption BLOCK_ROLL_ID = new JsonIntOption("blockRollId");
	JsonArrayOption BLOCK_ROLLS = new JsonArrayOption("blockRolls");
	JsonIntOption BLOCKS = new JsonIntOption("blocks");
	JsonArrayOption BLOODSPOT_ARRAY = new JsonArrayOption("bloodspotArray");
	JsonIntOption BLOODWEISER_KEGS = new JsonIntOption("bloodweiserBabes");// retain old key to maintain compatibility with old replays.
	JsonBooleanOption BOMB = new JsonBooleanOption("bomb");
	JsonFieldCoordinateOption BOMB_COORDINATE = new JsonFieldCoordinateOption("bombCoordinate");
	JsonBooleanOption BOMB_MOVING = new JsonBooleanOption("bombMoving");
	JsonFieldCoordinateOption BOTTOM_RIGHT = new JsonFieldCoordinateOption("bottomRight");
	JsonBooleanOption BRAWLER_AVAILABLE = new JsonBooleanOption("brawlerAvailable");
	JsonIntOption BRAWLER_COUNT = new JsonIntOption("brawlerCount");
	JsonIntOption BRAWLER_OPTIONS = new JsonIntOption("brawlerOptions");
	JsonBooleanOption CAN_BUY_CARDS = new JsonBooleanOption("canBuyCards");
	JsonEnumWithNameOption CARD = new JsonEnumWithNameOption("card", Factory.CARD);
	JsonStringArrayOption CARDS = new JsonStringArrayOption("cards");
	JsonStringArrayOption CARDS_DEACTIVATED = new JsonStringArrayOption("cardsDeactivated");
	JsonIntOption CARDS_PRICE = new JsonIntOption("cardsPrice");
	JsonStringArrayOption CARDS_USED = new JsonStringArrayOption("cardsUsed");
	JsonEnumWithNameOption CARD_EFFECT = new JsonEnumWithNameOption("cardEffect", Factory.CARD_EFFECT);
	JsonStringArrayOption CARD_EFFECTS = new JsonStringArrayOption("cardEffects");
	JsonStringArrayOption CARDS_ACTIVE = new JsonStringArrayOption("cardsActive");
	JsonStringArrayOption CARDS_AVAILABLE = new JsonStringArrayOption("cardsAvailable");
	JsonObjectOption CARD_CHOICES = new JsonObjectOption("cardChoices");
	JsonObjectOption CARD_CHOICE_INITIAL = new JsonObjectOption("cardChoiceFirst");
	JsonObjectOption CARD_CHOICE_REROLLED = new JsonObjectOption("cardChoiceRerolled");
	JsonEnumWithNameOption CARD_CHOICE_ONE = new JsonEnumWithNameOption("cardChoice1", Factory.CARD);
	JsonEnumWithNameOption CARD_CHOICE_TWO = new JsonEnumWithNameOption("cardChoice2", Factory.CARD);
	JsonEnumWithNameOption CARD_CHOICE_TYPE = new JsonEnumWithNameOption("cardChoiceType", Factory.CARD_TYPE);
	JsonStringOption CARD_SELECTION = new JsonStringOption("cardSelection");
	JsonEnumWithNameOption CARD_TYPE = new JsonEnumWithNameOption("cardType", Factory.CARD_TYPE);
	JsonIntOption CASUALTIES = new JsonIntOption("casualties");
	JsonArrayOption CASUALTY_MODIFIERS = new JsonArrayOption("casualtyModifiers");
	JsonIntArrayOption CASUALTY_ROLL = new JsonIntArrayOption("casualtyRoll");
	JsonIntArrayOption CASUALTY_ROLL_DECAY = new JsonIntArrayOption("casualtyRollDecay");
	JsonStringOption CATCHER_ID = new JsonStringOption("catcherId");
	JsonStringOption CHALLENGE = new JsonStringOption("challenge");
	JsonIntOption CHEERLEADERS = new JsonIntOption("cheerleaders");
	JsonBooleanOption CHOICE_FOLLOWUP = new JsonBooleanOption("choiceFollowup");
	JsonBooleanOption CHOICE_HEADS = new JsonBooleanOption("choiceHeads");
	JsonBooleanOption CHOICE_RECEIVE = new JsonBooleanOption("choiceReceive");
	JsonStringOption CHOOSING_TEAM_ID = new JsonStringOption("choosingTeamId");
	JsonEnumWithNameOption CLIENT_MODE = new JsonEnumWithNameOption("clientMode", Factory.CLIENT_MODE);
	JsonStringArrayOption CLIENT_PROPERTY_NAMES = new JsonStringArrayOption("clientPropertyNames");
	JsonStringArrayOption CLIENT_PROPERTY_VALUES = new JsonStringArrayOption("clientPropertyValues");
	JsonEnumWithNameOption CLIENT_STATE_ID = new JsonEnumWithNameOption("clientStateId", Factory.CLIENT_STATE_ID);
	JsonStringOption CLIENT_VERSION = new JsonStringOption("clientVersion");
	JsonStringOption COACH = new JsonStringOption("coach");
	JsonBooleanOption COACH_BANNED = new JsonBooleanOption("coachBanned");
	JsonBooleanOption COIN_CHOICE_HEADS = new JsonBooleanOption("coinChoiceHeads");
	JsonBooleanOption COIN_THROW_HEADS = new JsonBooleanOption("coinThrowHeads");
	JsonArrayOption COMMAND_ARRAY = new JsonArrayOption("commandArray");
	JsonIntOption COMMAND_NR = new JsonIntOption("commandNr");
	JsonIntOption COMPLETIONS = new JsonIntOption("completions");
	JsonEnumWithNameOption CONCEDE_GAME_STATUS = new JsonEnumWithNameOption("concedeGameStatus", Factory.CONCEDE_GAME_STATUS);
	JsonBooleanOption CONCEDED = new JsonBooleanOption("conceded");
	JsonStringOption CONCEDING_TEAM_ID = new JsonStringOption("concedingTeamId");
	JsonBooleanOption CONCESSION_POSSIBLE = new JsonBooleanOption("concessionPossible");
	JsonEnumWithNameOption CONFUSION_SKILL = new JsonEnumWithNameOption("confusionSkill", Factory.SKILL);
	JsonFieldCoordinateOption COORDINATE = new JsonFieldCoordinateOption("coordinate");
	JsonFieldCoordinateOption COORDINATE_FROM = new JsonFieldCoordinateOption("coordinateFrom");
	JsonFieldCoordinateArrayOption COORDINATES_TO = new JsonFieldCoordinateArrayOption("coordinatesTo");
	JsonIntOption COST = new JsonIntOption("cost");
	JsonIntOption CURRENT_MOVE = new JsonIntOption("currentMove");
	JsonIntOption CURRENT_SPPS = new JsonIntOption("currentSpps");
	JsonIntOption DEDICATED_FANS = new JsonIntOption("dedicatedFans");
	JsonIntOption DEDICATED_FANS_ROLL = new JsonIntOption("dedicatedFansRoll");
	JsonIntOption DEDICATED_FANS_RESULT = new JsonIntOption("dedicatedFansResult");
	JsonBooleanOption DEFECTING = new JsonBooleanOption("defecting");
	JsonBooleanArrayOption DEFECTING_ARRAY = new JsonBooleanArrayOption("defectingArray");
	JsonEnumWithNameOption DEFENDER_ACTION = new JsonEnumWithNameOption("defenderAction", Factory.PLAYER_ACTION);
	JsonStringOption DEFENDER_ID = new JsonStringOption("defenderId");
	JsonStringArrayOption DESCRIPTIONS = new JsonStringArrayOption("descriptions");
	JsonEnumWithNameOption DIALOG_ID = new JsonEnumWithNameOption("dialogId", Factory.DIALOG_ID);
	JsonObjectOption DIALOG_PARAMETER = new JsonObjectOption("dialogParameter");
	JsonArrayOption DICE_DECORATION_ARRAY = new JsonArrayOption("diceDecorationArray");
	JsonIntOption DICE_INDEX = new JsonIntOption("diceIndex");
	JsonEnumWithNameOption DIRECTION = new JsonEnumWithNameOption("direction", Factory.DIRECTION);
	JsonArrayOption DIRECTION_ARRAY = new JsonArrayOption("directionArray");
	JsonIntOption DIRECTION_ROLL = new JsonIntOption("directionRoll");
	JsonStringOption DISPLAY_NAME = new JsonStringOption("displayName");
	JsonIntOption DISTANCE = new JsonIntOption("distance");
	JsonIntArrayOption DISTANCE_ROLL = new JsonIntArrayOption("distanceRoll");
	JsonStringOption DIVISION = new JsonStringOption("division");
	JsonBooleanOption DODGING = new JsonBooleanOption("dodging");
	JsonFieldCoordinateOption END_COORDINATE = new JsonFieldCoordinateOption("endCoordinate");
	JsonIntOption ENTROPY = new JsonIntOption("entropy");
	JsonBooleanOption EXHAUSTED = new JsonBooleanOption("exhausted");
	JsonBooleanOption EXPLODES = new JsonBooleanOption("explodes");
	JsonIntOption FAME = new JsonIntOption("fame");
	JsonIntOption FAME_AWAY = new JsonIntOption("fameAway");
	JsonIntOption FAME_HOME = new JsonIntOption("fameHome");
	JsonIntOption FAN_FACTOR = new JsonIntOption("fanFactor");
	JsonIntOption FAN_FACTOR_MODIFIER = new JsonIntOption("fanFactorModifier");
	JsonIntOption FAN_FACTOR_MODIFIER_AWAY = new JsonIntOption("fanFactorModifierAway");
	JsonIntOption FAN_FACTOR_MODIFIER_HOME = new JsonIntOption("fanFactorModifierHome");
	JsonIntArrayOption FAN_FACTOR_ROLL_AWAY = new JsonIntArrayOption("fanFactorRollAway");
	JsonIntArrayOption FAN_FACTOR_ROLL_HOME = new JsonIntArrayOption("fanFactorRollHome");
	JsonObjectOption FIELD_COORDINATE = new JsonObjectOption("fieldCoordinate");
	JsonArrayOption FIELD_COORDINATES = new JsonArrayOption("fieldCoordinates");
	JsonObjectOption FIELD_COORDINATE_THROWER = new JsonObjectOption("fieldCoordinateThrower");
	JsonIntOption FIELD_COORDINATE_X = new JsonIntOption("x");
	JsonIntOption FIELD_COORDINATE_Y = new JsonIntOption("y");
	JsonArrayOption FIELD_MARKER_ARRAY = new JsonArrayOption("fieldMarkerArray");
	JsonObjectOption FIELD_MODEL = new JsonObjectOption("fieldModel");
	JsonDateOption FINISHED = new JsonDateOption("finished");
	JsonBooleanOption FIRST_RUN = new JsonBooleanOption("firstRun");
	JsonBooleanOption FIRST_TURN_AFTER_KICKOFF = new JsonBooleanOption("firstTurnAfterKickoff");
	JsonBooleanOption FOUL_USED = new JsonBooleanOption("foulUsed");
	JsonBooleanOption FOULING_PLAYER_BANNED = new JsonBooleanOption("foulingPlayerBanned");
	JsonIntOption FOULS = new JsonIntOption("fouls");
	JsonBooleanOption FUMBLE = new JsonBooleanOption("fumble");
	JsonBooleanOption FUMBLEROOSKIE_PENDING = new JsonBooleanOption("fumblerooskiePending");
	JsonObjectOption GAME = new JsonObjectOption("game");
	JsonLongOption GAME_ID = new JsonLongOption("gameId");
	JsonObjectOption GAME_LIST = new JsonObjectOption("gameList");
	JsonArrayOption GAME_LIST_ENTRIES = new JsonArrayOption("gameListEntries");
	JsonStringOption GAME_NAME = new JsonStringOption("gameName");
	JsonArrayOption GAME_OPTION_ARRAY = new JsonArrayOption("gameOptionArray");
	JsonEnumWithNameOption GAME_OPTION_ID = new JsonEnumWithNameOption("gameOptionId", Factory.GAME_OPTION_ID);
	JsonStringOption GAME_OPTION_VALUE = new JsonStringOption("gameOptionValue");
	JsonObjectOption GAME_OPTIONS = new JsonObjectOption("gameOptions");
	JsonObjectOption GAME_RESULT = new JsonObjectOption("gameResult");
	JsonLongOption GAME_TIME = new JsonLongOption("gameTime");
	JsonBooleanOption GOING_FOR_IT = new JsonBooleanOption("goingForIt");
	JsonIntOption GOLD = new JsonIntOption("gold");
	JsonBooleanOption GUST_OF_WIND = new JsonBooleanOption("gustOfWind");
	JsonBooleanOption HAIL_MARY_PASS = new JsonBooleanOption("hailMaryPass");
	JsonIntOption HALF = new JsonIntOption("half");
	JsonBooleanOption HAND_OVER_USED = new JsonBooleanOption("handOverUsed");
	JsonBooleanOption HAS_BLOCKED = new JsonBooleanOption("hasBlocked");
	JsonBooleanOption HAS_ENTROPY = new JsonBooleanOption("hasEntropy");
	JsonBooleanOption HAS_FED = new JsonBooleanOption("hasFed");
	JsonBooleanOption HAS_FOULED = new JsonBooleanOption("hasFouled");
	JsonBooleanOption HAS_MOVED = new JsonBooleanOption("hasMoved");
	JsonBooleanOption HAS_PASSED = new JsonBooleanOption("hasPassed");
	JsonBooleanOption HAS_USED_SECRET_WEAPON = new JsonBooleanOption("hasUsedSecretWeapon");
	JsonArrayOption HEAT_EXHAUSTION_ARRAY = new JsonArrayOption("heatExhaustionArray");
	JsonBooleanOption HOME_CHOICE = new JsonBooleanOption("homeChoice");
	JsonBooleanOption HOME_DATA = new JsonBooleanOption("homeData");
	JsonBooleanOption HOME_FIRST_OFFENSE = new JsonBooleanOption("homeFirstOffense");
	JsonBooleanOption HOME_GAINS_RE_ROLL = new JsonBooleanOption("homeGainsReRoll");
	JsonStringArrayOption HOME_PLAYERS_MVP = new JsonStringArrayOption("homePlayersMvp");
	JsonStringArrayOption HOME_PLAYERS_NOMINATED = new JsonStringArrayOption("homePlayersNominated");
	JsonBooleanOption HOME_PLAYING = new JsonBooleanOption("homePlaying");
	JsonBooleanOption HOME_TEAM = new JsonBooleanOption("homeTeam");
	JsonStringOption HOME_TEXT = new JsonStringOption("homeText");
	JsonStringArrayOption ICON_URLS_AWAY_MOVING = new JsonStringArrayOption("iconUrlsAwayMoving");
	JsonStringArrayOption ICON_URLS_AWAY_STANDING = new JsonStringArrayOption("iconUrlsAwayStanding");
	JsonStringArrayOption ICON_URLS_HOME_MOVING = new JsonStringArrayOption("iconUrlsHomeMoving");
	JsonStringArrayOption ICON_URLS_HOME_STANDING = new JsonStringArrayOption("iconUrlsHomeStanding");
	JsonArrayOption INDUCEMENT_ARRAY = new JsonArrayOption("inducementArray");
	JsonObjectOption INDUCEMENT_SET = new JsonObjectOption("inducementSet");
	JsonEnumWithNameOption INDUCEMENT_TYPE = new JsonEnumWithNameOption("inducementType", Factory.INDUCEMENT_TYPE);
	JsonStringArrayOption INDUCEMENT_TYPE_ARRAY = new JsonStringArrayOption("inducementTypeArray");
	JsonPlayerStateOption INJURY = new JsonPlayerStateOption("injury");
	JsonPlayerStateOption INJURY_DECAY = new JsonPlayerStateOption("injuryDecay");
	JsonArrayOption INJURY_DESCRIPTIONS = new JsonArrayOption("injuryDescriptions");
	JsonArrayOption INJURY_MODIFIERS = new JsonArrayOption("injuryModifiers");
	JsonIntArrayOption INJURY_ROLL = new JsonIntArrayOption("injuryRoll");
	JsonEnumWithNameOption INJURY_TYPE = new JsonEnumWithNameOption("injuryType", Factory.INJURY_TYPE);
	JsonIntOption INTERCEPTIONS = new JsonIntOption("interceptions");
	JsonFieldCoordinateOption INTERCEPTOR_COORDINATE = new JsonFieldCoordinateOption("interceptorCoordinate");
	JsonStringOption INTERCEPTOR_ID = new JsonStringOption("interceptorId");
	JsonBooleanOption IS_OWN_CHOICE = new JsonBooleanOption("isOwnChoice");
	JsonBooleanOption KICKED = new JsonBooleanOption("kicked");
	JsonStringOption KICKED_PLAYER_ID = new JsonStringOption("kickedPlayerId");
	JsonEnumWithNameOption KICKOFF_RESULT = new JsonEnumWithNameOption("kickoffResult", Factory.KICKOFF_RESULT);
	JsonIntArrayOption KICKOFF_ROLL = new JsonIntArrayOption("kickoffRoll");
	JsonArrayOption KNOCKOUT_RECOVERY_ARRAY = new JsonArrayOption("knockoutRecoveryArray");
	JsonBooleanOption KTM_USED = new JsonBooleanOption("ktmUsed");
	JsonArrayOption LASTING_INJURIES = new JsonArrayOption("lastingInjuries");
	JsonEnumWithNameOption LAST_TURN_MODE = new JsonEnumWithNameOption("lastTurnMode", Factory.TURN_MODE);
	JsonEnumWithNameOption LEADER_STATE = new JsonEnumWithNameOption("leaderState", Factory.LEADER_STATE);
	JsonBooleanOption JUMPING = new JsonBooleanOption("leaping");
	JsonBooleanOption LIMIT_REACHED = new JsonBooleanOption("limitReached");
	JsonBooleanOption LOAD_DIALOG = new JsonBooleanOption("loadDialog");
	JsonBooleanOption LOCKED = new JsonBooleanOption("locked");
	JsonStringOption LOGO_URL = new JsonStringOption("logoUrl");
	JsonIntArrayOption MASTER_CHEF_ROLL = new JsonIntArrayOption("masterChefRoll");
	JsonIntOption MAX_NR_OF_BRIBES = new JsonIntOption("maxNrOfBribes");
	JsonIntOption MAX_RE_ROLLS = new JsonIntOption("maxReRolls");
	JsonIntOption MAX_SELECTS = new JsonIntOption("maxSelects");
	JsonStringArrayOption MERCENARY_POSTION_IDS = new JsonStringArrayOption("mercenaryPositionIds");
	JsonStringArrayOption MERCENARY_SKILLS = new JsonStringArrayOption("mercenarySkills");
	JsonStringOption MESSAGE = new JsonStringOption("message");
	JsonStringArrayOption MESSAGE_ARRAY = new JsonStringArrayOption("messageArray");
	JsonIntOption MINIMUM_ROLL = new JsonIntOption("minimumRoll");
	JsonIntOption MINIMUM_ROLL_DODGE = new JsonIntOption("minimumRollDodge");
	JsonIntOption MINIMUM_ROLL_GFI = new JsonIntOption("minimumRollGfi");
	JsonIntegerMapOption MINIMUM_ROLLS = new JsonIntegerMapOption("minimumRolls");
	JsonArrayOption MODEL_CHANGE_ARRAY = new JsonArrayOption("modelChangeArray");
	JsonEnumWithNameOption MODEL_CHANGE_ID = new JsonEnumWithNameOption("modelChangeId", Factory.MODEL_CHANGE_ID);
	JsonStringOption MODEL_CHANGE_KEY = new JsonStringOption("modelChangeKey");
	JsonObjectOption MODEL_CHANGE_LIST = new JsonObjectOption("modelChangeList");
	JsonValueOption MODEL_CHANGE_VALUE = new JsonValueOption("modelChangeValue");
	JsonIntOption MODIFIER = new JsonIntOption("modifier");
	JsonArrayOption MOVE_SQUARE_ARRAY = new JsonArrayOption("moveSquareArray");
	JsonIntOption MOVEMENT = new JsonIntOption("movement");
	JsonStringOption NAME = new JsonStringOption("name");
	JsonStringOption NAME_GENERATOR = new JsonStringOption("nameGenerator");
	JsonBooleanOption NECROMANCER = new JsonBooleanOption("necromancer");
	JsonEnumWithNameOption NET_COMMAND_ID = new JsonEnumWithNameOption("netCommandId", Factory.NET_COMMAND_ID);
	JsonIntOption NR_OF_AWAY_CHOICES = new JsonIntOption("nrOfAwayChoices");
	JsonIntOption NR_OF_AWAY_MVPS = new JsonIntOption("nrOfAwayMvps");
	JsonArrayOption NR_OF_CARDS_PER_TYPE = new JsonArrayOption("nrOfCardsPerType");
	JsonIntOption NR_OF_CARDS = new JsonIntOption("nrOfCards");
	JsonIntOption NR_OF_DICE = new JsonIntOption("nrOfDice");
	JsonIntOption NR_OF_HOME_CHOICES = new JsonIntOption("nrOfHomeChoices");
	JsonIntOption NR_OF_HOME_MVPS = new JsonIntOption("nrOfHomeMvps");
	JsonIntOption NR_OF_ICONS = new JsonIntOption("nrOfIcons");
	JsonIntOption NR_OF_INDUCEMENTS = new JsonIntOption("nrOfInducements");
	JsonIntOption NR_OF_MERCENARIES = new JsonIntOption("nrOfMercenaries");
	JsonIntOption NR_OF_PLAYERS = new JsonIntOption("nrOfPlayers");
	JsonIntOption NR_OF_PLAYERS_ALLOWED = new JsonIntOption("nrOfPlayersAllowed");
	JsonIntOption NR_OF_SLOTS = new JsonIntOption("nrOfSlots");
	JsonIntOption NR_OF_STARS = new JsonIntOption("nrOfStars");
	JsonIntOption NUMBER = new JsonIntOption("number");
	JsonBooleanOption NURGLES_ROT = new JsonBooleanOption("nurglesRot");
	JsonIntOption OLD_ROLL = new JsonIntOption("oldRoll");
	JsonIntOption OPPONENT_TEAM_VALUE = new JsonIntOption("opponentTeamValue");
	JsonBooleanOption PASS_BLOCK_AVAILABLE = new JsonBooleanOption("passBlockAvailable");
	JsonFieldCoordinateOption PASS_COORDINATE = new JsonFieldCoordinateOption("passCoordinate");
	JsonBooleanOption PASS_DEVIATES = new JsonBooleanOption("passDeviates");
	JsonEnumWithNameOption PASS_RESULT = new JsonEnumWithNameOption("passResult", Factory.PASS_RESULT);
	JsonBooleanOption PASS_USED = new JsonBooleanOption("passUsed");
	JsonEnumWithNameOption PASSING_DISTANCE = new JsonEnumWithNameOption("passingDistance", Factory.PASSING_DISTANCE);
	JsonStringOption PASSWORD = new JsonStringOption("password");
	JsonIntOption PASSING = new JsonIntOption("passing");
	JsonIntOption PENALTY_SCORE_AWAY = new JsonIntOption("penaltyScoreAway");
	JsonIntOption PENALTY_SCORE_HOME = new JsonIntOption("penaltyScoreHome");
	JsonIntOption PETTY_CASH = new JsonIntOption("pettyCash");
	JsonIntOption PETTY_CASH_FROM_TV_DIFF = new JsonIntOption("pettyCashFromTvDiff");
	JsonIntOption PETTY_CASH_TRANSFERRED = new JsonIntOption("pettyCashTransferred");
	JsonIntOption PETTY_CASH_USED = new JsonIntOption("pettyCashUsed");
	JsonObjectOption PLAYER = new JsonObjectOption("player");
	JsonEnumWithNameOption PLAYER_ACTION = new JsonEnumWithNameOption("playerAction", Factory.PLAYER_ACTION);
	JsonArrayOption PLAYER_ARRAY = new JsonArrayOption("playerArray");
	JsonIntOption PLAYER_AWARDS = new JsonIntOption("playerAwards");
	JsonEnumWithNameOption PLAYER_CHOICE_MODE = new JsonEnumWithNameOption("playerChoiceMode", Factory.PLAYER_CHOICE_MODE);
	JsonStringOption PLAYER_KIND = new JsonStringOption("playerKind");
	JsonFieldCoordinateOption PLAYER_COORDINATE = new JsonFieldCoordinateOption("playerCoordinate");
	JsonFieldCoordinateArrayOption PLAYER_COORDINATES = new JsonFieldCoordinateArrayOption("playerCoordinates");
	JsonArrayOption PLAYER_DATA_ARRAY = new JsonArrayOption("playerDataArray");
	JsonEnumWithNameOption PLAYER_GENDER = new JsonEnumWithNameOption("playerGender", Factory.PLAYER_GENDER);
	JsonStringOption PLAYER_ID = new JsonStringOption("playerId");
	JsonStringOption PLAYER_ID_TOUCHDOWN = new JsonStringOption("playerIdTouchdown");
	JsonStringArrayOption PLAYER_IDS = new JsonStringArrayOption("playerIds");
	JsonStringArrayOption PLAYER_IDS_AWAY = new JsonStringArrayOption("playerIdsAway");
	JsonStringArrayOption PLAYER_IDS_HIT = new JsonStringArrayOption("playerIdsHit");
	JsonStringArrayOption PLAYER_IDS_HOME = new JsonStringArrayOption("playerIdsHome");
	JsonArrayOption PLAYER_MARKER_ARRAY = new JsonArrayOption("playerMarkerArray");
	JsonStringOption PLAYER_NAME = new JsonStringOption("playerName");
	JsonStringArrayOption PLAYER_NAMES = new JsonStringArrayOption("playerNames");
	JsonIntOption PLAYER_NR = new JsonIntOption("playerNr");
	JsonIntArrayOption PLAYER_NUMBERS = new JsonIntArrayOption("playerNumbers");
	JsonArrayOption PLAYER_POSITIONS = new JsonArrayOption("playerPositions");
	JsonArrayOption PLAYER_RESULTS = new JsonArrayOption("playerResults");
	JsonPlayerStateOption PLAYER_STATE = new JsonPlayerStateOption("playerState");
	JsonPlayerStateOption PLAYER_STATE_NEW = new JsonPlayerStateOption("playerStateNew");
	JsonPlayerStateOption PLAYER_STATE_OLD = new JsonPlayerStateOption("playerStateOld");
	JsonEnumWithNameOption PLAYER_TYPE = new JsonEnumWithNameOption("playerType", Factory.PLAYER_TYPE);
	JsonBooleanArrayOption PLAYERS_AFFECTED_AWAY = new JsonBooleanArrayOption("playersAffectedAway");
	JsonBooleanArrayOption PLAYERS_AFFECTED_HOME = new JsonBooleanArrayOption("playersAffectedHome");
	JsonFieldCoordinateMapOption PLAYERS_AT_COORDINATES = new JsonFieldCoordinateMapOption("playersAtCoordinates");
	JsonArrayOption POSITION_ARRAY = new JsonArrayOption("positionArray");
	JsonObjectOption ROSTER_POSITION = new JsonObjectOption("rosterPosition");
	JsonIntOption POSITION_ICON_INDEX = new JsonIntOption("positionIconIndex");
	JsonStringOption POSITION_ID = new JsonStringOption("positionId");
	JsonStringArrayOption POSITION_IDS = new JsonStringArrayOption("positionIds");
	JsonStringOption POSITION_NAME = new JsonStringOption("positionName");
	JsonBooleanOption PRO_RE_ROLL_OPTION = new JsonBooleanOption("proReRollOption");
	JsonObjectOption PUSHBACK = new JsonObjectOption("pushback");
	JsonEnumWithNameOption PUSHBACK_MODE = new JsonEnumWithNameOption("pushbackMode", Factory.PUSHBACK_MODE);
	JsonArrayOption PUSHBACK_SQUARE_ARRAY = new JsonArrayOption("pushbackSquareArray");
	JsonIntOption QUANTITY = new JsonIntOption("quantity");
	JsonStringOption RACE = new JsonStringOption("race");
	JsonIntOption RAISED_DEAD = new JsonIntOption("raisedDead");
	JsonStringOption RAISED_POSITION_ID = new JsonStringOption("raisedPositionId");
	JsonBooleanOption RECEIVE_CHOICE = new JsonBooleanOption("receiveChoice");
	JsonBooleanOption RECOVERING = new JsonBooleanOption("recovering");
	JsonEnumWithNameOption RECOVERING_INJURY = new JsonEnumWithNameOption("recoveringInjury", Factory.SERIOUS_INJURY);
	JsonIntOption REPLAY_TO_COMMAND_NR = new JsonIntOption("replayToCommandNr");
	JsonEnumWithNameOption REPORT_ID = new JsonEnumWithNameOption("reportId", Factory.REPORT_ID);
	JsonBooleanOption REPORT_INJURIES_APO = new JsonBooleanOption("reportInjuriesApo");
	JsonBooleanOption REPORT_INJURIES_IGOR = new JsonBooleanOption("reportInjuriesIgor");
	JsonObjectOption REPORT_LIST = new JsonObjectOption("reportList");
	JsonArrayOption REPORTS = new JsonArrayOption("reports");
	JsonBooleanOption RE_ROLLED = new JsonBooleanOption("reRolled");
	JsonEnumWithNameOption RE_ROLLED_ACTION = new JsonEnumWithNameOption("reRolledAction", Factory.RE_ROLLED_ACTION);
	JsonStringArrayOption RE_ROLL_AVAILABLE_AGAINST = new JsonStringArrayOption("reRollAvailableFor");
	JsonIntOption RE_ROLL_COST = new JsonIntOption("reRollCost");
	JsonBooleanOption RE_ROLL_INJURY = new JsonBooleanOption("reRollInjury");
	JsonEnumWithNameOption RE_ROLL_SOURCE = new JsonEnumWithNameOption("reRollSource", Factory.RE_ROLL_SOURCE);
	JsonBooleanOption RE_ROLL_USED = new JsonBooleanOption("reRollUsed");
	JsonIntOption RE_ROLLS = new JsonIntOption("reRolls");
	JsonIntOption RE_ROLLS_BRILLIANT_COACHING_ONE_DRIVE = new JsonIntOption("rerollBrilliantCoachingOneDrive");
	JsonIntOption RE_ROLLS_LEFT_AWAY = new JsonIntOption("reRollsLeftAway");
	JsonIntOption RE_ROLLS_LEFT_HOME = new JsonIntOption("reRollsLeftHome");
	JsonIntOption RE_ROLLS_STOLEN = new JsonIntOption("reRollsStolen");
	JsonStringOption RIOTOUS_POSITION_ID = new JsonStringOption("riotousPositionId");
	JsonIntOption RIOTOUS_AMOUNT = new JsonIntOption("riotousAmount");
	JsonIntArrayOption RIOTOUS_ROLL = new JsonIntArrayOption("riotousRoll");
	JsonIntOption RIP_SUFFERED = new JsonIntOption("ripSuffered");
	JsonIntOption ROLL = new JsonIntOption("roll");
	JsonStringOption ROLL_COUNT = new JsonStringOption("rollCount");
	JsonIntOption ROLL_SCATTER_DIRECTION = new JsonIntOption("rollScatterDirection");
	JsonIntOption ROLL_SCATTER_DISTANCE = new JsonIntOption("rollScatterDistance");
	JsonIntOption ROLL_AWAY = new JsonIntOption("rollAway");
	JsonIntOption ROLL_HOME = new JsonIntOption("rollHome");
	JsonArrayOption ROLL_MODIFIERS = new JsonArrayOption("rollModifiers");
	JsonIntArrayOption ROLLS = new JsonIntArrayOption("rolls");
	JsonIntArrayOption ROLLS_AWAY = new JsonIntArrayOption("rollsAway");
	JsonIntArrayOption ROLLS_HOME = new JsonIntArrayOption("rollsHome");
	JsonObjectOption ROSTER = new JsonObjectOption("roster");
	JsonStringOption ROSTER_ID = new JsonStringOption("rosterId");
	JsonStringOption ROSTER_NAME = new JsonStringOption("rosterName");
	JsonIntOption RUSHING = new JsonIntOption("rushing");
	JsonBooleanOption SAFE_THROW_HOLD = new JsonBooleanOption("safeThrowHold");
	JsonEnumWithNameOption SCATTER_DIRECTION = new JsonEnumWithNameOption("scatterDirection", Factory.DIRECTION);
	JsonDateOption SCHEDULED = new JsonDateOption("scheduled");
	JsonIntOption SCORE = new JsonIntOption("score");
	JsonBooleanOption SELECTED = new JsonBooleanOption("selected");
	JsonIntOption SELECTED_INDEX = new JsonIntOption("selectedIndex");
	JsonArrayOption SELECTED_BLOCK_TARGETS = new JsonArrayOption("selectedBlockTargets");
	JsonStringOption SEND_TO_BOX_BY_PLAYER_ID = new JsonStringOption("sendToBoxByPlayerId");
	JsonIntOption SEND_TO_BOX_HALF = new JsonIntOption("sendToBoxHalf");
	JsonEnumWithNameOption SEND_TO_BOX_REASON = new JsonEnumWithNameOption("sendToBoxReason", Factory.SEND_TO_BOX_REASON);
	JsonIntOption SEND_TO_BOX_TURN = new JsonIntOption("sendToBoxTurn");
	JsonEnumWithNameOption SERIOUS_INJURY = new JsonEnumWithNameOption("seriousInjury", Factory.SERIOUS_INJURY);
	JsonEnumWithNameOption SERIOUS_INJURY_DECAY = new JsonEnumWithNameOption("seriousInjuryDecay", Factory.SERIOUS_INJURY);
	JsonEnumWithNameOption SERIOUS_INJURY_NEW = new JsonEnumWithNameOption("seriousInjuryNew", Factory.SERIOUS_INJURY);
	JsonEnumWithNameOption SERIOUS_INJURY_OLD = new JsonEnumWithNameOption("seriousInjuryOld", Factory.SERIOUS_INJURY);
	JsonIntOption SERIOUS_INJURY_SUFFERED = new JsonIntOption("seriousInjurySuffered");
	JsonEnumWithNameOption SERVER_STATUS = new JsonEnumWithNameOption("serverStatus", Factory.SERVER_STATUS);
	JsonStringOption SERVER_VERSION = new JsonStringOption("serverVersion");
	JsonStringArrayOption SETTING_NAMES = new JsonStringArrayOption("settingNames");
	JsonStringArrayOption SETTING_VALUES = new JsonStringArrayOption("settingValues");
	JsonStringArrayOption SETUP_ERRORS = new JsonStringArrayOption("setupErrors");
	JsonStringOption SETUP_NAME = new JsonStringOption("setupName");
	JsonStringArrayOption SETUP_NAMES = new JsonStringArrayOption("setupNames");
	JsonBooleanOption SETUP_OFFENSE = new JsonBooleanOption("setupOffense");
	JsonStringOption SHORTHAND = new JsonStringOption("shorthand");
	JsonBooleanOption SHOW_NAME_IN_REPORT = new JsonBooleanOption("showNameInReport");
	JsonEnumWithNameOption SKILL = new JsonEnumWithNameOption("skill", Factory.SKILL);
	JsonArrayOption SKILL_ARRAY = new JsonArrayOption("skillArray");
	JsonArrayOption SKILL_CATEGORIES_DOUBLE = new JsonArrayOption("skillCategoriesDouble");
	JsonArrayOption SKILL_CATEGORIES_NORMAL = new JsonArrayOption("skillCategoriesNormal");
	JsonEnumWithNameOption SKILL_USE = new JsonEnumWithNameOption("skillUse", Factory.SKILL_USE);
	JsonLegacySkillValuesOption SKILL_VALUES = new JsonLegacySkillValuesOption("skillValues");
	JsonSkillValuesMapOption SKILL_VALUES_MAP = new JsonSkillValuesMapOption("skillValuesMap");
	JsonBooleanOption SKILL_USED = new JsonBooleanOption("skillUsed");
	JsonIntArrayOption SLOTS = new JsonIntArrayOption("slots");
	JsonEnumWithNameOption SOUND = new JsonEnumWithNameOption("sound", Factory.SOUND_ID);
	JsonEnumWithNameOption SPECIAL_EFFECT = new JsonEnumWithNameOption("specialEffect", Factory.SPECIAL_EFFECT);
	JsonIntArrayOption SPECTATOR_ROLL_AWAY = new JsonIntArrayOption("spectatorRollAway");
	JsonIntArrayOption SPECTATOR_ROLL_HOME = new JsonIntArrayOption("spectatorRollHome");
	JsonIntOption SPECTATORS = new JsonIntOption("spectators");
	JsonIntOption SPECTATORS_AWAY = new JsonIntOption("spectatorsAway");
	JsonIntOption SPECTATORS_HOME = new JsonIntOption("spectatorsHome");
	JsonIntOption SPIRALLING_EXPENSES = new JsonIntOption("spirallingExpenses");
	JsonBooleanOption STANDING_UP = new JsonBooleanOption("standingUp");
	JsonStringOption STAR_PLAYER_NAME = new JsonStringOption("starPlayerName");
	JsonStringArrayOption STAR_PLAYER_POSTION_IDS = new JsonStringArrayOption("starPlayerPositionIds");
	JsonFieldCoordinateOption START_COORDINATE = new JsonFieldCoordinateOption("startCoordinate");
	JsonDateOption STARTED = new JsonDateOption("started");
	JsonBooleanOption STAYS_ON_PITCH = new JsonBooleanOption("staysOnPitch");
	JsonStringArrayOption STEP_PARAMETER_KEYS = new JsonStringArrayOption("stepParameterKeys");
	JsonObjectOption STEP_STATE = new JsonObjectOption("stepState");
	JsonIntOption STRENGTH = new JsonIntOption("strength");
	JsonBooleanOption SUCCESSFUL = new JsonBooleanOption("successful");
	JsonBooleanOption SUCCESSFUL_DAUNTLESS = new JsonBooleanOption("successfulDauntless");
	JsonBooleanOption SUFFERING_ANIMOSITY = new JsonBooleanOption("sufferingAnimosity");
	JsonBooleanOption SUFFERING_BLOODLUST = new JsonBooleanOption("sufferingBloodlust");
	JsonBooleanOption SUPPRESS_EXTRA_EFFECT_HANDLING = new JsonBooleanOption("suppressExtraEffectHandling");
	JsonIntOption SWARMING_PLAYER_ACTUAL = new JsonIntOption("swarmingPlayerActual");
	JsonIntOption SWARMING_PLAYER_ALLOWED = new JsonIntOption("swarmingPlayerAllowed");
	JsonIntOption SWARMING_PLAYER_AMOUNT = new JsonIntOption("swarmingPlayerAmount");
	JsonStringOption TALK = new JsonStringOption("talk");
	JsonStringArrayOption TALKS = new JsonStringArrayOption("talks");
	JsonFieldCoordinateOption TARGET_COORDINATE = new JsonFieldCoordinateOption("targetCoordinate");
	JsonObjectOption TEAM_AWAY = new JsonObjectOption("teamAway");
	JsonStringOption TEAM_AWAY_COACH = new JsonStringOption("teamAwayCoach");
	JsonStringOption TEAM_AWAY_ID = new JsonStringOption("teamAwayId");
	JsonStringOption TEAM_AWAY_NAME = new JsonStringOption("teamAwayName");
	JsonObjectOption TEAM_HOME = new JsonObjectOption("teamHome");
	JsonStringOption TEAM_HOME_COACH = new JsonStringOption("teamHomeCoach");
	JsonStringOption TEAM_HOME_ID = new JsonStringOption("teamHomeId");
	JsonStringOption TEAM_HOME_NAME = new JsonStringOption("teamHomeName");
	JsonStringOption TEAM_ID = new JsonStringOption("teamId");
	JsonObjectOption TEAM_LIST = new JsonObjectOption("teamList");
	JsonArrayOption TEAM_LIST_ENTRIES = new JsonArrayOption("teamListEntries");
	JsonStringOption TEAM_NAME = new JsonStringOption("teamName");
	JsonBooleanOption TEAM_RE_ROLL_OPTION = new JsonBooleanOption("teamReRollOption");
	JsonObjectOption TEAM_RESULT_AWAY = new JsonObjectOption("teamResultAway");
	JsonObjectOption TEAM_RESULT_HOME = new JsonObjectOption("teamResultHome");
	JsonEnumWithNameOption TEAM_STATUS = new JsonEnumWithNameOption("teamStatus", Factory.TEAM_STATUS);
	JsonIntOption TEAM_VALUE = new JsonIntOption("teamValue");
	JsonStringOption TEAM_WITH_POSITION_ID = new JsonStringOption("teamWithPositionId");
	JsonTemporaryModifiersMapOption TEMPORARY_MODIFIERS_MAP = new JsonTemporaryModifiersMapOption("temporaryModifiersMap");
	JsonSkillPropertiesMapOption TEMPORARY_PROPERTIES_MAP = new JsonSkillPropertiesMapOption("temporaryPropertiesMap");
	JsonSkillWithValuesMapOption TEMPORARY_SKILL_MAP = new JsonSkillWithValuesMapOption("temporarySkillsMap");
	JsonIntArrayOption TENTACLE_ROLL = new JsonIntArrayOption("tentacleRoll");
	JsonBooleanOption TESTING = new JsonBooleanOption("testing");
	JsonStringOption TEXT = new JsonStringOption("text");
	JsonBooleanOption THRALL = new JsonBooleanOption("thrall");
	JsonEnumWithNameOption THROWER_ACTION = new JsonEnumWithNameOption("throwerAction", Factory.PLAYER_ACTION);
	JsonStringOption THROWER_ID = new JsonStringOption("throwerId");
	JsonStringOption THROWN_PLAYER_ID = new JsonStringOption("thrownPlayerId");
	JsonBooleanOption THROW_TEAM_MATE = new JsonBooleanOption("throwTeamMate");
	JsonBooleanOption TIMEOUT_ENFORCED = new JsonBooleanOption("timeoutEnforced");
	JsonBooleanOption TIMEOUT_POSSIBLE = new JsonBooleanOption("timeoutPossible");
	JsonLongOption TIMESTAMP = new JsonLongOption("timestamp");
	JsonFieldCoordinateOption TOP_LEFT = new JsonFieldCoordinateOption("topLeft");
	JsonIntOption TOTAL_NR_OF_COMMANDS = new JsonIntOption("totalNrOfCommands");
	JsonIntOption TOUCHDOWNS = new JsonIntOption("touchdowns");
	JsonArrayOption TRACK_NUMBER_ARRAY = new JsonArrayOption("trackNumberArray");
	JsonIntOption TREASURY = new JsonIntOption("treasury");
	JsonIntOption TREASURY_USED_ON_INDUCEMENTS = new JsonIntOption("treasuryUsedOnInducements");
	JsonObjectOption TURN_DATA_AWAY = new JsonObjectOption("turnDataAway");
	JsonObjectOption TURN_DATA_HOME = new JsonObjectOption("turnDataHome");
	JsonEnumWithNameOption TURN_MODE = new JsonEnumWithNameOption("turnMode", Factory.TURN_MODE);
	JsonIntOption TURN_MODIFIER = new JsonIntOption("turnModifier");
	JsonIntOption TURN_NR = new JsonIntOption("turnNr");
	JsonBooleanOption TURN_STARTED = new JsonBooleanOption("turnStarted");
	JsonLongOption TURN_TIME = new JsonLongOption("turnTime");
	JsonIntOption TURNS_PLAYED = new JsonIntOption("turnsPlayed");
	JsonBooleanOption UNDEAD = new JsonBooleanOption("undead");
	JsonArrayOption UNZAP_ARRAY = new JsonArrayOption("unzapArray");
	JsonStringOption UPLOAD_STATUS = new JsonStringOption("uploadStatus");
	JsonStringOption URL_ICON_SET = new JsonStringOption("urlIconSet");
	JsonStringOption URL_PORTRAIT = new JsonStringOption("urlPortrait");
	JsonArrayOption USED_SKILLS = new JsonArrayOption("usedSkills");
	JsonStringArrayOption USER_SETTING_NAMES = new JsonStringArrayOption("userSettingNames");
	JsonStringArrayOption USER_SETTING_VALUES = new JsonStringArrayOption("userSettingValues");
	JsonBooleanOption USED = new JsonBooleanOption("used");
	JsonIntOption USES = new JsonIntOption("uses");
	JsonBooleanOption USES_A_TEAM_REROLL = new JsonBooleanOption("usesATeamReroll");
	JsonBooleanOption USING_CHAINSAW = new JsonBooleanOption("usingChainsaw");
	JsonBooleanOption USING_STAB = new JsonBooleanOption("usingStab");
	JsonBooleanOption USING_VOMIT = new JsonBooleanOption("usingVomit");
	JsonIntOption VALUE = new JsonIntOption("value");
	JsonStringOption VICTIM_ID = new JsonStringOption("victimId");
	JsonBooleanOption WAITING_FOR_OPPONENT = new JsonBooleanOption("waitingForOpponent");
	JsonEnumWithNameOption WEATHER = new JsonEnumWithNameOption("weather", Factory.WEATHER);
	JsonIntArrayOption WEATHER_ROLL = new JsonIntArrayOption("weatherRoll");
	JsonIntOption WINNINGS = new JsonIntOption("winnings");
	JsonIntOption WINNINGS_AWAY = new JsonIntOption("winningsAway");
	JsonIntOption WINNINGS_HOME = new JsonIntOption("winningsHome");
	JsonIntOption WINNINGS_ROLL_AWAY = new JsonIntOption("winningsRollAway");
	JsonIntOption WINNINGS_ROLL_HOME = new JsonIntOption("winningsRollHome");
	JsonBooleanOption WITH_BALL = new JsonBooleanOption("withBall");
	JsonBooleanOption WIZARD_AVAILABLE = new JsonBooleanOption("wizardAvailable");
	JsonEnumWithNameOption WIZARD_SPELL = new JsonEnumWithNameOption("wizardSpell", Factory.SPECIAL_EFFECT);

}
