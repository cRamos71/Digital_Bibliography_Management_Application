package edu.ufp.inf.Util;

import java.util.Objects;

public class Date {

    private short day;
    private short month;
    private int year;

    public short getDay() {
        return day;
    }

    public void setDay(short day) {
        this.day = day;
    }

    public short getMonth() {
        return month;
    }

    public void setMonth(short month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Default constructor
     */
    public Date() {
    }

    public Date(short day, short month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "date{" +
                "day=" + day +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date date)) return false;
        return day == date.day && month == date.month && year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    /**
     * @param d
     * @return
     */
    public int compareTo(Date d) {
        if(this.year == d.year && this.month == d.month && this.day == d.day) {
            return 0;
        } else if(this.year == d.year){
            if(this.month == d.month){
                return (this.day - d.day) / Math.abs(this.day - d.day);
            }else {
                return (this.month - d.month) / Math.abs(this.month - d.month);
            }
        }
        return (this.year - d.year) / Math.abs(this.year - d.year);
    }

    /**
     * @param d
     * @return true if this date
     */
    public boolean afterDate(Date d) {
        return this.compareTo(d) > 0;
    }

    /**
     * @param d
     * @return
     */
    public boolean beforeDate(Date d) {
        return this.compareTo(d) < 0;
    }

    /**
     * @param d
     * @return
     */
    public int diferenceDays(Date d) {

        int thisDayCount = this.year * 365 + this.month * 30 + this.day;
        int otherDayCount = d.year * 365 + d.month * 30 + d.day;

        return Math.abs(thisDayCount - otherDayCount);

    }

    /**
     * @param d
     * @return
     */
    public int diferenceMonths(Date d) {
        int thisMonthCount = this.year * 12 + this.month;
        int otherMonthCount = d.year * 12 + d.month;

        return Math.abs(thisMonthCount - otherMonthCount);
    }

    /**
     * @param d
     * @return
     */
    public int diferenceYears(Date d) {
        return Math.abs(this.year - d.year);
    }

}
