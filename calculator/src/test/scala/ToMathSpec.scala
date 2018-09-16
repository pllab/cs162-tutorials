package cs162.tutorials.calculator

import org.scalatest._
import org.scalatest.prop._
import org.scalacheck._
import scala.util.{ Try, Success }

class ToMathSpec extends FlatSpec with Matchers with Checkers {
	import Calculator._

	"Numbers" should "be stringified as integers" in {
		check((a: Int) =>  
			toMath(Num(a)) == s"$a")
	}

	"Sum(a, b)" should "be stringified as (a + b)" in {
		check((a: Int, b: Int) =>
			toMath(Add(Num(a), Num(b))) == s"($a + $b)")
	}

	"Sub(a, b)" should "be stringified as (a - b)" in {
		check((a: Int, b: Int) =>
			toMath(Sub(Num(a), Num(b))) == s"($a - $b)")
	}

	"Mul(a, b)" should "be stringified as (a * b)" in {
		check((a: Int, b: Int) =>
			toMath(Mul(Num(a), Num(b))) == s"($a * $b)")		
	}
}
