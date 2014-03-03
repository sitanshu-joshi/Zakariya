package com.example.eventmng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ConsoleMessage;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class LoginActivity extends Activity{
	private EditText edtusername, edtPassword;
	private Button btnLogin,btnSignup, btnEvent;
	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		btnLogin = (Button)findViewById(R.id.btn_Login);
		btnSignup = (Button)findViewById(R.id.btn_Signup);
		btnEvent = (Button)findViewById(R.id.btn_list);
		edtusername = (EditText)findViewById(R.id.username);
		edtPassword = (EditText)findViewById(R.id.password);
		context = this;
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		btnLogin.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (validateLogin()) {
					
					String string = HttpHelper.getLoginResposne(edtusername.getText().toString().trim(), edtPassword.getText().toString().trim());
					System.out.println("======"+string);
					try {
						JSONObject jsonObject = new JSONObject(string);
						JSONArray data = (JSONArray)jsonObject.get("datas");
						JSONObject status = (JSONObject) data.get(0);
						int log = (Integer)status.get("status");
						if (log == 1) {
							Constant.userId = (String)status.get("id");
							
							Intent intent  = new Intent(LoginActivity.this,MainActivity.class);
							startActivity(intent);
							finish();
						} else {
							Toast.makeText(getApplicationContext(), "Please Try with Proper username & password", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					Toast.makeText(context, "please fill proper username & password", Toast.LENGTH_LONG).show();	
				}
			}
		});
		/**
		 * Redirect To Sign-In Screen
		 */
		btnSignup.setOnClickListener( new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, Signup.class);
				startActivity(intent);
			}
		});
		btnEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	
	}
	
	private boolean validateLogin(){
		boolean isValidate = false;
		if (edtPassword.getText().toString().trim().length() != 0 && edtusername.getText().toString().trim().length() != 0) {
			isValidate = true;
		} else {
			isValidate = false;
		}
		return isValidate;
	}
}
