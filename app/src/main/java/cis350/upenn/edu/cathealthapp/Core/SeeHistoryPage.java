package cis350.upenn.edu.cathealthapp.Core;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.Main.PicturePage;
import cis350.upenn.edu.cathealthapp.R;

public class SeeHistoryPage extends AppCompatActivity {
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_see_history_page);
        image = (ImageView) findViewById(R.id.document_image);
        List<String> dates = new ArrayList<String>();
        Set<Calendar> savedCals = LoadingScreenActivity.getDatesI();

        //display date above image as MM/DD/YYYY
        for (Calendar thiscal : savedCals) {
            String dateStr = getDateString(thiscal);
            dates.add(dateStr);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, dates);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        dateSpinner.setAdapter(adapter);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();

                Intent i = new Intent(getApplicationContext(), PicturePage.class);
                i.putExtra("picture", b);
                startActivityForResult(i, 1);
            }
        });
//        changeFont();
    }

    public static String getDateString(Calendar cal) {
        int day = cal.get(Calendar.DATE);
        String dayStr = day >= 10 ? String.valueOf(day) : "0" + day;
        int month = cal.get(Calendar.MONTH) + 1;
        String monthStr = month >= 10 ? String.valueOf(month) : "0" + month;
        int year = cal.get(Calendar.YEAR);
        return monthStr + "/" + dayStr + "/" + year;
    }

//    private void changeFont() {
//        TextView selectDate = (TextView) findViewById(R.id.graphtitle);
//        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
//        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
//        setFont(selectDate, Chunkfive);
//    }

//    private void setFont(TextView t, Typeface tf) {
//        t.setTypeface(tf);
//    }

    public void viewImage(View v) {
        Spinner spinnerDate = (Spinner) findViewById(R.id.dateSpinner);
        if (spinnerDate.getSelectedItem() != null) {
            String date = spinnerDate.getSelectedItem().toString();
            String[] dateParts = date.split("/");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            Bitmap imageUpload = LoadingScreenActivity.getDataFromDateI(day, month - 1, year);
            image.setImageBitmap(imageUpload);
        } else {
            Toast.makeText(getApplicationContext(), "No Image To Show.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seehistory, menu);
        return true;
    }

    public void goBack(View v) {
        Intent i = new Intent(this, VetInfoPage.class);
        startActivityForResult(i, 2);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_menuS) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageS) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitS) {
            quit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
