package com.wubaoguo.springboot.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {
	
	public static final String yyyyMMdd = "yyyyMMdd";
	
	public static final String yyyy_MM_dd = "yyyy-MM-dd";
	
	public static final String yyyyMMddHH = "yyyyMMddHH";

	public static final String yyyy_MM_dd_HH = "yyyy-MM-dd HH";
	
	public static final String default_Formater = "yyyy-MM-dd HH:mm:ss";
	
	public static boolean isDate(Date date)  {
		return date != null ? true : false;
	}
	
	/**
	 * 获取当前天 格式 yyyyMMdd
	 * 
	 * @return
	 */
	public static String yyyyMMdd() {
		return formaterDate(new Date(), yyyyMMdd);
	}
	
	/**
	 * 指定时间 获取格式 yyyyMMdd
	 * @param date
	 * @return
	 */
	public static String yyyyMMdd(Date date) {
		return isDate(date) ? formaterDate(date, yyyyMMdd) : yyyyMMdd();
	}
	
	/**
	 * 获取当前小时 格式 yyyyMMddHH
	 * 
	 * @return
	 */
	public static String yyyyMMddHH() {
		return formaterDate(new Date(), yyyyMMddHH);
	}
	
	/**
	 * 指定时间 获取当前小时 格式 yyyyMMddHH
	 * 
	 * @return
	 */
	public static String yyyyMMddHH(Date date) {
		return isDate(date) ? formaterDate(date, yyyyMMddHH) : yyyyMMddHH();
	}
	
	/**
	 * 获取当前天 格式yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String yyyy_MM_dd() {
		return formaterDate(new Date(), yyyy_MM_dd);
	}
	
	/**
	 * 指定时间 获取当前天 格式yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String yyyy_MM_dd(Date date) {
		return isDate(date) ? formaterDate(date, yyyy_MM_dd) : yyyy_MM_dd();
	}
	
	/**
	 * 获取当前小时 yyyy-MM-dd HH
	 * 
	 * @return
	 */
	public static String yyyy_MM_dd_HH() {
		return formaterDate(new Date(), yyyy_MM_dd_HH);
	}
	
	/**
	 * 指定时间  获取当前小时 yyyy-MM-dd HH
	 * 
	 * @return
	 */
	public static String yyyy_MM_dd_HH(Date date) {
		return isDate(date) ? formaterDate(new Date(), yyyy_MM_dd_HH) : yyyy_MM_dd_HH();
	}
	
	
	/**
	 * 格式当前时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param formater
	 * @return
	 */
	public static String formaterCurrent(String formater) {
		return formaterDate(new Date(), formater);
	}
	
	/**
	 * 获取默认格式化指定时间
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String formaterDefault(Date date) {
		return formaterDate(date, default_Formater);
	}
	
	/**
	 *  返回当前时间 格式化
	 * 
	 * @return
	 */
	public static String formaterCurrent() {
		return formaterDate(new Date(), default_Formater);
	}
	
	/**
	 * 指定时间根据指定格式转换
	 * 
	 * @param date
	 * @param formater
	 * @return
	 */
	public static String formaterDate(Date date, String formater) {
		if (!isDate(date)) {
			return "";
		}
		if (StringUtil.isBlank(formater)) {
			formater = default_Formater;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formater);
		return sdf.format(date);
	}
	
	public static Date formaterString(String date, String formater) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formater);
			return format.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	/**
	 * 返回日期 date 加上 afterMinute 分钟之后的日期
	 * 
	 * @param date
	 * @param afterHour
	 * @return
	 */
	public static Date getAfterMinuteDate(Date date, int afterMinute) {
		if (isDate(date)) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MINUTE, afterMinute);
			return c.getTime();
		}
		return null;
	}
	
	/**
	 * 返回日期 date 加上 afterMinute 分钟之后的日期
	 * 
	 * @param date
	 * @param afterMinute
	 * @return
	 */
	public static Long getAfterMinuteLong(Long date, int afterMinute) {
		return getAfterMinuteDate(new Date(date), afterMinute).getTime();
	}
	
	
	/**
	 * 返回日期 date 加上 afterHour 小时之后的日期
	 * 
	 * @param date
	 * @param afterMinute
	 * @return
	 */
	public static Long getAfterHourLong(Long date, int afterMinute) {
		return getAfterHourDate(new Date(date), afterMinute).getTime();
	}
	
	/**
	 * 返回日期 date 加上 afterHour 小时之后的日期
	 * 
	 * @param date
	 * @param afterHour
	 * @return
	 */
	public static Date getAfterHourDate(Date date, int afterHour) {
		return getAfterMinuteDate(date, afterHour * 60); 
	}
	
	/**
	 * 返回日期 date 前推  beforHour 小时之后的日期
	 * 
	 * @param date
	 * @param afterMinute
	 * @return
	 */
	public static Long getbeforHourLong(Long date, int beforHour) {
		return getBeforHourDate(new Date(date), beforHour).getTime();
	}
	
	/**
	 * 返回日期date 前推beforHour小时后的 日期
	 * 
	 * @param date
	 * @param beforMinute
	 * @return
	 */
	public static Long getBeforMinuteLong(Long date, int beforMinute) {
		return getBeforMinuteDate(new Date(date), beforMinute).getTime();
	}
	
	/**
	 * 返回日期date 前推beforHour小时后的 日期
	 * 
	 * @param date
	 * @param beforHour
	 * @return
	 */
	public static Date getBeforHourDate(Date date, int beforHour) {
		return getBeforMinuteDate(date, beforHour * 60);
	}
	
	/**
	 * 返回日期date 前推beforMinute 分钟后的日期
	 * 
	 * @param date
	 * @param beforMinute
	 * @return
	 */
	public static Date getBeforMinuteDate(Date date, int beforMinute) {
		if (isDate(date)) {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) - beforMinute);
			return c.getTime();
		}
		return null;
	}
	
	/**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
    public static int daysBetween(Date smdate, Date bdate) {    
    	try {
    		SimpleDateFormat sdf=new SimpleDateFormat(yyyy_MM_dd_HH);  
	        smdate=sdf.parse(sdf.format(smdate));
	        bdate=sdf.parse(sdf.format(bdate));  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(smdate);    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(bdate);    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24);  
    	    return Integer.parseInt(String.valueOf(between_days));     
		} catch (Exception e) {
			return 0;
		}
    }
    
    /**
     * 格式秒数 返回  秒、分钟、时
     * @param seconds
     * @return
     */
    public static String getFormatSeconds(int seconds){
		if (seconds < 60 && seconds >= 0) {
			return seconds+"秒";
		}
		if (seconds > 60 && seconds/60 < 60) {
			return seconds/60 + "分钟"+seconds%60+"秒";
		}
		if (seconds/60 > 60) {
			int h = seconds/60/60;
			return seconds/60/60 + "小时"+(seconds- h*3600)/60+"分"+(seconds- h*3600)%60+"秒";
		}
		return "";
	}
    
	/**
	 * 获取当前日期（中国,yyyy年MM月dd日）
	 */
	public static String cnToday() {
		return formaterDate(new Date(), "yyyy年MM月dd日");
	}

	/**
	 * 获取当前星期（中国, 如：星期日,星期一,星期二）
	 */
	public static String cnWeek() {
		Calendar c = GregorianCalendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		String[] s = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		return s[c.get(Calendar.DAY_OF_WEEK) - 1];
	}

	/**
	 * 比较两个时间，date1 大于 date2 返 1 ， date1 小于 date2 返 -1 ，相等返回 0
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compartToDate(Date date1, Date date2) {
		if (isDate(date1) && isDate(date2)) {
			if (date1.getTime() > date2.getTime()) {
				return 1;
			} else if (date1.getTime() < date2.getTime()) {
				return -1;
			} else {
				return 0;
			}
		}
		return 0;
	}
	
	/**
	 * 取给定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
		return calendar.getTime();
	}
	
	/**
	 *  获取当前年
	 * @param date
	 * @return
	 */
	public static int getYear(Long date){
		return ConvertUtil.obj2Integer(StringUtils.left(date.toString(), 4));
	}
	
	/**
	 * long 转换 Date
	 * 
	 * @param time
	 * @return
	 */
	public static Date long2Date(long time) {
		return long2Calendar(time).getTime();
	}
	
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	public static String getMonth0() {
		Calendar c = Calendar.getInstance();
		int march = c.get(Calendar.MARCH) + 1;
		return march <= 9 ? "0"+march : march+"";
	}
	
	/**
	 * long 时间 转换 Calender
	 * @param time
	 * @return
	 */
	public static Calendar long2Calendar(Long time) {
		Calendar calendar = null;
		if (time == null) {
			calendar = Calendar.getInstance();
		} else {
			String strTime = String.valueOf(time);
			int year = 0;
			int month = 0;
			int day = 0;
			int hour = 0;
			int minute = 0;
			int sec = 0;
			int length = strTime.length();
			if (length < 8) {
				throw new RuntimeException("时间格式不合法，不能少与八位数字！");
			}
			if (length >= 8) {
				year = Integer.valueOf(strTime.substring(0, 4));
				month = Integer.valueOf(strTime.substring(4, 6)) - 1;
				day = Integer.valueOf(strTime.substring(6, 8));
			}
			if (length >= 10) {
				hour = Integer.valueOf(strTime.substring(8, 10));
			}
			if (length >= 12) {
				minute = Integer.valueOf(strTime.substring(10, 12));
			}
			if (length >= 14) {
				sec = Integer.valueOf(strTime.substring(12, 14));
			}
			calendar = new GregorianCalendar(year, month, day, hour, minute, sec);
		}
		return calendar;
	}
	
	/**
	 * Calendar 转换 long 时间
	 * 
	 * @param calendar
	 * @param length
	 * @return
	 */
	public static Long calendar2Long(Calendar calendar,int length){
		String strYear = String.valueOf(calendar.get(Calendar.YEAR));
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String strDate = String.valueOf(calendar.get(Calendar.DATE));
		String srtHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		String second = String.valueOf(calendar.get(Calendar.SECOND));
		// 整理格式
		strMonth = strMonth.length() < 2 ? "0" + strMonth : strMonth;
		strDate = strDate.length() < 2 ? "0" + strDate : strDate;
		srtHours = srtHours.length() < 2 ? "0" + srtHours : srtHours;
		strMinute = strMinute.length() < 2 ? "0" + strMinute : strMinute;
		second = second.length() < 2 ? "0" + second : second;
		
		if (length == 8) {
			return Long.valueOf(strYear + strMonth + strDate);
		}
		if (length == 14) {
			return Long.valueOf(strYear + strMonth + strDate+ srtHours + strMinute + second);
		}
		
		return Long.valueOf(strYear + strMonth + strDate+ srtHours + strMinute);
	}
	
	/**
	 *  取得8位当前时间 
	 *  
	 * @return
	 */
	public static Long getCurrentDate8(){
		Calendar cldCurrent = Calendar.getInstance();
		return calendar2Long(cldCurrent, 8);
	}
	
	/**
	 * 取14位当前时间
	 * 
	 * @return
	 */
	public static Long getCurrentDate14(){
		Calendar cldCurrent = Calendar.getInstance();
		return calendar2Long(cldCurrent, 14);
	}
	
	/**
	 *  取6位当前时间
	 * 
	 * @return
	 */
	public static Long getCurrentDate6(){
		return ConvertUtil.obj2Long(StringUtils.left(getCurrentDate8().toString(), 6));
	}
	
	/**
	 * 返回传入的时间距离现在的时间的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static Long getLongDate2Now(Long date){
		Calendar calendar = DateUtil.long2Calendar(date);
		return Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();
	}
	
	/**
	 * 得到从from到to的插值时间，即指定的时间间隔多少天
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static Long getFromTime2TimeByDay(Long from,Long to){
		if (from == null || to == null) {
			return null;
		}
		Calendar fromCalendar = DateUtil.long2Calendar(from);
		Calendar toCalendar = DateUtil.long2Calendar(to);
		Long time = toCalendar.getTimeInMillis() - fromCalendar.getTimeInMillis();
		long intTime = time/(1000*60);
		return intTime/(60*24);
		
	}
	
	public static String getCurrentDate() {
		return ymdHms(new Date());
	}
	
	/**
	 * 获取时间的长字符串形式 "yyyy-MM-dd HH:mm:ss"
	 */
	public static String ymdHms(Date date) {
		return formaterDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 返回给定日期加上多少天之后的时间
	 * 
	 * @param from
	 * @param day
	 * @param length
	 * @return
	 */
	public static Long getDateAdd(Long from, int day, int length){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(from.toString()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, day);
		String date = sdf.format(calendar.getTime());
		return ConvertUtil.obj2Long(StringUtils.left(date, length));
	}
	
	
	/**
	 * 本周 周一8位日期和 周日 8位日期
	 * @return
	 */
	public  static Long[] getStartEndWeek() {
		Long date [] = new Long[2];
		Calendar c=Calendar.getInstance(Locale.CHINA);
		c.setTimeInMillis(System.currentTimeMillis());
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		date[0] = Calendar2Long(c, 8);
		
		c.add(Calendar.WEEK_OF_YEAR, 1);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		date[1] = Calendar2Long(c, 8);
		return date;
	}
	
	public static Long Calendar2Long(Calendar calendar,int length){
		String strYear = String.valueOf(calendar.get(Calendar.YEAR));
		String strMonth = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String strDate = String.valueOf(calendar.get(Calendar.DATE));
		String srtHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
		String strMinute = String.valueOf(calendar.get(Calendar.MINUTE));
		String second = String.valueOf(calendar.get(Calendar.SECOND));
		// 整理格式
		strMonth = strMonth.length() < 2 ? "0" + strMonth : strMonth;
		strDate = strDate.length() < 2 ? "0" + strDate : strDate;
		srtHours = srtHours.length() < 2 ? "0" + srtHours : srtHours;
		strMinute = strMinute.length() < 2 ? "0" + strMinute : strMinute;
		second = second.length() < 2 ? "0" + second : second;
		
		if (length == 8) {
			return Long.valueOf(strYear + strMonth + strDate);
		}
		if (length == 14) {
			return Long.valueOf(strYear + strMonth + strDate+ srtHours + strMinute + second);
		}
		
		return Long.valueOf(strYear + strMonth + strDate+ srtHours + strMinute);
	}
	
	public  static Long[] getLastStartEndWeek() {
		Long date [] = new Long[2];
		Calendar c=Calendar.getInstance(Locale.CHINA);
		c.setTimeInMillis(System.currentTimeMillis());
		c.add(Calendar.WEEK_OF_YEAR, -1);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		date[0] = Calendar2Long(c, 8);
		
		c.add(Calendar.WEEK_OF_YEAR, 1);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		
		date[1] = Calendar2Long(c, 8);
		return date;
	}
	

	/**
	 * 计算指定时间前  day 时间零点 返回 Long
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	 public static Long lastDayWholePointLong(long date, int day) {  
		    GregorianCalendar gc = new GregorianCalendar();  
	        gc.setTimeInMillis(date);
	        gc.set(Calendar.DAY_OF_YEAR, gc.get(Calendar.DAY_OF_YEAR) - day);
	    	gc.set(Calendar.HOUR_OF_DAY, 0);
			gc.set(Calendar.SECOND, 0);
			gc.set(Calendar.MINUTE, 0);
			gc.set(Calendar.MILLISECOND, 0);
	        return gc.getTimeInMillis();
	 } 
	 
	/**
	 * 计算指定时间前  day 时间零点 返回 Date
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	 public static Date lastDayWholePointDate(long date, int day) {  
	        return  new Date(lastDayWholePointLong(date, day));
	 } 
	
}
