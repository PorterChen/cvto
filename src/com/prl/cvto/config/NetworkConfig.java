package com.prl.cvto.config;

import android.content.Context;

public class NetworkConfig extends ConfigBase {
	private static final int CURRENT_VERSION = 1;
	private static final String PREFS_NAME = "NetworkConfig";

	public NetworkConfig(Context context) {
		super(context, PREFS_NAME, CURRENT_VERSION);
		
		
	}

}
