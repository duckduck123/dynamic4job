package com.bj58.job.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.bj58.cacheManager.Cache;
import com.bj58.constant.JobConstant;
import com.bj58.job.util.ConfigManager;
import com.bj58.job.util.JobConfig;
import com.bj58.job.util.LoggerHelper;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月30日 下午12:50:24 类说明
 */
@DisallowConcurrentExecution
public class FileLitenerJob implements Job {
	private File config = null;
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		watchConfig(context);
		watchJar();
	}
	/**
	 * 监控配置文件
	 * @param context
	 */
	public void watchConfig(JobExecutionContext context) {
		try {
			LoggerHelper.logger.info("监听文件......");
			List<JobConfig> needModified = new ArrayList<JobConfig>();
			List<JobConfig> newAdd = new ArrayList<JobConfig>();
			config = new File(Cache.path.get(JobConstant.CONFIG));
			long modifiedTime=config.lastModified();
			long oldmodifiedTime=Cache.fileTime.get(JobConstant.CONFIG);
			if (modifiedTime > oldmodifiedTime) {
				LoggerHelper.logger.info("jobConfig有更新");
				Hashtable<String, JobConfig> configs = new ConfigManager()
						.getConfigs(Cache.path.get(JobConstant.CONFIG));
				if (configs.size() > 0) {
					for (Entry<String, JobConfig> jobConfig : configs
							.entrySet()) {
						// 是否有修改
						if (Cache.jobConfigs.containsKey(jobConfig.getKey())) {
							JobConfig cacheConfig = Cache.jobConfigs
									.get(jobConfig.getKey());
							JobConfig item = jobConfig.getValue();
							if (!cacheConfig.getCron().equals(item.getCron())) {
								needModified.add(item);
								LoggerHelper.logger.info("有修改:"+item.getCron());
								continue;
							}
						} else {// 新增
							newAdd.add(jobConfig.getValue());
						}
					}
				}
			}
			if(newAdd.size() > 0||needModified.size() > 0){
				List<JobExecutionContext> currentlyExecuting = context
						.getScheduler().getCurrentlyExecutingJobs();
				if (currentlyExecuting != null && currentlyExecuting.size() == 1) {
					if (currentlyExecuting.get(0).getJobDetail().getKey().getName()
							.equals(context.getJobDetail().getKey().getName())) {
						if (needModified.size() > 0) {
							LoggerHelper.logger.info("根据最新配置文件修改任务!");
							for (JobConfig jobConfig : needModified) {
								Cache.jobConfigs.put(jobConfig.getName()+jobConfig.getGroup(),jobConfig);
								if(!jobConfig.getEnabled()){
									new JobManager().removeJob(jobConfig);
									LoggerHelper.logger.info("根据最新配置文件停止【"+jobConfig.getName()+"】任务完成!");
								}else {
									new JobManager().modifyJobTime(jobConfig);
									LoggerHelper.logger.info("根据最新配置文件修改【"+jobConfig.getName()+"】任务完成!");
								}
							}
							LoggerHelper.logger.info("根据最新配置文件修改任务完成!");
						}
						if (newAdd.size() > 0) {
							LoggerHelper.logger.info("根据最新配置文件添加任务!");
							for (JobConfig jobConfig : newAdd) {
								Cache.jobConfigs.put(jobConfig.getName()+jobConfig.getGroup(),jobConfig);
								new JobManager().addJob(jobConfig);
							}
							LoggerHelper.logger.info("根据最新配置文件添加任务完成!");
						}
						LoggerHelper.logger.info("更新job配置文件最后修改时间!");
						Cache.fileTime.put(JobConstant.CONFIG,
								config.lastModified());
					}
				}
			}else {
				Cache.fileTime.put(JobConstant.CONFIG,
						config.lastModified());
			}
			config = null;
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
	}
	/**
	 * 监控jar包修改
	 */
	public void watchJar(){
		try {
			LoggerHelper.logger.info("监听jar包......");
			JobConfig jobConfig=null;
			File file=null;
			for (Entry<String,JobConfig> item : Cache.jobConfigs.entrySet()) {
				jobConfig=item.getValue();
				if(jobConfig!=null&&jobConfig.getJarPath()!=""){
					file=new File(Cache.path.get(JobConstant.JARROOTPATH)+jobConfig.getJarPath());
					long modified=file.lastModified();
					//LoggerHelper.logger.info("未修改前:"+Cache.fileTime.get(jobConfig.getJarPath())+",修改后"+modified);
					if(file!=null&&modified>0&&modified!=Cache.fileTime.get(jobConfig.getJarPath())){
						LoggerHelper.logger.info("任务:"+jobConfig.getName()+",jar包有修改");
						Cache.cacheLoader.remove(jobConfig.getJarPath());
						LoggerHelper.logger.info("任务:"+jobConfig.getName()+",jar包更新成功");
						Cache.fileTime.put(jobConfig.getJarPath(),file.lastModified());
					}
				}
			}
			file=null;jobConfig=null;
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
	}
}
