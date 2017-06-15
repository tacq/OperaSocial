package com.ramytech.piaxi.auth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramytech.android.util.ErrorToast;
import com.ramytech.android.util.Validator;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.BaseActivity;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.auth.PhoneRegisterActivity.TimeCount;

public class ForgetPasswordActivity extends BaseActivity{
	private EditText phonenum;
	private EditText code;
	private ImageView codeButton;
	private TextView cutDown;
	private TimeCount time;
	
	private ActionBar actionBar;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_password);
		
		phonenum = (EditText)this.findViewById(R.id.login_phonenum);
		code = (EditText)this.findViewById(R.id.forget_password_identifying_code);
		cutDown = (TextView)this.findViewById(R.id.forget_password_cut_down);
		codeButton = (ImageView)this.findViewById(R.id.forget_password_button_send_code);
		
		actionBar = getActionBar();
		actionBar.show();
				
		codeButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(new Validator().isPhone(phonenum.getText().toString()))
				{
					sendCode();
					//TODO send code
					//构造CountDownTimer对象
					time = new TimeCount(61000, 1000);
					time.start();
						
					codeButton.setImageDrawable(getResources().getDrawable(R.drawable.buttonsendagain));
				}
				else
				{
					new ErrorToast(R.drawable.bgremindwhite,R.drawable.iconremind,"请输入11位的手机号码",getBaseContext()).showErrorToast();
				}

			}	
		});
	}
		
	class TimeCount extends CountDownTimer {
		
		public TimeCount(long millisInFuture, long countDownInterval) {
			//时间总长，计时时间间隔
			super(millisInFuture, countDownInterval);
		}
		
		@Override
		public void onFinish() {
			//计时完毕时触发
			cutDown.setText("00:00");
			codeButton.setEnabled(true);
		}
		
		@Override
		public void onTick(long millisUntilFinished){
			//计时过程显示
			if(millisUntilFinished /1000 == 60)
				cutDown.setText("01:00");
			else if(millisUntilFinished /1000 >9)
				cutDown.setText("00:"+millisUntilFinished /1000);
			else
				cutDown.setText("00:0"+millisUntilFinished /1000);
				codeButton.setEnabled(false);
		} 
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.next_step_menu, menu);
		menu.findItem(R.id.menu_next_step).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_next_step:
				if(checkCode(code.getText().toString(),phonenum.getText().toString()))
				{
					Intent intent = new Intent(ForgetPasswordActivity.this,ResetPasswordActivity.class);
					startActivity(intent);
				}
				else
				{
					new ErrorToast(R.drawable.bgremindwhite,R.drawable.iconremind,"验证码错误",getBaseContext()).showErrorToast();
				}
				break;
		}
		return true;
	}
	
	public boolean checkCode(String code,String phonenum)
	{
		//TODO  check code && check phonenum
		if(code.equals("031211"))
			return true;
		else
			return false;
	}
	
	private void sendCode()
	{
		BGTask task = new BGTask(true, null, "s04?t=1", ForgetPasswordActivity.this) {
			 
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
					time = new TimeCount(61000, 1000);
					time.start();	
					codeButton.setImageDrawable(getResources().getDrawable(R.drawable.buttonsendagain));
				}
			};		
			task.setParam("pno", phonenum.getText().toString());			
			task.execute();
	}
	
}
