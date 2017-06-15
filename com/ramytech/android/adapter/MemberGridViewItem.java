package com.ramytech.android.adapter;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberGridViewItem extends BaseAdapter{
	private Context context;
	
	private int[] headIcon;
	private String[] name;
	private int[] status;
	private String[] occupation;
	
	
	public MemberGridViewItem(Context context, int[] headIcon,String[] name,int[] status,String[] occupation)
	{
		this.context = context;
		this.headIcon = headIcon;
		this.name = name;
		this.status = status;
		this.occupation = occupation;
	}
	@Override
	public int getCount() {
		return headIcon.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.member_item, null);
			holder = new ViewHolder();
			holder.headIcon = (ImageView)convertView.findViewById(R.id.member_head_icon);
			holder.name = (TextView)convertView.findViewById(R.id.member_name);
			holder.status = (ImageView)convertView.findViewById(R.id.member_status);
			holder.occupation = (TextView)convertView.findViewById(R.id.member_occupation);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.headIcon.setImageDrawable(context.getResources().getDrawable(this.headIcon[position]));
		holder.name.setText(name[position]);
		if(status[position]!=0)	
			holder.status.setImageDrawable(context.getResources().getDrawable(this.status[position]));
		else
			holder.status.setImageDrawable(null);
		holder.occupation.setText(occupation[position]);
		
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView headIcon;
		TextView name;
		ImageView status;
		TextView occupation;
	}
}
