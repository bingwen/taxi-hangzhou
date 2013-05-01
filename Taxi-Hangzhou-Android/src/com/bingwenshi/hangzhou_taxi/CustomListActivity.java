package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.bingwenshi.hangzhou_taxi.MainActivity.ItemClickListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CustomListActivity extends Activity implements dataListenerActivity {

	private ListView listView;
	MyApplication myApplication;

	ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.custom_list);
		
		myApplication = (MyApplication)getApplication();
		myApplication._dataListenerActivity = this;
		
		listView = (ListView)findViewById(R.id.customListView); 
		TextView tv = (TextView)findViewById(R.id.listHeadTextView);
		
		if (myApplication.listMode == MyApplication.LIST_MODE_ARRANGED) {
			tv.setText("已约乘客");
			requestRecentData();
			
		}
		else {
			tv.setText("附近乘客");
			requestNearbyData();
		}
	}
	
	 //activity的回调函数  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        if(resultCode==2)//表示从时间选择页面传回来的
        {  
           requestDataByDate();//按照开始 结束日期 请求数据
        }  
    } 
    
	public void requestRecentData() {
		
		int num = 2;
		byte[] sendBytes = myApplication.sendMsgCreater.getCurrentBytes(num,myApplication.userCar);
		myApplication.connectSendList.add(sendBytes);
	}
	public void requestNearbyData() {
		
		int num = 10;
		byte[] sendBytes = myApplication.sendMsgCreater.getCustomBytes(num, myApplication.myPositionLongitude, myApplication.myPositionLatitude);
		myApplication.connectSendList.add(sendBytes);
	}
	public void requestDataByDate() {
		
		byte[] sendBytes = myApplication.sendMsgCreater.getHistoryBytes(myApplication.startDate, myApplication.endDate,myApplication.userCar);
		myApplication.connectSendList.add(sendBytes);
	}

	
	public void refreshView() {
		ListViewAdapter listItems = new ListViewAdapter(this, data,
				R.layout.list_item, new String[] { "listMsgText","listCustomPhone","listCustomPoint"},
				new int[] { R.id.listMsgText});
		listView.setAdapter(listItems);
	}

	@Override
	public void listenerUpdate() {
		// TODO Auto-generated method stub
		for (HashMap<String, Object> resultMap : myApplication.gotMsgList) 
		{
			if (((Integer)resultMap.get("msgMode")).intValue() == MyApplication.MSG_MODE_HISTORY || 
					((Integer)resultMap.get("msgMode")).intValue() == MyApplication.MSG_MODE_RECENT ||
					((Integer)resultMap.get("msgMode")).intValue() == MyApplication.MSG_MODE_NEARBY) {
				try {
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("listMsgText", (String)resultMap.get("msg"));//0
					map.put("listCustomPhone", (String)resultMap.get("phone"));//1
					map.put("listCustomPoint", (GeoPoint)resultMap.get("point"));//2 乘客坐标
					data.add(map);
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				myApplication.gotMsgList.remove(resultMap);
				  break;
			}
		}
	}

}
