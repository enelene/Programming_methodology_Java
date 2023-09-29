/* File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	private static final int ROWS = 14;

	public void run() {

		drawPyramid();
	}

	private void drawPyramid() {
		/*
		 * i presents rows and j presents how many bricks will be in that row. if i =0
		 * than j will be 14 and that is bottom row if i = 13 than j will be 1 and thats
		 * the top of the pyramid with only one brick and etc. x coordinate is chosen
		 * like the way it is to have pyramid in center y coordinate just goes above
		 * with one bricks height to start filling another row of pyramid.
		 */

		for (int i = 0; i < ROWS; i++) {
			for (int j = BRICKS_IN_BASE - i; j >= 1; j--) {
				double x = ((getWidth() - (BRICKS_IN_BASE - i) * BRICK_WIDTH) / 2.0 + BRICK_WIDTH * (j - 1));
				double y = getHeight() - (i + 1) * BRICK_HEIGHT;
				GRect BRICK = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(BRICK);

			}
		}
	}
}
