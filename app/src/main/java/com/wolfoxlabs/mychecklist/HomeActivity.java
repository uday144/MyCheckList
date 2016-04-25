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
    private RemindersDbAdapter mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);
        mDbHelper = new RemindersDbAdapter(this);
        db = new DatabaseHelper(getApplicationContext());
        mDbHelper.open();
        fillData();
        CalendarUtils calendarUtils = new CalendarUtils();
        today = calendarUtils.getToday();
        setupUI();
        GridView gridView = (GridView)findViewById(R.id.gridview);
        prepareData();
        gridView.setAdapter(new MyAdapter(this,mItems));
        DBoperrations();

}
    private void fillData() {
//        Cursor remindersCursor = mDbHelper.fetchAllReminders();
//        startManagingCursor(remindersCursor);
//
//        // Create an array to specify the fields we want to display in the list (only TITLE)
//        String[] from = new String[]{RemindersDbAdapter.KEY_TITLE};
//
//        // and an array of the fields we want to bind those fields to (in this case just text1)
//        int[] to = new int[]{R.id.text1};
//
//        // Now create a simple cursor adapter and set it to display
//        SimpleCursorAdapter reminders =
//                new SimpleCursorAdapter(this, R.layout.reminder_row, remindersCursor, from, to);
//        setListAdapter(reminders);
    }




    public void createReminder() {
        Intent i = new Intent(this, ReminderEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }

//    @Override
//    protected void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        Intent i = new Intent(this, ReminderEditActivity.class);
//        i.putExtra(RemindersDbAdapter.KEY_ROWID, id);
//        startActivityForResult(i, ACTIVITY_EDIT);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    int k;
    private void prepareData() {
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

        });

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
        Tag tag1 = new Tag("Shopping");
        Tag tag2 = new Tag("Important");
        Tag tag3 = new Tag("Watchlist");
        Tag tag4 = new Tag("Androidhive");

        // Inserting tags in db
        long tag1_id = db.createTag(tag1);
        long tag2_id = db.createTag(tag2);
        long tag3_id = db.createTag(tag3);
        long tag4_id = db.createTag(tag4);

        Log.d("Tag Count", "Tag Count: " + db.getAllTags().size());

        // Creating ToDos
        Todo todo1 = new Todo("iPhone 5S", 0);
        Todo todo2 = new Todo("Galaxy Note II", 0);
        Todo todo3 = new Todo("Whiteboard", 0);

        Todo todo4 = new Todo("Riddick", 0);
        Todo todo5 = new Todo("Prisoners", 0);
        Todo todo6 = new Todo("The Croods", 0);
        Todo todo7 = new Todo("Insidious: Chapter 2", 0);

        Todo todo8 = new Todo("Don't forget to call MOM", 0);
        Todo todo9 = new Todo("Collect money from John", 0);

        Todo todo10 = new Todo("Post new Article", 0);
        Todo todo11 = new Todo("Take database backup", 0);

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
        long todo8_id = db.createToDo(todo8, new long[] { tag2_id });
        long todo9_id = db.createToDo(todo9, new long[] { tag2_id });

        // Inserting todos under "Androidhive" Tag
        long todo10_id = db.createToDo(todo10, new long[] { tag4_id });
        long todo11_id = db.createToDo(todo11, new long[] { tag4_id });

        Log.e("Todo Count", "Todo count: " + db.getToDoCount());

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        db.createTodoTag(todo10_id, tag2_id);

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

        db.deleteToDo(todo8_id);

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
