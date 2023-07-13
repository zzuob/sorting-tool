package sorting;

import java.util.*;

public class Main {

    enum Mode {LONG, LINE, WORD}
    enum Type {NATURAL, BY_COUNT}


    static Scanner scan = new Scanner(System.in);
    static Mode mode = Mode.LINE;
    static Type type = Type.NATURAL;

    @Deprecated
    public static Object[] longCount() {
        // get number of integers, the largest integer, and it's total occurrences from input
        int numberOfIntegers = 0;
        long largestNumber = 0;
        int largestNumberRepeats = 0;
        boolean first = true;
        List<Long> allNumbers = new ArrayList<>();
        while (scan.hasNextLong()) {
            long number = scan.nextLong();
            numberOfIntegers++;
            allNumbers.add(number);
            if (first) {
                largestNumber = number;
                largestNumberRepeats = 1;
                first = false;
            } else if (largestNumber < number) {
                largestNumber = number;
                largestNumberRepeats = 1;
            } else if (largestNumber == number) {
                largestNumberRepeats++;
            }
        }
        String result = collectionToSortedString(allNumbers, " ");
        return new Object[]{numberOfIntegers, largestNumber, largestNumberRepeats, result};
    }

    @Deprecated
    private static String collectionToSortedString(Set<String> set, String delimiter) {
        String[] array = set.toArray(new String[0]);
        Arrays.sort(array);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            result.append(array[i]);
            if (i != array.length - 1) { // not last element
                result.append(delimiter);
            }
        }
        return result.toString();
    }

    @Deprecated
    private static String collectionToSortedString(List<Long> list, String delimiter) {
        Collections.sort(list);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
            if (i != list.size() - 1) { // not last element
                result.append(delimiter);
            }
        }
        return result.toString();
    }

    @Deprecated
    public static Object[] lineCount() {
        // get number of lines, the longest line(s) and their total occurrences from input
        int numberOfLines = 0;
        String longestLine = "";
        Set<String> longestLines = new HashSet<>();
        List<String> allLines = new ArrayList<>();
        int longestLineRepeats = 0;
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            numberOfLines++;
            if (line.length() > longestLine.length()) {
                longestLine = line;
                longestLineRepeats = 1;
                longestLines = new HashSet<>();
                longestLines.add(line);
            } else if (line.length() == longestLine.length()) {
                longestLineRepeats++;
                longestLines.add(line);
            }
        }
        String result = collectionToSortedString(longestLines, "\n");
        return new Object[]{numberOfLines, result, longestLineRepeats};
    }

    @Deprecated
    public static Object[] wordCount() {
        // get number of words, the longest word(s) and their total occurrences from input
        int numberOfWords = 0;
        String longestWord = "";
        int longestWordRepeats = 0;
        Set<String> longestWords = new HashSet<>();
        while (scan.hasNext()) {
            String word = scan.next();
            numberOfWords++;
            if (word.length() > longestWord.length()) {
                longestWord = word;
                longestWordRepeats = 1;
                longestWords = new HashSet<>();
                longestWords.add(word);
            } else if (word.length() == longestWord.length()) {
                longestWordRepeats++;
                longestWords.add(word);
            }
        }
        String result = collectionToSortedString(longestWords, " ");
        return new Object[]{numberOfWords, result, longestWordRepeats};
    }

    private static boolean getLoopCondition() {
        return switch (mode) {
            case LONG -> scan.hasNextLong();
            case LINE -> scan.hasNextLine();
            case WORD -> scan.hasNext();
        };
    }
    public static List<Object> getDataSet() {
        List<Object> allData = new ArrayList<>();
        while (getLoopCondition()) {
            Object data = switch (mode) {
                case LONG -> scan.nextLong();
                case LINE -> scan.nextLine();
                case WORD -> scan.next();
            };
            allData.add(data);
        }
        return allData;
    }

    public static void main(final String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("-sortingType".equals(args[i]) && i != args.length - 1) {
                if ("byCount".equalsIgnoreCase(args[i + 1])) {
                    type = Type.BY_COUNT;
                }
            } else if ("-dataType".equals(args[i]) && i != args.length - 1) {
                String value = args[i+1].toUpperCase();
                switch (value) {
                    case "LONG", "LINE", "WORD" -> mode = Mode.valueOf(value);
                }
            }
        }
        List<Object> allData = getDataSet();
        Sorter sorter = new Sorter(allData);
        String dataType = switch (mode) {
            case LONG -> "numbers";
            case LINE -> "lines";
            case WORD -> "words";
        };
        System.out.printf("Total %s: %d\n", dataType, sorter.getTotalOccurrences());
        if (type == Type.BY_COUNT) {
            List<Object> result = sorter.sortByCount();
            for (Object object: result) {
                System.out.println(sorter.getObjectStats(object));
            }

        } else {
            List<Object> result = sorter.sortNaturally();
            String spacing = Mode.LINE == mode ? "\n" : " ";
            for (Object object: result) {
                System.out.print(object+spacing);
            }
        }
    }
}
