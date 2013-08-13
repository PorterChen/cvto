package com.prl.cvto.config;

import android.content.Context;

public class FirstTimeConfig extends ConfigBase {
	
	private static final int CURRENT_VERSION = 1;
	private static final String PREFS_NAME = "FirstTimeConfig";
	private static final String KEY_ENTER_ACCOUNT_APPLY = "enter_account_apply";
	private static final String KEY_ENTER_TUTORIAL_INFO = "enter_tutorial_info";
	private static final String KEY_ACCEPT_EULA = "accept_eula";
	
	private boolean mEnterAccountApply;
	private boolean mEnterTutorialInfo;
	private boolean mAcceptEula;
	
	public FirstTimeConfig(Context context) {
		super(context, PREFS_NAME, CURRENT_VERSION);
		
		mEnterAccountApply = getBool(KEY_ENTER_ACCOUNT_APPLY, true);
		mEnterTutorialInfo = getBool(KEY_ENTER_TUTORIAL_INFO, true);
		mAcceptEula = getBool(KEY_ACCEPT_EULA, false); 
	}
	
	
	public boolean getEnterAccountApply() {
		return mEnterAccountApply;
	}
	
	public boolean getEnterTutorialInfo() {
		return mEnterTutorialInfo;
	}
	
	public boolean isAcceptEula() {
		return mAcceptEula;
	}
	
	public void setAcceptEula(boolean accept) {
		mAcceptEula = accept;		
        setBool(KEY_ACCEPT_EULA, accept);
	}

	public void setEnterAccountApply(boolean firstTime) {
		mEnterAccountApply = firstTime;		
        setBool(KEY_ENTER_ACCOUNT_APPLY, firstTime);
	}
	
	public void setEnterTutorialInfo(boolean firstTime) {
		mEnterTutorialInfo = firstTime;		
        setBool(KEY_ENTER_TUTORIAL_INFO, firstTime);
	}
	
}