
package edu.ucsb.cs.cs162.tuts.calc

// A mathematical expression.
sealed trait Expr

// A variable expression with a name.
final case class Var(name: String) extends Expr

// A number expression with a numeric value.
final case class Num(value: Double) extends Expr

// A unary operation expression (eg. -5 is UnOp("-", Num(5))).
final case class UnOp(op: String, value: Expr) extends Expr

// A binary operation expression (eg. 2+3 is BinOp("+", Num(2), Num(3))))
final case class BinOp(op: String, left: Expr, right: Expr) extends Expr

// The calculator object.
object Calculator {

  // Simplifies the head of the expression (should not simplify recursively!).  
  def simplifyHead(expr: Expr): Expr = ???
  
  // Evaluates the expression to a numeric value.
  def evaluate(expr: Expr): Double = ???
}
