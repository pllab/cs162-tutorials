
package cs162.tutorials.calculator

// An arithmetic expression
sealed trait Expr

// Basic numeric expression
case class Num(value: Int) extends Expr

// In the following definitions, `lhs` and `rhs` refer 
//   to "left-hand" and "right-hand" sides, respectively.

// Addition expression
case class Add(lhs: Expr, rhs: Expr) extends Expr

// Subtraction expression
case class Sub(lhs: Expr, rhs: Expr) extends Expr

// Multiplication expression
case class Mul(lhs: Expr, rhs: Expr) extends Expr

// Division expression
case class Div(lhs: Expr, rhs: Expr) extends Expr

// An evaluator for arithmetic calculations
object Calculator {

  // Evaluates an expression to an integer value.
  def evaluate(tree: Expr): Int = tree match {
    case Num(value) => value
    case _ => throw new Exception("Implement the rest here.")
  }

  def toMath(tree: Expr): String = tree match {
    case Num(value) => s"$value"
    case _ => throw new Exception("Implement the rest here.")
  }
}

