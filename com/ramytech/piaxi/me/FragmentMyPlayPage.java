package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FragmentMyPlayPage extends Fragment{
	
	private View view;
	private ListView myPiaPlayList;
	private ListView myPiaPlayItemList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_my_play_page, container, false);
		myPiaPlayList = (ListView)view.findViewById(R.id.my_pia_play);
		myPiaPlayItemList = (ListView)view.findViewById(R.id.my_pia_play_item);
		
		myPiaPlayList.setBackgroundColor(Color.WHITE);
		myPiaPlayItemList.setBackgroundColor(Color.WHITE);
		
		myPiaPlayList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData1()));
		myPiaPlayItemList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData2()));
		
		myPiaPlayList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub  
				switch(arg2)
				{
					case 0://我的PIA本
						 FragmentTransaction transaction = getFragmentManager().beginTransaction();
						 transaction.replace(R.id.mainContainer,new FragmentMyPiaPlayPage());
						 transaction.addToBackStack(null);
						 transaction.commit();
						break;
				}
				
			}
		});
		
		myPiaPlayItemList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub  
				switch(arg2)
				{
					case 0://我的PIA本
//					    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//					    transaction.replace(R.id.mainContainer,new FragmentMyPiaPlayPage());
//					    transaction.addToBackStack(null);
//					    transaction.commit();
						break;
					case 1://我的PIA本
					    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
					    transaction1.replace(R.id.mainContainer,new FragmentIllustrationPage());
					    transaction1.addToBackStack(null);
					    transaction1.commit();
						break;
					case 2://我的PIA本
//					    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//					    transaction.replace(R.id.mainContainer,new FragmentUserCenterPage());
//					    transaction.addToBackStack(null);
//					    transaction.commit();
						break;
				}
				
			}
		});
		
		getActivity().getActionBar().setTitle("我的作品");
		return view;
	}
	 private List<String> getData1()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  我的PIA本  ");
	        return data;
	 }
	 
	 private List<String> getData2()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  PIA剧  ");
	        data.add("  插画集  ");
	        data.add("  其它  ");
	        return data;
	 }
}
