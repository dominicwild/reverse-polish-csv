package org.rpc.csv;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TableTest {

  @ParameterizedTest
  @ValueSource(strings = {"", "something", "A", "1A", " "})
  @NullSource
  void invalid_cell_reference_throws_exception(String cellRef) {
    Table table = new Table();

    assertThatThrownBy(() -> table.valueAt(cellRef))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "0,0,A1",
      "0,0,a1",
      "1,1,B2",
      "2,1,B3",
      "2,4,E3",
  })
  void read_cells_from_table(int row, int col, String expectedCellRef) {
    Table table = new Table();
    String expectedValue = "abc";
    table.add(row, col, expectedValue);

    assertThat(table.valueAt(expectedCellRef)).isEqualTo(expectedValue);
  }

  @Test
  void output_table_string() {
    Table table = new Table();

    table.add(0, 2, "Something");
    table.add(0, 0, "10");
    table.add(1, 1, "a1 b2 * c3 +");
    table.add(1, 0, "1 2 +");
    table.add(1, 2, "34");
    table.add(0, 1, "ABC");
    table.add(2, 0, "1");
    table.add(2, 2, "3");
    table.add(2, 1, "2");

    String expectedTable = """
        10,ABC,Something
        1 2 +,a1 b2 * c3 +,34
        1,2,3""";

    assertThat(table.toString().replace("\r\n", "\n")).hasToString(expectedTable);
  }
}