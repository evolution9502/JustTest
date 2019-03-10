package com.yunk.carousellnews.DataUtilities;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by evolu on 2019/3/9.
 */

public class RelativeTimeParser {
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEK_MILLIS = 7 * DAY_MILLIS;
    private static final long MONTH_MILLIS = 30 * DAY_MILLIS;
    private static final long YEAR_MILLIS =12 * MONTH_MILLIS;

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else if (diff >= 3 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " days  ago";
        } else if (diff >= WEEK_MILLIS) {
            return diff / WEEK_MILLIS + " weeks ago";
        } else if (diff >= MONTH_MILLIS) {
            return diff / MONTH_MILLIS + " months ago";
        } else {
            return diff / YEAR_MILLIS + " years ago";
        }
    }
}
