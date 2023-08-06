package org.rpc.csv;

import static org.rpc.csv.CSVFile.LETTER_A_ASCII_CODE;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Table {

  Map<String, String> cells = new HashMap<>();

  public void add(int row, int col, String cellValue) {
    cells.put(createCellRef(row, col), cellValue);
  }

  public String valueAt(String cellRef) {
    if (notValid(cellRef)) {
      throw new IllegalArgumentException("Cell reference '" + cellRef + "' is not valid.");
    }
    return cells.get(cellRef.toUpperCase());
  }

  private static boolean notValid(String cellRef) {
    return cellRef == null || !cellRef.matches("[a-zA-Z]\\d");
  }

  private String createCellRef(int row, int col) {
    return toLetter(col) + (row + 1);
  }

  private String toLetter(int col) {
    return String.valueOf((char) (LETTER_A_ASCII_CODE + col));
  }

  public void forEach(Consumer<Entry<String, String>> iteratorFunction) {
    for (Map.Entry<String, String> entry : cells.entrySet()) {
      iteratorFunction.accept(entry);
    }
  }

  @Override
  public String toString() {
    return cells.keySet()
        .stream()
        .sorted(Comparator.comparing(Table::reverse))
        .collect(Collectors.groupingBy(this::getRowOf))
        .values()
        .stream()
        .map(rowValues -> String.join(",", rowValues))
        .sorted()
        .map(line -> {
          String newLine = line;
          String[] cellRefs = line.split(",");
          for (String cellRef : cellRefs) {
            newLine = newLine.replace(cellRef, valueAt(cellRef));
          }
          return newLine;
        })
        .collect(Collectors.joining(System.lineSeparator()));
  }

  private int getRowOf(String key) {
    return key.charAt(1) - LETTER_A_ASCII_CODE;
  }

  private static String reverse(String key1) {
    return new StringBuilder(key1).reverse().toString();
  }
}
