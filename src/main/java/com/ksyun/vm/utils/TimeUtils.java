/**
 * Id
 */
package com.ksyun.vm.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <code>title</code>
 * 时间工具类
 * <p>description
 * <p>example:
 * <blockquote><pre>
 * </blockquote></pre>
 * @author Author
 * @version Revision Date
 */
public class TimeUtils {
	public static String makeYMDHMSStringFormat(Date date){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return ymdhms.format(date);
	}
	public static String makeYMDStringFormat(Date date){
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		return ymd.format(date);
	}
	public static String makeYMStringFormat(Date date){
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		return ym.format(date);
	}
	public static Date makeYMDHMSDateFormat(String date){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return ymdhms.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Date makeYMDDateFormat(String date){
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return ymd.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Date makeYMDateFormat(String date){
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		try {
			return ym.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String makeYMDStringFormat(String date){
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd");
		Date dateTime = makeYMDHMSDateFormat(date);
		return ymd.format(dateTime);
	}
	public static String makeYMDHMSStringFormat(String date){
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateTime = makeYMDHMSDateFormat(date);
		return ymd.format(dateTime);
	}
	public static String makeYMStringFormat(String date){
		SimpleDateFormat ym = new SimpleDateFormat("yyyy-MM");
		Date dateTime = makeYMDHMSDateFormat(date);
		return ym.format(dateTime);
	}
	
	public static Date makeYMDHMSDateFormat(Date date){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = ymdhms.format(date);
		return makeYMDHMSDateFormat(dateTime);
	}
	
	public static Date makeYMDDateFormat(Date date){
		SimpleDateFormat ymdhms = new SimpleDateFormat("yyyy-MM-dd");
		String dateTime = ymdhms.format(date);
		return makeYMDDateFormat(dateTime);
	}
	public static String parseISODateFormat(String time){
		String[] times = time.split("T");
		String[] hms = times[1].split(":");
		String result = times[0] + " " + hms[0].substring(0, 2) + ":" + hms[1].substring(0, 2) + ":" + hms[2].substring(0, 2);
		return result;
	}
	public static void main(String[] args){
		System.out.println(parseISODateFormat("2013-09-04T10:43:08Z"));
	}
}
