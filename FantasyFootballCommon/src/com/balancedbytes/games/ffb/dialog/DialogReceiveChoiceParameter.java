package com.balancedbytes.games.ffb.dialog;

import com.balancedbytes.games.ffb.IDialogParameter;
import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * 
 * @author Kalimar
 */
public class DialogReceiveChoiceParameter implements IDialogParameter {
  
  private String fChoosingTeamId;

  public DialogReceiveChoiceParameter() {
    super();
  }
  
  public DialogReceiveChoiceParameter(String pChoosingTeamId) {
    fChoosingTeamId = pChoosingTeamId;
  }
  
  public DialogId getId() {
    return DialogId.RECEIVE_CHOICE;
  }

  public String getChoosingTeamId() {
    return fChoosingTeamId;
  }
  
  // transformation
  
  public IDialogParameter transform() {
    return new DialogReceiveChoiceParameter(getChoosingTeamId());
  }

  // ByteArray serialization
  
  public int getByteArraySerializationVersion() {
    return 1;
  }
  
  public void addTo(ByteList pByteList) {
    pByteList.addSmallInt(getByteArraySerializationVersion());
    pByteList.addByte((byte) getId().getId());
    pByteList.addString(getChoosingTeamId());
  }

  public int initFrom(ByteArray pByteArray) {
    int byteArraySerializationVersion = pByteArray.getSmallInt();
    UtilDialogParameter.validateDialogId(this, new DialogIdFactory().forId(pByteArray.getByte()));
    fChoosingTeamId = pByteArray.getString();
    return byteArraySerializationVersion;
  }
  
  // JSON serialization
  
  public JsonObject toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.DIALOG_ID.addTo(jsonObject, getId());
    IJsonOption.CHOOSING_TEAM_ID.addTo(jsonObject, fChoosingTeamId);
    return jsonObject;
  }
  
  public DialogReceiveChoiceParameter initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
    UtilDialogParameter.validateDialogId(this, (DialogId) IJsonOption.DIALOG_ID.getFrom(jsonObject));
    fChoosingTeamId = IJsonOption.CHOOSING_TEAM_ID.getFrom(jsonObject);
    return this;
  }

}
