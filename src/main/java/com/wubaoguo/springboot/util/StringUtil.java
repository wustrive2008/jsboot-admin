package com.wubaoguo.springboot.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	
	/**
	 * 邮箱验证
	 */
	public static final String EMAILREGULAR = "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+)*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))$";
	/**
	 * 真实名称,只包含中文、英文
	 */
	public static final String TRUENAME = "(^([a-zA-Z ]+|[\u4e00-\u9fa5]+)$)";
	/**
	 * 特殊字符
	 */
	public static final String SPECIALCHAR = "^([^`~!@$^&':;,?~！……&；：。，、？=]).*";
	/**
	 * 手机验证
	 */
	public static final String PHONEREGULAR = "^(0|86|17951)?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";
	/**
	 * 固话
	 */
	public static final String TELREGULAR = "(^[0-9]{3,4}\\-[0-9]{7,8}$)|(^[0-9]{7,8}$)|(^[0-9]{3,4}\\-[0-9]{7,8}\\-[0-9]{3,5}$)|(^[0-9]{7,8}\\-[0-9]{3,5}$)|(^\\([0-9]{3,4}\\)[0-9]{7,8}$)|(^\\([0-9]{3,4}\\)[0-9]{7,8}\\-[0-9]{3,5}$)|(^1[3,4,5,7,8]{1}[0-9]{9}$)";

	public static boolean isEmail(String email){
        Pattern pattern = Pattern.compile(EMAILREGULAR);//复杂匹配   
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            return true;
        }
        return false;
    }
	
	public static String asc2gbk(String param){
		String ret = "";
		if (StringUtils.isNotBlank(param)) {
			try {
				ret = new String(param.getBytes("ISO-8859-1"), "GBK").trim();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				ret ="";
			}
		}
		return ret;
	}
	
	public static String asc2utf8(String param){
		String ret = "";
		if (StringUtils.isNotBlank(param)) {
			try {
				ret = new String(param.getBytes("ISO-8859-1"),"UTF-8").trim();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				ret ="";
			}
		}
		return ret;
	}
	
	
	
	/**
	 * 阿拉伯数字转 汉字
	 * 
	 * @Title translateNumToChinese
	 * @param a
	 * @return
	 * @author zhaoqt
	 * @date Jul 27, 2015 9:35:43 AM
	 * @return String
	 */
	public static String translateNumToChinese(int a) {
		String[] units = { "", "十", "百", "千", "万", "十", "百", "千", "亿" };
		String[] nums = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		String result = "";
		if (a < 0) {
			result = "负";
			a = Math.abs(a);
		}
		String t = String.valueOf(a);
		for (int i = t.length() - 1; i >= 0; i--) {
			int r = (int) (a / Math.pow(10, i));
			if (r % 10 != 0) {
				String s = String.valueOf(r);
				String l = s.substring(s.length() - 1, s.length());
				result += nums[Integer.parseInt(l) - 1];
				result += (units[i]);
			} else {
				if (!result.endsWith("零")) {
					result += "零";
				}
			}
		}
		return result;
	}
	
	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}

	public static String dhhmreplaceByXX(String photo) {
		if (org.apache.commons.lang.StringUtils.isBlank(photo)) {
			return null;
		}
		char[] strs = photo.toCharArray();
		for (int i = 3; i < 8; i++) {
			strs[i] = '*';
		}
		return new String(strs);
	}
	
	public static String removeNewline(String str) {
		String result = null;
		if (str != null) {
			result = str.replaceAll("\\r\\n", "");
			result = result.replaceAll(new String(new char[] { 10 }), "");
		}
		return result;
	}
	
	/**
	 * 判断是否为数字
	 */
	public static boolean isNumber(String str) {
		if (str.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 功能描述：浮点型判断
	 * 
	 */
	public static boolean isFloat(String str) {
		if (str == null || "".equals(str))
			return false;
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 功能描述：整型判断
	 * 
	 */
	public static boolean isInteger(String str) {
		if (StringUtil.isBlank(str))
			return false;
		return str.matches("^-?\\d+");
	}
	
	/**
	 *  功能描述：接受一个List<String>，如果有任何一个为空，则返回true，否则返回false
	 *  
	 */
	public static boolean isBlank(String... list){
		for (String string : list) {
			if(StringUtils.isBlank(string)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 功能描述：获取子字符串的个数
	 * 
	 */
	public static int getSubCount(String sub, String string) {
		if (StringUtils.isBlank(string)) {
			return 0;
		}
		int subLength = sub.length();
		int forLength = string.length() - sub.length() + 1; // 循环次数
		int count = 0; // 记录循环次数
		for (int i = 0; i < forLength; i++) {
			if (StringUtils.substring(string, i, i + subLength).equals(sub)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * 如果IsNull，就返回defalut
	 * 
	 */
	public static String defaultIfNull(String str,String defalut){
		if (StringUtils.isBlank(str)) {
			return defalut;
		}
		return str;
	}
	
	/**
	 * 功能描述：都不为空，则返回true
	 * 
	 */
	public static boolean isNotBlank(String... list){
		return !isBlank(list);
	}
	
	/**
	 * trim从前台获取的参数，包括null变成""，'变成''
	 * 
	 */
	public static String trimParam(Object s){
		String str = ConvertUtil.obj2Str(s);
		if(StringUtils.isBlank(str)){
			return "";
		}else{
			return str.replaceAll("'", "''").trim();
		}
	}
	
	/**
	 * 描述：tapestry 比较一个字符串包含另一个字符串
	 * 
	 */
	public static boolean Str1ContainsStr2(Object o1 , Object o2){
		if(ConvertUtil.obj2Str(o1).contains(ConvertUtil.obj2Str(o2))){
			return true;
		}
		return false;
	}
	
	/**
	 * 截取指定长度字符串，一个汉字占两个字节，字符和数字占用一个
	 * 
	 */
	public static String subStr(String value,int length,boolean flag){
		if (StringUtils.isBlank(value) || value.getBytes().length <= length) 
			return value;
		for (int i = 0; i <= value.length(); i++) {
			if (value.substring(0, i).getBytes().length > length) {
				if (i - 1 >= 0) {
					return value.substring(0, i - 1)+(flag ? "...":"");
				}
			}
		}
		return value;
	}
	
	/**
	 * 清除一个时间格式字符串中的"-" ":"和空格，返回一个格式化的String<br>
	 * 例如：将一个 2015-11-13 12:00 转换为 201011131200 <br>
	 * 
	 */
	public static String clearDateStrToLong(String date) throws NumberFormatException{
		String ret = "";
		if(date!=null) {
			ret = date.replaceAll("[-]*[\\s]*[:]*", "");
		}
		return ret;
	}
	
	/**
	 * 小数格式化 
	 * 
	 */
	public static Double getDoubleFormat(Double value,Integer length){
		String str=".";
		for (int i = 0; i < length; i++) {
			str+="#";
		}
		return  Double.valueOf(new DecimalFormat(str).format(value));
	}
	
	/**
	 * gbk
	 * 
	 */
	public static  String decode(String str){
		String string = "";
		if(StringUtil.isNotBlank(str)){
			try {
				string = URLDecoder.decode(str, "gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return  string;
	}
	
	/**
	 * utf-8
	 * 
	 */
	public static  String decode_utf(String str){
		String string = "";
		if(StringUtil.isNotBlank(str)){
			try {
				string = URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return  string;
	}
	
	public static String decode_Iso_utf(String str) throws UnsupportedEncodingException{
		if(StringUtil.isBlank(str)){
			return str;
		}
		return new String(str.getBytes("ISO-8859-1"),"GBK");
	}
	
	/**
	 * 将2010-1-1 转为 20100101.
	 * 
	 */
	public static String datestr2long(String date){
		String[] time = date.split("-");
		StringBuffer dateBuf = new StringBuffer();
		for (int i = 0; i < time.length; i++) {
			if (i==0) {
				dateBuf.append(time[i]);
			}else {
				dateBuf.append(time[i].length()<2?"0"+time[i]:time[i]);
			}
		}
		return dateBuf.toString();
	}
	
	/**
	 * 将一个字符串根据指定的长度分隔成N个字符串的数组.
	 * 
	 */
	public static String[] splitForLength(String str , int length){
		int len = str.length();
		int pageSize = (int) Math.ceil(len/70D);
		String[]  array = new String[pageSize];
		for (int i = 0; i < pageSize; i++) {
			int begin = i*70;
			int end = ( begin+70) >= len ? len : (begin+70 );
			array[i] = str.substring( begin, end);
		}
		return array;
	}
	
	/**
	 * 根据一个字符数组组成一个由指定字符分隔的字符串.
	 * 
	 */
	public static String strArrayToStr(String[] arr,String sign){
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			buf.append(arr[i]);
			if ( i<arr.length-1 ) {
				buf.append(sign);
			}
		}
		return buf.toString();
	}
	
	/**
	 *  将一个字符串中的空格和换行转行成html代码.
	 *  
	 */
	public static String str2Html(String str){
		String retStr = "";
		if(str!=null){
			retStr = str;
			retStr = retStr.replace("&", "&amp;");
			retStr = retStr.replace(" ", "&nbsp;");
			retStr = retStr.replace("<", "&lt;");
			retStr = retStr.replace(">", "&gt;");
			retStr = retStr.replaceAll(" ", "&nbsp;");
			retStr = retStr.replaceAll("\r\n", "<br>");
			retStr = retStr.replaceAll("\r", "<br>");
		}
		return retStr;
	}
	
	/**
	 * 将字符串或路径中的 \ 转为  /.
	 * 
	 */
	public  static String convert(String text){
		StringBuffer buf = new StringBuffer("");
		if ( null==text ) {
			return null;
		}
		char[] chars = text.toCharArray();
		for ( char tmp : chars ) {
			if (tmp=='\\') {
				tmp = '/';
			}
			buf.append(tmp);
		}
	
		return buf.toString();
	}
	
	/**
	 *  获取随机数
	 *  
	 */
	public  static String getRandom(int length) {
		String yzm = StringUtils.right(Math.random()+"", length);
		while (yzm.length()!=length) {
			yzm = StringUtils.right(Math.random()+"", length);
		}
		return yzm;
	}
	
	public  static String trim(String str) {
		 if(StringUtil.isBlank(str)){
			 return str;
		 }
		 return str.trim();
	}
	public static String[] split(String str, String regex) {
		return str.split(regex);
	}
	
	public static String  replace(String str, String regex,String replacement) {
		return str.replaceAll(regex, replacement);
	}
	
	/**
	 * 验证手机号格式
	 * 
	 */
	public static boolean isValidMobileNum(String mobile){
		if(StringUtil.notEmptyNum(mobile)) {
			mobile = mobile.trim();
		}
		Pattern pattern = Pattern.compile(PHONEREGULAR);     
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
	}
	
	/**
	 * 验证是否固话
	 * 
	 */
	public static boolean isValidPhone(String phone){
		if(StringUtil.notEmptyNum(phone)) {
			phone = phone.trim();
		}
		Pattern pattern = Pattern.compile(TELREGULAR);     
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
	}
	
	public static boolean notEmpty(String str) {
		boolean b = false;
		if(null != str && !"".equals(str.trim())) {
			b = true;
		}
		return b;
	}
	
	public static boolean notEmptyNum(String str) {
		boolean b = false;
		if(null != str && !"".equals(str.trim()) && StringUtils.isNumeric(str)) {
			b = true;
		}
		return b;
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f',
				'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt() - 1) % randomChar.length]);
		}
		return stringBuffer.toString();
	}
	
	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}
	
	/**
	 * 根据指定 delim 拆分字符串
	 *   例如 str = 1，2,3  delim = ",，" 返回结果 1 2 3
	 * @param str
	 * @param delim
	 * @return
	 */
	public static String[] stringTokenizer(String str, String delim) {
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] arrays = new String[st.countTokens()];
		int i = 0;
		while(st.hasMoreTokens()){
			arrays[i] = (st.nextToken());
			i ++;
	     }
		return arrays;
	}
	
	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 * 
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator).append(str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}
	
	/**
	 * 过滤 emoji 表情，
	 * 
	 * @param str
	 * @return
	 */
	public static String filterEmojiCharacter(String str) {
		if(str == null) return null;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i<str.length(); i++) {
			char c = str.charAt(i);
			if(isEmojiCharacter(c))
				sb.append(c);
		}
		return sb.toString();
	}
	
	private static boolean isEmojiCharacter(char code) {
		return (code == 0x0) || 
                (code == 0x9) ||
                (code == 0xA) ||
                (code == 0xD) ||
                ((code >= 0x20) && (code <= 0xD7FF)) ||
                ((code >= 0xE000) && (code <= 0xFFFD)) ||
                ((code >= 0x10000) && (code <= 0x10FFFF));
	}
	
}
