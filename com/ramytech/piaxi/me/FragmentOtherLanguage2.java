package com.ramytech.piaxi.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ramytech.piaxi.R;

public class FragmentOtherLanguage2 extends Fragment{
	private View view;
	private EditText edit;
	private static final String OTHER_LANGUAGE = "other_language_for_second_step";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_other_language2, container, false);
		edit = (EditText)view.findViewById(R.id.edit_text_other_language_page);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("其它");	
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.radar_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_radar).setTitle("保存").setIcon(null);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
				saveLanguage();
				break;
		}
		return true;
	}
	
	private void saveLanguage()
	{
		SharedPreferences sp = getActivity().getSharedPreferences(OTHER_LANGUAGE,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString(OTHER_LANGUAGE, edit.getText().toString());
		editor.commit();
		getActivity().onBackPressed();
	}
}
