

# Control Structures

Scala has a small set of powerful control flow mechanisms that mostly are similar to constructs known from other languages: `if`, `while`, `match`, `try` and `for`. Scala focuses on defining high-level control structures through libraries while leaving its core language minimal, yet sufficient. We will discuss the first four now, and leave `for` for a later tutorial.

## Branching with `if`s

Branching control flow in Scala is similar to many other languages and has the following general form:
```
if(<condition>) <then-branch> [else <else-branch>]
```
A very important difference to these languages is that control structures in Scala actually result in  values. For instance, instead of what you might expect (coming from C or Java):
```java
int y = 6;
String x = null;
if(y > 5) {
  x = "big";
} else {
  x = "small";
}
Console.log.println(x);     // prints 'big'
```
in Scala we can have `if` _be_ an expression on its own, and carry the value through:
```scala
val y = 6
val x = if(y > 5) "big" else "small"
println(x) 		            // prints 'big'
```
This stems from functional programming, where programs are viewed as computations that result in a value, and thus every smaller part of programs has to amount in a value as well.

> This example actually outlines two different programming paradigms, namely, **imperative** and **declarative** programming. In imperative programming, control flow is explicitly stated and influences the change of values. In declarative, the control flow itself is an expression of change.

## Looping with `while`s

`while` loops are similar to many other languages, and Scala supports both `while` and `do...while` looping constructs. The general forms of a `while` loop are:
```
while(<condition>) { <body> }
do { <body> } while(<condition>)
```
While (no pun intended) many languages make good use of these constructs, they require a variable to be able to stop iteration, and this is generally considered bad form in Scala. We will discuss ways to evade loops later on in the tutorials, but you should know that there are situations where loops are warranted and put to good use.
> `while` loops do **not** return a useful value, as we discussed `if`s do.

## Trying for Exceptions with `try`

Trying a piece of code that might throw exceptions and catching those exceptions is very similar to C++ or Java:
```
try {
  <body>
} catch {
  case <exception> => <when-caught-do>
  ...
} finally {
  <finally-do>
}
```
Try-catch-finally blocks _do_ return values, so they can be used in computations. 
To throw exceptions from Scala, one can use the	`throw` statement, like this:
```scala
throw new Exception("something is wrong")
```
You can substitute the `java.lang.Exception` class for any other class that extends [`java.lang.Throwable`](https://docs.oracle.com/javase/7/docs/api/java/lang/Throwable.html).

## Pattern Matching with `match`

Pattern matching is a powered-up version of `switch-case`s from older languages, mixed with functional type-based concepts. A `match` expression will let you consider alternatives **in order**, and will choose the first alternative to fulfill all the requirements. A simple example, to show off the syntax of `match` would be:

```scala
val ingredient = args(0)
val additional = ingredient match {
  case "salt" => println("pepper")
  case "chips" => println("salsa")
  case "eggs" => println("bacon")
  case _ => println("huh?")
}
```
Pattern matching, however, is a much stronger mechanism than simply switching on values, but to show off its true power, we have to first introduce **case classes**, or classes that you can match on.

### Introducing Case Classes

Case classes are special classes that add some conveniences. First off, unlike standard classes, they add a _factory constructor_, meaning that we don't have to use `new` explicitly anymore. Compare the following snippets:

```scala
class X(value: Int)
val x = new X(1)
```
When using case classes, we instead have: 
```scala
case class X(value: Int)
val x = X(1)
```
This is a syntactic convenience, but helps with readability, as you don't have to read a multitude of `new`s. The other benefit of case classes is that they have default "natural" implementations of some utility methods (such as `toString` and `equals`, meaning that you can print them in a nice way and compare two values of a case class by comparing their fields). Compare the following two snippets:
```scala
// "normal" classes
class Point(x: Int, y: Int)
val a = new Point(2, 1) // Point@749210f
val b = new Point(2, 1) // Point@3320fe2
println(a == b)         // false
```
```scala
// case classes
case class Point(x: Int, y: Int)
val a = Point(2, 1)     // Point(2, 1)
val b = Point(2, 1)     // Point(2, 1)
println(a == b)         // true
```
Comparing two classes is done by referential equality, which means that only two objects at the same location are considered equal. With case classes, the _values of the fields_ are the deciding factor.

With case classes now being a thing, we can extend our pattern matching to consider them too. For example, we can check which one of these conditions applies for a point:

```scala
point match {
  case Point(0, 0) => println("Coordinate beginning!")
  case Point(_, 0) => println("On y axis!")
  case Point(0, _) => println("On x axis!")
  case Point(n, m) => println(s"In space at (${n}, ${m})!")
} 
```
> Notice the use of `_` whenever we don't care about some value. We could have put a variable name as well, but if we won't use it, there's no need to introduce it.

Additional conditions can be introduced into cases, via an `if` guard. A guarded case looks like this, for example:
```scala
  case Point(n, 0) if n > 100 => println("On y axis, but far from the beginning!")
```
This is a more succinct way of writing:
```scala
  case Point(n, 0) => 
    if(n > 100) {
      println("On y axis, but far from the beginning!")
    }
```
Pattern matching is total, meaning that all options have to be explored, otherwise a `MatchError` is thrown. This means that you have two choices:

- you add a _default_ case to the match, that will catch any and all remaining possibilities
```scala
  case _ => println("Something happens")
```
- you create a _limited number of options_ and explore all of them. This requires us to introduce a new concept, called `trait`s.

### Traits and Enumerations
A `trait` is an abstraction of a class. It defines what the class will look like and in what ways it can be used. Traits are similar to Java's interfaces in that way. However, even an empty trait can be useful as a tool for bunching case classes together:
```scala
trait Shape
case class Point(x: Int, y: Int) extends Shape
case class Line(a: Point, b: Point) extends Shape
```
By using the `extends` keyword, we made sure that the case classes `Point` and `Line` _are_ `Shape`s. If we were to match on a shape variable, the options become obvious and finite, and include everything that extends from `Shape`. To guarantee that no one can extend `Shape` later, we can use the `sealed` keyword like this:
```scala
sealed trait Shape
```
The `sealed` keyword allows for `Shape` to be extended only in the same file where it is defined, in turn allowing you to idiomatically limit how many options are possible when matching this trait. We can now say that when matching `Shape`, the only two options to match against (excluding further constraints via `if` guards and special interesting values) are `Point` and `Line`.

### Exercise Project: Calculator

Open the **calculator** project folder and set up your development environment in it. 
- Open an SBT console in that folder; 
- Check that the code compiles by executing `compile` in the console;
- Open your editor of choice in that folder

The project should compile, but contains many missing implementations that you will fill in. In this project, we'll build a simple calculator, able to evaluate arithmetic expressions like `1+3` to `4`. It will recognize additions, subtractions and multiplications over integers. For this purpose, we supply you with the following structure:

```scala
sealed trait Expr
case class Num(value: Int) extends Expr
case class Add(lhs: Expr, rhs: Expr) extends Expr
case class Sub(lhs: Expr, rhs: Expr) extends Expr
case class Mul(lhs: Expr, rhs: Expr) extends Expr
```
As you can see, the parameters of all binary operations (`+`, `-` and `*`) are expressions, which means that we can now nest these case classes like so:
```scala
Add(Num(2), Sub(Num(7), Num(3)))      // = 2 + (7 - 3)
```
### Exercises

1. In a similar way as with `evaluate`, write a `toMath` method that prints every `Expr` in its mathematical form. For example, `toMath(Add(Add(Num(2), Num(4)), Num(3)))` should output `(2 + 4) + 3`. If all `ToMathSpec` tests pass, you're done! The `toMath` method has the following signature:
```scala
  def toMath(tree: Expr): String = ???
```
2. Fill in the `evaluate` method that will, given an expression, like `Add(Num(2), Sub(Num(7), Num(3)))`, give you back the result of those operations: `2 + (7 - 3) = 2 + 4 = 6`. Use pattern matching over the `Expr` trait to accomplish this. Use the scala interpreter console if you're not sure which way to go, and try solving the problem on paper first, to be sure you have all the edge cases. Run `test` in the SBT console to run our prepared tests. If all `EvaluationSpec` tests pass, you're done! The `evaluate` method has the following signature:
```scala
  def evaluate(tree: Expr): Int = ???
```
3. **Hard.** Add an additional case class `Div` to the calculator and implement `evaluate` and `toMath` to work with it. To implement the operation correctly, we will need to throw an exception when dividing by 0, and present the correct result otherwise. This will require the use of `if`, `throw` and `match`! If the `HardSpec` tests pass, you're done!

> **Hint:** To solve all of these, you should build simple cases and then look at how they connect to form larger expressions. This may require some quite straightforward recursion...
