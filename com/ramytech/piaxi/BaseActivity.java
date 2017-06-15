package com.ramytech.piaxi;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ramytech.android.util.ActivityCollection;

public class BaseActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		ActivityCollection.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityCollection.removeActivity(this);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
