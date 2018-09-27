# Setup

For this class, you will use SBT as a platform to compile, run, and test Scala programs. It includes a package manager to automatically download required libraries, and we have provided an already-configured SBT template for your use as described below. SBT also includes a console which allows you to interactively try out Scala code without requiring you to write it to a file and compile it first. Scala and SBT are already installed on all CSIL machines.

### Project Basics

After making sure that git and SBT is installed on the machine you are using, you need to clone this repository. Execute the following command to clone this repository and have a copy of the assignment you can work on:

```
git clone https://github.com/pllab/cs162-tutorials.git
```

After running the command above, you will have a directory called `cs162-tutorials`. Henceforth, we will refer to that directory as the root directory of this assignment. The assignment has the following directory structure:

```
TODO: update the directory structure
hello-world
├── build.sbt
└── src
    ├── main
    │   └── scala
    │       └── Main.scala
    └── test
        └── scala
            └── EmptySpec.scala
```

The `docs` directory contains the tutorial text and the rest of the directory structure is the default directory structure expected by SBT and contains the **build file** (`build.sbt`) and the **source** directory (`src`). The source directory contains two further directories: `main` and `test`, that respectively hold the code for the project itself and the code for project tests.

SBT can handle projects that combine multiple languages, which is why both the `main` and `test` directories contain a `scala` directory; both of these `scala` directories contain only `scala` source files (other languages' source code would go in different directories under `main` and `test`). Richer and different directory hierarchies can be used, but must be configured in the build file.

Run `sbt` from the command line inside the root directory of the assignment. This command opens the interactive SBT shell. The interactive shell will be useful to have around, so be sure to keep it open all the time while developing your program.

The usual Scala development environment includes an **editor** of your choice to write the code in (we recommend emacs, which has a Scala mode available, but you can use anything you want), and the **SBT shell**. In this tutorial we distinguish the terminal prompt from the SBT shell prompt by prefixing the SBT shell prompt with your project name:

```bash
> sbt
TODO:update
sbt:hello-world>
```

You can now compile the current project by typing `compile`, and test it by typing `test` into the sbt shell. The `compile` command will compile any source code files found in `src/main/scala/`. The `test` command will compile any source code files found in `src/test/scala/` and execute all tests in those files.

```sbt
TODO:update
sbt:hello-world> compile
[success] Total time: 1 s, completed Sep 12, 2018 1:58:59 AM
sbt:hello-world>
```

#### Testing your code

The assignment template you have contains a test suite which we will use to grade your assignment. You can use `test` command in the sbt shell to run all the tests:

```sbt
sbt:cs162-tutorials> test
[info] - should distribute addition -- a(b + c) = ab + ac *** FAILED ***
[info]   Exception was thrown during property evaluation.
[info]     Message: Implement the rest here.
[info]     Occurred when passed generated values (
[info]       arg0 = 0, // 1 shrink
[info]       arg1 = 0, // 1 shrink
[info]       arg2 = 0 // 1 shrink
[info]     )
...    <many more lines showing test results>
[info] ScalaTest
[info] Run completed in 425 milliseconds.
[info] Total number of tests run: 21
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 2, failed 19, canceled 0, ignored 0, pending 0
[info] *** 19 TESTS FAILED ***
[error] Failed: Total 21, Failed 19, Errors 0, Passed 2
[error] Failed tests:
[error]         cs162.tutorials.calculator.HardSpec
[error]         cs162.tutorials.calculator.ToMathSpec
[error]         cs162.tutorials.calculator.EvaluationSpec
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 1 s, completed Sep 26, 2018 9:29:29 PM
sbt:cs162-tutoarials>
```

If you want to focus on only a subset of the tests, you can use `testOnly TEST-SUITE-CLASS` to run a specific test suite:

```sbt
sbt:cs162-tutorials> testOnly cs162.tutorials.calculator.HardSpec
[info] HardSpec:
[info] Div(a, b)
[info] - should evaluate to a / b if b != 0, and throw an exception otherwise *** FAILED ***
[info]   Exception was thrown during property evaluation.
[info]     Message: Implement the rest here.
[info]     Occurred when passed generated values (
[info]       arg0 = 0, // 1 shrink
[info]       arg1 = -1
[info]     )
[info] - should evaluate to a if b == 1 *** FAILED ***
[info]   Exception was thrown during property evaluation.
[info]     Message: Implement the rest here.
[info]     Occurred when passed generated values (
[info]       arg0 = 0 // 1 shrink
[info]     )
[info] - should evaluate to 1 if a == b *** FAILED ***
[info]   Exception was thrown during property evaluation.
[info]     Message: Implement the rest here.
[info]     Occurred when passed generated values (
[info]       arg0 = 1 // 30 shrinks
[info]     )
[info] - should be stringified as (a * b) *** FAILED ***
[info]   Exception was thrown during property evaluation.
[info]     Message: Implement the rest here.
[info]     Occurred when passed generated values (
[info]       arg0 = 0,
[info]       arg1 = 0 // 1 shrink
[info]     )
[info] ScalaTest
[info] Run completed in 146 milliseconds.
[info] Total number of tests run: 4
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 0, failed 4, canceled 0, ignored 0, pending 0
[info] *** 4 TESTS FAILED ***
[error] Failed: Total 4, Failed 4, Errors 0, Passed 0
[error] Failed tests:
[error]         cs162.tutorials.calculator.HardSpec
[error] (Test / testOnly) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 0 s, completed Sep 26, 2018 9:31:57 PM
sbt:cs162-tutorials>
```

You can also use `testQuick` command to run only the tests that failed in the last run.

> The exact output might vary, depending on how recently the code was changed and how many dependencies have to be reloaded.

No matter how large your project gets, the usage of SBT stays the same overall. There are additional commands which might be useful, including `reload` for when the build file changes, `console` to run an interactive Scala console inside SBT, and `exit` to leave SBT. Online SBT documentation is available [here](https://www.scala-sbt.org/1.x/docs/).

You shouldn't have to restart SBT even once while developing your projects. If new files are added to the `src/` directory hierarchy, SBT will notice them next time you run a command and recompile with them automatically.

### Scala REPL

Scala contains an interactive console (otherwise known as a REPL, or read-evaluate-print-loop), where you can type valid (or invalid!) Scala expressions and get them to immediately evaluate instead of being compiled.

By typing `console` into the SBT shell, we get the following:

```sbt
TODO:update
sbt:-world> console
[info] Starting scala interpreter...
import cs162.tutorials.hello-world._
Welcome to Scala version 2.11.5 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_171).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

This is yet another prompt, specifically stating that it is a pure `scala>` console. You can try out snippets of Scala code here to see what happens and how they work. This is useful when trying to figure out how to implement something and also when trying to debug code. From the REPL we can execute any Scala code directly, so we could try out any expression:

```sbt
scala> 3+3
res0: Int = 6

scala>
```

Here we are evaluating the expression `3+3`, and the REPL tells us that the result (which it names `res0`) has type `Int` and value `6`. You can use the name `res0` in later expressions if you like, the console will remember that it has value `6`.

We can also try function and method calls such as `println("Hello, World!")` or `"foo".toUpperCase`. Don't be intimidated while in the console, you can try out things here with no reprecussions until you get them to work the way you want. Erroneous code will simply cause an error message. Reading those error messages is very helpful for understanding the language. Scala tries to give informative error messages, sometimes even going above and beyond the call of duty to give suggestions.

We can return from this Scala REPL to the SBT one by pressing `CTRL+D`. We can exit the SBT shell by either typing `exit` or pressing `CTRL+D` again.

```sbt
scala>      <user hits CTRL+D>
[success] Total time: 5032 s, completed Sep 12, 2018 2:27:04 AM
sbt:cs162-tutorials> exit
[info] shutting down server
```

### A note on multiline code in the Scala REPL

By default, the REPL allows only single lines of code, and evaluates the code after each line you enter. To write (or copy and paste) multiple lines into the REPL, type `:paste` in the SBT shell. Doing so will enter a multiline mode where expressions aren't evaluated until you press `CTRL+D`.

The `console` command will automatically compile any source code in your project and make it available for you to use in the Scala REPL. If you don't want this to happen (for example, your project code is currently broken and doesn't compile), use `consoleQuick` instead. This will enter a Scala REPL without compiling your project (but neither will it be available for you to use).

## Asciicast

To see all of this in practice, follow this asciicast for a step-by-step illustration of the process, done on a CSIL machine.

TODO:update asciicast

[![asciicast](https://asciinema.org/a/202735.png)](https://asciinema.org/a/202735)

## Conclusion

At this point, you should be able to use SBT to compile, run, and test your Scala code and to enter the Scala REPL to try out Scala code interactively.
