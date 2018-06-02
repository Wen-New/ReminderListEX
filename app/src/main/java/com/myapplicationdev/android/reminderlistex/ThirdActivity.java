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

public class ThirdActivity extends AppCompatActivity {

    Intent i;
    Reminder reminder;

    EditText etTitle, etDesc;
    Button btnDate, btnTime, btnAlarmDate, btnAlarmTime, btnEdit, btnDelete;

    int rday, rmonth, ryear, rhour, rminute, id, cday, cmonth, cyear, chour, cminute;
    String title, desc, date, time, alarmdate, alarmtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        i = getIntent();
        reminder = (Reminder)i.getSerializableExtra("item");

        etTitle = (EditText)findViewById(R.id.etTitle2);
        etDesc = (EditText)findViewById(R.id.etDesc2);
        btnDate = (Button)findViewById(R.id.btnDate);
        btnTime = (Button)findViewById(R.id.btnTime);
        btnAlarmDate = (Button)findViewById(R.id.btnAlarmDate2);
        btnAlarmTime = (Button)findViewById(R.id.btnAlarmTime2);
        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        id = reminder.getId();
        title = reminder.getTitle();
        desc = reminder.getDesc();
        date = reminder.getDate();
        time = reminder.getTime();
        alarmdate = reminder.getAlarmdate();
        alarmtime = reminder.getAlarmtime();
        etTitle.setText(title);
        etDesc.setText(desc);
        btnDate.setText(date);
        btnTime.setText(time);
        btnAlarmDate.setText(alarmdate);
        btnAlarmTime.setText(alarmtime);

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
                        cmonth = monthOfYear;
                        cyear = year;
                        date = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
                        btnDate.setText(date);
                    }
                };

                DatePickerDialog myDateDialog = new DatePickerDialog(ThirdActivity.this, myDateListener, year, monthOfYear, dayOfMonth);

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

                DatePickerDialog myDateDialog = new DatePickerDialog(ThirdActivity.this, myDateListener, year, monthOfYear, dayOfMonth);

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
                TimePickerDialog timeDialog = new TimePickerDialog(ThirdActivity.this, myTimeListener, hour, minute, true);
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
                TimePickerDialog timeDialog = new TimePickerDialog(ThirdActivity.this, myTimeListener, hour, minute, true);
                timeDialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = etTitle.getText().toString();
                desc = etDesc.getText().toString();
                date = btnDate.getText().toString();
                time = btnTime.getText().toString();
                alarmdate = btnAlarmDate.getText().toString();
                alarmtime = btnAlarmTime.getText().toString();
                reminder.setTitle(title);
                reminder.setDesc(desc);
                reminder.setDate(date);
                reminder.setTime(time);
                reminder.setAlarmdate(alarmdate);
                reminder.setAlarmtime(alarmtime);
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                long row_affected = dbh.updateReminder(reminder);
                dbh.close();

                if (row_affected == 1){
                    Toast.makeText(ThirdActivity.this, "Updated event successfully",
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

                Intent intent = new Intent(ThirdActivity.this,
                        ReceiveReminder.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        ThirdActivity.this, 1,
                        intent, PendingIntent.FLAG_CANCEL_CURRENT);

                AlarmManager am = (AlarmManager)
                        getSystemService(Activity.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),
                        pendingIntent);

                setResult(RESULT_OK);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                long row_affected = dbh.deleteReminder(id);
                dbh.close();
                setResult(RESULT_OK);
                finish();
            }
        });
    }
}
