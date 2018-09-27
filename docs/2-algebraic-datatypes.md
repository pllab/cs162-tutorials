# Algebraic Data Types

Algebraic data types are composite types, most often **products**, **sums** and **functions**, that we create by combining two types `A` and `B` in the following ways:

- A product of types `A` and `B` is a type `(A, B)` that contains both a value of type `A` and a value of type `B`. These are also called _tuples_ (for products with an arbitrary number of elements) or _pairs_ (for products with exactly two elements). They are somewhat like C/C++ structs, except without explicit names for each element.
- A sum of types `A` and `B` is a type `A | B` that either contains a value of type `A` or a value of type `B`, but not both. These are also called _disjoint unions_. They are somewhat like C/C++ unions, except that unlike C/C++ unions, disjoint keep track of whether they contain an `A` or a `B`.
- A power type of types `A` and `B` is a type `A => B` that produces a value of type `B` when given a value of type `A`. These are also called _function_ types.

Scala has tuples and functions built-in to the language (chapter 3 of the book introduces these constructs). Classes can also be used to represent products, where instead of the index of the argument, our structure has a name both for itself and its particular fields. We will go over functions in the next tutorial.

## Sum Types

### Definition

Sum types denote disjoint values. We'll be using the same running example as the book does in this part: a calculator might need expressions which are either variables, numbers, or unary or binary operations. In the notation above, this would be written as:

```scala
Expr = Var(String) | Num(Double) | UnOp(String, Expr) | BinOp(String, Expr, Expr)
```

In Scala, sum types can be expressed using traits and case classes. Examples of this are given in **chapter 15** of the book.

```scala
sealed trait Expr
case class Var(name: String) extends Expr
case class Num(number: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```

If we now have a variable of type `Expr`, it is true that this variable can have only one value, and that value has to be one of the case classes that extend `Expr`; i.e., `Expr` behaves exactly like a sum type.

We can pattern-match on sum types using `match` expressions. By default there is no check that a given `match` expression actually covers all of the possible cases, because anyone could extend the base trait (e.g., `Expr`) with new cases in some other file. By using the `sealed` modifer we disallow anyone else from extending the trait in any other file, which allows the compiler to check that a `match` expression has all of the necessary cases.

### Usage

We can give a variable of type `Expr` a value of any type that inherits from the `Expr` trait, e.g.:

```scala
val e: Expr = Var("x")
```

To check whether a sum type variable currently has a value of a certain specific case class, we use pattern matching. Pattern matching is described in chapter 7.5 and then later detailed in chapter 15. Pattern matching is similar to the `switch` expression from languages like Java and C, but is much more powerful and expressive. For example, for case classes, `match` enables us to match nested structures. For the `Expr` type defined above, we can match the following case:

```scala
val e = BinOp("+", Unop("-", 1), Num(12))

e match {
  case BinOp("+", UnOp("-", a), Num(n)) => a + n
  case _ => 0
}
```

This code results in the value 13.

## Algebraic Data Types in Programming Languages

Recursive structures are handy when we are implementing programming languages, because language syntax is often recursive itself (for example, nested blocks of code). In the mathematical expression example above, we see that the fields of `UnOp` and `BinOp` are themselves `Expr`, meaning that we can create expressions of arbitrary depth by nesting `Expr`s inside of `Expr`s. One such expression might grow in the following way:

```
   Expr
=> BinOp("*", ?, ?)
=> BinOp("*", Num(?), ?)
=> BinOp("*", Num(2), ?)
=> BinOp("*", Num(2), BinOp("+", ?, ?))
=> BinOp("*", Num(2), BinOp("+", Num(3), ?))
=> BinOp("*", Num(2), BinOp("+", Num(3), Num(4)))
```

This type of recursion is called **structural recursion**, due to the fact that it's occuring while building up structures. The cases that don't use `Expr` as part of their construction (e.g., the argument to `Num`) are the base cases for recursion. The syntaxes of both natural and programming languages are structurally recursive in nature. This is why it is very important to master both building recursive structures using algebraic types, as well as manipulating and traversing these structures.

One benefit of using structural recursion is _self-similarity_. If a function is defined to work on an `Expr`, it can then be applied to any part of a complex expression simply by calling it on that expression's parts that, themselves, are `Expr` as well.

## Exercise: Expression Simplification and Evaluation

In the `src/main/scala/calculator` folder, the `Expr.scala` file is the one you'll have to edit. Fill in the `Calculator.simplifyHead` and `Calculator.evaluate` methods. You can run the calculator tests only by running:

```sbt
sbt:tutorials> testOnly edu.ucsb.cs.cs162.tuts.calculator.*
```
The corresponding tests are in the `src/test/scala/CalculatorSpec.scala`. Read them carefully and try to work from there, as you did with the first tutorial. 

To finish this task, take care of `Calculator.simplifyHead` first. This method simplifies certain patterns, where possible. The method has the following signature:
```scala
object Calculator {
  def simplifyHead(expr: Expr): Expr = ???
}
```
You will need to take care of these three cases, and more, as listed below:
1. the expression `-(-n)` should become `n`, regardless of what type of expression `n` is
2. the expression `0 + x` should become `x`, regardless of what type of expression `x` is
3. the expression `1 * x` should become `x`, regardless of what type of expression `x` is
4. the expression `0 * x` should become `0`, regardless of what type of expression `x` is
5. the expression `x - x` should become `0`, regardless of what type of expression `x` is
6. for two _numbers_ `n` and `m`, the expressions `n + m`, `n - m` and `n * m` should evaluate to a single number with the respective values of the sum, subtraction and multiplication of the `n` and `m`. For example, the expression `BinOp("+", Num(2), Num(3))` should evaluate to `Num(5)`.
7. the variable with the name *DUP* is to be treated as a duplicate of whatever other operand is, if appearing in a binary operation, except if the other operand is also this *DUP* variable, in which case they both have the numeric value `0` and should be simplified accordingly. For example, the expression `BinOp("+", Var("DUP"), Num(7))` should be simplified to `BinOp("+", Num(7), Num(7))`.

Examples of running the `simplifyHead` method can be found in the test file. Pay close attention to these!

After all the tests that have to do with `simplifyHead` are done, only the `evaluate` tests remain.

8. The `evaluate` method has the following signature:
```scala
object Calculator {
  def evaluate(expr: Expr): Double = ???
}
```
The goal of this method is to evaluate a certain expression down to a single number. Include the binary operations `+`, `-` and `*`, as well as the unary operation `-`. Read chapter 14 of the text book to get a handle of tests and assertions and write your own tests for this method to submit with the code. All variables should evaluate to **1** in all operations (since we haven't specified assignment yet in our little arithmetic expression language). An example of `evaluate` would be:
```scala
Calculator.evaluate(Num(100))
// results in 100

Calculator.evaluate(BinOp("-", Num(10), Num(7))
// results in 10 - 7 = 3

Calculator.evaluate(BinOp("+", Num(10), UnOp("-", Num(7)))
// results in 10 + (-(7)) = 10 + (-7) = 3

Calculator.evaluate(BinOp("+", Num(3), BinOp("*", Var("this is one"), Num(7))))
// results in 10, as 3 + 1 * 7 = 3 + 7 = 10
```

## Sidenote: Naming Conventions Explained

The names "product", "sum" and "power" come from the same source as the name "algebraic". A shallow explanation relates to algebra which is easily observed when looking at the cardinality of the types in question. If there exists a void type, that has no values, we can say that it has cardinality **0**. In Scala, this is the `Nothing` type. The `Unit` type has one value, called `()` in Scala. The cardinality of `Unit` is therefore **1**. `Boolean`, obviously, has **2** values, `true` and `false`.

If we ask about the cardinality of `(A, B)`, where `A` has the cardinality **a** and `B` has cardinality **b**, we can see that it is equal to **a * b**, hence the name **product**. This is because any value of one type can be mixed with any value of the other. For example, `(Boolean, Unit)` has two values: `(true, ())` and `(false, ())`. On the other hand, `(Boolean, Boolean)` has **4** values.

Looking at `A | B`, the number of possible values is equal to the sum of the cardinalities of `A` and `B`, thus **a + b**. For example, `Boolean + Unit` has **3** values: either `Boolean true`, `Boolean false` or `Unit ()`, thus **3**. This is why the types are called **sum** types.

The function type `A => B` has an output value for every input value given. This means that for every possible value of the input, we have all the possibilities of the output type. This means (following the previous two composite types) that a function is describable in term of $x$ values of type `B`, meaning that it is equal to `(B, B, ...repeated x times..., B)`.

For the sake of an example, let's imagine that a type `Tri = One | Two | Three` exists. This type obviously has a cardinality of **1 + 1 + 1 = 3** (and as such is equal to `Boolean + Unit` above). The type `Boolean => Tri` is equal to `(Tri, Tri)`, which we know has a cardinality of **3 * 3 = 9**. This is, however, equal to **3^2**, which is exactly the cardinality of `Tri` to the _power_ of the cardinality of `Boolean`. The type `Tri => Boolean` on the other hand is equal to `(Boolean, Boolean, Boolean)`, which means that it has a cardinality of **2 * 2 * 2 = 2^3 = 8**, equivalent to the cardinality of `Boolean` to the _power_ of the cardinality of `Tri`. This is why they're called **power** types.

