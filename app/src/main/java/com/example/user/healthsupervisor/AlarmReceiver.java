package com.example.user.healthsupervisor;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        mylayout.getTextView2().setText("Enough Rest. Do Work Now!");
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }
}