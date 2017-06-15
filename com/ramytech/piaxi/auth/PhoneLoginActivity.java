package com.ramytech.piaxi.auth;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.ramytech.android.util.GPSTracker;
import com.ramytech.android.util.MD5;
import com.ramytech.android.util.UserSetting;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.piaxi.BaseActivity;
import com.ramytech.piaxi.MainActivity;
import com.ramytech.piaxi.R;

/**
 * @author CazadorH
 * 手机登陆页面
 *
 */
public class PhoneLoginActivity extends BaseActivity implements OnClickListener { 
	private EditText phoneView, pwView;
	private String latitude="0.0";  
	private String longitude ="0.0"; 

	public static void actionStart(Context context) {		
		Intent intent = new Intent(context, PhoneLoginActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phonelogin);
		
		this.findViewById(R.id.login).setOnClickListener(this);
		this.findViewById(R.id.register).setOnClickListener(this);
		this.findViewById(R.id.forgetpw).setOnClickListener(this);
		phoneView = (EditText) this.findViewById(R.id.phonenum);
		pwView = (EditText) this.findViewById(R.id.password);
		//获取手机当前经纬度
//		getLocation();
		getCurrentLocation();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login) {
			login();
		} else if (v.getId() == R.id.register) {
			//TODO jump to reg
			Intent intent = new Intent(PhoneLoginActivity.this,PhoneRegisterActivity.class);
			startActivity(intent);
		} else if (v.getId() == R.id.forgetpw) {
			//TODO jump to forget pw
			Intent intent = new Intent(PhoneLoginActivity.this,ForgetPasswordActivity.class);
			startActivity(intent);
		}
	}
	
	private void login() {
		BGTask task = new BGTask(true, null, "s04?t=3", PhoneLoginActivity.this) {
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				System.out.println("reg succ=" + resObj.toString());
				JSONObject obj = ((JSONObject) resObj);
				String uid = "" + obj.optInt("pid");
				String token = obj.optString("token");
				String ts = obj.optString("ts");
				int type = obj.optInt("type");
				APIManager.shared().setCredential(uid, MD5.genMD5(token+ts), pwView.getText().toString(), type);
				APIManager.shared().saveCredential(PhoneLoginActivity.this);
				Intent it = new Intent(PhoneLoginActivity.this, MainActivity.class);//skip first scan
				it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
				PhoneLoginActivity.this.startActivity(it);
			}
		};
		UserSetting.saveUsername(PhoneLoginActivity.this, phoneView.getText().toString());			
		task.setParam("pno", phoneView.getText().toString());			
		task.setParam("passwd", pwView.getText().toString());
		System.out.println("x:"+latitude+" y:"+longitude);
		task.setParam("x", latitude);
		task.setParam("y", longitude);
		task.execute();
	}
	
	private void getCurrentLocation()
	{
		GPSTracker gpsTracker = new GPSTracker(this);
		latitude = String.valueOf(gpsTracker.latitude);
		longitude = String.valueOf(gpsTracker.longitude);
	}
	
	//FIXME renyun for test, remember to delete
	private void test(){
		
	}
}
