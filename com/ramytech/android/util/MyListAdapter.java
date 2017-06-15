package com.ramytech.android.util;

import android.app.Activity;
import android.database.DataSetObserver;
import android.widget.BaseAdapter;

public abstract class MyListAdapter extends BaseAdapter {
	
	protected Object[] items;
	protected Activity activity;		
	
	public MyListAdapter(Activity activity, Object[] items) {
		this.activity = activity;
		this.items = items;	
	}
	
	public void setNewData(Object[] items) {
		this.items = items;
		this.notifyDataSetChanged();
	}
	
	
	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {					
		return -1;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return items.length == 0;
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
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}
	
}