# Sorting Tool
Sorts data (lines, words or integers) in either their lexicographical order, or by their total occurrences in the data.

## Technologies

This program uses:
- Java 17

## Getting Started

This project runs directly from linux(or Unix-like) command line. Requires no installation beyond Java 17.

```
Usage: java SortingTool [options]

Options:
-sortingType (natural|byCount)  Sort lexicographically or by occurrence
-dataType (long|line|word)      Type of data to read
-inputFile FILE                 Input data from file, read from terminal if absent
-outputFile FILE                Output sorted data to file, print to terminal if absent

```
Default settings are `-sortingType natural -dataType line`

To end data entry from the terminal, press EOF (Linux: Ctrl+D, Mac: Cmd+D, Windows: Ctrl+Z).

### Examples

Command line I/O:
```
$ java SortingTool -sortingType byCount -dataType long
> 1 -2   33 4
> 42
> 1                 1
Total numbers: 7.
-2: 1 time(s), 14%
4: 1 time(s), 14%
33: 1 time(s), 14%
42: 1 time(s), 14%
1: 3 time(s), 43%
```

## Features

- reads whole lines, words or integers from file or from the terminal
- sorts data either lexicographically or by total occurrences in the data
- outputs the sorted data to either the terminal, or to a file

