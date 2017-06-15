package com.ramytech.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.piaxi.R;

public class IllustrationAdapter extends BaseAdapter{
	private Context context;
	
	private int[] preview1;
	private int[] preview2;
	private int[] preview3;
	private int[] more;
	
	private String[] name;
	private int[] count;
	
	
	public IllustrationAdapter(Context context, int[] preview1,int[] preview2,int[] preview3,int[] more,String[]name,int[] count)
	{
		this.context = context;
		this.preview1 = preview1;
		this.preview2 = preview2;
		this.preview3 = preview3;
		this.more = more;
		this.name = name;
		this.count = count;
	}
	@Override
	public int getCount() {
		return preview1.length;
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
			convertView = inflater.inflate(R.layout.illustration_item, null);
			holder = new ViewHolder();
			
			holder.preview1 = (ImageView)convertView.findViewById(R.id.illustration_item_preview1);
			holder.preview2 = (ImageView)convertView.findViewById(R.id.illustration_item_preview2);
			holder.preview3 = (ImageView)convertView.findViewById(R.id.illustration_item_preview3);
			holder.more = (ImageView)convertView.findViewById(R.id.illustration_item_more);
			holder.name = (TextView)convertView.findViewById(R.id.illustration_item_name);
			holder.count = (TextView)convertView.findViewById(R.id.illustration_item_count);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		if(this.count[position] >3)
		{
			holder.preview1.setImageDrawable(context.getResources().getDrawable(this.preview1[position]));
			holder.preview2.setImageDrawable(context.getResources().getDrawable(this.preview2[position]));
			holder.preview3.setImageDrawable(context.getResources().getDrawable(this.preview3[position]));
			holder.more.setImageDrawable(context.getResources().getDrawable(this.more[position]));
		}
		if(this.count[position] == 3)
		{
			holder.preview1.setImageDrawable(context.getResources().getDrawable(this.preview1[position]));
			holder.preview2.setImageDrawable(context.getResources().getDrawable(this.preview2[position]));
			holder.preview3.setImageDrawable(context.getResources().getDrawable(this.preview3[position]));
			holder.more.setImageDrawable(null);
		}
		
		if(this.count[position] == 2)
		{
			holder.preview1.setImageDrawable(context.getResources().getDrawable(this.preview1[position]));
			holder.preview2.setImageDrawable(context.getResources().getDrawable(this.preview2[position]));
			holder.preview3.setImageDrawable(null);
			holder.more.setImageDrawable(null);
		}
		
		if(this.count[position] == 1)
		{
			holder.preview1.setImageDrawable(context.getResources().getDrawable(this.preview1[position]));
			holder.preview2.setImageDrawable(null);
			holder.preview3.setImageDrawable(null);
			holder.more.setImageDrawable(null);
		}
		
		
		holder.name.setText(this.name[position]);
		holder.count.setText("共"+this.count[position]+"张");
		
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView preview1;
		ImageView preview2;
		ImageView preview3;
		ImageView more;
		
		TextView name;
		TextView count;
	}
}
