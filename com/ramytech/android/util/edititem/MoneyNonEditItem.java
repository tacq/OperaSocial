package com.ramytech.android.util.edititem;

public class MoneyNonEditItem extends NonEditItem {
	
	public MoneyNonEditItem(String name, String key, String showValue) {
		super(name, key, showValue);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setSelect(Object ret) {
		try {
			Double.parseDouble((String)ret);
		} catch (Exception ex) {return;}
		
		super.setSelect(ret);
	}
	
	@Override
	public String getShowValue() {return "¥" + this.showValueStr;}
}
