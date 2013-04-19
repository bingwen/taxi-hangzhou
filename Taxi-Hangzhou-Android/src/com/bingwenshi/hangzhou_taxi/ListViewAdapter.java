package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private class buttonViewHolder {

		TextView listTimeText;
		TextView listStartText;
		TextView listEndText;
		TextView listCustomText;

		Button buttonMap;
		Button buttonTel;
	}

	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private buttonViewHolder holder;

	public ListViewAdapter(Context c,
			ArrayList<HashMap<String, Object>> appList, int resource,
			String[] from, int[] to) {
		mAppList = appList;
		mContext = c;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		keyString = new String[from.length];
		valueViewID = new int[to.length];
		System.arraycopy(from, 0, keyString, 0, from.length);
		System.arraycopy(to, 0, valueViewID, 0, to.length);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAppList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mAppList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public void removeItem(int position) {
		mAppList.remove(position);
		this.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		if (convertView != null) {
			holder = (buttonViewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.list_item, null);

			holder = new buttonViewHolder();

			holder.listTimeText = (TextView) convertView
					.findViewById(valueViewID[0]);
			holder.listStartText = (TextView) convertView
					.findViewById(valueViewID[1]);
			holder.listEndText = (TextView) convertView
					.findViewById(valueViewID[2]);
			holder.listCustomText = (TextView) convertView
					.findViewById(valueViewID[3]);
			holder.buttonMap = (Button) convertView
					.findViewById(R.id.buttonMap);
			holder.buttonTel = (Button) convertView
					.findViewById(R.id.buttonTel);
			convertView.setTag(holder);
			
			convertView.setBackgroundColor(0x00ffffff);
			convertView.invalidate();
			
			View listView = (View)convertView
					.findViewById(R.id.listView);
			listView.setBackgroundColor(0x99ffffff);
			listView.invalidate();
		}

		HashMap<String, Object> appInfo = mAppList.get(position);

		if (appInfo != null) {

			holder.listTimeText.setText( "时间："+ (String) appInfo.get(keyString[0]));
			holder.listStartText.setText("上车："+(String) appInfo.get(keyString[1]));
			holder.listEndText.setText("下车："+(String) appInfo.get(keyString[2]));
			holder.listCustomText.setText("乘客："+(String) appInfo.get(keyString[3]));
			holder.buttonMap
					.setOnClickListener(new mapButtonListener(position));
			holder.buttonTel
			.setOnClickListener(new telButtonListener(position));

		}
		return convertView;
	}

	class mapButtonListener implements OnClickListener {
		private int position;

		mapButtonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.buttonMap.getId())
			{
				Intent intent = new Intent(v.getContext(), MapViewActivity.class);
				v.getContext().startActivity(intent);
			}
		}
	}

	class telButtonListener implements OnClickListener {
		private int position;

		telButtonListener(int pos) {
			position = pos;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.buttonMap.getId())
				removeItem(position);
		}
	}

}
