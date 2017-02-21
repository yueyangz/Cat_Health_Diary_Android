package cis350.upenn.edu.cathealthapp.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cis350.upenn.edu.cathealthapp.Core.Behavior;
import cis350.upenn.edu.cathealthapp.Core.Database;
import cis350.upenn.edu.cathealthapp.Core.TodayPageInfo;
import cis350.upenn.edu.cathealthapp.Persistence.Persistence;
import cis350.upenn.edu.cathealthapp.R;

public class LoadingScreenActivity extends AppCompatActivity {

    private static Database thisData;
    public static Persistence p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        final AppCompatActivity thisAct = this;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            public void run() {
                Intent i = new Intent(thisAct, MainActivity.class);
                startActivityForResult(i, 1);
            }

        }, 5000);
    }

    public static void initializePersistence(Context m) {
    }

    public static Set<Calendar> getDates() {
        return thisData.getDates();
    }

    public static List<TodayPageInfo> getDataFromDate(int day, int month, int year) {
        return thisData.getDataFromDate(day, month, year);
    }

    public static Behavior getDataFromDateB(int day, int month, int year) {
        return thisData.getDataFromDateB(day, month, year);
    }

    public static void addData(Calendar date, TodayPageInfo thisPage) {
        thisData.addData(date, thisPage);
    }

    public static void addPicture(Bitmap imageBitmap) {
        thisData.addPicture(imageBitmap);
    }

    public static HashSet<Bitmap> getPictures() {
        return thisData.getPictures();
    }

    public static void addDataB(Calendar date, Behavior newBehavior) {
        thisData.addDataB(date, newBehavior);
    }

    public static Database getThisData() {
        return thisData;
    }

    static String getNotes() {
        return thisData.getNotes();
    }

    static void saveNotes(String sleepNotes) {
        thisData.saveNotes(sleepNotes);
    }

    public static Set<Calendar> getDatesI() {
        return thisData.getDatesI();
    }

    public static Bitmap getDataFromDateI(int day, int month, int year) {
        return thisData.getDataFromDateI(day, month, year);
    }

    public static void addDataI(Calendar date, Bitmap imageBitmap) {
        thisData.addDataI(date, imageBitmap);
    }

    public static void prePopulate(Context context) {
        p = new Persistence(context, "objs.db", null, 1);
        thisData = new Database(context);
        thisData.prePopulatedbb();
        thisData.prePopulatedbtp();
    }
}
