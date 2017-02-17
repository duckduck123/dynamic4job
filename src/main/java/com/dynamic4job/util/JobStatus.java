package com.dynamic4job.util;

import org.quartz.Trigger.TriggerState;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年12月1日 下午5:17:02
 * 类说明
 */
public class JobStatus {
	public static String convert(TriggerState state){
		try {
			if(state==TriggerState.NONE){
				return "完成";
			}else if (state==TriggerState.NORMAL) {
				return "正常";
			}else if (state==TriggerState.PAUSED) {
				return "暂停";
			}else if (state==TriggerState.COMPLETE) {
				return "任务被触发";
			}else if (state==TriggerState.BLOCKED) {
				return "阻塞";
			}else if (state==TriggerState.ERROR) {
				return "异常";
			}
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
		return "无状态";
	} 
}
