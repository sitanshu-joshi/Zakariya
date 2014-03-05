package com.example.eventmng;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class Signup extends Activity implements LocationListener{
	private Context context;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	private EditText username,password;

	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		context = this;

		username = (EditText)findViewById(R.id.add_username);
		password = (EditText)findViewById(R.id.add_password);

		btnSignUp = (Button)findViewById(R.id.btnSignUp);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		/**
		 * Location detector
		 */
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		/*
		 * Or
		 */
//		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

		btnSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String res = HttpHelper.getSignupResposne(username.getText().toString().trim(), password.getText().toString().trim(), Constant.latitude, Constant.longitude);
				System.out.println("======="+res);
				try {
					JSONObject jsonObject = new JSONObject(res);
					JSONArray data = (JSONArray)jsonObject.get("datas");
					JSONObject status = (JSONObject) data.get(0);
					int log = (Integer)status.get("status");
					
					
					if (log == 1) {
						Toast.makeText(getApplicationContext(), "Successfully Created Account ", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						String msg = (String) status.get("msg");
						Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
					}
				}catch(Exception e) {
					e.printStackTrace();
					if (res.contains("1")) {
						Toast.makeText(getApplicationContext(), "Successfully Created Account ", Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "Problem in server, try after some time", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		System.out.println("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
		Constant.latitude = location.getLatitude();
		Constant.longitude = location.getLongitude();
		//		Toast.makeText(context, "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude(), Toast.LENGTH_LONG).show();
	}
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}
}
