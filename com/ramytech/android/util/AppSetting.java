package com.ramytech.android.util;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSetting {
	public static void save(Context ctx, String name, String key) {
		SharedPreferences sp = ctx.getSharedPreferences("app", Context.MODE_PRIVATE);
		sp.edit().putString(name, key).commit();
	}
	
	public static String get(Context ctx, String name) {
		SharedPreferences sp = ctx.getSharedPreferences("app", Context.MODE_PRIVATE);
		return sp.getString(name, null);
	}
	
	public static void clearAll(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("app", Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
		
}
