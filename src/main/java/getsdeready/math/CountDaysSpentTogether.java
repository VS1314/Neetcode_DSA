package getsdeready.math;

public class CountDaysSpentTogether {

    public static int dayOfYear(String date) {
        // Parse month and day.
        // Add completed months to day.
        // Return the total.
        int[] monthDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int month = Integer.parseInt(date.substring(0, 2));
        int day = Integer.parseInt(date.substring(3, 5));
        for (int i = 0; i < month - 1; i++) {
            day += monthDays[i];
        }
        return day;

    }

    public static int countDaysTogether(String arriveAlice, String leaveAlice, String arriveBob, String leaveBob) {
        // Reuse dayOfYear and the overlap rules.
        int aliceStart = dayOfYear(arriveAlice);
        int bobStart = dayOfYear(arriveBob);
        int aliceEnd = dayOfYear(leaveAlice);
        int bobEnd = dayOfYear(leaveBob);

        int start = Math.max(aliceStart, bobStart);
        int end = Math.min(aliceEnd, bobEnd);

        if (start > end)
            return 0;
        return end - start + 1;
    }
}
