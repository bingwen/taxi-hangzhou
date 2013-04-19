package com.bingwenshi.hangzhou_taxi;

import java.util.Calendar;
import android.app.Application;

public class MyApplication extends Application {
	
	public int listMode;
	public static final int LIST_MODE_ARRANGED = 0;   
    public static final int LIST_MODE_NEAARBY = 1;  
    
    public int msgMode;
    public static final int MSG_MODE_SCORE = 0;//积分查询
    public static final int MSG_MODE_CARD = 1;//刷卡查询
    public static final int MSG_MODE_OPERATION = 2;//运营查询
    public static final int MSG_MODE_BREAKS = 3;//违章查询
    public static final int MSG_MODE_NOTICE = 4;//通知查询
    public static final int MSG_MODE_QUALITY = 5;//服务质量
    public static final int MSG_MODE_COMPLAIN = 6;//投诉表扬
    public static final int MSG_MODE_QUESTION = 7;//常见问题
    
    public String[] mainGridStrings;
    
    public Calendar startDate;
    public Calendar endDate;
    
    public double myPositionLatitude;//維度
    public double myPositionLongitude;//經度
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		listMode = LIST_MODE_ARRANGED;
		msgMode = MSG_MODE_SCORE;
		mainGridStrings = new String[10];
		mainGridStrings[0] = "已约乘客";
		mainGridStrings[1] = "附近约车";
		mainGridStrings[2] = "积分查询";
		mainGridStrings[3] = "刷卡查询";
		mainGridStrings[4] = "运营查询";
		mainGridStrings[5] = "违章查询";
		mainGridStrings[6] = "通知公告";
		mainGridStrings[7] = "服务质量";
		mainGridStrings[8] = "投诉表扬";
		mainGridStrings[9] = "常见问题";
		
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		
		myPositionLatitude = 39.915* 1E6;
		myPositionLongitude = 116.404* 1E6;

	}
	
	public String[] getMsgList(){
		return mainGridStrings;
	}
}
