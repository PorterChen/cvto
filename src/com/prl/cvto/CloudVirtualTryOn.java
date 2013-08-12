package com.prl.cvto;

import android.app.Application;
import com.prl.cvto.config.FirsttimeConfig;
import com.prl.cvto.config.NetworkConfig;
import com.prl.cvto.config.DeviceConfig;

public class CloudVirtualTryOn extends Application{
	private static final String TAG ="CVTO.Application";
	
	public static class ConfigPool{
		public final DeviceConfig DeviceConfig = new DeviceConfig();
		public FirsttimeConfig FirsttimeConfig;
		public NetworkConfig NetworkConfig;
		
	} 
	
	
	
	
}
