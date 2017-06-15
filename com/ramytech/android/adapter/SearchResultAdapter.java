package com.ramytech.android.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.android.adapter.SetLanguageAdapter.ViewHolder;
import com.ramytech.piaxi.R;

public class SearchResultAdapter extends BaseAdapter{
	private Context context;
	private LinkedList<String> picUrl;
	private LinkedList<String> playName;
	private LinkedList<String> uploderName;
	private LinkedList<String> characterNumber;
	private LinkedList<String> femaleNumber;
	private LinkedList<String> maleNumber;
	private LinkedList<String> outSiderNumber;
	
	
	private int selectedPosition = -1;
	
	public SearchResultAdapter(Context context,
			LinkedList<String> picUrl,
			LinkedList<String> playName,
			LinkedList<String> uploderName,
			LinkedList<String> characterNumber,
			LinkedList<String> femaleNumber,
			LinkedList<String> maleNumber,
			LinkedList<String> outSiderNumber)
	{
		this.context = context;
		this.picUrl = picUrl;
		this.playName = playName;
		this.uploderName = uploderName;
		this.characterNumber = characterNumber;
		this.femaleNumber = femaleNumber;
		this.maleNumber = maleNumber;
		this.outSiderNumber = outSiderNumber;
	}
	
	public void updataData( LinkedList<String> picUrl,
							LinkedList<String> playName,
							LinkedList<String> uploderName,
							LinkedList<String> characterNumber,
							LinkedList<String> femaleNumber,
							LinkedList<String> maleNumber,
							LinkedList<String> outSiderNumber)	
	{
		this.picUrl = picUrl;
		this.playName = playName;
		this.uploderName = uploderName;
		this.characterNumber = characterNumber;
		this.femaleNumber = femaleNumber;
		this.maleNumber = maleNumber;
		this.outSiderNumber = outSiderNumber;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return picUrl.size();
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
			convertView = inflater.inflate(R.layout.search_result_item, null);
			holder = new ViewHolder();
			holder.pic = (ImageView)convertView.findViewById(R.id.result_icon);
			holder.playName = (TextView)convertView.findViewById(R.id.result_name);
			holder.uploderName = (TextView)convertView.findViewById(R.id.result_uploder_name);
			holder.characterNumber = (TextView)convertView.findViewById(R.id.result_character_number);
			holder.femaleNumber = (TextView)convertView.findViewById(R.id.result_female_number);
			holder.maleNumber = (TextView)convertView.findViewById(R.id.result_male_number);
			holder.outSiderNumber = (TextView)convertView.findViewById(R.id.result_outside_number);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		//TODO SetPic
		holder.playName.setText(playName.get(position));
		holder.uploderName.setText(uploderName.get(position));
		holder.characterNumber.setText(characterNumber.get(position));
		holder.femaleNumber.setText(femaleNumber.get(position));
		holder.maleNumber.setText(maleNumber.get(position));
		holder.outSiderNumber.setText(outSiderNumber.get(position));
		
		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView pic;
		TextView playName;
		TextView uploderName;
		TextView characterNumber;
		TextView femaleNumber;
		TextView maleNumber;
		TextView outSiderNumber;
	}
	
}