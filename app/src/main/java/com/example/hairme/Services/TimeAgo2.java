package com.example.hairme.Services;

import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeAgo2 {

    public String covertTimeToText(String dataDate) {

        String convTime = null;

        String prefix = "حتي";
        String suffix = "قبل";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " ثواني " + suffix;
            } else if (minute < 60) {
                convTime = minute + " دقائق "+suffix;
            } else if (hour < 24) {
                convTime = hour + " ساعات "+suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " سنه " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " شهر " + suffix;
                } else {
                    convTime = (day / 7) + " أسبوع " + suffix;
                }
            } else if (day < 7) {
                convTime = day+" أيام "+suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }


    public String covertTimeToAfter(String dataDate) {

        String convTime = null;

        String prefix = "حتي";

        try {

            Date nowTime = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            long dateDiff  = Math.abs( nowTime.getTime()- pasTime.getTime());

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour   = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day  = TimeUnit.MILLISECONDS.toDays(dateDiff);


            if (second < 60) {
                convTime = prefix + second +" ثواني " ;
            } else if (minute < 60) {
                convTime = minute + " دقائق "+prefix;
            } else if (hour < 24) {
                convTime = hour + " ساعات "+prefix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " سنه " + prefix;
                } else if (day > 30) {
                    convTime = (day / 30) + " شهر " + prefix;
                } else {
                    convTime = (day / 7) + " أسبوع " + prefix;
                }
            } else if (day < 7) {
                convTime = day+" أيام "+prefix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }
}
