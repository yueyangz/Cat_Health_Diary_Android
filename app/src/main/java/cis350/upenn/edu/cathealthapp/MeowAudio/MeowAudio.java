package cis350.upenn.edu.cathealthapp.MeowAudio;

/**
 * Created by shannondowling on 4/19/16.
 */

import android.content.Context;
import android.media.MediaPlayer;

import cis350.upenn.edu.cathealthapp.R;

public class MeowAudio {

    MediaPlayer mp;

    public MeowAudio(Context context) {
        mp = MediaPlayer.create(context, R.raw.meow);
    }

    public void playNoise() {
        mp.start();
    }

    public void donePlaying() {
        mp.release();
    }

}
