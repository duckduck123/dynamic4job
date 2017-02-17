package com.dynamic4job.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import sun.misc.ClassLoaderUtil;

import com.dynamic4job.cacheManager.Cache;
import com.dynamic4job.constant.JobConstant;
/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月26日 下午3:42:37 类说明
 */

public class JobClassLoader {

	public void invoke(String jarpath, String classnamespace, String methodname) {
		try {
			URLClassLoader myClassLoader = Cache.cacheLoader.get(jarpath);
			LoggerHelper.logger.info("当前classloader线程id:"+Thread.currentThread().getId());
			if (myClassLoader == null) {
				myClassLoader = loader(jarpath, classnamespace);
			}
			if (myClassLoader == null) {
				System.err.println("未加载所指定的jar包,请检查jarpath:" + jarpath
						+ "是否正确!");
				return;
			}
			Class<?> myClass=myClassLoader.loadClass(classnamespace);
			Method method = myClass.getMethod(methodname);
			method.setAccessible(true);
			method.invoke(myClass.newInstance());
			myClass=null;
			System.gc();
			LoggerHelper.logger.info("执行:GC");
		} catch (Exception e) {
			LoggerHelper.logger.error(e, e);
			// TODO: handle exception
		}
	}
	public URLClassLoader loader(String jarpath, String classnamespace) {
		URLClassLoader myClassLoader = null;
		try {
			String jarRootPath = Cache.path.get(JobConstant.JARROOTPATH)
					+ jarpath;
			LoggerHelper.logger.info("jarRootPath:" + jarRootPath);
			String libRootPath = Cache.path.get(JobConstant.JARROOTPATH)
					+ "lib";
			List<URL> urls = new ArrayList<URL>();
			File file = new File(jarRootPath);
			urls.add(file.toURI().toURL());
			File libfile = new File(libRootPath);
			File[] temp = libfile.listFiles();
			if (temp != null && temp.length > 0) {
				for (File f : temp) {
					urls.add(f.toURI().toURL());
				}
			}
			myClassLoader = new URLClassLoader(
					urls.toArray(new URL[urls.size()]), Thread.currentThread()
							.getContextClassLoader());
			Cache.cacheLoader.put(jarpath, myClassLoader);
			Cache.classMap.remove(classnamespace);
			Thread.currentThread().setContextClassLoader(myClassLoader);
		} catch (Exception e) {
			e.printStackTrace();
			LoggerHelper.logger.error(e, e);
			// TODO: handle exception
		}
		return myClassLoader;
	}
	public static void main(String[] args) {

	}
}
