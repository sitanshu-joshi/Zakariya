package com.example.eventmng;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CreateEvent extends Activity{
	
	private String buidingName;
	private EditText edtEvent, edtDesc;
	private TextView txtBuilding, txtChooseTime;
	private Button btnSave, btnCreateEvent;
	private LinearLayout layview;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_event);
		
		context = this;
		
		edtEvent = (EditText)findViewById(R.id.edtCreateEvent);
		edtDesc = (EditText)findViewById(R.id.edteventDescription);
		
		txtBuilding = (TextView)findViewById(R.id.eventBuiding);
		txtChooseTime = (TextView)findViewById(R.id.eventDT);
		
		btnSave = (Button)findViewById(R.id.btnCraeteEvent);
		btnCreateEvent = (Button)findViewById(R.id.btnSaveDate);
		
		layview = (LinearLayout)findViewById(R.id.viewDate);
		
		
		txtBuilding.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		txtChooseTime.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
