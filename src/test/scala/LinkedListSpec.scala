
package edu.ucsb.cs.cs162.tuts.linkedlist

import scala.language.reflectiveCalls
import org.scalatest._

class LinkedListSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  def fixture = new {
    val intList = Node(1, Node(2, Node(3, Node(4, Empty()))))

    def seqToLinkedList[T](l: T*): LinkedList[T] =
      l.reverse.foldLeft(Empty[T](): LinkedList[T]) { case (all, el) => Node(el, all) }
  }

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "An empty list" should "throw an exception if asked for a head element" in {
    assertThrows[Exception](Empty().head)
  }

  it should "return None when asked for a safe head element" in {
    assert(Empty().safeHead == None)
  }

  it should "return an empty tail" in {
    assert(Empty().tail == Empty())
  }

  it should "return a length of 0" in {
    assert(Empty().length == 0)
  }

  it should "return an empty list if asked to take or drop 0 elements" in {
    assert(Empty().take(0) == Empty())
    assert(Empty().drop(0) == Empty())
  }

  it should "throw an exception if asked to take or drop elements" in {
    (1 to 10).foreach { i =>
      assertThrows[Exception](Empty().take(i))
      assertThrows[Exception](Empty().drop(i))
    }
  }

  it should "return an empty list if asked for map or flatMap" in {
    assert(Empty().map { x: Int => x + 1 } == Empty())    
    assert(Empty().map { x: Double => x.toString } == Empty())
    assert(Empty().flatMap { x: Int => Node(x + 1, Empty()) } == Empty())   
    assert(Empty().flatMap { x: Double => Node(x.toString, Empty()) } == Empty())
  }

  it should "return an empty list when filtering by any predicate" in {
    assert(Empty().filter { x: Int => true } == Empty())    
    assert(Empty().filter { x: Double => false } == Empty())
  }

  it should "not change a list that is appended to it" in {
    def testWith[T](l: LinkedList[T]) = 
      assert(Empty().append(l) == l)

    testWith(Empty())
    testWith(Node(2, Empty()))
    testWith(Node(2, Node(3, Empty())))
    testWith(Node(1, Node(2, Node(3, Empty()))))
  }

  it should "return the starting value when folded" in {
    def testWith[T, U](start: U, fn: (U, T) => U) =
      assert(Empty[T]().foldLeft(start)(fn) == start)

    testWith[Int, String]("Hello", { _ + _.toString })
    testWith[Int, Int](10, { _ + _ })
  }

  "A non-empty list node" should "be able to return the head element" in {
    assert(Node(1, Empty()).head == 1)
    assert(Node("1", Node("2", Empty())).head == "1")
  }

  it should "be able to safely return some head element" in {
    assert(Node(1, Empty()).safeHead == Some(1))
    assert(Node("1", Node("2", Empty())).safeHead == Some("1"))
  }

  it should "always have a tail that it can return" in {
    assert(Node(1, Empty()).tail == Empty())
    assert(Node("1", Node("2", Empty())).tail == Node("2", Empty()))
  }

  it should "have a non-zero length" in {
    assert(Node(1, Empty()).length == 1)
    assert(Node(1, Node(2, Empty())).length == 2)
    assert(Node(1, Node(2, Node(3, Empty()))).length == 3)
    assert(Node(1, Node(2, Node(3, Node(4, Empty())))).length == 4)
  }

  it should "be able to take N first elements" in {
    assert(Node(1, Empty()).take(0) == Empty())
    assert(Node(1, Empty()).take(1) == Node(1, Empty()))
    assert(Node(1, Node(2, Empty())).take(1) == Node(1, Empty()))
    assert(Node(1, Node(2, Node(3, Empty()))).take(2) == Node(1, Node(2, Empty())))
    assert(Node(1, Node(2, Node(3, Node(4, Empty())))).take(3) == Node(1, Node(2, Node(3, Empty()))))
  }

  it should "be able to drop N first elements" in {
    assert(Node(1, Empty()).drop(1) == Empty())
    assert(Node(1, Node(2, Empty())).drop(1) == Node(2, Empty()))
    assert(Node(1, Node(2, Node(3, Empty()))).drop(2) == Node(3, Empty()))
    assert(Node(1, Node(2, Node(3, Node(4, Empty())))).drop(2) == Node(3, Node(4, Empty())))
  }

  it should "be able to mix take and drop" in {
    assert(fixture.intList.drop(1).take(2) == Node(2, Node(3, Empty())))
    assert(fixture.intList.drop(2).take(2) == Node(3, Node(4, Empty())))
    assert(fixture.intList.drop(1).take(3).drop(1) == Node(3, Node(4, Empty())))
  }

  it should "be able to map with a function" in {
    assert(fixture.intList.map { x: Int => x + 100 } == Node(101, Node(102, Node(103, Node(104, Empty())))))
    assert(fixture.intList.map { x: Int => s"$x" } == Node("1", Node("2", Node("3", Node("4", Empty())))))
  }

  it should "be able to flatMap with a function" in {
    // basically, flatMap N into N ranges from 1 to the current iterator, 
    // so for N = 4: (1) (1, 2) (1, 2, 3) (1, 2, 3, 4)

    assert(fixture.intList.flatMap { x: Int => fixture.seqToLinkedList((1 to x).map("*" * _): _*) } == 
      fixture.seqToLinkedList("*", "*", "**", "*", "**", "***", "*", "**", "***", "****"))
  }

  it should "be able to filter with a predicate" in {
    assert(fixture.intList.filter(x => true) == fixture.intList)
    assert(fixture.intList.filter(x => false) == Empty())
    assert(fixture.intList.filter(x => x % 2 == 0) == Node(2, Node(4, Empty())))
  }

  it should "be able to append another list" in {
    assert(fixture.intList.append(Node(5, Empty())) == Node(1, Node(2, Node(3, Node(4, Node(5, Empty()))))))
    assert(fixture.intList.append(Node(5, Node(6, Empty()))) == Node(1, Node(2, Node(3, Node(4, Node(5, Node(6, Empty())))))))
    assert(fixture.intList.append(Empty()) == fixture.intList)
  }  

  it should "be able to fold from the left" in {
    assert(fixture.intList.foldLeft(0) { _ + _ } == 10)
    assert(fixture.intList.foldLeft("*") { _ + _.toString } == "*1234")
  }

  it should "be able to fold from the right" in {
    assert(fixture.intList.foldRight(0) { _ + _ } == 10)
    assert(fixture.intList.foldRight("*") { _.toString + _ } == "1234*")
  }
}
