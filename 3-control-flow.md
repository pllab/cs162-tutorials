# Control Flow, Part 1

Scala has a small set of powerful control flow mechanisms that mostly are similar to constructs known from other languages: `if`, `while`, `match`, `for` and `try`. Scala focuses on defining high-level control structures through libraries while leaving its core language minimal, yet sufficient. We will discuss the first three now, and leave `for` and `try` for a later tutorial.

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

Additional conditions can be introduced into cases, via the `if` guard. A guarded case looks like this, for example:
```scala
  case Point(n, 0) if n > 100 => println("On y axis, but far from the beginning!")
```
