package com.ramytech.piaxi.homepage;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.MemberSubMenu;
import com.ramytech.android.util.MemberSubMenu.OnFanSelected;
import com.ramytech.piaxi.R;

public class FragmentDramaCommentPage extends Fragment implements
		OnClickListener, OnFanSelected {

	private View view;
	private String rid;// 主页面传递过来的剧本唯一标识符rid
	private ListView lv_comment;
	ArrayList<HashMap<String, Object>> listMessageItem = new ArrayList<HashMap<String, Object>>();
	private Button btn_gift;
	private Button btn_send;
	private EditText et_send_message;
	private SimpleAdapter adapter;
	private LinearLayout ll_gifts;
	
	private boolean flag = false;//用于判断最下面的礼物是不是显示状态

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_comment_page,
				container, false);
		rid = getArguments().getString("rid");

		testData();

		// 上面的留言listView
		lv_comment = (ListView) view.findViewById(R.id.lv_comment);
		adapter = new SimpleAdapter(this.getActivity(), listMessageItem,
				R.layout.item_message_list, new String[] { "iv_pic",
						"tv_message" }, new int[] { R.id.iv_pic,
						R.id.tv_message });
		lv_comment.setAdapter(adapter);
		lv_comment.setSelection(lv_comment.getBottom());// 显示最后一条

		// 下面的输入部分
		btn_gift = (Button) view.findViewById(R.id.btn_gift);
		btn_gift.setOnClickListener(this);
		btn_send = (Button) view.findViewById(R.id.btn_send);
		btn_send.setOnClickListener(this);
		et_send_message = (EditText) view.findViewById(R.id.et_send_message);
		ll_gifts = (LinearLayout) view.findViewById(R.id.ll_gifts);

		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("留言");
		return view;
	}

	private void testData() {
		for (int i = 0; i < 3; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("iv_pic", R.drawable.buttoncheckbox);// TODO 待确认，这图片到底怎么获取
			map.put("tv_message", "评论" + i);
			listMessageItem.add(map);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.member_menu, menu);
		menu.findItem(R.id.menu_member).setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.menu_member:
			new MemberSubMenu(getActivity(), this).show();
			break;
		}
		return true;
	}

	@Override
	public void onClick(int witchButton) {
		switch (witchButton) {
		case PiaxiConstant.BUTTON_FAN_RED:
			Toast.makeText(getActivity(), "BUTTON_FAN_RED", Toast.LENGTH_SHORT).show();
			listMessageItem.clear();
			for (int i = 0; i < 3; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("iv_pic", R.drawable.buttoncheckbox);// TODO 待确认，这图片到底怎么获取
				map.put("tv_message", "红粉评论" + i);
				listMessageItem.add(map);
			}
			adapter.notifyDataSetChanged();
			break;
		case PiaxiConstant.BUTTON_FAN_BLACK:
			Toast.makeText(getActivity(), "BUTTON_FAN_BLACK", Toast.LENGTH_SHORT).show();
			listMessageItem.clear();
			for (int i = 0; i < 12; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("iv_pic", R.drawable.buttoncheckbox);// TODO 待确认，这图片到底怎么获取
				map.put("tv_message", "黑粉评论" + i);
				listMessageItem.add(map);
			}
			adapter.notifyDataSetChanged();
			break;
		case PiaxiConstant.BUTTON_FAN_WATER:
			Toast.makeText(getActivity(), "BUTTON_FAN_WATER", Toast.LENGTH_SHORT).show();
			listMessageItem.clear();
			for (int i = 0; i < 5; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("iv_pic", R.drawable.buttoncheckbox);// TODO 待确认，这图片到底怎么获取
				map.put("tv_message", "水粉评论" + i);
				listMessageItem.add(map);
			}
			adapter.notifyDataSetChanged();
			break;
		case PiaxiConstant.BUTTON_MEMBERS:
			FragmentTransaction transactionfdlp = getFragmentManager()
					.beginTransaction();
			FragmentClubMemberPage fdlp = new FragmentClubMemberPage();
			Bundle bundlefdlp = new Bundle();
			bundlefdlp.putString("rid", rid);
			fdlp.setArguments(bundlefdlp);
			transactionfdlp.replace(R.id.mainContainer, fdlp);
			transactionfdlp.addToBackStack(null);
			transactionfdlp.commit();
			break;

		default:
			break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_gift:
			
			LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) ll_gifts
					.getLayoutParams();
			if(flag){
				linearParams.height = 1;
				flag = false;
			}else{
				linearParams.height = 300;
				flag = true;
			}
			ll_gifts.setLayoutParams(linearParams);
			break;
		case R.id.btn_send:
			String message = et_send_message.getText().toString();
			if (!(null == message || message.length() == 0)) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("iv_pic", R.drawable.icondone);// TODO 待确认，这图片到底怎么获取
				map.put("tv_message", message);
				listMessageItem.add(map);
				adapter.notifyDataSetChanged();
			}
			break;

		default:
			break;
		}
	}
}
