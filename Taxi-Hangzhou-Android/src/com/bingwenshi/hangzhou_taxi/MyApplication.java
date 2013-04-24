package com.bingwenshi.hangzhou_taxi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpResponse;

import com.galhttprequest.GalHttpRequest;
import com.galhttprequest.GalHttpRequest.GalHttpLoadTextCallBack;
import com.galhttprequest.GalHttpRequest.GalHttpRequestListener;

import android.R.integer;
import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyApplication extends Application {

	public static final String URL_HEAD = "http://www.baidu.com";

	public int listMode;
	public static final int LIST_MODE_ARRANGED = 0;
	public static final int LIST_MODE_NEAARBY = 1;

	public int msgMode;
	public static final int MSG_MODE_SCORE = 0;// 积分查询
	public static final int MSG_MODE_CARD = 1;// 刷卡查询
	public static final int MSG_MODE_OPERATION = 2;// 运营查询
	public static final int MSG_MODE_BREAKS = 3;// 违章查询
	public static final int MSG_MODE_NOTICE = 4;// 通知查询
	public static final int MSG_MODE_QUALITY = 5;// 服务质量
	public static final int MSG_MODE_COMPLAIN = 6;// 投诉表扬
	public static final int MSG_MODE_QUESTION = 7;// 常见问题

	public int userState;
	public static final int USER_STATE_WAITLOG = 0;// 未登录
	public static final int USER_STATE_LOGIN = 1;// 已登录
	public static final int USER_STATE_NEEDRELOG = 2;// 重新登录

	public dataListenerActivity _dataListenerActivity = null;

	public String[] mainGridStrings;

	public Calendar startDate;
	public Calendar endDate;

	public double myPositionLatitude;// 維度
	public double myPositionLongitude;// 經度

	public String testMsg;
	private static final String TAG = "MyApplication";

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				listenerUpdate();
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		testMsg = "test";

		TelephonyManager mTelephonyMgr = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
		String imsi = mTelephonyMgr.getSubscriberId();

		listMode = LIST_MODE_ARRANGED;
		msgMode = MSG_MODE_SCORE;

		userState = USER_STATE_WAITLOG;

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

		myPositionLatitude = 39.915 * 1E6;
		myPositionLongitude = 116.404 * 1E6;

	}

	public String[] getMsgList() {
		return mainGridStrings;
	}

	public void refreshData() {

	}

	public boolean checkLogin() {
		return false;

	}

	public void getMsg() {
		httpRequest(URL_HEAD);
	}

	void listenerUpdate() {
		if (_dataListenerActivity != null) {
			_dataListenerActivity.listenerUpdate();
		}
	}

	public void httpRequest(String urlString) {
		GalHttpRequest request = GalHttpRequest.requestWithURL(this, urlString);
		// 第一次调用startAsynRequestString或者startAsynRequestBitmap必须在主线程调用
		// 因为只有在主线程中调用才可以初始化GalHttprequest内部的全局句柄Handler

		request.setListener(new GalHttpRequestListener() {

			@Override
			public void loadFinished(InputStream arg0, boolean arg1) {
				// TODO Auto-generated method stub
				
				handleInputStream(arg0);
				handler.sendEmptyMessage(1);
			}

			@Override
			public void loadFailed(HttpResponse arg0, InputStream arg1) {
				// TODO Auto-generated method stub
				testMsg = "load error";
				handler.sendEmptyMessage(1);
			}
		});
		request.startAsynchronous();
	}
	
	public void handleInputStream(InputStream inputStream) {
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = -1;  
        try {
        	while ((len = inputStream.read(buffer)) != -1) {  
                outSteam.write(buffer, 0, len);  
            }  
            outSteam.close();  
            inputStream.close(); 
            handleDataHead(outSteam.toByteArray());
		} catch (Exception e) {
			// TODO: handle exception
		}
        //testMsg = outSteam.toString();
	}
	
	public void handleDataHead(byte[] dataArray){
		if (dataArray[0] != 0xFF ||  dataArray[1] != 0x27) {
			return;
		}
		int length = ((dataArray[2]<<8)&0xff00)|(dataArray[3]&0xff);
		
		int func = dataArray[4]&0xff;
		
		int[] imsi=new int[18];      
		System.arraycopy(dataArray, 5, imsi, 0, 18);
		
		int device_type = dataArray[23]&0xff;
		
		

	}

}
