package com.web.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.web.util.Putil;
import com.web.util.TaskCheckCommerce;

public class ServiceInit implements ServletContextListener {
//	java.util.Timer timer = new java.util.Timer();

	/**
	 * 此方法重写了ServletContextListener自带的contextInitialized方法,规定了定时器运行的时间，运行的任务
	 * 描述了开始运行时的要做的事情。
	 * 
	 * @param sce
	 */
	public void contextInitialized(ServletContextEvent sce) {
		new TaskCheckCommerce().start();
		
		Putil.printLog("contextInitialized");
	}

	/**
	 * 方法重写了ServletContextListener自带的contextDestroyed方法,规定了如何卸载任务
	 * 
	 * @param sce
	 */
	public void contextDestroyed(ServletContextEvent sce) {
//		timer.cancel();
		// task.cancel();
		// if(loadcacheTask.isAlive()) loadcacheTask.destroy() ;
		Putil.printLog("contextDestroyed");
	}

}
