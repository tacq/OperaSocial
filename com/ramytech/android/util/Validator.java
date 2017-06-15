package com.ramytech.android.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	public boolean isPhone(String phone) {

		Pattern p = Pattern
				.compile("^(1)\\d{10}$");
		Matcher m = p.matcher(phone);

		return m.matches();
	}

	public boolean isEmail(String email) {

		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public boolean isPassword(String password) {

		String str = ".*";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(password);

		if (password.length() >= 6 && password.length() <= 18 && m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isEmpty(String str) {
		return str == null || (str != null && str.equals(""));
	}
	
	public static boolean isEmptyStr(String str) {
		return str == null || (str != null && str.equals(""));
	}
}
