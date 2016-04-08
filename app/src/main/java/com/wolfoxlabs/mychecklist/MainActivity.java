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
        prepareData();
        gridView.setAdapter(new MyAdapter(this,mItems));

}

    private void prepareData() {
        for (int i = 0; i < 24 ; i++) {
            for(int j = 0; j < 5 ; j++) {
                String time = String.format("%02d", i)+":"+String.format("%02d", j*15);
                if(i%2==0){
                switch (j) {
                    case 0:
                        mItems.add(new Item(time, R.color.Blue1));
                        break;
                    case 1:
                        mItems.add(new Item(time, R.color.Blue2));
                        break;
                    case 2:
                        mItems.add(new Item(time, R.color.Blue3));
                        break;
                    case 3:
                        mItems.add(new Item(time, R.color.Blue4));
                        break;
                    case 4:
                        mItems.add(new Item(time, R.color.Blue1));
                        break;
                    default:
                        break;

                }
                }
                else
                {
                    switch (j) {
                        case 0:
                            mItems.add(new Item(time, R.color.Blue2));
                            break;
                        case 1:
                            mItems.add(new Item(time, R.color.Blue3));
                            break;
                        case 2:
                            mItems.add(new Item(time, R.color.Blue4));
                            break;
                        case 3:
                            mItems.add(new Item(time, R.color.Blue1));
                            break;
                        case 4:
                            mItems.add(new Item(time, R.color.Blue2));
                            break;
                        default:
                            break;

                    }
                }

            }
        }
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
