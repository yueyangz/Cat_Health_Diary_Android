package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Calendar;

import cis350.upenn.edu.cathealthapp.Core.Behavior;
import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.R;

public class BehaviorPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_behavior_page);

        // Setting up the page state and retrieving data for this day in the database
        int day = Integer.parseInt(getIntent().getStringExtra("Day"));
        int month = Integer.parseInt(getIntent().getStringExtra("Month"));
        int year = Integer.parseInt(getIntent().getStringExtra("Year"));
        Behavior savedB = LoadingScreenActivity.getDataFromDateB(day, month, year);

        //Setting the notes on the page according to the last save
        EditText activeNotes = (EditText) findViewById(R.id.active_notes);
        EditText sleepNotes = (EditText) findViewById(R.id.sleep_notes);
        activeNotes.setText(savedB.getActiveNotes(), TextView.BufferType.EDITABLE);
        activeNotes.invalidate();
        sleepNotes.setText(savedB.getSleepNotes(), TextView.BufferType.EDITABLE);
        sleepNotes.invalidate();

        activeNotes.getBackground().clearColorFilter();
        sleepNotes.getBackground().clearColorFilter();

        //Setting the spinners on the page according to the last save
//        if (savedB.getActive() != -1 && savedB.getSleep() != -1) {
//            MaterialSpinner spinnerBehavior = (MaterialSpinner) findViewById(R.id.behaviorSpinner);
////            spinnerBehavior.setSelection(savedB.getActive() - 1);
//            MaterialSpinner spinnerSleep = (MaterialSpinner) findViewById(R.id.sleepSpinner);
////            spinnerSleep.setSelection(savedB.getSleep() - 1);
//        }
        spinner(R.id.behaviorSpinner, savedB.getActive() - 1);
        spinner(R.id.sleepSpinner, savedB.getSleep() - 1);

        //Setting the cat seen radio buttons according to the last save
        final CheckBox seenCat = (CheckBox) findViewById(R.id.saw_cat);
        final CheckBox noCat = (CheckBox) findViewById(R.id.no_cat);
        if (savedB.isCatSeen()) {
            seenCat.toggle();
            seenCat.setEnabled(false);
        } else {
            noCat.toggle();
            noCat.setEnabled(false);
        }

        //Set the cat seen radio button state
        seenCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    seenCat.setEnabled(false);
                } else {
                    seenCat.setEnabled(true);
                }
            }
        });

        noCat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
                if (isChecked) {
                    noCat.setEnabled(false);
                } else {
                    noCat.setEnabled(true);
                }
            }
        });

        changeFont();

    }

    private void spinner(int id, int selectedId) {
        MaterialSpinner niceSpinner = (MaterialSpinner) findViewById(id);
        niceSpinner.setItems("1", "2", "3", "4", "5");
        niceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG);
            }
        });
        niceSpinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {
            @Override
            public void onNothingSelected(MaterialSpinner spinner) {
                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG);
            }
        });
        niceSpinner.setTextSize(14);
        if (selectedId >= 0)
            niceSpinner.setSelectedIndex(selectedId);
    }


    private void changeFont() {
        TextView sleep = (TextView) findViewById(R.id.Sleep);
        TextView active = (TextView) findViewById(R.id.Active);
        TextView seen = (TextView) findViewById(R.id.seen);
        TextView sleep_notes_text = (TextView) findViewById(R.id.sleep_notes_text);
        TextView active_notes_text = (TextView) findViewById(R.id.active_notes_text);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");

        EditText foodEdit = (EditText) findViewById(R.id.fillInFood3);
        setFont(sleep, Chunkfive);
        setFont(active, Chunkfive);
        setFont(seen, typeFace);
        setFont(sleep_notes_text, typeFace);
        setFont(active_notes_text, typeFace);
        setFont(seen, typeFace);

    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }


    //Method on check of the cat seen radio button
    public void onSeenCat(View v) {
        CheckBox seenCat = (CheckBox) findViewById(R.id.saw_cat);
        if (seenCat.isChecked()) {
            CheckBox noCat = (CheckBox) findViewById(R.id.no_cat);
            noCat.toggle();
        }
    }

    //Method on check of the cat not seen radio button
    public void onNoCat(View v) {
        CheckBox noCat = (CheckBox) findViewById(R.id.no_cat);
        if (noCat.isChecked()) {
            CheckBox seenCat = (CheckBox) findViewById(R.id.saw_cat);
            seenCat.toggle();
        }
    }

    //Method saving the state of the page on save click
    public void onSaveButtonClickBehave(View v) {

        //Getting the spinner state
        MaterialSpinner spinnerB = (MaterialSpinner) findViewById(R.id.behaviorSpinner);
//        int b = Integer.parseInt(spinnerB.getSelectedItem().toString());
        int b = Integer.parseInt(spinnerB.getItems().get(spinnerB.getSelectedIndex()).toString());
        MaterialSpinner spinnerS = (MaterialSpinner) findViewById(R.id.sleepSpinner);
//        int s = Integer.parseInt(spinnerS.getSelectedItem().toString());
        int s = Integer.parseInt(spinnerS.getItems().get(spinnerS.getSelectedIndex()).toString());

        //Getting the cat seen state
        CheckBox catStatus = (CheckBox) findViewById(R.id.saw_cat);
        boolean catSeen = catStatus.isChecked();

        //Getting the notes
        String activeNotes = ((EditText) findViewById(R.id.active_notes)).getText().toString();
        String sleepNotes = ((EditText) findViewById(R.id.sleep_notes)).getText().toString();
        Behavior newBehavior = new Behavior(b, s, catSeen, activeNotes, sleepNotes);


        //Saving info to database
        int day = Integer.parseInt(getIntent().getStringExtra("Day"));
        int month = Integer.parseInt(getIntent().getStringExtra("Month"));
        int year = Integer.parseInt(getIntent().getStringExtra("Year"));
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        Toast.makeText(getApplicationContext(), "Content Saved.", Toast.LENGTH_LONG).show();
        LoadingScreenActivity.addDataB(cal, newBehavior);
        setResult(1);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_behavior, menu);
        int day = Integer.parseInt(getIntent().getStringExtra("Day"));
        int month = Integer.parseInt(getIntent().getStringExtra("Month"));
        int year = Integer.parseInt(getIntent().getStringExtra("Year"));
        TextView theDate = (TextView) findViewById(R.id.DateBehavior);
        theDate.setText((month + 1) + "/" + day + "/" + year);
        return true;
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

    //Menu back button
    public void lastPage() {
        finish();
    }

    //Menu go to menu button
    public void menu() {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, 1);
    }

    //Menu quit app button
    public void quit() {

        finishAffinity();
    }
}


