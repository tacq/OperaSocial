package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.PiaMoneyTopUpListAdapter;
import com.ramytech.android.util.ChoosePayWayPopWindow;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class FragmentMyPiaMoneyPage extends Fragment{
	
	private View view;
	private TextView userName;
	private TextView explanation;
	private TextView balance;
	private TextView balanceExplanation;
	private ImageView headIcon;
	private ListView buyPiaCoinList;

	private String[] piaCoin = {"600PIA币","1200PIA币","1800PIA币","3000PIA币","6000PIA币","10000PIA币"};
	private String[] money= {"    6元    ","    12元    ","    18元    ","    30元    ","    60元    ","    100元    "};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_my_pia_money_page, container, false);
		headIcon = (ImageView)view.findViewById(R.id.my_pia_money_head_icon);
		userName = (TextView)view.findViewById(R.id.my_pia_money_user_name);
		explanation = (TextView)view.findViewById(R.id.my_pia_money_user_explanation);
		balance = (TextView)view.findViewById(R.id.balance);
		balanceExplanation = (TextView)view.findViewById(R.id.balance_explanation);
		initialUserInfo();
		
		buyPiaCoinList = (ListView)view.findViewById(R.id.pia_money_top_up_list);
		buyPiaCoinList.setAdapter(new PiaMoneyTopUpListAdapter(getActivity().getBaseContext(),piaCoin,money));
		buyPiaCoinList.setBackgroundColor(Color.WHITE);
		buyPiaCoinList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				new ChoosePayWayPopWindow(getActivity()).show();
			}
			
		});
		
		getActivity().getActionBar().setTitle("我的PIA币");
		return view;
	}
	
	private void initialUserInfo()
	{
		//TODO initial user base info 
	}
	

}
