package getsdeready.math;

public class DayoftheYear {

    public static int dayOfYear(String date) {
        int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8, 10));
        boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
        if (isLeapYear) {
            monthDays[1] = 29;
        }
        for (int i = 0; i < month - 1; i++) {
            day += monthDays[i];
        }
        return day; // return leap && month > 2 ? day + 1 : day;
    }
}
