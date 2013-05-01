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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		mBMapMan=new BMapManager(getApplication());
		mBMapMan.init(mapkey, null);  
		//ע�⣺����setContentViewǰ��ʼ��BMapManager����
		
		myApplication = (MyApplication)getApplication();
		
		setContentView(R.layout.activity_map);
		
		mapHeadtextView = (TextView)findViewById(R.id.mapHeadtextView);
		
		mMapView=(MapView)findViewById(R.id.bmapView);
		mMapView.setBuiltInZoomControls(true);
		//�����������õ����ſؼ�
		mMapController=mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		
		GeoPoint point =new GeoPoint((int)(39.915* 1E6),(int)(116.404* 1E6));
		this.updatePosition(point);

		mLocationClient = new LocationClient(getApplicationContext()); 
		
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");//���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");//���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(5000);//���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);//��ֹ���û��涨λ
		option.setPoiNumber(5);	//��෵��POI����	
		option.setPoiDistance(1000); //poi��ѯ����		
		option.setPoiExtraInfo(false); //�Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ		
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
		//�ֶ���λ��Դ��Ϊ�찲�ţ���ʵ��Ӧ���У���ʹ�ðٶȶ�λSDK��ȡλ����Ϣ��Ҫ��SDK����ʾһ��λ�ã���Ҫ
		//ʹ�ðٶȾ�γ�����꣨bd09ll��
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
		mMapController.setCenter(point);//���õ�ͼ���ĵ�
		mMapController.setZoom(12);//���õ�ͼzoom����
		
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
