# Task

## Description

Write a Java program to parse a given CSV file and evaluate each cell by these rules

1. Each cell is an expression in postfix notation. Please refer to the wikipedia page for a
   full description.
2. Each number or operation will always be separated by one or more spaces.
3. A cell can refer to another cell, via the LETTER NUMBER notation (A2, B4, etc -
   letters refer to columns, numbers to rows).
4. Support the basic arithmetic operators `+, -, *, /`

The output will be a CSV file of the same dimensions, where each cell is evaluated to its final
value. If any cell is an invalid expression, then **for that cell only** print #ERR.

For example, the following CSV input:
```csv
10, 1 3 +, 2 3 -
b1 b2 *, a1, b1 a2 / c1 +
+, 1 2 3, c3
```

Might output something like this:
```csv
10,4,-1
40,10,-0.9
#ERR,#ERR,#ERR
```

## Command line spec
Aside from reviewing the code, we run automated tests on your task. So please make sure
it confirms to the following spec exactly:
- The program should be buildable with the following command:
```bash
cd $SRC && find . -name '*.java' | xargs javac
```
(manually replace “$SRC” with your source root)
- The program should be runnable with the following command:
```bash
java com.example.Main $INPUT_FILE
```
And write its output to STDOUT.
(manually replace `com.example.Main` with your main class)

## Tips
- Write simple code (don’t over-engineer it)
- Think about how to model the problem, (so that the various cases are
handled cleanly, without messy hacks or bolted-on fixes)
- Think about the various edge cases (whether they’re handled - but
of course, the more complete the solution, the better).
Requirements
- Use only what is available in the standard library. This problem is simple enough that
you shouldn’t need 3rd party libraries.
- The above test data is just an example. Think thoroughly and test your application yourself.
- Do not over-engineer your solution, you should aim to finish the task in 2-4 hours so
don’t make it too complex.
- The timeframe is just a guide to help you plan. You are not being timed. Do not rush, do not write spaghetti code.
- Specific details about the behaviour of the application are left for you to decide how
best to handle.