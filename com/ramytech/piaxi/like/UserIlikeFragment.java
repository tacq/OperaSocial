package com.ramytech.piaxi.like;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.ramytech.android.adapter.UserILikeAdapter;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

public class UserIlikeFragment extends Fragment {
	private View view;
	private ListView listView;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	private static final String VALUE = "values";
	private static final String ITEMS_COUNT = "countAll";
	
	private int PAGE_ITEMS_COUNT = 50;
	private int PAGE = 1;
	
	private UserILikeAdapter userILikeAdapter;
	private LinkedList<String> userPhoto;
	private LinkedList<String> userName;
	private LinkedList<String> userLevel;
	private LinkedList<String> idPhoto;
	private LinkedList<String> occupation;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_user_i_like, container, false);
		listView = (ListView)view.findViewById(R.id.list_view_user_i_like);
		virtualData();
		userILikeAdapter = new UserILikeAdapter(getActivity(),
							userPhoto,
							userName,
							userLevel,
							idPhoto,
							occupation);
		listView.setAdapter(userILikeAdapter);
		loadingUserILike();
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("我关注的用户");	
		return view;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.radar_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_radar).setIcon(R.drawable.buttonradar);
		super.onCreateOptionsMenu(menu, inflater);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
//				goToNextPage(new SearchFilterFragment());
				break;
		}
		return true;
	}
	
	private void virtualData(){
		userPhoto = new LinkedList<String>();
		userName = new LinkedList<String>();
		userLevel = new LinkedList<String>();
		idPhoto = new LinkedList<String>();
		occupation = new LinkedList<String>();
		
		userPhoto.add("");
		userName.add("黑山");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("群主");
		
		userPhoto.add("");
		userName.add("兔搞搞");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("美工");
		
		userPhoto.add("");
		userName.add("断背");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("作者");
		
		userPhoto.add("");
		userName.add("卡夫卡");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("声优");
		
		userPhoto.add("");
		userName.add("月半");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("后期");
		
		userPhoto.add("");
		userName.add("欧亚基");
		userLevel.add("");
		idPhoto.add("");
		occupation.add("声优");
	}
	
	private void loadingUserILike(){
		BGTask task = new BGTask(1,true, null, "s09?t=2", getActivity()) {
			@Override
			protected void onPostExecute(String result){
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONArray arr = new JSONArray(result);
						JSONObject jobj = arr.optJSONObject(0);
						//为1获取成功；
						if (jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == 1)
						{
							this.onSuccess(this, jobj.optJSONObject(DATA));
						}
						//获取失败
						else if(jobj.has(ERRCODE) && jobj.getInt(ERRCODE) == -1)
						{
							this.onFail(this, "数据获取失败");
						}
						else if(jobj.has(ERRMSG))
						{
							this.onFail(this, jobj.getString(ERRMSG));
						} else if (jobj.has(ERRCODE)) {
							this.onFail(this, ReturnResult.values()[jobj.getInt(ERRCODE)].name());
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
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", ""+(PAGE++));
		task.setParam("ps", ""+PAGE_ITEMS_COUNT);
		task.execute();
	}
	
	
}
