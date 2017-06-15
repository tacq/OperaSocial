package com.ramytech.android.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.piaxi.R;

public class MessageEmailAdapter extends BaseAdapter{
	private int[] userHead;
	private LinkedList<String> userName;
	private int[] userLevel;
	private LinkedList<String> date;
	private LinkedList<String> message;
	private Context context;
	
	
	public MessageEmailAdapter(Context context,int[] userHead,LinkedList<String> userName,
			int[] userLevel,LinkedList<String> date,LinkedList<String> message)
	{
		this.context = context;
		this.userHead = userHead;
		this.userName = userName;
		this.userLevel = userLevel;
		this.date = date;
		this.message = message;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userName.size();
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


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.message_email_list_item, null);
			holder = new ViewHolder();
			holder.userHead = (ImageView)convertView.findViewById(R.id.meesage_email_head_icon);
			holder.userName = (TextView)convertView.findViewById(R.id.message_user_name);
			holder.userLevel = (ImageView)convertView.findViewById(R.id.message_user_level_icon);
			holder.date = (TextView)convertView.findViewById(R.id.message_date);
			holder.message = (TextView)convertView.findViewById(R.id.message);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.userHead.setImageDrawable(context.getResources().getDrawable(this.userHead[position]));
		holder.userName.setText(this.userName.get(position));
		holder.userLevel.setImageDrawable(context.getResources().getDrawable(this.userLevel[position]));
		holder.date.setText(this.date.get(position));
		holder.message.setText(this.message.get(position));
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView userHead;
		TextView userName;
		ImageView userLevel;
		TextView date;
		TextView message;
	}

}

