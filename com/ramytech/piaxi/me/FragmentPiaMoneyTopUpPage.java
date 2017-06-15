package com.ramytech.piaxi.me;

import com.ramytech.android.adapter.PiaMoneyTopUpListAdapter;
import com.ramytech.android.util.ErrorToast;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.drawable;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;
import com.ramytech.piaxi.R.menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class FragmentPiaMoneyTopUpPage extends Fragment{

	private View view;
	private ListView topUpList;
	private String[] month = {"1个月","3个月","6个月","12个月","24个月"};
	private String[] explanation = {"体验价","8折，节省","8折，节省","7折，节省","6折，节省"};
	private String[] money= {"    1000PIA币    ","    3000PIA币    ","    6000PIA币    ","    10000PIA币    ","    20000PIA币    "};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_pia_money_top_up_page, container, false);
		setHasOptionsMenu(true);
		
		getActivity().getActionBar().setTitle("会员中心");
		topUpList = (ListView)view.findViewById(R.id.top_up_money_list);
		topUpList.setAdapter(new PiaMoneyTopUpListAdapter(getActivity().getBaseContext(),month,explanation,money));
		
		topUpList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				new ErrorToast(R.drawable.bgremindwhite,R.drawable.iconremind,"PIA币不够快去充值~",getActivity().getBaseContext()).showErrorToast();
			}
			
		});
		
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.pia_money_top_up_menu, menu);
		menu.findItem(R.id.menu_pia_money_top_up).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_pia_money_top_up:
				//TODO save data
				FragmentManager fm = getFragmentManager();
			    FragmentTransaction transaction = fm.beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentMyPiaMoneyPage());
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
		}
		return true;
	}
	
	
}
