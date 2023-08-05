package org.rpc.run;

import org.rpc.CSVFile;
import org.rpc.ReversePolishCalculator;

public class Main {

  public static void main(String[] args) {
    String fileName = args[0];
    CSVFile csvFile = new CSVFile(fileName);
    ReversePolishCalculator calculator = new ReversePolishCalculator(csvFile);
    calculator.calculate();
  }
}