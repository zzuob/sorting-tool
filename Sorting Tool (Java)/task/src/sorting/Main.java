package sorting;

import java.io.*;
import java.util.*;

public class Main {

    enum Mode {LONG, LINE, WORD}
    enum Type {NATURAL, BYCOUNT}


    static Scanner scan = new Scanner(System.in);
    static Mode mode = Mode.LINE;
    static Type type = Type.NATURAL;
    static File outFile = null;


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
            Object data = null;
            switch (mode) {
                case LONG -> {
                    String nextData = scan.next();
                    if (nextData.matches("-?\\d+")) {
                        data = Long.parseLong(nextData);
                    } else {
                        System.out.println("\""+nextData+"\" is not a long. It will be skipped.");
                    }
                }
                case LINE -> data = scan.nextLine();
                case WORD -> data = scan.next();
            }
            allData.add(data);
        }
        return allData;
    }

    private static void appendErrorMessage(String text, StringBuilder errorMessage) {
        if (!errorMessage.isEmpty()) {
            errorMessage.append("\n");
        }
        errorMessage.append(text);
    }
    private static String validateArgs(String[] args) {
        StringBuilder errorMessage  = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                switch (args[i].toUpperCase()) {
                    case "-SORTINGTYPE" -> {
                        if (i != args.length - 1) {
                            String value = args[i+1].toUpperCase();
                            switch (value) {
                                case "NATURAL", "BYCOUNT" -> type = Type.valueOf(value);
                                default -> appendErrorMessage("No sorting type defined!", errorMessage);
                            }
                        } else {
                            appendErrorMessage("No sorting type defined!", errorMessage);
                        }
                    }
                    case "-DATATYPE" -> {
                        if (i != args.length - 1) {
                            String value = args[i+1].toUpperCase();
                            switch (value) {
                                case "LONG", "LINE", "WORD" -> mode = Mode.valueOf(value);
                                default -> appendErrorMessage("No data type defined!", errorMessage);
                            }
                        } else {
                            appendErrorMessage("No data type defined!", errorMessage);
                        }
                    }
                    case "-INPUTFILE" -> {
                        if (i != args.length - 1) {
                            File inFile = new File(args[i+1]);
                            try {
                                scan = new Scanner(inFile);
                            } catch (FileNotFoundException e) {
                                appendErrorMessage("Input file not found!", errorMessage);
                            }
                        } else {
                            appendErrorMessage("No input file defined!", errorMessage);
                        }
                    }
                    case "-OUTPUTFILE" -> {
                        if (i != args.length - 1) {
                            outFile = new File(args[i+1]);
                            try {
                                outFile.createNewFile();
                            } catch (IOException e) {
                                appendErrorMessage("Output file could not be opened!", errorMessage);
                            }
                        } else {
                            appendErrorMessage("No output file defined!", errorMessage);
                        }
                    }
                    default -> System.out.println("\""+args[i]+"\" is not a valid parameter. It will be skipped.");
                }
            }
        }
        return errorMessage.toString();
    }

    public static void main(final String[] args) {
        String errorMessage  = validateArgs(args);
        if ("".equals(errorMessage)) {
            List<Object> allData = getDataSet();
            Sorter sorter = new Sorter(allData);
            String dataType = switch (mode) {
                case LONG -> "numbers";
                case LINE -> "lines";
                case WORD -> "words";
            };
            StringBuilder output = new StringBuilder();
            output.append(String.format("Total %s: %d\n", dataType, sorter.getTotalOccurrences()));
            if (type == Type.BYCOUNT) {
                List<Object> result = sorter.sortByCount();
                for (Object object : result) {
                    output.append((sorter.getObjectStats(object))).append("\n");
                }

            } else {
                List<Object> result = sorter.sortNaturally();
                String spacing = Mode.LINE == mode ? "\n" : " ";
                for (Object object : result) {
                    output.append(object).append(spacing);
                }
            }
            if (outFile == null) {
                System.out.println(output); // print output to terminal
            } else {
                try (FileWriter writer = new FileWriter(outFile)) {
                    writer.write(output.toString()); // write output to file
                } catch (IOException e) {
                    System.out.println("Output file could not be written to!");
                }
            }
        } else {
            System.out.println(errorMessage);
        }
    }
}
