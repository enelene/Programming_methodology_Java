import acm.graphics.*;
import acm.program.*;

public class programHierarchy extends GraphicsProgram {

	private static final int RECTANGLE_WIDTH = 200;
	private static final int RECTANGLE_HEIGHT = 50;
	private static final int DISTANCE = 20; // This is distance between rectangles

	public void run() {

		Program();
		drawThreeRectangles();
		writeConsoleProgram();
		writeDialogProgram();
		writeGraphicsProgram();
		threeLine();
	}

	private void Program() {
		// these are coordinates of first rectangle where "program" is written.
		double x = (getWidth() - RECTANGLE_WIDTH) / 2.0;
		double y = (getHeight() - RECTANGLE_HEIGHT * 3) / 2.0;

		GRect Rectangle = new GRect(x, y, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
		add(Rectangle);

		GLabel label = new GLabel("Program");
		add(label, x + (RECTANGLE_WIDTH - label.getWidth()) / 2.0, y + (RECTANGLE_HEIGHT + label.getAscent()) / 2.0);
	}

	private void writeConsoleProgram() {
		// this is coordinates of rectangle where the "ConsoleProgram" is written.
		double x = (getWidth() - RECTANGLE_WIDTH) / 2.0;
		double y = (getHeight() + RECTANGLE_HEIGHT) / 2.0;

		GLabel label = new GLabel("ConsoleProgram");
		add(label, x + (RECTANGLE_WIDTH - label.getWidth()) / 2.0, y + RECTANGLE_HEIGHT / 2 + label.getAscent() / 2.0);
	}

	private void writeGraphicsProgram() {
		// this is coordinates of rectangle where the "GraphicsProgram" is written.

		double x = (getWidth() - RECTANGLE_WIDTH * 3) / 2.0 - DISTANCE;
		double y = (getHeight() + RECTANGLE_HEIGHT) / 2.0;

		GLabel label = new GLabel("GraphicsProgram");
		add(label, x + RECTANGLE_WIDTH / 2 - label.getWidth() / 2, y + RECTANGLE_HEIGHT / 2 + label.getAscent() / 2.0);

	}

	private void writeDialogProgram() {
		// this is coordinates of rectangle where the "DialogProgram" is written.
		double x = (getWidth() + RECTANGLE_WIDTH) / 2.0 + DISTANCE;
		double y = (getHeight() + RECTANGLE_HEIGHT) / 2.0;

		GLabel label = new GLabel("DialogProgram");
		add(label, x + RECTANGLE_WIDTH / 2 - label.getWidth() / 2, y + RECTANGLE_HEIGHT / 2 + label.getAscent() / 2.0);

	}

	private void drawThreeRectangles() {
		// it draws rectangles where dialog,console and graphics program are written.
		// difference between them is
		// is just DISTANCE in X coordinate.
		for (int i = 0; i < 3; i++) {
			double x = (getWidth() - RECTANGLE_WIDTH * 3) / 2.0 - DISTANCE + (RECTANGLE_WIDTH + DISTANCE) * i;
			double y = (getHeight() + RECTANGLE_HEIGHT) / 2.0;
			GRect Rectangle = new GRect(x, y, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
			add(Rectangle);
		}
	}

	private void threeLine() {
		// this draws three lines, which have the same starting point and end in
		// different places
		// which are (RECTANGLE_WIDTH + DISTANCE) * i away from each other
		for (int i = 0; i < 3; i++) {
			double x1 = getWidth() / 2.0;
			double y1 = (getHeight() - RECTANGLE_HEIGHT) / 2.0;
			double x2 = getWidth() / 2.0 - RECTANGLE_WIDTH - DISTANCE + (RECTANGLE_WIDTH + DISTANCE) * i;
			double y2 = y1 + RECTANGLE_HEIGHT;
			add(new GLine(x1, y1, x2, y2));
		}
	}

}
