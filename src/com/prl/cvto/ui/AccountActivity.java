package com.prl.cvto.ui;



import com.prl.cvto.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class AccountActivity extends Activity {
	private Button mbuttonlogin;
	private Button mbuttonfreetry;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_page);
		
		mbuttonlogin = (Button)findViewById(R.id.buttonLogin);
		mbuttonfreetry = (Button)findViewById(R.id.buttonFreetry);
		
		
		mbuttonlogin.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub              

			}   
		});   
		
		mbuttonfreetry.setOnClickListener(new Button.OnClickListener(){ 

			@Override
			public void onClick(View v) {				
				onClickFreetry(v);       
			}   
		});     
		
		  
	}
	
	
	public void onClickFreetry(View v) {
			startActivity(new Intent(AccountActivity.this, TabHostActivity.class)); 
	}	
	

}
