package com.dynamic4job.util;
/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月29日 下午3:27:04
 * 类说明
 */
public class JobConfig {
	private String name;
	private String group;
	private String jarPath;
	private String classNameSpace;
	private String methodName;
	private String cron;
	private Boolean enabled;
	
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getJarPath() {
		return jarPath;
	}
	public String getClassNameSpace() {
		return classNameSpace;
	}
	public String getMethodName() {
		return methodName;
	}
	public String getCron() {
		return cron;
	}
	public void setJarPath(String jarPath) {
		this.jarPath = jarPath;
	}
	public void setClassNameSpace(String classNameSpace) {
		this.classNameSpace = classNameSpace;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getName() {
		return name;
	}
	public String getGroup() {
		return group;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGroup(String group) {
		this.group = group;
	}
}
