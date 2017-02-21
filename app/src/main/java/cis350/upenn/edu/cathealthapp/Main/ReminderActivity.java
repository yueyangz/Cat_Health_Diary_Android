package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import cis350.upenn.edu.cathealthapp.Core.TodayPageInfo;
import cis350.upenn.edu.cathealthapp.Persistence.Persistence;
import cis350.upenn.edu.cathealthapp.R;

public class ReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reminder);

        //Allow reminders set up to ten years in the future
        List<String> years = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        years.add("" + (year));
        years.add("" + (year + 1));
        years.add("" + (year + 2));
        years.add("" + (year + 3));
        years.add("" + (year + 4));
        years.add("" + (year + 5));
        years.add("" + (year + 6));
        years.add("" + (year + 7));
        years.add("" + (year + 8));
        years.add("" + (year + 9));
        years.add("" + (year + 10));


        //prepare year spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, years);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        yearSpinner.setAdapter(adapter);
    }

    public void onSaveButtonClick(View view) {

        //collect data from spinners
        EditText reminder = (EditText) findViewById(R.id.cat_notes_today);
        String reminderText = reminder.getText().toString();
        Spinner spinnerDay = (Spinner) findViewById(R.id.daySpinner);
        int day = Integer.parseInt(spinnerDay.getSelectedItem().toString());
        Spinner spinnerMonth = (Spinner) findViewById(R.id.monthSpinner);
        int month = Integer.parseInt(spinnerMonth.getSelectedItem().toString());
        Spinner spinnerYear = (Spinner) findViewById(R.id.yearSpinner);
        int year = Integer.parseInt(spinnerYear.getSelectedItem().toString());
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(year, month - 1, day, 0, 0, 0);
        selectedCalendar.set(Calendar.MILLISECOND, 0);
        Set<Calendar> savedCals = LoadingScreenActivity.getDates();

        //find the indicated date and assign reminder
        for (Calendar savedCal : savedCals) {
            if (savedCal.getTime() == selectedCalendar.getTime()) {
//                TodayPageInfo today = LoadingScreenActivity.getDataFromDate(day, month, year);
                List<TodayPageInfo> tps = LoadingScreenActivity.getDataFromDate(day, month, year);
                for (TodayPageInfo tp : tps) {
//                    tp.setReminder(reminderText);
                    if (!reminderText.equals("")) {
                        tp.setReminder(reminderText);
                    } else {
                        tp.setReminder("");
                    }
                    LoadingScreenActivity.addData(selectedCalendar, tp);
                }
                Toast.makeText(getApplicationContext(), "Content Saved.", Toast.LENGTH_LONG).show();
                return;
            }
        }
        TodayPageInfo today = new TodayPageInfo();
        if (!reminderText.equals("")) {
            today.setReminder(reminderText);
        } else {
            today.setReminder("");
        }
        LoadingScreenActivity.addData(selectedCalendar, today);
        Toast.makeText(getApplicationContext(), "Content Saved.", Toast.LENGTH_LONG).show();

        Intent resIntent = new Intent();
        resIntent.putExtra("year", year);
        resIntent.putExtra("month", month - 1);
        resIntent.putExtra("day", day);
        setResult(7, resIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuC) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageC) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitC) {
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
