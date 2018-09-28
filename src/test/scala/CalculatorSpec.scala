
package edu.ucsb.cs.cs162.tuts.calculator

import scala.language.reflectiveCalls
import org.scalatest._

class SimplifyHeadSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  import Calculator._

  def fixture = 
    Seq(
      Var("x"), Var("hello"),
      Num(3), Num(-2), Num(0), 
      UnOp("-", Var("x")), 
      UnOp("!", Var("hello")),
      UnOp("*", Var("ptr")),
      BinOp("*", Num(2), Num(4)),
      BinOp("+", Num(0), Num(5)),
      BinOp("-", Num(2), Num(2))
    )

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "An expression" should "be simplified to N if it has the form -(-N)" in {
    def testWith(expr: Expr) = 
      simplifyHead(UnOp("-", UnOp("-", expr))) shouldBe expr
  
    fixture.foreach { testWith(_) }
  }

  it should "be simplified to X if it has the form 0 + X or X + 0" in {
    def testLeftWith(expr: Expr) =
      simplifyHead(BinOp("+", Num(0), expr)) shouldBe expr

    def testRightWith(expr: Expr) =
      simplifyHead(BinOp("+", expr, Num(0))) shouldBe expr

    fixture.foreach { expr =>
      testLeftWith(expr)
      testRightWith(expr)
    }
  }

  it should "be simplified to X if it has the form 1 * X or X * 1" in {
    def testLeftWith(expr: Expr) =
      simplifyHead(BinOp("*", Num(1), expr)) shouldBe expr

    def testRightWith(expr: Expr) =
      simplifyHead(BinOp("*", expr, Num(1))) shouldBe expr

    fixture.foreach { expr =>
      testLeftWith(expr)
      testRightWith(expr)
    }
  }

  it should "be simplified to 0 if it has the form 0 * X or X * 0" in {
    def testLeftWith(expr: Expr) =
      simplifyHead(BinOp("*", Num(0), expr)) shouldBe Num(0)

    def testRightWith(expr: Expr) =
      simplifyHead(BinOp("*", expr, Num(0))) shouldBe Num(0)

    fixture.foreach { expr =>
      testLeftWith(expr)
      testRightWith(expr)
    }
  }

  it should "be simplified to 0 if it has the form X - X" in {
    def testWith(expr: Expr) =
      simplifyHead(BinOp("-", expr, expr)) shouldBe Num(0)

    fixture.foreach { testWith(_) }
  }
}

class EvaluateSpec extends FlatSpec with Matchers {
  //---------------------------------------------------
  // Fixtures and headers
  //---------------------------------------------------

  import Calculator._

  def fixture = new {
    val numbers = Seq(0, 100, -42, 3.1415)
    val variables = Seq("hello", "world", "x", "y", "weird?!?")
    val badOps = Seq("?", "#", "!", "@", "op", "unary", "binary")

    val expressions = numbers.map(Num(_)) ++ variables.map(Var(_)) ++ Seq(
      UnOp("-", Num(3)), BinOp("+", Num(1), Num(1)), 
      BinOp("+", UnOp("-", Num(7)), Num(7)), UnOp("-", UnOp("-", Num(3)))
    )

    val pairs = expressions zip expressions.reverse
  }

  //---------------------------------------------------
  // Tests
  //---------------------------------------------------

  "A number" should "evaluate to its value" in {
    fixture.numbers.foreach { value =>
      evaluate(Num(value)) shouldBe value
    }
  }

  "A variable" should "evaluate to 1" in {
    fixture.variables.foreach { text =>
      evaluate(Var(text)) shouldBe 1
    }
  }

  "A unary operation" should "evaluate to 1 if the operator isn't '-'" in {
    def testWithOp(op: String)(e: Expr) =
      evaluate(UnOp(op, e)) shouldBe 1

    fixture.badOps.foreach { op =>
      val testWith = testWithOp(op) _

      fixture.expressions.foreach { testWith(_) }
    }
  }

  it should "evaluate to a negative of the argument if the operator is '-'" in {
    fixture.numbers.foreach { value =>
      evaluate(UnOp("-", Num(value))) shouldBe -evaluate(Num(value))
    }

    fixture.expressions.foreach { expr =>
      evaluate(UnOp("-", expr)) shouldBe -evaluate(expr)
    }
  }

  "A binary operation" should "evaluate to 1 if the operator isn't '+', '-' or '*'" in {
    def testWithOp(op: String)(e: Expr, f: Expr) =
      evaluate(BinOp(op, e, f)) shouldBe 1

    fixture.badOps.foreach { op =>
      val testWith = testWithOp(op) _

      fixture.pairs.foreach { case (a, b) => testWith(a, b) }
    }
  }

  it should "evaluate to a sum of the arguments if the operator is '+'" in {
    evaluate(BinOp("+", Num(-2), Num(2))) shouldBe 0
    evaluate(BinOp("+", Num(2), Num(3))) shouldBe 5
    evaluate(BinOp("+", Num(-1), Num(-1))) shouldBe -2
    evaluate(BinOp("+", BinOp("+", Num(2), Num(3)), Num(5))) shouldBe 10
  }

  it should "evaluate to a difference of the arguments if the operator is '-'" in {
    evaluate(BinOp("-", Num(-2), Num(2))) shouldBe -4
    evaluate(BinOp("-", Num(2), Num(3))) shouldBe -1
    evaluate(BinOp("-", Num(-1), Num(-1))) shouldBe 0
    evaluate(BinOp("-", BinOp("-", Num(2), Num(3)), Num(5))) shouldBe -6
  }

  it should "evaluate to a product of the arguments if the operator is '*'" in {
    evaluate(BinOp("*", Num(-2), Num(2))) shouldBe -4
    evaluate(BinOp("*", Num(2), Num(3))) shouldBe 6
    evaluate(BinOp("*", Num(-1), Num(-1))) shouldBe 1
    evaluate(BinOp("*", BinOp("*", Num(2), Num(3)), Num(5))) shouldBe 30
  }

  it should "treat Var(DUP) as a duplicate of the other argument" in {
    evaluate(BinOp("+", Var("DUP"), Num(2))) shouldBe 4
    evaluate(BinOp("*", Var("DUP"), Num(10))) shouldBe 100
    evaluate(BinOp("-", Var("DUP"), Num(-1))) shouldBe 0
    evaluate(BinOp("+", Num(2), Var("DUP"))) shouldBe 4
    evaluate(BinOp("*", Num(10), Var("DUP"))) shouldBe 100
    evaluate(BinOp("-", Num(-1), Var("DUP"))) shouldBe 0
  }
}

