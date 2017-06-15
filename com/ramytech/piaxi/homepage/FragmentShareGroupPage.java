package com.ramytech.piaxi.homepage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ramytech.android.bean.PictureBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

public class FragmentShareGroupPage extends Fragment {
	
	private View view;
	private ListView lv_share_group;
	private MyAdapter adapter = new MyAdapter();
	private List<String> groupList = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_share_group_page, container,
				false);
		
		getUserGroup();
		
		lv_share_group = (ListView) view.findViewById(R.id.lv_share_group);
		lv_share_group.setAdapter(adapter);
		 
		
		getActivity().getActionBar().setTitle("选择分组");
		return view;
	}
	
	private void getUserGroup(){
		System.out.println("====getUserGroup====");
		BGTask task = new BGTask(true, null, "s04?t=27", getActivity()) {
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
					//TODO 给groupList赋值

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		task.setParam("pid", APIManager.shared().getUID());
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
	
	class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return groupList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = getActivity().getLayoutInflater();
			convertView = inflater.inflate(R.layout.item_share_group_list, parent, false);
			TextView tv_share_group = (TextView) convertView.findViewById(R.id.tv_share_group);
			tv_share_group.setText(groupList.get(position));
			return convertView;
		}
		
	}
	
}
