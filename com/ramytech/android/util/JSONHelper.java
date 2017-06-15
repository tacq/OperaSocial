package com.ramytech.android.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

public class JSONHelper {
	public static Map<String, Object> jsonToMap(JSONObject obj) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Iterator<String> it = obj.keys();
		while (it.hasNext()) {
			String key = it.next();
			ret.put(key, obj.opt(key));
		}
		return ret;
	}
}
