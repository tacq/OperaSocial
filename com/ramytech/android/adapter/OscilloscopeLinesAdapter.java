package com.ramytech.android.adapter;

import java.util.LinkedList;
import java.util.Random;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ramytech.piaxi.R;

public class OscilloscopeLinesAdapter extends BaseAdapter{
	private Context myContext;
	private LinkedList<Integer> playPicId;
	private LinkedList<String> playNameList;
	private LinkedList<String> playWriterList;
	
	private int countLines = 30;
	
	public OscilloscopeLinesAdapter(Context context)
	{
		myContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return countLines;
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
			convertView = inflater.inflate(R.layout.oscilloscope_lines_item, null);
			holder = new ViewHolder();
			holder.singeLine = (ImageView)convertView.findViewById(R.id.oscilloscope_line);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}

		AnimatorSet animatorSet = new AnimatorSet();
		ObjectAnimator anim = ObjectAnimator.ofFloat(holder.singeLine, "scaleY",
					(float)(Math.random()*1.5),(float)(Math.random()*1.5));
		anim.setDuration(100);
		animatorSet.play(anim);
		animatorSet.start();
		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView singeLine;
	}
	
}
