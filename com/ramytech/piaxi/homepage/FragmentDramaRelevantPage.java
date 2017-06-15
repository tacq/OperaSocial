package com.ramytech.piaxi.homepage;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentDramaRelevantPage extends Fragment {
	
	private View view;
	private ListView lv_club_member;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_relevant_page, container, false);
		
		lv_club_member = (ListView) view.findViewById(R.id.lv_club_member);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("相关");	
		return view;
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.radar_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
				Toast.makeText(getActivity(), "button_radar", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}
}
