# Higher-Order Programming

Higher-order programming means that we can use certain abstractions (functions, methods, etc.) in places where values are usually expected. Most of the time, for us, this means passing functions as parameters to other functions, aptly named "higher-order functions". As stated in the textbook, Scala's higher-order functions are useful as a means of creating new control structures and understanding the standard library.

## Recursive Structures

Higher-order functions allow us to simplify the process of traversing recursive structures, such as those built from algebraic datatypes. As the running example, we'll use the expressions introduced in the previous tutorial. They were defined by the following sum type:

```scala
sealed trait Expr
case class Var(name: String) extends Expr
case class Num(number: Double) extends Expr
case class UnOp(operator: String, arg: Expr) extends Expr
case class BinOp(operator: String, left: Expr, right: Expr) extends Expr
```

Values of the `Expr` types can be imagined as trees, where `Var` and `Num` are leaf nodes, `UnOp` is a node with a single child, and `BinOp` is a node with two children. Every expression can be represented this way. Here is an illustration for `(Var("a") + Var("b")) * Var("c") + Num(7)`:

![Expression tree example](https://upload.wikimedia.org/wikipedia/commons/thumb/9/98/Exp-tree-ex-11.svg/250px-Exp-tree-ex-11.svg.png)

> If this idea is strange to you, take some time and play around with imagining different expressions as trees. Get used to this idea, as it will be very useful for the whole course.

If we are asked to write a method that will print out this tree in post-order (children first, then the parents), we can split the problem into two pieces: one piece that understand how to traverse the tree in the appropriate way, and a second piece that understands what to do at each node (i.e., print). Composing these pieces in a specific order (recurse to children if any, then print node) gives us the resulting method.

Now, if you were asked to turn every node that is a number **n** into a node that is also a number but with the value  **n+1**, the process would be similar: the ordering doesn't make a difference, so just copying the same code template would work: recurse to children if any, then increase if the node is a `Num`.

We can see (and chapter 9 of the textbook actually talks about this at length and with different examples) that these two methods, even though they do completely different computations, still have a similar _shape_, in that we recurse if children are available, and then we do some specific computation. This specific computation is a function that turns our tree's current node into some other value (in the print case, into `Unit` and a side-effect, and in the `+1` case, into a new `Num` expression with a value one larger than before). Seeing this shape allows us to extract the traversal as a generic, higher-order function and use it to rewrite both of the above methods (and more!) as a single higher-order function.

This kind of method is usually called a `map` if the resulting _values_ are important (as in the `+1` example). If we don't, (for example, every node turns into a `Unit` as with the print example) we call it `foreach`.

> Maps exist as both a structure (a key-value store) and a type of higher-order function that turns one kind of data into another while traversing a structure. In this tutorial, we'll be referring to the function `map` except if stated otherwise.

### Traversals

In the case of a `List[T]` and similar traversable structures in Scala, `map` has the following method signature:

```scala
trait List[T] {
  def map[U](fn: T => U): List[U]
}
```

What this means is that we can use `map` in a generic way, and supply a function that maps from the type `T` introduced by the trait itself into some new type `U`. The result ends up being a new `List`, this time holding types of our new type `U`.

Examples of using `map` in practice would be:

```scala
 Seq(1, 2, 3).map { _ + 10 }
/* process:
  fn(x) = x + 10
  Seq(fn(1)) ++ Seq(2, 3).map(fn)
  Seq(11, fn(2)) ++ Seq(3).map(fn)
  Seq(11, 12, fn(3)) ++ Seq().map(fn)
  Seq(11, 12, 13)
*/

 Seq(1, 2, 3).map { _.toString }
/* process:
  fn(x) = x.toString
  Seq(fn(1)) ++ Seq(2, 3).map(fn)
  Seq("1", fn(2)) ++ Seq(3).map(fn)
  Seq("1", "2", fn(3)) ++ Seq().map(fn)
  Seq("1", "2", "3")
*/
```

> Copy these snippets (with or without the comment) into the SBT `console` and try them out! Change them in some way and try them again. Be sure you understand these in practice, and not only in theory.

If we replace `U` with `Unit`, we get the signature:

```scala
map[Unit]: (fn: T => Unit): List[Unit]
```

This structure shows an interesting property of `map`: even if we lose all the information we had while turning `U` to `Unit` (as `Unit` only has one value, so everything turns into `()`), we still keep the structure, as the returning list has the same length as the original. Furthermore, if this specific `map` was used on a tree-like structure, the full structure of the tree would be returned. Seeing how pointless this result is, we can discard it and rewrite this specific case into a different traversal method called `foreach`:

```scala
trait List[T] {
  def map[U](fn: T => U): List[U]
  def foreach(fn: T => Unit): Unit
}
```

As mentioned before, we might choose to use `foreach` when the result isn't important. For example:

```scala
Seq(1, 2, 3).map { _ * 100 }.foreach { println }
/* prints:
 100
 200
 300
*/
```

All classes in the standard Scala collections library (`List`, `Map`, `Set`, etc) have `map` and `foreach` methods defined on them, along with many other useful methods.

### Folding

Structures that can be traversed can usually also be folded. Folding means that we slowly compress the structure into a smaller structure. For example, `evaluate` from the previous tutorial, which evaluated a tree of expressions into a single integer, was a fold. Folds are sometimes also called **reductions**. A fold can be directed from the left or from the right, which determines which order the elements of the structure are visited in. Using `foldLeft` is almost always sufficient.

Folds and maps are two of the most common tools in your functional programmer toolkit, so learn to use them well. `foldLeft` (on the `List[T]` example) has the following signature:

```scala
trait List[T] {
  def foldLeft[U](start: U)(op: (U, T) => U): U
}
```

The `start: U` argument is the starting accumulation value (i.e., this is the value that is returned if the structure is empty), and the `op: (U, T) => U` function is the step function: it takes the result of the last fold (the `U` parameter) and the next element to be folded (the `T` parameter) and creates a new value of type `U` that will be passed to the next step of the fold. As an example, consider the following `sum` function implemented with folds:

```scala
 Seq(1, 2, 3).foldLeft(0) { case (acc, next) => acc + next }
/* process:
 0   Seq(1, 2, 3)
 0+1 Seq(2, 3)
 1+2 Seq(3)
 3+3 Seq()
 6
*/
```

or the following fold that operates on type `String` and returns the type `Int`; it counts the number of letters in all the words in a sequence:

```scala
 Seq("hello", "world", "!!!!").foldLeft(0) { case (acc, next) => acc + next.length }
/* process:
  0                  Seq("hello", "world", "!!!!")
  0+"hello".length   Seq("world", "!!!!")
  5+"world".length   Seq("!!!!")
  10+"!!!!".length   Seq()
  14
*/
```

## Assignment: Linked Lists

The assignemnt for this tutorial is filling in all the methods of a linked list structure. The assignment's source files are in the `/src/main/scala/linkedlist` directory, while the tests are all in the `src/test/scala/LinkedListSpec.scala` file. There are more tests here than in the previous projects; so take a while reading about every method.

The methods in the `linkedlist` implementation should behave exactly like the ones in the `List` class in Scala. If you have any qualms about this class, read the documentation given in the `LinkedList.scala` file, and try their Scala counterparts out in the Scala console. **Chapter 16** of the textbook talks more about this specific topic.
