package com.ramytech.piaxi.me;

import com.ramytech.android.util.ErrorToast;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentSettingPage extends Fragment implements OnClickListener{
	
	private View view;
	private LinearLayout helpCenter;
	private LinearLayout aboutUs;
	private LinearLayout cleanCache;
	private LinearLayout feedBack;
	private ImageView newVersion;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_setting_page, container, false);
		helpCenter = (LinearLayout)view.findViewById(R.id.help_center);
		aboutUs = (LinearLayout)view.findViewById(R.id.about_us);
		cleanCache = (LinearLayout)view.findViewById(R.id.clean_cache);
		feedBack = (LinearLayout)view.findViewById(R.id.feed_back);
		newVersion = (ImageView)view.findViewById(R.id.new_version);
		
		helpCenter.setOnClickListener(this);
		aboutUs.setOnClickListener(this);
		cleanCache.setOnClickListener(this);
		feedBack.setOnClickListener(this);
		newVersion.setOnClickListener(this);
		
		getActivity().getActionBar().setTitle("设置");
		return view;
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.help_center:
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentHelpCenterPage());
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
			case R.id.about_us:
				FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
			    transaction1.replace(R.id.mainContainer,new FragmentAboutUsPage());
			    transaction1.addToBackStack(null);
			    transaction1.commit();
				break;
			case R.id.clean_cache:
				//TODO clean cache
				new ErrorToast("已清除10.8mb缓存",getActivity().getBaseContext()).showErrorToast();
				break;
			case R.id.feed_back:
				FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
			    transaction2.replace(R.id.mainContainer,new FragmentFeedBackPage());
			    transaction2.addToBackStack(null);
			    transaction2.commit();
				break;
			case R.id.new_version:
				//TODO update app
				break;
		}
	}
	
}
