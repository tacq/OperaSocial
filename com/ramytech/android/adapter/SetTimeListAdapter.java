package com.ramytech.android.adapter;

import java.util.LinkedList;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class SetTimeListAdapter extends BaseAdapter{
	private Context myContext;
	private LinkedList<String> timeName;
	private LinkedList<Integer> checkdPosition;
	
	public SetTimeListAdapter(Context context, LinkedList<Integer> checkdPosition)
	{
		myContext = context;
		this.checkdPosition = checkdPosition;
		initial();
	}
	
	private void initial()
	{
		timeName = new LinkedList<String>();
		timeName.add("古风");
		timeName.add("民国");
		timeName.add("现代");
		timeName.add("国外");
		timeName.add("仙穿");
		timeName.add("耽百");
	}
	
	public void update(LinkedList<Integer> checkdPosition)
	{
		this.checkdPosition = checkdPosition;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return timeName.size();
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
			final LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.set_time_list_item, null);
			holder = new ViewHolder();
			holder.selectedLanguage = (CheckBox)convertView.findViewById(R.id.language_checkbox);
			holder.timeName = (TextView)convertView.findViewById(R.id.time_name);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.timeName.setText(timeName.get(position));
		
		if(checkdPosition.get(position) ==1 )
		{
			holder.timeName.setTextColor(Color.RED);
			holder.selectedLanguage.setChecked(true);;
		}
		
		holder.selectedLanguage.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked)
					holder.timeName.setTextColor(Color.RED);
				else
					holder.timeName.setTextColor(Color.BLACK);
			}
			
		});
		
		return convertView;
	}
	
	static class ViewHolder 
	{
		TextView timeName;
		CheckBox selectedLanguage;
	}
}
