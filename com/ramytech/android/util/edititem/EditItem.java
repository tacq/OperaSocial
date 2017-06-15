package com.ramytech.android.util.edititem;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;



public abstract class EditItem {
	public static enum ItemType {
		ListSelect, DatePick, TextInput, ImagePick, NonEdit, ListMultiSelect, MultiTextInput, SearchTextInput;
	}
	
	public ItemType type;
	
	public String name, key, hint = null;
	protected String value = "", showValueStr;
		
	public abstract void parseValue(Object val);//use value from  API to init obj
	public abstract void setSelect(Object ret);//use result from user  to set value of obj
	public boolean isValueSet() { return !TextUtils.isEmpty(getValue().toString());}
	public void setDefaultValue() { value = ""; showValueStr = "";}
	
	public void insertFromMap(Map<String, Object> map) {
		if (map.containsKey(key)) parseValue(map.get(key));
	}
	
	public void insertFromJSONObject(JSONObject obj) {
		if (obj.has(key))
			try {
				parseValue(obj.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public EditItem setHint(String hint) {
		this.hint = hint;
		return this;
	}
	
	public Object getValue() {return value;}
	
	public String getShowValue() {return this.showValueStr;}
}
