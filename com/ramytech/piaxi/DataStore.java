package com.ramytech.piaxi;

public interface DataStore {
	public void setData(String key, Object obj);
	public Object getData(String key);
}
