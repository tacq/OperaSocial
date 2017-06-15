package com.ramytech.android.util.edititem;


public class NonEditItem extends EditItem {
	
	public NonEditItem(String name, String key, String showValue) {
		super();
		this.type = ItemType.NonEdit;
		this.name = name; 
		this.key = key;
		this.showValueStr = showValue;
	}
	
	@Override
	public void parseValue(Object val) {
		//do nothing
	}

	@Override
	public void setSelect(Object ret) {
		//do nothing
	}
}
