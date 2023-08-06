package org.rpc;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import org.rpc.csv.Table;

public class ReversePolishCalculator {

  public static final String ERR_VALUE = "#ERR";

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
      String processedValue = calculate(cellValue, table);
      entry.setValue(processedValue);
    });

    return table;
  }

  private String calculate(String rpcString, Table table) {
    Deque<Integer> valueStack = new ArrayDeque<>();

    try {
      for (String value : rpcString.split(" ")) {
        if (isANumber(value)) {
          valueStack.push(Integer.parseInt(value));
        } else if (isAnOperator(value)) {
          Integer operand1 = valueStack.pop();
          Integer operand2 = valueStack.pop();
          Integer result = operations.get(value.charAt(0)).apply(operand2, operand1);
          valueStack.push(result);
        } else if (isAReference(value)) {
          String processedValue = calculate(table.valueAt(value), table);
          valueStack.push(Integer.parseInt(processedValue));
        } else {
          throw new IllegalArgumentException("Invalid token at " + value);
        }
      }

      if (valueStack.size() != 1) {
        return ERR_VALUE;
      }

      return valueStack.pop() + "";
    } catch (Exception e) {
      return ERR_VALUE;
    }
  }

  private static boolean isAReference(String value) {
    return value.matches("[a-zA-Z]\\d");
  }

  private static boolean isAnOperator(String value) {
    return value.matches("[+\\-/*]");
  }

  private static boolean isANumber(String value) {
    return value.matches("\\d.*");
  }

}
