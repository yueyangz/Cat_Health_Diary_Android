package cis350.upenn.edu.cathealthapp.Core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.R;

public class TodayPage extends AppCompatActivity {
    public static final int foodCnt = 3;
    public static final int medicineCnt = 3;
    public static final int toiletCnt = 2;

    private boolean[] foodBs = new boolean[foodCnt];
    private boolean[] medicineBs = new boolean[medicineCnt];
    private boolean[] toiletBs = new boolean[toiletCnt];
//    private boolean food1 = false;
//    private boolean food2 = false;
//    private boolean food3 = false;
//    private boolean medicine1 = false;
//    private boolean medicine2 = false;
//    private boolean medicine3 = false;
//    private boolean toilet1 = false;
//    private boolean toilet2 = false;


    public void setCheckBoxes(int day, int month, int year) {
//        Set<Calendar> cals = LoadingScreenActivity.getDates();
//        Calendar cal = Calendar.getInstance();
//        for (Calendar c : cals) {
//            if (c.getTime() == cal.getTime()) {
        List<TodayPageInfo> tps = LoadingScreenActivity.getDataFromDate(day, month, year);
        for (TodayPageInfo tp : tps) {
            for (int i = 0; i < foodCnt; i++) {
                String checkBoxId = "checkbox_food" + (i + 1);
                int resId = getResources().getIdentifier(checkBoxId, "id", getPackageName());
                CheckBox foodC = (CheckBox) findViewById(resId);
                if (tp.getFoodBs()[i]) {
                    foodC.setChecked(true);
                    foodBs[i] = true;
                }
            }
            for (int i = 0; i < medicineCnt; i++) {
                String checkBoxId = "checkbox_medication" + (i + 1);
                int resId = getResources().getIdentifier(checkBoxId, "id", getPackageName());
                CheckBox medicineC = (CheckBox) findViewById(resId);
                if (tp.getMedicationB(i)) {
                    medicineC.setChecked(true);
                    medicineBs[i] = true;
                }
            }
            for (int i = 0; i < toiletCnt; i++) {
                String checkBoxId = "checkbox_toilet" + (i + 1);
                int resId = getResources().getIdentifier(checkBoxId, "id", getPackageName());
                CheckBox toiletC = (CheckBox) findViewById(resId);
                if (tp.getRestroom(i)) {
                    toiletC.setChecked(true);
                    toiletBs[i] = true;
                }
            }
        }
//            }
//        }
    }

    public void setEditTexts(int day, int month, int year) {
        List<TodayPageInfo> tps = LoadingScreenActivity.getDataFromDate(day, month, year);
        for (TodayPageInfo tp : tps) {
            for (int i = 0; i < foodCnt; i++) {
                String EditTextId = "fillInFood" + (i + 1);
                int resId = getResources().getIdentifier(EditTextId, "id", getPackageName());
                EditText foodText = (EditText) findViewById(resId);
                foodText.getBackground().clearColorFilter();
                foodText.setText(tp.getFood(i), TextView.BufferType.EDITABLE);
                foodText.invalidate();
            }
            for (int i = 0; i < medicineCnt; i++) {
                String EditTextId = "fillInMedication" + (i + 1);
                int resId = getResources().getIdentifier(EditTextId, "id", getPackageName());
                EditText medText = (EditText) findViewById(resId);
                medText.getBackground().clearColorFilter();
                medText.setText(tp.getMedication(i), TextView.BufferType.EDITABLE);
                medText.invalidate();
            }
            EditText catWeight = (EditText) findViewById(R.id.enter_weight);
            catWeight.getBackground().clearColorFilter();
            if (tp.getCatWeight() != -1) {
                catWeight.setText(String.valueOf(tp.getCatWeight()));
                catWeight.invalidate();
            }
            EditText reminder = (EditText) findViewById(R.id.enter_reminder);
            reminder.getBackground().clearColorFilter();
            if (!tp.getReminder().equals("")) {
                reminder.setText(tp.getReminder(), TextView.BufferType.EDITABLE);
                reminder.invalidate();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_todaypage);

        int day = Integer.parseInt(getIntent().getStringExtra("Day"));
        int month = Integer.parseInt(getIntent().getStringExtra("Month"));
        int year = Integer.parseInt(getIntent().getStringExtra("Year"));
        TextView theDate = (TextView) findViewById(R.id.Date);
        theDate.setText((month + 1) + "/" + day + "/" + year);
        setCheckBoxes(day, month, year);
        setEditTexts(day, month, year);
        changeFont();

    }

    private void changeFont() {
        TextView food = (TextView) findViewById(R.id.Food);
        TextView medication = (TextView) findViewById(R.id.Medication);
        TextView toiletry = (TextView) findViewById(R.id.Toiletry);
        TextView Input_Cat_Weight = (TextView) findViewById(R.id.Input_Cat_Weight);
        TextView reminder = (TextView) findViewById(R.id.reminder);
        TextView date = (TextView) findViewById(R.id.Date);
        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface goodDog = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont((EditText) findViewById(R.id.fillInFood3), oswald);
        setFont((EditText) findViewById(R.id.fillInFood2), oswald);
        setFont((EditText) findViewById(R.id.fillInFood1), oswald);
        setFont((EditText) findViewById(R.id.fillInMedication3), oswald);
        setFont((EditText) findViewById(R.id.fillInMedication2), oswald);
        setFont((EditText) findViewById(R.id.fillInMedication1), oswald);
        setFont((EditText) findViewById(R.id.enter_weight), oswald);
        setFont((EditText) findViewById(R.id.enter_reminder), oswald);
        setFont(food, goodDog);
        setFont(medication, goodDog);
        setFont(toiletry, goodDog);
        setFont(Input_Cat_Weight, goodDog);
        setFont(reminder, goodDog);
        setFont(date, oswald);
    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    public void onCheckboxClicked(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.checkbox_food1:
                foodBs[0] = isChecked;
//                if (isChecked) {
//                    food1 = true;
//                } else {
//                    food1 = false;
//                }
                break;
            case R.id.checkbox_food2:
                foodBs[1] = isChecked;
//                if (isChecked) {
//                    food2 = true;
//                } else {
//                    food2 = false;
//                }
                break;
            case R.id.checkbox_food3:
                foodBs[2] = isChecked;
//                if (isChecked) {
//                    food3 = true;
//                } else {
//                    food3 = false;
//                }
                break;
            case R.id.checkbox_medication1:
                medicineBs[0] = isChecked;
//                if (isChecked) {
//                    medicine1 = true;
//                } else {
//                    medicine1 = false;
//                }
                break;
            case R.id.checkbox_medication2:
                medicineBs[1] = isChecked;
//                if (isChecked) {
//                    medicine2 = true;
//                } else {
//                    medicine2 = false;
//                }
                break;
            case R.id.checkbox_medication3:
                medicineBs[2] = isChecked;
//                if (isChecked) {
//                    medicine3 = true;
//                } else {
//                    medicine3 = false;
//                }
                break;
            case R.id.checkbox_toilet1:
                toiletBs[0] = isChecked;
//                if (isChecked) {
//                    toilet1 = true;
//                } else {
//                    toilet1 = false;
//                }
                break;
            case R.id.checkbox_toilet2:
                toiletBs[1] = isChecked;
//                if (isChecked) {
//                    toilet2 = true;
//                } else {
//                    toilet2 = false;
//                }
                break;
            default:
                break;
        }
    }

    public void takePicture(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }

    }

    public void onSaveButtonClick(View view) {
        TodayPageInfo thisPage = new TodayPageInfo();
        for (int i = 0; i < foodCnt; i++) {
            thisPage.setFoodB(i, foodBs[i]);
            int resId = getResources().getIdentifier("fillInFood" + (i + 1), "id", getPackageName());
            String food = ((EditText) findViewById(resId)).getText().toString();
            thisPage.setFood(i, food);
        }
        for (int i = 0; i < medicineCnt; i++) {
            thisPage.setMedicationB(i, medicineBs[i]);
            int resId = getResources().getIdentifier("fillInMedication" + (i + 1), "id", getPackageName());
            String med = ((EditText) findViewById(resId)).getText().toString();
            thisPage.setMedication(i, med);
        }
        for (int i = 0; i < toiletCnt; i++) {
            thisPage.setRestroom(i, toiletBs[i]);
        }

        // saving cat weight
        String catWeight = ((EditText) findViewById(R.id.enter_weight)).getText().toString();
        if (!catWeight.equals("")) {
            try {
                thisPage.setCatWeight(Double.parseDouble(catWeight));
                Toast.makeText(getApplicationContext(), "Weight Saved.", Toast.LENGTH_LONG).show();
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please input a numerical weight.", Toast.LENGTH_LONG).show();
                thisPage.setCatWeight(-1);

            }
        } else {
            thisPage.setCatWeight(-1);
            Toast.makeText(getApplicationContext(), "Content Saved.", Toast.LENGTH_LONG).show();
        }
        // get current reminder text
        String reminderText = ((EditText) findViewById(R.id.enter_reminder)).getText().toString();
        // check if the reminder is blank
        if (!reminderText.equals("")) {
            thisPage.setReminder(reminderText);
        } else {
            thisPage.setReminder("");
        }

        // save data for the specified date in the database
        int day = Integer.parseInt(getIntent().getStringExtra("Day"));
        int month = Integer.parseInt(getIntent().getStringExtra("Month"));
        int year = Integer.parseInt(getIntent().getStringExtra("Year"));
//        <<<<<<<HEAD:
//        app / src / main / java / cis350 / upenn / edu / cathealthapp / Date / TodayPage.java
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        LoadingScreenActivity.addData(cal, thisPage);

        Intent resIntent = new Intent();
        resIntent.putExtra("year", year);
        resIntent.putExtra("month", month);
        resIntent.putExtra("day", day);
        setResult(7, resIntent);
        finish();
//        =======
//        Date date = new Date(day, month, year);
//        LoadingScreenActivity.addData(date, thisPage);
//        >>>>>>>zhixu - dev:
//        app / src / main / java / cis350 / upenn / edu / cathealthapp / TodayPage.java
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todaypage, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            LoadingScreenActivity.addPicture(imageBitmap);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuT) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageT) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitT) {
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
