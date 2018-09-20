
# Scala Generics and Useful Library Classes

So far, we've been working with some simple data types and introduced some control structures, but we've been working with one hand tied behind our back. This hand is the massive Scala Standard Library, that already has implementations for many things one might expect of a modern language. Some of those features, just to show a few, includes:

- collections (`List`, `Set`, `Map`),
- concurrency (`TrieMap`, `Future`, `Promise`),
- IO and system-level operations, etc.

Many of these features use more complex features of the Scala type-system, but most require the user (in this case, us) to only understand **generics**.

## Generics

Scala classes can be _generic over some type_, meaning that they can have a type-shaped hole that you can plug whatever you want into. For example, an `Array` is a sequential structure that has elements of some type in it, but these elements (although they have to all be of the _same_ type) aren't predetermined. In other words, therre's only one `Array` class, but it can have elements that are `Int` or `String` or any other class. We write this as:
```scala
 Array[Int]
 Array[String]
 Array[T]
```
We read this as "an `Array` of `T`". `T` is called the _type parameter_ of `Array`.

> You might have seen such structures in languages such as Java (also called generics there, e.g. `Array<Int>`) or C++ (where they're called templates). The idea is the same!

Using generics is simple: we introduce them when we know that we'll need the class to be able to work with different types (to be _polymorphic_). For example, this is how one might write a `LinkedList` trait:

```scala
trait LinkedList[T]
```
We can do the same for both (case) classes:

```scala
case class ListNode[T](value: T, next: LinkedList[T]) extends LinkedList[T]
```
Here we defined a `ListNode` that can have a value of any type `T`, and contain a pointer to the rest of the list (that _has_ to have elements of the same type `T`). This linked list, as is, can't end, because every element has to have one more. To solve this, we introduce another case, in which we just hit an end, called `ListEnd`:
```scala
case object ListEnd extends LinkedList[T]
```
To finalize this object, however, we need to have a `T` introduced. Objects, as they are singular, can't have type parameters associated with them. From this perspective, we can see that `LinkedList[T]` would have a problem deciding what `T` is.

In this situation, we have several ways to actually solve this situation, but the two that might first cross your mind would be:

1. We can make `ListEnd` into a case class with no parameters, but one _type parameter_, so that  `ListEnd[T]() extends LinkedList[T]`.
2. We can use the top-most super-type `Any`, which is a parent to all other types in the Scala type hierarchy, and say that `case object ListEnd extends LinkedList[Any]`.

The first solution introduces a semantical weirdness, making a separate `ListEnd` for each type provided. The second one uses `Any` and cheats through the type system. Other solutions exist and are better. but are outside the scope of this tutorial.

To print out a test `LinkedList`, we can now do something like this:

```scala
// first solution
println(ListNode(2, ListNode(3, ListEnd())))

// second solution
println(ListNode(2, ListNode(3, ListEnd)))
```
If you `run` one of these through SBT, you should see the output (with or without an additional `()` behind `ListEnd`):
```scala
ListNode(2, ListNode(3, ListEnd))
```
> Note that we don't have to _write_ the type argument explicitly here. The type inferencer in Scala can figure it out in simple cases.

If we wanted to do the same, but with strings, it would be simple:
```scala
ListNode("hello", ListNode("world", ListEnd))
``` 

It might seem weird that our class is _split_ into cases, but it does make sense to split different functionalities into different units. This is one of the main tenets of object-oriented programming, after all, and case classes and traits do a good job with that.

Whenever you want some functionality to be required in all cases, put that into the trait. Otherwise, include them in a case class extending that trait. We get to what value we _actually_ have by pattern matching. For example, look at this `length` method of our `LinkedList`, which returns how many elements a list has:

```scala
trait LinkedList[T] {
  def length: Int
}
```
We will leave the implementation out of the trait, as every case will have different ways to deal with `length`. In the case of `LinkEnd`, we would have a simple solution:
```scala
case object ListEnd extends LinkedList[Any] {
  def length = 0
}
```
The `ListNode` case is a bit more involved, as the length of one node depends on the nature and length of the next ones. Every non-empty node increases the length by one, so:
```scala
case class ListNode[T](value: T, next: LinkedList[T]) extends LinkedList[T] {
  def length = 1 + next.length
}
```
We can now check that the length of `ListNode("hello", ListNode("world", ListEnd()))` returns `2`, as it should. 

> For now, we are using either the Scala interactive console for this, or printing the value out, but we will learn how to write unit tests to be sure that everything is alright.

## Useful Classes

Among the many useful classes that use generics, to do the next project and write more methods for the `LinkedList` trait above, we'll need the following:

### Option[T]

`Option[T]` allows you to say that a variable of a certain type _might not_ have a value. This is a replacement for the Java way of saying that something is `null` at some point. Types in Scala don't have `null` included in their types. If a variable is of a type, then it has a value. To specify that a variable might _not_ have a value, we use `Option[T]`.

`Option[T]` is a trait. It has two case classes: `Some[T]` and `None`. Notice that this kind of looks like the case we had with `LinkedList` above: one of the cases carries a type parameter `T`, while the other doesn't. This can be useful if a computation _can't_ give us a result. For example, integer division doesn't return a result when dividing with `0` -- a case which can be treated with an exception or with an optional result.

```scala
def div(a: Int, b: Int): Option[Int] =
  if(b == 0) None
  else Some(a / b)
```
To check whether an optional value exists or not, we can simply pattern match on it:
```scala
  div(a, b) match {
    case Some(result) => 
      // do something with the result
    case None => 
      // do something else
  }
```
`Option[T]` also has many methods to help out with this. Many of these work by passing functions as arguments, which Scala makes simple. Take for example, the `map` function which chains operations. `map` has the following type:
```scala
trait Option[A] {
  // ...
  def map[B](f: A => B): Option[B]
  // ...
}
```
Similar to classes with type parameters, methods can introduce them as well (similar to Java and C++). In this case, in the `Option[A]` instance, `map` introduces `B`, and asks for a function from `A` to `B`. Once given this function, it uses it on the optional value currently held within the class and produces a new `Option[B]` value. For example:
```scala
val a = 15
val b = 5
val aDivB = div(a, b) // returns Some(3)
println(aDivB.map(x => x + 5)) // prints Some(8)
```
Compare that to the situation where `b = 0`:
```scala
val a = 15
val b = 0
val aDivB = div(a, b) // returns None
println(aDivB.map(x => x + 5)) // prints None
```
So, we can still _do_ the operation, but if we ever meet a `None`, it will just stay `None`.

You can see that in the call to map, we pass a **function** as an argument. This argument takes a value `x`, which is implicitly of type `Int` in our case, and does something to that `x`. We could have done this by using methods instead:

```scala
def addFive(x: Int) = x + 5
// ...
aDivB.map(addFive) // same as aDivB.map(x => x + 5)
```
If we're using the argument of the function only once, as above, we can write the function as:
```scala
aDivB.map(_ + 5)
```
> Using functions as arguments (called "higher-order functions") isn't actually tied to `Option[T]` specifically, but is a general pattern in functional programming. Many functions in all of the standard library classes use such patterns.

### Try[T]

We've seen how we can use `try...catch` structure to catch exceptional behaviors. A more _functional_ style of doing the same would be to use the `Try[T]` trait, which has the following cases: `Success[T]` and `Failure[T]`. We would use them in the following way: instead of doing
```scala
try {
  val d = a / 0
} catch { ... }
``` 
we would write:
```scala
val d = Try(a / 0)
```
When using `try...catch`, it's as if the value `d` has the type `Int` and sometimes doesn't exist. When using `Try`, the value always exist, but is sometimes a `Success(n)`, where `n` holds the actual value, and sometimes a `Failure(e)` where `e` is an `Exception` object. To use `Try`, we would match it similarly to how we matched `Option[T]`.
 
## Exercise Project: LinkedList

Open the  **linkedlist**  project folder and set up your development environment in it.

-   Open an SBT console in that folder;
-   Check that the code compiles by executing  `compile`  in the console;
-   Open your editor of choice in that folder

The project should compile, but contains many missing implementations that you will fill in. In this project, we'll build the basis for a `LinkedList` class. There are three methods you should fill in:

1. `def hasMore: Boolean = ???` which should return true if there's more in the list (if the tail of the list isn't empty).

2. `def maybeHead: Option[T] = ???` should return the value which is in the node under examination or `None` if there really is none.

3. `def map[U](fn: T => U): LinkedList[U] = ???` which behaves like `map` described in `Option{T]`. When applied to the end of the list, it should return the end (maybe of a different type, if you implemented `ListEnd` using case classes). When applied to an actual node, it should return a new node, whose value is `fn` applied to its own value. For example, if we take the list we used earlier and map the function `_ + 5` from before:
```scala
val lst = ListNode(2, ListNode(3, ListEnd))
println(lst.map(_ + 5))   // prints ListNode(7, ListNode(8, ListEnd))
```

> Use the SBT `compile`,`console`, `consoleQuick` and `test` options to help yourself out. 

When all the tests pass, you're done! Congratulations!

