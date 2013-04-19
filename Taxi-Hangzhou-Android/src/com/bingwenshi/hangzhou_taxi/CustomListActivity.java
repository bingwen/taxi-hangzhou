package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bingwenshi.hangzhou_taxi.MainActivity.ItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CustomListActivity extends Activity {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.custom_list);
		
		listView = (ListView)findViewById(R.id.customListView); 
		ListViewAdapter listItems = new ListViewAdapter(this, this.getData(),
				R.layout.list_item, new String[] { "listTimeText",
						"listStartText", "listEndText", "listCustomText" },
				new int[] { R.id.listTimeText, R.id.listStartText,
						R.id.listEndText, R.id.listCustomText });
		listView.setAdapter(listItems);

		
	}

	private ArrayList<HashMap<String, Object>> getData() {

		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 10; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("listTimeText", "时间1234");
			map.put("listStartText", "上车地点");
			map.put("listEndText", "下车地点");
			map.put("listCustomText", "乘客");
			map.put("listCustomPhone", "13732251012");
			map.put("listCustomCoordinate", "乘客");
			data.add(map);
		}

		return data;
	}

}
