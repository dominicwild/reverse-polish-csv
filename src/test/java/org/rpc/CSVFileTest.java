package org.rpc;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

  @ParameterizedTest
  @CsvSource({
      "A1, 10",
      "A2, 1 3 +",
      "A3, 2 3 -",
      "B1, b1 b2 *",
      "C2, 1 2 3",
      "C3, c3"
  })
  void read_cells_of_csv_in_letter_function_notation(String cellString, String expectedCellValue){
    CSVFile csvFile = new CSVFile(csvFilePath.toFile().getPath());

    String value = csvFile.getCell(cellString);
    assertThat(value).isEqualTo(expectedCellValue);
  }

}