package io.github.gangstagggg.klar;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Viser varselet når en påminnelse forfaller. */
public class ReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL = "paminnelser";

    @Override
    public void onReceive(Context c, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        NotificationManager nm = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            return;
        }
        nm.createNotificationChannel(new NotificationChannel(CHANNEL, "Påminnelser",
                NotificationManager.IMPORTANCE_HIGH));
        Intent open = new Intent(c, MainActivity.class);
        open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pi = PendingIntent.getActivity(c, id, open,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        Notification n = new Notification.Builder(c, CHANNEL)
                .setSmallIcon(R.drawable.ic_notify)
                .setContentTitle(title == null || title.isEmpty() ? "Klar" : title)
                .setContentText(body == null ? "" : body)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        try {
            nm.notify(id, n);
        } catch (SecurityException ignored) {
            // Varsler er ikke tillatt.
        }
    }
}
