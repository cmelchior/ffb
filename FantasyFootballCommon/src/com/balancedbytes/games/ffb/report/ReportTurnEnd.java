package com.balancedbytes.games.ffb.report;

import java.util.ArrayList;
import java.util.List;

import com.balancedbytes.games.ffb.HeatExhaustion;
import com.balancedbytes.games.ffb.KnockoutRecovery;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.model.Player;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;



/**
 * 
 * @author Kalimar
 */
public class ReportTurnEnd implements IReport {

  private String fPlayerIdTouchdown;
  private List<KnockoutRecovery> fKnockoutRecoveries;
  private List<HeatExhaustion> fHeatExhaustions;
  private List<Player> unzappedPlayers;
  
  public ReportTurnEnd() {
    fKnockoutRecoveries = new ArrayList<KnockoutRecovery>();
    fHeatExhaustions = new ArrayList<HeatExhaustion>();
    unzappedPlayers = new ArrayList<Player>();
  }

  public ReportTurnEnd(String pPlayerIdTouchdown, KnockoutRecovery[] pKnockoutRecoveries, HeatExhaustion[] pHeatExhaustions, List<Player> unzappedPlayers) {
    this();
    fPlayerIdTouchdown = pPlayerIdTouchdown;
    add(pKnockoutRecoveries);
    add(pHeatExhaustions);
    this.unzappedPlayers.addAll(unzappedPlayers);
  }
  
  public ReportId getId() {
    return ReportId.TURN_END;
  }
  
  public String getPlayerIdTouchdown() {
    return fPlayerIdTouchdown;
  }
    
  public KnockoutRecovery[] getKnockoutRecoveries() {
    return fKnockoutRecoveries.toArray(new KnockoutRecovery[fKnockoutRecoveries.size()]);
  }
  
  private void add(KnockoutRecovery pKnockoutRecovery) {
    if (pKnockoutRecovery != null) {
      fKnockoutRecoveries.add(pKnockoutRecovery);
    }
  }
  
  private void add(KnockoutRecovery[] pKnockoutRecoveries) {
    if (ArrayTool.isProvided(pKnockoutRecoveries)) {
      for (KnockoutRecovery knockoutRecovery : pKnockoutRecoveries) {
        add(knockoutRecovery);
      }
    }
  }

  public List<Player> getUnzappedPlayers() {
    return new ArrayList<Player>(unzappedPlayers);
  }

  public HeatExhaustion[] getHeatExhaustions() {
    return fHeatExhaustions.toArray(new HeatExhaustion[fHeatExhaustions.size()]);
  }
  
  private void add(HeatExhaustion pHeatExhaustion) {
    if (pHeatExhaustion != null) {
      fHeatExhaustions.add(pHeatExhaustion);
    }
  }
  
  private void add(HeatExhaustion[] pHeatExhaustions) {
    if (ArrayTool.isProvided(pHeatExhaustions)) {
      for (HeatExhaustion heatExhaustion : pHeatExhaustions) {
        add(heatExhaustion);
      }
    }
  }
  
  // transformation
  
  public IReport transform() {
    return new ReportTurnEnd(getPlayerIdTouchdown(), getKnockoutRecoveries(), getHeatExhaustions(), getUnzappedPlayers());
  }
  
  // JSON serialization
  
  public JsonObject toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.REPORT_ID.addTo(jsonObject, getId());
    IJsonOption.PLAYER_ID_TOUCHDOWN.addTo(jsonObject, fPlayerIdTouchdown);
    JsonArray knockoutRecoveryArray = new JsonArray();
    for (KnockoutRecovery knockoutRecovery : fKnockoutRecoveries) {
      knockoutRecoveryArray.add(knockoutRecovery.toJsonValue());
    }
    IJsonOption.KNOCKOUT_RECOVERY_ARRAY.addTo(jsonObject, knockoutRecoveryArray);
    JsonArray heatExhaustionArray = new JsonArray();
    for (HeatExhaustion heatExhaustion : fHeatExhaustions) {
      heatExhaustionArray.add(heatExhaustion.toJsonValue());
    }
    IJsonOption.HEAT_EXHAUSTION_ARRAY.addTo(jsonObject, heatExhaustionArray);
    JsonArray unzappedArray = new JsonArray();
    for (Player unzappedPlayer: unzappedPlayers) {
      unzappedArray.add(unzappedPlayer.toJsonValue());
    }
    IJsonOption.UNZAP_ARRAY.addTo(jsonObject, unzappedArray);
    return jsonObject;
  }
  
  public ReportTurnEnd initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
    UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
    fPlayerIdTouchdown = IJsonOption.PLAYER_ID_TOUCHDOWN.getFrom(jsonObject);
    JsonArray knockoutRecoveryArray = IJsonOption.KNOCKOUT_RECOVERY_ARRAY.getFrom(jsonObject);
    if (knockoutRecoveryArray != null) {
      for (int i = 0; i < knockoutRecoveryArray.size(); i++) {
        add(new KnockoutRecovery().initFrom(knockoutRecoveryArray.get(i)));
      }
    }
    JsonArray heatExhaustionArray = IJsonOption.HEAT_EXHAUSTION_ARRAY.getFrom(jsonObject);
    if (heatExhaustionArray != null) {
      for (int i = 0; i < heatExhaustionArray.size(); i++) {
        add(new HeatExhaustion().initFrom(heatExhaustionArray.get(i)));
      }
    }

    JsonArray unzappedArray = IJsonOption.UNZAP_ARRAY.getFrom(jsonObject);
    if (unzappedArray != null) {
      for (int i = 0; i < unzappedArray.size(); i++) {
        unzappedPlayers.add(Player.getFrom(unzappedArray.get(i)));
      }
    }

    return this;
  }  
  
}
