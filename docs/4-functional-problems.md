# Functional Problems

This tutorial is basically just an extended exercise after the previous three tutorials and uses the concepts from those tutorials in practical ways. There are two separate problems to solve in this assignment:

## Exercises: Tail Recursion and List Problems

1. Open the `src/main/scala/functional/Factorial.scala` file for the source code, and `src/test/scala/FactorialSpec.scala` for the tests. Complete the method that calculates the factorial of a number, so that the method is tail-recursive (described in **chapter 8.9** of the textbook), and all the tests pass. For more hints, look at the documentation in the source code around the `factorial` method.

> Reminder: don't forget to uncomment the `@tailrec` annotation in the source code! Without that line, your solution will not be considered.

To run only the tests for this project, use:
```sbt
sbt:tutorials> testOnly edu.ucsb.cs.cs162.tuts.functional.FactorialSpec
```

2. Write the list problems described in `src/main/scala/functional/ListProblems.scala`, with tests in the `src/test/scala/ListProblemsSpec.scala` file. There are three problems there: `sumOdd`, `sumPairs` and `safePenultimate`. All three of these can be implemented by using the things presented in the previous assignments in practice. 

To run only the tests for this part of the exercise, use:
```sbt
sbt:tutorials> testOnly edu.ucsb.cs.cs162.tuts.functional.ListProblemsSpec
```

### That's all, folks!

Try running `test` in SBT now, and if it's all green, congratulations! You're doing well!
