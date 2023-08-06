package org.rpc.csv;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.IntStream;

public class CSVFile {

  public static final int LETTER_A_ASCII_CODE = 65;
  private final String fileName;

  public CSVFile(String fileName) {
    this.fileName = fileName;
    if (Files.notExists(Path.of(fileName))) {
      throw new IllegalArgumentException("File with path '" + fileName + "' does not exist");
    }
  }

  public Table toTable() {
    Table table = new Table();
    try {
      List<String> lines = Files.readAllLines(Path.of(fileName));
      IntStream.range(0, lines.size())
          .forEach(rowIndex -> addCells(lines.get(rowIndex), rowIndex, table));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
    return table;
  }

  private void addCells(String line, int rowIndex, Table table) {
    String[] lineValues = line.split(",");
    IntStream.range(0, lineValues.length)
        .forEach(colIndex -> table.add(
                rowIndex,
                colIndex,
                lineValues[colIndex].trim()
            )
        );
  }
}

