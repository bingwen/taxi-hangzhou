package com.bingwenshi.hangzhou_taxi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InfoActivity extends Activity {
	
	private Button applyButton;
	private Button backButton;
	
	private EditText namEditText;
	private EditText phoneEditText;
	private EditText passwordeEditText;
	private EditText careNumeEditText;
	private EditText peopleEditText;
	private EditText serveNumEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//»•µÙ±ÍÃ‚¿∏
		setContentView(R.layout.info);
		
		applyButton = (Button)findViewById(R.id.buttonApply);
		backButton = (Button)findViewById(R.id.buttonBack);
		backButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		namEditText = (EditText)findViewById(R.id.nameInput);
		phoneEditText = (EditText)findViewById(R.id.phoneNumInput);
		passwordeEditText = (EditText)findViewById(R.id.passwordInput);
		careNumeEditText = (EditText)findViewById(R.id.carNumInput);
		peopleEditText = (EditText)findViewById(R.id.peopleNumInput);
		serveNumEditText = (EditText)findViewById(R.id.serveNumInput);
	}
}
