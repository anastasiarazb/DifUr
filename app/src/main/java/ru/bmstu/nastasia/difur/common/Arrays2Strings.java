package ru.bmstu.nastasia.difur.common;

public class Arrays2Strings {

    public static String array1DtoString(Object[] messages, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Object msg: messages) {
            sb.append(msg).append(delimiter);
        }
        return sb.toString();
    }

    public static String array1DtoString(Object[] messages, char delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Object msg: messages) {
            sb.append(msg).append(delimiter);
        }
        return sb.toString();
    }

    public static String arr2DtoString(Object[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                sb.append(arr[i][j]).append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public static String arr1DtoString(Object[] arr) {
        int n = arr.length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            sb.append(arr[i]).append(' ');
        }

        return sb.toString();
    }
}
