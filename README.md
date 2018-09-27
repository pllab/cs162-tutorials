# Assignment 1: Scala Tutorials

**Due date: October 9, 2018 11:59:59 PM**

In this assignment, you will familarize yourself with Scala, learn how
to use the tools in Scala ecosystem and get used to test-driven
development using unit tests.

## Exercises

First, clone this repository and make sure that you are in the root
directory of your clone when you are following the instructions.

There are four tutorials in the `docs/` directory in Markdown format. You
can read them on GitHub in a nicely formatted way (just like how this
README document looks), or you can open the files in your favorite
text editor to read the Markdown source.

The assignment is to go through the tutorials and to do the exercises
contained in the tutorials. The exercises get increasingly more difficult, so
start with the first tutorial and don't get surprised when the later
exercises start feeling difficult. Try to get adjusted to the functional
programming mindset and don't be shy about asking questions if you get
stuck.

## Grading

You **must** use only the purely functional subset of Scala. This
means that you are **not allowed** to use mutations, more explicitly
you must not use any of:
- mutable variables created using `var` keyword,

- mutable collections, e.g. anything under `scala.collection.mutable`,

- `Array` data type.

If you use any mutation, you will automatically fail the assignment.

Your code must compile. Invoking `compile` and `test:compile` on SBT
console must succeed. Otherwise, you will automatically fail the
assignment.

To grade your assignment, we will make sure that the unit tests are
not tampered with and your grade will be in proportion to the number
of unit tests that pass.

## Submission

We will use only the contents of the `src` directory for grading so
make sure that all your code is in proper directories under it.  You
will use `turnin` on CSIL to submit your assignment.  To submit your
assignment, on the root directory of the repository you cloned,
    1. Make sure that you run the unit tests on CSIL and get the
       result you expect.
    2. run `turnin assign1@cs162 src`.
    3. Read the instructions on screen and the list of files you are
       submitting carefully and submit the assignment only if you are
       sure that you are submitting all the files.

## General Tips

- Start early. There is quite a bit of material to go through.
- If you get stuck, go through the relevant parts of the
  text book and the relevant unit tests.
- Don't be shy about asking questions on Piazza, or going to office
  hours. You can ask questions anonymously and/or privately on Piazza.
