package com.ramytech.piaxi.me;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentWritePlay extends Fragment{
	
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_write_play_page, container, false);
		
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("写剧本");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.my_pia_play_menu, menu);
		menu.findItem(R.id.menu_creat_my_pia_play).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_edit).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_creat_my_pia_play).setIcon(getActivity().getResources().getDrawable(R.drawable.buttonsavenovel));
		menu.findItem(R.id.menu_creat_my_pia_play).setTitle("");
		
		menu.findItem(R.id.menu_edit).setTitle("发布");
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_creat_my_pia_play:
				break;
			case R.id.menu_edit:
				break;
		}
		return true;
	}
}
