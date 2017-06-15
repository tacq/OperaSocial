package com.ramytech.piaxi.writer;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.ramytech.android.util.ChoosePhotoDialog;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.me.FragmentBillPayPage;
import com.ramytech.piaxi.me.FragmentBindYYPage;
import com.ramytech.piaxi.me.FragmentCollectionPage;
import com.ramytech.piaxi.me.FragmentMessageEmailPage;
import com.ramytech.piaxi.me.FragmentMyAccountPage;
import com.ramytech.piaxi.me.FragmentMyOrganizationPage;
import com.ramytech.piaxi.me.FragmentMyPlayPage;
import com.ramytech.piaxi.me.FragmentPersonalInfoPage;
import com.ramytech.piaxi.me.FragmentSettingPage;

public class FragmentWriterPage extends Fragment implements OnClickListener{
	
	private View view;
	private ImageView edit;
	private ImageView headIcon;
	private ImageView myWorkButton;
	private ImageView myFavouriteButton;
	private ImageView myBillButton;
	private TextView userName;
	private TextView balance;
	private TextView originalPlay;
	private TextView boutiquePlay;
	private TextView hotPlay;
	private TextView published_play;

	private ListView mySettingItem;
	private ListView settingItem;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_me_homepage, container, false);
		edit = (ImageView)view.findViewById(R.id.button_profile);
		headIcon = (ImageView)view.findViewById(R.id.head_icon_me_page);
		userName = (TextView)view.findViewById(R.id.user_name_me_page);
		balance = (TextView)view.findViewById(R.id.balance_me_page);
		originalPlay = (TextView)view.findViewById(R.id.original_play);
		boutiquePlay = (TextView)view.findViewById(R.id.boutique_play);
		hotPlay = (TextView)view.findViewById(R.id.hot_play);
		published_play = (TextView)view.findViewById(R.id.published_play);
		myWorkButton = (ImageView)view.findViewById(R.id.button_my_work);
		myFavouriteButton = (ImageView)view.findViewById(R.id.button_my_favorite);
		myBillButton = (ImageView)view.findViewById(R.id.button_my_bill);
		mySettingItem = (ListView)view.findViewById(R.id.my_setting_item);
		settingItem = (ListView)view.findViewById(R.id.setting_item);
		
		edit.setOnClickListener(this);
		headIcon.setOnClickListener(this);
		myWorkButton.setOnClickListener(this);
		myFavouriteButton.setOnClickListener(this);
		myBillButton.setOnClickListener(this);
		
		mySettingItem.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData()));
		mySettingItem.setBackgroundColor(Color.WHITE);
		mySettingItem.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub  
				switch(arg2)
				{
					case 0://我的社团
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
					    transaction.replace(R.id.mainContainer,new FragmentMyOrganizationPage());
					    transaction.addToBackStack(null);
					    transaction.commit();
						break;
					case 1://我的账号
					    FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
					    transaction1.replace(R.id.mainContainer,new FragmentMyAccountPage());
					    transaction1.addToBackStack(null);
					    transaction1.commit();
						break;
				}
				
			}
			
		});
		
		settingItem.setAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,getData1()));
		settingItem.setBackgroundColor(Color.WHITE);
		settingItem.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				switch (arg2) {
				case 0:
					FragmentTransaction transaction1 = getFragmentManager()
							.beginTransaction();
					transaction1.replace(R.id.mainContainer,
							new FragmentBindYYPage());
					transaction1.addToBackStack(null);
					transaction1.commit();
					break;
				case 1:// 消息邮件
					FragmentTransaction transaction2 = getFragmentManager()
							.beginTransaction();
					transaction2.replace(R.id.mainContainer,
							new FragmentMessageEmailPage());
					transaction2.addToBackStack(null);
					transaction2.commit();
					break;
				case 2:// 设置
					FragmentTransaction transaction3 = getFragmentManager()
							.beginTransaction();
					transaction3.replace(R.id.mainContainer,
							new FragmentSettingPage());
					transaction3.addToBackStack(null);
					transaction3.commit();
					break;
				}

			}
			
		});
		getActivity().getActionBar().setTitle("我");	
		return view;
	}
	
	 private List<String> getData()
	 { 
	        List<String> data = new ArrayList<String>();
	        data.add("  我的社团");
	        data.add("  我的账号");
	        return data;
	 }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.button_profile:
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentPersonalInfoPage());
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
			case R.id.head_icon_me_page:
				new ChoosePhotoDialog(getActivity()).show();
				break;
			case R.id.button_my_work:
				FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
			    transaction1.replace(R.id.mainContainer,new FragmentMyPlayPage());
			    transaction1.addToBackStack(null);
			    transaction1.commit();
				break;
			case R.id.button_my_favorite:
				FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
			    transaction2.replace(R.id.mainContainer,new FragmentCollectionPage());
			    transaction2.addToBackStack(null);
			    transaction2.commit();
				break;
			case R.id.button_my_bill:
				FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
			    transaction3.replace(R.id.mainContainer,new FragmentBillPayPage());
			    transaction3.addToBackStack(null);
			    transaction3.commit();
				break;
		}
	}
	
	private List<String> getData1()
	{
		List<String> data = new ArrayList<String>();
        data.add("  YY绑定");
        data.add("  消息邮件");
        data.add("  设置");
        return data;
	}
}