package org.rpc.csv;

import static java.util.function.Function.identity;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Cell(String coordinate, String value) {

}

public class CSVFile implements CellReader {

  private final Map<String, String> cells;

  public CSVFile(String fileName) {
    try {
      List<String> lines = Files.readAllLines(Path.of(fileName));
      this.cells = IntStream.range(0, lines.size())
          .mapToObj(rowIndex -> createCells(lines.get(rowIndex), rowIndex))
          .flatMap(identity())
          .collect(Collectors.toMap(Cell::coordinate, Cell::value));
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private Stream<Cell> createCells(String line, int rowIndex) {
    String[] lineValues = line.split(",");
    return IntStream.range(0, lineValues.length)
        .mapToObj(colIndex -> new Cell(
                toLetter(colIndex) + (rowIndex + 1),
                lineValues[colIndex].trim()
            )
        );
  }

  private String toLetter(int col) {
    return String.valueOf((char) (65 + col));
  }

  @Override
  public String valueAt(String cellRef) {
    if (cellRef == null || !cellRef.matches("[A-Z]\\d")) {
      throw new IllegalArgumentException("Cell reference '" + cellRef + "' is not valid.");
    }
    return cells.get(cellRef);
  }
}

