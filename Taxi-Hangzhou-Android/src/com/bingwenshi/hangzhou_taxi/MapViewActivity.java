package com.bingwenshi.hangzhou_taxi;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MapViewActivity extends Activity {
	
	final static String TAG = "MapActivty";
	
	private Button mapMyPositionbtn;
	private Button mapCustomPositionbtn;
	private Button mapRefreshPositionbtn;
	
	private TextView mapHeadtextView;
	
	private static String mapkey = "97097FA1927E5E3F1880952AE56072C3E0127B8F";
	public MapView mMapView = null;
	public BMapManager mBMapMan = null;
	public MapController mMapController;

	
	public LocationClient mLocationClient = null;
	public MyLocationListener myListener = null;
	
	private MyApplication myApplication;
	
	//OverlayTest itemOverlay;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init(mapkey, null);  
		//注意：请在setContentView前初始化BMapManager对象
		
		myApplication = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_map);
		
		mapHeadtextView = (TextView)findViewById(R.id.mapHeadtextView);
		
		mMapView=(MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		//设置启用内置的缩放控件
		mMapController=mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		this.updatePosition(point);

		mLocationClient = new LocationClient(getApplicationContext()); 
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//返回的定位结果包含地址信息
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5000);//设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);//禁止启用缓存定位
		option.setPoiNumber(5);	//最多返回POI个数	
		option.setPoiDistance(1000); //poi查询距离		
		option.setPoiExtraInfo(false); //是否需要POI的电话和地址等详细信息		
		mLocationClient.setLocOption(option);
		
		myListener = new MyLocationListener();
		myListener.setCallfuc(this);
		
		mLocationClient.registerLocationListener( myListener );
		mLocationClient.start();
		
		mapMyPositionbtn = (Button)findViewById(R.id.mapMyPositionbtn);
		mapMyPositionbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();
			}
			
		});
		
		mapCustomPositionbtn = (Button)findViewById(R.id.mapCustomPositionbtn);
		mapCustomPositionbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mapRefreshPositionbtn = (Button)findViewById(R.id.mapRefreshPositionbtn);
		mapRefreshPositionbtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
			}
			
		});
		
	}
	
	public void setMyPosition(GeoPoint point){
		
		MyLocationOverlay myLocationOverlay = new MyLocationOverlay(mMapView);
		
		LocationData locData = new LocationData();
		//手动将位置源置为天安门，在实际应用中，请使用百度定位SDK获取位置信息，要在SDK中显示一个位置，需要
		//使用百度经纬度坐标（bd09ll）
		locData.latitude = point.getLatitudeE6();
		locData.longitude = point.getLongitudeE6();
		locData.direction = 2.0f;
		
		myLocationOverlay.setData(locData);
		
		mMapView.getOverlays().add(myLocationOverlay);
		mMapView.refresh();
		mMapView.getController().animateTo(new GeoPoint((int)(locData.latitude*1e6),
		(int)(locData.longitude* 1e6)));
	}
	
	public void updatePosition(GeoPoint point) {
		mMapController.setCenter(point);//设置地图中心点
		mMapController.setZoom(12);//设置地图zoom级别
		
		setMyPosition(point);
	}
	
	protected void onDestroy(){
	        mMapView.destroy();
	        if(mBMapMan!=null){
	                mBMapMan.destroy();
	                mBMapMan=null;
	        }
	        super.onDestroy();
	}
	@Override
	protected void onPause(){
	        mMapView.onPause();
	        if(mBMapMan!=null){
	                mBMapMan.stop();
	        }
	        super.onPause();
	}
	@Override
	protected void onResume(){
	        mMapView.onResume();
	        if(mBMapMan!=null){
	                mBMapMan.start();
	        }
	        super.onResume();
	}
}
