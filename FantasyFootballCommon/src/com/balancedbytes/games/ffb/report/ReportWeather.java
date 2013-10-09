package com.balancedbytes.games.ffb.report;

import javax.xml.transform.sax.TransformerHandler;

import org.xml.sax.helpers.AttributesImpl;

import com.balancedbytes.games.ffb.Weather;
import com.balancedbytes.games.ffb.WeatherFactory;
import com.balancedbytes.games.ffb.bytearray.ByteArray;
import com.balancedbytes.games.ffb.bytearray.ByteList;
import com.balancedbytes.games.ffb.json.IJsonOption;
import com.balancedbytes.games.ffb.json.UtilJson;
import com.balancedbytes.games.ffb.xml.UtilXml;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;



/**
 * 
 * @author Kalimar
 */
public class ReportWeather implements IReport {
  
  private static final String _XML_ATTRIBUTE_WEATHER = "weather";
  private static final String _XML_ATTRIBUTE_ROLL = "roll";
  
  private Weather fWeather;
  private int[] fWeatherRoll;
  
  public ReportWeather() {
    super();
  }

  public ReportWeather(Weather pWeather, int[] pRoll) {
    fWeather = pWeather;
    fWeatherRoll = pRoll;
  }
  
  public ReportId getId() {
    return ReportId.WEATHER;
  }
  
  public Weather getWeather() {
    return fWeather;
  }
  
  public int[] getWeatherRoll() {
    return fWeatherRoll;
  }
  
  // transformation
  
  public IReport transform() {
    return new ReportWeather(getWeather(), getWeatherRoll());
  }

  // XML serialization
  
  public void addToXml(TransformerHandler pHandler) {
    AttributesImpl attributes = new AttributesImpl();
    UtilXml.addAttribute(attributes, XML_ATTRIBUTE_ID, getId().getName());
    UtilXml.addAttribute(attributes, _XML_ATTRIBUTE_WEATHER, (getWeather() != null) ? getWeather().getName() : null);    
    UtilXml.addAttribute(attributes, _XML_ATTRIBUTE_ROLL, getWeatherRoll());
    UtilXml.addEmptyElement(pHandler, XML_TAG, attributes);
  }

  public String toXml(boolean pIndent) {
    return UtilXml.toXml(this, pIndent);
  }
  
  // ByteArray serialization
  
  public int getByteArraySerializationVersion() {
    return 1;
  }

  public void addTo(ByteList pByteList) {
    pByteList.addSmallInt(getId().getId());
    pByteList.addSmallInt(getByteArraySerializationVersion());
    if (getWeather() != null) {
      pByteList.addByte((byte) getWeather().getId());
    } else {
      pByteList.addByte((byte) 0);
    }
    pByteList.addByteArray(getWeatherRoll());
  }
  
  public int initFrom(ByteArray pByteArray) {
    UtilReport.validateReportId(this, new ReportIdFactory().forId(pByteArray.getSmallInt()));
    int byteArraySerializationVersion = pByteArray.getSmallInt();
    fWeather = new WeatherFactory().forId(pByteArray.getByte());
    fWeatherRoll = pByteArray.getByteArrayAsIntArray();
    return byteArraySerializationVersion;
  }
  
  // JSON serialization
  
  public JsonValue toJsonValue() {
    JsonObject jsonObject = new JsonObject();
    IJsonOption.REPORT_ID.addTo(jsonObject, getId());
    IJsonOption.WEATHER.addTo(jsonObject, fWeather);
    IJsonOption.WEATHER_ROLL.addTo(jsonObject, fWeatherRoll);
    return jsonObject;
  }
  
  public ReportWeather initFrom(JsonValue pJsonValue) {
    JsonObject jsonObject = UtilJson.toJsonObject(pJsonValue);
    UtilReport.validateReportId(this, (ReportId) IJsonOption.REPORT_ID.getFrom(jsonObject));
    fWeather = (Weather) IJsonOption.WEATHER.getFrom(jsonObject);
    fWeatherRoll = IJsonOption.WEATHER_ROLL.getFrom(jsonObject);
    return this;
  }
    
}
