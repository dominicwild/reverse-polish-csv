package org.rpc.csv;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class CSVFileTest {

  static String csvContents =
      """
          10, 1 3 +, 2 3 -
          b1 b2 *, a1, b1 a2 / c1 +
          +, 1 2 3, c3
          """;
  static Path csvFilePath;


  @BeforeAll
  static void beforeAll() throws IOException {
    csvFilePath = Files.createTempFile("data", "csv");
    Files.write(csvFilePath, csvContents.getBytes());
  }

  @AfterAll
  static void afterAll() throws IOException {
    Files.delete(csvFilePath);
  }

  @Test
  void non_existent_file_throws_exception() {
    assertThatThrownBy(() -> new CSVFile("file-that-does-not-exist.csvvvv"))
        .isInstanceOf(UncheckedIOException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "A1, 10",
      "B1, 1 3 +",
      "C1, 2 3 -",
      "A2, b1 b2 *",
      "B3, 1 2 3",
      "C2, b1 a2 / c1 +",
      "C3, c3"
  })
  void read_cells_of_csv_in_letter_function_notation(String cellString, String expectedCellValue) {
    CSVFile csvFile = new CSVFile(csvFilePath.toFile().getPath());

    String value = csvFile.valueAt(cellString);
    assertThat(value).isEqualTo(expectedCellValue);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "a1", "something", "A", "1A", " "})
  @NullSource
  void invalid_cell_reference_throws_exception(String cellRef) {
    CSVFile csvFile = new CSVFile(csvFilePath.toFile().getPath());

    assertThatThrownBy(() -> csvFile.valueAt(cellRef))
        .isInstanceOf(IllegalArgumentException.class);
  }
}