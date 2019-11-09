package com.sparky.lirenadmin.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static void main(String[] args) {
        System.out.println(yestoday(new Date()));
    }

    public static Date yestoday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

}
