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

public class MemberListAdapter extends BaseAdapter{
	private Context context;
	
	private int[] headIcon;
	private String[] userName;
	private int[] userLevel;
	private int[] userStatus;
	private int[] invite;
	
	
	public MemberListAdapter(Context context, int[] headIcon,String[] userName,int[] userLevel,int[] userStatus,int[] invite)
	{
		this.context = context;
		this.headIcon = headIcon;
		this.userName = userName;
		this.userLevel = userLevel;
		this.userStatus = userStatus;
		this.invite = invite;
	}
	
	@Override
	public int getCount() {
		return headIcon.length;
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
			convertView = inflater.inflate(R.layout.member_list_item, null);
			holder = new ViewHolder();
			holder.headIcon = (ImageView)convertView.findViewById(R.id.member_headicon_member_list_item);
			holder.userName = (TextView)convertView.findViewById(R.id.member_name_member_list_item);
			holder.userLevel = (ImageView)convertView.findViewById(R.id.member_level);
			holder.userStatus = (ImageView)convertView.findViewById(R.id.member_status_member_list_item);
			holder.invite = (ImageView)convertView.findViewById(R.id.whether_join_in);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
	
		
		holder.headIcon.setImageDrawable(context.getResources().getDrawable(this.headIcon[position]));
		holder.userName.setText(userName[position]);
		
		if(userLevel[position]!=-1)	
			holder.userLevel.setImageDrawable(context.getResources().getDrawable(this.userLevel[position]));
		else
			holder.userLevel.setImageDrawable(null);
		
		if(userStatus[position]!=-1)	
			holder.userStatus.setImageDrawable(context.getResources().getDrawable(this.userStatus[position]));
		else
			holder.userStatus.setImageDrawable(null);
		
		if(invite[position]!=-1)	{
			holder.invite.setImageDrawable(context.getResources().getDrawable(this.invite[position]));
			holder.invite.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					// TODO add click event
					Toast.makeText(context, "做点啥", Toast.LENGTH_SHORT).show();
					
				}
				
			});
		}
		else
			holder.invite.setImageDrawable(null);
		
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView headIcon;
		TextView userName;
		ImageView userLevel;
		ImageView userStatus;
		ImageView invite;
	}
}
