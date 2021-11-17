package zzuli.zw.blog.utils;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static String weekOfMonth(Date date){
        Calendar cal = Calendar.getInstance();
        String[] weekDays = { "7", "1", "2", "3", "4", "5", "6" };
        try {
            cal.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) w = 0;
        return weekDays[w];
    }

    public static String weekOfYear(){
        LocalDate localDate = LocalDate.now();
        WeekFields weekFields = WeekFields.ISO;
        int weekNumber = localDate.get(weekFields.weekOfWeekBasedYear());
        return String.valueOf(weekNumber);
    }
}
