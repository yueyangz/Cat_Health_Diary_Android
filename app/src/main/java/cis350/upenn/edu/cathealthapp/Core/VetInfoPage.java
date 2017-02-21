package cis350.upenn.edu.cathealthapp.Core;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.R;

public class VetInfoPage extends AppCompatActivity {

    ImageView uploadedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vet_info_page);
        uploadedImage = (ImageView) findViewById(R.id.uploaded_image);

        List<String> years = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        years.add("" + (year));
        years.add("" + (year - 1));
        years.add("" + (year - 2));
        years.add("" + (year - 3));
        years.add("" + (year - 4));
        years.add("" + (year - 5));
        years.add("" + (year - 6));
        years.add("" + (year - 7));
        years.add("" + (year - 8));
        years.add("" + (year - 9));
        years.add("" + (year - 10));

        MaterialSpinner niceSpinnerDay = (MaterialSpinner) findViewById(R.id.daySpinner);

        niceSpinnerDay.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31");
        niceSpinnerDay.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
            }
        });
        niceSpinnerDay.setTextSize(14);
//        niceSpinnerDay.setBackgroundColor(Color.TRANSPARENT);
        MaterialSpinner niceSpinnerMonth = (MaterialSpinner) findViewById(R.id.monthSpinner);
        niceSpinnerMonth.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        niceSpinnerMonth.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG);
            }
        });
//        niceSpinnerMonth.setBackgroundColor(Color.TRANSPARENT);
        niceSpinnerMonth.setTextSize(14);
        MaterialSpinner niceSpinnerYear = (MaterialSpinner) findViewById(R.id.yearSpinner);
        niceSpinnerYear.setItems(years);
        niceSpinnerYear.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG);
            }
        });
//        niceSpinnerYear.setBackgroundColor(Color.TRANSPARENT);
        niceSpinnerYear.setTextSize(14);


//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_item, years);
//
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        Spinner yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
//        yearSpinner.setAdapter(adapter);
        changeFont();
    }

    private void changeFont() {
        TextView lastVisit = (TextView) findViewById(R.id.lastVisit);
        TextView year = (TextView) findViewById(R.id.year);
        TextView month = (TextView) findViewById(R.id.month);
        TextView day = (TextView) findViewById(R.id.day);
        Typeface oswald = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(lastVisit, Chunkfive);
        setFont(year, oswald);
        setFont(day, oswald);
        setFont(month, oswald);

    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vetinfo, menu);
        return true;
    }

    public void loadImage(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                uploadedImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveImage(View v) {
        Bitmap imageBitmap = null;

        //convert uploaded image into Bitmap object
        if (uploadedImage.getDrawable() != null) {
            imageBitmap = ((BitmapDrawable) uploadedImage.getDrawable()).getBitmap();
        }

        //pull date information from spinners
        MaterialSpinner spinnerD = (MaterialSpinner) findViewById(R.id.daySpinner);
//        int day = Integer.parseInt(spinnerD.getSelectedItem().toString());
        int day = Integer.parseInt(spinnerD.getItems().get(spinnerD.getSelectedIndex()).toString());
        MaterialSpinner spinnerM = (MaterialSpinner) findViewById(R.id.monthSpinner);
//        int month = Integer.parseInt(spinnerM.getSelectedItem().toString());
        int month = Integer.parseInt(spinnerM.getItems().get(spinnerM.getSelectedIndex()).toString());
        MaterialSpinner spinnerY = (MaterialSpinner) findViewById(R.id.yearSpinner);
//        int year = Integer.parseInt(spinnerY.getSelectedItem().toString());
        int year = Integer.parseInt(spinnerY.getItems().get(spinnerY.getSelectedIndex()).toString());
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);


        //only save if bitmap conversion successful/ image was indeed uploaded
        if (imageBitmap == null) {
            Toast.makeText(getApplicationContext(), "Please upload a picture.", Toast.LENGTH_LONG).show();
        } else {
            LoadingScreenActivity.addDataI(cal, imageBitmap);
            Toast.makeText(getApplicationContext(), "Content saved.", Toast.LENGTH_LONG).show();
        }
    }

    public void seeHistory(View v) {
        Intent i = new Intent(this, SeeHistoryPage.class);
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
        if (id == R.id.action_menuV) {
            menu();
            return true;
        }
        if (id == R.id.action_lastPageV) {
            lastPage();
            return true;
        }
        if (id == R.id.action_quitV) {
            quit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
