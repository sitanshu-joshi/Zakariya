package com.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventmng.R;
import com.example.eventmng.data.EventList;

public class AdapterListEvent extends BaseAdapter{

	private Context context;
	private List<EventList> lstscore;
	private LayoutInflater inflater;
	private ViewHolder holder;
		
	public AdapterListEvent(Context context, List<EventList> lstscore) {
		super();
		this.context = context;
		this.lstscore = lstscore;
		inflater = LayoutInflater.from(this.context);		
	}

	@Override
	public int getCount() {
		return lstscore.size();
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
				convertView = inflater.inflate(R.layout.raw_eventlist, null);
				holder.time = (TextView)convertView.findViewById(R.id.event_time);
				holder.name = (TextView)convertView.findViewById(R.id.event_name);
				holder.desc = (TextView)convertView.findViewById(R.id.event_descr);
				convertView.setTag(holder);			
			} else {
				holder = (ViewHolder) convertView.getTag();
			}	
			holder.name.setText(lstscore.get(position).getTitle()+"");
			holder.time.setText(lstscore.get(position).getTime()+"");				
			holder.desc.setText(lstscore.get(position).getDescription()+"");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return convertView;
	}

	public class ViewHolder {
		private TextView name;
		private TextView time;
		private TextView desc;
	}

}


