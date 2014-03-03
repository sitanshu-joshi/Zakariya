package com.example.eventmng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventmng.data.EventList;
import com.example.evntmng.util.HttpHelper;
import com.util.Constant;

public class EventDetail extends Activity{
	public static EventList eventDetail;
	
	TextView txtName, txtTime, txtDesc, txtBuilding, txtLat, txtlong;
	Button btnPutAcomment, btnViewComments;
	private Context context = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		context = this;
		txtName = (TextView)findViewById(R.id.detail_title);
		txtTime = (TextView)findViewById(R.id.detail_time);
		txtDesc = (TextView)findViewById(R.id.detial_description);
		txtBuilding = (TextView)findViewById(R.id.detail_building);
		txtLat = (TextView)findViewById(R.id.detail_latitude);
		txtlong = (TextView)findViewById(R.id.detail_longitude);
		btnPutAcomment = (Button)findViewById(R.id.btnPutComment);
		btnViewComments = (Button)findViewById(R.id.btnViewComments);
		
		// Set the Data.
		txtName.setText(eventDetail.getTitle());
		txtTime.setText(eventDetail.getTime());
		txtDesc.setText(eventDetail.getDescription());
		txtBuilding.setText(eventDetail.getBuildingTitle());
		txtLat.setText(eventDetail.getLatitude());
		txtlong.setText(eventDetail.getLongitude());
		
		
		btnPutAcomment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setACommentof();
			}
		});
		btnViewComments.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(EventDetail.this, CommnetsActivity.class);
				startActivity(intent);
			}
		});
	}
	private void setACommentof() {
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		linearLayout.setPadding(30, 0, 30, 0);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		final EditText edtComments = new EditText(context);
		edtComments.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		edtComments.setImeOptions(EditorInfo.IME_ACTION_DONE);
		edtComments.setInputType(InputType.TYPE_CLASS_TEXT);

		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Put A Commment");
		linearLayout.addView(edtComments);

		dialog.setView(linearLayout);
		dialog.setPositiveButton("Send", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialoginterface, int buttons) {				
				String target = edtComments.getText().toString().trim();
				if (!target.equalsIgnoreCase("")) {
					target = target.replaceAll(" ", "%20");
					String  userid = Constant.userId;
					if (userid == null || userid.length() == 0) {
						userid = "3";
					}
					String string = HttpHelper.getComment(eventDetail.getId(), target, userid);
					System.out.println(string);
					try {
						JSONObject jsonObject = new JSONObject(string);
						JSONArray data = (JSONArray)jsonObject.get("datas");
						JSONObject status = (JSONObject) data.get(0);
						int log = (Integer)status.get("status");
						if (log == 1) {
							Toast.makeText(getApplicationContext(), "Your Comment has been posted successfully", Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(getApplicationContext(), "Something going wrong, please try after some time", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 				
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int buttons) {

			}
		});
		dialog.show();
	}

}
