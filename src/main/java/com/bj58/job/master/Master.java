package com.bj58.job.master;

import com.bj58.cacheManager.Cache;
import com.bj58.constant.JobConstant;
import com.bj58.job.manager.FileLitenerJob;
import com.bj58.job.manager.JobManager;
import com.bj58.job.manager.JobStatusListener;
import com.bj58.job.util.ConfigManager;
import com.bj58.job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月26日 下午2:03:01
 * 类说明
 */
public class Master {
	private static JobManager jobManager=new JobManager();
	public static void main(String[] args) {
		try {
			if(args.length<=1){
				System.err.println("配置文件路径错误!");
				return;
			}
			LoggerHelper.init(args[1]);
			LoggerHelper.logger.info("读取log4j配置文件 初始化log4j配置完成");
			
			LoggerHelper.logger.info("缓存job配置文件路径");
			Cache.path.put(JobConstant.CONFIG,args[0]);
			
			LoggerHelper.logger.info("初始化配置文件");
			new ConfigManager().init(args[0]);
			
			LoggerHelper.logger.info("初始化job");
			jobManager.init(Cache.jobConfigs);
			
			LoggerHelper.logger.info("添加监控配置文件任务");
			jobManager.addJob("*/10 * * * * ?", "watchFile", "watchFile",FileLitenerJob.class);
			
			LoggerHelper.logger.info("添加监控当前运行job任务");
			jobManager.addJob("*/10 * * * * ?", "currentlyExecuting", "currentlyExecuting",JobStatusListener.class);
		} catch (Exception e) {
			LoggerHelper.logger.error("Master启动异常:"+e);
			// TODO: handle exception
		}
		
	}
}
