package sorting;

import java.util.*;

public class Main {

    enum Mode {LONG, LINE, WORD}
    enum Type {NATURAL, BY_COUNT}


    static Scanner scan = new Scanner(System.in);
    static Mode mode = Mode.LINE;
    static Type type = Type.NATURAL;


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
