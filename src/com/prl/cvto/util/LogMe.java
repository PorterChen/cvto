package com.prl.cvto.util;

import android.util.Log;

/**
 * The message using this class should not be deprived. We often consider
 * this kind of message as important one and should be used in production 
 * build.
 * 
 * @author demon
 *
 */

public class LogMe {
	private static final String DELIMITER = ": ";
	
	public static void i(String tag, String message) {
		Log.i(tag, message);
		//Crashlytics.log(tag + DELIMITER + message);
	}
	
	public static void e(String tag, String message) {
		Log.e(tag, message);
		//Crashlytics.log(tag + DELIMITER + message);
	}	
	
	public static void setInt(String key, int value) {
		//Crashlytics.setInt(key, value);
	}
	
	public static void setFloat(String key, float value) {
		//Crashlytics.setFloat(key, value);		
	}
	
	public static void setDouble(String key, double value) {
		//Crashlytics.setDouble(key, value);	
	}
	
	public static void setBool(String key, boolean value) {
		//Crashlytics.setBool(key, value);		
	}
	
	public static void setLong(String key, long value) {		
		//Crashlytics.setLong(key, value);
	}
	
	public static void setString(String key, String value) {
		//Crashlytics.setString(key, value);
	}
}
