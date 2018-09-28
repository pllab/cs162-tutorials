
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
    ListProblems.sumOdd(List(1, 1, 1, 1)) shouldBe 4
    ListProblems.sumOdd(List(1, 2, 3, 4)) shouldBe 4
    ListProblems.sumOdd(List(2, 4)) shouldBe 0
    ListProblems.sumOdd(List()) shouldBe 0
  }

  "ListProblems.sumPairs" should "throw an illegal argument exception if the lists aren't of the same length" in {
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(), List(1)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1), List(1, 2)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1, 2, 3), List(1, 2)))
    an [IllegalArgumentException] should be thrownBy (ListProblems.sumPairs(List(1), List(1, 2, 3, 4)))
  }

  it should "sum two lists pairwise" in {
    ListProblems.sumPairs(List(2), List(100)) shouldBe List(102)
    ListProblems.sumPairs(List(1), List(1)) shouldBe List(2)
    ListProblems.sumPairs(List(-1, -2, -3), List(1, 2, 3)) shouldBe List(0, 0, 0)
    ListProblems.sumPairs(List(0, 0, 0), List(1, 1, 1)) shouldBe List(1, 1, 1)
  }

  "ListProblems.safePenultimate" should "return the penultimate element if there is one, or none if not" in {
    ListProblems.safePenultimate(List()) shouldBe None
    ListProblems.safePenultimate(List(1)) shouldBe None
    ListProblems.safePenultimate(List(1, 2)) shouldBe Some(1)
    ListProblems.safePenultimate(List(1, 2, 3)) shouldBe Some(2)
    ListProblems.safePenultimate(List(1, 2, 3, 4)) shouldBe Some(3)
  }
}