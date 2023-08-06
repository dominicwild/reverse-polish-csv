package org.rpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.rpc.csv.Table;

class ReversePolishCalculatorTest {

  final int A = 0;
  final int B = 1;
  final int C = 2;
  private static ReversePolishCalculator calculator;

  @BeforeAll
  static void beforeAll() {
    calculator = new ReversePolishCalculator();
  }

  @ParameterizedTest
  @CsvSource({
      "10 +",
      "notValid",
      "2 2 + -",
      "+ + - + /",
      "10 2 3 6 2",
      "10 2 +-",
      "10 2+",
      "/10 *2",
  })
  void invalid_cell_is_error(String invalidCellValue) {
    String cellRef = "A1";
    Table table = new Table();
    table.add(0, 0, invalidCellValue);

    Table processedTable = calculator.processCells(table);

    assertThat(processedTable.valueAt(cellRef)).isEqualTo("#ERR");
  }

  @ParameterizedTest
  @CsvSource({
      "10,10",
      "1 3 +,4",
      "2 3 -,-1",
      "6 2 /,3",
      "5 4 *,20",
      "2 3 + 2 + 2 -,5",
      "5 7 * 10 + 15 5 / -,42"
  })
  void process_cell_without_reference(String cellValue, String expectedProcessedValue) {
    String cellRef = "A1";
    Table table = new Table();
    table.add(0, A, cellValue);

    Table processedTable = calculator.processCells(table);

    assertThat(processedTable.valueAt(cellRef)).isEqualTo(expectedProcessedValue);
  }

  @ParameterizedTest
  @CsvSource({
      "a1,10",
      "b1 b2 *,40",
      "b1 a2 / c1 +,7",
  })
  void process_cell_with_reference(String cellOpString, String expectedValue) {
    Table table = new Table();

    table.add(0, A, "10"); // A1
    table.add(1, A, "5");  // A2
    table.add(0, B, "10"); // B1
    table.add(1, B, "4");  // B2
    table.add(0, C, "5");  // C1

    table.add(5, A, cellOpString);

    Table processedTable = calculator.processCells(table);

    assertThat(processedTable.valueAt("A6")).isEqualTo(expectedValue);
  }
}