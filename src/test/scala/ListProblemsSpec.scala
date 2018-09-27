
package edu.ucsb.cs.cs162.tuts.functional

import scala.language.reflectiveCalls
import org.scalatest._

class ListProblemsSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "ListProblems.sumOdd" should "filter out all the odd numbers and then sum those" in {
    assert(ListProblems.sumOdd(List(1, 1, 1, 1)) == 4)
    assert(ListProblems.sumOdd(List(1, 2, 3, 4)) == 4)
    assert(ListProblems.sumOdd(List(2, 4)) == 0)
    assert(ListProblems.sumOdd(List()) == 0)
  }

  "ListProblems.sumPairs" should "throw an illegal argument exception if the lists aren't of the same length" in {
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(), List(1)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1), List(1, 2)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1, 2, 3), List(1, 2)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1), List(1, 2, 3, 4)))
  }

  it should "sum two lists pairwise" in {
    assert(ListProblems.sumPairs(List(2), List(100)) == List(102))
    assert(ListProblems.sumPairs(List(1), List(1)) == List(2))
    assert(ListProblems.sumPairs(List(-1, -2, -3), List(1, 2, 3)) == List(0, 0, 0))
    assert(ListProblems.sumPairs(List(0, 0, 0), List(1, 1, 1)) == List(1, 1, 1))
  }

  "ListProblems.safePenultimate" should "return the penultimate element if there is one, or none if not" in {
    assert(ListProblems.safePenultimate(List()) == None)
    assert(ListProblems.safePenultimate(List(1)) == None)
    assert(ListProblems.safePenultimate(List(1, 2)) == Some(1))    
    assert(ListProblems.safePenultimate(List(1, 2, 3)) == Some(2))
    assert(ListProblems.safePenultimate(List(1, 2, 3, 4)) == Some(3))
  }
}