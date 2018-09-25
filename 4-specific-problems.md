
 
# Specific Problems

> Note: this tutorial assumes that you've read and understood the "Programming in Scala (3rd Edition)" up to (and including) chapter 13, and specifically chapters **19**, **20** and **21**. If you haven't, go and review this material. The tutorials will outline specific ways in which we use Scala in this course.

This tutorial is basically just an extended exercise after reading chapters 19, 20 and 21. Use the techniques for tailrec annotations and type bounds to fix the problems with the code below.

## Problem 1

Complete the following method that calculates the factorial, so that it is tail recursive (described in section 8.9), and compiles (fill in the `???` spots, too):

```scala
def factorial(n: Int): Int = {
  @tailrec
  def iter(x: Int, result: Int): Int =
    if (x == ???) result
    else iter(x - ???, result * x)
  
  iter(n, res2)
}
```
> Hint: you can change everything within the function except that it has to remain `@tailrec`!

## Problem 2

Consider the following code:
```scala
trait Animal {
  def fitness: Int
}

trait Reptile extends Animal
trait Mammal extends Animal

trait Zebra extends Mammal

trait Giraffe extends Mammal
```
Animals, as defined above, have a certain `fitness` to survive. 

Write a method called `selection` that takes two animals as parameters and returns the one with the highest `fitness` value. What would the best signature of a method like that be? Consider the following:

```scala
object Evolution {
  def selection(a1: Animal, a2: Animal): Animal
}
```
How would you improve this method, so that we can only compare a `Zebra` to `Zebra`, a `Giraffe` to `Girrafe` and so on, without being able to compare a `Mammal` to a `Reptile`?

## Problem 3

Consider the following:
```scala
trait Fruit {
  def name: String
  def rename(into: String): Pet
}

case class Apple(name: String) extends Fruit {
  override def rename(into: String) = copy(name = into)
}

case class Orange(name: String) extends Fruit {
  override def rename(into: String) = copy(name = into)
}
```
Fix the code above (change the signature of the `rename` method in the trait, for example)  so that the following code _can't_ compile:
```scala
case class Tomato(name: String) extends Fruit {
  override def rename(into: String) = new Apple(into)
}
```
> Hint: read chapter 19 if you're sort of lost on this one...
