package com.ramytech.android.util.edititem;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class ImageSelectItem extends EditItem {
	private String secondKey;
	
	public ImageSelectItem(String name, String key, String secondKey) {
		this.type = ItemType.ImagePick;
		this.name = name; 
		this.key = key;
		this.secondKey = secondKey;
	}
	
	public static ImageSelectItem newInstance(String name, String key, String secondKey) {
		ImageSelectItem f = new ImageSelectItem(name, key, secondKey);		
		return f;
	}
	
	@Override
	public void insertFromMap(Map<String, Object> map) {
		super.insertFromMap(map);
		if (map.containsKey(secondKey)) this.showValueStr = map.get(secondKey).toString();
	}
	
	@Override
	public void insertFromJSONObject(JSONObject obj) {
		super.insertFromJSONObject(obj);
		if (obj.has(secondKey))
			try {
				this.showValueStr = obj.get(secondKey).toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Override
	public void parseValue(Object val) {
		if (val instanceof ImageUploadEntity) setSelect(val);
		else if (val instanceof Integer) {
			value = val.toString();
		} else {
			throw new RuntimeException("parse unknown type");
		}
	}

	@Override
	public void setSelect(Object ret) {
		if (ret instanceof ImageUploadEntity) {
			ImageUploadEntity iue = (ImageUploadEntity) ret;			
			this.value = "" + iue.avatar_id;
			this.showValueStr = iue.avatar_url;			
		} else {
			throw new RuntimeException("parse unknown type");
		}
	}
}
