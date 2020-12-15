package it.polimi.gamifiedmarketingapp.utils;

public final class TypeUtils {
	
	public static Integer getTruthValueAsInteger(Boolean condition) {
		return condition == true ? 1 : 0;
	}

}
