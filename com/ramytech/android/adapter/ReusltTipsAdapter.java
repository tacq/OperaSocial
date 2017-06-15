package com.ramytech.android.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ramytech.piaxi.R;

public class ReusltTipsAdapter extends BaseAdapter{
	private Context context;
	private LinkedList<String> tips;
	
	public ReusltTipsAdapter(Context context,
			LinkedList<String> tips)
	{
		this.context = context;
		this.tips = tips;
	}
	
	public void updataData(LinkedList<String>tips)
	{
		this.tips = tips;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tips.size();
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
		final ViewHolder holder;
		if(convertView==null)
		{
			final LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.result_tips_item, null);
			holder = new ViewHolder();
			holder.tips = (TextView)convertView.findViewById(R.id.tips_result_tip_item);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		holder.tips.setText(tips.get(position));
		
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView tips;
	}
	
}