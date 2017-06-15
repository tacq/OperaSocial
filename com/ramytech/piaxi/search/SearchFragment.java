package com.ramytech.piaxi.search;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View.OnClickListener;

import com.ramytech.android.adapter.PlayListAdapter;
import com.ramytech.android.adapter.ReusltTipsAdapter;
import com.ramytech.android.adapter.SearchResultAdapter;
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
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SearchFragment extends Fragment {
	private View view;
	private LinearLayout searchTitle;
	private EditText edit;
	
	private LinkedList<String> picUrl;
	private LinkedList<String> playName;
	private LinkedList<String> uploderName;
	private LinkedList<String> characterNumber;
	private LinkedList<String> femaleNumber;
	private LinkedList<String> maleNumber;
	private LinkedList<String> outSiderNumber;
	
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	private static final String VALUE = "values";
	private static final String ITEMS_COUNT = "countAll";
	
	private int page = 1;
	private int PAGE_ITEMS_COUNT = 20;
	private int itemsCount = 0;
	private int finalLoadingItemsCount = 0;
	private int loadingFlag[] ;
	
	
	private ListView searchTipsListView;
	private LinkedList<String> searchTips;
	private ReusltTipsAdapter resultTipsAdapter;
	private int searchType;
	
	private ListView searchResultListView;
	private SearchResultAdapter searchResultAdapter;
	
	
	private int loadingTime = 1;
	
	private boolean loadingDone = true;
	private int targetNumber = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_search, container, false);
		searchTitle = (LinearLayout)view.findViewById(R.id.search_title_search_page);
		edit = (EditText)view.findViewById(R.id.search_context_search_page);
		searchTipsListView = (ListView)view.findViewById(R.id.search_tips_search_page);
		searchResultListView = (ListView)view.findViewById(R.id.result_list_search_page);
		
		initialLinkedList();
		
		resultTipsAdapter = new ReusltTipsAdapter(getActivity(),searchTips);
		searchTipsListView.setAdapter(resultTipsAdapter);
		
		
		searchTipsListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//position（4，5，8，1，3，6，9）
				switch(position)
				{
					case 0:
						searchType = 4;
						break;
					case 1:
						searchType = 5;
						break;
					case 2:
						searchType = 8;
						break;
					case 3:
						searchType = 1;
						break;
					case 4:
						searchType = 3;
						break;
					case 5:
						searchType = 6;
						break;
					case 6:
						searchType = 9;
						break;
				}
				
				searchTipsListView.setVisibility(View.GONE);
				search();
				searchResultListView.setOnScrollListener(new OnScrollListener(){

					@Override
					public void onScrollStateChanged(AbsListView view, int scrollState) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onScroll(AbsListView view, int firstVisibleItem,
							int visibleItemCount, int totalItemCount) {
						if(page<loadingTime)
						{
							if(1+firstVisibleItem+visibleItemCount == (25 + 50*(page-2)) && loadingFlag[page-1] == 0 )
							{
								loadingFlag[page-1] = 1;
								search();	
							}
						}
						
						if(page == loadingTime )
						{
							loadingFlag[page-1] = 1;
							PAGE_ITEMS_COUNT = finalLoadingItemsCount;
							search();	
						}
						
					}
					
				});
			}
			
		});
		
		
		
		edit.addTextChangedListener(new TextWatcher(){

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
				searchTips.clear();
				if(s.length() == 0)
				{	
				}
				else
				{
					searchTips.add("文件名包含“"+s+"”的作品");
					searchTips.add("作者名包含“"+s+"”的作品");
					searchTips.add("人物设定包含“"+s+"”的作品");
					searchTips.add("音色包含“"+s+"”的作品");
					searchTips.add("全文检索包含“"+s+"”的作品");
					searchTips.add("查询码包含“"+s+"”的作品");
					searchTips.add("内容简介包含“"+s+"”的作品");
				}
				
				resultTipsAdapter.updataData(searchTips);
				resultTipsAdapter.notifyDataSetChanged();
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
			
		});
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("搜索");	
		return view;
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.radar_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_radar).setIcon(R.drawable.buttonfilteroutline);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
				goToNextPage(new SearchFilterFragment());
				break;
		}
		return true;
	}
	
	private void goToNextPage(Fragment fragment)
    {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.mainContainer,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
	
	private void initialLinkedList()
	{
		picUrl = new LinkedList<String>();
		playName = new LinkedList<String>();
		uploderName = new LinkedList<String>();
		characterNumber = new LinkedList<String>();
		femaleNumber = new LinkedList<String>();
		maleNumber = new LinkedList<String>();
		outSiderNumber = new LinkedList<String>();
		searchTips = new LinkedList<String>();
	}
	
	private void search()
	{
		searchTitle.setVisibility(View.GONE);
		BGTask task = new BGTask(1,true, null, "s05?t=8", getActivity()) {
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
					JSONArray classList = obj.optJSONArray(VALUE);
					
					if(page == 2)
					{
						itemsCount = obj.optInt(ITEMS_COUNT);
						if(itemsCount<= PAGE_ITEMS_COUNT)
						{
							PAGE_ITEMS_COUNT = itemsCount;
						}
						else
						{
							if(itemsCount%PAGE_ITEMS_COUNT !=0)
							{
								finalLoadingItemsCount = itemsCount % PAGE_ITEMS_COUNT;
								loadingTime = itemsCount/PAGE_ITEMS_COUNT+1;
							}else
							{
								loadingTime = itemsCount/PAGE_ITEMS_COUNT;
							}
							
							loadingFlag = new int [loadingTime];
							loadingFlag[0] = 1;
							for(int i = 1; i <loadingTime; i ++)
							{
								loadingFlag[i] = 0;
							}
						}
					}
					
					targetNumber = classList.length()-1;
					for(int i = 0; i < classList.length(); i++)
					{
						String rid = classList.optJSONArray(i).get(0).toString();
						String titlt = classList.optJSONArray(i).get(1).toString();
						String writerName = classList.optJSONArray(i).get(2).toString();
						String ossKey = classList.optJSONArray(i).get(3).toString();
						
						
						playName.add(titlt);
						picUrl.add(ossKey);
						
						getPlayInfo(rid,i);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", ""+(page++));
		task.setParam("ps", ""+PAGE_ITEMS_COUNT);
		task.setParam("word", edit.getText().toString());
		task.setParam("type",""+searchType);
		task.execute();
	}
	
	private void getPlayInfo(String rid,final int i)
	{
		BGTask task = new BGTask(1,true, null, "s05?t=9", getActivity()) {
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
					
					String rid  = classList.optJSONArray(0).get(0).toString();
					String titlt = classList.optJSONArray(0).get(1).toString();
					String writerName  = classList.optJSONArray(0).get(2).toString();
					String ossKey = classList.optJSONArray(0).get(3).toString();
					String language  = classList.optJSONArray(0).get(4).toString();
					String era = classList.optJSONArray(0).get(5).toString();
					String length  = classList.optJSONArray(0).get(6).toString();
					String line = classList.optJSONArray(0).get(7).toString();
					String remark  = classList.optJSONArray(0).get(8).toString();
					String uploadTime = classList.optJSONArray(0).get(9).toString();
					String protagonist = classList.optJSONArray(0).get(10).toString();
					String personNum = classList.optJSONArray(0).get(11).toString();
					
					uploderName.add(writerName);
					characterNumber.add(length);

					maleNumber.add(getNumberString(personNum.substring(1, 4)));
					femaleNumber.add(getNumberString(personNum.substring(4, 7)));
					outSiderNumber.add(getNumberString(personNum.substring(7, 10)));
					
					System.out.println("i:"+i + " targetNumber:"+targetNumber);
					if(i == targetNumber)
					{
						if(page == 2)
						{
							searchResultAdapter = new SearchResultAdapter(getActivity(),
																			picUrl,
																			playName,
																			uploderName,
																			characterNumber,
																			femaleNumber,
																			maleNumber,
																			outSiderNumber);
							searchResultListView.setAdapter(searchResultAdapter);
							searchResultListView.setVerticalScrollBarEnabled(false);
							
//							//数据总数
//							itemsCount = obj.optInt(ITEMS_COUNT);
//							if(itemsCount<= PAGE_ITEMS_COUNT)
//							{
//								PAGE_ITEMS_COUNT = itemsCount;
//							}
//							else
//							{
//								if(itemsCount%PAGE_ITEMS_COUNT !=0)
//								{
//									finalLoadingItemsCount = itemsCount % PAGE_ITEMS_COUNT;
//									loadingTime = itemsCount/PAGE_ITEMS_COUNT+1;
//								}else
//								{
//									loadingTime = itemsCount/PAGE_ITEMS_COUNT;
//								}
//								
//								loadingFlag = new int [loadingTime];
//								loadingFlag[0] = 1;
//								for(int i = 1; i <loadingTime; i ++)
//								{
//									loadingFlag[i] = 0;
//								}
//							}
						}
						else
						{
							searchResultAdapter.updataData(picUrl,
														playName,
														uploderName,
														characterNumber,
														femaleNumber,
														maleNumber,
														outSiderNumber);
							searchResultAdapter.notifyDataSetChanged();
						}
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", "1");
		task.setParam("ps", "1");
		task.setParam("rid", rid);
		task.execute();
	}
	
	private String getNumberString(String s)
	{
		String result="0";
		for(int i = 0; i <s.length();i++)
		{
			if(s.charAt(i) != '0')
			{
				return s.substring(i, s.length());
			}
		}
		return result;
	}
}
