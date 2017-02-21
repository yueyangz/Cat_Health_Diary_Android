package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cis350.upenn.edu.cathealthapp.R;

public class NotesAndRemindersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notes_and_reminders);
        String currentNotes = LoadingScreenActivity.getNotes();
        EditText catNotes = (EditText) findViewById(R.id.cat_notes);
        catNotes.setText(currentNotes);
        catNotes.invalidate();
        changeFont();
    }

    public void saveNotes(View v) {
        String sleepNotes = ((EditText) findViewById(R.id.cat_notes)).getText().toString();
        LoadingScreenActivity.saveNotes(sleepNotes);
        Toast.makeText(getApplicationContext(), "Content Saved.", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_behavior, menu);
        return true;
    }

    private void changeFont() {
        TextView graph=(TextView)findViewById(R.id.notes_reminder);
        EditText catNotes = (EditText)findViewById(R.id.cat_notes);
        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(graph, Chunkfive);
        setFont(catNotes, oswald);


    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuB) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageB) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitB) {
            quit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void lastPage() {
        finish();
    }

    public void menu() {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 1);
    }

    public void quit() {

        finishAffinity();
    }
}
