package org.rpc.run;

import org.rpc.ReversePolishCalculator;
import org.rpc.csv.CSVFile;
import org.rpc.csv.Table;

public class Main {

  public static void main(String[] args) {
    String fileName = args[0];
    CSVFile csvFile = new CSVFile(fileName);
    ReversePolishCalculator calculator = new ReversePolishCalculator();
    Table processedTable = calculator.processCells(csvFile.toTable());
    System.out.println(processedTable.toString());
  }
}