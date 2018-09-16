package cs162.tutorials.calculator

import org.scalatest._
import org.scalatest.prop._
import org.scalacheck._
import scala.util.{ Try, Success }

class EvaluationSpec extends FlatSpec with Matchers with Checkers {
	import Calculator._

	"Numbers" should "evaluate to integers" in {
		check((a: Int) =>  
			evaluate(Num(a)) == a)
	}

	"Sum(a, b)" should "evaluate to a + b" in {
		check((a: Int, b: Int) =>
			evaluate(Add(Num(a), Num(b))) == a + b)
	}

	it should "be commutative -- a + b == b + a" in {
		check((a: Int, b: Int) => 
			evaluate(Add(Num(a), Num(b))) ==
				evaluate(Add(Num(b), Num(a))))
	}

	it should "be associative -- (a + b) + c == a + (b + c)" in {
		check((a: Int, b: Int, c: Int) => 
			evaluate(Add(Add(Num(a), Num(b)), Num(c))) ==
				evaluate(Add(Num(a), Add(Num(b), Num(c)))))		
	}

	"Sub(a, b)" should "evaluate to a - b" in {
		check((a: Int, b: Int) =>
			evaluate(Sub(Num(a), Num(b))) == a - b)
	}

	it should "evaluate to 0 if a == b" in {
		check((n: Int) => 
			evaluate(Sub(Num(n), Num(n))) == 0)
	}

	it should "evaluate to the same as the sum of a + (-b)" in {
		check((a: Int, b: Int) =>
			evaluate(Sub(Num(a), Num(b))) ==
				evaluate(Add(Num(a), Num(-b))))
	}

	"Mul(a, b)" should "evaluate to a * b" in {
		check((a: Int, b: Int) =>
			evaluate(Mul(Num(a), Num(b))) == a * b)		
	}

	it should "be commutative -- ab == ba" in {
		check((a: Int, b: Int) => 
			evaluate(Mul(Num(a), Num(b))) ==
				evaluate(Mul(Num(b), Num(a))))
	}

	it should "be associative -- (ab)c == a(bc)" in {
		check((a: Int, b: Int, c: Int) => 
			evaluate(Mul(Mul(Num(a), Num(b)), Num(c))) ==
				evaluate(Mul(Num(a), Mul(Num(b), Num(c)))))		
	}

	it should "evaluate to 0 if a or b is 0" in {
		check((a: Int) =>
			evaluate(Mul(Num(a), Num(0))) == 0)
	}

	it should "evaluate to an operand if the other is 1" in {
		check((a: Int) =>
			evaluate(Mul(Num(a), Num(1))) == a)
	}

	it should "distribute addition -- a(b + c) = ab + ac" in {
		check((a: Int, b: Int, c: Int) =>
			evaluate(Mul(Num(a), Add(Num(b), Num(c)))) ==
				evaluate(Add(Mul(Num(a), Num(b)), Mul(Num(a), Num(c)))))
	}
}
