package com.ramytech.piaxi.me;


import com.ramytech.android.adapter.MyPiaPlayListAdapter;
import com.ramytech.android.util.CreatMyPiaPlayDialog;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FragmentMyPiaPlayPage extends Fragment{
	private View view;
	private ListView myPiaList;
	private int[]icon;
	private String[] name;
	private String[] date;
	private int[] button;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_my_pia_play_page, container, false);
		myPiaList = (ListView)view.findViewById(R.id.my_pia_play_list_list_view);
		
		initial();
		MyPiaPlayListAdapter myPiaPlayListAdapter = new MyPiaPlayListAdapter(getActivity().getBaseContext(),icon,name,date,button);
		myPiaList.setAdapter(myPiaPlayListAdapter);


		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("我的PIA本");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.my_pia_play_menu, menu);
		menu.findItem(R.id.menu_creat_my_pia_play).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_creat_my_pia_play:
				new CreatMyPiaPlayDialog(this,getActivity()).show();
				break;
			case R.id.menu_edit:
				//TODO edit something
				break;
		}
		return true;
	}
	private void initial()
	{
		//TODO load data of my pia play list;
		
		//test date
		icon = new int[2];
		name = new String[2];
		date = new String[2];
		button = new int[2];
		
		icon[0] = R.drawable.picnopic;
		icon[1] = R.drawable.picnopic;
		
		name[0] = "猎户家的小娘子";
		name[1] = "猎户家的小娘子";
		
		date[0] = "上传时间：2014年9月18日";
		date[1] = "上传时间：2014年9月18日";
		
		button[0] = R.drawable.buttonnotpublish;
		button[1] = R.drawable.buttonnotpublish;
	}
}
