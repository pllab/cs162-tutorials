
package edu.ucsb.cs.cs162.tuts.functional

// A number of list problems.
object Factorial {
  import scala.annotation.tailrec
  import scala.math.BigInt

  // Find the factorial of `n`.
  // The `apply` method can be called by using the `()` operator,
  //  as if `Factorial` was a function itself.
  // Example: Factorial(5) is actually Factorial.apply(5)
  def apply(n: BigInt): BigInt = {

    // Tail-recursively iterates through the factorial calculation,
    //  saving the intermediate results in the accumulator `acc`.

    // WARNING: this method **has** to be tail-recursive.    
    // Uncomment this when you start working on this problem.

    // UNCOMMENT ME: 
    /* @tailrec */
    def iter(count: BigInt, acc: BigInt): BigInt = ???

    iter(n, 1)
  }
}