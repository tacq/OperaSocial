package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.MemberListAdapter;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentAddMemberPage extends Fragment{
	private View view;
	private ListView memberListView;
	
	private int[] headIcon;
	private String[] userName;
	private int[] userLevel;
	private int[] userStatus;
	private int[] invite;

	private String condition;
	
	public FragmentAddMemberPage(String condition)
	{
		//根据condition获取能添加成员的信息
		this.condition = condition;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_add_member_page, container, false);
		memberListView = (ListView)view.findViewById(R.id.add_member_list);
		
		initial();
		
		MemberListAdapter memberListAdapter = new MemberListAdapter(getActivity().getBaseContext(),headIcon,userName,userLevel,userStatus,invite);
		memberListView.setAdapter(memberListAdapter);
		
		
		
		getActivity().getActionBar().setTitle("添加成员");
		return view;
	}
	
	private void initial()
	{
		//TODO get new member info
		headIcon = new int[3];
		userName = new String[3];
		
		userLevel = new int[3];
		userStatus = new int[3];
		invite = new int[3];
		
		for(int  i = 0; i< 3 ; i++)
		{
			headIcon[i] =  R.drawable.head;
		}
		userName[0] = "黑山";
		userName[1] = "兔搞搞";
		userName[2] = "断背";
		
		userLevel[0] = R.drawable.vip1;
		userLevel[1] = R.drawable.vip2;
		userLevel[2] = R.drawable.vip3;
		
		userStatus[0] = R.drawable.iconpainter;
		userStatus[1] = R.drawable.iconauthor2;
		userStatus[2] = R.drawable.iconvoicer2;
		
		invite[0] = R.drawable.buttoninvite;
		invite[1] = R.drawable.buttonjoined;
		invite[2] = -1;
		
	}
}
