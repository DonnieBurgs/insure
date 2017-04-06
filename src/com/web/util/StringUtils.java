package com.web.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串的工具类
 */
public class StringUtils {

	// 常用字符
	public final static String FOLDER_SEPARATOR = "/";

	public final static String WINDOWS_FOLDER_SEPARATOR = "\\";

	public final static String TOP_PATH = "..";

	public final static String CURRENT_PATH = ".";

	private final static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	private StringUtils() {
		// 私有实例防止生成实例
	}

	/**
	 * 以指定字符串编码转换字符串
	 * 
	 * @param objectString
	 * @param encoding
	 * @return String
	 */
	public static String convertCharSet(String objectString, String encoding) {
		if (objectString == null)
			return objectString;

		try {
			return new String(objectString.getBytes("iso-8859-1"), encoding);
		} catch (UnsupportedEncodingException e) {
			return objectString;
		}

	}

	/**
	 * 以指定字符串编码转换字符串
	 * 
	 * @param objectString
	 * @param oldEncoding
	 * @param newEnvoding
	 * @return String
	 */
	public static String convertCharSet(String objectString, String oldEncoding, String newEnvoding) {
		if (objectString == null)
			return objectString;

		try {
			String tempStr = new String(objectString.getBytes(newEnvoding), oldEncoding);
			return new String(tempStr.getBytes(oldEncoding), newEnvoding);
		} catch (UnsupportedEncodingException e) {
			return objectString;
		}

	}

	/**
	 * 判断是否有长度
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean hasLength(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 消除前空白
	 * 
	 * @param str
	 * @return String
	 */
	public static String trimLeadingWhitespace(String str) {
		if (str.length() == 0) {
			return str;
		}
		StringBuffer buf = new StringBuffer(str);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	/**
	 * 返回对象的toString()
	 * 
	 * @param obj
	 * @return String
	 */
	public static String toString(Object obj) {
		if (obj != null) {
			return obj.toString().trim();
		}
		return new String();
	}

	/**
	 * 返回字符串出现的次数
	 * 
	 * @param str
	 * @param sub
	 * @return int
	 */
	public static int countCurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * 替换字符串
	 * 
	 * @param inStr
	 * @param oldStr
	 * @param newStr
	 * @return String
	 */
	public static String replaceStr(String inStr, String oldStr, String newStr) {
		if (inStr == null) {
			return null;
		}
		if (oldStr == null || newStr == null) {
			return inStr;
		}
		StringBuffer buffer = new StringBuffer();
		int pos = 0;
		int index = inStr.indexOf(oldStr);
		int patLen = oldStr.length();
		while (index >= 0) {
			buffer.append(inStr.substring(pos, index));
			buffer.append(newStr);
			pos = index + patLen;
			index = inStr.indexOf(oldStr, pos);
		}
		buffer.append(inStr.substring(pos));

		return buffer.toString();
	}

	public static String deleteAny(String inStr, String charsToDelete) {
		if (inStr == null || charsToDelete == null) {
			return inStr;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			char c = inStr.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}

	/**
	 * 首字转换，true:转为大写；false:转为小写
	 * 
	 * @param str
	 * @param capitalize
	 * @return String
	 */

	public static String changFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(str.length());
		if (capitalize) {
			buffer.append(Character.toUpperCase(str.charAt(0)));
		} else {
			buffer.append(Character.toLowerCase(str.charAt(0)));
		}
		buffer.append(str.substring(1));
		return buffer.toString();
	}

	/**
	 * 字符串切割
	 * 
	 * @param str
	 * @param delimiters
	 * @return String[]
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * 字符串切割 trimTokens:截取前后空格；ignoreEmptyTokens:是否包换空字符串
	 * 
	 * @param str
	 * @param delimiters
	 * @param trimTokens
	 * @param ignoreEmptyTokens
	 * @return String[]
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {
		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	private static String cutStringArrayHelper(String sourceStr, int size, List<String> tempList) {
		String result = "";
		if (sourceStr.length() > size) {
			result = sourceStr.substring(0, size);
			cutStringArrayHelper(sourceStr.substring(size), size, tempList);
		} else {
			result = sourceStr;
		}
		if (result == null) {
			result = " ";
		}
		tempList.add(result);
		return result;
	}

	/**
	 * 指定长度分割
	 * 
	 * @param sourceStr
	 * @param size
	 * @return String[]
	 */
	public static String[] cutStringArray(String sourceStr, int size) {
		List<String> tempList = new ArrayList<String>();
		cutStringArrayHelper(sourceStr, size, tempList);
		String[] result = new String[tempList.size()];
		int count = tempList.size();
		for (int i = 0; i < result.length; i++) {
			String str = tempList.get(i);
			result[count - i - 1] = str;
		}
		return result;
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		int t;
		for (int i = 0; i < 16; i++) {// 16 == bytes.length;

			t = bytes[i];
			if (t < 0)
				t += 256;
			sb.append(hexDigits[(t >>> 4)]);
			sb.append(hexDigits[(t % 16)]);
		}
		return sb.toString();
	}

	/**
	 * 生成MD5
	 * 
	 * @param input
	 * @return String
	 * @throws Exception
	 */
	public static String code(String input) throws Exception {
		try {
			MessageDigest md = MessageDigest.getInstance(System.getProperty("MD5.algorithm", "MD5"));
			return bytesToHex(md.digest(input.getBytes("utf-8")));

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("Could not found MD5 algorithm.", e);
		}
	}

	/**
	 * 以GBK编码方式截取字符串长度
	 * 
	 * @param str
	 * @param end
	 * @param symbol
	 * @return String
	 * @throws Exception
	 */
	public static String limitLengthByGBK(String str, Integer end, String symbol) {
		if (str == null || str.equals("")) {
			return str;
		}
		if (str.length() < end) {
			return str;
		}
		int counterOfDoubleByte = 0;
		try {
			byte[] b = str.getBytes("GBK");
			if (b.length <= end)
				return str;
			for (int i = 0; i < end; i++) {
				if (b[i] < 0) {
					counterOfDoubleByte++;
				}
			}

			if (counterOfDoubleByte % 2 == 0) {
				return new String(b, 0, end, "GBK") + symbol;
			} else {
				return new String(b, 0, end - 1, "GBK") + symbol;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String limitLengthByChar(String str, int length, String symbol) throws UnsupportedEncodingException {
		// 如果字符串的位数小于等于要截取的位数，附加上表示省略的信息的字符串后返回
		if (str.length() <= length) {
			return str + symbol;
			// 从零开始，截取length个字符，附加上表示省略的信息的字符串后返回
		} else {
			str = new String(str.getBytes("GBK"));
			char[] charArray = str.toCharArray();
			char[] charArrayDesc = new char[length];
			System.arraycopy(charArray, 0, charArrayDesc, 0, length);
			return new String(charArrayDesc) + symbol;
		}
	}

	/**
	 * 正则带有Html标签
	 * 
	 * @param inputString
	 * @return String
	 */
	// str.replaceAll("</?[^>]+>", "")
	public static String Html2Text(String inputString) {
		if (inputString == null || inputString.equals(""))
			return inputString;

		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		Pattern p;
		Matcher m;
		String regEx_script = "</?[^>]+>";
		// [,Pattern.CASE_INSENSITIVE]参数忽略大小写
		p = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		if (p == null)
			return inputString;

		m = p.matcher(htmlStr);
		htmlStr = m.replaceAll("");
		textStr = htmlStr;
		return textStr;
	}

	public static String cutString(String target, int cutSize, String append) {
		if (target == null) {
			return null;
		}
		char[] arr = target.toCharArray();
		String engStr = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+:\"<>? I";// QWERTYUOPASDFGHJKLZXCVBNM大写英文除I外当成中文看待
		BigDecimal index = getLength(target);
		String returnStr = "";
		if (index.intValue() > cutSize) {
			index = new BigDecimal(0);
			for (int i = 0; i < arr.length; i++) {
				if (engStr.indexOf(arr[i]) == -1) {
					index = index.add(new BigDecimal(1));
				} else {
					index = index.add(new BigDecimal(2));
				}
				if (index.intValue() <= cutSize) {
					returnStr = returnStr + arr[i];
				} else {
					break;
				}

			}
			returnStr = returnStr + append;
			return returnStr;
		} else {
			return target;
		}
	}

	public static BigDecimal getLength(String str) {
		if (str == null || str.equals("")) {
			return new BigDecimal(0);
		}
		char[] arr = str.toCharArray();
		String engStr = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+:\"<>? I";// QWERTYUOPASDFGHJKLZXCVBNM大写英文除I外当成中文看待
		BigDecimal index = new BigDecimal(0);
		for (int i = 0; i < arr.length; i++) {
			if (engStr.indexOf(arr[i]) == -1) {
				index = index.add(new BigDecimal(1));
			} else {
				index = index.add(new BigDecimal(0.5));
			}
		}
		return index;
	}

	public static String getLimitLengthString(String str, int length, String symbol) throws UnsupportedEncodingException {
		return subContentStringOrialBytes(str, length, symbol);
	}

	public static int ascii(String c) {
		byte x[] = new byte[2];
		x = c.getBytes();

		if (null == x || x.length > 2 || x.length <= 0) {
			return 0;
		}

		if (x.length == 1) { // 英文字符
			return x[0];
		}

		if (x.length == 2) {
			int hightByte = 256 + x[0];
			int lowByte = 256 + x[1];
			int ascii = (256 * hightByte + lowByte) - 256 * 256;
			return ascii;
		}

		return 0;
	}

	/**
	 * 截取字符串的前targetCount个字符
	 * 
	 * @param str
	 *            被处理字符串
	 * @param targetCount
	 *            截取长度
	 * @return String
	 */
	public static String subContentStringOrialBytes(String str, int targetCount) {
		return subContentStringOrialBytes(str, targetCount, "...");
	}

	/**
	 * 获取指定长度字符串的字节长
	 * 
	 * @param str
	 *            被处理字符串
	 * @param maxlength
	 *            截取长度
	 * @return String
	 */
	private static long getStringByteLength(String str, int maxlength) {
		if (str == null)
			return 0;
		int tmp_len = maxlength;

		if (str.length() < maxlength)
			tmp_len = str.length();
		else if (str.length() > maxlength * 2)
			tmp_len = maxlength * 2;

		char[] tempchar = str.substring(0, tmp_len).toCharArray();

		int intVariable = 0;
		String s1 = null;
		for (int i = 0; i < tempchar.length && intVariable <= maxlength; i++) {
			s1 = String.valueOf(tempchar[i]);
			intVariable += s1.getBytes().length;
		}
		s1 = null;
		tempchar = null;
		return intVariable;
	}

	/**
	 * 截取指定长度的字符串,基于bytes,即是中文的长度为2,英文为1
	 * 
	 * @param str
	 *            被处理字符串
	 * @param targetCount
	 *            截取长度
	 * @param more
	 *            后缀字符串
	 * @return
	 */
	public static String subContentStringOrialBytes(String str, int targetCount, String more) {
		if (str == null)
			return "";
		int initVariable = 0;
		StringBuffer restr = new StringBuffer();
		if (getStringByteLength(str, targetCount) <= targetCount)
			return str;

		String s1 = null;
		byte[] b;
		char[] tempchar = str.toCharArray();
		for (int i = 0; (i < tempchar.length && targetCount > initVariable); i++) {
			s1 = String.valueOf(tempchar[i]);
			b = s1.getBytes();
			initVariable += b.length;
			restr.append(tempchar[i]);
		}

		if (targetCount == initVariable || (targetCount == initVariable - 1)) {
			restr.append(more);
		}
		return restr.toString();
	}

	/**
	 * 截取指定长度的字符串,存在问题,但效率会高一点点.just a little
	 * 
	 * @param str
	 *            被处理字符串
	 * @param targetCount
	 *            截取长度
	 * @param more
	 *            后缀字符串
	 * @return String
	 */
	public static String subContentStringOrial(String str, int targetCount) {
		return subContentStringOrial(str, targetCount, "...");
	}

	/**
	 * 截取指定长度的字符串,存在问题,但效率会高一点点.just a little
	 * 
	 * @param str
	 *            被处理字符串
	 * @param targetCount
	 *            截取长度
	 * @param more
	 *            后缀字符串
	 * @return String
	 */
	public static String subContentStringOrial(String str, int targetCount, String more) {
		if (str == null)
			return "";
		int initVariable = 0;
		StringBuffer restr = new StringBuffer();
		if (str.length() <= targetCount)
			return str;

		String s1 = null;
		byte[] b;
		char[] tempchar = str.toCharArray();
		for (int i = 0; (i < tempchar.length && targetCount > initVariable); i++) {
			s1 = String.valueOf(tempchar[i]);
			b = s1.getBytes();
			initVariable += b.length;
			restr.append(tempchar[i]);
		}

		if (targetCount == initVariable || (targetCount == initVariable - 1)) {
			restr.append(more);
		}
		return restr.toString();
	}

	public static String bSubstring(String s, int length) throws Exception {

		byte[] bytes = s.getBytes("Unicode");

		int n = 0; // 表示当前的字节数

		int i = 2; // 要截取的字节数，从第3个字节开始

		for (; i < bytes.length && n < length; i++) {

			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节

			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1) {
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}
		return new String(bytes, 0, i, "Unicode");
	}

	public static String join(Collection<? extends Object> collection, String separator) {
		Iterator<? extends Object> iter;
		if (collection == null || (!(iter = collection.iterator()).hasNext())) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder(String.valueOf(iter.next()));
		while (iter.hasNext()) {
			stringBuilder.append(separator).append(iter.next());
		}
		return stringBuilder.toString();
	}

	public static String replace(String str, String replacement) {
		if (str == null || str.equals("")) {
			return str;
		}
		return str.replaceAll("\r\n", replacement);
	}

}