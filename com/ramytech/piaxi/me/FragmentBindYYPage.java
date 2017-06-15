package com.ramytech.piaxi.me;

import com.ramytech.android.util.BindSuccessDialog;
import com.ramytech.android.util.ErrorToast;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.R.id;
import com.ramytech.piaxi.R.layout;
import com.ramytech.piaxi.R.menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class FragmentBindYYPage extends Fragment{
	
	private View view;
	private EditText yyNumber;
	private ImageView sendCode;
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_bind_yy_page, container, false);
		yyNumber = (EditText)view.findViewById(R.id.yy_number);
		sendCode = (ImageView)view.findViewById(R.id.send_code);
		
		sendCode.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new ErrorToast("已提交，请耐心等待工\n作人员审核",getActivity().getBaseContext()).showErrorToast();
			}
			
		});
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("YY绑定");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.clear();
		inflater.inflate(R.menu.bind_success_menu, menu);
		menu.findItem(R.id.menu_bind_success).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_bind_success:
				//TODO save data
				new BindSuccessDialog(getActivity()).show();
				break;
		}
		return true;
	}
}
