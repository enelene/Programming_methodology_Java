/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private RandomGenerator rgen = RandomGenerator.getInstance();
	/** velocity of ball */
	private double vx;
	private double vy;

	private static final int DELAY = 10;
	/** number of bricks */
	private int BrickCounter = NBRICK_ROWS * NBRICKS_PER_ROW;

	/** initialization process */
	public void init() {
		drawBricks();
		drawPaddle();
		drawBall();
		addMouseListeners();
	}

	/** playing game */
	public void run() {
		
		for (int i = 0; i < NTURNS; i++) {
			waitForClick();
			startGame();
		}
		/** after n turns, if we still have bricks than game is over */
		if (BrickCounter > 0) {
			removeAll();
			printGameOver();
		}

	}

	private GRect BRICK;

	/** draws (i+1)(j+1) bricks */
	private void drawBricks() {
		for (int i = 0; i < NBRICK_ROWS; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				double x = (APPLICATION_WIDTH - NBRICKS_PER_ROW * BRICK_WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 4.0
						+ j * (BRICK_WIDTH + BRICK_SEP);
				double y = BRICK_Y_OFFSET + i * (BRICK_HEIGHT + BRICK_SEP);
				BRICK = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(BRICK);
				BRICK.setFilled(true);
				if (i % 10 == 1 || i % 10 == 0)
					BRICK.setColor(Color.RED);
				if (i % 10 == 2 || i % 10 == 3)
					BRICK.setColor(Color.ORANGE);
				if (i % 10 == 4 || i % 10 == 5)
					BRICK.setColor(Color.YELLOW);
				if (i % 10 == 6 || i % 10 == 7)
					BRICK.setColor(Color.GREEN);
				if (i % 10 == 8 || i % 10 == 9)
					BRICK.setColor(Color.CYAN);
			}
		}
	}

	private GRect PADDLE;

	private void drawPaddle() {
		double x = (APPLICATION_WIDTH - PADDLE_WIDTH) / 2.0;
		double y = (getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
		PADDLE = new GRect(x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
		add(PADDLE);
		PADDLE.setFilled(true);

	}

	/** paddle moves between walls, and only x coordinate changes */
	public void mouseMoved(MouseEvent e) {
		double y = (getHeight() - PADDLE_HEIGHT - PADDLE_Y_OFFSET);
		if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
			PADDLE.setLocation(e.getX() - PADDLE_WIDTH / 2, y);
		}
	}

	private GOval Ball;

	private void drawBall() {
		Ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		Ball.setFilled(true);
		add(Ball, APPLICATION_WIDTH / 2 - BALL_RADIUS, getHeight() / 2);
		// add (Ball, 1, 1);
	}

	private void startGame() {
		vx = rgen.nextDouble(1.0, 3.0);
		vy = 3.0;
		if (rgen.nextBoolean(0.5))
			vx *= -1;

		while (true) {
			makeMovement();
			checkCollisions();
			/** while ball touches the wall below it means that you lost one life */
			if (Ball.getY() >= getHeight() - 2 * BALL_RADIUS) {
				remove(Ball);
				drawBall();
				break;

			}
			/** if all bricks are removed before you lose 3 lives, it means that you won */
			if (BrickCounter == 0) {
				removeAll();
				printYouWon();
				break;
			}

		}

	}

	/** checks if the corners of square, where ball is drawn, touched anything */
	private GObject getCollidingObject() {
		if (getElementAt(Ball.getX(), Ball.getY()) != null) {
			return getElementAt(Ball.getX(), Ball.getY());
		}
		if (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY()) != null) {
			return getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY());
		}
		if (getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(Ball.getX() + 2 * BALL_RADIUS, Ball.getY() + 2 * BALL_RADIUS);
		}
		if (getElementAt(Ball.getX(), Ball.getY() + 2 * BALL_RADIUS) != null) {
			return getElementAt(Ball.getX(), Ball.getY() + 2 * BALL_RADIUS);
		}
		/** if corners have not touched anything than it returns null */
		return null;
	}

	private void makeMovement() {
		Ball.move(vx, vy);
		pause(DELAY);

	}

	private void checkCollisions() {

		getCollidingObject();
		checkWalls();
		checkPaddleAndBricks();

	}

	/** ball should only move inside application */
	private void checkWalls() {

		if (Ball.getX() <= 0 || Ball.getX() >= getWidth() - 2 * BALL_RADIUS) {
			vx *= -1;
		}
		if (Ball.getY() <= 0) {
			vy *= -1;
		}

	}

	/**
	 * only things ball can touch are ball and paddle. if ball touched paddle than
	 * it will just change its velocity else , its brick so we will remove exactly
	 * that brick and then change the velocity of ball
	 */
	private void checkPaddleAndBricks() {

		GObject collider = getCollidingObject();
		if (collider == PADDLE) {
			/** for sticky situation */
			if (Ball.getY() < PADDLE.getY() + 4 - 2 * BALL_RADIUS) {
				vy *= -1;
			}
		} else if (collider != null) {
			remove(collider);
			vy *= -1;
			BrickCounter--;
		}
	}

	/** notifies that game has finished and tells your result */
	private void printYouWon() {

		GLabel YOUWON = new GLabel("YOU WON !!!");

		double x = (APPLICATION_WIDTH - YOUWON.getWidth()) / 2;
		double y = (APPLICATION_HEIGHT - YOUWON.getHeight()) / 2;

		YOUWON.setColor(Color.GREEN);
		add(YOUWON, x, y);

	}

	private void printGameOver() {

		GLabel GAMEOVER = new GLabel("GAME OVER ");
		double x = (APPLICATION_WIDTH - GAMEOVER.getWidth()) / 2;
		double y = (APPLICATION_HEIGHT - GAMEOVER.getHeight()) / 2;
		GAMEOVER.setColor(Color.RED);
		add(GAMEOVER, x, y);
	}

}
