package com.ramytech.android.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	public static final int TimeSlice = 2;
	public static class MSlice {
		public Object obj;//free time item
		private static final SimpleDateFormat HourFormatter = new SimpleDateFormat("HH:mm");
		public int count;
		public MSlice(Date date) {
			parseDate(date);			
		}
		
		public MSlice(int count) {
			this.count = count;
		}
		
		public void parseDate(Date date) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			count = c.get(Calendar.HOUR_OF_DAY) * TimeSlice + c.get(Calendar.MINUTE) / (60 / TimeSlice);
		}	
		
		public String getShowName() {			
			return HourFormatter.format(getDate().getTime());
		}
		
		public Calendar getDate() {
			return this.getDateByDay(Calendar.getInstance());
		}
		
		public Calendar getDateByDay(Calendar date) {
			Calendar c = (Calendar) date.clone();
			c.set(Calendar.HOUR_OF_DAY, count / TimeSlice);
			c.set(Calendar.MINUTE, (count % TimeSlice) * 60 / TimeSlice);
			return c;
		}
		
		@Override
		public String toString() {
			return getShowName();
		}		
	}
	
	public static boolean isSameDay(Date date1, Date date2) {
		if (date1 == null || date2 == null) return false;
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		
		return sameDay;
	}
	
	public static Calendar firstDayOfMonth(Calendar today) {
		Calendar d = (Calendar) today.clone();
		d.set(Calendar.DAY_OF_MONTH, 1);
		return d;
	}
	
	public static Calendar previousMonth(Calendar today) {
		Calendar d = (Calendar) today.clone();
		d.add(Calendar.MONTH, -1);
		return d;
	}
	
	public static Calendar nextMonth(Calendar today) {
		Calendar d = (Calendar) today.clone();
		d.add(Calendar.MONTH, 1);
		return d;
	}
	
	public static Date parseStandard(String d) {
		try {
			return DateUtil.StandardFormatter.parse(d.substring(0, 19));
		} catch (Exception e) {
			return new Date();
		}
	}
	
	public static SimpleDateFormat YearFormatter = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat MonthFormatter = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat StandardFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	public static SimpleDateFormat DateFormatter = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat DateShowFormatter = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat MinuteFormatter = new SimpleDateFormat("yyyyMMddHHmm");
	public static SimpleDateFormat MinuteShowFormatter = new SimpleDateFormat("MM'月'dd'日' HH:mm");
	public static SimpleDateFormat MDayShowFormatter = new SimpleDateFormat("MM'月'dd'日'");
	public static SimpleDateFormat HMFormatter = new SimpleDateFormat("HH:mm");
}
