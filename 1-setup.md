# Setup

We encourage you to use SBT as a platform to compile, run, and test Scala programs. SBT isn't necessary, but it is very convenient. It includes a package manager to automatically download required libraries, and we have provided an already-configured SBT template for your use as described below. SBT also includes a console which allows you to interactively try out Scala code without requiring you to write it to a file and compile it first. Scala and SBT are already installed on all CSIL machines.

### Project Basics

We assume that you are starting in a terminal in your home directory. Create a project directory (named whatever you wish) and enter that directory. You can now create a minimal Scala project using our template by running:

```bash
> sbt new pllab/scala.g8
```

When you run this command it will ask you for some minimal details: the name, package, and version of the project. It will then generate a project directory for you based on our pre-existing template and the details that you provide.

There should now be a new directory inside the current directory with the name that you input above (in the remainder of this tutorial, we assume that you gave the name `hello-world` and the organization `cs162.tuts`). The structure of the directory is as follows:

```
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

This structure is the default directory structure expected by SBT and contains the **build file** (`build.sbt`) and the **source** directory (`src`). The source directory contains two further directories: `main` and `test`, that respectively hold the code for the project itself and the code for project tests.

SBT can handle projects that combine multiple languages, which is why both the `main` and `test` directories contain a `scala` directory; both of these `scala` directories contain only `scala` source files (other languages' source code would go in different directories under `main` and `test`). Richer and different directory hierarchies can be used, but must be configured in the build file.

Run `sbt` from the command line inside the generated project directory `hello-world`. This command opens the interactive SBT shell. The interactive shell will be useful to have around, so be sure to keep it open all the time while developing your program.

The usual Scala development environment includes an **editor** of your choice to write the code in (we recommend emacs, which has a Scala mode available, but you can use anything you want), and the **SBT shell**. In this tutorial we distinguish the terminal prompt from the SBT shell prompt by prefixing the SBT shell prompt with your project name:

```bash
> sbt

sbt:hello-world>
```

You can now compile the current project by typing `compile`, and test it by typing `test` into the sbt shell. The `compile` command will compile any source code files found in `src/main/scala/`. The `test` command will compile any source code files found in `src/test/scala/` and execute all tests in those files.

```sbt
sbt:hello-world> compile
[success] Total time: 1 s, completed Sep 12, 2018 1:58:59 AM
sbt:hello-world>
```

The test files in the template we had you use for this project only contain an empty test suite, so we won't go deeper into that topic right now (more later).

```sbt
sbt:hello-world> test
[info] EmptySpec:
[info] ScalaTest
[info] Run completed in 239 milliseconds.
[info] Total number of tests run: 0
[info] Suites: completed 1, aborted 0
[info] Tests: succeeded 0, failed 0, canceled 0, ignored 0, pending 0
[info] No tests were executed.
[info] Passed: Total 0, Failed 0, Errors 0, Passed 0
[success] Total time: 1 s, completed Sep 12, 2018 1:58:41 AM
sbt:hello-world>
```

> The exact output might vary, depending on how recently the code was changed and how many dependencies have to be reloaded.

No matter how large your project gets, the usage of SBT stays the same overall. There are additional commands which might be useful, including `reload` for when the build file changes, `console` to run an interactive Scala console inside SBT, and `exit` to leave SBT. Online SBT documentation is available [here](https://www.scala-sbt.org/1.x/docs/).

You shouldn't have to restart SBT even once while developing your projects. If new files are added to the `src/` directory hierarchy, SBT will notice them next time you run a command and recompile with them automatically.

### Scala REPL

Scala contains an interactive console (otherwise known as a REPL, or read-evaluate-print-loop), where you can type valid (or invalid!) Scala expressions and get them to immediately evaluate instead of being compiled.

By typing `console` into the SBT shell, we get the following:

```sbt
sbt:hello-world> console
[info] Updating ...
[info] Done updating.
[info] Compiling 1 Scala source to /home/mika/hello-world/target/scala-2.11/classes ...
[info] Done compiling.
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

We can also try a `println` statement or a method call, such as `Main.main`. Don't be intimidated while in the console, you can try out things here with no reprecussions until you get them to work the way you want. Erroneous code will simply cause an error message. Reading those error messages is very helpful for understanding the language. Scala tries to give informative error messages, sometimes even going above and beyond the call of duty to give suggestions.

We can return from this Scala REPL to the SBT one by pressing `CTRL+D`. We can exit the SBT shell by either typing `exit` or pressing `CTRL+D` again.

```sbt
scala>      <user hits CTRL+D>
[success] Total time: 5032 s, completed Sep 12, 2018 2:27:04 AM
sbt:hello-world> exit
[info] shutting down server
```

### A note on multiline code in the Scala REPL

By default, the REPL allows only single lines of code, and evaluates the code after each line you enter. To write (or copy and paste) multiple lines into the REPL, type `:paste` in the SBT shell. Doing so will enter a multiline mode where expressions aren't evaluated until you press `CTRL+D`.

The `console` command will automatically compile any source code in your project and make it available for you to use in the Scala REPL. If you don't want this to happen (for example, your project code is currently broken and doesn't compile), use `consoleQuick` instead. This will enter a Scala REPL without compiling your project (but neither will it be available for you to use).

## Asciicast

To see all of this in practice, follow this asciicast for a step-by-step illustration of the process, done on a CSIL machine.

[![asciicast](https://asciinema.org/a/202735.png)](https://asciinema.org/a/202735)

## Conclusion

At this point, you should be able to use SBT to compile, run, and test your Scala code and to enter the Scala REPL to try out Scala code interactively.
