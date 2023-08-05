package org.rpc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class CSVFile {

  private final HashMap<String, String> cells;

  public CSVFile(String fileName) {
    try {
      List<String> csvLines = Files.readAllLines(Path.of(fileName));
      cells = new HashMap<>();
      for (int row = 0; row < csvLines.size(); row++) {
        String line = csvLines.get(row);
        String[] lineValues = line.split(",");

        for (int col = 0; col < lineValues.length; col++) {
          String lineValue = lineValues[col].trim();
          String key = toLetter(col) + row;
          cells.put(key, lineValue);
        }
      }
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private String toLetter(int row) {
    return String.valueOf((char) (64 + row));
  }

  public String getCell(String cellString) {
    return cells.get(cellString);
  }
}
