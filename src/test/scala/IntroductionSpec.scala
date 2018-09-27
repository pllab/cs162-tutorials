
package edu.ucsb.cs.cs162.tuts.introduction

import scala.language.reflectiveCalls
import org.scalatest._

class CourseSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  def fixture = new {
    val goodSubjects = Seq("ANTH", "ART", "CMPSC", "ECON", "HIST", "MUSIC")
    val badSubjects = Seq("A", "AR", "CMPSCCMPSC", "ECONNOCE", "HHIISSTT", "")
    val goodNumbers = Seq(4, 8, 64, 101, 160, 162)
    val badNumbers = Seq(-202, -1, 0, 600, 1000)

    val subjectNumberPairs = for { x <- goodSubjects; y <- goodNumbers } yield (x, y)
  }

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "A course" should "tell us what its name is" in {
    fixture.subjectNumberPairs.foreach { case (name, number) =>
      assert(new Course(name, number).name == s"${name}${number}")
    }
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
