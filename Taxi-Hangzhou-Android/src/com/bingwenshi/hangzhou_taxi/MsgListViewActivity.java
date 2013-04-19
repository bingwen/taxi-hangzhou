package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MsgListViewActivity extends Activity {


	private ListView listView;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		setContentView(R.layout.msg_list_view);
		myApplication = (MyApplication)getApplication();
		
		TextView msgHeadTextView = (TextView)findViewById(R.id.msgHeadTextView);
		
		String[] headStrings = myApplication.mainGridStrings;
		msgHeadTextView.setText(headStrings[myApplication.msgMode+2]);
		
		listView = (ListView)findViewById(R.id.msgListView); 
		MsgViewAdapter listItems = new MsgViewAdapter(this, this.getData(),
				R.layout.list_item, new String[] { "msgListContentText" },
				new int[] { R.id.msgListContentText});
		listView.setAdapter(listItems);
		
		Button backButton = (Button)findViewById(R.id.buttonMsgBack);
		backButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		Button buttonSelectDate = (Button)findViewById(R.id.buttonSelectDate);
		buttonSelectDate.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(arg0.getContext(), MsgDateChooseActivity.class);
				arg0.getContext().startActivity(intent);
			}
			
		});

		
	}
	
	private ArrayList<HashMap<String, Object>> getData() {

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("msgListContentText", "�ϳ�ʱ�䣺2013-4-1 09:11:35\n�³�ʱ�䣺2013-4-1 09:17:22\n��ʻ��̣�10 KM\n�Ⱥ�ʱ�䣺3 ����\n��16");
			data.add(map);
		}

		return data;
	}
}
