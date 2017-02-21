package cis350.upenn.edu.cathealthapp.Core;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cis350.upenn.edu.cathealthapp.Main.LoadingScreenActivity;
import cis350.upenn.edu.cathealthapp.Persistence.Persistence;

public class Database implements Serializable {
    private static final String DBIIMGS = "dbiimgs";
    private static final String CATIMGS = "catimgs";

    private HashMap<Calendar, List<TodayPageInfo>> databaseTP; // TodayPageInfo
    private HashMap<Calendar, Behavior> databaseB;
    private HashMap<Calendar, Bitmap> databaseI;
    private String notes;
    private HashSet<Bitmap> pictures;

    private Context context;

    public Database(Context context) {
        databaseTP = new HashMap<>();
        databaseB = new HashMap<Calendar, Behavior>();
        this.context = context;
        // load album imgs
        pictures = new HashSet<Bitmap>();
        File folder = context.getDir(CATIMGS, Context.MODE_PRIVATE);
        for (File imgFile : folder.listFiles()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            pictures.add(bitmap);
        }

        // load dbimgs
        databaseI = new HashMap<Calendar, Bitmap>();
        File dbiFolder = context.getDir(DBIIMGS, Context.MODE_PRIVATE);
        for (File imgFile : dbiFolder.listFiles()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            String imgFileName = imgFile.getName();
            String[] dateParts = imgFileName.split(" ");
            int month = Integer.parseInt(dateParts[0]);
            int day = Integer.parseInt(dateParts[1]);
            int year = Integer.parseInt(dateParts[2]);
            Calendar cal = Calendar.getInstance();
            cal.set(year, month - 1, day, 0, 0, 0);
            cal.set(Calendar.MILLISECOND, 0);
            databaseI.put(cal, bitmap);
        }

    }

    public void addPicture(Bitmap b) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
//            String folderName = "catimgs/";
//            File folder = new File(folderName);
//            File folder = new File(Environment.getExternalStorageDirectory(), CATIMGS);

            File folder = context.getDir(CATIMGS, Context.MODE_PRIVATE);
//            Log.i("I", String.valueOf(folder.mkdirs()));
            File outputFile = new File(folder, b.toString() + ".png");
//            Log.i("I", String.valueOf(outputFile.createNewFile()));
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pictures.add(b);
    }

    public HashSet<Bitmap> getPictures() {
        return pictures;
    }

    public void saveNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void addData(Calendar cal, TodayPageInfo todayPage) {
        boolean contains = false;
        List<TodayPageInfo> tps = databaseTP.get(cal);
        if (tps == null) {
            tps = new LinkedList<>();
            tps.add(todayPage);
            databaseTP.put(cal, tps);
        } else {
            tps.add(todayPage);
        }
        saveToDB(databaseTP, "dbtp");
    }

    public void saveToDB(HashMap dbtp, String itemName) {

        Persistence p = LoadingScreenActivity.p;
        SQLiteDatabase db = p.getWritableDatabase();
        ContentValues values = new ContentValues();

        // serialize obj
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(dbtp);
            oos.flush();
            byte[] bytes = baos.toByteArray();
            Log.i("I", Arrays.toString(bytes));
            values.put("obj_bytes", bytes);
        } catch (IOException e) {
            Log.i("I", "Cannot save " + itemName);
        }
        values.put("obj_name", itemName);
//        Log.i("I", "Updated row num: " + db.update("objs", values, "obj_name=?", new String[]{itemName}));
        Log.i("I", "Updated row num: " + db.replace("objs", null, values));
        db.close();
    }

    public void prePopulatedbtp() {
        SQLiteDatabase db = LoadingScreenActivity.p.getReadableDatabase();
        Cursor cursor = db.query("objs", new String[]{"obj_name", "obj_bytes"}, "obj_name=?", new String[]{"dbtp"}, null, null, null);
        if (cursor.moveToNext()) {
            String obj_name = cursor.getString(0);
            byte[] obj_bytes = cursor.getBlob(1);
            Log.i("I", obj_name);
            Log.i("I", Arrays.toString(obj_bytes));
            ByteArrayInputStream bais = new ByteArrayInputStream(obj_bytes);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                databaseTP = (HashMap<Calendar, List<TodayPageInfo>>) ois.readObject();
            } catch (IOException e) {
                Log.i("I", e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void prePopulatedbb() {
        SQLiteDatabase db = LoadingScreenActivity.p.getReadableDatabase();
        Cursor cursor = db.query("objs", new String[]{"obj_name", "obj_bytes"}, "obj_name=?", new String[]{"dbb"}, null, null, null);
        if (cursor.moveToNext()) {
            String obj_name = cursor.getString(0);
            byte[] obj_bytes = cursor.getBlob(1);
            Log.i("I", obj_name);
            Log.i("I", Arrays.toString(obj_bytes));
            ByteArrayInputStream bais = new ByteArrayInputStream(obj_bytes);
            try {
                ObjectInputStream ois = new ObjectInputStream(bais);
                databaseB = (HashMap<Calendar, Behavior>) ois.readObject();
            } catch (IOException e) {
                Log.i("I", e.getMessage());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void populateData(Calendar cal, TodayPageInfo tp) {
        boolean contains = false;
        List<TodayPageInfo> tps = databaseTP.get(cal);
        if (tps == null) {
            tps = new LinkedList<>();
            tps.add(tp);
            databaseTP.put(cal, tps);
        } else {
            tps.add(tp);
        }
    }


    public void addDataB(Calendar cal, Behavior behavior) {
        boolean contains = false;
        Set<Calendar> cals = databaseB.keySet();
        for (Calendar c : cals) {
//            if (c.getTime() == cal.getTime()) {
            if (com2Calendar(c, cal)) {
                databaseB.put(c, behavior);
                contains = true;
            }
        }
        if (!contains) {
            databaseB.put(cal, behavior);
        }
        saveToDB(databaseB, "dbb");
    }

    public void addDataI(Calendar cal, Bitmap bitmap) {
        boolean contains = false;
        Set<Calendar> cals = databaseI.keySet();
        for (Calendar c : cals) {
            if (com2Calendar(c, cal)) {
                databaseI.put(c, bitmap);
                contains = true;
            }
        }
        if (!contains) {
            databaseI.put(cal, bitmap);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        try {
//            File folder = new File(Environment.getExternalStorageDirectory(), DBIIMGS);
            File folder = context.getDir(DBIIMGS, Context.MODE_PRIVATE);
//            Log.i("I", String.valueOf(folder.mkdirs()));
            File outputFile = new File(folder, SeeHistoryPage.getDateString(cal).replace('/', ' '));
//            Log.i("I", String.valueOf(outputFile.createNewFile()));
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(bytes);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Calendar> getDatesI() {
        return databaseI.keySet();
    }

    public Set<Calendar> getDates() {
        return databaseTP.keySet();
    }

    public List<TodayPageInfo> getDataFromDate(int day, int month, int year) {
        Set<Calendar> cals = databaseTP.keySet();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        List<TodayPageInfo> ret = databaseTP.get(cal);
        return ret == null ? new LinkedList<TodayPageInfo>() : ret;
//        for (Calendar c : cals) {
////            if (c.getTime() == cal.getTime()) {
//            if (com2Calendar(c, cal)) {
//                return databaseTP.get(c);
//            }
//        }
//        return new LinkedList<>();
    }

    public Behavior getDataFromDateB(int day, int month, int year) {
        Set<Calendar> cals = databaseB.keySet();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (Calendar c : cals) {
//            if (c.getTime() == cal.getTime()) {
            if (com2Calendar(c, cal)) {
                return databaseB.get(c);
            }
        }
        return new Behavior();
    }

    public Bitmap getDataFromDateI(int day, int month, int year) {
        Set<Calendar> cals = databaseI.keySet();
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        for (Calendar c : cals) {
//            if (c.getTime() == cal.getTime()) {
            if (com2Calendar(c, cal)) {
                return databaseI.get(c);
            }
        }
        return null;
    }

    private boolean com2Calendar(Calendar c1, Calendar c2) {
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) && c1.get(Calendar.DATE) == c2.get(Calendar.DATE));
    }
}
