package com.bingwenshi.hangzhou_taxi;

import java.util.Calendar;
import android.app.Application;

public class MyApplication extends Application {
	
	public int listMode;
	public static final int LIST_MODE_ARRANGED = 0;   
    public static final int LIST_MODE_NEAARBY = 1;  
    
    public int msgMode;
    public static final int MSG_MODE_SCORE = 0;//���ֲ�ѯ
    public static final int MSG_MODE_CARD = 1;//ˢ����ѯ
    public static final int MSG_MODE_OPERATION = 2;//��Ӫ��ѯ
    public static final int MSG_MODE_BREAKS = 3;//Υ�²�ѯ
    public static final int MSG_MODE_NOTICE = 4;//֪ͨ��ѯ
    public static final int MSG_MODE_QUALITY = 5;//��������
    public static final int MSG_MODE_COMPLAIN = 6;//Ͷ�߱���
    public static final int MSG_MODE_QUESTION = 7;//��������
    
    public String[] mainGridStrings;
    
    public Calendar startDate;
    public Calendar endDate;
    
    public double myPositionLatitude;//�S��
    public double myPositionLongitude;//����
    
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		listMode = LIST_MODE_ARRANGED;
		msgMode = MSG_MODE_SCORE;
		mainGridStrings = new String[10];
		mainGridStrings[0] = "��Լ�˿�";
		mainGridStrings[1] = "����Լ��";
		mainGridStrings[2] = "���ֲ�ѯ";
		mainGridStrings[3] = "ˢ����ѯ";
		mainGridStrings[4] = "��Ӫ��ѯ";
		mainGridStrings[5] = "Υ�²�ѯ";
		mainGridStrings[6] = "֪ͨ����";
		mainGridStrings[7] = "��������";
		mainGridStrings[8] = "Ͷ�߱���";
		mainGridStrings[9] = "��������";
		
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		
		myPositionLatitude = 39.915* 1E6;
		myPositionLongitude = 116.404* 1E6;

	}
	
	public String[] getMsgList(){
		return mainGridStrings;
	}
}
