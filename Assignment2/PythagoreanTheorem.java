/* File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		println("Enter values to compute Pythagorean theorem.");
		double a = readInt("a:"); // first value
		double b = readInt("b:"); // second value
		if (a > 0 && b > 0) {
			double c = Math.sqrt(a * a + b * b); // this counts square root from (a^2 + b^2)
			println("c:" + c); // prints result

		} else {
			println("leg of triangle can not be equal to 0 or be negative ");
		}
	}
}
