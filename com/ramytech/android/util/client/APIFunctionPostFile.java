package com.ramytech.android.util.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.TextUtils;

public abstract class APIFunctionPostFile extends AsyncTask<Void, Void, String> {
	private String aurl;
	private Map<String, Object> params;
	protected Context ctx;

	public APIFunctionPostFile(Context ctx, Map<String, Object> ps, String url) {
		super();
		this.ctx = ctx;
		if (ps == null)
			this.params = new HashMap<String, Object>();
		else
			this.params = ps;
		aurl = APIManager.shared().getURL(url);
	}

	@Override
	protected String doInBackground(Void... uri) {
		AndroidHttpClient httpclient = AndroidHttpClient.newInstance("android");
		HttpResponse response;
		String responseString = null;
		try {
			HttpRequestBase req;
//			APIManager.shared().addAuthInfo(ctx, this.params);
			// aurl += "?";
			MultipartEntityBuilder entityBuilder = MultipartEntityBuilder
					.create();
			entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (String key : this.params.keySet()) {
				if (this.params.get(key).getClass() == File.class)
					entityBuilder.addPart(key,
							new FileBody((File) this.params.get(key)));
				else if (this.params.get(key).getClass() == Bitmap.class) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					((Bitmap) this.params.get(key)).compress(
							Bitmap.CompressFormat.PNG, 100, baos);
					entityBuilder.addBinaryBody(key, baos.toByteArray(),
							ContentType.APPLICATION_OCTET_STREAM, key);
				} else {
					StringBody body = new StringBody(this.params.get(key)
							.toString(), ContentType.create("text/plain",
							HTTP.UTF_8));
					entityBuilder.addPart(key, body);
				}
			}

			HttpPost post = new HttpPost(aurl);

			post.setEntity(entityBuilder.build());
			req = post;

			response = httpclient.execute(req);
			StatusLine statusLine = response.getStatusLine();
			System.out.println("post statuscode=" + statusLine.getStatusCode());
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				response.getEntity().writeTo(out);
				out.close();
				responseString = out.toString();
			} else {
				// Closes the connection.
				response.getEntity().getContent().close();
				throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			httpclient.close();
		}

		return responseString;
	}

	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		if (!TextUtils.isEmpty(result)) {
			try {
				JSONArray arr = new JSONArray(result);
				JSONObject jobj = arr.optJSONObject(0);
				if (jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == 1)
					this.onSuccess(this, jobj.optJSONObject(DATA));				
				else if (jobj.has(ERRMSG)) {
					this.onFail(this, jobj.getString(ERRMSG));
				} else if (jobj.has(ERRCODE)) {
					this.onFail(this, ReturnResult.values()[jobj.getInt(ERRCODE)].name());
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

	abstract public void onSuccess(APIFunctionPostFile api, Object resObj);

	public void onFail(APIFunctionPostFile api, String errMsg) {
		System.out.println("errmsg=:" + errMsg);
	}
}
