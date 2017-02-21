package cis350.upenn.edu.cathealthapp.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import cis350.upenn.edu.cathealthapp.Core.TodayPageInfo;
import cis350.upenn.edu.cathealthapp.R;

/**
 * Created by zhixu on 11/1/16.
 */

public class ReminderAdapter extends BaseAdapter {
    private Context context;
    private List<TodayPageInfo> tpis;
    private TextView tv;
    private CheckBox cb;

    public ReminderAdapter(Context context, List<TodayPageInfo> tpis) {
        this.context = context;
        this.tpis = tpis;
    }

    @Override
    public int getCount() {
        return tpis.size();
    }

    @Override
    public Object getItem(int position) {
        return tpis.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.layout_reminder_views, null);

            Views views = new Views();
            tv = (TextView) convertView.findViewById(R.id.reminder_text);
            cb = (CheckBox) convertView.findViewById(R.id.is_done);
            cb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    tpis.get(position).setDone(cb.isChecked());
                }
            });

            views.tv = tv;
            views.cb = cb;
            convertView.setTag(views);
        } else {
            Views views = (Views) convertView.getTag();
            tv = views.tv;
            cb = views.cb;
        }
        TodayPageInfo tpi = tpis.get(position);
        tv.setText(tpi.getReminder());
        cb.setChecked(tpi.isDone());
        return convertView;
    }

    public class Views {
        TextView tv;
        CheckBox cb;
    }
}
