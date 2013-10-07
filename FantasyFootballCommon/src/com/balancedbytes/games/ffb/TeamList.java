package com.balancedbytes.games.ffb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.AttributesImpl;

import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.bytearray.IByteArraySerializable;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.util.ArrayTool;
import com.balancedbytes.games.ffb.util.StringTool;
import com.balancedbytes.games.ffb.xml.IXmlReadable;
import com.balancedbytes.games.ffb.xml.IXmlSerializable;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;


/**
 * 
 * @author Kalimar
 */
public class TeamList implements IXmlSerializable, IByteArraySerializable {
    
//  <teams coach="47257">
//    <team>
//      <id>492614</id>
//      <status>1</status>
//      <division>1</division>
//      <name>Bauernopfer</name>
//      <rating>147</rating>
//      <strength>152</strength>
//      <race>Elf</race>
//      <treasury>0</treasury>
//    </team>
//  </teams>

  public static final String XML_TAG = "teams";
  
  private static final String _XML_ATTRIBUTE_COACH = "coach";

  private String fCoach;
  private List<TeamListEntry> fTeamListEntries;
  
  public TeamList() {
    fTeamListEntries = new ArrayList<TeamListEntry>();
  }
  
  public TeamList(String pCoach, TeamListEntry[] pTeamListEntries) {
    this();
    fCoach = pCoach;
    add(pTeamListEntries);
  }

  public String getCoach() {
    return fCoach;
  }
  
  public TeamListEntry[] getTeamListEntries() {
    return fTeamListEntries.toArray(new TeamListEntry[fTeamListEntries.size()]);
  }
  
  public void add(TeamListEntry pTeamListEntry) {
    if (pTeamListEntry != null) {
      fTeamListEntries.add(pTeamListEntry);
    }
  }
  
  private void add(TeamListEntry[] pTeamListEntries) {
    if (ArrayTool.isProvided(pTeamListEntries)) {
      for (TeamListEntry teamListEntry : pTeamListEntries) {
        add(teamListEntry);
      }
    }
  }
  
  public int size() {
    return fTeamListEntries.size();
  }
  
  public TeamList filterActiveTeams() {
    TeamList filteredTeamList = new TeamList();
    for (TeamListEntry teamListEntry : getTeamListEntries()) {
      if (TeamStatus.ACTIVE == teamListEntry.getTeamStatus()) {
        filteredTeamList.add(teamListEntry);
      }
    }
    return filteredTeamList;
  }
  
  public TeamList filterDivisions(String[] pDivisions) {
    TeamList filteredTeamList = new TeamList();
    if (ArrayTool.isProvided(pDivisions)) {
      for (TeamListEntry teamListEntry : getTeamListEntries()) {
        for (String division : pDivisions) {
          if (StringTool.isProvided(teamListEntry.getDivision()) && teamListEntry.getDivision().equals(division)) {
            filteredTeamList.add(teamListEntry);
          }
        }
      }
    }
    return filteredTeamList;
  }
  
  // XML serialization
  
  public void addToXml(TransformerHandler pHandler) {
  	
  	AttributesImpl attributes = new AttributesImpl();
  	UtilXml.addAttribute(attributes, _XML_ATTRIBUTE_COACH, getCoach());
  	UtilXml.startElement(pHandler, XML_TAG, attributes);
  	
    TeamListEntry[] teamListEntries = getTeamListEntries();
    for (TeamListEntry teamListEntry : teamListEntries) {
    	teamListEntry.addToXml(pHandler);
    }
  	
  	UtilXml.endElement(pHandler, XML_TAG);
  	
  }
  
  public String toXml(boolean pIndent) {
    return UtilXml.toXml(this, pIndent);
  }
    
  public IXmlReadable startXmlElement(String pXmlTag, Attributes pXmlAttributes) {
    IXmlReadable xmlElement = this;
    if (XML_TAG.equals(pXmlTag)) {
      fCoach = UtilXml.getStringAttribute(pXmlAttributes, _XML_ATTRIBUTE_COACH);
    }
    if (TeamListEntry.XML_TAG.equals(pXmlTag)) {
      TeamListEntry teamListEntry = new TeamListEntry();
      teamListEntry.startXmlElement(pXmlTag, pXmlAttributes);
      add(teamListEntry);
      xmlElement = teamListEntry;
    }
    return xmlElement;
  }
  
  public boolean endXmlElement(String pXmlTag, String pValue) {
    return XML_TAG.equals(pXmlTag);
  }
  
  // ByteArray serialization
  
  public int getByteArraySerializationVersion() {
    return 1;
  }
  
  public void addTo(ByteList pByteList) {
    pByteList.addSmallInt(getByteArraySerializationVersion());
    pByteList.addString(getCoach());
    TeamListEntry[] teamListEntries = getTeamListEntries();
    pByteList.addByte((byte) teamListEntries.length);
    for (TeamListEntry teamListEntry : teamListEntries) {
      teamListEntry.addTo(pByteList);
    }
  }
  
  public int initFrom(ByteArray pByteArray) {
    int byteArraySerializationVersion = pByteArray.getSmallInt();
    fCoach = pByteArray.getString();
    int nrOfTeamListEntries = pByteArray.getByte();
    for (int i = 0; i < nrOfTeamListEntries; i++) {
      TeamListEntry teamListEntry = new TeamListEntry();
      teamListEntry.initFrom(pByteArray);
      add(teamListEntry);
    }
    return byteArraySerializationVersion;
  }
  
  // JSON serialization
  
  public JsonObject toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.COACH.addTo(jsonObject, fCoach);
    JsonArray teamList = new JsonArray();
    TeamListEntry[] teamListEntries = getTeamListEntries();
    for (TeamListEntry teamListEntry : teamListEntries) {
      teamList.add(teamListEntry.toJsonValue());
    }
    IJsonOption.TEAM_LIST.addTo(jsonObject, teamList);
    return jsonObject;
  }
  
  public void initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.asJsonObject(pJsonValue);
    fCoach = IJsonOption.COACH.getFrom(jsonObject);
    JsonArray teamList = IJsonOption.TEAM_LIST.getFrom(jsonObject);
    for (int i = 0; i < teamList.size(); i++) {
      TeamListEntry teamListEntry = new TeamListEntry();
      teamListEntry.initFrom(teamList.get(i));
      add(teamListEntry);
    }
  }

}
