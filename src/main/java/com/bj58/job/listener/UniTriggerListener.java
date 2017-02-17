package com.bj58.job.listener;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import com.bj58.job.manager.JobManager;
import com.bj58.job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月23日 下午4:35:48 类说明
 */
public class UniTriggerListener implements TriggerListener {
	public String getName() {
		// TODO Auto-generated method stub
		return "myTriggerListener";
	}
	/**
     * (1)
     * Trigger被激发 它关联的job即将被运行
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed.
     */
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		LoggerHelper.logger.info("-------------------job:【"+trigger.getJobKey().getName()+"】StartTime:["+trigger.getStartTime().getTime()+"]开始执行-------------------");
	}
	/**
     * (2)
     * Trigger被激发 它关联的job即将被运行,先执行(1)，在执行(2) 如果返回TRUE 那么任务job会被终止
     * Called by the Scheduler when a Trigger has fired, and it's associated JobDetail is about to be executed
     */
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		// TODO Auto-generated method stub
		//System.out.println("vetoJobExceptionssssss");
		return false;
	}
	 /**
     * (3) 当Trigger错过被激发时执行,比如当前时间有很多触发器都需要执行，但是线程池中的有效线程都在工作，
     *  那么有的触发器就有可能超时，错过这一轮的触发。
     * Called by the Scheduler when a Trigger has misfired.
     */
	public void triggerMisfired(Trigger trigger) {
		// TODO Auto-generated method stub
		//System.out.println("triggerMisfired");
	}
	 /**
     * (4) 任务完成时触发
     * Called by the Scheduler when a Trigger has fired, it's associated JobDetail has been executed
     * and it's triggered(xx) method has been called.
     */
	public void triggerComplete(Trigger trigger, JobExecutionContext context,
			CompletedExecutionInstruction triggerInstructionCode) {
		// TODO Auto-generated method stub
		LoggerHelper.logger.info("-------------------job:【"+trigger.getJobKey().getName()+"】endTime:["+new Date().getTime()+"]执行完毕!-------------------");
	}

}
