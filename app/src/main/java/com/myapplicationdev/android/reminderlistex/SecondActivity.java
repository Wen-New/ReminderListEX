package com.myapplicationdev.android.reminderlistex;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    Intent i = getIntent();

    EditText etTitle, etDesc;
    Button btnDate, btnTime, btnAlarmDate, btnAlarmTime, btnAdd;

    int rday, rmonth, ryear, rhour, rminute, cday, cmonth, cyear, chour, cminute;
    String title, desc, date, time, alarmdate, alarmtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etTitle = (EditText)findViewById(R.id.etTitle);
        etDesc = (EditText)findViewById(R.id.etDesc);
        btnDate = (Button)findViewById(R.id.btnDate);
        btnTime = (Button)findViewById(R.id.btnTime);
        btnAlarmDate = (Button)findViewById(R.id.btnAlarmDate);
        btnAlarmTime = (Button)findViewById(R.id.btnAlarmTime);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur = Calendar.getInstance();
                int dayOfMonth = cur.get(Calendar.DAY_OF_MONTH);
                int monthOfYear = cur.get(Calendar.MONTH);
                int year = cur.get(Calendar.YEAR);

                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cday = dayOfMonth;
                        cmonth = monthOfYear + 1;
                        cyear = year;
                        date = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        btnDate.setText(date);
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(SecondActivity.this, myDateListener, year, monthOfYear, dayOfMonth);

                myDateDialog.show();
            }
        });

        btnAlarmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur = Calendar.getInstance();
                int dayOfMonth = cur.get(Calendar.DAY_OF_MONTH);
                int monthOfYear = cur.get(Calendar.MONTH);
                int year = cur.get(Calendar.YEAR);

                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        rday = dayOfMonth;
                        rmonth = monthOfYear+1;
                        ryear = year;
                        alarmdate = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        btnAlarmDate.setText(alarmdate);
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(SecondActivity.this, myDateListener, year, monthOfYear, dayOfMonth);

                myDateDialog.show();
            }
        });

        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur = Calendar.getInstance();
                int hour = cur.get(Calendar.HOUR_OF_DAY);
                int minute = cur.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        chour = hour;
                        cminute = minute;
                        time = String.format("%02d:%02d", hour, minute);
                        btnTime.setText(time);
                    }
                };
                TimePickerDialog timeDialog = new TimePickerDialog(SecondActivity.this, myTimeListener, hour, minute, true);
                timeDialog.show();
            }
        });

        btnAlarmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cur = Calendar.getInstance();
                int hour = cur.get(Calendar.HOUR_OF_DAY);
                int minute = cur.get(Calendar.MINUTE);

                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        rhour = hour;
                        rminute = minute;
                        alarmtime = String.format("%02d:%02d", hour, minute);
                        btnAlarmTime.setText(alarmtime);
                        ;
                    }
                };
                TimePickerDialog timeDialog = new TimePickerDialog(SecondActivity.this, myTimeListener, hour, minute, true);
                timeDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                desc = etDesc.getText().toString();
                DBHelper dbh = new DBHelper(SecondActivity.this);
                long row_affected = dbh.insertReminder(title, desc, date, time, alarmdate, alarmtime);
                dbh.close();

                if (row_affected != -1){
                    Toast.makeText(SecondActivity.this, "Added event successfully",
                            Toast.LENGTH_SHORT).show();
                }

                //launch notification here
                int remind = ((rday - cday)*24*60*60) + ((rhour - chour)*60*60) + ((rminute - cminute)*60);

                Calendar cal = Calendar.getInstance();
                /*cal.add(Calendar.DAY_OF_MONTH, (rday - cday));
                cal.add(Calendar.MONTH, (rmonth - cmonth));
                cal.add(Calendar.YEAR, (ryear - cyear));
                cal.add(Calendar.HOUR_OF_DAY, (rhour - chour));*/
                cal.add(Calendar.SECOND, remind);
                Log.i("seconds", remind+"");

                Intent intent = new Intent(SecondActivity.this,
                        ReceiveReminder.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        SecondActivity.this, 1,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
