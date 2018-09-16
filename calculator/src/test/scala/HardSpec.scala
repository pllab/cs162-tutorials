
package cs162.tutorials.calculator

import org.scalatest._
import org.scalatest.prop._
import org.scalacheck._
import scala.util.{ Try, Success }

class HardSpec extends FlatSpec with Matchers with Checkers {
	import Calculator._

	"Div(a, b)" should "evaluate to a / b if b != 0, and throw an exception otherwise" in {
		import org.scalacheck.Prop.{throws}
		check { (a: Int, b: Int) => 
			if(b != 0) {
				evaluate(Div(Num(a), Num(b))) == a / b
			} else {
				throws(classOf[Exception])(evaluate(Div(Num(a), Num(b))))
			}
		}
	}

	it should "evaluate to a if b == 1" in {
		import org.scalacheck.Prop.{throws}
		check { (a: Int) => 
			evaluate(Div(Num(a), Num(1))) == a
		}
	}

	it should "evaluate to 1 if a == b" in {
		import org.scalacheck.Prop.{throws}
		check { (n: Int) => 
			if(n != 0) {
				evaluate(Div(Num(n), Num(n))) == 1
			} else {
				throws(classOf[Exception])(evaluate(Div(Num(n), Num(n))))
			}
		}
	}

	it should "be stringified as (a * b)" in {
		check((a: Int, b: Int) =>
			toMath(Mul(Num(a), Num(b))) == s"($a * $b)")		
	}
}