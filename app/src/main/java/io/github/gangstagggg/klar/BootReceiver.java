package io.github.gangstagggg.klar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/** Planlegger påminnelsene på nytt etter at telefonen har startet. */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Reminders.scheduleAll(c, Reminders.stored(c));
        }
    }
}
