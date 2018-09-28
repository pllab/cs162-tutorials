
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
    an [Exception] should be thrownBy (Empty().head)
  }

  it should "return None when asked for a safe head element" in {
    Empty().safeHead shouldBe None
  }

  it should "return an empty tail" in {
    Empty().tail shouldBe Empty()
  }

  it should "return a length of 0" in {
    Empty().length shouldBe 0
  }

  it should "return an empty list if asked to take or drop 0 elements" in {
    Empty().take(0) shouldBe Empty()
    Empty().drop(0) shouldBe Empty()
  }

  it should "throw an exception if asked to take or drop elements" in {
    (1 to 10).foreach { i =>
      an [Exception] should be thrownBy (Empty().take(i))
      an [Exception] should be thrownBy (Empty().drop(i))
    }
  }

  it should "return an empty list if asked for map or flatMap" in {
    Empty().map { x: Int => x + 1 } shouldBe Empty()   
    Empty().map { x: Double => x.toString } shouldBe Empty()
    Empty().flatMap { x: Int => Node(x + 1, Empty()) } shouldBe Empty()  
    Empty().flatMap { x: Double => Node(x.toString, Empty()) } shouldBe Empty()
  }

  it should "return an empty list when filtering by any predicate" in {
    Empty().filter { x: Int => true } shouldBe Empty()
    Empty().filter { x: Double => false } shouldBe Empty()
  }

  it should "not change a list that is appended to it" in {
    def testWith[T](l: LinkedList[T]) = 
      Empty().append(l) shouldBe l

    testWith(Empty())
    testWith(Node(2, Empty()))
    testWith(Node(2, Node(3, Empty())))
    testWith(Node(1, Node(2, Node(3, Empty()))))
  }

  it should "return the starting value when folded" in {
    def testWith[T, U](start: U, fn: (U, T) => U) =
      Empty[T]().foldLeft(start)(fn) shouldBe start

    testWith[Int, String]("Hello", { _ + _.toString })
    testWith[Int, Int](10, { _ + _ })
  }

  "A non-empty list node" should "be able to return the head element" in {
    Node(1, Empty()).head shouldBe 1
    Node("1", Node("2", Empty())).head shouldBe "1"
  }

  it should "be able to safely return some head element" in {
    Node(1, Empty()).safeHead shouldBe Some(1)
    Node("1", Node("2", Empty())).safeHead shouldBe Some("1")
  }

  it should "always have a tail that it can return" in {
    Node(1, Empty()).tail shouldBe Empty()
    Node("1", Node("2", Empty())).tail shouldBe Node("2", Empty())
  }

  it should "have a non-zero length" in {
    Node(1, Empty()).length shouldBe 1
    Node(1, Node(2, Empty())).length shouldBe 2
    Node(1, Node(2, Node(3, Empty()))).length shouldBe 3
    Node(1, Node(2, Node(3, Node(4, Empty())))).length shouldBe 4
  }

  it should "be able to take N first elements" in {
    Node(1, Empty()).take(0) shouldBe Empty()
    Node(1, Empty()).take(1) shouldBe Node(1, Empty())
    Node(1, Node(2, Empty())).take(1) shouldBe Node(1, Empty())
    Node(1, Node(2, Node(3, Empty()))).take(2) shouldBe Node(1, Node(2, Empty()))
    Node(1, Node(2, Node(3, Node(4, Empty())))).take(3) shouldBe Node(1, Node(2, Node(3, Empty())))
  }

  it should "be able to drop N first elements" in {
    Node(1, Empty()).drop(1) shouldBe Empty()
    Node(1, Node(2, Empty())).drop(1) shouldBe Node(2, Empty())
    Node(1, Node(2, Node(3, Empty()))).drop(2) shouldBe Node(3, Empty())
    Node(1, Node(2, Node(3, Node(4, Empty())))).drop(2) shouldBe Node(3, Node(4, Empty()))
  }

  it should "be able to mix take and drop" in {
    fixture.intList.drop(1).take(2) shouldBe Node(2, Node(3, Empty()))
    fixture.intList.drop(2).take(2) shouldBe Node(3, Node(4, Empty()))
    fixture.intList.drop(1).take(3).drop(1) shouldBe Node(3, Node(4, Empty()))
  }

  it should "be able to map with a function" in {
    fixture.intList.map { x: Int => x + 100 } shouldBe Node(101, Node(102, Node(103, Node(104, Empty()))))
    fixture.intList.map { x: Int => s"$x" } shouldBe Node("1", Node("2", Node("3", Node("4", Empty()))))
  }

  it should "be able to flatMap with a function" in {
    // basically, flatMap N into N ranges from 1 to the current iterator, 
    // so for N = 4: (1) (1, 2) (1, 2, 3) (1, 2, 3, 4)

    fixture.intList.flatMap { x: Int => fixture.seqToLinkedList((1 to x).map("*" * _): _*) } shouldBe
      fixture.seqToLinkedList("*", "*", "**", "*", "**", "***", "*", "**", "***", "****")
  }

  it should "be able to filter with a predicate" in {
    fixture.intList.filter(x => true) shouldBe fixture.intList
    fixture.intList.filter(x => false) shouldBe Empty()
    fixture.intList.filter(x => x % 2 == 0) shouldBe Node(2, Node(4, Empty()))
  }

  it should "be able to append another list" in {
    fixture.intList.append(Node(5, Empty())) shouldBe Node(1, Node(2, Node(3, Node(4, Node(5, Empty())))))
    fixture.intList.append(Node(5, Node(6, Empty()))) shouldBe Node(1, Node(2, Node(3, Node(4, Node(5, Node(6, Empty()))))))
    fixture.intList.append(Empty()) shouldBe fixture.intList
  }  

  it should "be able to fold from the left" in {
    fixture.intList.foldLeft(0) { _ + _ } shouldBe 10
    fixture.intList.foldLeft("*") { _ + _.toString } shouldBe "*1234"
  }

  it should "be able to fold from the right" in {
    fixture.intList.foldRight(0) { _ + _ } shouldBe 10
    fixture.intList.foldRight("*") { _.toString + _ } shouldBe "1234*"
  }
}
