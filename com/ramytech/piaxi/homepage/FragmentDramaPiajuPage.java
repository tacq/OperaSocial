package com.ramytech.piaxi.homepage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ramytech.android.bean.DramaBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class FragmentDramaPiajuPage extends Fragment implements OnClickListener {
	
	private View view;
	private String rid;//主页面传递过来的剧本唯一标识符rid
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_piajupage, container, false);
		rid = getArguments().getString("rid");
		System.out.println("获取到了 rid:"+rid);
		loadingPixjuDataByRid(rid);
		getActivity().getActionBar().setTitle("剧本标题");	
		return view;
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void loadingPixjuDataByRid(String rid)
	{
		System.out.println("====loadingDramaDataByRid====rid:" + rid);
		BGTask task = new BGTask(true, null, "s05?t=10", getActivity()) {
			@Override
			protected void onPostExecute(String result){
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONArray arr = new JSONArray(result);
						JSONObject jobj = arr.optJSONObject(0);
						//为1获取成功；
						if (jobj.has(PiaxiConstant.ERRCODE) && jobj.getInt(PiaxiConstant.ERRCODE) == 1)
						{
							this.onSuccess(this, jobj.optJSONObject(PiaxiConstant.DATA));
						}
						//获取失败
						else if(jobj.has(PiaxiConstant.ERRCODE) && jobj.getInt(PiaxiConstant.ERRCODE) == -1)
						{
							this.onFail(this, "数据获取失败");
						}
						else if(jobj.has(PiaxiConstant.ERRMSG))
						{
							this.onFail(this, jobj.getString(PiaxiConstant.ERRMSG));
						} else if (jobj.has(PiaxiConstant.ERRCODE)) {
							this.onFail(this, ReturnResult.values()[jobj.getInt(PiaxiConstant.ERRCODE)].name());
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
				//成功了  但是返回是空  值返回了：{}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", "1");
		task.setParam("ps", "1");
		task.setParam("code", rid);
		task.execute();
	}


}
