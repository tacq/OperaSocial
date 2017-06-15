package com.ramytech.android.util.client;

import java.util.Map;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ramytech.android.util.ProgressDialog;

public abstract class BGPostTask extends APIFunctionPostFile {

	private ProgressDialog dialog;
	public boolean isCancelled;
	private FragmentActivity activity;
	private int loadingResId = -1;

	public BGPostTask(Map<String, Object> ps, String url,
			FragmentActivity activity, int loadingResId) {
		super(activity, ps, url);

		this.loadingResId = loadingResId;
		this.dialog = ProgressDialog.newInstance(loadingResId);
		this.activity = activity;

		this.isCancelled = false;
	}

	public BGPostTask(Map<String, Object> ps, String url,
			FragmentActivity activity) {
		super(activity, ps, url);

		this.dialog = ProgressDialog.newInstance(loadingResId);
		this.activity = activity;

		this.isCancelled = false;
	}

	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (activity != null && dialog != null)
			dialog.show(activity.getSupportFragmentManager(), "bgtask");
	}

	public void onSuccess(APIFunctionPostFile api, Object resObj) {
		System.out.println("succ");
		if (!this.isCancelled) {
			if (dialog != null && activity != null)
				dialog.dismiss();
			if (activity != null)
				Toast.makeText(activity, "操作成功！", Toast.LENGTH_SHORT).show();
		}
	}

	public void onFail(APIFunctionPostFile api, String errMsg) {
		System.out.println("fail," + errMsg);
		if (!this.isCancelled) {
			if (dialog != null)
				dialog.dismiss();
			if (activity != null)
				Toast.makeText(activity, "请求失败，请稍候重试...", Toast.LENGTH_LONG)
						.show();
		}
	}
}