
# Algebraic Datatypes

> Note: this tutorial assumes that you've read and understood the "Programming in Scala (3rd Edition)" up to (and including) chapter 7, and chapter 15. If you haven't, go and review this material. The tutorials will outline specific ways in which we use Scala in this course.

Algebraic datatypes are composite types, most often **products**, **sums** and **functions** in practice, that we create by combining two types `A` and `B` in the following ways:

- a product of `A` and `B` is a type `(A, B)` that contains both a value of `A` and a value of `B`. These are also called _tuples_ or _structs_.
- a sum of `A` and `B` is a type `A | B` that either contains a value of `A` or a value of `B`, but not both. These are also called _disjoint sums_.
- a power type of `A` and `B` is a type `A => B` that produces a value of `B` given a value of `A`. These are also called _function_ types.

Scala has tuples and functions built-in (chapter 3 of the book introduces these constructs). Classes can also be used to represent products, where instead of the index of the argument, our structure has a name both for itself and its particular fields. We will go over functions in the next tutorial.

## Sum Types

### Definition

Sum types denote disjoint values. We'll be using the same running example as the book does in this part: a calculator might need expressions which are either variables, numbers, unary or binary operations. In the notation above, this would be written as:

```scala
Expr = Var(String) | Num(Double) | UnOp(String, Expr) | BinOp(String, Expr, Expr) 
```

In Scala, sum types can be expressed using traits and case classes. Examples of this are given in chapter 15 of the book.

```scala
trait Expr
case class Var(name: String) extends Expr
case class Num(number: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```
If we now have a variable of type `Expr`, it is true that this variable can have only one value, and that value has to be one of the case classes that extend `Expr`, in turn behaving exactly like a sum type does.
> Hint: to make sure that there are no cases we missed, we can use the `sealed` modifier keyword to make the trait non-extendable outside the file it's defined in, which guarantees that Scala will check for totality (that we covered all the cases).

### Usage
To assign a value to a variable of type `Expr`, we need to decide on one value type, as `Expr` has no value by itself. One example could be:
```scala
val e: Expr = Var("x")
```
To check whether a sum type variable currently has a value of a certain specific case class, we use pattern matching. Pattern matching is described in chapter 7.5 and then later detailed in chapter 15. Pattern matching is similar to switch from languages like Java and C, but additionally enables us to match values by type. 

Furthermore, for case classes, it enables us to match nested structures. For example, for the `Expr` type defined above, we can match the following case:
```scala
e match {
  case BinOp("+", UnOp("-", a), Num(n)) => ...
  ...
}
```
Without pattern matching on types, we would have to check that the variable `e` is of type `BinOp` first, then match the internal parts separately, creating lots of unneeded code.

## Algebraic Datatypes in Programming Languages

When implementing programming languages, we notice that recursive structures come in handy, as many languages are themselves recursive. For instance, with the simple mathematical expression example above, we see that the fields of `UnOp` and `BinOp` are themselves `Expr`, meaning that we can create expressions of greater complexity. One such expression might grow in the following way:

```
   Expr
=> BinOp("*", ?, ?)
=> BinOp("*", Num(?), ?)
=> BinOp("*", Num(2), ?)
=> BinOp("*", Num(2), BinOp("+", ?, ?))
=> BinOp("*", Num(2), BinOp("+", Num(3), ?))
=> BinOp("*", Num(2), BinOp("+", Num(3), Num(4)))
```
The observed recursion is called **structural recursion** here, due to the fact that it's occuring while building up structures. The cases that don't use `Expr` as part of their construction would be the base cases for recursion. The syntaxes of both natural and programming languages are structurally recursive in nature. This is why it is very important to master both building recursive structures using algebraic types, as well as manipulating and traversing these structures.

One benefit of using structural recursion is _self-similarity_. If a function is defined to work on an `Expr`, it can then be applied to any part of a complex expression simply by calling it on that expression's parts that, themselves, are `Expr` as well.

## Assignment: Expression Simplification

Open the `calculator` project locally, and the `Expr.scala` file in the `src/main/scala` directory. Fill in the `simplifyExpr` method for the above `Expr` trait and its case classes. The `simplifyExpr` function works by recognizing some familiar patterns and reducing them to simpler ones. 

You will need to take care of these three cases, and more, as listed below:
1. (1 point) the expression `-(-n)` should become `n`, regardless of what type of expression `n` is
2. (1 point) the expression `0 + x` should become `x`, regardless of what type of expression `x` is
3. (1 point) the expression `1 * x` should become `x`, regardless of what type of expression `x` is
4. (1 point) the expression `0 * x` should become `0`, regardless of what type of expression `x` is
5. (1 point) the expression `x - x` should become `0`, regardless of what type of expression `x` is
6. (2 points) for two _numbers_ `n` and `m`, the expressions `n + m`, `n - m` and `n * m` should evaluate to a single number with the respective values of the sum, subtraction and multiplication of the `n` and `m`. For example, the expression `BinOp("+", Num(2), Num(3))` should evaluate to `Num(5)`.
7. (3 points) the variable with the name *DUP* is to be treated as a duplicate of whatever other operand is, if appearing in a binary operation, except if the other operand is also this *DUP* variable, in which case they both have the numeric value `0` and should be simplified accordingly. For example, the expression `BinOp("+", Var("DUP"), Num(7))` should be simplified to `BinOp("+", Num(7), Num(7))`.

Run `testOnly cs162.tuts.calculator.simplifyExpr` to test only the `simplifyExpr` functionality. You can also run all the tests for this project by simply running `test` as shown in the first tutorial.

8. **Hard** (5 points) Fill in the `evaluate` method in the same class. The `evaluate` method has the following signature:
```scala
trait Expr {
  def evaluate: Double
}
```
The goal of this method is to evaluate a certain expression down to a single number. Include the binary operations `+`, `-` and `*`, as well as the unary operation `-`. Read chapter 14 of the text book to get a handle of tests and assertions and write your own tests for this method to submit with the code. All variables evaluate to **1** in operations. An example of `evaluate` would be:

```scala
evaluate(Num(100))
// results in 100

evaluate(BinOp("-", Num(10), Num(7))
// results in 10 - 7 = 3

evaluate(BinOp("+", Num(10), UnOp("-", Num(7)))
// results in 10 + (-(7)) = 10 + (-7) = 3

evaluate(BinOp("+", Num(3), BinOp("*", Var("this is one"), Num(7))))
// results in 10, as 3 + 1 * 7 = 3 + 7 = 10
```

## Sidenote: Naming Conventions Explained

The names "product", "sum" and "power" come from the same source as the name "algebraic". A shallow explanation relates to algebra which is easily observed when looking at the cardinality of the types in question. If there exists a void type, that has no values, we can say that it has cardinality **0**. In Scala, this is the `Nothing` type. The `Unit` type has one value, called `()` in Scala. The cardinality of `Unit` is therefore **1**. `Boolean`, obviously, has **2** values, `true` and `false`.

If we ask about the cardinality of `(A, B)`, where `A` has the cardinality **a** and `B` has cardinality **b**, we can see that it is equal to **a * b**, hence the name **product**. This is because any value of one type can be mixed with any value of the other. For example, `(Boolean, Unit)` has two values: `(true, ())` and `(false, ())`. On the other hand, `(Boolean, Boolean)` has **4** values.

Looking at `A | B`, the number of possible values is equal to the sum of the cardinalities of `A` and `B`, thus **a + b**. For example, `Boolean + Unit` has **3** values: either `Boolean true`, `Boolean false` or `Unit ()`, thus **3**. This is why the types are called **sum** types.

The function type `A => B` has an output value for every input value given. This means that for every possible value of the input, we have all the possibilities of the output type. This means (following the previous two composite types) that a function is describable in term of $a$ values of type `B`, meaning that it is equal to `(B, B, ...a times... , B)`. 
For the sake of an example, let's imagine that a type `Tri = One | Two | Three` exists. This type obviously has a cardinality of **1 + 1 + 1 = 3** (and as such is equal to `Boolean + Unit` above). The type `Boolean => Tri` is equal to `(Tri, Tri)`, which we know has a cardinality of **3 * 3 = 9**. This is, however, equal to **3^2**, which is exactly the cardinality of `Tri` to the _power_ of the cardinality of `Boolean`. The type `Tri => Boolean` on the other hand is equal to `(Boolean, Boolean, Boolean)`, which means that it has a cardinality of **2 * 2 * 2 = 2^3 = 8**, equivalent to the cardinality of `Boolean` to the _power_ of the cardinality of `Tri`. This is why they're called **power** types.
