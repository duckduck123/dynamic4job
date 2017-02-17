package com.bj58.job.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.bj58.job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月23日 下午4:42:08
 * 类说明
 */
public class UniJobListener implements JobListener{
	/**
	 * getName() 方法返回一个字符串用以说明 JobListener 的名称。
	 * 对于注册为全局的监听器，getName() 主要用于记录日志，
	 * 对于由特定 Job 引用的 JobListener，
	 * 注册在 JobDetail 上的监听器名称必须匹配从监听器上 getName() 方法的返回值。
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return "MyjobListener";
	}
	/**
	 * Scheduler 在 JobDetail 将要被执行时调用这个方法。
	 */
	public void jobToBeExecuted(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//System.out.println("MyJobListener.jobToBeExecuted()");
	}
	/**
	 * Scheduler 在 JobDetail 即将被执行，但又被 TriggerListener 否决了时调用这个方法
	 */
	public void jobExecutionVetoed(JobExecutionContext context) {
		// TODO Auto-generated method stub
		//System.out.println("MyJobListener.jobExecutionVetoed()");
	}
	/**
	 * Scheduler 在 JobDetail 被执行之后调用这个方法。
	 */
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		//LoggerHelper.logger.error(jobException.getStackTrace());
		// TODO Auto-generated method stub
		//System.out.println("MyJobListener.jobWasExecuted()");
	}

}
