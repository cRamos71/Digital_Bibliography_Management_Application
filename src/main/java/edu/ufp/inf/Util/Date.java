package edu.ufp.inf.Util;

import edu.princeton.cs.algs4.StdOut;

import java.io.Serializable;

public class Date implements Comparable<Date>, Serializable {
    private static final int[] DAYS = new int[]{0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private final int month;
    private final int day;
    private final int year;

    public Date(int month, int day, int year) {
        if (!isValid(month, day, year)) {
            throw new IllegalArgumentException("Invalid date");
        } else {
            this.month = month;
            this.day = day;
            this.year = year;
        }
    }

    public Date(String date) {
        String[] fields = date.split("/");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date");
        } else {
            this.month = Integer.parseInt(fields[0]);
            this.day = Integer.parseInt(fields[1]);
            this.year = Integer.parseInt(fields[2]);
            if (!isValid(this.month, this.day, this.year)) {
                throw new IllegalArgumentException("Invalid date");
            }
        }
    }

    public int month() {
        return this.month;
    }

    public int day() {
        return this.day;
    }

    public int year() {
        return this.year;
    }

    private static boolean isValid(int m, int d, int y) {
        if (m >= 1 && m <= 12) {
            if (d >= 1 && d <= DAYS[m]) {
                return m != 2 || d != 29 || isLeapYear(y);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean isLeapYear(int y) {
        if (y % 400 == 0) {
            return true;
        } else if (y % 100 == 0) {
            return false;
        } else {
            return y % 4 == 0;
        }
    }

    public edu.princeton.cs.algs4.Date next() {
        if (isValid(this.month, this.day + 1, this.year)) {
            return new edu.princeton.cs.algs4.Date(this.month, this.day + 1, this.year);
        } else {
            return isValid(this.month + 1, 1, this.year) ? new edu.princeton.cs.algs4.Date(this.month + 1, 1, this.year) : new edu.princeton.cs.algs4.Date(1, 1, this.year + 1);
        }
    }

    public boolean isAfter(Date that) {
        return this.compareTo(that) > 0;
    }

    public boolean isBefore(Date that) {
        return this.compareTo(that) < 0;
    }

    public int compareTo(Date that) {
        if (this.year < that.year) {
            return -1;
        } else if (this.year > that.year) {
            return 1;
        } else if (this.month < that.month) {
            return -1;
        } else if (this.month > that.month) {
            return 1;
        } else if (this.day < that.day) {
            return -1;
        } else {
            return this.day > that.day ? 1 : 0;
        }
    }

    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else if (other == null) {
            return false;
        } else if (other.getClass() != this.getClass()) {
            return false;
        } else {
            Date that = (Date)other;
            return this.month == that.month && this.day == that.day && this.year == that.year;
        }
    }

    public int hashCode() {
        return this.day + 31 * this.month + 372 * this.year;
    }

    public static void main(String[] args) {
        edu.princeton.cs.algs4.Date today = new edu.princeton.cs.algs4.Date(2, 25, 2004);
        StdOut.println(today);

        for(int i = 0; i < 10; ++i) {
            today = today.next();
            StdOut.println(today);
        }

        StdOut.println(today.isAfter(today.next()));
        StdOut.println(today.isAfter(today));
        StdOut.println(today.next().isAfter(today));
        edu.princeton.cs.algs4.Date birthday = new edu.princeton.cs.algs4.Date(10, 16, 1971);
        StdOut.println(birthday);

        for(int i = 0; i < 10; ++i) {
            birthday = birthday.next();
            StdOut.println(birthday);
        }

    }
}
