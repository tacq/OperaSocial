package com.ramytech.piaxi.like;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ramytech.android.adapter.OrganizationListAdapter;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;

public class GroupIlikeFragment extends Fragment {
	private View view;
	private ListView listView;
	
	private int PAGE = 1;
	private int PAGE_ITEMS_COUNT = 50;
	private int itemsCount = 0;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	private static final String VALUE = "values";
	private static final String ITEMS_COUNT = "countAll";
	
	private int[] organizaitonIcon;
	private String[] organizationName;
	private String[] organizationIntroduction;
	
	private int[] member1;
	private int[] member2;
	private int[] member3;
	private int[] memberMore;
	
	private String[] countOfMember;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_group_i_like, container, false);
		listView = (ListView)view.findViewById(R.id.list_view_group_i_like);
		
		LoadingGroupsIlike();
		initial();
		OrganizationListAdapter organizationListAdapter= new OrganizationListAdapter(getActivity().getBaseContext(),
				organizaitonIcon,organizationName,organizationIntroduction,member1,member2,member3,memberMore,countOfMember);
		
		listView.setAdapter(organizationListAdapter);
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("我关注的社团");	
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
	
	private void addGroupslike(){
		
	}
	
	private void LoadingGroupsIlike()
	{
		BGTask task = new BGTask(PAGE,true, null, "s14?t=10", getActivity()) {
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
					//剧组数量
					itemsCount = obj.optInt("countAll");
					JSONArray classList = obj.optJSONArray(VALUE);
//					String area = classList.optJSONArray(0).get(1).toString();;
//					String count = classList.optJSONArray(0).get(2).toString();;
//					String pic = classList.optJSONArray(0).get(3).toString();
					
					System.out.println("itemsCount"+itemsCount);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", ""+PAGE);			
		task.setParam("ps", ""+PAGE_ITEMS_COUNT);
		task.execute();
	}
	
	private void initial()
	{
		//TODO getDate;
		
		//test Date;
		organizaitonIcon = new int[3];
		organizationName = new String[3];
		organizationIntroduction = new String[3];
		
		member1 = new int[3];
		member2 = new int[3];
		member3 = new int[3];
		memberMore = new int[3];
		
		countOfMember = new String[3];
		
		for(int i = 0; i<3;i++)
		{
			organizaitonIcon[i] = R.drawable.picnopic;
			organizationIntroduction[i] = "伙伴们一起愉快的玩耍吧";
			member1[i] = R.drawable.head60px;
			member2[i] = R.drawable.head60px;
		}
		
		organizationName[0] = "FFF小分队";
		organizationName[1] = "查水表！快开门";
		organizationName[2] = "壮哉我大古风";
		
		member3[0] = 0;
		member3[1] = R.drawable.head60px;
		member3[2] = R.drawable.head60px;
		
		memberMore[0] = 0;
		memberMore[1] = R.drawable.buttonmoremember;
		memberMore[2] = R.drawable.buttonmoremember;
		
		countOfMember[0] = "2个成员";
		countOfMember[1] = "12个成员";
		countOfMember[2] = "6个成员";	
		
	}
	
}
