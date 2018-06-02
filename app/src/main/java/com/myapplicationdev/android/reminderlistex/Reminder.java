package com.myapplicationdev.android.reminderlistex;


import java.io.Serializable;

public class Reminder implements Serializable {
    private int id;
    private String title;
    private String desc;
    private String date;
    private String time;
    private String alarmdate;
    private String alarmtime;

    public Reminder(int id, String title, String desc, String date, String time, String alarmdate, String alarmtime) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.time = time;
        this.alarmdate = alarmdate;
        this.alarmtime = alarmtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAlarmdate() {
        return alarmdate;
    }

    public void setAlarmdate(String alarmdate) {
        this.alarmdate = alarmdate;
    }

    public String getAlarmtime() {
        return alarmtime;
    }

    public void setAlarmtime(String alarmtime) {
        this.alarmtime = alarmtime;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", alarmdate='" + alarmdate + '\'' +
                ", alarmtime='" + alarmtime + '\'' +
                '}';
    }
}
