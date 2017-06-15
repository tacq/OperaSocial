package com.ramytech.android.util.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.ramytech.piaxi.IndexActivity;

public abstract class APIFunction extends AsyncTask<Void, Void, String> {
	private HttpParams params;
	List<NameValuePair> postParameters;
	private boolean isPost;
	private String aurl;
	protected Context ctx;
	private String cookies;
	public static String sessionId;

	public APIFunction(Context ctx, boolean isP, Map<String, Object> ps,
			String url) {
		super();
		this.ctx = ctx;
		this.isPost = isP;
		postParameters = new ArrayList<NameValuePair>();
		if (ps != null)
			for (String key : ps.keySet()) {
				this.postParameters.add(new BasicNameValuePair(key, ps.get(key)
						.toString()));
			}
		aurl = APIManager.shared().getURL(url);
		setParam("clienttype", "1");// android client
	}

	public void setParam(String key, String value) {
		this.postParameters.add(new BasicNameValuePair(key, value));
	}

	@Override
	protected String doInBackground(Void... uri) {
		AndroidHttpClient httpclient = AndroidHttpClient.newInstance("android");
		HttpResponse response;
		String responseString = null;
		try {
			HttpRequestBase req;

			if (this.isPost) {
				HttpPost post = new HttpPost(aurl);
				APIManager.shared().addAuthInfo(ctx, postParameters);

				UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
						postParameters, HTTP.UTF_8);
				post.setEntity(formEntity);
				req = post;
			} else {
				APIManager.shared().addAuthInfo(ctx, postParameters);
				String querystring = URLEncodedUtils.format(postParameters,
						"utf-8");
				StringBuilder sb = new StringBuilder(255);
				sb.append(aurl);
				if (aurl.contains("?"))
					sb.append('&');
				else
					sb.append('?');
				sb.append(querystring);
				HttpGet get = new HttpGet(sb.toString());
				req = get;
			}
			if (sessionId != null) {
				System.out.println("test:"+cookies);
				req.setHeader("Cookie", cookies);
			}
			response = httpclient.execute(req);
			if(sessionId ==null)
				appendCookies(response);
			StatusLine statusLine = response.getStatusLine();
			System.out.println("statuscode=" + statusLine.getStatusCode());
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			response.getEntity().writeTo(out);
			out.close();
			responseString = out.toString();
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		} finally {
			httpclient.close();
		}

		return responseString;
	}

	
	private void appendCookies(HttpMessage resMsg) {
		Header setCookieHeader = resMsg.getFirstHeader("Set-Cookie");
		Header[] headers = resMsg.getHeaders("Set-Cookie");
		;
		cookies = setCookieHeader.getValue();
		if (headers != null && headers.length == 1) {
			sessionId = headers[0].getValue();
		}
		System.out.println("sessionId:" + sessionId);
		sessionId = sessionId.substring(0, sessionId.indexOf(";"));
		System.out.println("sessionId:" + sessionId);
	}

	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		System.out.println("后台通信开始了，请求接口：" + aurl);
		if (!TextUtils.isEmpty(result)) {
			try {
				JSONArray arr = new JSONArray(result);
				JSONObject jobj = arr.optJSONObject(0);
				if (jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == 1)
					this.onSuccess(this, jobj.optJSONObject(DATA));
				else if (jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == 401) {
					// not login
					if (ctx != null) {
						Intent intent = new Intent(ctx, IndexActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						ctx.startActivity(intent);
					} else {
						this.onFail(this, "请重新登陆");
					}
				} else if (jobj.has(ERRMSG)) {
					this.onFail(this, jobj.getString(ERRMSG));
				} else if (jobj.has(ERRCODE)) {
					this.onFail(this,
							ReturnResult.values()[jobj.getInt(ERRCODE)].name());
				} else {
					this.onFail(this, result);
				}
				return;
			} catch (JSONException ex) {
				System.out.println("jsonerr, result=" + result);
			}
		}

		this.onFail(this, result);
	}

	abstract public void onSuccess(APIFunction api, Object resObj);

	public void onFail(APIFunction api, String errMsg) {
		System.err.println("errmsg=:" + errMsg);
	}
}
