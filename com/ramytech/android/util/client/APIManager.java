package com.ramytech.android.util.client;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.content.SharedPreferences;

public class APIManager {
	private static final String IMGSERVER_ROOT = "http://yizhenimg.augbase.com/";

	private static final String TEMP_USER_UID = "0";
	private static final String TEMP_USER_TOKEN = "d2861c36e8cb41868598736e99f17799";

	public static String getLTRURL(String name) {
		return IMGSERVER_ROOT + "origin/" + name;
	}

	public static String getAvatarURL(String name) {
		return IMGSERVER_ROOT + "user/" + name;
	}

	public static String getCommentURL(String name) {
		return IMGSERVER_ROOT + "forum/" + name;
	}

	public static String getForumURL(String name) {
		return IMGSERVER_ROOT + "forum/" + name;
	}

	public static String getAuthorURL(String name) {
		return IMGSERVER_ROOT + "author/" + name;
	}

	public static String getThumbURL(String name) {
		return IMGSERVER_ROOT + "origin_thumb/" + name;
	}

	public static String getChatURL(String name) {
		return IMGSERVER_ROOT + "xychat/" + name;
	}

	private static APIManager instance;

	public static APIManager shared() {
		if (instance == null) {
			instance = new APIManager("http://112.124.3.218:5051/wsapp/svs/");
		}
		return instance;
	}

	private APIManager(String url) {
		baseURL = url;
	}

	private final String baseURL;
	private String uid;
	private String token;
	private String password;
	private int role;
	private String sessionId;

	public String getUID() {
		return uid;
	}

	public String getToken() {
		return token;
	}

	public int getRole() {
		return role;
	}
	
	public String getSessionId()
	{
		return sessionId;
	}

	public String getURL(String url) {
		return baseURL + url;
	}

	public void setTempUser() {
		this.uid = TEMP_USER_UID;
		this.token = TEMP_USER_TOKEN;
	}

	public boolean isTempUser() {
		return TEMP_USER_UID.equals(uid) && TEMP_USER_TOKEN.equals(token);
	}

	public void addAuthInfo(Context ctx, String url) {
		if (url == null)
			return;
		if (uid == null && ctx != null)
			this.loadCredential(ctx);
		if (uid != null)
			url += "&pid=" + uid;
		if (token != null)
			url += "&key=" + token;
	}

	public void addAuthInfo(Context ctx, Map<String, Object> map) {
		if (map == null)
			return;
		if (uid == null && ctx != null)
			this.loadCredential(ctx);
		if (uid != null)
			map.put("pid", uid);
		if (token != null)
			map.put("key", token);
	}

	public void addAuthInfo(Context ctx, HttpParams params) {
		if (params == null)
			return;
		if (uid == null && ctx != null)
			this.loadCredential(ctx);
		if (uid != null)
			params.setParameter("pid", uid);
		if (token != null)
			params.setParameter("key", token);
	}

	public void addAuthInfo(Context ctx, List<NameValuePair> postParameters) {
		if (postParameters == null)
			return;
		if (uid == null && ctx != null)
			this.loadCredential(ctx);
		if (uid != null)
			postParameters.add(new BasicNameValuePair("pid", uid));
		if (token != null)
			postParameters.add(new BasicNameValuePair("key", token));
	}

	public void setCredential(String uid, String token, String password,
			int role) {
		this.uid = uid;
		this.token = token;
		this.password = password;
		this.role = role;
	}
	
	public void setCredential(String uid, String token,
			int role) {
		this.uid = uid;
		this.token = token;
		this.role = role;
	}
	
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public void saveCredential(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		sp.edit().putString("token", token).putString("uid", uid)
				.putString("pw", password).putInt("role", role).commit();
	}

	public void loadCredential(Context ctx) {
		SharedPreferences sp = ctx.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		this.token = sp.getString("token", null);
		this.uid = sp.getString("uid", null);
		this.password = sp.getString("pw", null);
		this.role = sp.getInt("role", -1);
	}

	public boolean isLogin(Context ctx) {
		if (this.token != null && this.uid != null && this.role >= 0)
			return true;
		this.loadCredential(ctx);
		if (this.token != null && this.uid != null && this.role >= 0)
			return true;
		return false;
	}

	public boolean clearCredential(Context ctx) {
		this.uid = null;
		this.token = null;
		this.password = null;
		this.role = -1;
		SharedPreferences sp = ctx.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		return sp.edit().clear().commit();
	}

	public boolean isPasswordMatch(String p) {
		if (password != null)
			return password.equals(p);
		return false;
	}
}
