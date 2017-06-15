package com.ramytech.android.util.edititem;


public class TextInputItem extends EditItem {
	
	public TextInputItem(String name, String key, String defaultValue) {
		type = ItemType.TextInput;
		this.name = name;
		this.key = key;
		setSelect(defaultValue);
	}
	
	public static TextInputItem newInstance(String name, String key, String defaultValue) {
		TextInputItem f = new TextInputItem(name, key, defaultValue);
					
		return f;
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
