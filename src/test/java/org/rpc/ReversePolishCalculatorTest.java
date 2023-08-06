package org.rpc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.rpc.csv.Table;

class ReversePolishCalculatorTest {

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
    table.add(0, 0, cellValue);

    ReversePolishCalculator calculator = new ReversePolishCalculator();

    Table processedTable = calculator.processCells(table);

    assertThat(processedTable.valueAt(cellRef)).isEqualTo(expectedProcessedValue);
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

    ReversePolishCalculator calculator = new ReversePolishCalculator();

    Table processedTable = calculator.processCells(table);

    assertThat(processedTable.valueAt(cellRef)).isEqualTo("#ERR");
  }


}