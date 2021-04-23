package com.fumbbl.ffb;

/**
 * 
 * @author Kalimar
 */
public enum PlayerGender implements INamedObject {

	MALE("male", "M", "he", "his", "him", "himself"),
	FEMALE("female", "F", "she", "her", "her", "herself"),
	UNSPECIFIED("unspecified", "U", "they", "their", "them", "themself"),
	NEUTRAL("neutral", "N", "it", "its", "it", "itself");

	private String fName;
	private String fTypeString;
	private String fNominative;
	private String fGenitive;
	private String fDative;
	private String fSelf;

	private PlayerGender(String pName, String pTypeString, String pNominative, String pGenitive, String pDative,
			String pSelf) {
		fName = pName;
		fTypeString = pTypeString;
		fNominative = pNominative;
		fGenitive = pGenitive;
		fDative = pDative;
		fSelf = pSelf;
	}

	public String getName() {
		return fName;
	}

	public String getTypeString() {
		return fTypeString;
	}

	public String getNominative() {
		return fNominative;
	}

	public String getGenitive() {
		return fGenitive;
	}

	public String getDative() {
		return fDative;
	}

	public String getSelf() {
		return fSelf;
	}

	public static PlayerGender fromOrdinal(int ordinal) {
		switch (ordinal) {
		case 1:
			return MALE;
		case 2:
			return FEMALE;
		case 3:
			return UNSPECIFIED;
		default:
			return NEUTRAL;
		}
	}

}