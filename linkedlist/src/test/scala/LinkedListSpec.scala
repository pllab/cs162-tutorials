package cs162.tutorials.linkedlist

import org.scalatest._
import org.scalatest.Matchers._
import org.scalatest.prop._
import org.scalacheck._

class LinkedListSpec extends FlatSpec with Matchers with Checkers {

  "A non-empty list" should "have a length greater than 0" in {
    ListNode(3, ListEnd()) should have length 1
    ListNode(3, ListNode(2, ListEnd())) should have length 2
    ListNode(3, ListNode(2, ListNode(1, ListEnd()))) should have length 3
  }

  it should "have more elements" in {
    assert(ListNode(1, ListEnd()).hasMore)
    assert(ListNode(1, ListNode(0, ListEnd())).hasMore)
  }

  it should "have a head element to extract" in {
    check((head: Int) =>
      ListNode(head, ListEnd()).maybeHead == Some(head)  
    )
  }

  it should "map into a list of the same length, with values that are results of fn(x)" in {
    check((a: Int, b: Int, c: Int, d: Int) =>
        ListNode(a, ListNode(b, ListNode(c, ListEnd()))).map(_ + d) ==
          ListNode(a + d, ListNode(b + d, ListNode(c + d, ListEnd())))
    )
  }

  "An empty list" should "have length 0" in {
    ListEnd[Unit]() should have length 0
  }

  it should "not have more elements" in {
    assert(ListEnd[Unit]().hasMore == false)
  }

  it should "not have a head element to extract" in {
    ListEnd[Unit]().maybeHead should be (None)
  }

  it should "map into the list end for the type supplied in map" in {
    ListEnd[Int]().map(x => x) should be (ListEnd[Int]())
    ListEnd[Int]().map(x => "") should be (ListEnd[String]())
    ListEnd[Int]().map(x => ()) should be (ListEnd[Unit]())
  }
}

