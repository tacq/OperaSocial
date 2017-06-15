package com.ramytech.android.util.edititem;


public class ListSelectItem extends EditItem {
	protected int selected, defaultSelect;
	protected Object[] values;
	
	public static ListSelectItem newInstance(String name, String key, Object[] vs, int defaultSelect) {
		ListSelectItem f = new ListSelectItem();
		f.type = ItemType.ListSelect;
		f.name = name; 
		f.key = key;
		f.values = vs;
		f.defaultSelect = defaultSelect;
		f.setSelect(f.defaultSelect);
		return f;
	}

	@Override
	public void parseValue(Object val) {
		if (val instanceof Integer) {
			this.setSelect(val);
		} else if (val instanceof String)
			this.setSelect(Integer.parseInt((String)val));
		else {
			throw new RuntimeException("parse unknown type");	
		}
	}

	@Override
	public void setSelect(Object ret) {
		this.selected = (Integer) ret;
		this.value = "" + selected;
		this.showValueStr = this.values[selected].toString();
	}

	public String[] getShowValues() {
		String ret[] = new String[this.values.length];
		for (int i = 0; i < ret.length; i ++) ret[i] = this.values[i].toString();
		return ret;
	}
	
	public int getSelected() { return selected;}
}
