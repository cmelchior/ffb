package com.balancedbytes.games.ffb.server.step.game.start;

import java.util.HashMap;
import java.util.Map;

import com.balancedbytes.games.ffb.Card;
import com.balancedbytes.games.ffb.CardType;
import com.balancedbytes.games.ffb.GameOption;
import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.dialog.DialogBuyCardsParameter;
import com.balancedbytes.games.ffb.model.Game;
import com.balancedbytes.games.ffb.model.GameResult;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.commands.ClientCommandBuyCard;
import com.balancedbytes.games.ffb.report.ReportCardsBought;
import com.balancedbytes.games.ffb.server.CardDeck;
import com.balancedbytes.games.ffb.server.GameState;
import com.balancedbytes.games.ffb.server.step.AbstractStep;
import com.balancedbytes.games.ffb.server.step.StepAction;
import com.balancedbytes.games.ffb.server.step.StepCommandStatus;
import com.balancedbytes.games.ffb.server.step.StepId;
import com.balancedbytes.games.ffb.server.step.StepParameter;
import com.balancedbytes.games.ffb.server.step.StepParameterKey;
import com.balancedbytes.games.ffb.server.step.UtilSteps;
import com.balancedbytes.games.ffb.server.util.UtilDialog;

/**
 * Step in start game sequence to buy cards.
 * 
 * Sets stepParameter INDUCEMENT_GOLD_AWAY for all steps on the stack.
 * Sets stepParameter INDUCEMENT_GOLD_HOME for all steps on the stack.
 * 
 * @author Kalimar
 */
public final class StepBuyCards extends AbstractStep {
	
	protected int fInducementGoldHome;
	protected int fInducementGoldAway;
	
  protected boolean fCardsSelectedHome;
  protected boolean fCardsSelectedAway;
  
  protected boolean fReportedHome;
  protected boolean fReportedAway;
  
  private transient Map<CardType, CardDeck> fDeckByType;
  private transient CardType fBuyCardHome;
  private transient CardType fBuyCardAway;
  
	public StepBuyCards(GameState pGameState) {
		super(pGameState);
		fDeckByType = new HashMap<CardType, CardDeck>();
		fBuyCardHome = null;
		fBuyCardAway = null;
	}
	
	public StepId getId() {
		return StepId.BUY_CARDS;
	}
		
	@Override
	public void start() {
		super.start();
		executeStep();
	}
	
	@Override
	public StepCommandStatus handleNetCommand(NetCommand pNetCommand) {
		StepCommandStatus commandStatus = super.handleNetCommand(pNetCommand);
		if (commandStatus == StepCommandStatus.UNHANDLED_COMMAND) {
			switch (pNetCommand.getId()) {
				case CLIENT_BUY_CARD:
					ClientCommandBuyCard buyCardCommand = (ClientCommandBuyCard) pNetCommand;
					if (UtilSteps.checkCommandIsFromHomePlayer(getGameState(), buyCardCommand)) {
						fBuyCardHome = buyCardCommand.getCardType();
						if (fBuyCardHome == null) {
							fCardsSelectedHome = true;
						}
					} else {
						fBuyCardAway = buyCardCommand.getCardType();
						if (fBuyCardAway == null) {
							fCardsSelectedAway = true;
						}
					}
	        commandStatus = StepCommandStatus.EXECUTE_STEP;
	        break;
        default:
        	break;
			}
		}
		if (commandStatus == StepCommandStatus.EXECUTE_STEP) {
			executeStep();
		}
		return commandStatus;
	}

  private void executeStep() {
    Game game = getGameState().getGame();
    GameResult gameResult = game.getGameResult();
    if (game.getOptions().getOptionValue(GameOption.MAX_NR_OF_CARDS).getValue() == 0) {
    	calculateInducementGold();
    	publishParameter(new StepParameter(StepParameterKey.INDUCEMENT_GOLD_HOME, fInducementGoldHome));
    	publishParameter(new StepParameter(StepParameterKey.INDUCEMENT_GOLD_AWAY, fInducementGoldAway));
    	getResult().setNextAction(StepAction.NEXT_STEP);
    	return;
    }
    buildDecks();
    if (fBuyCardHome != null) {
    	fInducementGoldHome -= fBuyCardHome.getPrice();
    	CardDeck deck = fDeckByType.get(fBuyCardHome);
    	Card card = getGameState().getDiceRoller().drawCard(deck);
    	game.getTurnDataHome().getInducementSet().addAvailableCard(card);
    	fBuyCardHome = null;
    } else if (fBuyCardAway != null) {
    	fInducementGoldAway -= fBuyCardAway.getPrice();
    	CardDeck deck = fDeckByType.get(fBuyCardAway);
    	Card card = getGameState().getDiceRoller().drawCard(deck);
    	game.getTurnDataAway().getInducementSet().addAvailableCard(card);
    	fBuyCardAway = null;
    } else {
      if (!fCardsSelectedHome && !fCardsSelectedAway) {
      	calculateInducementGold();
    	  fInducementGoldHome += game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue() ;
    	  fInducementGoldAway += game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue() ;
      }
      if (fInducementGoldHome < CardType.getMinimumPrice()) {
      	fCardsSelectedHome = true;
      }
      if (fInducementGoldAway < CardType.getMinimumPrice()) {
      	fCardsSelectedAway = true;
      }
      if (fCardsSelectedHome && !fReportedHome)  {
      	fReportedHome = true;
      	int totalCostHome = 0;
      	Card[] cardsHome = game.getTurnDataHome().getInducementSet().getAllCards();
      	for (Card card : cardsHome) {
      		totalCostHome += card.getType().getPrice();
      	}
      	gameResult.getTeamResultHome().setPettyCashUsed(Math.max(0, totalCostHome - game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue()));
      	getResult().addReport(new ReportCardsBought(game.getTeamHome().getId(), cardsHome.length, totalCostHome));
      }
      if (fCardsSelectedAway && !fReportedAway)  {
      	fReportedAway = true;
      	int totalCostAway = 0;
      	Card[] cardsAway = game.getTurnDataAway().getInducementSet().getAllCards();
      	for (Card card : cardsAway) {
      		totalCostAway += card.getType().getPrice();
      	}
      	gameResult.getTeamResultAway().setPettyCashUsed(Math.max(0, totalCostAway - game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue()));
      	getResult().addReport(new ReportCardsBought(game.getTeamAway().getId(), cardsAway.length, totalCostAway));
      }
      if (!fCardsSelectedHome && !fCardsSelectedAway) {
        int homeTV = gameResult.getTeamResultHome().getTeamValue();
        int awayTV = gameResult.getTeamResultAway().getTeamValue();
        if (homeTV > awayTV) {
          UtilDialog.showDialog(getGameState(), createDialogParameter(game.getTeamHome().getId(), fInducementGoldHome));
        } else {
          UtilDialog.showDialog(getGameState(), createDialogParameter(game.getTeamAway().getId(), fInducementGoldAway));
        }
      } else if (!fCardsSelectedHome) {
        UtilDialog.showDialog(getGameState(), createDialogParameter(game.getTeamHome().getId(), fInducementGoldHome));
      } else if (!fCardsSelectedAway) {
        UtilDialog.showDialog(getGameState(), createDialogParameter(game.getTeamAway().getId(), fInducementGoldAway));
      } else {
      	fInducementGoldHome = Math.max(0, fInducementGoldHome - game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue());
      	publishParameter(new StepParameter(StepParameterKey.INDUCEMENT_GOLD_HOME, fInducementGoldHome));
      	fInducementGoldAway = Math.max(0, fInducementGoldAway - game.getOptions().getOptionValue(GameOption.FREE_CARD_CASH).getValue());      	
      	publishParameter(new StepParameter(StepParameterKey.INDUCEMENT_GOLD_AWAY, fInducementGoldAway));
      	getResult().setNextAction(StepAction.NEXT_STEP);      	
      }
    }
  }
  
  private void calculateInducementGold() {
    Game game = getGameState().getGame();
    GameResult gameResult = game.getGameResult();
    int homeTV = gameResult.getTeamResultHome().getTeamValue();
    int awayTV = gameResult.getTeamResultAway().getTeamValue();
    fInducementGoldHome = gameResult.getTeamResultHome().getPettyCashTransferred();
    fInducementGoldAway = gameResult.getTeamResultAway().getPettyCashTransferred();
    if ((awayTV > homeTV)  && ((awayTV - homeTV) > fInducementGoldHome)) {
    	fInducementGoldHome = (awayTV - homeTV);
    }
    if ((homeTV > awayTV) && ((homeTV - awayTV) > fInducementGoldAway)) {
    	fInducementGoldAway = (homeTV - awayTV);
    }
  }
  
  private void buildDecks() {
    Game game = getGameState().getGame();
  	fDeckByType.clear();
  	for (CardType type : CardType.values()) {
  		CardDeck deck = new CardDeck(type);
  		deck.build(game);
  		fDeckByType.put(type, deck);
  	}
  }
  
  private DialogBuyCardsParameter createDialogParameter(String pTeamId, int pAvailableGold) {
  	int availableCards = getGameState().getGame().getOptions().getOptionValue(GameOption.MAX_NR_OF_CARDS).getValue();
  	DialogBuyCardsParameter dialogParameter = new DialogBuyCardsParameter(pTeamId, availableCards, pAvailableGold);
  	for (CardType type : fDeckByType.keySet()) {
  		CardDeck deck = fDeckByType.get(type);
			dialogParameter.put(type, deck.size());
  	}
  	return dialogParameter;
  }
    
  public int getByteArraySerializationVersion() {
  	return 1;
  }

  @Override
  public void addTo(ByteList pByteList) {
  	super.addTo(pByteList);
  	pByteList.addInt(fInducementGoldHome);
  	pByteList.addInt(fInducementGoldAway);
  	pByteList.addBoolean(fCardsSelectedHome);
  	pByteList.addBoolean(fCardsSelectedAway);
  	pByteList.addBoolean(fReportedHome);
  	pByteList.addBoolean(fReportedAway);
  }
  
  @Override
  public int initFrom(ByteArray pByteArray) {
  	int byteArraySerializationVersion = super.initFrom(pByteArray);
  	fInducementGoldHome = pByteArray.getInt();
  	fInducementGoldAway = pByteArray.getInt();
  	fCardsSelectedHome = pByteArray.getBoolean();
  	fCardsSelectedAway = pByteArray.getBoolean();
  	fReportedHome = pByteArray.getBoolean();
  	fReportedAway = pByteArray.getBoolean();
  	return byteArraySerializationVersion;
  }
  
}
