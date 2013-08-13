package com.prl.cvto.ui;

import com.prl.cvto.R;
import com.prl.cvto.util.TabManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class TabHostActivity extends FragmentActivity {	 	
	
	private final String TAG = "CVTO.TabHost";
	private static final String STRING_TAB_DISPLAY = "tab_display";
	private static final String STRING_TAB_FITTING = "tab_fitting";
	private static final String STRING_TAB_SETTING = "tab_setting";
	private static final String STRING_TAB_INFOMATION = "tab_infomaiotn";

	private TabHost mTabHost; 
	private TabManager mTabManager;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
			
		setContentView(R.layout.tabhost_main);				
		mTabHost = (TabHost) findViewById(android.R.id.tabhost); 
		mTabHost.setup();   
		
		mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
		
		initialTabHost();		       
		updateTabHostFrag(); 
	   
	}
	
	private void initialTabHost() {
		 mTabHost.setCurrentTab(0);
		 View mTabView = null;
		 //Tab for Display
		 mTabView = setTabHostWidget(mTabView, getString(R.string.tab_display), R.drawable.tabicon_display);
		 mTabManager.addTab(
			mTabHost.newTabSpec(STRING_TAB_DISPLAY).setIndicator(mTabView), DisplayActivity.class, null);
	
		 //Tab for Choose widget
		 mTabView = setTabHostWidget(mTabView, getString(R.string.tab_fitting), R.drawable.tabicon_display);
	     mTabManager.addTab(
	        mTabHost.newTabSpec(STRING_TAB_FITTING).setIndicator(mTabView), FittingActivity.class, null);
	     
	     //Tab for Setting
	     mTabView = setTabHostWidget(mTabView, getString(R.string.tab_setting), R.drawable.tabicon_setting);
	     mTabManager.addTab(
	      	mTabHost.newTabSpec(STRING_TAB_SETTING).setIndicator(mTabView), SettingActivity.class, null);
	  
	     //Tab for Information
	     mTabView = setTabHostWidget(mTabView, getString(R.string.tab_info), R.drawable.tabicon_setting);
	     mTabManager.addTab(
	       	mTabHost.newTabSpec(STRING_TAB_INFOMATION).setIndicator(mTabView), InfoActivity.class, null);
	     
	}
	
	private void updateTabHostFrag() {
		DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); 		 
        int screenWidth = dm.widthPixels;  						 	 
           
           
        TabWidget tabWidget = mTabHost.getTabWidget();  			
        int count = tabWidget.getChildCount();  					
        if (count > 3) {   
            for (int i = 0; i < count; i++) {   
                tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth)/3);			
            }   
        }
	}
	
	private View setTabHostWidget(View tab, String label, Integer iconId) {   
	    Log.d(TAG, "In setTabHostWidget");	    
	    tab = LayoutInflater.from(this).inflate(R.layout.tabhost_custom_panel, null);
	    ImageView tabimage = (ImageView) tab.findViewById(R.id.tabicon);
	    TextView tabtext = (TextView) tab.findViewById(R.id.tabtext);
	    
	    if(iconId != null){
	        tabimage.setImageResource(iconId);
	    }
	    
	    tabtext.setText(label);
	    
	   return tab; 
	}
	
	
}
