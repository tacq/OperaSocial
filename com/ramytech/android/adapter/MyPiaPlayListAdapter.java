package com.ramytech.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramytech.piaxi.R;

public class MyPiaPlayListAdapter extends BaseAdapter {

	private Context context;
	private int[] icon;
	private String[] name;
	private String[] date; 
	private int[] button;
	
	public MyPiaPlayListAdapter(Context context,int[] icon, String[] name, String[] date, int[] button)
	{
		this.context = context;
		this.icon = icon;
		this.name = name;
		this.date = date;
		this.button = button;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return icon.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.my_pia_play_list_item, null);
			holder = new ViewHolder();
			holder.icon = (ImageView)convertView.findViewById(R.id.my_pia_play_icon);
			holder.name = (TextView)convertView.findViewById(R.id.my_pia_play_name);
			holder.date = (TextView)convertView.findViewById(R.id.my_pia_play_upload_date);
			holder.button = (ImageView)convertView.findViewById(R.id.whether_published);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.icon.setImageDrawable(context.getResources().getDrawable(this.icon[position]));
		holder.name.setText(this.name[position]);
		holder.date.setText(this.date[position]);
		holder.button.setImageDrawable(context.getResources().getDrawable(this.button[position]));
		holder.button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO 决定是否发布
				Toast.makeText(context, "点击发布", Toast.LENGTH_SHORT).show();
			}
			
		});
		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView icon;
		TextView name;
		TextView date;
		ImageView button;
	}

}
