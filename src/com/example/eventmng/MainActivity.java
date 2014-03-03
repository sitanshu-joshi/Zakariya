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
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adapter.AdapterListEvent;
import com.example.eventmng.data.Building;
import com.example.eventmng.data.EventList;
import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class MainActivity extends Activity {
	ListView lstView;
	List<EventList> lsEventLists;
	TextView txtGetBuilding;
	Button btnCreateEvent;
	
	private Context context;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        lstView = (ListView)findViewById(R.id.listView1);        
        txtGetBuilding = (TextView)findViewById(R.id.txtGetEvent);
        btnCreateEvent = (Button)findViewById(R.id.btnCreateEvent);
        
        btnCreateEvent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, CreateEvent.class);
				startActivity(intent);
			}
		});
        
        txtGetBuilding.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				building(Constant.lstBuildings);
			}
		});
        
        lstView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				EventList ev = lsEventLists.get(position);
				EventDetail.eventDetail = ev;
				Intent intent = new Intent(MainActivity.this, EventDetail.class);
				startActivity(intent);
			}
		});
        txtGetBuilding.setText(Constant.lstBuildings.get(0).getTitle());
        initialSetup(Constant.lstBuildings.get(0).getId());  
    }
    private void initialSetup(String buildingId){
    	String resp = HttpHelper.getEventList(buildingId);
    	try {
    		JSONObject jsonObject = new JSONObject(resp);
    		JSONArray data = (JSONArray)jsonObject.get("datas");
			JSONObject status = (JSONObject) data.get(0);
			
			int log = (Integer) status.get("status");
			if (log == 1) {
				int size = (Integer) status.get("size");
				lsEventLists = new ArrayList<EventList>();
				for (int i = 0; i < size; i++) {
					
					JSONObject object = (JSONObject)status.get(i+"");
					System.out.println(object);
					String id = object.getString("id");
					String title = object.getString("title");
					String time = object.getString("time");
					String description = object.getString("description");
					String posterUserId = object.getString("poster_user_id");
					String buildingtitle = object.getString("buildingtitle");
					String latitude = object.getString("latitude");
					String longitude = object.getString("longitude");
								
					EventList eventList = new EventList();
					eventList.setId(id);
					eventList.setTime(time);
					eventList.setTitle(title);
					eventList.setDescription(description);
					if (description != null && description.toString().equalsIgnoreCase("null")) {
						eventList.setPosterUserId(posterUserId);
					}
					eventList.setBuildingTitle(buildingtitle);
					if (latitude != null && latitude.toString().equalsIgnoreCase("null") && longitude != null && longitude.toString().equalsIgnoreCase("null")) {
						eventList.setLatitude(latitude);
						eventList.setLongitude(longitude);
					}
					lsEventLists.add(eventList);
				}
				System.out.println(lsEventLists);
				if (lsEventLists.size() > 0) {
					AdapterListEvent adapterStoreDetail = new AdapterListEvent(getApplicationContext(), lsEventLists);
					lstView.setAdapter(adapterStoreDetail);
				}
			} else {
				Toast.makeText(context, "No Events are available", Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void testSetup(){
    	EventList eventList = new EventList();
        eventList.setBuildingId(444+"");
        eventList.setBuildingTitle("Safal Profitire");
        eventList.setId(1+"");
        eventList.setLatitude(22.34+"");
        eventList.setLongitude(72.32+"");
        eventList.setPosterUserId(2+"");
        eventList.setTime("17/2/2014 12:45:23");
        eventList.setTitle("Safal profitier");
        eventList.setDescription("205-A corporate road");
        
        lsEventLists = new ArrayList<EventList>();
        lsEventLists.add(eventList);
        
        AdapterListEvent adapterStoreDetail = new AdapterListEvent(getApplicationContext(), lsEventLists);
		lstView.setAdapter(adapterStoreDetail);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
				// Call event list
				initialSetup(Constant.lstBuildings.get(item).getId());
				txtGetBuilding.setText(Constant.lstBuildings.get(item).getTitle());
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
