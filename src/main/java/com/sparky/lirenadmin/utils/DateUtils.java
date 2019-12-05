package com.sparky.lirenadmin.utils;

import com.alibaba.fastjson.JSONArray;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {
    private final static String SECOND_REGEX = "\\d{4}(\\-|\\/|.)\\d{1,2}(\\-|\\/|.)\\d{1,2}\\s\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}";
    private final static String MINUTE_REGEX = "\\d{4}(\\-|\\/|.)\\d{1,2}(\\-|\\/|.)\\d{1,2}\\s\\d{1,2}\\:\\d{1,2}";
    private final static String DATE_REGEX = "\\d{4}(\\-|\\/|.)\\d{1,2}(\\-|\\/|.)\\d{1,2}";
    private final static String YEAR_MONTH_REGEX = "\\d{4}(\\-|\\/|.)\\d{1,2}";
    private final static String HOUR_MINUTE_REGEX = "\\d{1,2}\\:\\d{1,2}";
    private final static String HOUR_MINUTE_SECOND_REGEX = "\\d{1,2}\\:\\d{1,2}:\\d{1,2}";

    public static void main(String[] args) {
//        System.out.println(com.sparky.lirenadmin.utils.DateUtils.formatDate(getDateTime("2919-1-2"),"yyyy-MM-dd"));
//        System.out.println(formatDate(getDateTime("2919-1-2 10:1:2"),"yyyy-MM-dd HH:mm:ss"));
//        System.out.println(com.sparky.lirenadmin.utils.DateUtils.formatDate(getDateTime("10:1:1"),"HH:mm:ss"));
//        System.out.println(com.sparky.lirenadmin.utils.DateUtils.formatDate(getDateTime("10:01:01"),"HH:mm:ss"));

        pagingQuery(-1);
        pagingQuery(0);
        pagingQuery(1);
        pagingQuery(2);
        pagingQuery(3);
        pagingQuery(4);
        pagingQuery(5);
        pagingQuery(6);
    }

    private static List<String > pagingQuery(int currentPage){
        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("112");
        strings.add("113");
        strings.add("114");
        strings.add("115");
        strings.add("116");
        strings.add("117");
        strings.add("118");
        strings.add("119");
        strings.add("120");
        return pagingQuery(currentPage, strings);
    }

    private static List<String> pagingQuery(int currentPage, List<String> hashList){

        int pageSize = 3;

        int total = hashList.size();
        if (CollectionUtils.isEmpty(hashList)){
            return new ArrayList<>();
        }

        //计算的出
        int totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        if (currentPage < 1) {
            currentPage = totalPage;
        }

        int from = 0;
        int to = 0;

        if (total < 1){
            return null;
        }

        if (currentPage >= totalPage){
            from = (totalPage - 1) * pageSize;
            to = total;
        }else{
            from = (currentPage - 1) * pageSize;
            to = currentPage * pageSize;
        }
        return hashList.subList(from, to);
    }

    public static String formatDate(Date date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date yestoday(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

    /**
     * 获取指定月的第一天
     * @param date
     * @return
     */
    public static Date getMonthBegining(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return getDayBegining(calendar.getTime());
    }

    /**
     * 获取指定月最后一天最后一秒
     * @param date
     * @return
     */
    public static Date getMonthEnding(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer day = calendar.getMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return getDayEnding(calendar.getTime());
    }

    /**
     * 获取指定日期的0点
     * @param date
     * @return
     */
    public static Date getDayBegining(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的23点59分59秒
     * @param date
     * @return
     */
    public static Date getDayEnding(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        return calendar.getTime();
    }

    public static int diff(Date begin, Date end){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(begin);
        int beginDay = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(end);
        int endDay = calendar.get(Calendar.DAY_OF_YEAR);
        return endDay -  beginDay;
    }

    public static Date getMonth(String month){
        Date thisMonth = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
            thisMonth = df.parse(month);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式转换失败.month:" + month);
        }
        return thisMonth;
    }

    public static Date getDate(String date) {
        Date d = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            d = df.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式转换失败.month:" + date);
        }
        return d;
    }

    public static Date getDateTime(String date) {
        String pattern1 = "yyyy-MM";
        String pattern2 = "yyyy-MM-dd";
        String pattern3 = "yyyy-MM-dd HH:mm:ss";
        String pattern4 = "yyyy-MM-dd HH:mm";
        Date d = null;
        try {
            if (date.matches(YEAR_MONTH_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat(pattern1);
                d = df.parse(date);
            }else if(date.matches(DATE_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat(pattern2);
                d = df.parse(date);
            }else if(date.matches(SECOND_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat(pattern3);
                d = df.parse(date);
            }else if(date.matches(MINUTE_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat(pattern4);
                d = df.parse(date);
            }else if(date.matches(HOUR_MINUTE_SECOND_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                d = df.parse(date);
            }else if(date.matches(HOUR_MINUTE_REGEX)){
                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                d = df.parse(date);
            }else{
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("日期格式转换失败.month:" + date);
        }
        return d;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

}
