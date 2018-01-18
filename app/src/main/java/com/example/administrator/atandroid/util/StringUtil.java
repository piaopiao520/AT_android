package com.example.administrator.atandroid.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 私有化构造器防止生成对象
	 */
	private StringUtil() {

	}

	/**
	 * 字符串替换 例： String strVal="This is a dog";String
	 * strResult=StringUtil.replace(strVal,"dog","cat"); 结果： strResult equals
	 * "This is cat"
	 *
	 * @param strSrc
	 *            要进行替换操作的字符串
	 * @param strOld
	 *            要查找的字符串
	 * @param strNew
	 *            要替换的字符串
	 * @return 替换后的字符串
	 */
	public static final String replace(String strSrc, String strOld, String strNew) {
		if (strSrc == null || strOld == null || strNew == null)
			return "";
		int i = 0;
		if (strOld.equals(strNew)) // 避免新旧字符一样产生死循环
			return strSrc;
		if ((i = strSrc.indexOf(strOld, i)) >= 0) {
			char[] arr_cSrc = strSrc.toCharArray();
			char[] arr_cNew = strNew.toCharArray();
			int intOldLen = strOld.length();
			StringBuffer buf = new StringBuffer(arr_cSrc.length);
			buf.append(arr_cSrc, 0, i).append(arr_cNew);
			i += intOldLen;
			int j = i;
			while ((i = strSrc.indexOf(strOld, i)) > 0) {
				buf.append(arr_cSrc, j, i - j).append(arr_cNew);
				i += intOldLen;
				j = i;
			}
			buf.append(arr_cSrc, j, arr_cSrc.length - j);
			return buf.toString();
		}
		return strSrc;
	}

	/**
	 * String型变量转换成int型变量
	 * 
	 * @param intStr
	 *            要进行转换的字符串
	 * @return intVal 如果intStr不可以转换成int型数据转换失败抛出异常
	 */
	public static int strToInt(String intStr) throws Exception {
		int intVal;
		try {
			intVal = Integer.parseInt(intStr);
		} catch (Exception e) {
			throw new Exception("转换失败");
		}
		return intVal;
	}

	/**
	 * String型变量转换成double型变量
	 * 
	 * @param doubleStr
	 *            要进行转换的字符串
	 * @return intVal 如果doubleStr不可以转换成double型数据，转换失败抛出异常
	 */
	public static double strToDouble(String doubleStr) throws Exception {
		double dVal = 0;

		try {
			dVal = Double.parseDouble(doubleStr);
			return dVal;
		} catch (Exception e) {
			throw new Exception("转换失败");
		}
	}

	/**
	 * String型变量转换成long型变量
	 * 
	 * @param longStr
	 *            要进行转换的字符串
	 * @return intVal 如果longStr不可以转换成long型数据 转换失败抛出异常
	 */
	public static long strToLong(String longStr) throws Exception {
		long longVal = 0;

		try {
			longVal = Long.parseLong(longStr);
			return longVal;
		} catch (Exception e) {
			throw new Exception("转换失败");
		}

	}

	/**
	 * String型变量转换成float型变量
	 * 
	 * @param floatStr
	 *            要进行转换的字符串
	 * @return intVal 如果floatStr不可以转换成float型数据转换失败抛出异常
	 */
	public static float stringToFloat(String floatStr) throws Exception {
		Float value;
		try {
			value = Float.parseFloat(floatStr);
			return value;
		} catch (Exception e) {
			throw new Exception("转换失败");
		}
	}

	/**
	 * 判断是不是合法字符串
	 */
	public static boolean isLetter(String str) {
		if (str == null || str.equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile("[\\w\\.-_]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 从指定的字符串str中提取Email
	 * 
	 * @param str
	 *            指定的字符串
	 * @return
	 */
	public static String getEmail(String str) {
		String email = null;
		if (str == null || str.length() < 1) {
			return email;
		}
		// 找出含有@
		int beginPos;
		int i;
		String token = "@";
		String preHalf = "";
		String sufHalf = "";

		beginPos = str.indexOf(token);
		if (beginPos > -1) {
			// 前项扫描
			String s = null;
			i = beginPos;
			while (i > 0) {
				s = str.substring(i - 1, i);
				if (isLetter(s))
					preHalf = s + preHalf;
				else
					break;
				i--;
			}
			// 后项扫描
			i = beginPos + 1;
			while (i < str.length()) {
				s = str.substring(i, i + 1);
				if (isLetter(s))
					sufHalf = sufHalf + s;
				else
					break;
				i++;
			}
			// 判断合法性
			email = preHalf + "@" + sufHalf;
			if (isEmail(email)) {
				return email;
			}
		}
		return null;
	}

	/**
	 * 功能描述：判断输入的字符串是否符合Email样式.
	 * 
	 * @param str
	 *            传入的字符串
	 * @return 是Email样式返回true,否则返回false
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.length() < 1 || email.length() > 256) {
			return false;
		}
		Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
		return pattern.matcher(email).matches();
	}

	/**
	 * 判断传入的字符串是否为空白,包括null和""
	 * 
	 * @param str
	 * @return 返回boolean类型结果
	 */
	public static boolean isBlank(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 功能描述：人民币转成大写
	 * 
	 * @param str
	 *            数字字符串
	 * @return String 人民币转换成大写后的字符串
	 */
	public static String strToRMB(String str) throws Exception {
		double value;
		try {
			value = StringUtil.strToDouble(str);
		} catch (Exception e) {
			throw new Exception("转换失败");
		}
		if (value < 0) {
			throw new Exception("转换失败，转换值小于零");
		}
		char[] hunit = { '拾', '佰', '仟' }; // 段内位置表示
		char[] vunit = { '万', '亿' }; // 段名表示
		char[] digit = { '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖' }; // 数字表示
		long midVal = (long) (value * 100); // 转化成整形
		String valStr = String.valueOf(midVal); // 转化成字符串

		String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
		String rail = valStr.substring(valStr.length() - 2); // 取小数部分

		String prefix = ""; // 整数部分转化的结果
		String suffix = ""; // 小数部分转化的结果
		// 处理小数点后面的数
		if (rail.equals("00")) { // 如果小数部分为0
			suffix = "整";
		} else {
			suffix = digit[rail.charAt(0) - '0'] + "角" + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
		}
		// 处理小数点前面的数
		char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
		char zero = '0'; // 标志'0'表示出现过0
		byte zeroSerNum = 0; // 连续出现0的次数
		for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
			int idx = (chDig.length - i - 1) % 4; // 取段内位置
			int vidx = (chDig.length - i - 1) / 4; // 取段位置
			if (chDig[i] == '0') { // 如果当前字符是0
				zeroSerNum++; // 连续0次数递增
				if (zero == '0') { // 标志
					zero = digit[0];
				} else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
					prefix += vunit[vidx - 1];
					zero = '0';
				}
				continue;
			}
			zeroSerNum = 0; // 连续0次数清零
			if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
				prefix += zero;
				zero = '0';
			}
			prefix += digit[chDig[i] - '0']; // 转化该数字表示
			if (idx > 0)
				prefix += hunit[idx - 1];
			if (idx == 0 && vidx > 0) {
				prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
			}
		}
		if (prefix.length() > 0)
			prefix += '圆'; // 如果整数部分存在,则有圆的字样
		return prefix + suffix; // 返回正确表示
	}

	/**
	 * 功能描述：判断是不是合法的手机号码
	 * 
	 * @param handset
	 * @return boolean
	 */
	public static boolean isPhoneNum(String handset) {
		try {
			String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(handset);
			return matcher.matches();

		} catch (RuntimeException e) {
			return false;
		}
	}

	/**
	 * 使用encode转码
	 * 
	 * @param str
	 * @return
	 */
	public static String strEncode(String str) throws Exception {
		try {
			return URLEncoder.encode(str, "utf-8");
		} catch (Exception e) {
			throw new Exception("转换失败");
		}
	}

	/**
	 * 使用decode解码
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String strDecode(String str) throws Exception {
		try {
			return URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new Exception("转换失败");
		}
	}

}
