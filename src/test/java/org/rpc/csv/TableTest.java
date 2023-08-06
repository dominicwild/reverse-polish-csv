package org.rpc.csv;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class TableTest {

  @ParameterizedTest
  @ValueSource(strings = {"", "a1", "something", "A", "1A", " "})
  @NullSource
  void invalid_cell_reference_throws_exception(String cellRef) {
    Table table = new Table();

    assertThatThrownBy(() -> table.valueAt(cellRef))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @CsvSource({
      "0,0,A1",
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
}