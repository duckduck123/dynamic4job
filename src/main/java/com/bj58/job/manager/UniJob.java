package com.bj58.job.manager;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.bj58.constant.JobConstant;
import com.bj58.job.util.JobClassLoader;
import com.bj58.job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月26日 下午3:25:17 类说明
 */
@DisallowConcurrentExecution
public class UniJob implements Job {
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("start --------------------------"+new Date()+"-------------------");
		// TODO Auto-generated method stub
		JobDataMap param = context.getMergedJobDataMap();
		new JobClassLoader().invoke(param.getString(JobConstant.JARPATH),
				param.getString(JobConstant.CLASSNAMESPACE),
				param.getString(JobConstant.METHODNAME));
	}

}
