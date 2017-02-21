package cis350.upenn.edu.cathealthapp.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.HashSet;

import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;

/**
 * Created by Jonathan on 4/24/16.
 */
public class AlbumLayout extends ScrollView {

    public AlbumLayout(Context c) {
        super(c);
    }

    public AlbumLayout(Context c, AttributeSet a) {
        super(c, a);
    }

    public void onDraw(Canvas canvas) {

        HashSet<Bitmap> pictures = LoadingScreenActivity.getPictures();
        int currentWidth = 10;
        int currentHeight = 0;

        for (Bitmap pic : pictures) {
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(pic, 1000, 1000, true);
            canvas.drawBitmap(scaledBitmap, currentWidth, currentHeight, null);
            break;
        }
    }
}
