package com.wolfoxlabs.mychecklist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wolfoxlabs.mychecklist.helper.DatabaseHelper;
import com.wolfoxlabs.mychecklist.model.Todo;
import com.wolfoxlabs.mychecklist.model.Tag;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends Activity implements DatePickerDialog.OnDateSetListener {
    private Date today;
    private final List<Item> mItems = new ArrayList<Item>();
    Button calendarDate;
    Calendar now;
    String[] strDays = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thusday",
            "Friday", "Saturday" };
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    // Database Helper
    DatabaseHelper db;

    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);

        db = new DatabaseHelper(getApplicationContext());

        CalendarUtils calendarUtils = new CalendarUtils();
        today = calendarUtils.getToday();
        setupUI();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        prepareEmptyGrid();
        myAdapter = new MyAdapter(this,mItems);
        gridView.setAdapter(myAdapter);
        DBoperrations();

}





    public void createReminder() {
        Intent i = new Intent(this, ReminderEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
    public void showReminder() {
        Intent i = new Intent(this, ReminderListActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        myAdapter.fillData(103);
    }
    int k;
    private void prepareEmptyGrid() {
        for (int i = 0; i < 24 ; i++) {
            for(int j = 0; j < 5 ; j++) {

                if(i==13)
                    k = 1;
                String time = String.format("%02d", k)+":"+String.format("%02d", (j-1)*15);

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
            k++;
        }
    }

    private void setupUI() {

        // Formating the date
        now = Calendar.getInstance();
        String format = dateFormat.format(today);

        // Top title
        calendarDate = (Button) findViewById(R.id.top_done_button);
        calendarDate.setText(strDays[now.get(Calendar.DAY_OF_WEEK) - 1]+", "+format+", 5 Tasks");
// Show a datepicker when the dateButton is clicked
        calendarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showDatePicker();
                showReminder();
            }

        });

    }

    private void showDatePicker() {
        now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                HomeActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setThemeDark(false);
        dpd.dismissOnPause(true);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    public void changeAdaptor()
    {

    }

  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String dateStr = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        Date date = null;
      try {
          date = dateFormat.parse(dateStr);
      }
      catch (ParseException pe)
      {

      }
        now.setTime(date);
        calendarDate.setText(strDays[now.get(Calendar.DAY_OF_WEEK) - 1]+", "+dateStr+", 5 Tasks");
    }

    private void DBoperrations()
    {
        // Creating tags
        Tag tag1 = new Tag("Writer");
        Tag tag2 = new Tag("Reader");
        Tag tag3 = new Tag("Watchlist");
        Tag tag4 = new Tag("Coding");
        Tag tag5 = new Tag("Meditation");
        // Inserting tags in db
        long tag1_id = db.createTag(tag1);
        long tag2_id = db.createTag(tag2);
        long tag3_id = db.createTag(tag3);
        long tag4_id = db.createTag(tag4);
        long tag5_id = db.createTag(tag5);
        Log.d("Tag Count", "Tag Count: " + db.getAllTags().size());

        // Creating ToDos
        Todo todo1 = new Todo("Write Blog", 0);
        Todo todo2 = new Todo("Write on quora", 0);


        Todo todo3 = new Todo("TED", 0);
        Todo todo4 = new Todo("YouTube: Gradle", 0);
        Todo todo9 = new Todo("YouTube: Standups", 0);

        Todo todo5 = new Todo("Read Coding Horror", 0);
        Todo todo10 = new Todo("Read https://rominirani.com/2014/08/19/gradle-tutorial-part-6-android-studio-gradle/", 0);

        Todo todo8 = new Todo("Read Code complete", 0);
        Todo todo6 = new Todo("Read on quora", 0);

        Todo todo7 = new Todo("CODE: Algo", 0);


        // Inserting todos in db
        // Inserting todos under "Shopping" Tag
        long todo1_id = db.createToDo(todo1, new long[] { tag1_id });
        long todo2_id = db.createToDo(todo2, new long[] { tag1_id });
        long todo3_id = db.createToDo(todo3, new long[] { tag1_id });

        // Inserting todos under "Watchlist" Tag
        long todo4_id = db.createToDo(todo4, new long[] { tag3_id });
        long todo5_id = db.createToDo(todo5, new long[] { tag3_id });
        long todo6_id = db.createToDo(todo6, new long[] { tag3_id });
        long todo7_id = db.createToDo(todo7, new long[] { tag3_id });

        // Inserting todos under "Important" Tag

        long todo9_id = db.createToDo(todo9, new long[] { tag2_id });


        Log.e("Todo Count", "Todo count: " + db.getToDoCount());

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        //db.createTodoTag(todo10_id, tag2_id);

        // Getting all tag names
        Log.d("Get Tags", "Getting All Tags");

        List<Tag> allTags = db.getAllTags();
        for (Tag tag : allTags) {
            Log.d("Tag Name", tag.getTagName());
        }

        // Getting all Todos
        Log.d("Get Todos", "Getting All ToDos");

        List<Todo> allToDos = db.getAllToDos();
        for (Todo todo : allToDos) {
            Log.d("ToDo", todo.getNote());
        }

        // Getting todos under "Watchlist" tag name
        Log.d("ToDo", "Get todos under single Tag name");

        List<Todo> tagsWatchList = db.getAllToDosByTag(tag3.getTagName());
        for (Todo todo : tagsWatchList) {
            Log.d("ToDo Watchlist", todo.getNote());
        }

        // Deleting a ToDo
        Log.d("Delete ToDo", "Deleting a Todo");
        Log.d("Tag Count", "Tag Count Before Deleting: " + db.getToDoCount());

        db.deleteToDo(todo1_id);

        Log.d("Tag Count", "Tag Count After Deleting: " + db.getToDoCount());

        // Deleting all Todos under "Shopping" tag
        Log.d("Tag Count",
                "Tag Count Before Deleting 'Shopping' Todos: "
                        + db.getToDoCount());

        db.deleteTag(tag1, true);

        Log.d("Tag Count",
                "Tag Count After Deleting 'Shopping' Todos: "
                        + db.getToDoCount());

        // Updating tag name
        tag3.setTagName("Movies to watch");
        db.updateTag(tag3);

        // Don't forget to close database connection
        db.closeDB();
    }
}
