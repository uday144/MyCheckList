package com.wolfoxlabs.mychecklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    private Date today;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);
        CalendarUtils calendarUtils = new CalendarUtils();
        today = calendarUtils.getToday();
        setupUI();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));

}
    private void setupUI() {
        // Set the Top title
        TextView tv = (TextView) findViewById(R.id.top_title);
        tv.setText("Today");

        // Formating the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd/MM/yyyy");
        String format = dateFormat.format(today);

        // Top title
        TextView calendarDate = (TextView) findViewById(R.id.calendar_date);
        calendarDate.setText(format);


    }

}
