package com.dynamic4job.manager;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.dynamic4job.cacheManager.Cache;
import com.dynamic4job.constant.JobConstant;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年12月1日 下午3:42:19
 * 类说明
 */
public class ScheduleManager {
	public static Scheduler getScheduler(String key){
		Scheduler scheduler=null;
		try {
			if(Cache.scheduler.get(key)!=null){
				scheduler=Cache.scheduler.get(key);
			}else {
				scheduler=new StdSchedulerFactory().getScheduler();
				Cache.scheduler.put(JobConstant.SCHEDULENAME,scheduler);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return scheduler;
	}
}
