package com.ramytech.android.adapter;


import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class SetLanguageAdapter extends BaseAdapter{
	private Context context;
	private int[]picGrey;
	private int[]picRed;
	
	private int selectedPosition = -1;
	
	public SetLanguageAdapter(Context context,int[] picGrey,int[]picRed)
	{
		this.context = context;
		this.picGrey = picGrey;
		this.picRed = picRed;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return picGrey.length;
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
		final ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.set_plot_grid_item, null);
			holder = new ViewHolder();
			holder.pic = (ImageView)convertView.findViewById(R.id.plot_image);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(position == selectedPosition)
		{
			holder.pic.setImageDrawable(context.getResources().getDrawable(picRed[position]));
		}
		else
		{
			holder.pic.setImageDrawable(context.getResources().getDrawable(picGrey[position]));
		}
		
		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView pic;
	}
	
}