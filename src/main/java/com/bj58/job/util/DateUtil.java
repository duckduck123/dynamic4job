package com.bj58.job.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年12月1日 下午5:03:17
 * 类说明
 */
public class DateUtil {
	/**
	 * 日期格式，年月日时分秒
	 */
	public static final String PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
	public static String getCurrentTime(){
		try {
			SimpleDateFormat df = new SimpleDateFormat(PATTERN_FULL);
			return df.format(new Date());
		} catch (Exception e) {
			// TODO: handle exception
			LoggerHelper.logger.error(e,e);
		}
		return "";
	}
	public static String formatTime(Long date){
		try {
			SimpleDateFormat df = new SimpleDateFormat(PATTERN_FULL);
			return df.format(date);
		} catch (Exception e) {
			// TODO: handle exception
			LoggerHelper.logger.error(e,e);
		}
		return "";
	}
}
