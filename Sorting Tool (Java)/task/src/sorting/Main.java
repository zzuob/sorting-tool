package sorting;

import java.util.*;

public class Main {

    enum Mode {LONG, LINE, WORD}

    static Scanner scan = new Scanner(System.in);

    public static double percentageOccurrences(int repeats, int total) {
        return ((double)repeats/total)*100;
    }
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
        Collections.sort(allNumbers);
        return new Object[]{numberOfIntegers, largestNumber, largestNumberRepeats, allNumbers};
    }

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

    public static Object[] lineCount() {
        // get number of lines, the longest line(s) and their total occurrences from input
        int numberOfLines = 0;
        String longestLine = "";
        Set<String> longestLines = new HashSet<>();
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
    public static void main(final String[] args) {
        Mode mode = Mode.WORD;
        boolean sortIntegers = false;
        for (int i = 0; i < args.length; i++) {
            if ("-sortIntegers".equals(args[i])) {
                //mode = Mode.SORT_INTEGERS;
                break;
            }
            if ("-dataType".equals(args[i]) && i != args.length - 1) {
                String value = args[i+1].toUpperCase();
                switch (value) {
                    case "LONG", "LINE", "WORD" -> mode = Mode.valueOf(value);
                }
            }
        }
        // { int frequency of data, (Object) the largest occurrence, int number of repeats }
        Object[] result = switch (mode) {
            case LONG -> longCount();
            case LINE -> lineCount();
            case WORD -> wordCount();
        };
        String type = switch (mode) {
            case LONG -> "number";
            case LINE -> "line";
            case WORD -> "word";
        };
        String max = switch (mode) {
            case LONG -> "greatest";
            case LINE,WORD -> "longest";
        };
        String spacing = Mode.LINE == mode ? "\n" : " ";
        double percentage = percentageOccurrences((Integer) result[2], (Integer) result[0]);
        String occurrences = String.format("(%d time(s), %d%%).", (Integer) result[2], Math.round(percentage));
        System.out.println("Total "+type+"s: "+result[0]);
        System.out.println("The greatest "+type+":"+spacing+result[1]+spacing+occurrences);
    }
}
