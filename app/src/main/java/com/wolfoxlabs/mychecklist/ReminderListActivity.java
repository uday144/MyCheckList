
package com.wolfoxlabs.mychecklist;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.wolfoxlabs.mychecklist.helper.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ReminderListActivity extends ListActivity {
    private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private DatabaseHelper  mDbHelper;
    private static final String TAG = "ExampleQuizApp";
    private static final String QUIZ_JSON_FILE = "QA.json";
    private int mQuestionIndex = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reminder_list);
        mDbHelper = new DatabaseHelper(this);

        fillData();
        registerForContextMenu(getListView());

    }
    

	private void fillData() {
        Cursor remindersCursor = mDbHelper.fetchAllReminders();
        startManagingCursor(remindersCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DatabaseHelper.KEY_TITLE};
        
        // and an array of the fields we want to bind those fields to (in this case just text1)
        int[] to = new int[]{R.id.text1};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter reminders = 
        	    new SimpleCursorAdapter(this, R.layout.reminder_row, remindersCursor, from, to);
        setListAdapter(reminders);
    }
    

	

    private void createReminder() {
        Intent i = new Intent(this, ReminderEditActivity.class);
        startActivityForResult(i, ACTIVITY_CREATE);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ReminderEditActivity.class);
        i.putExtra(DatabaseHelper.KEY_ID, id);
        startActivityForResult(i, ACTIVITY_EDIT); 
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
    /**
     * Create a quiz, as defined in Quiz.json, when the user clicks on "Read quiz from file."
     *
     * @throws IOException
     */
    public String[] readQuizFromFile() {
        // clearQuizStatus();
        String[] questions = null;
        try {
            JSONObject jsonObject = JsonUtils.loadJsonFile(this, QUIZ_JSON_FILE);
            JSONArray jsonArray = jsonObject.getJSONArray(JsonUtils.JSON_FIELD_QUESTIONS);
            questions = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject questionObject = jsonArray.getJSONObject(i);
                Question question = Question.fromJson(questionObject, mQuestionIndex++);

                questions[i]=question.question;
            }
        }
        catch (IOException  | JSONException jsonException)
        {

        }

        return  questions;
    }



    /**
     * Used to ensure questions with smaller indexes come before questions with larger
     * indexes. For example, question0 should come before question1.
     */
    private static class Question implements Comparable<Question> {

        private String question;
        private int questionIndex;
        private String[] answers;
        private int correctAnswerIndex;

        public Question(String question, int questionIndex, String[] answers,
                        int correctAnswerIndex) {
            this.question = question;
            this.questionIndex = questionIndex;
            this.answers = answers;
            this.correctAnswerIndex = correctAnswerIndex;
        }
        public Question(String question, int questionIndex, String[] answers) {
            this.question = question;
            this.questionIndex = questionIndex;
            this.answers = answers;

        }

        public static Question fromJson(JSONObject questionObject, int questionIndex)
                throws JSONException {
            String question = questionObject.getString(JsonUtils.JSON_FIELD_QUESTION);
            JSONArray answersJsonArray = questionObject.getJSONArray(JsonUtils.JSON_FIELD_ANSWERS);
            String[] answers = new String[JsonUtils.NUM_ANSWER_CHOICES];
            for (int j = 0; j < answersJsonArray.length(); j++) {
                answers[j] = answersJsonArray.getString(j);
            }
            // int correctIndex = questionObject.getInt(JsonUtils.JSON_FIELD_CORRECT_INDEX);
            //  return new Question(question, questionIndex, answers, correctIndex);
            return new Question(question, questionIndex, answers);
        }

        @Override
        public int compareTo(Question that) {
            return this.questionIndex - that.questionIndex;
        }


    }
}
