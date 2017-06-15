package com.ramytech.android.adapter;

import java.util.LinkedList;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ClassListAdapter extends BaseAdapter{
	private LinkedList<String> className;
	private Context myContext;
	
	private int selectedPosition = -1;
	
	public ClassListAdapter(Context context,LinkedList<String> className)
	{
		myContext = context;
		this.className = className;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return className.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setSelectedPosition(int selectedPosition)
	{
		this.selectedPosition = selectedPosition;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.class_list_item, null);
			holder = new ViewHolder();
			holder.background = (LinearLayout)convertView.findViewById(R.id.class_list_item_layout);
			holder.tagName = (TextView)convertView.findViewById(R.id.tag_name);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(position == selectedPosition)
		{
			holder.tagName.setText(className.get(position));
			holder.tagName.setTextColor(Color.WHITE);
			holder.background.setBackground(myContext.getResources().getDrawable(R.drawable.bghomepageredrectangle));
			
			if(position!=0&&position!=1)
         	   holder.background.setTag("choosed");
		}
		else
		{
			holder.tagName.setText(className.get(position));
			holder.tagName.setTextColor(Color.BLACK);
			holder.background.setBackground(myContext.getResources().getDrawable(R.drawable.bghomepagegreyrectangle));
			holder.background.setTag("");

		}
		
		return convertView;
	}
	
	static class ViewHolder
	{
		TextView tagName;
		LinearLayout background;
	}

}
