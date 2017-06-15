package com.ramytech.android.adapter;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PiaMoneyTopUpListAdapter extends BaseAdapter {

	private Context context;
	private String[] month;
	private String[] explanation; 
	private String[] money;
	public PiaMoneyTopUpListAdapter(Context context,String[] month, String[] explanation, String[] money)
	{
		this.context = context;
		this.month = month;
		this.explanation = explanation;
		this.money = money;
	}
	
	public PiaMoneyTopUpListAdapter(Context context,String[] month, String[] money)
	{
		this.context = context;
		this.month = month;
		this.money = money;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return month.length;
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
			convertView = inflater.inflate(R.layout.pia_money_top_up_item, null);
			holder = new ViewHolder();
			holder.topUpMonth = (TextView)convertView.findViewById(R.id.top_up_month);
			holder.topUpExplanation = (TextView)convertView.findViewById(R.id.top_up_explanation);
			holder.piaMoneyAmount = (TextView)convertView.findViewById(R.id.pia_money_amount);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.topUpMonth.setText(month[position]);
		if(explanation!=null)
		{
			holder.topUpExplanation.setText(explanation[position]);
		}
		else
		{
			holder.topUpExplanation.setTextSize(0);
		}
		holder.piaMoneyAmount.setText(money[position]);

		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView topUpMonth;
		TextView topUpExplanation;
		TextView piaMoneyAmount;
	}

}
