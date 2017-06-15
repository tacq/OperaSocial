package com.ramytech.android.util;

import com.ramytech.piaxi.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class BindSuccessDialog {
	
	private Context context;
	private Activity activity;
	
	private Button gotIt;
	
	private View view;
	private PopupWindow popupWindow;
	
	public BindSuccessDialog(Activity activity)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.bind_success_dialog, null);
		
		gotIt = (Button) view.findViewById(R.id.got_it);
		gotIt.setOnClickListener(clickListener);
         
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new poponDismissListener()); 
        backgroundAlpha(0.5f);
        
	}
	 private View.OnClickListener clickListener = new View.OnClickListener() 
	 {
        
        @Override
        public void onClick(View v) {
       	 switch(v.getId())
       	 {
       	 	case R.id.got_it:
       	 		popupWindow.dismiss();
                   break;
       	 }
        }
	 };
	 
	 private void backgroundAlpha(float bgAlpha)  
	 {  
		 WindowManager.LayoutParams lp = activity.getWindow().getAttributes();  
		 lp.alpha = bgAlpha; //0.0-1.0  
		 activity.getWindow().setAttributes(lp);
	 }  
	 
	 class poponDismissListener implements PopupWindow.OnDismissListener{  
		  
	        @Override  
	        public void onDismiss() {   
	            backgroundAlpha(1f);  
	        }  
	          
	    }  
}
