package com.utils.util.date;


import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class DateUtil {
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal();

    private static final Object object = new Object();

    private static SimpleDateFormat getDateFormat(String pattern)
            throws RuntimeException {
        SimpleDateFormat dateFormat = (SimpleDateFormat) threadLocal.get();
        if (dateFormat == null) {
            synchronized (object) {
                if (dateFormat == null) {
                    dateFormat = new SimpleDateFormat(pattern);
                    dateFormat.setLenient(false);
                    threadLocal.set(dateFormat);
                }
            }
        }
        dateFormat.applyPattern(pattern);
        return dateFormat;
    }

    private static int getInteger(Date date, int dateType) {
        int num = 0;
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
            num = calendar.get(dateType);
        }
        return num;
    }

    private static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            myDate = addInteger(myDate, dateType, amount);
            dateString = DateToString(myDate, dateStyle);
        }
        return dateString;
    }

    private static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    private static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0L;
        Map map = new HashMap();
        List absoluteValues = new ArrayList();

        if ((timestamps != null) && (timestamps.size() > 0)) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(((Long) timestamps.get(i)).longValue() - ((Long) timestamps.get(j)).longValue());
                        absoluteValues.add(Long.valueOf(absoluteValue));
                        long[] timestampTmp = {((Long) timestamps.get(i)).longValue(), ((Long) timestamps.get(j)).longValue()};
                        map.put(Long.valueOf(absoluteValue), timestampTmp);
                    }
                }

                long minAbsoluteValue = -1L;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = ((Long) absoluteValues.get(0)).longValue();
                    for (int i = 1; i < absoluteValues.size(); i++) {
                        if (minAbsoluteValue > ((Long) absoluteValues.get(i)).longValue()) {
                            minAbsoluteValue = ((Long) absoluteValues.get(i)).longValue();
                        }
                    }
                }

                if (minAbsoluteValue != -1L) {
                    long[] timestampsLastTmp = (long[]) map.get(Long.valueOf(minAbsoluteValue));

                    long dateOne = timestampsLastTmp[0];
                    long dateTwo = timestampsLastTmp[1];
                    if (absoluteValues.size() > 1)
                        timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne : dateTwo;
                }
            } else {
                timestamp = ((Long) timestamps.get(0)).longValue();
            }
        }

        if (timestamp != 0L) {
            date = new Date(timestamp);
        }
        return date;
    }

    public static boolean isDate(String date) {
        boolean isDate = false;
        if ((date != null) &&
                (getDateStyle(date) != null)) {
            isDate = true;
        }

        return isDate;
    }

    public static DateStyle getDateStyle(String date) {
        DateStyle dateStyle = null;
        Map map = new HashMap();
        List timestamps = new ArrayList();
        for (DateStyle style : DateStyle.values())
            if (!style.isShowOnly()) {
                Date dateTmp = null;
                if (date != null)
                    try {
                        ParsePosition pos = new ParsePosition(0);
                        dateTmp = getDateFormat(style.getValue()).parse(date, pos);
                        if (pos.getIndex() != date.length())
                            dateTmp = null;
                    } catch (Exception e) {
                    }
                if (dateTmp != null) {
                    timestamps.add(Long.valueOf(dateTmp.getTime()));
                    map.put(Long.valueOf(dateTmp.getTime()), style);
                }
            }
        Date accurateDate = getAccurateDate(timestamps);
        if (accurateDate != null) {
            dateStyle = (DateStyle) map.get(Long.valueOf(accurateDate.getTime()));
        }
        return dateStyle;
    }

    public static Date StringToDate(String date) {
        DateStyle dateStyle = getDateStyle(date);
        return StringToDate(date, dateStyle);
    }

    public static Date StringToDate(String date, String pattern) {
        Date myDate = null;
        if (date != null)
            try {
                myDate = getDateFormat(pattern).parse(date);
            } catch (Exception e) {
            }
        return myDate;
    }

    public static Date StringToDate(String date, DateStyle dateStyle) {
        Date myDate = null;
        if (dateStyle != null) {
            myDate = StringToDate(date, dateStyle.getValue());
        }
        return myDate;
    }

    public static String DateToString(Date date, String pattern) {
        String dateString = null;
        if (date != null)
            try {
                dateString = getDateFormat(pattern).format(date);
            } catch (Exception e) {
            }
        return dateString;
    }

    public static String DateToString(Date date, DateStyle dateStyle) {
        String dateString = null;
        if (dateStyle != null) {
            dateString = DateToString(date, dateStyle.getValue());
        }
        return dateString;
    }

    public static String StringToString(String date, String newPattern) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newPattern);
    }

    public static String StringToString(String date, DateStyle newDateStyle) {
        DateStyle oldDateStyle = getDateStyle(date);
        return StringToString(date, oldDateStyle, newDateStyle);
    }

    public static String StringToString(String date, String olddPattern, String newPattern) {
        return DateToString(StringToDate(date, olddPattern), newPattern);
    }

    public static String StringToString(String date, DateStyle olddDteStyle, String newParttern) {
        String dateString = null;
        if (olddDteStyle != null) {
            dateString = StringToString(date, olddDteStyle.getValue(), newParttern);
        }
        return dateString;
    }

    public static String StringToString(String date, String olddPattern, DateStyle newDateStyle) {
        String dateString = null;
        if (newDateStyle != null) {
            dateString = StringToString(date, olddPattern, newDateStyle.getValue());
        }
        return dateString;
    }

    public static String StringToString(String date, DateStyle olddDteStyle, DateStyle newDateStyle) {
        String dateString = null;
        if ((olddDteStyle != null) && (newDateStyle != null)) {
            dateString = StringToString(date, olddDteStyle.getValue(), newDateStyle.getValue());
        }
        return dateString;
    }

    public static String addYear(String date, int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }

    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, 1, yearAmount);
    }

    public static String addMonth(String date, int monthAmount) {
        return addInteger(date, 2, monthAmount);
    }

    public static Date addMonth(Date date, int monthAmount) {
        return addInteger(date, 2, monthAmount);
    }

    public static String addDay(String date, int dayAmount) {
        return addInteger(date, 5, dayAmount);
    }

    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    public static String addHour(String date, int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }

    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, 11, hourAmount);
    }

    public static String addMinute(String date, int minuteAmount) {
        return addInteger(date, 12, minuteAmount);
    }

    public static Date addMinute(Date date, int minuteAmount) {
        return addInteger(date, 12, minuteAmount);
    }

    public static String addSecond(String date, int secondAmount) {
        return addInteger(date, 13, secondAmount);
    }

    public static Date addSecond(Date date, int secondAmount) {
        return addInteger(date, 13, secondAmount);
    }

    public static int getYear(String date) {
        return getYear(StringToDate(date));
    }

    public static int getYear(Date date) {
        return getInteger(date, 1);
    }

    public static int getMonth(String date) {
        return getMonth(StringToDate(date));
    }

    public static int getMonth(Date date) {
        return getInteger(date, 2) + 1;
    }

    public static int getDay(String date) {
        return getDay(StringToDate(date));
    }

    public static int getDay(Date date) {
        return getInteger(date, 5);
    }

    public static int getHour(String date) {
        return getHour(StringToDate(date));
    }

    public static int getHour(Date date) {
        return getInteger(date, 11);
    }

    public static int getMinute(String date) {
        return getMinute(StringToDate(date));
    }

    public static int getMinute(Date date) {
        return getInteger(date, 12);
    }

    public static int getSecond(String date) {
        return getSecond(StringToDate(date));
    }

    public static int getSecond(Date date) {
        return getInteger(date, 13);
    }

    public static String getDateYYYYMMDD(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD);
    }

    public static String getStringYYYYMMDDHHMMSS(Date date) {
        return DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    public static Date getDateYYYYMMDDHHMMSS(String date) {
        return StringToDate(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
    }

    public static String getTime(String date) {
        return StringToString(date, DateStyle.HH_MM_SS);
    }

    public static String getTime(Date date) {
        return DateToString(date, DateStyle.HH_MM_SS);
    }

    public static Week getWeek(String date) {
        Week week = null;
        DateStyle dateStyle = getDateStyle(date);
        if (dateStyle != null) {
            Date myDate = StringToDate(date, dateStyle);
            week = getWeek(myDate);
        }
        return week;
    }

    public static Week getWeek(Date date) {
        Week week = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(7) - 1;
        switch (weekNumber) {
            case 0:
                week = Week.SUNDAY;
                break;
            case 1:
                week = Week.MONDAY;
                break;
            case 2:
                week = Week.TUESDAY;
                break;
            case 3:
                week = Week.WEDNESDAY;
                break;
            case 4:
                week = Week.THURSDAY;
                break;
            case 5:
                week = Week.FRIDAY;
                break;
            case 6:
                week = Week.SATURDAY;
        }

        return week;
    }

    public static int getIntervalDays(Date smdate, Date bdate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / 86400000L;

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean gteNowDate(Date inputDate) {
        getDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(inputDate);
        c2.setTime(getDateYYYYMMDDHHMMSS(getStringYYYYMMDDHHMMSS(new Date())));
        if (c1.compareTo(c2) > 0) {
            result = true;
        }
        return result;
    }

    public static boolean lteNowDate(Date inputDate) {
        getDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(inputDate);
        c2.setTime(getDateYYYYMMDDHHMMSS(getStringYYYYMMDDHHMMSS(new Date())));
        if (c1.compareTo(c2) < 0) {
            result = true;
        }
        return result;
    }

    public static Date timestampToDate(Long timestamp)
            throws ParseException {
        getDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat df = (DateFormat) threadLocal.get();
        String time = df.format(timestamp);
        Date date = df.parse(time);
        return date;
    }

    public static Date dateTimestampToDate(String timestamp) throws ParseException {
        StringBuffer stringBuffer = new StringBuffer();
        if (timestamp.length() < 13) {
            int l = 13 - timestamp.length();
            for (int i = 0; i < l; i++) {
                stringBuffer.append("0");
            }
        }
        timestamp = timestamp + stringBuffer.toString();
        getDateFormat("yyyy-MM-dd");
        DateFormat df = (DateFormat) threadLocal.get();
        String time = df.format(Long.valueOf(timestamp));
        Date date = df.parse(time);
        return date;
    }

    public static Long currentTime(Date date) {
        return date.getTime() / 1000L;
    }

    public static Date find_max_01(List<Date> list, Date d) {
        if ((list == null) || (list.size() <= 0)) {
            return null;
        }
        if (list.size() == 1) {
            return (Date) list.get(0);
        }
        list = new ArrayList(list);
        Collections.sort(list);

        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cm = d.compareTo((Date) list.get(mid));
            if (cm < 0)
                right = mid - 1;
            else if (cm > 0)
                left = mid + 1;
            else {
                return (Date) list.get(mid);
            }
        }
        if (left <= 0) {
            return (Date) list.get(0);
        }
        if (left >= list.size()) {
            return (Date) list.get(list.size() - 1);
        }
        long dleft = d.getTime() - ((Date) list.get(left - 1)).getTime();
        long dright = ((Date) list.get(left)).getTime() - d.getTime();
        return dleft < dright ? (Date) list.get(left - 1) : (Date) list.get(left);
    }

    public static Date find_max_02(List<Date> list, Date d) {
        if ((list == null) || (list.size() <= 0)) {
            return null;
        }
        long gap = 9223372036854775807L;
        Date r = null;
        long time = d.getTime();
        for (Date t : list) {
            long tm = Math.abs(time - t.getTime());
            if (gap > tm) {
                gap = tm;
                r = t;
            }
        }
        return r;
    }

    public static TimeDiff getTimeDiff(Long endTime) {
        TimeDiff timeDiff = new TimeDiff();
        LocalDateTime start = LocalDateTime.ofEpochSecond(endTime, 0, ZoneOffset.of("+8"));
        LocalDateTime end = LocalDateTime.now(ZoneOffset.of("+8"));
        Duration duration = Duration.between(start, end);
        long days = duration.toDays(); //相差的天数
        long hours = duration.toHours();//相差的小时数
        long minutes = duration.toMinutes();//相差的分钟数
        long millis = duration.toMillis();//相差毫秒数
        long nanos = duration.toNanos();//相差的纳秒数
        timeDiff.setDays(days);
        timeDiff.setHours(hours);
        timeDiff.setMinutes(minutes);
        timeDiff.setMillis(millis);
        timeDiff.setNanos(nanos);
        return timeDiff;
    }

    @Data
    public static class TimeDiff {
        private Long nanos;
        private Long millis;
        private Long minutes;
        private Long hours;
        private Long days;
    }

    public static String timeDiffStr(Long time){
        String result = "";
        DateUtil.TimeDiff timeDiff = DateUtil.getTimeDiff(time);
        if (timeDiff.getMinutes() < 60) {
            result = timeDiff.getMinutes() + "分钟前";
        } else if (timeDiff.getHours() < 24) {
            result = timeDiff.getHours() + "小时前";
        } else {
            result = timeDiff.getDays() + "天前";
        }
        return result;
    }

}