package com.ramytech.android.util.client;

import java.util.Map;

import org.apache.http.util.TextUtils;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ramytech.android.util.ProgressDialog;

public class BGTask extends APIFunction {

	private DialogFragment dialog;
	public boolean isCancelled;
	private FragmentActivity activity;
	private String nodataMsg = null;
	private int loadingResId = -1;

	public BGTask(boolean isP, Map<String, Object> ps, String url,
			FragmentActivity activity, String nodataMsg) {
		super(activity, isP, ps, url);

		this.nodataMsg = nodataMsg;
		this.activity = activity;
		if (activity != null)
			this.dialog = ProgressDialog.newInstance(loadingResId);
		this.isCancelled = false;
	}

	public BGTask(boolean isP, Map<String, Object> ps, String url,
			FragmentActivity activity, int loadingResId) {
		super(activity, isP, ps, url);

		this.loadingResId = loadingResId;
		this.activity = activity;
		if (activity != null)
			this.dialog = ProgressDialog.newInstance(loadingResId);
		this.isCancelled = false;
	}

	public BGTask(boolean isP, Map<String, Object> ps, String url,
			FragmentActivity activity) {
		super(activity, isP, ps, url);

		this.nodataMsg = "没有更多数据。";
		this.activity = activity;
		if (activity != null)
			this.dialog = ProgressDialog.newInstance(loadingResId);
		this.isCancelled = false;
	}
	
	public BGTask(int loadingCount,boolean isP, Map<String, Object> ps, String url,
			FragmentActivity activity) {
		super(activity, isP, ps, url);

		this.nodataMsg = "没有更多数据。";
		this.activity = activity;
		if (activity != null&&loadingCount == 1)
			this.dialog = ProgressDialog.newInstance(loadingResId);
		this.isCancelled = false;
	}
	
	@Override
	public void onPreExecute() {
		super.onPreExecute();
		if (dialog != null && activity != null) {
			dialog.show(
					activity.getSupportFragmentManager().beginTransaction(),
					"bgtask");
		}
	}

	@Override
	public void onSuccess(APIFunction api, Object resObj) {
		System.out.println("succ");
		if (!this.isCancelled) {
			try {
				if (dialog != null)
					dialog.dismiss();
			} catch (Exception ex) {
			}
		}
	}

	public void onFail(APIFunction api, String errMsg) {
		super.onFail(api, errMsg);
		if (!this.isCancelled) {
			try {
				if (dialog != null)
					dialog.dismiss();
			} catch (Exception ex) {
			}
			if (activity != null) {
				if (TextUtils.isEmpty(errMsg))
					errMsg = "网络错误";
				Toast.makeText(activity, errMsg, Toast.LENGTH_SHORT).show();
			}
		}
	}
}