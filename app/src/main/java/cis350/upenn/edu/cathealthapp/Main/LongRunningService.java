package cis350.upenn.edu.cathealthapp.Main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;


public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		AlarmManagerUtil.setAlarm(this, 1, 20, 0, 0,
				0);
		return super.onStartCommand(intent, flags, startId);
	}

}
