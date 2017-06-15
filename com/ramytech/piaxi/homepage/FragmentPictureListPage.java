package com.ramytech.piaxi.homepage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.ramytech.android.bean.PictureBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.RadarSubMenu;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

public class FragmentPictureListPage extends Fragment {

	private View view;
	private String rid;// 传递过来的剧本唯一标识符rid

	// TODO 要把下载下来的插画list放在表格中展示
	private List<PictureBean> pictureBeanList = new ArrayList<PictureBean>();
	ArrayList<HashMap<String, Object>> listPictureItem = new ArrayList<HashMap<String, Object>>();// 将pictureBeanList转换成listPictureItem展示数据

	
	private GridView gv_picture;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_picture_list_page, container,
				false);
		rid = getArguments().getString("rid");
		System.out.println("获取到了 rid:" + rid);
		getPictureList();
		testData();

		gv_picture = (GridView) view.findViewById(R.id.gv_picture);

		SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), 
				listPictureItem,
				R.layout.item_picture_list,
				new String[] { "iv_pic", "tv_pic_name", "tv_user_name" },
				new int[] { R.id.iv_pic, R.id.tv_pic_name, R.id.tv_user_name });
		gv_picture.setAdapter(adapter);
		gv_picture.setOnItemClickListener(new ItemClickListener());

		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("插画集");
		return view;
	}
	
	
	private void testData(){
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("iv_pic", R.drawable.buttoncheckbox);//TODO 待确认，这图片到底怎么获取
			map.put("tv_pic_name", "标题"+i);
			map.put("tv_user_name", "用户"+i);
			listPictureItem.add(map);
		}
	}

	private void getPictureList() {
		System.out.println("====getPictureList====rid:" + rid);
		BGTask task = new BGTask(true, null, "s08?t=7", getActivity()) {
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

				this.onFail(this, "数据获取失败");
			}

			@Override
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject obj = ((JSONObject) resObj);
				System.out.println(obj);
				try {
					JSONArray list = obj.optJSONArray(PiaxiConstant.VALUES);
					for (int i = 0; i < list.length(); i++) {
						PictureBean pictureBean = new PictureBean();
						pictureBean.setPicId(list.optJSONArray(i).get(0)
								.toString());
						pictureBean.setRid(list.optJSONArray(i).get(1)
								.toString());
						pictureBean.setPid(list.optJSONArray(i).get(2)
								.toString());
						pictureBean.setUserName(list.optJSONArray(i).get(3)
								.toString());
						pictureBean.setUploadTime(list.optJSONArray(i).get(4)
								.toString());
						pictureBean.setPicFlag(list.optJSONArray(i).get(5)
								.toString());
						pictureBean.setOssKey(list.optJSONArray(i).get(6)
								.toString());
						pictureBean.setClickCount(list.optJSONArray(i).get(7)
								.toString());
						pictureBean.setDownCount(list.optJSONArray(i).get(8)
								.toString());
						pictureBean.setPicName(list.optJSONArray(i).get(9)
								.toString());
						pictureBeanList.add(pictureBean);

						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("iv_pic", pictureBean.getOssKey());//TODO 待确认，这图片到底怎么获取
						map.put("tv_pic_name", pictureBean.getPicName());
						map.put("tv_user_name", pictureBean.getUserName());
						listPictureItem.add(map);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		task.setParam("pid", APIManager.shared().getUID());
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("rid", rid);
		task.execute();
	}

	// 当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
	class ItemClickListener implements OnItemClickListener {
		public void onItemClick(AdapterView<?> arg0,// The AdapterView where the
													// click happened
				View arg1,// The view within the AdapterView that was clicked
				int arg2,// The position of the view in the adapter
				long arg3// The row id of the item that was clicked
		) {
			seePicture();
		}

	}

	private void seePicture() {
		FragmentTransaction transactionfdlp = getFragmentManager()
				.beginTransaction();
		FragmentPictureDetailPage f = new FragmentPictureDetailPage();
		Bundle bundle = new Bundle();
		// TODO 感觉要把Picture对象传过去
		// bundle.putString("rid", rid);
		f.setArguments(bundle);
		transactionfdlp.replace(R.id.mainContainer, f);
		transactionfdlp.addToBackStack(null);
		transactionfdlp.commit();
	}

	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.add_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_add).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_add:
				FragmentTransaction transaction = getFragmentManager().beginTransaction();
				FragmentPictureUploadPage f = new FragmentPictureUploadPage();
				Bundle bundle = new Bundle();  
                bundle.putString("rid", rid);  
                f.setArguments(bundle);  
			    transaction.replace(R.id.mainContainer,f);
			    transaction.addToBackStack(null);
			    transaction.commit();
				break;
			case R.id.menu_radar:
				Toast.makeText(getActivity(), "button_radar", Toast.LENGTH_SHORT).show();
				break;
		}
		return true;
	}

}
