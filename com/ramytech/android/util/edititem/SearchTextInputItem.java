package com.ramytech.android.util.edititem;

import java.util.Map;

import org.json.JSONObject;



public class SearchTextInputItem extends EditItem {
	public static interface SearchTaskParser {
		public String[] parserResult(JSONObject obj);
	}

	public static class SearchTask {
		public boolean isPost;
		public String url;
		public Map<String, Object> params;
		public SearchTaskParser searchTaskParser;
		
		public SearchTask (boolean isP, String url, Map<String, Object> params, SearchTaskParser parser) {
			this.isPost = isP;
			this.url = url;
			this.params = params;
			this.searchTaskParser = parser;
		}
	}
	
	public SearchTask task;
	
	public SearchTextInputItem(String name, String key, String defaultValue, SearchTask task) {
		type = ItemType.SearchTextInput;
		this.name = name;
		this.key = key;
		this.task = task;
		setSelect(defaultValue);
	}	

	@Override
	public void parseValue(Object val) {
		if (val instanceof Integer)
			this.setSelect("" + val);
		else if (val instanceof String)
			this.setSelect(val);
		else {
			throw new RuntimeException("parse unknown type");
		}
	}

	@Override
	public void setSelect(Object ret) {
		this.value = (String) ret;
		this.showValueStr = this.value;
	}
}
