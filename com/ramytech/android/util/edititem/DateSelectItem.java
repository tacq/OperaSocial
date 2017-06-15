package com.ramytech.android.util.edititem;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import com.ramytech.android.util.DateUtil;

public class DateSelectItem extends EditItem {
	private static final String TOTODAY = "至今";
	public DateFormat valueFormatter, showFormatter;
	private Date date;
	
	private boolean isToTodayEnabled = false;
	
	public static DateSelectItem newInstance(String name, String key, DateFormat valueFormatter, DateFormat showFormatter) {
		DateSelectItem f = new DateSelectItem();
		f.type = ItemType.DatePick;
		f.name = name; f.key = key;
		f.valueFormatter = valueFormatter;
		f.showFormatter = showFormatter;
		return f;
	}
	
	public DateSelectItem setToTodayEnabled(boolean v) {
		this.isToTodayEnabled = v;
		return this;
	}
	
	public static DateSelectItem newInstance(String name, String key, DateFormat valueFormatter, DateFormat showFormatter, Date defaultDate) {
		DateSelectItem f = DateSelectItem.newInstance(name, key, valueFormatter, showFormatter);
		f.setSelect(defaultDate);
		return f;
	}

	@Override
	public void parseValue(Object val) {
		String tvalue = (String) val;
		if (this.isToTodayEnabled && TOTODAY.equals(tvalue)) {
			this.setSelect(new Date());
		} else 
		try {
			date = valueFormatter.parse(tvalue);
			this.setSelect(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void setSelect(Object ret) {
		date = (Date) ret;
		if (this.isToTodayEnabled && DateUtil.isSameDay(date, new Date())) {
			value = TOTODAY;
			showValueStr = TOTODAY;
		} else {
			value = valueFormatter.format(date);
			this.showValueStr = showFormatter.format(date);
		}
	}
}
