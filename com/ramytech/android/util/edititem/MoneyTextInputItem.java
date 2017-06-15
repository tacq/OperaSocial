package com.ramytech.android.util.edititem;


public class MoneyTextInputItem extends TextInputItem {
	
	public MoneyTextInputItem(String name, String key, String defaultValue) {
		super(name, key, defaultValue);
	}

	public static MoneyTextInputItem newInstance(String name, String key, String defaultValue) {
		MoneyTextInputItem f = new MoneyTextInputItem(name, key, defaultValue);		
		return f;
	}
	
	@Override
	public String getShowValue() {return "¥" + this.showValueStr;}
}
