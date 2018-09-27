
package edu.ucsb.cs.cs162.tuts.functional

// A number of list problems.
object ListProblems {

	// Sums all the odd numbers in the list.
	def sumOdd(list: List[Int]): Int = ???

	// Sums the two lists pairwise. 
	// Requires that the two lists are of the same length.
	// Examples: (1, 2, 3) + (4, 5, 6) = (5, 7, 9) 
	// Hint: look at List.zip and List.map in the Scala list documentation
	def sumPairs(left: List[Int], right: List[Int]): List[Int] = ???

	// Gets the penultimate element of a list safely, returning 
	//  `None` if there's no such element.
	// Examples: (1, 2, 3) -- penultimate --> 2
	// Hint: List.foldLeft can be useful here.
	def safePenultimate(list: List[Int]): Option[Int] = ???
}