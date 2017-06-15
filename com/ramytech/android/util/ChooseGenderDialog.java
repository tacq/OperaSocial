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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

public class ChooseGenderDialog {
	
	private String gender;
	
	private Context context;
	private Activity activity;
	
	private ImageView back;
	private ImageView[] genderChoice = new ImageView[3];
	
	private View view;
	private PopupWindow popupWindow;
	
	private int currentGender;
	
	public ChooseGenderDialog(Activity activity, String gender )
	{
		this.activity = activity;
		this.gender = gender;
		this.context = activity.getBaseContext();
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.choose_gender_dialog, null);
		
		back = (ImageView)view.findViewById(R.id.back_button_choose_gender_dialog);
		genderChoice[0] = (ImageView)view.findViewById(R.id.choice_boy);
		genderChoice[1] = (ImageView) view.findViewById(R.id.choice_secret_gender);
		genderChoice[2] = (ImageView) view.findViewById(R.id.choice_girl); 
		
		initial(gender);
        
		back.setOnClickListener(clickListener);
		genderChoice[0].setOnClickListener(clickListener);
		genderChoice[1].setOnClickListener(clickListener);
		genderChoice[2].setOnClickListener(clickListener);
         
        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.my_popwindow_anim_style);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(view, Gravity.CENTER,0,0);
        popupWindow.setOnDismissListener(new OnDismissListener()
        {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				TextView genderTextView = (TextView)activity.findViewById(R.id.gender);
				genderTextView.setText(getGender());
				backgroundAlpha(1f);  
			}
        });
        backgroundAlpha(0.5f);
	}
	 private View.OnClickListener clickListener = new View.OnClickListener() 
	 {
        @Override
        public void onClick(View v) {
       	 switch(v.getId())
       	 {
       	 	case R.id.back_button_choose_gender_dialog:
       	 		popupWindow.dismiss();
       	 		break;
       	 	case R.id.choice_boy:
       	 		changeGender(0,currentGender);
       	 		currentGender = 0;
       	 		break;
       	 	case R.id.choice_secret_gender:
	       	 	changeGender(1,currentGender);
	   	 		currentGender = 1;
       	 		break;
       	 	case R.id.choice_girl:
	       	 	changeGender(2,currentGender);
	   	 		currentGender = 2;
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
	 
	 public void initial(String gender)
	 {
		 if(gender.equals("男"))
		 {
			 genderChoice[0].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonmalered));
			 genderChoice[1].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonsecretgrey));
			 genderChoice[2].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonfemalegrey));
			 currentGender = 0;
		 }
		 if(gender.equals("保密"))
		 {
			 genderChoice[0].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonmalegrey));
			 genderChoice[1].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonsecretred));
			 genderChoice[2].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonfemalegrey)); 
			 currentGender = 1;
		 }
		 if(gender.equals("女"))
		 {
			 genderChoice[0].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonmalegrey));
			 genderChoice[1].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonsecretgrey));
			 genderChoice[2].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonfemalered));
			 currentGender = 2;
		 }
	 }
	 
	 public void setGender(String gender)
	 {
		 this.gender = gender;
	 }
	 
	 public String getGender()
	 {
		 return gender;
	 }
	 
	 public void changeGender(int newGender, int oldGedner)
	 {
		 switch(newGender)
		 {
		 	case 0:
		 		setGender("男");
		 		genderChoice[0].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonmalered));
		 		break;
		 	case 1:
		 		setGender("保密");
		 		genderChoice[1].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonsecretred));
		 		break;
		 	case 2:
		 		setGender("女");
		 		genderChoice[2].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonfemalered));
		 		break;
		 }
		 switch(oldGedner)
		 {
		 	case 0:
		 		genderChoice[0].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonmalegrey));
		 		break;
		 	case 1:
		 		genderChoice[1].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonsecretgrey));
		 		break;
		 	case 2:
		 		genderChoice[2].setImageDrawable(context.getResources().getDrawable(R.drawable.buttonfemalegrey)); 
		 		break;
		 }
	 }
}
