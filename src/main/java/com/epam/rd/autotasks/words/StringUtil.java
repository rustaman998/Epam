package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;

public class StringUtil {

    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (sample == null || words == null||  words.length == 0) {
            return 0;
        }

        int count = 0;
        for (String word : words) {
            if (word != null && word.trim().equalsIgnoreCase(sample.trim())) {
                count++;
            }
        }

        return count;
    }


    public static String[] splitWords(String text) {
        if (text == null || text.isEmpty()||  text.matches("[ ,.\\-;:!?]+")) {
            return null;
        }

        return Arrays.stream(text.split("[ ,.\\-;:!?]+"))
                .filter(word -> !word.isEmpty())
                .toArray(String[]::new);
    }


    public static String convertPath(String path, boolean toWindows) {
        if (path == null || path.isEmpty() || isIllegalPath(path)) {
            return null;
        }

        if (toWindows) {
            return convertToWindowsPath(path);
        } else {
            return convertToUnixPath(path);
        }
    }


    private static String convertToWindowsPath(String path) {
        if (path.startsWith("~/")) {
            // Convert ~ to the user directory in Windows path
            return "C:\\User" + path.substring(1).replace('/', '\\');
        } else if (path.startsWith("/")) {
            // Convert Unix path to Windows path
            return "C:" + path.replace('/', '\\');
        } else if (path.equals("/")) {
            // Special case for root directory on Unix
            return "C:\\";
        } else if (path.startsWith("~")) {
            // Handle other cases where ~ might appear in the path
            return "C:\\User" + path.substring(1).replace('/', '\\');
        }
        // Already a Windows path
        return path.replace('/', '\\');
    }

    private static String convertToUnixPath(String path) {
        if (path == null || path.isEmpty()) {
            return "/default/path"; // Замените на значение по умолчанию
        }

        // Already a Unix path
        if (path.startsWith("/") || path.startsWith("~")) {
            return path.replace('\\', '/');
        }

        if (path.startsWith("C:\\User")) {
            // Convert ~ to the user directory in Unix path
            return "~" + path.substring(7).replace('\\', '/');
        } else if (path.startsWith("C:\\")) {
            // Convert Windows path to Unix path
            return "/" + path.substring(3).replace('\\', '/');
        }

        // ... (other conditions)

        // Default case, return the input path after replacing backslashes
        return path.replace('\\', '/');
    }

        private static boolean isIllegalPath(String path) {
        // Add additional conditions to check for illegal paths
        return path.contains("\\/") || path.contains("//") ||
                path.matches(".*[A-Za-z]:\\\\\\\\$") || path.matches(".*/~.*");
    }

//    private static boolean isIllegalPath(String path) {
//        // Add additional conditions to check for illegal paths
//        return path.contains("\\/") || path.contains("//") ||
//                path.matches(".*[A-Za-z]:\\\\\\\\$") || path.matches(".*/~.*");
//    }
    public static String joinWords(String[] words) {
        if (words == null || words.length == 0 || Arrays.stream(words).allMatch(String::isEmpty)) {
            return null;
        }

        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (String word : words) {
            if (!word.isEmpty()) {
                joiner.add(word);
            }
        }

        return joiner.toString();
    }
    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));
        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}