package com.ramytech.piaxi.homepage;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.BaseActivity;
import com.ramytech.piaxi.R;

public class JuIntroduceActivity extends BaseActivity implements
		OnClickListener {

	private Button btn_choosecharacter;
	private Button btn_positive;
	private Button btn_negative;
	private Button btn_share;
	private Button btn_favorite;

	private String cid;// 传递过来的剧标识
	private LinearLayout ll_judge_dialog;
	private ListView lv_judge;

	ArrayList<HashMap<String, Object>> listJudgeItem = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ju_introduce);

		Intent intent = getIntent();
		cid = intent.getStringExtra("cid");

		btn_choosecharacter = (Button) findViewById(R.id.btn_choosecharacter);
		btn_positive = (Button) findViewById(R.id.btn_positive);
		btn_negative = (Button) findViewById(R.id.btn_negative);
		btn_share = (Button) findViewById(R.id.btn_share);
		btn_favorite = (Button) findViewById(R.id.btn_favorite);
		btn_choosecharacter.setOnClickListener(this);
		btn_positive.setOnClickListener(this);
		btn_negative.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_favorite.setOnClickListener(this);

		ll_judge_dialog = (LinearLayout) findViewById(R.id.ll_judge_dialog);
		lv_judge = (ListView) findViewById(R.id.lv_judge);
		adapter = new SimpleAdapter(this, listJudgeItem,
				R.layout.item_judge_list, new String[] { "iv_icon",
						"tv_judge_message", "tv_judege_coustom" }, new int[] {
						R.id.iv_icon, R.id.tv_judge_message,
						R.id.tv_judege_coustom });
		lv_judge.setAdapter(adapter);
		lv_judge.setOnItemClickListener(new ItemClickListener());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_choosecharacter:
			showChoosecharacterDialog();
			break;
		case R.id.btn_positive:
			getPositveData();
			ll_judge_dialog.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();

			break;
		case R.id.btn_negative:
			getNegativeData();
			ll_judge_dialog.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();

			break;
		case R.id.btn_share:
			// TODO 这里是Activity 但是后面又是Fragment
			// FragmentTransaction transaction =
			// getFragmentManager().beginTransaction();
			// FragmentShareGroupPage f = new FragmentShareGroupPage();
			// transaction.replace(R.id.mainContainer,f);
			// transaction.addToBackStack(null);
			// transaction.commit();
			break;
		case R.id.btn_favorite:

			//跳转到主界面，收藏Fragment
//			this.finish();
//			FragmentTransaction transaction = getFragmentManager()
//					.beginTransaction();
//			FragmentCollectionPage f = new FragmentCollectionPage();
//			transaction.replace(R.id.mainContainer, f);
//			transaction.addToBackStack(null);
//			transaction.commit();
			break;
		default:
			break;
		}

	}

	private void getPositveData() {
		listJudgeItem.clear();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("iv_icon", R.drawable.iconlove3grey);// TODO 待确认，这图片到底怎么获取
		map1.put("tv_judge_message", "本子不错，值得鼓励");
		map1.put("tv_judege_coustom", "选此项需消费100pia币，作者获得50RP(积分)");
		listJudgeItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("iv_icon", R.drawable.iconlove2grey);// TODO 待确认，这图片到底怎么获取
		map2.put("tv_judge_message", "此本只应天上有，人间哪得几回闻");
		map2.put("tv_judege_coustom", "选此项需消费500pia币，作者获得250RP(积分)");
		listJudgeItem.add(map2);
		HashMap<String, Object> map3 = new HashMap<String, Object>();
		map3.put("iv_icon", R.drawable.iconlove1grey);// TODO 待确认，这图片到底怎么获取
		map3.put("tv_judge_message", "剧情如此动人，我已情不自禁");
		map3.put("tv_judege_coustom", "选此项需消费1000pia币，作者获得500RP(积分)");
		listJudgeItem.add(map3);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("iv_icon", R.drawable.iconlove3grey);// TODO 待确认，这图片到底怎么获取
		map4.put("tv_judge_message", "有钱奏是任性！！！赏");
		map4.put("tv_judege_coustom", "选此项需消费10000pia币，作者获得5000RP(积分)");
		listJudgeItem.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("iv_icon", R.drawable.iconlove3grey);// TODO 待确认，这图片到底怎么获取
		map5.put("tv_judge_message", "我就随便说说，认真你就输了");
		map5.put("tv_judege_coustom", "选此项无消费，作者也不得分");
		listJudgeItem.add(map5);
	}

	private void getNegativeData() {
		listJudgeItem.clear();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("iv_icon", R.drawable.iconhate1grey);// TODO 待确认，这图片到底怎么获取
		map1.put("tv_judge_message", "真心的，拿回去再改改吧");
		map1.put("tv_judege_coustom", "选此项需消费100pia币，作者将被扣减10RP(积分)");
		listJudgeItem.add(map1);
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("iv_icon", R.drawable.iconhate2grey);// TODO 待确认，这图片到底怎么获取
		map2.put("tv_judge_message", "这也能发表？我已无话可说");
		map2.put("tv_judege_coustom", "选此项需消费1000pia币，作者将被扣减100RP(积分)");
		listJudgeItem.add(map2);
		HashMap<String, Object> map4 = new HashMap<String, Object>();
		map4.put("iv_icon", R.drawable.iconlove3grey);// TODO 待确认，这图片到底怎么获取
		map4.put("tv_judge_message", "就是不喜欢，有钱奏是任性！！！");
		map4.put("tv_judege_coustom", "选此项需消费10000pia币，作者将被扣减500RP(积分)");
		listJudgeItem.add(map4);
		HashMap<String, Object> map5 = new HashMap<String, Object>();
		map5.put("iv_icon", R.drawable.iconlove3grey);// TODO 待确认，这图片到底怎么获取
		map5.put("tv_judge_message", "你可以不当回事，但我就是不喜欢");
		map5.put("tv_judege_coustom", "选此项需消费10pia币，作者不会被扣RP值");
		listJudgeItem.add(map5);
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			ll_judge_dialog.setVisibility(View.INVISIBLE);
			judgeJu("1", "100", "本子不错，值得鼓励");
			// judgeJu("2", "100", "真心的，拿回去再改改吧");//差评
		}

	}

	private void showChoosecharacterDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("选角。。。。。");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	private void judgeJu(String flag, String money, String note) {
		System.out.println("============judgeJu===========");
		BGTask task = new BGTask(true, null, "s11?t=3", this) {
			@Override
			protected void onPostExecute(String result) {
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONArray arr = new JSONArray(result);
						JSONObject jobj = arr.optJSONObject(0);
						// 为1获取成功；
						if (jobj.has(PiaxiConstant.ERRCODE)
								&& jobj.getInt(PiaxiConstant.ERRCODE) == 1) {
							this.onSuccess(this,
									jobj.optJSONObject(PiaxiConstant.DATA));
						}
						// 获取失败
						else if (jobj.has(PiaxiConstant.ERRCODE)
								&& jobj.getInt(PiaxiConstant.ERRCODE) == -1) {
							this.onFail(this, "数据获取失败");
						} else if (jobj.has(PiaxiConstant.ERRMSG)) {
							this.onFail(this,
									jobj.getString(PiaxiConstant.ERRMSG));
						} else if (jobj.has(PiaxiConstant.ERRCODE)) {
							this.onFail(this, ReturnResult.values()[jobj
									.getInt(PiaxiConstant.ERRCODE)].name());
						} else {
							this.onFail(this, result);
						}
						return;
					} catch (JSONException ex) {
						System.out.println("jsonerr, result=" + result);
					}
				}

				this.onFail(this, "数据获取失败lll");
			}

			@Override
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject obj = ((JSONObject) resObj);
				System.out.println("loadingClassListData succ:" + obj);
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("cid", cid);
		task.setParam("flag", flag);
		task.setParam("money", money);
		task.setParam("note", note);
		task.execute();
	}

}
