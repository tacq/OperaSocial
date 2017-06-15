package com.ramytech.piaxi.like;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View.OnClickListener;

import com.ramytech.android.adapter.PageAdapter;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class LikeHomePageFragment extends Fragment implements OnClickListener{
	private View view;
	private ViewPager mPager;
	private List<ImageView> listViews; 
	private LinearLayout usersILike;
	private LinearLayout groupsIJoin;
	private ListView recommendUsersList;
	private ListView recommendGroupsList;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	private static final String VALUE = "values";
	private static final String ITEMS_COUNT = "countAll";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_like_homepage, container, false);
		mPager = (ViewPager)view.findViewById(R.id.ad_like_page);
		listViews = new ArrayList<ImageView>();
		usersILike = (LinearLayout)view.findViewById(R.id.users_i_like);
		groupsIJoin = (LinearLayout)view.findViewById(R.id.groups_i_join);
		recommendUsersList = (ListView)view.findViewById(R.id.recommend_users);
		recommendGroupsList = (ListView)view.findViewById(R.id.recommend_groups);
		
		usersILike.setOnClickListener(this);
		groupsIJoin.setOnClickListener(this);
		
		LoadingAdPictures();
		LoadingUserILike();
		LoadingUserUnLike();
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("关注");	
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
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
			case R.id.users_i_like:
				goToNextPage(new UserIlikeFragment());
				break;
			case R.id.groups_i_join:
				goToNextPage(new GroupIlikeFragment());
				break;
		}
		
	}
	
	private void LoadingAdPictures()
	{
		BGTask task = new BGTask(1,true, null, "s09?t=6", getActivity()) {
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
				try {
					JSONArray classList = obj.optJSONArray(VALUE);
					String area = classList.optJSONArray(0).get(1).toString();;
					String count = classList.optJSONArray(0).get(2).toString();;
					String pic = classList.optJSONArray(0).get(3).toString();
					
					System.out.println("area"+area);
					
//					mPager.setAdapter(new PageAdapter(listViews));
//					mPager.setCurrentItem(0);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
	
	private void LoadingUserILike()
	{
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
				try {
					JSONArray classList = obj.optJSONArray(VALUE);
					
					String name = classList.optJSONArray(0).get(1).toString();;
					String ossKey = classList.optJSONArray(0).get(2).toString();;
					
					System.out.println("length:"+classList.length()+" name"+name);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
	
	private void LoadingUserUnLike()
	{
		BGTask task = new BGTask(1,true, null, "s09?t=7", getActivity()) {
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
				System.out.println("asdf"+obj);
				try {
					JSONArray classList = obj.optJSONArray(VALUE);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
	
	private void goToNextPage(Fragment fragment)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
	
}
