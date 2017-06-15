package com.ramytech.piaxi.auth;

import com.ramytech.piaxi.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BindYYActivity extends Activity{
	private ImageView buttonGo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bind_yy);
		buttonGo = (ImageView)findViewById(R.id.button_go);
		buttonGo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	

}
