package com.prl.ui;

import com.prl.vtos.R;
import com.prl.vtos.R.layout;
import com.prl.vtos.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AccountActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_page);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.account, menu);
		return true;
	}

}
