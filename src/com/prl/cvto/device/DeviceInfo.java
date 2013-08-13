package com.prl.cvto.device;

import android.os.Build;
import android.util.Log;

/**
 * 
 * @OriginAuthor zillians/giggle
 *
 */

public class DeviceInfo {	
    static DeviceInfo mDeviceInfo;
    
    static {
        mDeviceInfo = null;
    }
    
    static public synchronized DeviceInfo getDeviceInfo() {
        if (mDeviceInfo == null) {
            mDeviceInfo = new DeviceInfo();
        }
        
        return mDeviceInfo;
    }
    
    /********** DeviceInfo **********/
    private final String TAG = "CVTO.DeviceInfo";
    
    private String mDeviceName;
    private String mHardwareChipName;
    
    private DeviceInfo() {
        initializeDeviceInfo();    
    }
           
    public String getDeviceName() {
        return mDeviceName;
    }
    
    public String getHardwareChipName() {
    	return mHardwareChipName;
    }
    
    private void initializeDeviceInfo() {
    	mHardwareChipName = Build.HARDWARE;
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            mDeviceName = capitalize(model);
        } else {
            mDeviceName = capitalize(manufacturer) + " " + model;
        }
        
        Log.d(TAG, "Device Name: " + mDeviceName);
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }
    
    public final boolean isGalaxyS2() {    	
    	return mDeviceName.compareToIgnoreCase("Samsung GT-I9100") == 0;
    }
    
    public final boolean isGalaxyNexus() {
    	return mDeviceName.compareToIgnoreCase("Samsung Galaxy Nexus") == 0;
    }
}
