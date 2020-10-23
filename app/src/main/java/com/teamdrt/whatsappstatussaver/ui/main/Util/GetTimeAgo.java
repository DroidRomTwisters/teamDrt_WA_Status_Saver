package com.teamdrt.whatsappstatussaver.ui.main.Util;

import android.app.Application;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetTimeAgo extends Application {

    public static String getTimeAgo(long time, Context ctx) {
        if (time < 1000000000000L) {
            time *= 1000;
        }

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/YY");
        return simpleDateFormat.format(time);

    }
}
