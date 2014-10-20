package com.zc.addmony.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {
	
	/**
	 * 手机号段
	 */
	final static String[] PHONENUMBER_PREFIX = { "130", "131", "132", "133",
			"134", "135", "136", "137", "138", "139", "147", "150", "151",
			"152", "153", "155", "156", "157", "158", "159", "180", "181",
			"182", "183", "185", "186", "187", "188", "189" };

	/**
	 * 判断手机号是否可用
	 * 
	 * @param number
	 * @return
	 */
	public static boolean patternPhoneNumber(String number) {
		int len = PHONENUMBER_PREFIX.length;
		if (number != null) {
			for (int i = 0; i < len; i++) {
				Pattern p = Pattern.compile(PHONENUMBER_PREFIX[i] + "\\d{8}");
				if (p.matcher(number).matches()) {
					// if (number.startsWith("1349")) {// 134��ͷ�Ĳ�����1349
					// return false;
					// }
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 判断邮箱是否可用
	 * 
	 * @param emailStr
	 * @return
	 */
	public static boolean patternEmail(String emailStr) {
		if (emailStr.contains(" ") || emailStr.contains("__")
				|| emailStr.contains("_.") || emailStr.contains("._")
				|| emailStr.contains("..")) {
			return false;
		}
		// String
		// check="^([a-z0-9A-Z]+[-|_\\.]?)+[a-z0-9A-Z]@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
		String check = "^[a-z0-9A-Z]{1}[\\w\\.]{2,14}[a-z0-9A-Z]{1}@[a-z0-9A-Z]{1}[a-z0-9A-Z\\.]{1,28}[a-zA-Z]{1}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(emailStr);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			String[] temp = emailStr.split("@")[1].split("\\.");
			if (temp.length < 2 || temp.length > 5) {// @��������1�������4��"."
				return false;
			}
			return true;
		}
		return false;
	}

}
