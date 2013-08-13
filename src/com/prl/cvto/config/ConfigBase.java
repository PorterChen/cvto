package com.prl.cvto.config;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigBase {
	  private static final String KEY_CONFIG_VERSION = "config_version";
	    
	    private SharedPreferences mSettings;
	    private SharedPreferences.Editor mSettingsEditor;
	    private int mConfigVersion;    
	    private int mLatestConfigVersion;
	    
	    public ConfigBase(Context context, String prefernceName, int latestVersion) {
	        mSettings = context.getSharedPreferences(prefernceName, Context.MODE_PRIVATE);
	        
	        // Damn, the edit() always create a new one, so I need to save only one here
	        mSettingsEditor = mSettings.edit();
	        mConfigVersion = getInt(KEY_CONFIG_VERSION, -1);        
	        mLatestConfigVersion = latestVersion;
	    }
	    
	    public boolean isNeedUpgrade() {
	    	return mConfigVersion != mLatestConfigVersion;
	    }
	    
	    public void setUptoDate() {
	        mConfigVersion = mLatestConfigVersion;
	        setInt(KEY_CONFIG_VERSION, mConfigVersion);
	    }
	    
	    public void setInt(String key, int value) {
	    	mSettingsEditor.putInt(key, value);
	    	mSettingsEditor.apply();
	    	//LogMe.setInt(key, value);
	    }
	    
	    public void setBool(String key, boolean value) {
	    	mSettingsEditor.putBoolean(key, value);
	    	mSettingsEditor.apply();
	    	//LogMe.setBool(key, value);
	    }
	    
	    public void setFloat(String key, float value) {
	    	mSettingsEditor.putFloat(key, value);
	    	mSettingsEditor.apply();
	    	//LogMe.setFloat(key, value);
	    }
	    
	    public void setLong(String key, long value) {
	    	mSettingsEditor.putLong(key, value);
	    	mSettingsEditor.apply();
	    	//LogMe.setLong(key, value);
	    }
	    public void setString(String key, String value) {
	    	mSettingsEditor.putString(key, value);
	    	mSettingsEditor.apply(); 
	    	//LogMe.setString(key, value);
	    }
	    
	    public void setStringSet(String key, Set<String> values) {
	    	mSettingsEditor.putStringSet(key, values);
	    	mSettingsEditor.apply(); 
	    }
	        
	    public int getInt(String key, int defValue) {
	    	int v = mSettings.getInt(key, defValue);
	    	//LogMe.setInt(key, v);
	    	return v;
	    }
	    
	    public boolean getBool(String key, boolean defValue) {
	    	boolean v = mSettings.getBoolean(key, defValue);
	    	//LogMe.setBool(key, v);
	    	return v;
	    }
	    
	    public Float getFloat(String key, float defValue) {
	    	float v = mSettings.getFloat(key, defValue);
	    	//LogMe.setFloat(key, v);
	    	return v;
	    }    
	    
	    public Long getLong(String key, long defValue) {
	    	long v = mSettings.getLong(key, defValue);
	    	//LogMe.setLong(key, v);
	    	return v;
	    }   
	    
	    public String getString(String key, String defValue) {
	    	String v = mSettings.getString(key, defValue);
	    	//LogMe.setString(key, v);
	    	return v;
	    }
	    
	    public Set<String> getStringSet(String key, Set<String> defValue) {
	    	return mSettings.getStringSet(key, defValue);
	    }
}
