
package edu.ucsb.cs.cs162.tuts.linkedlist

// A linked list trait with two case classes -- a non-empty 
//  list and an empty list.
sealed trait LinkedList[T] {
  // Returns the head of a list or throws an exception if no head found.
  def head: T

  // Returns Some(head) if there is a head, or None if there isn't.
  def safeHead: Option[T]

  // Returns the elements behind the head element (could be Empty).
  def tail: LinkedList[T]

  // Returns the length of the linked list, which is equal to the number
  //  of non-empty nodes in the list.
  def length: Int

  // Returns a list consisting of the first `n` nodes of the list.
  // Throws an exception if the requested number is more than the list has.
  def take(n: Int): LinkedList[T]

  // Returns a list consisting of all the elements except the first `n` nodes of the list.
  // Throws an exception if the requested number is more than the list has.
  def drop(n: Int): LinkedList[T]

  // Maps a function over the list producing a new list with exactly the same number of elements,
  //  but where every element is equal to `fn` applied to the corresponding element
  //  from the original list. 
  // Example: (1, (2, (3, Empty))) -- *3 --> (3, (6, (9, Empty)))
  def map[U](fn: T => U): LinkedList[U]

  // Maps a function over the list producing a linked list for every element in the original list,
  //  and flattening them together into one single list.
  // Example: 
  //  ("hi", ("you", ("!", Empty)))
  //   -- chars -> ( ('h', ('i', Empty)), ('y', ('o', ('u', Empty))), ('!', Empty), Empty ) -> 
  //   -> ('h', ('i', ('y', ('o', ('u', ('!', Empty))))))
  def flatMap[U](fn: T => LinkedList[U]): LinkedList[U]

  // Filters all the elements from the original list where the predicate `pred` is true.
  // Example: (1, (2, (3, (4, Empty)) -- x % 2 == 0 --> (2, (4, Empty))  
  def filter(pred: T => Boolean): LinkedList[T]

  // Appends two lists together.
  def append(other: LinkedList[T]): LinkedList[T]

  // Folds the list from the left, using `start` as a starting value, and reducing the list in steps
  //  using a function that has an `accum` accumulation argument and the current value from the list.
  def foldLeft[U](start: U)(fn: (U, T) => U): U

  // Folds the list from the right, using `start` as a starting value, and reducing the list in steps
  //  using a function that has an `accum` accumulation argument and the current value from the list.
  def foldRight[U](start: U)(fn: (T, U) => U): U
}

// A non-empty implementation of LinkedList[T].
// Hint: only one method implementation in this class isn't a one-liner.
case class Node[T](value: T, rest: LinkedList[T]) extends LinkedList[T] {
  def head: T = ???

  def safeHead: Option[T] = ???

  def tail: LinkedList[T] = ???

  def length: Int = ???

  def take(n: Int): LinkedList[T] = ???

  def drop(n: Int): LinkedList[T] = ???

  def map[U](fn: T => U): LinkedList[U] = ???

  def flatMap[U](fn: T => LinkedList[U]): LinkedList[U] = ???

  def filter(pred: T => Boolean): LinkedList[T] = ???

  def append(other: LinkedList[T]): LinkedList[T] = ???

  def foldLeft[U](start: U)(fn: (U, T) => U): U = ???

  def foldRight[U](start: U)(fn: (T, U) => U): U = ???
}

// An empty implementation of LinkedList[T]
case class Empty[T]() extends LinkedList[T] {
  def head: T =
    throw new Exception("Empty list does not have a head!")

  def safeHead: Option[T] =
    None

  def tail: LinkedList[T] =
    Empty[T]()

  def length: Int =
    0

  def take(n: Int): LinkedList[T] = // ???
    if(n > 0) throw new Exception(s"Empty list does not have ${n} elements to take!") else Empty[T]()

  def drop(n: Int): LinkedList[T] = // ???
    if(n > 0) throw new Exception(s"Empty list does not have ${n} elements to drop!") else Empty[T]()

  def map[U](fn: T => U): LinkedList[U] = // ???
    Empty[U]()

  def flatMap[U](fn: T => LinkedList[U]): LinkedList[U] = // ???
    Empty[U]()

  def filter(pred: T => Boolean): LinkedList[T] = // ???
    Empty[T]()

  def append(other: LinkedList[T]): LinkedList[T] = // ???
    other

  def foldLeft[U](start: U)(fn: (U, T) => U): U = // ???
    start

  def foldRight[U](start: U)(fn: (T, U) => U): U = // ???
    start
}