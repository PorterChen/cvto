package com.prl.cvto.device;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import java.util.Collections;

import android.media.MediaCodecInfo.CodecCapabilities;



	/**
	 * 
	 * The order of the device matching algorithm:
	 * 1. Phone model    (HTC Butterfly)
	 * 2. Exactly chip   (dlxub1)
	 * 3. Partially chip (dlxub)
	 * 
	 * @author giggle
	 *
	 */


public class DeviceDependentCodec {

	private static class DevicePort {
		private String mPhoneModel = null;
		private String mExactChip = null;
		private String mPartialChip = null;
		
		public DevicePort(String phoneModel, String exactChip, String partialChip) {
			mPhoneModel = phoneModel;
			mExactChip = exactChip;
			mPartialChip = partialChip;
		}
		
		public boolean isMatch(String phoneModel, String chipName) {		
			
			if (mPhoneModel != null && mPhoneModel.equalsIgnoreCase(phoneModel)) return true;
			if (mExactChip != null && mExactChip.equalsIgnoreCase(chipName)) return true;
			
			if (mPartialChip != null && chipName != null) {
				if (chipName.startsWith(mPartialChip, 0)) 
					return true;
			}
			
			return false;
		}
	}
    
    private static class Config { 
    	final private DevicePort mDevice; 
    	
        private String  mEncodeName = null;
        private String  mDecodeName = null;
        private int     mSupportColorFormat = -1;
        private String  mSupportCodecType = null;
        
        public Config(DevicePort device, 
        		String encode_name, String decode_name, int color_format, String support_type) {
        	mDevice = device;
            mEncodeName = encode_name;
            mDecodeName = decode_name;
            mSupportColorFormat = color_format;
            mSupportCodecType = support_type;
        }
        
        public boolean isMatch(String phoneModel, String chipName) {
        	return mDevice.isMatch(phoneModel, chipName);
        }
        
        public boolean hasEncodeName() {
            return mEncodeName != null;
        }
        
        public String getEncodeName() {
            return mEncodeName;
        }
        
        public boolean hasDecodeName() {
            return mDecodeName != null;
        }
        
        public String getDecodeCodec() {
            return mDecodeName;
        }
        
        public boolean hasColorFormat() {
            return mSupportColorFormat != -1;
        }
        
        public int getSupportColorFormat() {
            return mSupportColorFormat;
        }
        
        public boolean hasCodecType() {
            return mSupportCodecType != null;
        }
        
        public String getSupportCodecType() {
            return mSupportCodecType;
        }
    }
    
    private final static String TAG = "VEDR.DeviceDependentCodec";
    DeviceInfo mDeviceInfo;
    
    private static final List<Config> mDeviceConfig;
    
    static {
        
    	List<Config> device_config = new ArrayList<Config>();
    	        
        device_config.add(new Config(
        		new DevicePort(null, "smdk4x12", null),
                "OMX.SEC.AVC.Encoder", 
                null,
                CodecCapabilities.COLOR_FormatYUV420SemiPlanar,
                "video/avc"));        
          
        device_config.add(new Config(
        		new DevicePort("HTC One S", null, null),
        		"OMX.qcom.video.encoder.avc", 
        		null,
        		CodecCapabilities.COLOR_FormatYUV420SemiPlanar,
        		"video/avc"));
        
        device_config.add(new Config(
        		new DevicePort(null, null, "mt6"),
        		null, 
        		null,
        		CodecCapabilities.COLOR_FormatYUV420Planar,
        		"video/avc"));

        mDeviceConfig = Collections.unmodifiableList(device_config);
    }
        

    public DeviceDependentCodec() {
    	mDeviceInfo = DeviceInfo.getDeviceInfo();
        
    }
    

    public String getEncodeMediaCodecName() {
    	Config config = getConfig(mDeviceInfo.getDeviceName(), mDeviceInfo.getHardwareChipName());
        
    	if (config == null || !config.hasEncodeName()) {
    		return null;
    	}        
    	return config.getEncodeName();
    }
    
    public int getSupportColorFormat() {
    	Config config = getConfig(mDeviceInfo.getDeviceName(), mDeviceInfo.getHardwareChipName());
    	
    	if (config == null || !config.hasColorFormat()) {
    		return CodecCapabilities.COLOR_FormatYUV420SemiPlanar;
    	}        
    	return config.getSupportColorFormat();
    }
    
    public String getSupportCodecType() {
    	Config config = getConfig(mDeviceInfo.getDeviceName(), mDeviceInfo.getHardwareChipName());
        	
    	if (config == null || !config.hasCodecType()) {
    		return "video/avc";
    	}        
    	return config.getSupportCodecType();        
    }
    	
    private Config getConfig(String phoneModel, String chipName) {
    	for (Config config : mDeviceConfig) {
    		if (config.isMatch(phoneModel, chipName)) 
    		{
    			Log.d(TAG, "getConfig matched!");
    			return config;
    		}
    	}
    	Log.d(TAG, "getConfig unmatched any");
    		return null;
     	  }	
}		
		