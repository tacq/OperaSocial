package com.ramytech.android.adapter;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class OrganizationListAdapter extends BaseAdapter{
	
	private Context context;
	private int[] organizaitonIcon;
	private String[] organizationName;
	private String[] organizationIntroduction;
	
	private int[] member1;
	private int[] member2;
	private int[] member3;
	private int[] memberMore;
	
	private String[] countOfMember;
	
	public OrganizationListAdapter(Context context,int[] organizaitonIcon
	, String[] organizationName
	, String[] organizationIntroduction
	, int[] member1
	, int[] member2
	, int[] member3
	, int[] memberMore
	, String[] countOfMember)
	{
		this.context = context;
		this.organizaitonIcon = organizaitonIcon;
		this.organizationName = organizationName;
		this.organizationIntroduction = organizationIntroduction;
		this.member1 = member1;
		this.member2 = member2;
		this.member3 = member3;
		this.memberMore = memberMore;
		this.countOfMember = countOfMember;
	}

	@Override
	public int getCount() {
		return this.organizaitonIcon.length;
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
			convertView = inflater.inflate(R.layout.organizaiton_list_item, null);
			holder = new ViewHolder();
			holder.organizaitonIcon = (ImageView)convertView.findViewById(R.id.organization_photo);
			holder.organizationName = (TextView)convertView.findViewById(R.id.organization_name);
			holder.organizationIntroduction = (TextView)convertView.findViewById(R.id.original_introduction);
			
			holder.member1 = (ImageView)convertView.findViewById(R.id.member1_head_icon);
			holder.member2 = (ImageView)convertView.findViewById(R.id.member2_head_icon);
			holder.member3 = (ImageView)convertView.findViewById(R.id.member3_head_icon);
			holder.memberMore = (ImageView)convertView.findViewById(R.id.member_more);
			
			holder.countOfMember = (TextView)convertView.findViewById(R.id.count_of_member);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.organizaitonIcon.setImageDrawable(context.getResources().getDrawable(organizaitonIcon[position]));
		holder.organizationName.setText(organizationName[position]);
		holder.organizationIntroduction.setText(organizationIntroduction[position]);
		
		if(member1[position]!=0)
			holder.member1.setImageDrawable(context.getResources().getDrawable(member1[position]));
		else
			holder.member1.setImageDrawable(null);
		
		if(member2[position]!=0)
			holder.member2.setImageDrawable(context.getResources().getDrawable(member2[position]));
		else
			holder.member2.setImageDrawable(null);
		
		if(member3[position]!=0)
			holder.member3.setImageDrawable(context.getResources().getDrawable(member3[position]));
		else
			holder.member3.setImageDrawable(null);
		if(memberMore[position]!=0)
			holder.memberMore.setImageDrawable(context.getResources().getDrawable(memberMore[position]));
		else
			holder.memberMore.setImageDrawable(null);
		
		holder.countOfMember.setText(countOfMember[position]);
		
		return convertView;
	}
	
	static class ViewHolder
	{
		ImageView  organizaitonIcon;
		TextView organizationName;
		TextView organizationIntroduction;
		
		ImageView member1;
		ImageView member2;
		ImageView member3;
		ImageView memberMore;
		
		TextView countOfMember;
	}

}
