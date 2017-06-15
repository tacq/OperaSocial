package com.ramytech.piaxi.homepage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.ramytech.android.adapter.HomeListAdapter;
import com.ramytech.android.bean.HomeBean;
import com.ramytech.android.constants.PiaxiConstant;
import com.ramytech.android.util.ErrorToast;
import com.ramytech.android.util.MoreSubMenu;
import com.ramytech.android.util.MyDragListView;
import com.ramytech.android.util.PeoplenearbySubMenu;
import com.ramytech.android.util.RadarSubMenu;
import com.ramytech.android.util.client.APIFunction;
import com.ramytech.android.util.client.APIManager;
import com.ramytech.android.util.client.BGTask;
import com.ramytech.android.util.client.ReturnResult;
import com.ramytech.piaxi.R;
import com.ramytech.piaxi.db.Category;
import com.ramytech.piaxi.db.DBHelper;

public class FragmentHomePage extends Fragment{
	private View view;
	private LinearLayout buttonHomepageMore;
	private MyDragListView classList;
	private ListView playList;
	
	private List<HomeBean> homeBeanList;
	
	private int initialSelectedItem = 0;//默认选择的类别，是第一个 “推荐”
		
	//database: to save user classify info
	private static final String CLASS_NAME = "class_name"; 
	private static final String DELETED_CLASS_NAME = "deleted_class_name";
	
//	private String nowCode = "1";//用于记录现在的查询编码，默认是1，推荐
//	private ArrayList<String> array;//左边类别列表的数据
//	private ArrayList<Integer> arrayEncoding;//类别列表对应的查询编码列表
	private MyAdapter classListAdapter = null;
	
	private int ver;
	
	private int fixedCount;
	
	private static final int MAX_LIST = 40;
	
	private static final int APP_START = 1;
	private static final int APP_RUNNING = 0;

//	LinkedList<Category> categoryList = new LinkedList<Category>();
	List<Category> categoryList = new ArrayList<Category>();
	private Category categoryForQuery = new Category();//code为1的Category对象，用于查询内容时的传值
	
	private int PAGE = 1;
	private int PAGE_ITEMS_COUNT = 50;
	private int itemsCount = 0;
	
	private int finalLoadingItemsCount = 0;
	
//	private PlayListAdapter playListAdapter;
	//TODO 上面的要删掉，只用下面的就好了
	private HomeListAdapter homeListAdapter;
	
	
	private int loadingFlag[] ;
	private int loadingTime = 1;
	
	private DbUtils db = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_homepage, container, false);
		buttonHomepageMore = (LinearLayout)view.findViewById(R.id.button_homepage_more);
		classList = (MyDragListView)view.findViewById(R.id.class_list);
		playList = (ListView)view.findViewById(R.id.play_list);
		
		db = DBHelper.getIns(getActivity()).getDB();
		
		//FIXME 这样赋值有问题的，这样就不知道用哪种展示方式了
		categoryForQuery = new Category();
		categoryForQuery.setCode("1");//默认设置为1，推荐
		categoryForQuery.setType(PiaxiConstant.TYPE_JUBEN);
		setPlayList();

		buttonHomepageMore.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
			    FragmentTransaction transaction = getFragmentManager().beginTransaction();
			    transaction.replace(R.id.mainContainer,new FragmentClassifiedPage());
			    transaction.addToBackStack(null);
			    transaction.commit();
			}
			
		});
		
		playList.setOnScrollListener(new OnScrollListener(){

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if(PAGE<loadingTime)
				{
					if(1+firstVisibleItem+visibleItemCount == (25 + 50*(PAGE-2)) && loadingFlag[PAGE-1] == 0 )
					{
						loadingFlag[PAGE-1] = 1;
						loadingClassContentListData(categoryForQuery);	
					}
				}
				
				if(PAGE == loadingTime )
				{
					loadingFlag[PAGE-1] = 1;
					PAGE_ITEMS_COUNT = finalLoadingItemsCount;
					loadingClassContentListData(categoryForQuery);	
				}
			}
			
		});
		
		playList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				//根据剧和剧本的不同，点击进入不同的界面
				HomeBean h = homeBeanList.get(position);
				if(h.getFlag() == PiaxiConstant.TYPE_INT_JUBEN){
					String rid = h.getRid();
					FragmentTransaction transaction = getFragmentManager().beginTransaction();
					FragmentDramaHomePage fdhp = new FragmentDramaHomePage();
					Bundle bundle = new Bundle();  
	                bundle.putString("rid", rid);  
	                fdhp.setArguments(bundle);  
				    transaction.replace(R.id.mainContainer,fdhp);
				    transaction.addToBackStack(null);
				    transaction.commit();
				}else{
					Intent intent = new Intent();
					intent.putExtra("cid", h.getCid());
					intent.setClass(getActivity(), JuIntroduceActivity.class);
					startActivity(intent);
				}
			}
		});
		
		
		setHasOptionsMenu(true);
		getActivity().getActionBar().setTitle("首页");	
		return view;
	}
	
	private static final String DESC = "desc";
	

	//TODO 这个是实现列表换位置的
//	private MyDragListView.DropListener onDrop = new MyDragListView.DropListener() {
//        @Override
//        public void drop(int from, int to) {
//        	
//        	if( to<effectiveClassNumber() && from>(fixedCount-1))
//        	{
//        		String item = classListAdapter.getItem(from);
//	            classListAdapter.remove(item);
//	            classListAdapter.insert(item, to);
//	            classListAdapter.setSelectedPosition(to);
//	            saveNewOrder(from,to);
//	            classListAdapter.notifyDataSetChanged();
//        	}
//
//        }
//    };
	
	public void initialFirstSelectedItem()
	{
		View itemView = classList.getChildAt(0);
		LinearLayout linearLayout;
		TextView text;

		linearLayout = (LinearLayout)itemView.findViewById(R.id.class_list_item_layout);
		text = (TextView)itemView.findViewById(R.id.tag_name);
		linearLayout.setBackground(view.getContext().getResources().getDrawable(R.drawable.bghomepageredrectangle));
		text.setTextColor(Color.WHITE);
	}
	
//	public ArrayList<String> getClassListData()
//	{
//		ArrayList<String> data = new ArrayList<String>();
//		try {
//			List<Category> list = db.findAll(Selector.from(Category.class).where("flag", "=", 1));
//			for(int i=0;i<list.size();i++){
//				data.add(list.get(i).getName());
//			}
//		} catch (DbException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		arrayEncoding = new ArrayList<Integer>();
////				
////		SharedPreferences sp = view.getContext().getSharedPreferences(CLASS_NAME, 0);
////		
////		for(int i = 0; i<MAX_LIST;i++)		
////		{
////			if(!(sp.getString("class"+i, "标签").equals("标签"))){
////				data.add(sp.getString("class"+i, "标签"));
////				arrayEncoding.add(sp.getInt("encoding"+i, 0));
////			}	
////			else
////				return data;
////		}
//		return data;
//	}
	
	private int effectiveClassNumber()
	{
		SharedPreferences sp = view.getContext().getSharedPreferences(CLASS_NAME, 0);
		for(int i = 0; i<MAX_LIST;i++)
		{
			String labText = sp.getString("class"+i, "标签");
			if(labText.equals("标签"))
			{
				return i;
			}
		}
		return 0;
		
	}
	
	public void setPlayList()
	{
		
		//左侧列表数据段
		SharedPreferences sp = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
		int app_status = sp.getInt("app_state", 0);
		System.out.println("==============app_status:"+app_status);
		if(app_status == APP_START)
		{
			System.out.println("11111111111111111111111111111111111");
			loadingClassListData();
			
			final SharedPreferences sp0 = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp0.edit();
//			SharedPreferences.Editor editor = sp.edit();
			editor.putInt("app_state", APP_RUNNING);
			editor.commit();
			int app_status1 = sp.getInt("app_state", 0);
			System.out.println("==============app_status1:"+app_status1);
		}
		
		if(app_status == APP_RUNNING)
		{
			System.out.println("2222222222222222222222222");
//			SharedPreferences sp0 = view.getContext().getSharedPreferences(CLASS_NAME, 0);
//			fixedCount = sp0.getInt("fixedCount", -1);
			initinalClassList();
		}
		System.out.println("33333333333333333333333333333");
		//右侧显示的对应列表的数据。
		homeBeanList = new ArrayList<HomeBean>();
		
		loadingClassContentListData(categoryForQuery);

	}
	
	
	private void saveNewOrder(int from, int to)
	{
		SharedPreferences sp = view.getContext().getSharedPreferences(CLASS_NAME, 0);
		SharedPreferences.Editor editor = sp.edit();
		String dragString = sp.getString("class"+from, "标签");
		int i;
		for(i = from;i < to;i++)
		{
			editor.putString("class"+i, sp.getString("class"+(i+1), "标签"));
		}
		editor.putString("class"+i,dragString);
		editor.commit();
	}
	
	private class MyAdapter extends BaseAdapter {

        private int selectedPosition = -1;

        public void setSelectedPosition(int selectedPosition)
        {
            this.selectedPosition = selectedPosition;
        }

        
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                row = inflater.inflate(R.layout.class_list_item, parent, false);
            }
            LinearLayout background = (LinearLayout)row.findViewById(R.id.class_list_item_layout);
            TextView label = (TextView) row.findViewById(R.id.tag_name);
            label.setText(categoryList.get(position).getName());

            if(position == selectedPosition)
            {
            	label.setTextColor(Color.WHITE);
                background.setBackground(getResources().getDrawable(R.drawable.bghomepageredrectangle));
                if(!(categoryList.get(position).getName().equals("标签")))
                	background.setTag("choosed");
            }
            else
            {
            	label.setTextColor(Color.BLACK);
                background.setBackground(getResources().getDrawable(R.drawable.bghomepagegreyrectangle));
                background.setTag("");
            }
            return (row);
        }
		@Override
		public int getCount() {
			return categoryList.size();
		}
		@Override
		public Object getItem(int arg0) {
			return null;
		}
		@Override
		public long getItemId(int arg0) {
			return 0;
		}
    }
	

//	private class MyAdapter extends ArrayAdapter<String> {
//
//	        private int selectedPosition = -1;
//
//	        MyAdapter() {
//	            super(getActivity(), R.layout.class_list_item, array);
//	        }
//	        public ArrayList<String> getList() {
//	            return array;
//	        }
//
//	        public void setSelectedPosition(int selectedPosition)
//	        {
//	            this.selectedPosition = selectedPosition;
//	        }
//
//	        
//	        public View getView(int position, View convertView, ViewGroup parent) {
//	            View row = convertView;
//	            if (row == null) {
//	                LayoutInflater inflater = getActivity().getLayoutInflater();
//	                row = inflater.inflate(R.layout.class_list_item, parent, false);
//	            }
//	            LinearLayout background = (LinearLayout)row.findViewById(R.id.class_list_item_layout);
//	            TextView label = (TextView) row.findViewById(R.id.tag_name);
//	            label.setText(array.get(position));
//
//	            if(position == selectedPosition)
//	            {
//	            	label.setTextColor(Color.WHITE);
//	                background.setBackground(getResources().getDrawable(R.drawable.bghomepageredrectangle));
//	                if(!(getClassListData().get(position).equals("标签")))
//	                	background.setTag("choosed");
//	            }
//	            else
//	            {
//	            	label.setTextColor(Color.BLACK);
//	                background.setBackground(getResources().getDrawable(R.drawable.bghomepagegreyrectangle));
//	                background.setTag("");
//	            }
//	            return (row);
//	        }
//	    }
	
	//我把常量放在常量类里面了哈
	private static final String ERRCODE = "rstVal";
	private static final String ERRMSG = "msg";
	private static final String DATA = "data";
	
	private static final String VER = "ver";
	private static final String VALUE = "values";
	
	private static final String ITEMS_COUNT = "countAll";
	
	private void loadingClassListData() {
		System.out.println("============loadingClassListData===========");
		BGTask task = new BGTask(true, null, "s05?t=1", getActivity()) {
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

				this.onFail(this, "数据获取失败lll");
			}
			
			@Override
			public void onSuccess(APIFunction api, Object resObj) {
				super.onSuccess(api, resObj);
				JSONObject obj = ((JSONObject) resObj);
				System.out.println("loadingClassListData succ:" + obj);
				try {
					JSONArray classList = obj.optJSONArray(VALUE);
					//版本号
					ver = obj.optInt(VER);
					SharedPreferences sp = view.getContext().getSharedPreferences(CLASS_NAME, 0);
					//不等于版本号，更新数据
					if(sp.getInt("version", -1) != ver)
					{
						SharedPreferences.Editor editor = sp.edit();
						editor.putInt("version", ver);
						new ErrorToast("分类列表已更新",getActivity().getBaseContext()).showErrorToast();
						db.deleteAll(Category.class);
						for(int i = 0; i < classList.length(); i++)
						{
							Category c = new Category();
							c.setFlag(1);
							c.setType(classList.optJSONArray(i).get(0).toString());
							c.setName(classList.optJSONArray(i).get(1).toString());
							c.setCode(classList.optJSONArray(i).get(2).toString());
							c.setIsfix(Integer.parseInt(classList.optJSONArray(i).get(3).toString()));
							db.save(c);
							categoryList.add(c);
						}
						
//						//往数据库里存储数据
//						fixedClass = new LinkedList<String>();
//						unfixedClass = new LinkedList<String>();
//						fixedEncoding = new LinkedList<Integer>();
//						unfixedEncoding = new LinkedList<Integer>();
//						DataBaseHelper dataBaseHelper = new DataBaseHelper(getActivity().getBaseContext());
//						SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
//						//清空原来数据表
//						db.execSQL("DELETE FROM play_classification");
//						
//						for(int i = 0; i < classList.length(); i++)
//						{
//							int playType = Integer.parseInt(classList.optJSONArray(i).get(0).toString());
//							String playName = classList.optJSONArray(i).get(1).toString();
//							int playEncoding = Integer.parseInt(classList.optJSONArray(i).get(2).toString());
//							int order = Integer.parseInt(classList.optJSONArray(i).get(3).toString());
//							db.execSQL("insert into play_classification(play_type,play_name,play_encoding,play_order) values(?,?,?,?)", 
//									new Object[] {playType,playName,playEncoding,order});
//							if(order < 0)
//							{
//								fixedClass.add(playName);
//								fixedEncoding.add(playEncoding);
//							}
//							else
//							{
//								unfixedClass.add(playName);
//								unfixedEncoding.add(playEncoding);
//							}
//						}
//						System.out.println("========fixedClass=======:"+fixedClass);
//						System.out.println("========fixedEncoding=======:"+fixedEncoding);
//						System.out.println("========unfixedClass=======:"+unfixedClass);
//						System.out.println("========unfixedEncoding=======:"+unfixedEncoding);
//						db.close();
//						
//						editor.putInt("fixedCount", fixedClass.size());
//						fixedCount = fixedClass.size();
//						int i = 0;
//						for(i = 0 ; i < fixedClass.size();i++)
//						{
//							editor.putString("class"+i,fixedClass.get(i));
//							editor.putInt("encoding"+i,fixedEncoding.get(i));
//						}
//						int j = 0;
//						for(j = 0 ; j < unfixedClass.size();j++)
//						{
//							editor.putString("class"+(i+j),unfixedClass.get(j));
//							editor.putInt("encoding"+(i+j),unfixedEncoding.get(j));
//						}
//						for(int k = 0; k <MAX_LIST-j-i;k++)
//						{
//							editor.putString("class"+(i+j+k),"标签");
//						}
//						
//						editor.commit();
//						//清空删除列表
//						initialDeletedClassName();
						initinalClassList();
					}
					else
					{
//						SharedPreferences sp0 = view.getContext().getSharedPreferences(CLASS_NAME, 0);
//						fixedCount = sp0.getInt("fixedCount", -1);
						initinalClassList();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.execute();
	}
	
	private void loadingClassContentListData(Category categoryForQuery)
	{
		final Category category = categoryForQuery;
		System.out.println("====loadingClassContentListData====category:" + category +",PAGE:"+PAGE);
		BGTask task = new BGTask(PAGE,true, null, "s05?t=3", getActivity()) {
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
				System.out.println("loadingClassContentListData succ:" + obj);
				try {
					JSONArray classList = obj.optJSONArray(VALUE);
					//版本号
					if(PiaxiConstant.TYPE_JUBEN.equals(category.getType())){//接收到的是剧本
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						for(int n = 0; n < classList.length(); n++)
						{
							HomeBean h = new HomeBean();
							h.setFlag(PiaxiConstant.TYPE_INT_JUBEN);
							h.setRid(classList.optJSONArray(n).get(0).toString());
							h.setTitle(classList.optJSONArray(n).get(1).toString());
							h.setWriterName(classList.optJSONArray(n).get(2).toString());
							h.setOssKey(classList.optJSONArray(n).get(3).toString());
							h.setBucket(classList.optJSONArray(n).get(4).toString());
							homeBeanList.add(h);
						}
					}else if(PiaxiConstant.TYPE_JU.equals(category.getType())){//接收到的是剧
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						for(int j = 0; j < classList.length(); j++)
						{
							HomeBean h = new HomeBean();
							h.setFlag(PiaxiConstant.TYPE_INT_JU);
							h.setCid(classList.optJSONArray(j).get(0).toString());
							h.setCname(classList.optJSONArray(j).get(1).toString());
							h.setCnote(classList.optJSONArray(j).get(2).toString());
							h.setOssKey(classList.optJSONArray(j).get(3).toString());
							h.setBucket(classList.optJSONArray(j).get(4).toString());
							homeBeanList.add(h);
						}
					}
					
					//page定位到第二页时数据加载完毕
					if(PAGE == 2)
					{
//						playListAdapter = new PlayListAdapter(view.getContext(),picId,playNameList,playWriterList,ridList);
//						playList.setAdapter(playListAdapter);
//						playList.setVerticalScrollBarEnabled(false);
						
						homeListAdapter = new HomeListAdapter(view.getContext(), homeBeanList);
						playList.setAdapter(homeListAdapter);
						playList.setVerticalScrollBarEnabled(false);
						
						//数据总数
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
							for(int k = 1; k <loadingTime; k ++)
							{
								loadingFlag[k] = 0;
							}
						}
					}
					else
					{
//						playListAdapter.updataData(picId, playNameList, playWriterList,ridList);
//						playListAdapter.notifyDataSetChanged();
						homeListAdapter.updataData(homeBeanList);
						homeListAdapter.notifyDataSetChanged();
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		task.setParam("pid", APIManager.shared().getUID());			
		task.setParam("key", APIManager.shared().getToken());
		task.setParam("pn", ""+(PAGE++));
		task.setParam("ps", ""+PAGE_ITEMS_COUNT);
		task.setParam("code", category.getCode());
		task.execute();
	}
	
	public void initialDeletedClassName()
	{
		SharedPreferences sp = view.getContext().getSharedPreferences(DELETED_CLASS_NAME, 0);
		SharedPreferences.Editor editor = sp.edit();
		for(int i = 0; i < MAX_LIST;i++)
		{
			editor.putString("deleted_class_name"+i, "空");
		}
		editor.commit();
	}
	
	private void initinalClassList()
	{
//		array = getClassListData();
		try {
			categoryList = db.findAll(Selector.from(Category.class).where("flag", "=", "1"));
			System.out.println("---------categoryList:"+categoryList);
			categoryForQuery = categoryList.get(0);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		classListAdapter = new MyAdapter();
		classList.setAdapter(classListAdapter);
//		classList.setDropListener(onDrop);
		classList.setDivider(null);
		classList.setVerticalScrollBarEnabled(false);
		classListAdapter.setSelectedPosition(initialSelectedItem);
		classListAdapter.notifyDataSetChanged();
				
		classList.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long arg3) {
				classListAdapter.setSelectedPosition(position);
				classListAdapter.notifyDataSetChanged();
				System.out.println("点击左边类别了，类别："+categoryList.get(position).getName()+",nowEncoding:"+categoryList.get(position).getCode());
				//找到点击类别对应的查询编码，调用接口查询对应类别的内容
//				picId = new LinkedList<Integer>();
//				playNameList = new LinkedList<String>();
//				playWriterList = new LinkedList<String>();
//				ridList = new LinkedList<Integer>();
				homeBeanList = new ArrayList<HomeBean>();
				PAGE = 1;
				categoryForQuery = categoryList.get(position);
				loadingClassContentListData(categoryForQuery);
			}
			
		});
		
		//初始化ClassList之后再查询内容类表，不然不知道返回的值是什么
		//右侧显示的对应列表的数据。
//		picId = new LinkedList<Integer>();
//		playNameList = new LinkedList<String>();
//		playWriterList = new LinkedList<String>();
//		ridList = new LinkedList<Integer>();
//		
//		loadingClassContentListData(categoryForQuery);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.clear();
		inflater.inflate(R.menu.drama_read_menu, menu);
		menu.findItem(R.id.menu_radar).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_peoplenearby).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.findItem(R.id.menu_more).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		super.onCreateOptionsMenu(menu, inflater);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())
		{
			case R.id.menu_radar:
				new RadarSubMenu(getActivity()).show();
				break;
			case R.id.menu_peoplenearby:
				new PeoplenearbySubMenu(getActivity()).show();
				break;
			case R.id.menu_more:
				new MoreSubMenu(getActivity()).show();
				break;
		}
		return true;
	}
	
}

