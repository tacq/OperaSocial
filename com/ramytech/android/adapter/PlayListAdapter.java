package com.ramytech.android.adapter;

import java.util.LinkedList;

import com.ramytech.android.adapter.ClassListAdapter.ViewHolder;
import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayListAdapter extends BaseAdapter{
	private Context myContext;
	private LinkedList<Integer> playPicId;
	private LinkedList<String> playNameList;
	private LinkedList<String> playWriterList;
	private LinkedList<Integer> playRidList;
	
	public PlayListAdapter(Context context,LinkedList<Integer> picId,LinkedList<String>playNameList,LinkedList<String> playWriterList,LinkedList<Integer> playRidList)
	{
		this.myContext = context;
		this.playPicId = picId;
		this.playNameList = playNameList;
		this.playWriterList = playWriterList;
		this.playRidList = playRidList;
	}
	
	public void updataData(LinkedList<Integer> picId,LinkedList<String>playNameList,LinkedList<String> playWriterList,LinkedList<Integer> playRidList)
	{
		this.playPicId = picId;
		this.playNameList = playNameList;
		this.playWriterList = playWriterList;
		this.playRidList = playRidList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return playPicId.size();
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
			convertView = inflater.inflate(R.layout.item_play_list, null);
			holder = new ViewHolder();
			holder.playPic = (ImageView)convertView.findViewById(R.id.play_image);
			holder.playName = (TextView)convertView.findViewById(R.id.play_name);
			holder.playWriter = (TextView)convertView.findViewById(R.id.play_writer);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		if(playPicId.get(position) == -1)
			holder.playPic.setImageDrawable(myContext.getResources().getDrawable(R.drawable.nopic));
		else
			holder.playPic.setImageDrawable(myContext.getResources().getDrawable(playPicId.get(position)));
		holder.playName.setText(playNameList.get(position));
		holder.playWriter.setText(playWriterList.get(position));

		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView playPic;
		TextView playName;
		TextView playWriter;
	}
	
}
