package com.myapplicationdev.android.reminderlistex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Reminder> reminders;

    public CustomAdapter(Context context, int resource,ArrayList<Reminder> reminders) {
        super(context, resource, reminders);
        this.context = context;
        this.resource = resource;
        this.reminders= reminders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resource, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.textViewTitle);
        TextView tvDescription = (TextView) rowView.findViewById(R.id.textViewDesc);
        TextView tvDateTime = (TextView) rowView.findViewById(R.id.textViewDateTimeStated);

        Reminder currentItem = reminders.get(position);

        if (currentItem == null) {
            tvTitle.setText("");
            tvDescription.setText("");
            tvDateTime.setText("");
        } else {
            String title = "Title: " + currentItem.getTitle();
            String desc = "Description: " + currentItem.getDesc();
            String datetime = "Deadline: " + currentItem.getDate() + " " + currentItem.getTime() + " h";
            tvTitle.setText(title);
            tvDescription.setText(desc);
            tvDateTime.setText(datetime);
        }
        return rowView;
    }
}
