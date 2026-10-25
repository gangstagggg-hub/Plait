package io.github.gangstagggg.klar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONObject;

/** Planlegger påminnelser med AlarmManager, slik at de kommer selv om appen er lukket. */
final class Reminders {
    private static final String PREFS = "reminders";
    private static final String KEY = "json";

    private Reminders() {}

    /** Erstatter alle planlagte påminnelser med listen webappen sender: [{id, t, title, body}]. */
    static void apply(Context c, String json) {
        SharedPreferences sp = c.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        try {
            JSONArray old = new JSONArray(sp.getString(KEY, "[]"));
            for (int i = 0; i < old.length(); i++) {
                cancel(c, am, old.getJSONObject(i).getInt("id"));
            }
        } catch (Exception ignored) {
            // Ingen gamle påminnelser å fjerne.
        }
        sp.edit().putString(KEY, json).apply();
        scheduleAll(c, json);
    }

    /** Brukes også etter omstart av telefonen. */
    static void scheduleAll(Context c, String json) {
        AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        long now = System.currentTimeMillis();
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject o = arr.getJSONObject(i);
                long t = o.getLong("t");
                if (t <= now) {
                    continue;
                }
                Intent in = new Intent(c, ReminderReceiver.class);
                in.putExtra("id", o.getInt("id"));
                in.putExtra("title", o.optString("title", "Klar"));
                in.putExtra("body", o.optString("body", ""));
                PendingIntent pi = PendingIntent.getBroadcast(c, o.getInt("id"), in,
                        PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, t, pi);
            }
        } catch (Exception ignored) {
            // Ugyldig liste, ingenting å planlegge.
        }
    }

    static String stored(Context c) {
        return c.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY, "[]");
    }

    private static void cancel(Context c, AlarmManager am, int id) {
        Intent in = new Intent(c, ReminderReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(c, id, in,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_NO_CREATE);
        if (pi != null) {
            am.cancel(pi);
            pi.cancel();
        }
    }
}
