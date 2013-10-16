package com.chiemtinhapp.helper;

public enum Gender {
	Male, Female;
	
	public static Gender parseString(String gender) {
		if (gender.equalsIgnoreCase("male")) {
			return Gender.Male;
		}
		else {
			return Gender.Female;
		}
	}
}
