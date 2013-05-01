package com.bingwenshi.hangzhou_taxi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpResponse;

import com.baidu.platform.comapi.basestruct.GeoPoint;
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

public class MyApplication extends Application implements Runnable{

	//public static final String SERVER_IP = "10.180.69.31";
	//public static final int SERVER_PORT = 6800;
	
	public static final String SERVER_IP = "115.238.57.12";
	public static final int SERVER_PORT = 9999;

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
	
	public static final int MSG_MODE_REGISTER = 10;// 注册
	public static final int MSG_MODE_LOGIN = 11;// 登录
	public static final int MSG_MODE_LOGOUT = 12;// 登出
	
	public static final int MSG_MODE_HISTORY = 10;// 注册
	public static final int MSG_MODE_RECENT = 11;// 登录
	public static final int MSG_MODE_NEARBY = 12;// 登出

	public int userState;
	public static final int USER_STATE_WAITLOG = 0;// 未登录
	public static final int USER_STATE_LOGIN = 1;// 已登录
	public static final int USER_STATE_NEEDRELOG = 2;// 重新登录
	//用户信息
	public String userNameString = "";
	public String userPhone = "";
	//public String userPassword = "";
	public String userCar = "";
	public String userService = "";
	//public String userPeopleNum = "";
	
	
	public int msgState;
	public static final int MSG_STATE_WAIT = 0;// 等待
	public static final int MSG_STATE_SUCCESS = 1;// 成功
	public static final int MSG_STATE_FAILED = 2;// 失败
	public static final int MSG_STATE_ERROR = 3;// 消息格式错误
	
	//msg变量
	//private int bodyLength;
	//int func;
	//int device_type;
	public byte[] imsi;
	
	//socket
	Socket connectSocket;
	InputStream connectInputStream;
	OutputStream connectOutputStream;
	public ArrayList<byte[]> connectSendList = new ArrayList<byte[]>(); 
	public ArrayList<HashMap<String, Object>> gotMsgList = new ArrayList<HashMap<String, Object>>();
	
	SendMsgCreater sendMsgCreater = new SendMsgCreater();
	
	public dataListenerActivity _dataListenerActivity = null;

	public String[] mainGridStrings;

	public Calendar startDate;
	public Calendar endDate;

	public double myPositionLatitude = 30.34365;// 維度
	public double myPositionLongitude = 120.4545;// 經度
	public GeoPoint customPoint;

	//public String testMsg;
	private static final String TAG = "MyApplication";
	
	//handler 通知主线程更新数据
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
	
		testSocketConnect();
	}

	public String[] getMsgList() {
		return mainGridStrings;
	}

	public void refreshData() {

	}
	//检查是否登录
	public boolean checkLogin() {
		return false;
	}
	//发送消息
	public void getMsg() {
		
	}
	//更新监听者的UI
	void listenerUpdate() {
		if (_dataListenerActivity != null) {
			_dataListenerActivity.listenerUpdate();
		}
	}
	//检查是否已建立socket链接
	public void testSocketConnect() {
		if (connectSocket == null) {
			new Thread(this).start();
		}
	}
	//建立socket链接
	public void createSocketConnect() {
		if (connectSocket == null) {
			try {
				connectSocket = new Socket(SERVER_IP,SERVER_PORT);
				connectSocket.setSoTimeout(2000);
				connectInputStream = connectSocket.getInputStream();
				connectOutputStream = connectSocket.getOutputStream();
				 
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	 @Override  
    public void run() {  
        while(true){
        	if (connectSocket == null) {
    			createSocketConnect();
    		}
        	//接收数据
        	ByteArrayOutputStream outSteam = new ByteArrayOutputStream();  
            byte[] buffer = new byte[1024];  
            int len = -1;  
            try {
            	while (true) {  
            		try {
                		len = connectInputStream.read(buffer);
                		Log.e(TAG, ">>>>>>>>read...");
                		outSteam.write(buffer, 0, len); 
                		if (len<1024) {
							break;
						}
    				} catch (Exception e) {
    					// TODO: handle exception
    					Log.e(TAG, "time out");
    					break;
    				}
                }  
                outSteam.close();  
                //处理数据
                byte[] gotBytes = outSteam.toByteArray();
                if (gotBytes.length>0) {
              
                	 GotMsgDecoder gotMsgDecoder = new GotMsgDecoder();
                	 gotMsgDecoder.handleData(gotBytes);
                     if (gotMsgDecoder.decodeSuccess()) {
     					gotMsgList.add(gotMsgDecoder.resultMap);
     					handler.sendEmptyMessage(1);
     				}
				}
                
    		} catch (Exception e) {
    			// TODO: handle exception
    		} 
            //检查消息队列是否有消息
            if (!connectSendList.isEmpty()) {
            	//获取第一条消息
				byte[] sendBytes= connectSendList.get(0);
				connectSendList.remove(0);
				try {
					//发送消息
					Log.e(TAG, ">>>>>send....");
                	Log.e(TAG, byte2HexString(sendBytes));
					connectOutputStream.write(sendBytes);
					connectOutputStream.flush();
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, ">>>>>send srror");
				}
			}
            
        }  
    } 
	 public static String byte2HexString( byte[] b) { 
		 StringBuilder sb = new StringBuilder();
		 for (int i = 0; i < b.length; i++) { 
			 String hex = Integer.toHexString(b[i] & 0xFF); 
			 if (hex.length() == 1) { 
				 hex = '0' + hex; 
			 } 
			 sb.append(hex);
		} 
		 return sb.toString();

	 } 
	
}
