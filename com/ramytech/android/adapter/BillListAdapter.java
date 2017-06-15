package com.ramytech.android.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.piaxi.R;

public class BillListAdapter extends BaseAdapter{
	private Context myContext;
	
	private int[] headIcon;
	private String []name;
	private String []novelName;
	private int[]gift;
	private String[] money;
	private int[] count;
	
	public BillListAdapter(Context context,int[] headIcon,String []name,String []novelName,int[]gift,String[] money,int[] count)
	{
		myContext = context;
		this.headIcon = headIcon;
		this.name = name;
		this.novelName = novelName;
		this.gift = gift;
		this.money = money;
		this.count = count;
	}

	@Override
	public int getCount() {
		return headIcon.length;
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
			final LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.bill_item, null);
			holder = new ViewHolder();
			holder.headIcon = (ImageView)convertView.findViewById(R.id.head_icon_bill);
			holder.name = (TextView)convertView.findViewById(R.id.novel_writer_name);
			holder.novelName = (TextView)convertView.findViewById(R.id.novel_name_for_bill_page);
			holder.gift = (ImageView)convertView.findViewById(R.id.gift);
			holder.money = (TextView)convertView.findViewById(R.id.bill);
			holder.count = (TextView)convertView.findViewById(R.id.bill_count);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.headIcon.setImageDrawable(myContext.getResources().getDrawable(headIcon[position]));
		holder.name.setText(name[position]);
		holder.novelName.setText(novelName[position]);
		if(gift[position]!=-1)
			holder.gift.setImageDrawable(myContext.getResources().getDrawable(gift[position]));
		else
			holder.gift.setImageDrawable(null);
		holder.money.setText(" "+money[position]);
		holder.count.setText(""+count[position]);
		
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView headIcon;
		TextView name;
		TextView novelName;
		ImageView gift;
		TextView money;
		TextView count;
	}

}

