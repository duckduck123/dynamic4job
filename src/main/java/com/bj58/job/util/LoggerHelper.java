package com.bj58.job.util;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月29日 下午5:40:31
 * 类说明
 */
public class LoggerHelper {
	public static void init(String path){
		PropertyConfigurator.configure(path);
		jobLog=LogFactory.getLog(JobLogger.class);
		logger=Logger.getLogger(LoggerHelper.class);
	}
	public static Log jobLog;
	
	public static Logger logger;
	
	public static void main(String[] args) {
//		PropertyConfigurator.configure("D:\\AppData\\Eclipse\\58Workspace\\JobServer\\config\\log4j.properties");
//		Logger logger = Logger.getLogger(LoggerHelper.class);
//		logger.info("info.test");
//		logger.debug("info.debug");
//		logger.error("info.error");
		
		Log logger =LogFactory.getLog("joblog");
		logger.info("-----------------------------------------------------");
		logger.info("time	name	status	preFireTime		nextFireTime");
		logger.info("-----------------------------------------------------");
		logger.info("2016-12-1 16:38:55	job2 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
		logger.info("-----------------------------------------------------");
		logger.info("2016-12-1 16:38:55	job3 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
		logger.info("-----------------------------------------------------");
		logger.info("2016-12-1 16:38:55	job4 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
		logger.info("-----------------------------------------------------");
		logger.info("2016-12-1 16:38:55	job5 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
		logger.info("-----------------------------------------------------");
		logger.info("2016-12-1 16:38:55	job6 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
		logger.info("-----------------------------------------------------");
	}
}
