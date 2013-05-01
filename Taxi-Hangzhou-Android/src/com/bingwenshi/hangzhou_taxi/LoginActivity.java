package com.bingwenshi.hangzhou_taxi;

import java.util.HashMap;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class LoginActivity extends Activity implements dataListenerActivity{
	
	private Button registerButton;
	private Button loginButton;
	
	private ProgressBar loginProgressBar;
	
	private EditText phoneEditText;
	private EditText passwordEditText;
	
	private MyApplication myApplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		myApplication = (MyApplication)getApplication();
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.activity_login);
		
		myApplication._dataListenerActivity = this;
		
		registerButton = (Button)findViewById(R.id.registerbtn);
		loginButton = (Button)findViewById(R.id.loginbtn);
		
		loginProgressBar = (ProgressBar)findViewById(R.id.loginProgressBar);
		phoneEditText = (EditText)findViewById(R.id.loginPhoneInput);
		passwordEditText = (EditText)findViewById(R.id.loginPasswordInput);
		
		phoneEditText.setText("13312345678");
		passwordEditText.setText("test123");
		
		registerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myApplication.userState = MyApplication.USER_STATE_WAITLOG;
				Intent intent = new Intent(arg0.getContext(), InfoActivity.class);
				 arg0.getContext().startActivity(intent);
			}
		});
		
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				loginProgressBar.setIndeterminate(true);
				loginProgressBar.setVisibility(View.VISIBLE);
				
				String username = phoneEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				
				byte[] sendBytes = myApplication.sendMsgCreater.getLoginBytes(username, password);
				myApplication.connectSendList.add(sendBytes);
				//getMainView();
			}
		});
		
		
	}
	
	public void getMainView() {
		((MyApplication)getApplication()).userState = MyApplication.USER_STATE_LOGIN;
		Intent intent = new Intent(this, MainActivity.class);
		 this.startActivity(intent);
	}

	@Override
	public void listenerUpdate() {
		// TODO Auto-generated method stub
		for (HashMap<String, Object> resultMap : myApplication.gotMsgList) {
			if (((Integer)resultMap.get("msgMode")).intValue() == MyApplication.MSG_MODE_LOGIN) {
				int state = ((Integer)resultMap.get("state")).intValue();
				
				if (state == 0) {
					myApplication.userState = MyApplication.USER_STATE_LOGIN;
					try {
						myApplication.userNameString = (String)resultMap.get("username");
						myApplication.userPhone = (String)resultMap.get("phone");
						myApplication.userCar = (String)resultMap.get("carnum");
						myApplication.userService = (String)resultMap.get("servicenum");
						
					} catch (Exception e) {
						// TODO: handle exception
					}
					loginProgressBar.setIndeterminate(false);
					loginProgressBar.setVisibility(View.INVISIBLE);
					getMainView();
				}
				else {
					 AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage("请检查您的用户名和密码");
						 builder.setTitle("登录失败");
						 
						 builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						 loginProgressBar.setIndeterminate(false);
						loginProgressBar.setVisibility(View.INVISIBLE);
						  builder.create().show();
				}
				myApplication.gotMsgList.remove(resultMap);
				  break;
			}
		}
		
	}
}
