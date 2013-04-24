package com.bingwenshi.hangzhou_taxi;

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
				//listenerUpdate();
				//getMainView();
				myApplication.getMsg();
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
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// builder.setMessage("请检查您的用户名和密码");
		 //builder.setTitle("登录失败");
		 
		 builder.setTitle("title");
		 builder.setMessage(myApplication.testMsg);
		 
		 builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		 

		  builder.create().show();
		
	}
}
