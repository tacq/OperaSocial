package com.ramytech.android.util;

import java.util.Map;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.ramytech.piaxi.MainActivity;

public class TabV4Fragment extends Fragment {
	protected int tabNum;
	private MainActivity mainActivity;
	
	protected Map<String, Object> params;
	protected String url;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		if (activity instanceof MainActivity) {
			mainActivity = (MainActivity)activity;
		}
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		mainActivity = null;
	}
	
	@Override
	public void onResume() {
		super.onResume();		
//		if (mainActivity != null) mainActivity.onTabResume(tabNum);
	}
	
	protected void pushNewFrag(TabV4Fragment frag) {
		if (mainActivity != null) {
			frag.tabNum = this.tabNum;
			mainActivity.pushNewFrag(frag);
		}
	}
	
	protected void finish() {
		if (mainActivity != null) {
			mainActivity.getSupportFragmentManager().popBackStack();
		}
	}
	
	protected void finish(int num) {
		if (mainActivity != null) {
			for (int i = 0; i < num; i ++)
				mainActivity.getSupportFragmentManager().popBackStack();
		}
	}
	
	protected void saveData(String key, Object obj) {
		if (mainActivity != null) {
			mainActivity.setData(key, obj);			
		}
	}
	
	protected Object getData(String key) {
		if (mainActivity != null) {
			return mainActivity.getData(key);
		}
		return null;
	}
	
	public void reloadData() {
		//do nothing
	}
	
}
