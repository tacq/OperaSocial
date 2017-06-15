package com.ramytech.piaxi.me;

import java.util.ArrayList;
import java.util.List;

import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentUserCenterPage extends Fragment{
	
	private View view;
	
	private TextView userName;
	private TextView explanation;
	private ImageView headIcon;
	private Button buttonOpen;
	private ListView userPrivilegeList;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_user_center_page, container, false);
		
		userName = (TextView)view.findViewById(R.id.user_name);
		explanation = (TextView)view.findViewById(R.id.explanation);
		headIcon = (ImageView)view.findViewById(R.id.head_icon);
		buttonOpen = (Button)view.findViewById(R.id.button_open);
		userPrivilegeList = (ListView)view.findViewById(R.id.user_privilege);
		
		initialUserInfo();
		
		getActivity().getActionBar().setTitle("会员中心");
		
		userPrivilegeList.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData()));
		userPrivilegeList.setBackgroundColor(Color.WHITE);
		
		return view;
	}
	
	 private List<String> getData()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  特权1  ");
	        data.add("  特权2  ");
	        data.add("  特权3  ");
	        data.add("  特权4  ");
	        data.add("  特权5  ");
	        return data;
	 }
	 
	private void initialUserInfo()
	{
		//TODO set user's name / headIcon /explanation 
	}
}
