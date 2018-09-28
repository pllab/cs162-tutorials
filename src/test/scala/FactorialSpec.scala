
package edu.ucsb.cs.cs162.tuts.functional

import scala.language.reflectiveCalls
import org.scalatest._

class FactorialSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "The factorial of N" should "be the product of all the numbers from 1 to N" in {
    Factorial(0) shouldBe 1
    Factorial(1) shouldBe 1
    Factorial(2) shouldBe 2 * 1
    Factorial(5) shouldBe 5 * 4 * 3 * 2 * 1

    // 50 * 40 * ... * 2 * 1
    Factorial(50) shouldBe BigInt("30414093201713378043612608166064768844377641568960512000000000000")

    // 100 * 99 * ... * 50 * 40 * ... * 2 * 1
    Factorial(100) shouldBe BigInt("93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000")
  }
}