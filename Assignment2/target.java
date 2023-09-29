/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class target extends GraphicsProgram {
	public void run() {
		draw3Circles();
	}

	private void draw3Circles() {
		/*
		 * when i = 0 it draws a big red(because 0 is even) circle with radius equal to
		 * 72 cm when i=1 it draws medium white(because 1 is odd) circle with radius
		 * equal to 72 - 1*72/2.54*0,89 when i =2 it draws a little red(because 2 is
		 * even) circle with radius equal to 72 - 2*72/2.54*0.89
		 */
		for (int i = 0; i < 3; i++) {
			double Cx = getWidth() / 2.0;
			double Cy = getHeight() / 2.0;
			double r = pixelInCm * 2.54 - i * pixelInCm * 0.89;
			GOval circle = new GOval(Cx - r, Cy - r, 2 * r, 2 * r);
			add(circle);
			circle.setFilled(true);
			if (isEven(i)) {
				circle.setColor(Color.RED);
			} else {
				circle.setColor(Color.WHITE);
			}
		}
	}

	double pixelInCm = 72.00 / 2.54;

	private boolean isEven(int a) {
		// we use this type of boolean to indicate which one should be colored red
		if (a % 2 == 0) {
			return true;
		}
		return false;
	}
}
