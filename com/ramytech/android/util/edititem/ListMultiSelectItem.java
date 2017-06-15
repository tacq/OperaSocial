package com.ramytech.android.util.edititem;

import java.text.DateFormat;

import org.json.JSONArray;
import org.json.JSONException;

import com.ramytech.android.util.client.MyJSONParser;

public class ListMultiSelectItem extends EditItem {
	public boolean mSelected[];
	public Object values[];
	
	public DateFormat valueFormatter, showFormatter;
	
	private int maxShowLen = 18;
	
	public static ListMultiSelectItem newInstance(String name, String key, String[] vs) {
		ListMultiSelectItem f = new ListMultiSelectItem();
		f.type = ItemType.ListMultiSelect;
		f.name = name;f.key = key;
		f.values = vs;f.mSelected = new boolean[vs.length];
		f.clear(true);
		return f;
	}
	
	public ListMultiSelectItem setMaxShowLen(int len) {
		this.maxShowLen = Math.max(2, len);
		return this;
	}
	
	public int getSelectedCount() {
		int sum = 0;
		for (int i = 0; i < this.mSelected.length; i ++) if (mSelected[i]) sum ++;
		return sum;
	}
	
	private void clear(boolean v) {
		for (int i = 0; i < this.mSelected.length; i ++) this.mSelected[i] = v;
	}
	
	@Override
	public void parseValue(Object val) {
		boolean tSelect[] = new boolean[this.values.length];
		System.out.println("type=" + val.getClass());
		if (val instanceof int[]) {
			int[] tlist = (int[]) val;
			for (int t : tlist) tSelect[t] = true;
			this.setSelect(tSelect);
		} else if (val instanceof JSONArray) {
			JSONArray ret = (JSONArray) val;
			int[] tlist = new int[ret.length()];
			try {
				MyJSONParser.insertArrayFromJSON(tlist, ret);
				for (int t : tlist) {
					tSelect[t] = true;
				}
				this.setSelect(tSelect);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NegativeArraySizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		} else {
			throw new RuntimeException("parse unknown type");
		}
	}

	@Override
	public void setSelect(Object ret) {
		if (ret instanceof boolean[]) {
			this.mSelected = (boolean[])ret;
			setupValue();
			this.setupShowValue();
		} else 
			throw new RuntimeException();
	}
	
	public void setupValue() {
		String ret = "";
		for (int i = 0; i < this.mSelected.length; i ++) 
			if (this.mSelected[i]) {
				ret += "$" + i;
			}
		if (ret.length() > 0)
			this.value = ret.substring(1);
	}
	
	public void setupShowValue() {
		String ret = "";
		for (int i = 0; i < this.mSelected.length; i ++) 
			if (this.mSelected[i]) {
				ret += "," + this.values[i];		
			}
		if (ret.length() > maxShowLen)
			ret = ret.substring(1, maxShowLen) + "...";
		else if (ret.length() > 0)
			ret = ret.substring(1);
		this.showValueStr = ret;
	}
}