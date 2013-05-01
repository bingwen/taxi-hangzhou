package com.bingwenshi.hangzhou_taxi;

import java.util.Calendar;
import java.util.Date;

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MsgDateChooseActivity extends Activity {
	
    private EditText startDateInput; 
    private EditText endDateInput; 
    
    private Calendar startDate;
    private Calendar endDate;
    
    int mYear;   
    int mMonth;   
    int mDay; 
    
    private MyApplication myApplication;
  
    static final int START_DATE_DIALOG_ID = 0;   
    static final int END_DATE_DIALOG_ID = 1;   
    private DatePickerDialog.OnDateSetListener startDateListener =   
            new DatePickerDialog.OnDateSetListener() {   
        	@Override
                public void onDateSet(DatePicker view, int year,    
                                      int monthOfYear, int dayOfMonth) {   
                    startDate.set(year, monthOfYear, dayOfMonth);
                    myApplication.startDate = startDate;
                    
                    startDateInput.setText(   
                            new StringBuilder()
                                    .append(monthOfYear + 1).append("-")   
                                    .append(dayOfMonth).append("-")   
                                    .append(year).append(" ")); 
                }

    			
        };   
        private DatePickerDialog.OnDateSetListener endDateListener =   
                new DatePickerDialog.OnDateSetListener() {   
            	@Override
                    public void onDateSet(DatePicker view, int year,    
                                          int monthOfYear, int dayOfMonth) {   
            		endDate.set(year, monthOfYear, dayOfMonth);
                    myApplication.endDate = endDate;
                        endDateInput.setText(   
                                new StringBuilder()
                                        .append(monthOfYear + 1).append("-")   
                                        .append(dayOfMonth).append("-")   
                                        .append(year).append(" ")); 
                    }

        			
            };
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.date_choose);
		
		myApplication = (MyApplication)getApplication();
  
		Button buttonDateChooseBack = (Button)findViewById(R.id.buttonDateChooseBack);
		buttonDateChooseBack.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
			
		});
		
		
		Button buttonDateChooseApply = (Button)findViewById(R.id.buttonDateChooseApply);
		buttonDateChooseApply.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub				
				setResult(2);
				finish();
			}
		});
		
		startDateInput = (EditText) findViewById(R.id.loginPasswordInput); 
		endDateInput = (EditText) findViewById(R.id.endDateInput); 
		
		startDateInput.setOnClickListener(new View.OnClickListener() {   
            public void onClick(View v) {   
            	showDialog(START_DATE_DIALOG_ID);  
            }
        }); 
		
		endDateInput.setOnClickListener(new View.OnClickListener() {   
            public void onClick(View v) {   
            	showDialog(END_DATE_DIALOG_ID);  
            }
        }); 
  
        //获得当前时间
        Calendar c = Calendar.getInstance();   
        startDate = endDate = c;
        
        int mYear = c.get(Calendar.YEAR);   
        int mMonth = c.get(Calendar.MONTH);   
        int mDay = c.get(Calendar.DAY_OF_MONTH);   
        
        startDateInput.setText(   
                new StringBuilder()
                        .append(mMonth + 1).append("-")   
                        .append(mDay).append("-")   
                        .append(mYear).append(" ")); 
        endDateInput.setText(   
                new StringBuilder()
                        .append(mMonth + 1).append("-")   
                        .append(mDay).append("-")   
                        .append(mYear).append(" ")); 
    }   
       
    @Override  
    protected Dialog onCreateDialog(int id) {   
        switch (id) {   
        	case START_DATE_DIALOG_ID:   
        		return new DatePickerDialog(this,   
            		startDateListener,   
                        mYear, mMonth, mDay);   
        	case END_DATE_DIALOG_ID:   
        		return new DatePickerDialog(this,   
        				endDateListener,   
                        mYear, mMonth, mDay); 
        }   
        return null;   
    }   
       
  
 
   
       
}  
