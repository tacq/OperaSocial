package com.ramytech.piaxi.auth;

import com.ramytech.android.util.Validator;
import com.ramytech.piaxi.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class RegistrantInfoActivity extends Activity{
	private EditText nickName;
	private EditText password;
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrant_info);
		
		nickName = (EditText)findViewById(R.id.set_password_edit_text);
		password = (EditText)findViewById(R.id.nick_name_edit_text);

		actionBar = getActionBar();
		actionBar.show();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.next_step_menu, menu);
		menu.findItem(R.id.menu_next_step).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_next_step:
				if(checkInfo(nickName.getText().toString(),password.getText().toString()))
				{
					Intent intent = new Intent(RegistrantInfoActivity.this,BindYYActivity.class);
					startActivity(intent);
				}
				break;
		}
		return true;
	}
	
	
	public boolean checkInfo(String name,String password)
	{
		Validator check = new Validator();
		if(check.isPassword(password)&&check.isEmpty(name))
		{
			//TODO save data
			return true;
		}
		else
		{
			if(check.isEmpty(name))
			{
			}
			return false;
		}
	}
	
}
