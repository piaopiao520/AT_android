package com.example.administrator.atandroid.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static String datePattern = "yyyy-MM-dd";// 日期格式 年-月-日
	private static String timePattern = "HH:MM:ss";// 时间格式 时:分:秒

	/**
	 * 防止用户生成对象
	 */
	private DateUtil() {

	}

	/**
	 * @return 返回时间格式datePattern + timePattern
	 */
	public static String getPattern() {
		return datePattern + "  " + timePattern;
	}

	/**
	 * 根据默认的时间格式获取格式化时间(yyyy-MM-dd HH:MM:a)
	 * 
	 * @param date
	 *            Date类型对象
	 * @return String类型的格式化时间
	 */
	public static final String getFormatDate(Date date) {
		SimpleDateFormat df = null;
		String returnValue = "";

		if (date != null) {
			df = new SimpleDateFormat(DateUtil.getPattern());
			returnValue = df.format(date);
		}

		return (returnValue);
	}

	/**
	 * 根据输入的格式来获取格式化时间，如果格式为空则以默认格式进行格式化 年:y 月:M 日:d 时:H 分:M 秒:s
	 * 
	 * @param pattern
	 *            格式化时间的格式(yyyy-MM-dd HH:MM:a)
	 * @param date
	 *            Date类型对象
	 * @return String类型的格式化时间
	 */
	public static final String getFormatDate(String pattern, Date date) {

		if (pattern == null) {
			return getFormatDate(date);
		}
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 根据输入的时间格式和String类型的时间获取Date对象,如果输入的时间格式有误或为空则返回null
	 *
	 * @param pattern
	 *            时间格式
	 * @param strDate
	 *            String类型的时间
	 * @return Date类型对象
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date StringToDate(String pattern, String strDate) throws ParseException {
		SimpleDateFormat df = null;
		Date date = null;
		if (pattern == null) {
			return null;
		}
		df = new SimpleDateFormat(pattern);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			return null;
		}

		return date;
	}

	/**
	 * 根据时间格式将String类型时间解析成毫秒时间
	 * 
	 * @param pattern
	 *            时间格式
	 * @param strDate
	 *            String类型的时间
	 * @return long类型毫秒时间
	 */
	public static long getTimeStamp(String pattern, String strDate) {
		long returnTimeStamp = 0;
		Date aDate = null;
		try {
			aDate = StringToDate(pattern, strDate);
		} catch (ParseException pe) {
			aDate = null;
		}
		if (aDate == null) {
			returnTimeStamp = 0;
		} else {
			returnTimeStamp = aDate.getTime();
		}
		return returnTimeStamp;
	}

	/**
	 * 通过strWeek返回所在的星期数 Mon:1 Tue:2 Wed:3 Thu:4 Fri:5 Sat:6 Sun:0 否则返回-1
	 * 
	 * @param strWeek
	 *            字符串类型星期数
	 * @return 返回整型星期数
	 */
	public static final int getWeekNum(String strWeek) {
		int returnValue = 0;
		switch (strWeek) {
		case "Mon":
			returnValue = 1;
			break;
		case "Tue":
			returnValue = 2;
			break;
		case "Wed":
			returnValue = 3;
			break;
		case "Thu":
			returnValue = 4;
			break;
		case "Fri":
			returnValue = 5;
			break;
		case "Sat":
			returnValue = 6;
			break;
		case "Sun":
			returnValue = 0;
			break;
		default:
			returnValue = -1;
		}
		return returnValue;
	}

	/**
	 * 根据当前时间获取时间戳
	 * 
	 * @return
	 */
	public static long getTimesTamp() {
		return System.currentTimeMillis();
	}
}