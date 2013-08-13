package com.prl.cvto;

import java.io.FileNotFoundException;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import com.prl.cvto.author.AuthManager;
import com.prl.cvto.author.UserCredential;
import com.prl.cvto.config.FirstTimeConfig;
import com.prl.cvto.config.NetworkConfig;
import com.prl.cvto.config.DeviceConfig;
import com.prl.cvto.device.DeviceDependentCodec;


public class CloudVirtualTryOn extends Application{
	private static final String TAG ="CVTO.Application";
	
	public static class ConfigPool{
		
		public final DeviceConfig DeviceConfig = new DeviceConfig();
		
		public DeviceDependentCodec  DeviceDependentCodec;
		public FirstTimeConfig FirstTimeConfig;
		public NetworkConfig NetworkConfig;
		
	} 
	
	public final ConfigPool ConfigPool = new ConfigPool();
	    
	// Initially, it's a guest account. Don't worry, we will override it if we found a valid credential
	private UserCredential mCredential = new UserCredential(AuthManager.GUEST_UID, AuthManager.GUEST_SECRET, true);
	 
	 
	@Override
    public void onCreate() {
        super.onCreate();
        
        boolean isDebuggable =  ( 0 != ( getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE ) );
        if (!isDebuggable) {
        	//Crashlytics.start(this);
        }
        
        initializeConfig();        
        loadCredential();    
    } 
	
	public UserCredential getCredential() {
		return mCredential;
	}

	public void saveCredential(UserCredential credential) {
		mCredential = credential;
		String credentialFile = ConfigPool.DeviceConfig.getCredentialFileName();
		try {
			UserCredential.save(mCredential, openFileOutput(credentialFile, Context.MODE_PRIVATE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	    
	private void loadCredential() {
		String credentialFile = ConfigPool.DeviceConfig.getCredentialFileName();
		UserCredential credential = null;
		try {
			credential = UserCredential.load(openFileInput(credentialFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		if (credential != null) {
			mCredential = credential;
		}
	}
	    
	private void initializeConfig() {
		ConfigPool.DeviceDependentCodec = new DeviceDependentCodec();		
	
		ConfigPool.NetworkConfig = new NetworkConfig(this);        	        
		ConfigPool.FirstTimeConfig = new FirstTimeConfig(this);
	
	               
	}

	
}
