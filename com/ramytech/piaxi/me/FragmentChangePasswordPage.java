package com.ramytech.piaxi.me;


import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FragmentChangePasswordPage extends Fragment implements OnClickListener{
	private View view;
	private EditText oldPassword;
	private EditText newPassword;
	private EditText confirmPassword;
	private Button buttonSave;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_change_password_page, container, false);
		oldPassword = (EditText)view.findViewById(R.id.current_password);
		newPassword = (EditText)view.findViewById(R.id.new_password);
		confirmPassword = (EditText)view.findViewById(R.id.confirm_password);	
		buttonSave = (Button)view.findViewById(R.id.save_password_button);
		buttonSave.setOnClickListener(this);
		getActivity().getActionBar().setTitle("修改密码");
		return view;
	}
	
	private boolean checkNewPassword(String password, String confirm_password)
	{
		if(password.equals(confirm_password))
			return true;
		else
			return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.save_password_button:
				if(checkNewPassword(newPassword.getText().toString(),confirmPassword.getText().toString()))
					changePassword();
				break;
		}
	}
	
	private void changePassword() {
		BGTask task = new BGTask(true, null, "s04?t=7", getActivity()) {
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				System.out.println("reg succ=" + resObj.toString());
			}
		};
		
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("passwdOld", oldPassword.getText().toString());
		task.setParam("passwdNew", confirmPassword.getText().toString());
		task.execute();
	}
	
}
