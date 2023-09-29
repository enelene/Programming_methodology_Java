/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {

	public void run() {
		/*
		 * variable times is brought to count how many moves we need to reach 1 at first
		 * it equals to 0 but we add one to it for every move we make
		 */
		int times = 0;
		int n = readInt("Enter N: ");
		if (n >= 1) {
			while (n != 1) {

				if (isEven(n)) {
					// this happens when integer is even

					println(n + " is even so i take half: " + n / 2);
					n = n / 2;
					times++;
				} else {
					// this happens when integer is odd
					println(n + " is odd so i make 3n+1: " + (3 * n + 1));
					n = 3 * n + 1;
					times++;

				}
			}
			println("The process took " + times + " to reach 1");
		} else {
			// this program does not work for 0 or negative numbers
			println("n is either negative or 0, please enter positive number. ");
			// run method restarts everything
			run();
		}

	}

	private boolean isEven(int a) {
		// this boolean is true if integer is even and false if integer is odd
		if (a % 2 == 0) {
			return true;
		}
		return false;
	}
}
