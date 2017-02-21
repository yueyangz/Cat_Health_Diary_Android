package cis350.upenn.edu.cathealthapp.Core;

import java.io.Serializable;

/**
 * Created by Jonathan on 3/14/16.
 */
public class Behavior implements Serializable{
    int active;
    int sleep;
    boolean seenCat;
    String activeNotes;
    String sleepNotes;

    public Behavior(int active, int sleep, boolean seen, String activeNotes, String sleepNotes) {
        this.active = active;
        this.sleep = sleep;
        seenCat = seen;
        this.activeNotes = activeNotes;
        this.sleepNotes = sleepNotes;
    }

    public Behavior() {
        active = -1;
        sleep = -1;
    }

    public boolean isCatSeen() {
        return seenCat;
    }

    public String getActiveNotes() {
        return activeNotes;
    }

    public String getSleepNotes() {
        return sleepNotes;
    }

    public int getActive() {
        return active;
    }

    public int getSleep() {
        return sleep;
    }
}
