package com.ramytech.piaxi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.ramytech.android.util.WiperSwitch;
import com.ramytech.android.util.WiperSwitch.OnChangedListener;
import com.ramytech.piaxi.auth.PhoneLoginActivity;

/**
 * @author CazadorH
 * app登陆前首页
 *
 */
public class IndexActivity extends BaseActivity { 


	public static void actionStart(Context context) {		
		Intent intent = new Intent(context, IndexActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		
		((WiperSwitch)this.findViewById(R.id.phoneLogin)).setOnChangedListener(new OnChangedListener(){

			@Override
			public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
				if (checkState) {
					IndexActivity.this.startActivity(new Intent(IndexActivity.this, PhoneLoginActivity.class));
					wiperSwitch.setChecked(false);
				}
			}
			
		});
		
		((WiperSwitch)this.findViewById(R.id.qqLogin)).setOnChangedListener(new OnChangedListener(){

			@Override
			public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
				if (checkState) {
					//TODO qqlogin
					wiperSwitch.setChecked(false);
				}
			}
			
		});
		
		((WiperSwitch)this.findViewById(R.id.weiboLogin)).setOnChangedListener(new OnChangedListener(){

			@Override
			public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
				if (checkState) {
					//TODO weibo login
					wiperSwitch.setChecked(false);
				}
			}
			
		});
	}
}
