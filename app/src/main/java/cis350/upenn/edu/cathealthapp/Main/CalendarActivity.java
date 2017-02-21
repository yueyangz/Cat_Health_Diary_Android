package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.R.layout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cis350.upenn.edu.cathealthapp.Core.TodayPage;
import cis350.upenn.edu.cathealthapp.Core.TodayPageInfo;
import cis350.upenn.edu.cathealthapp.Main.GraphActivity;
import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.R;
import cis350.upenn.edu.cathealthapp.Main.ReminderActivity;
import cis350.upenn.edu.cathealthapp.Util.ReminderAdapter;


public class CalendarActivity extends AppCompatActivity {

    private static Date date;
    private static CalendarView calendar;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        calendar = (CalendarView) findViewById(R.id.catHealthCalendar);
        date = new Date(calendar.getDate());

        //replace current 'date' object with newly chosen date
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
//                if (view.getDate() != date) {
                if (!((year == date.getYear() + 1900) && (month == date.getMonth()) && (day == date.getDate()))) {
                    date = new Date(year, month, day);
                    System.out.println("Day:" + day + " , Month:" + month + " , Year:" + year);
                    Intent i = new Intent(getApplicationContext(), TodayPage.class);
                    i.putExtra("Year", "" + year);
                    i.putExtra("Month", "" + month);
                    i.putExtra("Day", "" + day);
                    startActivityForResult(i, 888);
                    Calendar curCal = new GregorianCalendar();
                    curCal.set(year, month, day);
                    showReminders(curCal);
                }
            }
        });

        // list of reminders for the calendar
        lv = (ListView) findViewById(R.id.reminder_list);

        showReminders(Calendar.getInstance());

        changeFont();
    }

    private void showReminders(Calendar curCal) {
        LinkedList<TodayPageInfo> list = new LinkedList<>();
        // get all dates that have saved data
        Set<Calendar> datesInputted = LoadingScreenActivity.getDates();
        ArrayList<Calendar> listOfDates = new ArrayList(datesInputted);
        Collections.sort(listOfDates);

        // get calendar object for current date
//        Calendar curCal = Calendar.getInstance();
        // for all dates with information stored
        for (Calendar c : listOfDates) {
            // get the calendar object for that day
            Calendar dCal = new GregorianCalendar();
            dCal.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
            // only want dates in the future - past reminders don't matter
            if (dCal.compareTo(curCal) >= 0) {
                List<TodayPageInfo> tps = LoadingScreenActivity.getDataFromDate(c.get(Calendar.DATE), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
                // if there is a saved reminder on that day
                for (TodayPageInfo tp : tps) {
                    String r = tp.getReminder();
                    if (r != "") {
                        // need to add it to list of shown reminders with the date
                        list.addFirst(tp);
                    }
                }
            }
        }

        ReminderAdapter cbAdapter = new ReminderAdapter(this, list);
        lv.setAdapter(cbAdapter);
    }

    private void changeFont() {
        TextView reminder = (TextView) findViewById(R.id.reminder);

        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(reminder, Chunkfive);


    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    public void onWeightButtonClick(View v) {
        Intent i = new Intent(this, GraphActivity.class);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        i.putExtra("Year", "" + year);
        i.putExtra("Month", "" + month);
        i.putExtra("Day", "" + day);
        startActivityForResult(i, 6);
    }

    public void onReminderButtonClick(View v) {
        Intent i = new Intent(this, ReminderActivity.class);
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        i.putExtra("Year", "" + year);
        i.putExtra("Month", "" + month);
        i.putExtra("Day", "" + day);
        startActivityForResult(i, 888);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 888:
                switch (resultCode) {
                    case 7:
                        int year = data.getIntExtra("year", 0);
                        int month = data.getIntExtra("month", 0);
                        int day = data.getIntExtra("day", 0);
                        Calendar curCal = new GregorianCalendar();
                        curCal.set(year, month, day);
                        showReminders(curCal);
                        break;
                }
                break;
        }
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
