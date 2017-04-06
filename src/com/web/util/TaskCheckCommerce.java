package com.web.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.web.db.DbUtils;


public class TaskCheckCommerce extends Thread {

	public static boolean stop = false ;
	
	public TaskCheckCommerce() {
		super();
	}

	public void run() {
		while(true) {
			if(stop) return ;
			checkScheduleComplete() ;
			checkSchedule();
			
			//延时10分钟
    		try {Thread.currentThread().sleep(1000 * 60 * 10) ;} catch(Exception e) {}
		}
	}
	
/**
 * 	
 */
	public void checkScheduleComplete() {

		try {
			
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
	}
	
	public static void countSchedule(Map<String, Object> sch) {
		try {
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
	}
	
	//待删除
	public void checkScheduleComplete1() {

		try {
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
	}

	public void checkSchedule() {
    
        Calendar cstart = Calendar.getInstance();
        cstart.add(Calendar.MINUTE, 60);
        Calendar cend = Calendar.getInstance();
        cend.add(Calendar.MINUTE, -60);
        Calendar ctoken = Calendar.getInstance();
        ctoken.add(Calendar.MINUTE, -15);

		try {

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

}
