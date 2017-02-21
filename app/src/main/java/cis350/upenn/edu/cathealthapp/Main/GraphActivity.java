package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import cis350.upenn.edu.cathealthapp.Core.TodayPageInfo;
import cis350.upenn.edu.cathealthapp.R;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_graph);
        changeFont();

        Set<Calendar> datesInputted = LoadingScreenActivity.getDates();
        Iterator<Calendar> it = datesInputted.iterator();
        while (it.hasNext()) {
            Log.d("set calendar", it.next().toString());

        }

        GraphView graphWeight = (GraphView) findViewById(R.id.weight_graph);
        LinkedList<DataPoint> weights = new LinkedList<DataPoint>();
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.add(Calendar.DATE, 30);
        cal.add(Calendar.DATE, -29);
        Date tomorrow = cal.getTime();

        if (datesInputted == null || datesInputted.size() == 0) {
            graphWeight.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
            graphWeight.getViewport().setMaxX(tomorrow.getTime());
//            graphWeight.getViewport().setMinY(0);
            graphWeight.getViewport().setMaxY(20);
            graphWeight.getGridLabelRenderer().setPadding(60);
            graphWeight.getViewport().setMinX(today.getTime());
            graphWeight.getViewport().setXAxisBoundsManual(true);
            graphWeight.getViewport().setMaxX(tomorrow.getTime());
            graphWeight.getViewport().setMinX(today.getTime());
            graphWeight.getGridLabelRenderer().setNumHorizontalLabels(2);
            graphWeight.getGridLabelRenderer().setHumanRounding(false);
            graphWeight.getGridLabelRenderer().setHighlightZeroLines(true);
            graphWeight.getGridLabelRenderer().setVerticalAxisTitle("Weight");
            graphWeight.getViewport().setYAxisBoundsManual(true);
            graphWeight.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLUE);
            changeFont();
            return;
        }

        TreeMap<Date, Double> mapping = new TreeMap<>();
        Iterator<Calendar> setItr = datesInputted.iterator();
        while (setItr.hasNext()) {
            Calendar c = setItr.next();
            Date thisDate = new java.util.Date(c.get(Calendar.YEAR) - 1900, c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            List<TodayPageInfo> tps = LoadingScreenActivity.getDataFromDate(c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.MONTH), c.get(Calendar.YEAR));
            for (TodayPageInfo tp : tps) {
                if (tp.getCatWeight() != -1) {
                    mapping.put(thisDate, tp.getCatWeight());
                }
            }
        }

        Iterator<Date> dateItr = mapping.keySet().iterator();
        while (dateItr.hasNext()) {
            Date d = dateItr.next();
            DataPoint dp = new DataPoint(d, mapping.get(d));
            if (dp.getY() <= 0) continue;
            weights.addLast(dp);
        }

        for (DataPoint d : weights) {
            Log.d("datapoint", d.toString());
        }

        DataPoint[] dataPoints = new DataPoint[weights.size()];
        int i = 0;
        for (DataPoint d : weights) {
            dataPoints[i] = d;
            i++;
        }
        for (DataPoint d : dataPoints) {
            Log.d("datapointsArray: ", d.toString());
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
//        graphWeight.removeAllSeries();

        Log.d("LGS", series.toString());


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLUE);
        paint.setPathEffect(new DashPathEffect(new float[]{8, 5}, 0));
        series.setCustomPaint(paint);

        graphWeight.getLegendRenderer().setVisible(true);
        graphWeight.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        series.setTitle("Weight");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);


        SimpleDateFormat sd = new SimpleDateFormat("yy/MM/dd");
        graphWeight.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext(), sd));
        graphWeight.addSeries(series);
        graphWeight.getGridLabelRenderer().setLabelVerticalWidth(10);
        graphWeight.getGridLabelRenderer().setPadding(60);
        graphWeight.getViewport().setMinY(0);
        graphWeight.getGridLabelRenderer().setHighlightZeroLines(true);
        graphWeight.getGridLabelRenderer().setVerticalAxisTitleColor(Color.BLUE);
        graphWeight.getViewport().setXAxisBoundsManual(true);
        graphWeight.getViewport().setYAxisBoundsManual(true);
        graphWeight.getGridLabelRenderer().setHumanRounding(false);
        if (dataPoints.length == 0) {
            graphWeight.getViewport().setMaxY(20);
            graphWeight.getGridLabelRenderer().setNumHorizontalLabels(2);
            graphWeight.getViewport().setMaxX(today.getTime() + 86400000);
            graphWeight.getViewport().setMinX(today.getTime());
            return;
        } else {
            graphWeight.getViewport().setMinX(dataPoints[0].getX());
        }


        if (dataPoints.length == 1) {
            graphWeight.getGridLabelRenderer().setNumHorizontalLabels(2);
            graphWeight.getViewport().setMaxX(dataPoints[0].getX() + 86400000);
        } else {
            int size = dataPoints.length > 4 ? 4 : dataPoints.length;
            graphWeight.getGridLabelRenderer().setNumHorizontalLabels(size);
            graphWeight.getViewport().setMaxX(dataPoints[dataPoints.length - 1].getX());
        }


        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Date d = new Date((long) dataPoint.getX());
                int year = d.getYear() - 100;
                int month = d.getMonth() + 1;
                int day = d.getDate();
                StringBuilder sb = new StringBuilder();
                sb.append(year).append("/").append(month).append("/").append(day);
                String date = sb.toString();
                String text = "".concat("The weight is ").concat(String.valueOf(dataPoint.getY())).concat(" lbs on ")
                        .concat(date);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void changeFont() {
        TextView graph = (TextView) findViewById(R.id.graphtitle);
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(graph, Chunkfive);


    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_behavior, menu);
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
