package com.myapplicationdev.android.reminderlistex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    private ListView lvReminder;
    ArrayList<Reminder> alReminder;
    CustomAdapter aaReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvReminder = (ListView) findViewById(R.id.lv);
        alReminder = new ArrayList<Reminder>();
        aaReminder = new CustomAdapter(MainActivity.this, R.layout.activity_row, alReminder);
        lvReminder.setAdapter(aaReminder);

        DBHelper dbh = new DBHelper(MainActivity.this);
        alReminder.clear();
        alReminder.addAll(dbh.getAllReminders());
        dbh.close();

        btnAdd = (Button)findViewById(R.id.btnAdd);

        aaReminder.notifyDataSetChanged();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        SecondActivity.class);
                int requestCode = 1;
                i.putExtra("requestCode", requestCode);
                startActivityForResult(i, requestCode);
            }
        });

        lvReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,
                        ThirdActivity.class);
                Reminder item = alReminder.get(position);
                int requestCode = 2;
                i.putExtra("item", item);
                startActivityForResult(i, requestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1){
            DBHelper dbh = new DBHelper(MainActivity.this);
            alReminder.clear();
            alReminder.addAll(dbh.getAllReminders());
            dbh.close();

            aaReminder.notifyDataSetChanged();
        } else if (resultCode == RESULT_OK && requestCode == 2){
            DBHelper dbh = new DBHelper(MainActivity.this);
            alReminder.clear();
            alReminder.addAll(dbh.getAllReminders());
            dbh.close();

            aaReminder.notifyDataSetChanged();
        }
    }

}
