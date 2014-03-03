package com.example.eventmng;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.adapter.Adaptercomment;
import com.example.eventmng.data.Comment;
import com.example.evntmng.util.HttpHelper;

public class CommnetsActivity extends Activity{
	
	ListView lstview;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments);
		
		context = this;
		lstview = (ListView)findViewById(R.id.listForComments);
		
		initiaSetup();
	}
	
	private void initiaSetup(){
		List<Comment> lstComments = new ArrayList<Comment>();
		try {
			String res = HttpHelper.getcommentsFromId(EventDetail.eventDetail.getId());
			System.out.println(res);
			JSONObject jsonObject = new JSONObject(res);
    		JSONArray data = (JSONArray)jsonObject.get("datas");
			JSONObject status = (JSONObject) data.get(0);
			
			int log = (Integer) status.get("status");
			if (log == 1) {
				int size = (Integer) status.get("size");
				for (int i = 0; i < size; i++) {
					JSONObject object = (JSONObject)status.get(i+"");
					Comment comment = new Comment();
					comment.setComment(object.getString("comment"));
					comment.setPoster_user_id(object.getString("poster_user_id"));
					comment.setId(object.getString("id"));
					lstComments.add(comment);
				}
				if (lstComments.size() > 0) {
					Adaptercomment adaptercomment = new Adaptercomment(context, lstComments);
					lstview.setAdapter(adaptercomment);
				}

			} else {
				Toast.makeText(getApplicationContext(), "No comments are available for current event", Toast.LENGTH_LONG).show();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
				
	}
}
