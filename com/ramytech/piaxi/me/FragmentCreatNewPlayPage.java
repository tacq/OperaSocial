package com.ramytech.piaxi.me;

import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentCreatNewPlayPage extends Fragment{
	private View view;
	private EditText fileName;
	private EditText briefIntro;
	private CheckBox originalPlay;
	private TextView countCharacters;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_creat_new_play_page, container, false);
		fileName = (EditText)view.findViewById(R.id.new_play_name);
		briefIntro = (EditText)view.findViewById(R.id.brief_intro_edit_text);
		originalPlay = (CheckBox)view.findViewById(R.id.original_check_box);
		countCharacters = (TextView)view.findViewById(R.id.count_characters);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("剧本设置(1/3)");
		briefIntro.addTextChangedListener(new TextWatcher(){

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO 动态显示字符数？或者汉字字数。当前显示汉字字数，一个标点也算一个字
				
				countCharacters.setText(briefIntro.getText().length()+"/100");
			}
			
		});
		
		return view;
	}
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.next_step_menu, menu);
		menu.findItem(R.id.menu_next_step).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_next_step:
				saveData();
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				transaction.replace(R.id.mainContainer,new FragmentNewPlaySettingSecondStep());
				transaction.addToBackStack(null);
				transaction.commit();
				break;
		}
		return true;
	}
	
	private void saveData()
	{
		//TODO save file name brief introduction and whether original
	}
}
