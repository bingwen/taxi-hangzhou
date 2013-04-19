package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MsgViewAdapter extends BaseAdapter {

	private class viewHolder {

		TextView msgListContentText;
	}

	private ArrayList<HashMap<String, Object>> mAppList;
	private LayoutInflater mInflater;
	private Context mContext;
	private String[] keyString;
	private int[] valueViewID;
	private viewHolder holder;

	public MsgViewAdapter(Context c,
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
			holder = (viewHolder) convertView.getTag();
		} else {
			convertView = mInflater.inflate(R.layout.msg_item, null);

			holder = new viewHolder();

			holder.msgListContentText = (TextView) convertView
					.findViewById(valueViewID[0]);
			
			convertView.setTag(holder);
			
			convertView.setBackgroundColor(0x00ffffff);
			convertView.invalidate();
			
			View listView = (View)convertView
					.findViewById(R.id.msgListItemView);
			listView.setBackgroundColor(0x99ffffff);
			listView.invalidate();
		}

		HashMap<String, Object> appInfo = mAppList.get(position);

		if (appInfo != null) {

			holder.msgListContentText.setText((String) appInfo.get(keyString[0]));

		}
		return convertView;
	}

}

