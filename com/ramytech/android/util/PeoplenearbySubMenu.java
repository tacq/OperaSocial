package com.ramytech.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ramytech.piaxi.R;

public class PeoplenearbySubMenu {
	private Context context;
	private Activity activity;
	
	private Button btn_nearby;
	private Button btn_readdrama;
	
	private View view;
	private PopupWindow popupWindow;
	
	public PeoplenearbySubMenu(Activity activity)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.popup_peoplenearby_sub_menu, null);
		
		btn_nearby = (Button) view.findViewById(R.id.btn_nearby);
		btn_readdrama = (Button) view.findViewById(R.id.btn_readdrama);
		
		btn_nearby.setOnClickListener(clickListener);
		btn_readdrama.setOnClickListener(clickListener);
         
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Rect outRect = new Rect();  
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect); 
        popupWindow.showAtLocation(view, Gravity.RIGHT|Gravity.TOP,0,activity.getActionBar().getHeight()+outRect.top);
        popupWindow.setOnDismissListener(new poponDismissListener()); 
        
	}
	 private View.OnClickListener clickListener = new View.OnClickListener() 
	 {
        
        @Override
        public void onClick(View v) {
       	 switch(v.getId())
       	 {
       	 	case R.id.btn_nearby:
       	 		Toast.makeText(context, "button_inspect", 1000).show();
       	 		//TODO add function for button inspect
       	 		popupWindow.dismiss();
                   break;
       	 	case R.id.btn_readdrama:
       	 		Toast.makeText(context, "btn_readdrama", 1000).show();
       	 		//TODO add function for button more
       	 		popupWindow.dismiss();
       	 		break;
       	 }
        }
	 };
	 
	 
	 class poponDismissListener implements PopupWindow.OnDismissListener{  
		  
	        @Override  
	        public void onDismiss() {   
	        }  
	          
	    }  
}
