package com.ramytech.android.util;

import com.ramytech.piaxi.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class ChoosePhotoDialog {
	
	private Context context;
	private Activity activity;
	
	private LinearLayout takePhoto;
	private LinearLayout fromAlbum;
	private LinearLayout cancel;
	
	private View view;
	private PopupWindow popupWindow;
	
	public ChoosePhotoDialog(Activity activity)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.choose_upload_phone, null);
		
		takePhoto = (LinearLayout)view.findViewById(R.id.take_phone);
		fromAlbum = (LinearLayout)view.findViewById(R.id.choose_from_phone_gallery);
        cancel = (LinearLayout) view.findViewById(R.id.cancel);
         
        takePhoto.setOnClickListener(clickListener);
        fromAlbum.setOnClickListener(clickListener);
        cancel.setOnClickListener(clickListener);
         
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style2);
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
       	 	case R.id.take_phone:
       	 		popupWindow.dismiss();
       	 		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       	 		activity.startActivityForResult(intent, 1);//启动相机       	 		
       	 		break;
       	 	case R.id.choose_from_phone_gallery:
       	 		popupWindow.dismiss();
       	 		Intent intent1 = new Intent(Intent.ACTION_PICK, 
       	 		android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       	 		activity.startActivityForResult(intent1, 2);        	
       	 		break;
       	 	case R.id.cancel:
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
