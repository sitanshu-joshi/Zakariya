package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventmng.R;
import com.example.eventmng.data.Comment;

public class Adaptercomment extends BaseAdapter{

	private Context context;
	private List<Comment> lstCommnets;
	private LayoutInflater inflater;
	private ViewHolder holder;
		
	public Adaptercomment(Context context, List<Comment> lstscore) {
		super();
		this.context = context;
		this.lstCommnets = lstscore;
		inflater = LayoutInflater.from(this.context);		
	}

	@Override
	public int getCount() {
		return lstCommnets.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		try {
			if(convertView == null){			
				holder = new ViewHolder();	
				convertView = inflater.inflate(R.layout.raw_comment, null);
				holder.commnet = (TextView)convertView.findViewById(R.id.comment_name);
				holder.id = (TextView)convertView.findViewById(R.id.comment_user_id);
				convertView.setTag(holder);			
			} else {
				holder = (ViewHolder) convertView.getTag();
			}	
			holder.commnet.setText(lstCommnets.get(position).getComment()+"");
			holder.id.setText(lstCommnets.get(position).getPoster_user_id()+"");				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return convertView;
	}

	public class ViewHolder {
		private TextView commnet;
		private TextView id;
	}

}
