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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ChoosePayWayPopWindow {
	
	private Context context;
	private Activity activity;
	
	private ImageView payByWeixin;
	private ImageView payByALi;
	private Button cancel;
	
	private View view;
	private PopupWindow popupWindow;
	
	public ChoosePayWayPopWindow(Activity activity)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.choose_pay_way_dialog, null);
		
		 payByWeixin = (ImageView)view.findViewById(R.id.pay_by_weixin);
         payByALi = (ImageView)view.findViewById(R.id.pay_by_zhifubao);
         cancel = (Button) view.findViewById(R.id.withdraw_pay_button);
         
         payByWeixin.setOnClickListener(clickListener);
         payByALi.setOnClickListener(clickListener);
         cancel.setOnClickListener(clickListener);
         
         popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
         popupWindow.setFocusable(true);
         popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style);
         popupWindow.setOutsideTouchable(true);
         popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
         popupWindow.setOnDismissListener(new poponDismissListener()); 
         backgroundAlpha(0.5f);
	}
	 private View.OnClickListener clickListener = new View.OnClickListener() 
	 {
        
        @Override
        public void onClick(View v) {
       	 switch(v.getId())
       	 {
       	 	case R.id.withdraw_pay_button:
       	 			popupWindow.dismiss();
                   break;
       	 	case R.id.pay_by_weixin:
       	 		Toast.makeText(context, "TODO用微信支付", Toast.LENGTH_SHORT).show();
       	 		break;
       	 	case R.id.pay_by_zhifubao:
       	 		Toast.makeText(context, "TODO用支付宝支付", Toast.LENGTH_SHORT).show();
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
