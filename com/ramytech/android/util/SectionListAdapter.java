package com.ramytech.android.util;

import java.util.List;

import android.app.Activity;
import android.database.DataSetObserver;
import android.widget.BaseAdapter;


public abstract class SectionListAdapter extends BaseAdapter {
	public static class SectionListItem {
		public static final int TYPE_SECTION = 0;
		public static final int TYPE_ITEM = 1;		
		public static final int TYPE_ITEM2 = 2;		
		
		public int id;
		public int typeid;
		public Object obj;
		public String showname;
		
		public SectionListItem(int id, int typeid, String showname, Object obj) {
			this.id = id;
			this.typeid = typeid;
			this.obj = obj;
			this.showname = showname;
		}
		
		public static SectionListItem newSectionItem() {
			SectionListItem ret = new SectionListItem(-1, TYPE_SECTION, "", null);
			return ret;
		}
		
		public static SectionListItem newSectionItem(String name) {
			SectionListItem ret = new SectionListItem(-1, TYPE_SECTION, name, null);
			return ret;
		}
	}
	protected List<SectionListItem> items;
	protected Activity activity;		
	
	public SectionListAdapter(Activity activity, List<SectionListItem> items) {
		this.activity = activity;
		this.items = items;	
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {					
		return items.get(position).id;
	}

	@Override
	public int getItemViewType(int position) {
		return items.get(position).typeid;
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return items.size() == 0;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		return items.get(position).typeid == SectionListItem.TYPE_ITEM;
	}
	
}