# Setup

For this class, you will use SBT as a platform to compile, run, and test Scala programs. It includes a package manager to automatically download required libraries, and we have provided an already-configured SBT template for your use as described below. SBT also includes a console which allows you to interactively try out Scala code without requiring you to write it to a file and compile it first. Scala and SBT are already installed on all CSIL machines.

## Project Basics

After making sure that git and SBT is installed on the machine you are using, you need to clone this repository. Execute the following command to clone this repository and have a copy of the assignment you can work on:

```
git clone https://github.com/pllab/cs162-tutorials.git
```

After running the command above, you will have a directory called `cs162-tutorials`. Henceforth, we will refer to that directory as the root directory of this assignment. The assignment has the following directory structure:

```
.
├── build.sbt
├── docs
│   ├── 1-setup.md
│   ├── 2-algebraic-datatypes.md
│   ├── 3-higher-order-programming.md
│   └── 4-specific-problems.md
├── README.md
└── src
    ├── main
    │   └── scala
    │       ├── introduction
    │       │   ├── Course.scala
    │       │   └── UniversityStandard.scala
    │       ├── calc
    │       │   └── Expr.scala
    │       └── linkedlist
    │       |   └── LinkedList.scala
    │       └── functional
    │           ├── Factorial.scala
    │           └── ListProblems.scala
    └── test
        └── scala
            ├── CalcSpec.scala
            ├── FactorialSpec.scala
            ├── IntroductionSpec.scala
            ├── LinkedListSpec.scala
            └── ListProblemsSpec.scala
```

The `docs` directory contains the tutorial text and the rest of the directory structure is the default directory structure expected by SBT and contains the **build file** (`build.sbt`) and the **source** directory (`src`). The source directory contains two further directories: `main` and `test`, that respectively hold the code for the project itself and the code for project tests.

SBT can handle projects that combine multiple languages, which is why both the `main` and `test` directories contain a `scala` directory; both of these `scala` directories contain only `scala` source files (other languages' source code would go in different directories under `main` and `test`). Richer and different directory hierarchies can be used, but must be configured in the build file.

Run `sbt` from the command line inside the root directory of the assignment. This command opens the interactive SBT shell. The interactive shell will be useful to have around, so be sure to keep it open all the time while developing your program.

The usual Scala development environment includes an **editor** of your choice to write the code in (we recommend **emacs**, which has a Scala mode available, but you can use anything you want), and the **SBT shell**. In this tutorial we distinguish the terminal prompt from the SBT shell prompt by prefixing the SBT shell prompt with your project name. 

To open the SBT shell, open the terminal, navigate to the root directory and type "sbt". The output of this command will look similar to the following:

```bash
> sbt
[info] Loading project definition from /home/mika/Projects/cs162-tutorials/project
[info] Loading settings from build.sbt ...
[info] Set current project to tutorials (in build file:/home/mika/Projects/cs162-tutorials/)
[info] sbt server started at local:///home/mika/.sbt/1.0/server/c4ca5aec08569b3746c7/sock
sbt:tutorials> 
```

Several things will be different from user to user, depending on where the root directory is and what your username is. The new shell, denoted by `sbt:tutorials>` is the prompt of the SBT shell.

You can now compile the current project by typing `compile`, and test it by typing `test` into the SBT shell. The `compile` command will compile any source code files found in `src/main/scala/`. 

```sbt
sbt:tutorials> compile
[success] Total time: 1 s, completed Sep 12, 2018 1:58:59 AM
sbt:tutorials>
```

### Testing your code

Once you're finished with writing code in your editor (or _before_ you start writing, even), you might want to run the test suites, to see what has to be done. These tests are used for grading, so take this part seriously. You can find the test code in `src/test/scala` directory. Whenever you feel stuck about understanding the expected functionality in a specific part of the assignment, refer to the relevant tests. Tests are organized in spec (short for specification), as described in **chapter 14**. You can use the `test` command in the SBT shell to run all the tests:

```sbt
sbt:tutorials> test
[info] Updating ...
[info] Done updating.
[info] Compiling 6 Scala sources to /home/mika/Projects/cs162-tutorials/target/scala-2.12/classes ...
[info] Done compiling.
[info] Compiling 5 Scala sources to /home/mika/Projects/cs162-tutorials/target/scala-2.12/test-classes ...
[info] Done compiling.
[info] FactorialSpec:
[info] The factorial of N
[info] - should be the product of all the numbers from 1 to N *** FAILED ***
[info]   scala.NotImplementedError: an implementation is missing
[info]   at scala.Predef$.$qmark$qmark$qmark(Predef.scala:284)
[info]   at edu.ucsb.cs.cs162.tuts.functional.Factorial$.iter$1(Factorial.scala:23)
[info]   at edu.ucsb.cs.cs162.tuts.functional.Factorial$.apply(Factorial.scala:25)
[info]   at edu.ucsb.cs.cs162.tuts.functional.FactorialSpec.$anonfun$new$1(FactorialSpec.scala:17)
[info] ...
[info] ScalaTest
[info] Run completed in 2 seconds, 56 milliseconds.
[info] Total number of tests run: 49
[info] Suites: completed 6, aborted 0
[info] Tests: succeeded 10, failed 39, canceled 0, ignored 0, pending 0
[info] *** 39 TESTS FAILED ***
[error] Failed: Total 49, Failed 39, Errors 0, Passed 10
[error] Failed tests:
[error] 	edu.ucsb.cs.cs162.tuts.functional.FactorialSpec
[error] 	edu.ucsb.cs.cs162.tuts.linkedlist.LinkedListSpec
[error] 	edu.ucsb.cs.cs162.tuts.functional.ListProblemsSpec
[error] 	edu.ucsb.cs.cs162.tuts.introduction.CourseSpec
[error] 	edu.ucsb.cs.cs162.tuts.calc.EvaluateSpec
[error] 	edu.ucsb.cs.cs162.tuts.calc.SimplifyHeadSpec
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 9 s, completed Sep 27, 2018 2:14:06 AM
```
Don't be frightened by this output! Read the lines that start with "- should..." to see which tests failed. These tests can be found in the appropriate file in the `/src/test/scala` folder.

If you want to focus on only a subset of the tests, you can use `testOnly TEST-SUITE-CLASS` to run a specific test. The `TEST-SUITE-CLASS` is a namespace or class name, for example:

```sbt
sbt:tutorials> testOnly edu.ucsb.cs.cs162.tuts.introduction.*
[info] CourseSpec:
[info] A course
[info] - should tell us what its name is *** FAILED ***
[info]   scala.NotImplementedError: an implementation is missing
[info]   at scala.Predef$.$qmark$qmark$qmark(Predef.scala:284)
[info]   at edu.ucsb.cs.cs162.tuts.introduction.UniversityStandard$.minimalCourseNumber$lzycompute(UniversityStandard.scala:6)
[info]   at edu.ucsb.cs.cs162.tuts.introduction.UniversityStandard$.minimalCourseNumber(UniversityStandard.scala:6)
[info]   at edu.ucsb.cs.cs162.tuts.introduction.Course.<init>(Course.scala:6)
[info]   at edu.ucsb.cs.cs162.tuts.introduction.CourseSpec.$anonfun$new$2(IntroductionSpec.scala:27)
[info]   at scala.collection.immutable.List.foreach(List.scala:389)
[info]   at edu.ucsb.cs.cs162.tuts.introduction.CourseSpec.$anonfun$new$1(IntroductionSpec.scala:26)
[info]   at scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:12)
[info]   at org.scalatest.OutcomeOf.outcomeOf(OutcomeOf.scala:85)
[info]   at org.scalatest.OutcomeOf.outcomeOf$(OutcomeOf.scala:83)
[info] ...
```
You can also use `testQuick` command to run only the tests that failed in the last run. After changing things around (in your editor, you know, **vim**), this is a fast way to check if you fixed something or made it worse.

> You will find many places in the Scala classes where some piece of code is simply marked as `???`. This is a piece of Scala type-magic that allows us to compile the code _without_ implementing a particular piece. Whenever you see a `???`, you will have to fill it in, or your code won't be able to run once we try to grade it.

## Opening a Scala interpreter Read-Eval-Print-Loop (REPL)

A **Read–Eval–Print Loop** (REPL, interactive shell or interpreter shell) is a simple, interactive programming environment that takes user inputs, evaluates them, and returns the result to the user. Type `console` and hit enter inside the SBT shell to run an interactive Scala console. If your code doesn't compile (and this stops `console` from working), try typing `consoleQuick` to skip compiling and just go to the REPL.

```sbt
sbt:tutorials> console
[info] Starting scala interpreter...
Welcome to Scala 2.12.6 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_171).
Type in expressions for evaluation. Or try :help.

scala> import edu.ucsb.cs.cs162.tuts._

scala> 
```

This is yet another prompt, specifically stating that it is a pure `scala>` console. You can try out snippets of Scala code here to see what happens and how they work. This is useful when trying to figure out how to implement some functionality and also when trying to debug code. From the REPL we can execute any Scala code directly, so we could try out any expression:

```sbt
scala> 3 + 3
res0: Int = 6

scala>
```

Here we are evaluating the expression `3 + 3`, and the REPL tells us that the result (which it names `res0`) has type `Int` and value `6`. You can use the name `res0` in later expressions if you like, the console will remember that it has value `6`.

We can also try function and method calls such as `println("Hello, World!")` or `"foo".toUpperCase`. Don't be intimidated while in the console, you can try out things here with no reprecussions until you get them to work the way you want. Erroneous code will simply cause an error message. Reading those error messages is very helpful for understanding the language. Scala tries to give informative error messages, sometimes even going above and beyond the call of duty to give suggestions.

We can return from this Scala REPL to the SBT one by pressing `CTRL+D`.

```sbt
scala> [user hits CTRL+D]
[success] Total time: 5032 s, completed Sep 12, 2018 2:27:04 AM
```

### A note on multiline code in the Scala REPL

By default, the REPL allows only single lines of code, and evaluates the code after each line you enter. To write (or copy and paste) multiple lines into the REPL, type `:paste` in the SBT shell. Doing so will enter a multiline mode where expressions aren't evaluated until you press `CTRL+D`.

## Assignment

The first assignment (`introduction`) is a project that contains only two simple classes with missing method implementations (`???`) that need to be filled in. This project will teach you how to read the code and work with tests, and get comfortable with SBT and Scala in your favorite editor (no, really, use **sublime text**). The source code is in `src/main/scala/introduction`, while the tests are in `src/test/scala/IntroductionSpec.scala`.

To run only tests for the `introduction` project, you can run:
```sbt
sbt:tutorials> testOnly edu.ucsb.cs.cs162.tuts.introduction.*
```
The tests there contain some useful comments for you to start with. Remember to use the SBT REPL for situations where you're not too sure of how something works, and don't want to recompile the project until it works!

### What next?

This first assignment isn't meant to be a real challenge, and should take little time. It also only tests your knowledge of some very basic Scala concepts and serves to get your spirits up once all the tests start glowing green! The actual practical assignments start with the second tutorial. In short, here are the topics that the full week 1 project covers, in full:

**Tutorial 1**: Introduction -- basic Scala and SBT workflow

**Tutorial 2**: Algebraic Data Types -- use of algebraic data types in implementation of programming language concepts;

**Tutorial 3**: Higher-Order Programming -- structural recursion, traversals and folds as the basis of functional programming;

**Tutorial 4**: Functional Problems -- some functional problems that can be solved with a bit of work if one finished tutorials 1-3 successfully.

The projects will be graded by looking at the passed tests. You may **not** remove any tests, but may write your own tests in. This is something that isn't mandatory for this assignment, but will be a useful practice going forward. 

Good luck!
