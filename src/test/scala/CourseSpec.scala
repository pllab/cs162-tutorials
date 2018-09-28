
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
    new Course("ANTH", 4).name shouldBe "ANTH4"
    new Course("ANTH", 8).name shouldBe "ANTH8"
    new Course("ANTH", 64).name shouldBe "ANTH64"
    new Course("ANTH", 101).name shouldBe "ANTH101"
    new Course("ANTH", 160).name shouldBe "ANTH160"
    new Course("ANTH", 162).name shouldBe "ANTH162"

    new Course("ART", 4).name shouldBe "ART4"
    new Course("ART", 8).name shouldBe "ART8"
    new Course("ART", 64).name shouldBe "ART64"
    new Course("ART", 101).name shouldBe "ART101"
    new Course("ART", 160).name shouldBe "ART160"
    new Course("ART", 162).name shouldBe "ART162"

    new Course("CMPSC", 4).name shouldBe "CMPSC4"
    new Course("CMPSC", 8).name shouldBe "CMPSC8"
    new Course("CMPSC", 64).name shouldBe "CMPSC64"
    new Course("CMPSC", 101).name shouldBe "CMPSC101"
    new Course("CMPSC", 160).name shouldBe "CMPSC160"
    new Course("CMPSC", 162).name shouldBe "CMPSC162"

    // These next three lines are the same as 18 lines the above:
    fixture.subjectNumberPairs.foreach { case (name, number) =>
      new Course(name, number).name shouldBe s"${name}${number}"
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
      introduction shouldBe 'isLowerDivisionCourse
      introduction should not be 'isUpperDivisionCourse
      introduction should not be 'isGradDivisionCourse
      introduction should not be 'isSeminar
    }
  }

  it should "be an upper division course if the number is between 100 and 200" in {
    (100 to 199).foreach { number =>
      val introduction = new Course("NVM", number)
      introduction should not be 'isLowerDivisionCourse
      introduction shouldBe 'isUpperDivisionCourse
      introduction should not be 'isGradDivisionCourse
      introduction should not be 'isSeminar
    }
  }

  it should "be a grad division course if the number is between 200 and 500" in {
    (200 to 299).foreach { number =>
      val introduction = new Course("NVM", number)
      introduction should not be 'isLowerDivisionCourse
      introduction should not be 'isUpperDivisionCourse
      introduction shouldBe 'isGradDivisionCourse
      introduction should not be 'isSeminar
    }
  }

  it should "be a seminar if the number is between 500 and 599" in {
    (500 to 599).foreach { number =>
      val introduction = new Course("NVM", number)
      introduction should not be 'isLowerDivisionCourse
      introduction should not be 'isUpperDivisionCourse
      introduction should not be 'isGradDivisionCourse
      introduction shouldBe 'isSeminar
    }
  }  
}
