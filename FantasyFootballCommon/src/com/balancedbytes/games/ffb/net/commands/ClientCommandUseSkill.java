package com.balancedbytes.games.ffb.net.commands;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import com.balancedbytes.games.ffb.Skill;
import com.balancedbytes.games.ffb.SkillFactory;
import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.net.NetCommand;
import com.balancedbytes.games.ffb.net.NetCommandId;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;



/**
 * 
 * @author Kalimar
 */
public class ClientCommandUseSkill extends NetCommand {
  
  private static final String _XML_ATTRIBUTE_SKILL = "skill";
  private static final String _XML_ATTRIBUTE_SKILL_USED = "skillUsed";
  
  private Skill fSkill;
  private boolean fSkillUsed;
  
  public ClientCommandUseSkill() {
    super();
  }

  public ClientCommandUseSkill(Skill pSkill, boolean pSkillUsed) {
    fSkill = pSkill;
    fSkillUsed = pSkillUsed;
  }
  
  public NetCommandId getId() {
    return NetCommandId.CLIENT_USE_SKILL;
  }
  
  public boolean isSkillUsed() {
    return fSkillUsed;
  }
  
  public Skill getSkill() {
    return fSkill;
  }
  
  // XML serialization
  
  public void addToXml(TransformerHandler pHandler) {
  	AttributesImpl attributes = new AttributesImpl();
  	UtilXml.addAttribute(attributes, _XML_ATTRIBUTE_SKILL, (getSkill() != null) ? getSkill().getName() : null);
  	UtilXml.addAttribute(attributes, _XML_ATTRIBUTE_SKILL_USED, isSkillUsed());
    UtilXml.addEmptyElement(pHandler, getId().getName(), attributes);
  }
  
  public String toXml(boolean pIndent) {
    return UtilXml.toXml(this, pIndent);
  }
 
  // ByteArray serialization
  
  public int getByteArraySerializationVersion() {
    return 1;
  }
  
  public void addTo(ByteList pByteList) {
    pByteList.addSmallInt(getByteArraySerializationVersion());
    pByteList.addByte((byte) getSkill().getId());
    pByteList.addBoolean(isSkillUsed());
  }
  
  public int initFrom(ByteArray pByteArray) {
    int byteArraySerializationVersion = pByteArray.getSmallInt();
    fSkill = new SkillFactory().forId(pByteArray.getByte());
    fSkillUsed = pByteArray.getBoolean();
    return byteArraySerializationVersion;
  }
  
  // JSON serialization
  
  public JsonObject toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.NET_COMMAND_ID.addTo(jsonObject, getId());
    IJsonOption.SKILL.addTo(jsonObject, fSkill);
    IJsonOption.SKILL_USED.addTo(jsonObject, fSkillUsed);
    return jsonObject;
  }
  
  public void initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.asJsonObject(pJsonValue);
    UtilNetCommand.validateCommandId(this, (NetCommandId) IJsonOption.NET_COMMAND_ID.getFrom(jsonObject));
    fSkill = (Skill) IJsonOption.SKILL.getFrom(jsonObject);
    fSkillUsed = IJsonOption.SKILL_USED.getFrom(jsonObject);
  }
    
}
