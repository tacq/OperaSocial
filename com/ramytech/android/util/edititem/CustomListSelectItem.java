package com.ramytech.android.util.edititem;



public class CustomListSelectItem extends ListSelectItem {
	public static class PairValue implements Value {
		public PairValue(int key, String value) {
			this.key = key;
			this.value = value;
		}
		private int key;
		private String value;
		@Override
		public String getValue() {
			return "" + key;
		}
		@Override
		public String getShowValue() {
			return value;
		}
	}
	
	public static class StringValue implements Value {
		public StringValue(String value) {
			this.value = value;
		}
		private String value;
		@Override
		public String getValue() {
			return value;
		}
		@Override
		public String getShowValue() {
			return value;
		}
	}
	
	public abstract static interface Value {
		public abstract String getValue();
		public abstract String getShowValue();
	}
	
	public static CustomListSelectItem newInstance(String name, String key, Value[] vs, int defaultSelect) {
		CustomListSelectItem f = new CustomListSelectItem();
		f.type = ItemType.ListSelect;
		f.name = name; 
		f.key = key;
		f.values = vs;
		f.defaultSelect = defaultSelect;
		f.setSelect(f.defaultSelect);
		return f;
	}
	
	@Override
	public void setDefaultValue() {
		this.setSelect(this.defaultSelect);
	}

	@Override
	public void parseValue(Object val) {
		if (val instanceof String) {
			for (int i = 0; i < values.length; i ++) if (((Value)values[i]).getValue().equals(val)) {
				this.setSelect(i);
				return;
			}
		} else if (val instanceof Integer) {
			parseValue("" + val);
		} else {
			throw new RuntimeException("parse unknown type");
		}
	}

	@Override
	public void setSelect(Object ret) {
		this.selected = (Integer) ret;
		this.value = ((Value)this.values[selected]).getValue();
		this.showValueStr = ((Value)this.values[selected]).getShowValue();
	}

	@Override
	public String[] getShowValues() {
		String ret[] = new String[this.values.length];
		for (int i = 0; i < ret.length; i ++) ret[i] = ((Value)this.values[i]).getShowValue();
		return ret;
	}
}
