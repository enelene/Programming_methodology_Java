/*
 * File: FindRange.java
 * Name:  Elene Gabeskiria
 * Section Leader:  
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	// SENTINEL, in this case 0, is special symbol to let us know that entering
	// integers is finished.
	private static final int SENTINEL = 0;

	public void run() {

		findMinAndMax();
	}

	private void findMinAndMax() {

		int n = readInt("Please  enter integer: ");
		if (n == SENTINEL) {
			// in that case you enter 0 as first integer.
			println("please enter another integer.");
			return;
		}
		/*
		 * we brought min and max variables that at first equal to n but when we enter
		 * another and another integers min and max changes step by step.
		 */
		int max = n;
		int min = n;
		while (true) {
			// enter as much integer as you want until you enter 0.
			n = readInt("enter integer: ");
			if (n == SENTINEL) {
				break;
			}
			// this is how min and max are changing in process.
			if (n > max) {
				max = n;

			} else if (n < min) {
				min = n;
			}
		}
		// to show you result we print min and max.
		println("max:" + max);
		println("min:" + min);

	}
}
