package com.ramytech.android.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ramytech.piaxi.R;

public class MoreSubMenu {
	private Context context;
	private Activity activity;

	private Button btn_report;
	private Button btn_leavemessage;
	private Button btn_luzhi;
	private Button btn_download;
	private Button btn_createclub;
	private Button btn_followclub;

	private View view;
	private PopupWindow popupWindow;

	public MoreSubMenu(Activity activity) {
		this.activity = activity;
		this.context = activity.getBaseContext();
	}

	public void show() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.popup_more_sub_menu, null);

		btn_report = (Button) view.findViewById(R.id.btn_report);
		btn_leavemessage = (Button) view.findViewById(R.id.btn_leavemessage);
		btn_luzhi = (Button) view.findViewById(R.id.btn_luzhi);
		btn_download = (Button) view.findViewById(R.id.btn_download);
		btn_createclub = (Button) view.findViewById(R.id.btn_createclub);
		btn_followclub = (Button) view.findViewById(R.id.btn_followclub);

		btn_report.setOnClickListener(clickListener);
		btn_leavemessage.setOnClickListener(clickListener);
		btn_luzhi.setOnClickListener(clickListener);
		btn_download.setOnClickListener(clickListener);
		btn_createclub.setOnClickListener(clickListener);
		btn_followclub.setOnClickListener(clickListener);

		popupWindow = new PopupWindow(view,
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		Rect outRect = new Rect();
		activity.getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(outRect);
		popupWindow.showAtLocation(view, Gravity.RIGHT | Gravity.TOP, 0,
				activity.getActionBar().getHeight() + outRect.top);
		popupWindow.setOnDismissListener(new poponDismissListener());

	}

	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_report:
				popupWindow.dismiss();
				break;
			case R.id.btn_leavemessage:
				popupWindow.dismiss();
			case R.id.btn_luzhi:
				popupWindow.dismiss();
				break;
			case R.id.btn_download:
				popupWindow.dismiss();
			case R.id.btn_createclub:
				popupWindow.dismiss();
				break;
			case R.id.btn_followclub:
				popupWindow.dismiss();
				break;
			}
		}
	};

	class poponDismissListener implements PopupWindow.OnDismissListener {

		@Override
		public void onDismiss() {
		}

	}
}
