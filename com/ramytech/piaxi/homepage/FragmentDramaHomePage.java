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
import com.ramytech.piaxi.search.SearchFilterFragment;

import android.os.Bundle;
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
import android.widget.LinearLayout;

public class FragmentDramaHomePage extends Fragment implements OnClickListener {
	
	private View view;
	private String rid;//主页面传递过来的剧本唯一标识符rid
	private LinearLayout ll_button_piadrama;
	private LinearLayout ll_button_comment;
	private LinearLayout ll_button_relevant;
	private LinearLayout ll_button_read;
	private LinearLayout ll_writer_name;
	private LinearLayout ll_picture;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_drama_homepage, container, false);
		rid = getArguments().getString("rid");
		System.out.println("获取到了 rid:"+rid);
		loadingDramaDataByRid(rid);
		
		ll_picture = (LinearLayout) view.findViewById(R.id.ll_picture);
		ll_picture.setOnClickListener(this);
		ll_button_piadrama = (LinearLayout) view.findViewById(R.id.ll_button_piadrama);
		ll_button_piadrama.setOnClickListener(this);
		ll_button_comment = (LinearLayout) view.findViewById(R.id.ll_button_comment);
		ll_button_comment.setOnClickListener(this);
		ll_button_relevant = (LinearLayout) view.findViewById(R.id.ll_button_relevant);
		ll_button_relevant.setOnClickListener(this);
		ll_button_read = (LinearLayout) view.findViewById(R.id.ll_button_read);
		ll_button_read.setOnClickListener(this);
		
		ll_writer_name = (LinearLayout) view.findViewById(R.id.ll_writer_name);
		ll_writer_name.setOnClickListener(this);
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("剧本标题");	
		return view;
		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_picture:
			FragmentTransaction transactionfdlp = getFragmentManager().beginTransaction();
			FragmentPictureListPage fdlp = new FragmentPictureListPage();
			Bundle bundlefdlp = new Bundle();  
            bundlefdlp.putString("rid", rid);  
            fdlp.setArguments(bundlefdlp);  
		    transactionfdlp.replace(R.id.mainContainer,fdlp);
		    transactionfdlp.addToBackStack(null);
		    transactionfdlp.commit();
			break;
		case R.id.ll_button_piadrama:
			//Fragment转换，需要把rid传过去
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			FragmentDramaPiajuPage fdpp = new FragmentDramaPiajuPage();
			Bundle bundle = new Bundle();  
            bundle.putString("rid", rid);  
            fdpp.setArguments(bundle);  
		    transaction.replace(R.id.mainContainer,fdpp);
		    transaction.addToBackStack(null);
		    transaction.commit();
			break;
		case R.id.ll_button_comment:
			FragmentTransaction transactionCo = getFragmentManager().beginTransaction();
			FragmentDramaCommentPage fCo = new FragmentDramaCommentPage();
			Bundle bundleCo = new Bundle();  
			bundleCo.putString("rid", rid);  
			fCo.setArguments(bundleCo);  
            transactionCo.replace(R.id.mainContainer,fCo);
            transactionCo.addToBackStack(null);
            transactionCo.commit();
			break;
		case R.id.ll_button_relevant:
			FragmentTransaction transactionRe = getFragmentManager().beginTransaction();
			FragmentDramaRelevantPage f = new FragmentDramaRelevantPage();
			Bundle bundleRe = new Bundle();  
			bundleRe.putString("rid", rid);  
            f.setArguments(bundleRe);  
            transactionRe.replace(R.id.mainContainer,f);
            transactionRe.addToBackStack(null);
            transactionRe.commit();
			break;
		case R.id.ll_button_read:
			FragmentTransaction transactionRead = getFragmentManager().beginTransaction();
			FragmentDramaReadPage fRead = new FragmentDramaReadPage();
			Bundle bundleRead = new Bundle();  
			bundleRead.putString("rid", rid);  
			fRead.setArguments(bundleRead);  
            transactionRead.replace(R.id.mainContainer,fRead);
            transactionRead.addToBackStack(null);
            transactionRead.commit();
			break;
		case R.id.ll_writer_name:
			FragmentTransaction transactionfdwhp = getFragmentManager().beginTransaction();
			FragmentDramaWriterHomePage fdwhp = new FragmentDramaWriterHomePage();
			Bundle bundlefdwhp = new Bundle();  
            bundlefdwhp.putString("rid", rid);  
            fdwhp.setArguments(bundlefdwhp);  
		    transactionfdwhp.replace(R.id.mainContainer,fdwhp);
		    transactionfdwhp.addToBackStack(null);
		    transactionfdwhp.commit();
			break;

		default:
			break;
		}
		
	}
	
	private void loadingDramaDataByRid(String rid)
	{
		System.out.println("====loadingDramaDataByRid====rid:" + rid);
		BGTask task = new BGTask(true, null, "s05?t=9", getActivity()) {
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
				
// {"countAll":0,"alias":["rid","titlt","writerName","ossKey","language","era","length","line","remark"],
//	 			"values":[[null,null,null,null,null,null,null,null,null]],
//	 			"desc":["文件标识","文件标题","作者昵称","文件封面获取码","语种分类编码","时代分类编码","字数","剧情分类编码","简介"]}
				
				try {
					JSONArray drama = obj.optJSONArray(PiaxiConstant.DESC);//正确的应该是VALUES
					DramaBean d = new DramaBean();//全局变量，获取之后就可以在页面中设置值了
//					d.setRid(Integer.parseInt(drama.get(0).toString()));
					d.setTitle(drama.get(1).toString());
					d.setWriterName(drama.get(2).toString());
					d.setOssKey(drama.get(3).toString());
//					d.setLanguage(Integer.parseInt(drama.get(4).toString()));
//					d.setEra(Integer.parseInt(drama.get(5).toString()));
//					d.setLength(Integer.parseInt(drama.get(6).toString()));
//					d.setLine(Integer.parseInt(drama.get(7).toString()));
					d.setRemark(drama.get(8).toString());
					
					System.out.println("=========解析报文得到的剧本对象："+d);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
