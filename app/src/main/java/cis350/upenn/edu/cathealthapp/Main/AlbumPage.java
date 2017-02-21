package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Iterator;

import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Main.MainActivity;
import cis350.upenn.edu.cathealthapp.R;

public class AlbumPage extends AppCompatActivity {

    HashSet<Bitmap> pictures = LoadingScreenActivity.getPictures();
    Iterator pictureAlbum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_album_page);
        ImageView thisImage = (ImageView) findViewById(R.id.album);

        //get the first image in our database's image set
        pictureAlbum = pictures.iterator();
        if (pictureAlbum.hasNext()) {
            Bitmap thisBitmap = (Bitmap) pictureAlbum.next();
            thisImage.setImageBitmap(thisBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todaypage, menu);
        return true;
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

    public void onNextButtonClick(View v) {

        //iterate endlessly through our database' picture set
        ImageView thisImage = (ImageView) findViewById(R.id.album);
        if (pictureAlbum.hasNext()) {
            Bitmap thisBitmap = (Bitmap) pictureAlbum.next();
            thisImage.setImageBitmap(thisBitmap);
        } else {
            // start from beginning of album again
            pictureAlbum = pictures.iterator();
            Bitmap thisBitmap = (Bitmap) pictureAlbum.next();
            thisImage.setImageBitmap(thisBitmap);
        }
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
