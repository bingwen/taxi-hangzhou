package com.bingwenshi.hangzhou_taxi;

import java.util.ArrayList;
import java.util.HashMap;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.Toast;

public class ListViewAdapter extends BaseAdapter {

	private class buttonViewHolder {

		TextView listMsgText;
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

			holder.listMsgText = (TextView) convertView
					.findViewById(valueViewID[0]);
			
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

			holder.listMsgText.setText((String) appInfo.get(keyString[0]));
			holder.buttonMap
					.setOnClickListener(new mapButtonListener(position,(GeoPoint)appInfo.get(keyString[2])));
			holder.buttonTel
			.setOnClickListener(new telButtonListener(position,(String)appInfo.get(keyString[1])));

		}
		return convertView;
	}

	class mapButtonListener implements OnClickListener {
		public GeoPoint point ;
		mapButtonListener(int pos,GeoPoint point) {
			this.point = point;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.buttonMap.getId())
			{
				((MyApplication)v.getContext().getApplicationContext()).customPoint = point;
				Intent intent = new Intent(v.getContext(), MapViewActivity.class);
				v.getContext().startActivity(intent);
			}
		}
	}

	class telButtonListener implements OnClickListener {
		private String numString;

		telButtonListener(int pos, String str) {
			numString = str;
		}

		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if (vid == holder.buttonMap.getId())
			{
			
			 if(numString.trim().length()!=0) 
		        { 
		         Intent phoneIntent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + numString)); 
		         //启动 
		         v.getContext().startActivity(phoneIntent);
		        } 
		        //否则Toast提示一下 
		        else{ 
		        	Toast.makeText(v.getContext(), "Sorry,号码格式有误", Toast.LENGTH_LONG).show(); 
		        } 
			}
		}
	}

}
