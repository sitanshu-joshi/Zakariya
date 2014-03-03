package com.example.eventmng;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmng.data.Building;
import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class Signup extends Activity implements LocationListener{
	private Context context;
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	private EditText username,password;
	private TextView campus;
	private Button btnSignUp;
	private String strBuildingid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		context = this;

		username = (EditText)findViewById(R.id.add_username);
		password = (EditText)findViewById(R.id.add_password);
		campus = (TextView)findViewById(R.id.add_campusname);
		btnSignUp = (Button)findViewById(R.id.btnSignUp);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		/**
		 * Location detector
		 */
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		/*
		 * Or
		 */
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
		getBuildings();

		btnSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String res = HttpHelper.getSignupResposne(username.getText().toString().trim(), password.getText().toString().trim(), strBuildingid, Constant.latitude, Constant.longitude);
				
				try {
					JSONObject jsonObject = new JSONObject(res);
					JSONArray data = (JSONArray)jsonObject.get("datas");
					JSONObject status = (JSONObject) data.get(0);
					int log = (Integer) status.get("status");
					if (log == 1) {
						Toast.makeText(getApplicationContext(), "Successfully Created Account ", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "Problem in server, try after some time", Toast.LENGTH_SHORT).show();
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		campus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				building(Constant.lstBuildings);
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


	private void getBuildings(){
		String resp =	HttpHelper.getBuildings();

		try {
			JSONObject jsonObject = new JSONObject(resp);
			JSONArray data = (JSONArray)jsonObject.get("datas");
			JSONObject status = (JSONObject) data.get(0);

			int log = (Integer) status.get("status");
			if (log == 1) {
				int size = (Integer) status.get("size");

				Constant.lstBuildings = new ArrayList<Building>();
				for (int i = 0; i < size; i++) {
					JSONObject object = (JSONObject)status.get(i+"");

					Building building = new Building();
					building.setId(object.getString("id"));
					building.setTitle(object.getString("title"));
					String lat = object.getString("latitude");
					String lon = object.getString("longitude");
					
					if (lat != null && !lat.equalsIgnoreCase("null") && lon != null && !lon.equalsIgnoreCase("null") ) {
						building.setLatitude(Double.parseDouble(lat));
						building.setLonditude(Double.parseDouble(lon));
					}


					Constant.lstBuildings.add(building);
				}
				System.out.println(Constant.lstBuildings);
			} else {
				Toast.makeText(getApplicationContext(), "Currently no buildings are available", Toast.LENGTH_SHORT).show();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void building(List<Building> buildings){
		List<String> strings = new ArrayList<String>();
		for (int i = 0; i < buildings.size(); i++) {
			strings.add(buildings.get(i).getTitle());
		}
		final CharSequence[] items = strings.toArray(new CharSequence[strings.size()]);

		//			final CharSequence[] items = {"Visited","Store Was Close","Not Visited"};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				strBuildingid = Constant.lstBuildings.get(item).getId();
				campus.setText(Constant.lstBuildings.get(item).getTitle());
			}
		});		
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {				
			
			}
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {

			}
		});
		AlertDialog alert = builder.create();
		builder.setTitle("Choose Building");
		alert.show();
	}


}
