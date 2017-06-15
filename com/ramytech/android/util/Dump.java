package com.ramytech.android.util;

import java.util.Map;

public class Dump {
	public static void dumpMap(Map<String, Object> m) {
		for (String key : m.keySet()) {
			System.out.println("k=" + key + ",v=" + m.get(key));
		}
	}
}
