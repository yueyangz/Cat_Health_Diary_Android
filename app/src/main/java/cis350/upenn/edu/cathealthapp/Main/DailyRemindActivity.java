package cis350.upenn.edu.cathealthapp.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import cis350.upenn.edu.cathealthapp.R;
import cis350.upenn.edu.cathealthapp.view.SelectRemindCyclePopup;


public class DailyRemindActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView date_tv;
    private TimePickerView pvTime;
    private RelativeLayout repeat_rl;
    private TextView tv_repeat_value;
    private LinearLayout allLayout;
    private Button set_btn;
    private String time;
    private int cycle;
    private Switch mSwitch;
    private LinearLayout secLayout;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private String repeat_freq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_remind);
        allLayout = (LinearLayout) findViewById(R.id.all_layout);
        secLayout = (LinearLayout) findViewById(R.id.sec_layout);
        set_btn = (Button) findViewById(R.id.set_btn);
        mSwitch = (Switch)findViewById(R.id.switch_def);
        date_tv = (TextView) findViewById(R.id.date_tv);
        repeat_rl = (RelativeLayout) findViewById(R.id.repeat_rl);
        tv_repeat_value = (TextView) findViewById(R.id.tv_repeat_value);
        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                time = getTime(date);
                date_tv.setText(time);
            }
        });
        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });
        repeat_rl.setOnClickListener(this);
        set_btn.setOnClickListener(this);


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        boolean isSwitchOn = pref.getBoolean("switch_on", false);
        if (isSwitchOn) {
            time = pref.getString("choose_time", "");
            repeat_freq = pref.getString("repeat_freq", "Everyday");
            cycle = pref.getInt("cycle", 0);
            date_tv.setText(time);
            tv_repeat_value.setText(repeat_freq);
            mSwitch.setChecked(true);
        } else {
            mSwitch.setChecked(false);
            secLayout.setVisibility(View.GONE);
        }


        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    secLayout.setVisibility(View.VISIBLE);
                } else {
                    time = "";
                    date_tv.setText("Choose time");
                    tv_repeat_value.setText("");
                    secLayout.setVisibility(View.GONE);
                    editor.clear();
                    editor.commit();
                }
            }
        });

        changeFont();

    }

    private void changeFont() {
        TextView graph = (TextView) findViewById(R.id.textR);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Regular.ttf");
        Typeface Chunkfive = Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        setFont(graph, Chunkfive);


    }

    private void setFont(TextView t, Typeface tf) {
        t.setTypeface(tf);
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.repeat_rl:
                selectRemindCycle();
                break;
            case R.id.set_btn:
                setClock();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        boolean isSwitchOn = pref.getBoolean("switch_on", false);
        Intent intent = new Intent();
        intent.putExtra("switch_on", isSwitchOn);
        if (isSwitchOn) {
            String choose_time = pref.getString("choose_time", "");
            int cycleRecorded = pref.getInt("cycle", 0);
            intent.putExtra("choose_time", choose_time);
            intent.putExtra("cycle", cycleRecorded);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setClock() {
        if (time != null && time.length() > 0) {
            editor.putBoolean("switch_on", true);
            editor.putString("choose_time", time);
            if (tv_repeat_value.getText() == "") {
                repeat_freq = "Everyday";
                cycle = 0;
                tv_repeat_value.setText(repeat_freq);
            }
            editor.putString("repeat_freq", repeat_freq);
            editor.putInt("cycle", cycle);
            editor.commit();
            String[] times = time.split(":");
            if (cycle == 0) {
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0);
            } if(cycle == -1){
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0);
            }else {
                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]));
                }
            }
            Toast.makeText(this, "Reminder set successfully!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Choose time!", Toast.LENGTH_LONG).show();
        }

    }


    public void selectRemindCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup
                .SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        repeat_freq = parseRepeat(repeat, 0);
                        tv_repeat_value.setText(repeat_freq);
                        cycle = repeat;
                        fp.dismiss();
                        break;
                    case 8:
                        tv_repeat_value.setText("Everyday");
                        repeat_freq = "Everyday";
                        cycle = 0;
                        fp.dismiss();
                        break;
                    case 9:
                        tv_repeat_value.setText("Only once");
                        repeat_freq = "Only Once";
                        cycle = -1;
                        fp.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }



    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "Mon.";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "Tues.";
                weeks = "2";
            } else {
                cycle = cycle + "," + "Tues.";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "Wed.";
                weeks = "3";
            } else {
                cycle = cycle + "," + "Wed.";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "Thur.";
                weeks = "4";
            } else {
                cycle = cycle + "," + "Thur.";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "Fri.";
                weeks = "5";
            } else {
                cycle = cycle + "," + "Fri.";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "Sat.";
                weeks = "6";
            } else {
                cycle = cycle + "," + "Sat.";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "Sun.";
                weeks = "7";
            } else {
                cycle = cycle + "," + "Sun.";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

}
