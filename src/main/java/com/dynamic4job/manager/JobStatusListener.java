package com.dynamic4job.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.dynamic4job.cacheManager.Cache;
import com.dynamic4job.constant.JobConstant;
import com.dynamic4job.util.DateUtil;
import com.dynamic4job.util.JobConfig;
import com.dynamic4job.util.JobStatus;
import com.dynamic4job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月29日 下午5:25:40 类说明
 */
@DisallowConcurrentExecution
public class JobStatusListener implements Job {
//	logger.info("time	name	status	preFireTime		nextFireTime");
//	logger.info("-----------------------------------------------------");
//	logger.info("2016-12-1 16:38:55	job2 	正常 	2016-12-1 16:38:55 	2016-12-1 16:38:57");
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			Scheduler scheduler = ScheduleManager
					.getScheduler(JobConstant.SCHEDULENAME);
			String formatLog = "%s	%s	%s	%s	%s";
			List<String> statusMapList=new ArrayList<String>();
			List<JobExecutionContext> cuContexts=scheduler.getCurrentlyExecutingJobs();
			for (JobExecutionContext jobExecutionContext : cuContexts) {
				statusMapList.add(jobExecutionContext.getTrigger().getJobKey().getName());
			}
			for (Entry<String, JobConfig> ite : Cache.jobConfigs.entrySet()) {
				StringBuilder layout = new StringBuilder();
				JobConfig temp = ite.getValue();
				TriggerKey triggerKey = new TriggerKey(temp.getName(),
						temp.getGroup());
				Trigger t = scheduler.getTrigger(triggerKey);
				if(t==null) continue;
				String state="";
				if(statusMapList.contains(temp.getName())){
					state="运行";
				}else {
					state= JobStatus.convert(scheduler.getTriggerState(triggerKey));
				}
				String logStr = String.format(formatLog,
						DateUtil.getCurrentTime(), temp.getName(),state,
						t.getPreviousFireTime()==null?"":DateUtil.formatTime(t.getPreviousFireTime().getTime()),
						DateUtil.formatTime(t.getNextFireTime().getTime()));
				for (int i = 0; i < logStr.length(); i++) {
					layout.append('-');
				}
				LoggerHelper.jobLog.trace(layout.toString());
				LoggerHelper.jobLog.info(logStr);
			}
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
	}
}
