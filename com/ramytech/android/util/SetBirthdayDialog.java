package com.ramytech.android.util;

import java.lang.reflect.Field;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.PopupWindow.OnDismissListener;

import com.ramytech.piaxi.R;

public class SetBirthdayDialog {
	private static String date;
	private String birthday;
	private Context context;
	private Activity activity;
	
	private ImageView back;
	private Button confirm;
	private DatePicker datePicker;
	
	private int year;
	private int month;
	private int day;
	
	private View view;
	private PopupWindow popupWindow;
	
	
	public SetBirthdayDialog(Activity activity, String birthday)
	{
		this.activity = activity;
		this.context = activity.getBaseContext();
		this.birthday = birthday;
		
	}
	
	public void show()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.set_birthday_dialog, null);
		
		back = (ImageView)view.findViewById(R.id.back_button_choose_gender_dialog);
		confirm = (Button)view.findViewById(R.id.confirm_birthday_button);
		datePicker = (DatePicker)view.findViewById(R.id.data_picker);
		
		initial(birthday);
        
		back.setOnClickListener(clickListener);
		confirm.setOnClickListener(clickListener);
         
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
       	 	case R.id.back_button_choose_gender_dialog:
       	 		popupWindow.dismiss();
       	 		TextView birthDate = (TextView)(activity).findViewById(R.id.birthday);
       	 		birthDate.setText(birthday);
       	 		break;
       	 	case R.id.confirm_birthday_button:
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
	 
	 class poponDismissListener implements PopupWindow.OnDismissListener
	 {  
		  
	        @Override  
	        public void onDismiss() {   
	            backgroundAlpha(1f);  
	        }  
	 }
	 
	 public void initial(final String birthday)
	 {
		
		 if(birthday.equals(""))
		 {
			 Calendar calendar=Calendar.getInstance();
			 year=calendar.get(Calendar.YEAR);
			 month = calendar.get(Calendar.MONTH);
			 day = calendar.get(Calendar.DAY_OF_MONTH);
		 }
		 else
		 {
			 String[] strarray=birthday.split("-");
			 year = Integer.parseInt(strarray[0]);
			 month = Integer.parseInt(strarray[1])-1;
			 day = Integer.parseInt(strarray[2]);
			 System.out.println("month:"+month+"day:"+day);
		 }
		 
		 datePicker.init(year, month, day, new OnDateChangedListener(){

	            public void onDateChanged(DatePicker view, int year,
	                    int monthOfYear, int dayOfMonth) {
	            	TextView birthday = (TextView)(activity).findViewById(R.id.birthday);
	            	String month;
	            	String day;
	            	if(monthOfYear<10)
	            	{
	            		month = "0"+(monthOfYear+1);
	            	}
	            	else
	            	{
	            		month = ""+(monthOfYear+1);
	            	}
	            	if(dayOfMonth<10)
	            	{
	            		day = "0"+dayOfMonth;
	            	}
	            	else
	            	{
	            		day = ""+ dayOfMonth;
	            	}
	            	date = year+"-"+month+"-"+day;
	            	birthday.setText(year+"-"+month+"-"+day);
	            }
	            
	        });
		 setDatePickerDividerColor(datePicker);
	 }
	 
	 public String getDate()
	 {
		 return date;
	 }
	 
	 //change datePicker divider color
	 private void setDatePickerDividerColor(DatePicker datePicker){         
	        LinearLayout llFirst = (LinearLayout) datePicker.getChildAt(0);
	        LinearLayout mSpinners      = (LinearLayout) llFirst.getChildAt(0);
	        
	        for (int i = 0; i < mSpinners.getChildCount(); i++) {
	            NumberPicker picker = (NumberPicker) mSpinners.getChildAt(i); 
	            Field[] pickerFields = NumberPicker.class.getDeclaredFields();
	            for (Field pf : pickerFields) {
	                if (pf.getName().equals("mSelectionDivider")) {
	                    pf.setAccessible(true);
	                    try {
	                        pf.set(picker,new ColorDrawable(activity.getResources().getColor(R.color.red)));
	                    } catch (IllegalArgumentException e) {
	                        e.printStackTrace();
	                    } catch (NotFoundException e) {
	                        e.printStackTrace();
	                    } catch (IllegalAccessException e) {
	                        e.printStackTrace();
	                    }
	                    break;
	                }
	            }
	        }
	    }
}
