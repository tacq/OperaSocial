package com.ramytech.android.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.android.adapter.PlayListAdapter.ViewHolder;
import com.ramytech.piaxi.R;

public class UserILikeAdapter extends BaseAdapter{
	private Context myContext;
	private LinkedList<String> userPhoto;
	private LinkedList<String> userName;
	private LinkedList<String> userLevel;
	private LinkedList<String> idPhoto;
	private LinkedList<String> occupation;
	
	public UserILikeAdapter(Context context,
							LinkedList<String> userPhoto,
							LinkedList<String> userName,
							LinkedList<String> userLevel,
							LinkedList<String> idPhoto,
							LinkedList<String> occupation)
	{
		this.myContext = context;
		this.userPhoto = userPhoto;
		this.userName = userName;
		this.userLevel = userLevel;
		this.idPhoto = idPhoto;
		this.occupation = occupation;
	}
	
	public void updataData(LinkedList<String> userPhoto,
							LinkedList<String> userName,
							LinkedList<String> userLevel,
							LinkedList<String> idPhoto,
							LinkedList<String> occupation)
	{
		this.userPhoto = userPhoto;
		this.userName = userName;
		this.userLevel = userLevel;
		this.idPhoto = idPhoto;
		this.occupation = occupation;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userPhoto.size();
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
			convertView = inflater.inflate(R.layout.user_i_like_item, null);
			holder = new ViewHolder();
			holder.userPhoto = (ImageView)convertView.findViewById(R.id.head_icon_users_i_like);
			holder.userName = (TextView)convertView.findViewById(R.id.username_users_i_like);
			holder.userLevel = (ImageView)convertView.findViewById(R.id.users_level_users_i_like);
			holder.idPhoto = (ImageView)convertView.findViewById(R.id.occupation_photo_users_i_like);
			holder.occupation = (TextView)convertView.findViewById(R.id.occupation_users_i_like);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
//		holder.userPhoto
		holder.userName.setText(userName.get(position));
//		holder.userLevel
//		holder.idPhoto
		holder.occupation.setText(occupation.get(position));

		return convertView;
	}
	
	static class ViewHolder 
	{
		ImageView userPhoto;
		TextView userName;
		ImageView userLevel;
		ImageView idPhoto;
		TextView occupation;
	}
}
