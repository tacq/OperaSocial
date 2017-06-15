package com.ramytech.piaxi.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ramytech.piaxi.R;

public class FragmentNewPlaySettingFinalStep extends Fragment implements OnClickListener{
	private View view;
	
	private EditText roleName;
	private LinearLayout newCharacterSetting;
	private LinearLayout roleColor;
	private LinearLayout roleType;
	
	private ImageView addCharacter;
	
	private int visible = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_new_play_setting_final_step, container, false);
		
		addCharacter = (ImageView)view.findViewById(R.id.add_character);
		roleName = (EditText)view.findViewById(R.id.role_name);
		newCharacterSetting = (LinearLayout)view.findViewById(R.id.new_character_setting);
		roleColor = (LinearLayout)view.findViewById(R.id.role_color_layout);
		roleType = (LinearLayout)view.findViewById(R.id.role_type_layout);
		
		newCharacterSetting.setVisibility(View.GONE);
		addCharacter.setOnClickListener(this);
		roleColor.setOnClickListener(this);
		roleType.setOnClickListener(this);
		
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("剧本设置(3/3)");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.next_step_menu, menu);
		menu.findItem(R.id.menu_next_step).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_next_step).setTitle("写剧本");
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_next_step:
				break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
			case R.id.add_character:
				addCharacter();
				break;
			case R.id.role_color_layout:
				
				break;
			case R.id.role_type_layout:
				break;
		}
	}
	
	public void addCharacter()
	{
		if(visible ==0)
		{
			newCharacterSetting.setVisibility(View.VISIBLE);
			visible = 1;
		}
		else
		{
			newCharacterSetting.setVisibility(View.GONE);
			visible = 0;
		}
		
	}
	
}
