package com.cn.sce;

public class TimeSpan {
    private long ticks;

    public static TimeSpan substract(DateTime dt1, DateTime dt2) {
        return new TimeSpan(dt1.getTime() - dt2.getTime());
    }

    public static TimeSpan parse(DateTime dateTime) {
        return new TimeSpan(dateTime.getTime());
    }

    public long getTicks() {
        return this.ticks;
    }

    public TimeSpan(long millis) {
        this.ticks = millis;
    }

    public double getTotalMilliseconds() {
        return (double)this.ticks;
    }

    public double getTotalseconds() {
        return (double)this.ticks / 1000.0D;
    }

    public double getTotalMinutes() {
        return (double)this.ticks / 60000.0D;
    }

    public double getTotalHours() {
        return (double)this.ticks / 3600000.0D;
    }

    public double getTotalDays() {
        return (double)this.ticks / 8.64E7D;
    }
}
