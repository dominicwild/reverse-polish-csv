package org.rpc.csv;

import static org.rpc.csv.CSVFile.LETTER_A_ASCII_CODE;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

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
}
