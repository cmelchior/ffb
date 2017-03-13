package com.balancedbytes.games.ffb.dialog;

import java.util.ArrayList;
import java.util.List;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class DialogArgueTheCallParameter implements IDialogParameter {
  
  private String fTeamId;
  private List<String> fPlayerIds;

  public DialogArgueTheCallParameter() {
    fPlayerIds = new ArrayList<String>();
  }

  public DialogArgueTheCallParameter(String teamId) {
    this();
    setTeamId(teamId);
  }
  
  public DialogId getId() {
    return DialogId.ARGUE_THE_CALL;
  }

  public String getTeamId() {
    return fTeamId;
  }
  
  public void setTeamId(String teamId) {
    fTeamId = teamId;
  }
  
  public void addPlayerId(String pPlayerId) {
    if (StringTool.isProvided(pPlayerId)) {
      fPlayerIds.add(pPlayerId);
    }
  }
  
  public void addPlayerIds(String[] pPlayerIds) {
    if (ArrayTool.isProvided(pPlayerIds)) {
      for (String playerId : pPlayerIds) {
        addPlayerId(playerId);
      }
    }
  }
  
  public String[] getPlayerIds() {
    return fPlayerIds.toArray(new String[fPlayerIds.size()]);
  }
  
  // transformation
  
  public IDialogParameter transform() {
    DialogArgueTheCallParameter transformedParameter = new DialogArgueTheCallParameter(getTeamId());
    transformedParameter.addPlayerIds(getPlayerIds());
    return transformedParameter;
  }

  // JSON serialization
  
  public JsonObject toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.DIALOG_ID.addTo(jsonObject, getId());
    IJsonOption.TEAM_ID.addTo(jsonObject, getTeamId());
    IJsonOption.PLAYER_IDS.addTo(jsonObject, getPlayerIds());
    return jsonObject;
  }
  
  public DialogArgueTheCallParameter initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
    UtilDialogParameter.validateDialogId(this, (DialogId) IJsonOption.DIALOG_ID.getFrom(jsonObject));
    setTeamId(IJsonOption.TEAM_ID.getFrom(jsonObject));
    addPlayerIds(IJsonOption.PLAYER_IDS.getFrom(jsonObject));
    return this;
  }

}
