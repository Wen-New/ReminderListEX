package com.myapplicationdev.android.reminderlistex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminder.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_REMINDER = "reminder";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESC = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_ALARMDATE = "alarmdate";
    private static final String COLUMN_ALARMTIME = "alarmtime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_REMINDER + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESC + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_ALARMDATE + " TEXT, "
                + COLUMN_ALARMTIME + " TEXT ) ";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        // Create table(s) again
        onCreate(db);
    }

    public long insertReminder(String title, String desc, String date, String time, String alarmdate, String alarmtime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME, time);
        values.put(COLUMN_ALARMDATE, alarmdate);
        values.put(COLUMN_ALARMTIME, alarmtime);
        long result = db.insert(TABLE_REMINDER, null, values);
        if (result == -1){
            Log.d("DBHelper", "Insert failed");
        }
        db.close();
        Log.d("SQL Insert ",""+ result); //id returned, shouldn’t be -1
        return result;
    }

//    public ArrayList<String> getAllReminders() {
//        ArrayList<String> reminders = new ArrayList<String>();
//
//        String selectQuery = "SELECT " + COLUMN_ID + ","
//                + COLUMN_TITLE + ","
//                + COLUMN_DESC + ","
//                + COLUMN_DATE + ","
//                + COLUMN_TIME + ","
//                + COLUMN_REMIND + " FROM " + TABLE_REMINDER;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String title = cursor.getString(1);
//                String desc = cursor.getString(2);
//                String date = cursor.getString(3);
//                String time = cursor.getString(4);
//                String remind = cursor.getString(5);
//                reminders.add("ID:" + id + ", " + title + ", " + desc + ", " + date + ", " + time + ", " + remind);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return reminders;
//    }

    public int updateReminder(Reminder data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_DESC, data.getDesc());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_TIME, data.getTime());
        values.put(COLUMN_ALARMDATE, data.getAlarmdate());
        values.put(COLUMN_ALARMTIME, data.getAlarmtime());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        Log.i("DBHelper",data.toString());
        int result = db.update(TABLE_REMINDER, values, condition, args);
        db.close();
        return result;
    }

    public int deleteReminder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_REMINDER, condition, args);
        db.close();
        return result;
    }

    public ArrayList<Reminder> getAllReminders() {
        ArrayList<Reminder> reminders = new ArrayList<Reminder>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_TITLE, COLUMN_DESC, COLUMN_DATE, COLUMN_TIME, COLUMN_ALARMDATE, COLUMN_ALARMTIME};
        Cursor cursor = db.query(TABLE_REMINDER, columns, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String desc = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                String alarmdate = cursor.getString(5);
                String alarmtime = cursor.getString(6);
                Reminder reminder = new Reminder(id, title, desc, date, time, alarmdate, alarmtime);
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return reminders;
    }

}
