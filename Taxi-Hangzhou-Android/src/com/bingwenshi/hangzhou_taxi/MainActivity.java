package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	private GridView mainGridView;
	private String[] mainGridStrings;
	private Intent intent;
	private MyApplication myApplication;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		


		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		myApplication = (MyApplication) getApplication(); 
		
		mainGridStrings = myApplication.mainGridStrings;
		
		mainGridView = (GridView) this.findViewById(R.id.mainGridView);

		// 生成动态数组，并且转入数据
		ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			switch (i) {
			case 0:
				map.put("ItemImage", R.drawable.icon1);
				break;
			case 1:
				map.put("ItemImage", R.drawable.icon2);
				break;
			case 2:
				map.put("ItemImage", R.drawable.icon3);
				break;
			case 3:
				map.put("ItemImage", R.drawable.icon4);
				break;
			case 4:
				map.put("ItemImage", R.drawable.icon5);
				break;
			case 5:
				map.put("ItemImage", R.drawable.icon9);
				break;
			case 6:
				map.put("ItemImage", R.drawable.icon7);
				break;
			case 7:
				map.put("ItemImage", R.drawable.icon8);
				break;
			case 8:
				map.put("ItemImage", R.drawable.icon6);
				break;
			case 9:
				map.put("ItemImage", R.drawable.icon10);
				break;
			default:
				break;
			}
			
			map.put("ItemText", mainGridStrings[i]);
			lstImageItem.add(map);
		}
		//
		SimpleAdapter saImageItems = new SimpleAdapter(this, lstImageItem,
				R.layout.grid_item, new String[] { "ItemImage", "ItemText" },
				new int[] { R.id.ItemImage, R.id.ItemText });
		mainGridView.setColumnWidth(100);
		
		mainGridView.setAdapter(saImageItems);
		mainGridView.setOnItemClickListener(new ItemClickListener());

	}

	class ItemClickListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub

			HashMap<String, Object> item = (HashMap<String, Object>) arg0
					.getItemAtPosition(arg2);

			switch (arg2) {
			case 0:
				myApplication.listMode = MyApplication.LIST_MODE_ARRANGED;
				 intent = new Intent(arg1.getContext(), CustomListActivity.class);
				 //intent = new Intent(arg1.getContext(), MapViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 1:
				myApplication.listMode = MyApplication.LIST_MODE_NEAARBY;
				 intent = new Intent(arg1.getContext(), CustomListActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 2:
				myApplication.msgMode = MyApplication.MSG_MODE_SCORE;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 3:
				myApplication.msgMode = MyApplication.MSG_MODE_CARD;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 4:
				myApplication.msgMode = MyApplication.MSG_MODE_OPERATION;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 5:
				myApplication.msgMode = MyApplication.MSG_MODE_BREAKS;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 6:
				myApplication.msgMode = MyApplication.MSG_MODE_NOTICE;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 7:
				myApplication.msgMode = MyApplication.MSG_MODE_QUALITY;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 8:
				myApplication.msgMode = MyApplication.MSG_MODE_COMPLAIN;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			case 9:
				myApplication.msgMode = MyApplication.MSG_MODE_QUESTION;
				 intent = new Intent(arg1.getContext(), MsgListViewActivity.class);
				 arg1.getContext().startActivity(intent);
				break;
			default:
				break;
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.add(Menu.NONE, Menu.FIRST + 1, 5, "修改信息");
		menu.add(Menu.NONE, Menu.FIRST + 2, 5, "退出");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case Menu.FIRST + 1:
			Intent intent = new Intent(this, InfoActivity.class);
			this.startActivity(intent);
			break;
		case Menu.FIRST + 2:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
