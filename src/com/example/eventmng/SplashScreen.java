package com.example.eventmng;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.eventmng.data.Building;
import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

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
					getBuildings();
					Intent intent = new Intent(context,LoginActivity.class);								
					startActivity(intent);
					finish();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}					
		},3000);						
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

}
