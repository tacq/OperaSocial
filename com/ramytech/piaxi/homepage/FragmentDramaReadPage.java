package com.ramytech.piaxi.homepage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ramytech.piaxi.R;

public class FragmentDramaReadPage extends Fragment {

	private View view;
	private String rid;// 传递过来的剧本唯一标识符rid

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_read_page, container,
				false);
		rid = getArguments().getString("rid");
		System.out.println("获取到了 rid:" + rid);

		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("剧本名称");
		return view;
	}
	
	

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.drama_read_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_peoplenearby).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_more).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
				break;
			case R.id.menu_peoplenearby:
				break;
			case R.id.menu_more:
				break;
		}
		return true;
	}

}
