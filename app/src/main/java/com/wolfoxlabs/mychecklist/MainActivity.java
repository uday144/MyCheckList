package com.wolfoxlabs.mychecklist;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private Date today;
    private final List<Item> mItems = new ArrayList<Item>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);
        CalendarUtils calendarUtils = new CalendarUtils();
        today = calendarUtils.getToday();
        setupUI();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        for (int i = 0; i < 30 ; i++) {
            mItems.add(new Item("Yel",   R.color.Blue2));
            mItems.add(new Item("Red",       R.color.Blue3));
            mItems.add(new Item("Gre",   R.color.Blue4));
            mItems.add(new Item("Red",       R.color.Blue1));
        }
        gridView.setAdapter(new MyAdapter(this,mItems));

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
