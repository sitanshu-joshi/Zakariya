package com.example.eventmng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmng.data.Building;
import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class CreateEvent extends Activity{

	private String buidingName;
	private EditText edtEvent, edtDesc;
	private TextView txtBuilding, txtChooseTime;
	private Button btnSave, btnCreateEvent;
	private LinearLayout layview;
	private Context context;
	private DatePicker datePicker;
	String buildingId;
	private int mYear;
	private int mMonth;
	private int mDay;

	static final int DATE_DIALOG_ID = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);

		context = this;

		edtEvent = (EditText)findViewById(R.id.edtCreateEvent);
		edtDesc = (EditText)findViewById(R.id.edteventDescription);
		datePicker = (DatePicker)findViewById(R.id.datePicker);
		txtBuilding = (TextView)findViewById(R.id.eventBuiding);
		txtChooseTime = (TextView)findViewById(R.id.eventDT);

		btnSave = (Button)findViewById(R.id.btnCraeteEvent);
		

		layview = (LinearLayout)findViewById(R.id.viewDate);

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String res = HttpHelper.CreateEvent(edtEvent.getText().toString().trim(), edtDesc.getText().toString().trim(), txtChooseTime.getText().toString(), buildingId, Constant.userId);
				try {
					JSONObject jsonObject = new JSONObject(res);
					JSONArray data = (JSONArray)jsonObject.get("datas");
					JSONObject status = (JSONObject) data.get(0);
					int log = (Integer)status.get("status");
					if (log == 1) {
						Toast.makeText(getApplicationContext(), "Craeted Event Successfull", Toast.LENGTH_LONG).show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(), "Please Try with Proper username & password", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		txtBuilding.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				building(Constant.lstBuildings);
			}
		});
		txtChooseTime.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		updateDisplay();
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
				buildingId = Constant.lstBuildings.get(item).getId();
				txtBuilding.setText(Constant.lstBuildings.get(item).getTitle());
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
	
	private void updateDisplay() {
		txtChooseTime.setText(
				new StringBuilder()
				// Month is 0 based so add 1
				.append(mYear).append("-")        
				.append(mMonth + 1).append("-")
				.append(mDay).append(""));
	}
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
			new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, 
				int monthOfYear, int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,
					mDateSetListener,
					mYear, mMonth, mDay);
		}
		return null;
	}
}
