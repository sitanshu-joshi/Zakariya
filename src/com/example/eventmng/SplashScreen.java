package com.example.eventmng;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity{
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_main);
		context = this;
		
		/**
		 * Redirect to Main Activity
		 */
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {				
			@Override
			public void run() {
				try {
					Intent intent = new Intent(context,LoginActivity.class);								
					startActivity(intent);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}					
		},3000);						
	}
}
