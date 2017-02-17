package com.dynamic4job.cacheManager;

import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.quartz.Scheduler;

import com.dynamic4job.util.JobConfig;

/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月29日 下午4:59:46
 * 类说明
 */
public class Cache {
	
	public static Hashtable<String,Scheduler> scheduler=new Hashtable<String, Scheduler>();
	
	/**
	 * job配置集合
	 */
	public static Hashtable<String,JobConfig> jobConfigs=new Hashtable<String,JobConfig>();
	/**
	 * 把classloader缓存起来
	 */
	public static ConcurrentHashMap<String,URLClassLoader> cacheLoader=new ConcurrentHashMap<String,URLClassLoader>();
	
	public static ConcurrentHashMap<String,Class> classMap=new ConcurrentHashMap<String,Class>();
	
	public static Hashtable<String,Long> fileTime=new Hashtable<String, Long>();
	
	public static Hashtable<String,String> path=new Hashtable<String,String>();
}
