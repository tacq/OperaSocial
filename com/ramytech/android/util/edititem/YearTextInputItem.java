package com.ramytech.android.util.edititem;


public class YearTextInputItem extends TextInputItem {
	
	public YearTextInputItem(String name, String key, String defaultValue) {
		super(name, key, defaultValue);
	}

	public static YearTextInputItem newInstance(String name, String key, String defaultValue) {
		YearTextInputItem f = new YearTextInputItem(name, key, defaultValue);		
		return f;
	}
	
	@Override
	public String getShowValue() {return this.showValueStr + "年";}
}
