package com.bj58.job.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.PropertyResourceBundle;

import javax.xml.parsers.*;

import org.w3c.dom.*;
import org.xml.sax.*;

import com.bj58.cacheManager.Cache;
import com.bj58.constant.JobConstant;
/**
 * @author 作者 mister_ge@foxmail.com:
 * @version 创建时间：2016年11月29日 下午3:31:22 类说明
 */
public class ConfigManager {
	/**
	 * 读取文件
	 * 
	 * @param filePath
	 * @return 
	 */
	
	public void init(String filePath) {
		try {
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				InputStream inputStream = cl.getResourceAsStream("META-INF/system.properties");
				PropertyResourceBundle pp = new PropertyResourceBundle(inputStream);
				String sysKey = System.getProperty("crmsh_jobs_online");
				String namespace="";
				if (sysKey == null) {
					 namespace=pp.getString("path");
				}else {
					 namespace=pp.getString("onlinePath");
				}
				
				Cache.path.put(JobConstant.JARROOTPATH, namespace);
			} catch (Exception e) {
				LoggerHelper.logger.error("读取path.properties异常:"+e,e);
				// TODO: handle exception
			}
			Cache.jobConfigs.putAll(getConfigs(filePath));
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
	}
	public Hashtable<String,JobConfig> getConfigs(String filePath){
		Hashtable<String,JobConfig> configs=new Hashtable<String,JobConfig>();
		try {
			//读取log4j配置
			File file = new File(filePath);
			Cache.fileTime.put(JobConstant.CONFIG, file.lastModified());
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nodeList = doc.getElementsByTagName("job");
			for (int i = 0; i < nodeList.getLength(); i++) {
				if(!getNodeBooleanValue(doc.getElementsByTagName("enabled").item(i).getFirstChild())){
					continue;
				}
				JobConfig entity=new JobConfig();
				entity.setName(getNodeValue(doc.getElementsByTagName("name").item(i).getFirstChild()));
				entity.setGroup(getNodeValue(doc.getElementsByTagName("group").item(i).getFirstChild()));
				entity.setJarPath(getNodeValue(doc.getElementsByTagName("jarPath").item(i).getFirstChild()));
				entity.setClassNameSpace(getNodeValue(doc.getElementsByTagName("classNameSpace").item(i).getFirstChild()));
				entity.setMethodName(getNodeValue(doc.getElementsByTagName("methodName").item(i).getFirstChild()));
				entity.setCron(getNodeValue(doc.getElementsByTagName("cron").item(i).getFirstChild()));
				entity.setEnabled(getNodeBooleanValue(doc.getElementsByTagName("enabled").item(i).getFirstChild()));
				configs.put(entity.getName()+entity.getGroup(),entity);
				Cache.fileTime.put(entity.getJarPath(),new Date().getTime());
			}
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
		return configs;
	}
	public  String getNodeValue(Node node){
		if(node==null) return "";
		return node.getNodeValue();
	}
	public  Boolean getNodeBooleanValue(Node node){
		 try {
			 String value=node.getNodeValue();
			 return Boolean.valueOf(value);
		} catch (Exception e) {
			LoggerHelper.logger.error(e,e);
			// TODO: handle exception
		}
		 return false;
	}
	public void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File file = new File(
					"D:\\AppData\\Eclipse\\58Workspace\\JobServer\\config\\JobConfig.xml");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document doc = builder.parse(file);
			NodeList nl = doc.getElementsByTagName("job");
			for (int i = 0; i < nl.getLength(); i++) {
				System.out.print("name:"+ doc.getElementsByTagName("name").item(i).getFirstChild().getNodeValue());
				System.out.print("group:"+ doc.getElementsByTagName("group").item(i).getFirstChild().getNodeValue());
				System.out.print("jarPath:"+ doc.getElementsByTagName("jarPath").item(i).getFirstChild().getNodeValue());
				System.out.print("classNameSpace:"+ doc.getElementsByTagName("classNameSpace").item(i).getFirstChild().getNodeValue());
				System.out.print("methodName:"+ doc.getElementsByTagName("methodName").item(i).getFirstChild().getNodeValue());
				Node node=doc.getElementsByTagName("cron").item(i).getFirstChild();
				System.out.print("cron:"+ doc.getElementsByTagName("cron").item(i).getFirstChild().getNodeValue());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

	}

}
