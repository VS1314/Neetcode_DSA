package getsdeready.math;

public class ExcelSheetColumnTitle {

    public static String convertToTitle(int columnNumber) {
        // Your implementation.
        StringBuilder sb = new StringBuilder();
        while (columnNumber > 0) {
            int lastDigit = columnNumber % 26;
            if (lastDigit == 0)
                lastDigit = 26;
            char letter = (char) ('A' + lastDigit - 1);
            sb.append(letter);
            columnNumber = (columnNumber - lastDigit) / 26;
        }
        return sb.reverse().toString();
    }
}
