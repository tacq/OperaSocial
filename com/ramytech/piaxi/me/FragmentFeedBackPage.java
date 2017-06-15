package com.ramytech.piaxi.me;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ramytech.android.util.ErrorToast;
import com.ramytech.android.util.Validator;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.auth.PhoneLoginActivity;

public class FragmentFeedBackPage extends Fragment{
	private View view;
	private EditText email;
	private EditText message;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_feed_back_page, container, false);
		email = (EditText)view.findViewById(R.id.feedback_email);
		message = (EditText)view.findViewById(R.id.feedback_message);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("意见反馈");
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.next_step_menu, menu);
		menu.findItem(R.id.menu_next_step).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_next_step).setTitle("发送");
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_next_step:
				sendMessage(email.getText().toString(),message.getText().toString());
				break;
		}
		return true;
	}
	
	private void sendMessage(String email, String message)
	{
		if(checkInfo(email,message));
		{
			feedBack();
			new ErrorToast("感谢您的反馈",getActivity().getBaseContext()).showErrorToast();
		}
	}
	
	private boolean checkInfo(String email,String message)
	{
		Validator validator = new Validator();
		if(!(validator.isEmail(email)))
		{
			new ErrorToast("邮箱格式错误",getActivity().getBaseContext()).showErrorToast();
			return false;
		}
		if(validator.isEmpty(message))
		{
			new ErrorToast("信息不能为空",getActivity().getBaseContext()).showErrorToast();
			return false;
		}
		return true;
	}
	
	private void feedBack(){
		BGTask task = new BGTask(true, null, "s12?t=1", FragmentFeedBackPage.this.getActivity()) {
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				System.out.println("reg succ=" + resObj.toString());
				//此处借口返回错误500
				
			}
		};

        task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("note", message.getText().toString());
		task.execute();
	}
}
