package com.cn.sce;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateTime {
    private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Calendar calendar;

    public static DateTime now() {
        return new DateTime();
    }

    public static DateTime parse(String date, String format) throws ParseException {
        Date d = (new SimpleDateFormat(format)).parse(date);
        return new DateTime(d);
    }

    public static DateTime parse(String date) throws ParseException {
        return parse(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static DateTime parse(long time) {
        return new DateTime(time);
    }

    public DateTime() {
        this.calendar = Calendar.getInstance();
    }

    public DateTime(Date date) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTime(date);
    }

    public DateTime(long time) {
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(time);
    }

    public DateTime(int year, int month, int dayOfMonth, int hourOfDay, int minute, int second) {
        this.calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute, second);
    }

    public DateTime(int year, int month, int dayOfMonth) {
        this.calendar = new GregorianCalendar(year, month, dayOfMonth);
    }

    public String toString() {
        return this.toString("yyyy-MM-dd HH:mm:ss");
    }

    public String toString(String format) {
        Date date = this.calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public long getTime() {
        return this.calendar.getTimeInMillis();
    }

    public Date getDate() {
        return this.calendar.getTime();
    }

    public Timestamp getTimestamp() {
        return new Timestamp(this.getTime());
    }

    public Integer getYear() {
        return this.calendar.get(1);
    }

    public Integer getMonth() {
        return this.calendar.get(2);
    }

    public DayOfWeek getDayOfWeek() {
        Integer index = this.calendar.get(7);
        return DayOfWeek.of(index);
    }

    public Integer getDay() {
        return this.calendar.get(5);
    }

    public Integer getHour() {
        return this.calendar.get(11);
    }

    public Integer getMinute() {
        return this.calendar.get(12);
    }

    public Integer getSecond() {
        return this.calendar.get(13);
    }

    public DateTime addYear(Integer amount) {
        this.calendar.add(1, amount);
        return this;
    }

    public DateTime addMonth(Integer amount) {
        this.calendar.add(2, amount);
        return this;
    }

    public DateTime addDay(Integer amount) {
        this.calendar.add(5, amount);
        return this;
    }

    public DateTime addHour(Integer amount) {
        this.calendar.add(11, amount);
        return this;
    }

    public DateTime addMinute(Integer amount) {
        this.calendar.add(12, amount);
        return this;
    }

    public DateTime addSecond(Integer amount) {
        this.calendar.add(13, amount);
        return this;
    }

    public TimeSpan substract(DateTime dt) {
        return new TimeSpan(this.getTime() - dt.getTime());
    }
}
