package com.dynamic4job.manager;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.ListenerManager;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.dynamic4job.cacheManager.Cache;
import com.dynamic4job.constant.JobConstant;
import com.dynamic4job.listener.UniJobListener;
import com.dynamic4job.listener.UniTriggerListener;
import com.dynamic4job.util.JobConfig;
import com.dynamic4job.util.LoggerHelper;

import static org.quartz.CronScheduleBuilder.cronSchedule;
/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月26日 下午3:14:15 类说明
 */
public class JobManager {

	public void init(Hashtable<String, JobConfig> jobConfigs) {
		try {
			Scheduler scheduler =ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			ListenerManager manager = scheduler.getListenerManager();
			// 添加job监听
			manager.addJobListener(new UniJobListener());
			// 添加Trigger监听
			manager.addTriggerListener(new UniTriggerListener());
			if (jobConfigs != null && jobConfigs.size() > 0) {
				for (Entry<String, JobConfig> item : jobConfigs.entrySet()) {
					addJob(item.getValue());
				}
			}
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param jobName
	 *            job名称
	 * @param jobGroupName
	 *            job分组名称
	 * @param triggerKey
	 *            触发器key
	 * @param paramDataMap
	 *            参数
	 */
	public void addJob(Scheduler scheduler, String jobName,
			String jobGroupName, String cron, JobDataMap paramDataMap) {
		try {
			if (scheduler == null) {
				scheduler = ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			}
			JobDetail jobDetail = JobBuilder.newJob(UniJob.class)
					.withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
			jobDetail.isConcurrentExectionDisallowed();
			if (paramDataMap != null && paramDataMap.size() > 0) {
				jobDetail.getJobDataMap().putAll(paramDataMap);
			}
			// 触发器
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(new TriggerKey(jobName, jobGroupName))
					.withSchedule(cronSchedule(cron)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			LoggerHelper.logger.info("-------------------addjob:" + jobName);
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}

	}
	public void addJob(JobConfig jobConfig) {
		try {
			Scheduler scheduler =ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			JobDetail jobDetail = JobBuilder.newJob(UniJob.class)
					.withIdentity(jobConfig.getName(), jobConfig.getGroup())
					.build();// 任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put(JobConstant.JARPATH, jobConfig.getJarPath());
			jobDetail.getJobDataMap().put(JobConstant.CLASSNAMESPACE, jobConfig.getClassNameSpace());
			jobDetail.getJobDataMap().put(JobConstant.METHODNAME, jobConfig.getMethodName());
			// 触发器
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(
							new TriggerKey(jobConfig.getName(), jobConfig
									.getGroup()))
					.withSchedule(cronSchedule(jobConfig.getCron())).build();
			scheduler.scheduleJob(jobDetail, trigger);
			LoggerHelper.logger.info("-------------------addjob:" + jobConfig.getName());
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}

	}
	public void addJob(String cron,String jobName,String group,Class cls) {
		try {
			Scheduler scheduler =ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			JobDetail jobDetail = JobBuilder.newJob(cls)
					.withIdentity(jobName, group)
					.build();// 任务名，任务组，任务执行类
			// 触发器
			Trigger trigger = TriggerBuilder
					.newTrigger()
					.withIdentity(
							new TriggerKey(jobName,group))
					.withSchedule(cronSchedule(cron)).build();
			scheduler.scheduleJob(jobDetail, trigger);
			LoggerHelper.logger.info("-------------------addjob:" + jobName);
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}

	}
	public void modifyJobTime(JobConfig jobConfig) {
		try {
			Scheduler scheduler =ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			Trigger newTrigger = TriggerBuilder
					.newTrigger()
					.withIdentity(
							new TriggerKey(jobConfig.getName(),jobConfig.getGroup()))
					.withSchedule(cronSchedule(jobConfig.getCron())).build();
			scheduler.rescheduleJob(new TriggerKey(jobConfig.getName(),jobConfig.getGroup()), newTrigger);
			LoggerHelper.logger.info("-------------------modifyjob:" + jobConfig.getName());
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
		}
	}
	public void removeJob(JobConfig jobConfig) {
		try {
			Scheduler sched =ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			
			sched.pauseTrigger(new TriggerKey(jobConfig.getName(),jobConfig.getGroup()));// 停止触发器
			sched.unscheduleJob(new TriggerKey(jobConfig.getName(),jobConfig.getGroup()));// 移除触发器
			sched.deleteJob(new JobKey(jobConfig.getName(),jobConfig.getGroup()));// 删除任务
			LoggerHelper.logger.info("-------------------deletejob:" + jobConfig.getName());
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
		}
	}
	public boolean isRunning(String jobName) {
		try {
			Scheduler scheduler = ScheduleManager.getScheduler(JobConstant.SCHEDULENAME);
			List<JobExecutionContext> jobExecutionContexts=scheduler.getCurrentlyExecutingJobs();
			if(jobExecutionContexts!=null&&jobExecutionContexts.size()>0){
				for (JobExecutionContext jExecutionContext : jobExecutionContexts) {
					if(jExecutionContext.getJobDetail().getKey().getName().equals(jobName)){
						return true;
					}
				}
			}
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
		return false;
	}
}
