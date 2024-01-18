package com.bijlipay.Unzipping.File.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String longToDate(long longDate, String pattern) {
        Date date = new Date(longDate * 1000L);
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateConvertToString = formatter.format(date);
        return dateConvertToString;
    }

    public static String currentDate(String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = new Date();
        String currentDate = formatter.format(date);
        return currentDate;
    }
}
