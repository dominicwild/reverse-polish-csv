package org.rpc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.rpc.csv.Table;

public class ReversePolishCalculator {

  interface Operation {

    Integer apply(Integer operand2, Integer operand1);
  }

  static Map<Character, Operation> operations = new HashMap<>();

  static {
    operations.put('+', Integer::sum);
    operations.put('-', (v1, v2) -> v1 - v2);
    operations.put('*', (v1, v2) -> v1 * v2);
    operations.put('/', (v1, v2) -> v1 / v2);
  }

  public Table processCells(Table table) {

    table.forEach(entry -> {
      String cellValue = entry.getValue();
      String processedValue = calculate(cellValue);
      entry.setValue(processedValue);
    });

    return table;
  }

  private String calculate(String rpcString) {
    Deque<Integer> valueStack = new ArrayDeque<>();

    for (String value : rpcString.split(" ")) {
      if (isANumber(value)) {
        valueStack.push(Integer.parseInt(value));
      }
      if (value.matches("[+\\-/*]")) {
        Integer operand1 = valueStack.pop();
        Integer operand2 = valueStack.pop();
        Integer result = operations.get(value.charAt(0)).apply(operand2, operand1);
        valueStack.push(result);
      }
    }

    return valueStack.pop() + "";
  }

  private static boolean isANumber(String value) {
    return value.matches("\\d.*");
  }
}
