package com.ramytech.piaxi.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ramytech.android.util.ErrorToast;
import com.ramytech.android.util.Validator;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.BaseActivity;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.auth.ForgetPasswordActivity.TimeCount;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

public class ResetPasswordActivity extends BaseActivity{
	
	private EditText oldPassword;
	private EditText newPassword;
	private EditText confirmPassword;
	private ImageView saveChangeButton;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_reset_password);
		
		oldPassword = (EditText)findViewById(R.id.old_password);
		newPassword = (EditText)findViewById(R.id.set_new_password);
		confirmPassword = (EditText)findViewById(R.id.confirm_new_password);
		saveChangeButton = (ImageView)findViewById(R.id.save_new_password_button);
		
		//TODO get old password.
		
		
		saveChangeButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(checkPassword(newPassword.getText().toString(),confirmPassword.getText().toString()))
				{
					//TODO go somewhere
				}
			}
			
		});
		
		
	}
	
	public boolean checkPassword(String newPassword, String confrimPassword)
	{
		Validator check = new Validator();
		if(!(check.isEmpty(newPassword))&&newPassword.equals(confirmPassword)&&check.isPassword(newPassword))
		{
			// TODO save new password
			BGTask task = new BGTask(true, null, "s04?t=1", ResetPasswordActivity.this) {
				 
				 @Override
					protected void onPostExecute(String result){
						if (!TextUtils.isEmpty(result)) {
							try {
								JSONArray arr = new JSONArray(result);
								JSONObject jobj = arr.optJSONObject(0);
								//为1获取成功；
								if (jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == 1)
								{
									this.onSuccess(this, jobj.optJSONObject(DATA));
								}
								//获取失败
								else if(jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == -1)
								{
									this.onFail(this, jobj.getString(ERRMSG));
								}
								else if(jobj.has(ERRMSG))
								{
									this.onFail(this, jobj.getString(ERRMSG));
								} else if (jobj.has(ERRCODE)) {
									this.onFail(this, ReturnResult.values()[jobj.getInt(ERRCODE)].name());
								} else {
									this.onFail(this, result);
								}
								return;
							} catch (JSONException ex) {
								System.out.println("jsonerr, result=" + result);
							}
						}

						this.onFail(this, "数据获取失败");
					}
				 
					public void onSuccess(APIFunction api, Object resObj) {
						super.onSuccess(api, resObj);
						System.out.println("reg succ=" + resObj.toString());
					}
				};		
				task.setParam("pid", APIManager.shared().getUID());
				task.setParam("key", "");
				task.setParam("passwdOld","");
				task.setParam("passwdNew", newPassword);
				task.execute();
			
			return true;
		}
		else
		{
			if(check.isEmptyStr(newPassword))
			{
				new ErrorToast("密码不能为空",getBaseContext()).showErrorToast();
			}
			if(!newPassword.equals(confirmPassword))
			{
				new ErrorToast("密码输入不一致",getBaseContext()).showErrorToast();
			}
			if(!check.isPassword(newPassword))
			{
				new ErrorToast("密码格式不正确",getBaseContext()).showErrorToast();
			}
			
			return false;
		}
	}
	
}
