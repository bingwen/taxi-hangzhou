package com.bingwenshi.hangzhou_taxi;

import java.util.HashMap;

import com.bingwenshi.hangzhou_taxi.R.id;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class InfoActivity extends Activity implements dataListenerActivity{
	
	private Button applyButton;
	private Button backButton;
	
	private EditText nameEditText;
	private EditText phoneEditText;
	private EditText passwordeEditText;
	private EditText careNumeEditText;
	private EditText peopleEditText;
	private EditText serveNumEditText;

	private ProgressBar infoProgressBar;
	
	private MyApplication myapplication;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.info);
		
		myapplication = (MyApplication)getApplication();
		
		myapplication._dataListenerActivity = this;
		
		nameEditText = (EditText)findViewById(R.id.nameInput);
		phoneEditText = (EditText)findViewById(R.id.phoneNumInput);
		passwordeEditText = (EditText)findViewById(R.id.passwordInput);
		careNumeEditText = (EditText)findViewById(R.id.carNumInput);
		peopleEditText = (EditText)findViewById(R.id.peopleNumInput);
		serveNumEditText = (EditText)findViewById(R.id.serveNumInput);
		
		nameEditText.setText("张芳芳2");
		phoneEditText.setText("test123");
		passwordeEditText.setText("13312345679");
		careNumeEditText.setText("A12345");
		peopleEditText.setText("123456789012345678");
		serveNumEditText.setText("420733198407238712");
		
		
		infoProgressBar = (ProgressBar)findViewById(R.id.infoProgressBar);
		applyButton = (Button)findViewById(R.id.buttonApply);
		if (myapplication.userState == MyApplication.USER_STATE_LOGIN) {
			applyButton.setText("修改");
		}
		else {
			applyButton.setText("注册");
		}
		applyButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//infoProgressBar.setIndeterminate(true);
				//infoProgressBar.setVisibility(View.VISIBLE);
				
				String username = nameEditText.getText().toString();
				String password = passwordeEditText.getText().toString();
				String phoneString = phoneEditText.getText().toString();
				String carNum = careNumeEditText.getText().toString();
				String server = serveNumEditText.getText().toString();
				String peopleNum = peopleEditText.getText().toString();
				
				byte[] sendBytes = myapplication.sendMsgCreater.getRegisterBytes(username, password, phoneString, carNum, server, peopleNum);
				myapplication.testSocketConnect();
				myapplication.connectSendList.add(sendBytes);
				
			}
		});
		backButton = (Button)findViewById(R.id.buttonBack);
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
	}

	@Override
	public void listenerUpdate() {
		// TODO Auto-generated method stub
		for (HashMap<String, Object> resultMap : myapplication.gotMsgList) {
			if (((Integer)resultMap.get("msgMode")).intValue() == MyApplication.MSG_MODE_REGISTER) {
				int state = ((Integer)resultMap.get("state")).intValue();
				
				if (state == 0) {
					myapplication.userState = MyApplication.USER_STATE_LOGIN;
					getMainView();
				}
				else {
					 AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setMessage("账号已存在，或手机号是省外号码，请重新输入");
						 builder.setTitle("注册失败");
						 
						 builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						  builder.create().show();
				}

				  break;
			}
		}

	}
	public void getMainView() {
		((MyApplication)getApplication()).userState = MyApplication.USER_STATE_LOGIN;
		Intent intent = new Intent(this, MainActivity.class);
		 this.startActivity(intent);
	}
}
