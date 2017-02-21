package cis350.upenn.edu.cathealthapp.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.preference.PreferenceManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import cis350.upenn.edu.cathealthapp.Core.SeeHistoryPage;
import cis350.upenn.edu.cathealthapp.Core.TodayPage;
import cis350.upenn.edu.cathealthapp.Core.VetInfoPage;
import cis350.upenn.edu.cathealthapp.MeowAudio.MeowAudio;
import cis350.upenn.edu.cathealthapp.R;

import static cis350.upenn.edu.cathealthapp.Main.DailyRemindActivity.parseRepeat;

public class MainActivity extends AppCompatActivity {

    private static final int CalendarButton_ID = 1;
    private static final int TodayButton_ID = 2;
    private static final int BehaviorButton_ID = 3;
    private static final int VetInfoButton_ID = 4;
    private static final int NotesAndReminders_ID = 5;
    private static final int GraphButton_ID = 6;
    private static final int AlbumButton_ID = 7;
    MeowAudio meow;
    private boolean sound = false;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static final String ICONIMG = "iconimg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
        setContentView(R.layout.activity_main);
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        meow = new MeowAudio(this);
        changeFont();
        LoadingScreenActivity.prePopulate(getApplicationContext());

//        File folder = new File(Environment.getExternalStorageDirectory(), ICONIMG);
//        folder.mkdirs();
        File folder = getApplicationContext().getDir(ICONIMG, Context.MODE_PRIVATE);
        for (File imgFile : folder.listFiles()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView iconView = (ImageView) findViewById(R.id.imageView2);
            iconView.setImageBitmap(bitmap);
        }
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        boolean isSwitchOn = pref.getBoolean("switch_on", false);
        if (isSwitchOn) {
            String choose_time = pref.getString("choose_time", "");
            int choose_cycle = pref.getInt("cycle", 0);
            reStart(choose_time, choose_cycle);
        }
    }

    private void changeFont() {
        TextView caption = (TextView) findViewById(R.id.caption);
        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(caption, Chunkfive);
    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void getCalendarInstance(Intent i) {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        i.putExtra("Year", "" + year);
        i.putExtra("Month", "" + month);
        i.putExtra("Day", "" + day);

    }

    public void onTodayButtonClick(View v) {
        Intent i = new Intent(this, TodayPage.class);
        getCalendarInstance(i);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, TodayButton_ID);
    }

    public void onCalendarButtonClick(View v) {
        Intent i = new Intent(this, CalendarActivity.class);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, CalendarButton_ID);
    }

    public void onVetInfoButtonClick(View v) {
        Intent i = new Intent(this, VetInfoPage.class);
        getCalendarInstance(i);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, VetInfoButton_ID);
    }

    public void onBehaviorButtonClick(View v) {
        Intent i = new Intent(this, BehaviorPage.class);
        getCalendarInstance(i);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, BehaviorButton_ID);
    }

    public void onNotesAndRemindersButtonClick(View v) {
        Intent i = new Intent(this, NotesAndRemindersActivity.class);
        getCalendarInstance(i);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, NotesAndReminders_ID);
    }

    public void onAlbumButtonClick(View v) {
        Intent i = new Intent(this, AlbumPage.class);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, AlbumButton_ID);
    }

    public void onWeightButtonClick(View v) {
        Intent i = new Intent(this, GraphActivity.class);
        getCalendarInstance(i);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, GraphButton_ID);
    }

    public void onIconClick(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 15);
    }

    public void onSettingButtonClick(View v) {
        Intent i = new Intent(this, DailyRemindActivity.class);
        if (sound) {
            meow.playNoise();
        }
        startActivityForResult(i, 100);
    }

//    public void onSoundButtonClick(View v) {
//        sound = !sound;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    boolean returnSwitchOn = data.getBooleanExtra("switch_on", false);
                    editor.putBoolean("switch_on", returnSwitchOn);
                    if (returnSwitchOn) {
                        String returnChooseTime = data.getStringExtra("choose_time");
                        int returnCycle = data.getIntExtra("cycle", 0);
                        editor.putString("choose_time", returnChooseTime);
                        editor.putInt("cycle", returnCycle);
                    }
                    editor.commit();
                }
                break;
            case 15:
                if (resultCode == RESULT_OK) {
                    Uri targetUri = data.getData();
                    Bitmap bitmap;
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                        ImageView iconView = (ImageView) findViewById(R.id.imageView2);
                        iconView.setImageBitmap(bitmap);

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        byte[] bytes = baos.toByteArray();
                        try {
//                            File folder = new File(Environment.getExternalStorageDirectory(), ICONIMG);
                            File folder = getApplicationContext().getDir(ICONIMG, Context.MODE_PRIVATE);
//                            Log.i("I", String.valueOf(folder.mkdirs()));
                            for (File file : folder.listFiles())
                                file.delete();
                            File outputFile = new File(folder, "icon");
//                            Log.i("I", String.valueOf(outputFile.createNewFile()));
                            FileOutputStream fos = new FileOutputStream(outputFile);
                            fos.write(bytes);
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            default:
                break;
        }
    }

    private void reStart(String time, int cycle) {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");

            if (cycle == 0) {
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0);
            }
            if (cycle == -1) {
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0);
            } else {
                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]));
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        super.onSaveInstanceState(savedInstanceState);
        SharedPreferences myPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString("Save", "");
        if (json.equals("")) {
            SharedPreferences.Editor myPrefsEditor = myPref.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(LoadingScreenActivity.getThisData()); // myObject - instance of MyObject
            myPrefsEditor.putString("Save", json1);
            myPrefsEditor.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuM) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageM) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitM) {
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
        /*
        SharedPreferences myPref = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        String json = myPref.getString("Save", "");
        if (json.equals("")) {
            LoadingScreenActivity.thisData = new Database();
            SharedPreferences.Editor myPrefsEditor = myPref.edit();
            Gson gson1 = new Gson();
            String json1 = gson1.toJson(LoadingScreenActivity.thisData); // myObject - instance of MyObject
            myPrefsEditor.putString("Save", json1);
            myPrefsEditor.commit();
        } */

        // release the media player
        meow.donePlaying();


        finishAffinity();
    }
}
