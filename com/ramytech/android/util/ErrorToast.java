package com.ramytech.android.util;

import com.ramytech.piaxi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorToast {
	
	private LinearLayout layout;
	private ImageView iconRemind;
	private TextView errorMessageTextView;
	private Toast toast;
	private LayoutInflater layoutInflater;
	private View view;
	private Context myContext;
	
	public ErrorToast(int backgroundPicID,int iconRemindID,String errorMessage,Context context)
	{
		this.myContext = context;
		this.layoutInflater = LayoutInflater.from(this.myContext);
		view = layoutInflater.inflate(R.layout.phone_number_error, null);
		
		this.layout = (LinearLayout)view.findViewById(R.id.phone_number_error_layout);
		this.iconRemind =(ImageView)view.findViewById(R.id.icon_remind);
		this.errorMessageTextView = (TextView)view.findViewById(R.id.phone_num_error_message);
		
		this.layout.setBackgroundResource(backgroundPicID);
		this.iconRemind.setImageDrawable(context.getResources().getDrawable(iconRemindID));
		this.errorMessageTextView.setText(errorMessage);
		
		this.toast = new Toast(context);
		this.toast.setView(view);
	}
	
	public ErrorToast(String errorMessage,Context context)
	{
		this.myContext = context;
		this.layoutInflater = LayoutInflater.from(this.myContext);
		view = layoutInflater.inflate(R.layout.phone_number_error, null);
		
		this.layout = (LinearLayout)view.findViewById(R.id.phone_number_error_layout);
		this.iconRemind =(ImageView)view.findViewById(R.id.icon_remind);
		this.errorMessageTextView = (TextView)view.findViewById(R.id.phone_num_error_message);
		
		this.layout.setBackgroundResource(R.drawable.bgremindwhite);
		this.iconRemind.setImageDrawable(context.getResources().getDrawable(R.drawable.iconremind));
		this.errorMessageTextView.setText(errorMessage);
		
		this.toast = new Toast(context);
		this.toast.setView(view);
	}
	
	public void setBackgroundPic(int backgroundPicID)
	{
		if(view!=null)
			this.layout.setBackgroundResource(backgroundPicID);
		else
			return;
	}
	
	public void seticonRemindPic(int iconRemindID)
	{
		if(view != null)
			this.iconRemind.setImageDrawable(myContext.getResources().getDrawable(iconRemindID));
		else
			return;
	}
	
	public void setErrorMessage(String errorMessage)
	{
		if(view!=null)
			this.errorMessageTextView.setText(errorMessage);
		else
			return;
	}
	
	public void showErrorToast()
	{
		toast.show();
	}

}
