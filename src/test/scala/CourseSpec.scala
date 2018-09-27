
package edu.ucsb.cs.cs162.tuts.introduction

import scala.language.reflectiveCalls
import org.scalatest._

class CourseSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  // Useful fixtures for all the tests.
  def fixture = new {
    val goodSubjects = Seq("ANTH", "ART", "CMPSC")
    val badSubjects = Seq("", "AR", "CMPSCCMPSC")
    val goodNumbers = Seq(4, 8, 64, 101, 160, 162)
    val badNumbers = Seq(-202, -1, 0, 600, 1000)

    val subjectNumberPairs = for { x <- goodSubjects; y <- goodNumbers } yield (x, y)
  }

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "A course" should "tell us what its name is" in {
    assert(new Course("ANTH", 4).name == "ANTH4")
    assert(new Course("ANTH", 8).name == "ANTH8")
    assert(new Course("ANTH", 64).name == "ANTH64")
    assert(new Course("ANTH", 101).name == "ANTH101")
    assert(new Course("ANTH", 160).name == "ANTH160")
    assert(new Course("ANTH", 162).name == "ANTH162")

    assert(new Course("ART", 4).name == "ART4")
    assert(new Course("ART", 8).name == "ART8")
    assert(new Course("ART", 64).name == "ART64")
    assert(new Course("ART", 101).name == "ART101")
    assert(new Course("ART", 160).name == "ART160")
    assert(new Course("ART", 162).name == "ART162")

    assert(new Course("CMPSC", 4).name == "CMPSC4")
    assert(new Course("CMPSC", 8).name == "CMPSC8")
    assert(new Course("CMPSC", 64).name == "CMPSC64")
    assert(new Course("CMPSC", 101).name == "CMPSC101")
    assert(new Course("CMPSC", 160).name == "CMPSC160")
    assert(new Course("CMPSC", 162).name == "CMPSC162")

    // These next three lines are the same as 18 lines the above:
    fixture.subjectNumberPairs.foreach { case (name, number) =>
      assert(new Course(name, number).name == s"${name}${number}")
    }
    // We use the `fixture` object from above, this is where we keep useful things
    //  like some names and numbers that can be used in multiple tests.
  }

  it should "throw an illegal argument exception if the course number isn't between 1 and 600" in {
    fixture.goodNumbers.foreach { number =>
      noException should be thrownBy (new Course("NVM", number))
    }

    fixture.badNumbers.foreach { number =>
      an [IllegalArgumentException] should be thrownBy (new Course("NVM", number))
    }
  }

  it should "throw an assertion error if the course subject is shorter than 3 or longer than 5 characters" in {
    fixture.goodSubjects.foreach { subject =>
      noException should be thrownBy (new Course(subject, 300))
    }

    fixture.badSubjects.foreach { subject =>
      an [IllegalArgumentException] should be thrownBy (new Course(subject, 300))
    }
  }

  // Look at these tests, they will tell you how to implement the methods 
  //  in the actual class that you are testing.
  it should "be a lower division course if the number is between 1 and 99" in {
    (1 to 99).foreach { number =>
      val introduction = new Course("NVM", number)
      assert(introduction.isLowerDivisionCourse)
      assert(!introduction.isUpperDivisionCourse)
      assert(!introduction.isGradDivisionCourse)
      assert(!introduction.isSeminar)
    }
  }

  it should "be an upper division course if the number is between 100 and 200" in {
    (100 to 199).foreach { number =>
      val introduction = new Course("NVM", number)
      assert(!introduction.isLowerDivisionCourse)
      assert(introduction.isUpperDivisionCourse)
      assert(!introduction.isGradDivisionCourse)
      assert(!introduction.isSeminar)
    }
  }

  it should "be a grad division course if the number is between 200 and 500" in {
    (200 to 299).foreach { number =>
      val introduction = new Course("NVM", number)
      assert(!introduction.isLowerDivisionCourse)
      assert(!introduction.isUpperDivisionCourse)
      assert(introduction.isGradDivisionCourse)
      assert(!introduction.isSeminar)
    }
  }

  it should "be a seminar if the number is between 500 and 599" in {
    (500 to 599).foreach { number =>
      val introduction = new Course("NVM", number)
      assert(!introduction.isLowerDivisionCourse)
      assert(!introduction.isUpperDivisionCourse)
      assert(!introduction.isGradDivisionCourse)
      assert(introduction.isSeminar)
    }
  }  
}
