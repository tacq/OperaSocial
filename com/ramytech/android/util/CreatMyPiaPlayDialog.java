package com.ramytech.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import com.ramytech.piaxi.R;
import com.ramytech.piaxi.me.FragmentCreatNewPlayPage;

public class CreatMyPiaPlayDialog {
	private Context context;
	private Activity activity;
	private Fragment fragment;
	
	private Button importPlay;
	private Button creatNewPlay;


	private View view;
	private PopupWindow popupWindow;
	
	
	public CreatMyPiaPlayDialog(Fragment fragment,Activity activity)
	{
		this.activity = activity;
		this.fragment = fragment;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.creat_my_pia_play_dialog, null);
		
		importPlay = (Button)view.findViewById(R.id.import_button);
		creatNewPlay = (Button)view.findViewById(R.id.creat_new_play_button);
		
	
		importPlay.setOnClickListener(clickListener);
		creatNewPlay.setOnClickListener(clickListener);
         
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
       	 	case R.id.import_button:
       	 		popupWindow.dismiss();
       	 		break;
       	 	case R.id.creat_new_play_button:
       	 		popupWindow.dismiss();
	       	 	FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentCreatNewPlayPage());
			    transaction.addToBackStack(null);
			    transaction.commit();
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
	 
	 class poponDismissListener implements PopupWindow.OnDismissListener
	 {  
		  
	        @Override  
	        public void onDismiss() {   
	            backgroundAlpha(1f);  
	        }  
	 }
	 
}
