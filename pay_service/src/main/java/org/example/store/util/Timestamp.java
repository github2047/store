package org.example.store.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Timestamp {


    public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    static {
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static String now() {
        return df.format(new Date());
    }

    public static String format(Date date) {
        return df.format(date);
    }

    public static String format(Long time) {
        return df.format(new Date(time));
    }

    public static Date parse(String timestamp) throws ParseException {
        return df.parse(timestamp);
    }
}
