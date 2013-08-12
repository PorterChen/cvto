package com.prl.cvto.ui;

import com.prl.cvto.R;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class TabHostActivity extends TabActivity {
	 	private Intent mIntent;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		mIntent = this.getIntent();		
		
		setContentView(R.layout.main_tabhost);		
		
		//Tab for Display
		setTabWidget(TryOnDisplayActivity.class, getString(R.string.tab_display), 
				     getString(R.string.tab_display), R.drawable.tabicon_display);		
		
		//Tab for Choose widget
		setTabWidget(TryOnDisplayActivity.class, getString(R.string.tab_widget), 
			         getString(R.string.tab_widget), R.drawable.tabicon_display);				
		
		//Tab for Setting
		setTabWidget(SettingActivity.class, getString(R.string.tab_setting), 
			         getString(R.string.tab_setting), R.drawable.tabicon_setting);			
		
		//Tab for Information
		setTabWidget(TryOnDisplayActivity.class, getString(R.string.tab_info), 
			         getString(R.string.tab_info), R.drawable.tabicon_setting);		
		
		
		
	}
	
	private void setTabWidget(Class<?> activity, String name, String label, Integer iconId) {
	    Intent intent = new Intent().setClass(this, activity);	 
	    TabHost tabHost = getTabHost();
	    
	    View tab = LayoutInflater.from(this).inflate(R.layout.custom_tabhost, null);
	    ImageView image = (ImageView) tab.findViewById(R.id.icon);
	    TextView text = (TextView) tab.findViewById(R.id.text);
	    
	    if(iconId != null){
	        image.setImageResource(iconId);
	    }
	    
	    text.setText(label);
	 
	    TabSpec spec = tabHost.newTabSpec(name).setIndicator(tab).setContent(intent);
	    tabHost.addTab(spec);
	 
	}
}
