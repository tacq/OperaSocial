package com.ramytech.android.util.client;

import android.content.Context;
import android.content.SharedPreferences;

public class AppSetting {
	public static void saveUsername(Context ctx, String username) {
		SharedPreferences sp = ctx.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		sp.edit().putString("username", username).commit();
	}

	public static String getUsername(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		return sp.getString("username", "default");
	}

	public static void clearAll(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("app",
				Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}
}
