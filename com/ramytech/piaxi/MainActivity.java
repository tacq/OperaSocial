package com.ramytech.piaxi;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.image.BGImageLoader;
import com.ramytech.android.util.ActivityRequests;
import com.ramytech.android.util.AlertUtil;
import com.ramytech.android.util.GPSTracker;
import com.ramytech.android.util.MD5;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.edititem.EditItemOnClickListener;
import com.ramytech.piaxi.homepage.FragmentHomePage;
import com.ramytech.piaxi.like.LikeHomePageFragment;
import com.ramytech.piaxi.me.FragmentMeHomePage;
import com.ramytech.piaxi.search.SearchFragment;

/**
 * @author CazadorH app主页
 *
 */
public class MainActivity extends BaseActivity implements OnClickListener,
		DataStore {
	private static final int TAB_ICONS[][] = {
			{ R.drawable.buttonhomepagegrey, R.drawable.buttonfollowgrey,
					R.drawable.buttonsearchgrey, R.drawable.buttonmegrey },
			{ R.drawable.buttonhomepagered, R.drawable.buttonfollowred,
					R.drawable.buttonsearchred, R.drawable.buttonmered } };

	private BGReminder reminder;

	private static final int TAB_BTN_IDS[] = { R.id.btn1, R.id.btn2, R.id.btn3,
			R.id.btn4 };

	private Map<String, Object> dataStore = new HashMap<String, Object>();

	private ActionBar actionBar;

	private static final int APP_START = 1;
	private static final int APP_END = -1;
	private String latitude="0.0";  
	private String longitude ="0.0";

	@Override
	public void setData(String key, Object obj) {
		dataStore.put(key, obj);
	}

	@Override
	public Object getData(String key) {
		return dataStore.get(key);
	};

	private int currentTab = 0, oldTab = -1;
	private ImageButton tabBtns[];
	private Fragment currentFrag;

	private View tableLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		BGImageLoader.init(this);
		APIManager.shared().loadCredential(this);

		tableLayout = this.findViewById(R.id.tableLayout1);

		tabBtns = new ImageButton[TAB_BTN_IDS.length];
		for (int i = 0; i < tabBtns.length; i++) {
			tabBtns[i] = (ImageButton) this.findViewById(TAB_BTN_IDS[i]);
			tabBtns[i].setOnClickListener(this);
		}
		getCurrentLocation();
		setupTabs();
		getActionBar().setTitle("首页");
	}

	private void checkNetworkInfo() {
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		// mobile 3G Data Network
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		// txt3G.setText(mobile.toString());
		// wifi
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// txtWifi.setText(wifi.toString());

		// 如果3G网络和wifi网络都未连接，且不是处于正在连接状态 则进入Network Setting界面 由用户配置网络连接
		if (mobile == State.CONNECTED || mobile == State.CONNECTING)
			return;
		if (wifi == State.CONNECTED || wifi == State.CONNECTING)
			return;

		AlertUtil.alert(this, null, "当前网络连接不可用，请检查网络设置。",
				new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// startActivity(new
						// Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
						startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)); // 进入手机中的wifi网络设置界面
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 检测网络状况
		checkNetworkInfo();

		final SharedPreferences sp = this.getSharedPreferences("app",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("app_state", APP_START);
		if (sp.getBoolean("FIRST_BOOT", true)) {
		}
		editor.commit();

		if (!APIManager.shared().isLogin(this)) {
			Intent it = new Intent(this, IndexActivity.class);
			it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
					| Intent.FLAG_ACTIVITY_NEW_TASK);
			this.startActivity(it);
			this.finish();
			return;
		} else {
			// 使用token重新登录
			//loginWithToken();
			setupMainView();
		}

		if (reminder == null)
			reminder = new BGReminder();
		reminder.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		final SharedPreferences sp = this.getSharedPreferences("app",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("app_state", APP_END);
		editor.commit();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (reminder != null)
			reminder.onPause();
	}

	public static interface PhotoCallback {
		public void onResult(int requestCode, int resultCode, Intent data);
	}

	private int requestCode, resultCode;
	private Intent data;
	private boolean mPostResume = false;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case Activity.RESULT_OK:
			Toast.makeText(getBaseContext(), "拍照成功", Toast.LENGTH_SHORT).show();
			break;
		case ActivityRequests.TAKE_PHOTO:
		case ActivityRequests.CHOOSE_PHOTO:
		case ActivityRequests.CROP_PHOTO:
			this.requestCode = requestCode;
			this.resultCode = resultCode;
			this.data = data;
			mPostResume = true;
			break;
		}
	}

	@Override
	public void onPostResume() {
		super.onPostResume();
		if (mPostResume) {
			PhotoCallback callback = (PhotoCallback) this
					.getData(EditItemOnClickListener.PHOTO_ACTIONKEY);
			if (callback != null)
				callback.onResult(requestCode, resultCode, data);
		}
		mPostResume = false;
	}

	private void setupTabs() {
		for (int i = 0; i < TAB_BTN_IDS.length; i++) {
			tabBtns[i].setImageResource(TAB_ICONS[currentTab == i ? 1 : 0][i]);
		}
	}

	private void setupMainView() {
		if (oldTab != currentTab) {
			currentFrag = null;
			switch (currentTab) {
			case 0:
				currentFrag = new FragmentHomePage();
				break;
			case 1:
				currentFrag = new LikeHomePageFragment();
				break;
			case 2:
				currentFrag = new SearchFragment();
				break;
			case 3:
				currentFrag = new FragmentMeHomePage();
				break;
			default:
				break;
			}
			if (currentFrag != null) {
				clearFragmentsInStack();
				if (oldTab < 0) {
					this.getSupportFragmentManager().beginTransaction()
							.add(R.id.mainContainer, currentFrag)
							.addToBackStack("tab" + currentTab).commit();
				} else {
					this.getSupportFragmentManager().beginTransaction()
							.replace(R.id.mainContainer, currentFrag)
							.addToBackStack("tab" + currentTab).commit();
				}
			}
		}
	}

	@Override
	public void onClick(View v) {
		oldTab = currentTab;
		for (int i = 0; i < TAB_BTN_IDS.length; i++) {
			if (v.getId() == TAB_BTN_IDS[i]) {
				currentTab = i;
				break;
			}
		}
		if (oldTab != currentTab) {
			setupTabs();
			setupMainView();
		}
	}

	public void onTabResume(int cTab) {
		currentTab = cTab;
		setupTabs();
	}

	public void pushNewFrag(Fragment frag) {
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.mainContainer, frag)
				.addToBackStack("tab" + currentTab).commit();
	}

	@Override
	public void onBackPressed() {
		if (this.getSupportFragmentManager().getBackStackEntryCount() > 1)
			super.onBackPressed();
		else
			this.finish();
	}

	private void clearFragmentsInStack() {
		if (this.getSupportFragmentManager().getBackStackEntryCount() > 0) {
			FragmentManager.BackStackEntry first = this
					.getSupportFragmentManager().getBackStackEntryAt(0);
			this.getSupportFragmentManager().popBackStack(first.getId(),
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
	}

	public void setTabLayoutVisible(boolean v) {
		this.tableLayout.setVisibility(v ? View.VISIBLE : View.GONE);
	}

	private void loginWithToken() {
		BGTask task = new BGTask(true, null, "s04?t=13", MainActivity.this) {
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject obj = ((JSONObject) resObj);
				String uid = "" + obj.optInt("pid");
				String token = obj.optString("token");
				String ts = obj.optString("ts");
				int type = obj.optInt("type");
				APIManager.shared().setCredential(uid, MD5.genMD5(token+ts), type);
				APIManager.shared().saveCredential(MainActivity.this);
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("x", latitude);
		task.setParam("y", longitude);
		task.execute();
	}

	private void getCurrentLocation()
	{
		GPSTracker gpsTracker = new GPSTracker(this);
		latitude = String.valueOf(gpsTracker.latitude);
		longitude = String.valueOf(gpsTracker.longitude);
	}
}
