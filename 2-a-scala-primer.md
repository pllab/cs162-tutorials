
# Simple Object-Oriented Programming

Scala is a complex, versatile high-level language. The basics of Scala, however, are easy to grasp. As previously stated, Scala is an object-oriented and functional language. We're going to delve into these topics and show how these paradigms are complementary to each other and offer great scalability together: functional programming gives us a way to easily combine simple parts into more complex ones, and object-oriented programming gives a way to structure these hierarchically. To try any of these features out, you can use SBT's `console` feature (how to get to it is explained in [tutorial 1](#)).

For this tutorial, it is advised you make a new project, following the same process as outlined in the previous tutorial. Call this project `community`, as our running example will involve interpersonal relations between people.

## Objects and Classes

Scala is a pure object-oriented language, meaning that all values in Scala are objects. An **object** is a collection of fields (values) and methods (functions) that describe the specific behaviors an object can perform. 

We've seen an object earlier, when we looked at the `Main.scala` file which contained an object of the same name:

```scala
object Main
```
This is a valid line of Scala code: it defines an empty object called Main. This object can't do anything as it doesn't have any fields or methods. As another example of objects, let's consider the following:

```scala
object UniquePerson {
  val message = "Hello!"

  def talk(): Unit = 
    println(message)
}
```
Here we have an object that contains one field (called `message`, and implicitly of type `String`) and a method `talk` that takes no parameters and returns a `Unit` -- it merely prints out the value held in the `message` field.

> A field is just a value or variable inside an object. The keyword `val` can be used to define immutable values, and the keyword `var` when mutability is needed. These aren't specific to defining fields, and can also define local variables, or be used in the console. Try to use `var` as little as possible. Whenever that seems hard, there's something you're missing about how to write Scala.

> When you don't really know what to fill a method with, write `???`. `???` is a value that will satisfy all typing conditions that are needed (for example, `def getString(): String = ???` will make `???` return a `String`), but when executed will throw an error (saying `Unimplemented exception`). This is useful to see whether all the pieces are set properly before actually testing the code out. Sometimes it's hard to start filling all the methods in, so remember you have this option.

As previously seen, to call a method on an object, we need to provide the object and method in the following notation: `<object>.<method>(<arguments>)`. For example, to run the above method, we'd invoke it as `UniquePerson.talk()`. This dot notation is not specific to only methods; we could also do `println(UniquePerson.message)` to reach the `message` field.

When we define an object as above, it is unique. Sometimes, however, we need to create many similar objects, with similar methods and fields, and thus need a class. A **class** is a blueprint for objects, and serves as a way to quickly create multiple objects that have similar behaviors and are recognizable as being similar (they are of the same _type_). Classes are very similar to objects. For example, to define an empty class, it is enough to name it:

```scala
class Person
```
This isn't very useful, so we can fill it in the same way we did with an object. However, with classes being object blueprints, we can do more: we can make them special. Following the example above, we might want to define people with different messages!

```scala
class Person(message: String) {
  def talk(): Unit =
    println(message)
}
```
> Many things in Scala can be omitted, as the Scala compiler is smart enough to figure them out. For example, this would also have worked just fine:
```scala
class Person(message: String) {
  // This is the same exact method as above.
  def talk = println(message)
}
```
> For starters, however, it's better to write out the full types to get used to them.
Now that we have our `Person` class, we can create objects of that class by instantiating them with the `new` keyword. You can save the code that defines the `Person` class into a file and fire up the Scala console within SBT and try this out:

```sbt
scala> val rob = new Person("Hello world")
rob: cs162.tutorials.helloworld.Person =
  cs162.tutorials.helloworld.Person@733c2ad7

scala>
```
With this, we've created a new Person, that behaves exactly like any other element of the `Person` class. The cryptic output of the Scala console is easy to read:

```scala
name: Type = <value>
```
In our case, the name of the variable created is `rob`, the type is our defined type `cs162.tutorials.helloworld.Person` and the value is a new element of this type and identified by hexadecimal identifiers (`733c2ad7` in this case), which are basically just random numbers.

We can check that `rob` is indeed an object of the `Person` class by checking out whether it responds to the `talk()` method:

```sbt
scala> rob.talk()
Hello world

scala>
```
> Again, Scala can make things shorter! When a method doesn't have arguments, you can omit the parentheses completely and basically just call `rob.talk`. This makes it indistinguishable from a field from the outside, which is okay, as we're aiming for completely transparent code anyway, with no internal changes.

### Exercises

1. Create `alex`, a friend of `rob`'s, who will say "Hello!". Test out whether `alex` can talk in the Scala console.
1. Create `sally`, a friend of `rob`'s and `alex`'s, who will say "Hi you two!". Test out whether `sally` can talk in the Scala console, too.
2. Make a main method (look at the [previous tutorial] for hints on what types it has to have) that makes `rob`, `alex` and `sally` talk, in any order you wish. Run the program from SBT's console, as done in the previous tutorial.

## Pure Object-Oriented Programming

As stated before, being a **pure** object-oriented language, everything in Scala is an object. Does this means that even numbers are objects then? Yes! Let's look at an example already briefly mentioned in the previous tutorial:

```scala
scala> 3 + 4
res0: Int = 7

scala>
```
We added these two numbers together seemingly without calling a method (using the dot notation). In actuality, we just have a choice to skip this notation when we find it cumbersome. For example, this works too:

```scala
scala> 3.+(4)
res0: Int = 7

scala>
```
This means that `3` is an object, the method name is `+` and `4` is the argument. If you try to apply this to our talking friends, we see it works albeit with a brief warning:

```
scala> rob talk
warning: there was one feature warning; 
  re-run with -feature for details
Hello world

scala>
```
> In practice, people often use this feature when they want their method call to look more like a feature of the language. For instance, to check whether a set contains an element, instead of writing `set.contains(element)` we can just say `set contains element`. Scala tries it's best to be concise and comfortable at every turn.

Types are a mechanism to stop silly mistakes from happening. For one, even dynamically-typed languages (that have types but check them at runtime) often have the following problem:

```scala
val radius = getRadius();    // returns Int, e.g. 34
val userInput = getInput();  // returns a String containing 
							 // a number, e.g. "22"
val newRadius = 
  radius + userInput;		 // output: 3422, obviously very wrong!
```
It would be nice to have a system that would warn us that `radius` and `userInput` aren't compatible, even though both have a `+` operation.

Types are exactly this system. For example, if we want the `newRadius` to be an integer in the end, we could simply annotate this: 
```scala
val radius = getRadius()		// getRadius(): Int = 34
val userInput = getInput() 		// getInput(): String = "22"
val newRadius: Int = 			// we annotate the value with
								// the expected type
  radius + userInput

<console>:13: error: type mismatch;
 found   : String
 required: Int
       var newRadius: Int = radius + userInput
								   ^
```
You can see that we didn't annotate `radius` and `userInput` when defining them. Scala can infer types in most situations and doesn't require you to put the annotations in yourself, but this process could be wrong. For example, had we _not_ annotated `newRadius: Int`, the inferred type would be `String`, and the value "3422". Using proper types will significantly decrease the number of awkward, buggy situations your code exhibits. 

Scala contains a number of built-in types that one might expect in a modern language, such as `Int`, `Byte`, `Float`, `Char`, `String`, `Array`, etc. New types can be defined using classes. For example, the `Person` class we defined above defined a new type that you can now use in your code. We can write a method `talkTo` as a part of `Person`:

```scala
class Person(message: String) {
  def talk = println(message)

  def talkWith(other: Person): Unit = {
    talk() 
    other.talk()
  }
}
```
> We can call methods concerning an object within itself by either calling it by name (so, just `talk()` in this case) or by using the special object `this`, and writing `this.talk()`)


### Exercises

4. How would you make `rob` and `sally` talk, and what would that conversation look like? Try it out in the Scala console.
5. Add a `name` field to the Person class (you decide on the appropriate type), so that we can change the `talkWith` method to do the following:
```scala
  def talkWith(other: Person): Unit = {
    println(this.name + ": " + this.message)
    println(other.name + ": " + other.message)
  }
```
> Strings in Scala can be _interpolated_ by using `s""` instead of just quotes, and blocking values into `${}` parts, so the above line can be rewritten as:
```scala
  println(s"${this.name}: ${this.message}")
```
6. Make a new community of people in the Scala console and see if they can talk with each other so that the output looks exactly like this:
```
Grievous: General Kenobi!
Obi: Hello there!
```
