package com.ramytech.android.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface.OnClickListener;

public class AlertUtil {
	public static void alert(Activity activity, String title, String message) {
		if (title == null) title = "提示";
		new AlertDialog.Builder(activity)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("确定", null)
		.show();
	}
	
	public static void alert(Activity activity, String title, String message, OnClickListener listener) {
		if (title == null) title = "提示";
		new AlertDialog.Builder(activity)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("确定", listener)
		.show();
	}
	
	public static void alert(Activity activity, String title, String message, OnClickListener pListener, OnClickListener nListener) {
		if (title == null) title = "提示";
		new AlertDialog.Builder(activity)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton("同意", pListener)
		.setNegativeButton("拒绝", nListener)
		.show();
	}
	
	public static void alert(Activity activity, String title, String message, OnClickListener pListener, String pStr, OnClickListener nListener, String nStr) {
		if (title == null) title = "提示";
		new AlertDialog.Builder(activity)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(pStr, pListener)
		.setNegativeButton(nStr, nListener)
		.show();
	}
	
	public static void confirm(Activity activity, String title, String message) {
		
	}
}
