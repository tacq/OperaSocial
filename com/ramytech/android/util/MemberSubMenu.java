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

import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.piaxi.R;

public class MemberSubMenu {
	
	public interface OnFanSelected{
		void onClick(int witchButton);
	}
	
	private Context context;
	private Activity activity;
	private OnFanSelected fanSelected;

	private Button btn_fan_red;
	private Button btn_fan_black;
	private Button btn_fan_water;
	private Button btn_members;

	private View view;
	private PopupWindow popupWindow;

	public MemberSubMenu(Activity activity, final OnFanSelected fanSelected) {
		this.activity = activity;
		this.context = activity.getBaseContext();
		this.fanSelected = fanSelected;
	}

	public void show() {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.popup_member_sub_menu, null);

		btn_fan_red = (Button) view.findViewById(R.id.btn_fan_red);
		btn_fan_black = (Button) view.findViewById(R.id.btn_fan_black);
		btn_fan_water = (Button) view.findViewById(R.id.btn_fan_water);
		btn_members = (Button) view.findViewById(R.id.btn_members);

		btn_fan_red.setOnClickListener(clickListener);
		btn_fan_black.setOnClickListener(clickListener);
		btn_fan_water.setOnClickListener(clickListener);
		btn_members.setOnClickListener(clickListener);

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
			case R.id.btn_fan_red:
				fanSelected.onClick(PiaxiConstant.BUTTON_FAN_RED);
				popupWindow.dismiss();
				break;
			case R.id.btn_fan_black:
				fanSelected.onClick(PiaxiConstant.BUTTON_FAN_BLACK);
				popupWindow.dismiss();
				break;
			case R.id.btn_fan_water:
				fanSelected.onClick(PiaxiConstant.BUTTON_FAN_WATER);
				popupWindow.dismiss();
				break;
			case R.id.btn_members:
				fanSelected.onClick(PiaxiConstant.BUTTON_MEMBERS);
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
