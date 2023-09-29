/* File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Font;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {

		drawScaffold();
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String HiddenWord) {
		double x = getWidth() / 2 - BEAM_LENGTH;
		double y = getHeight() / 2 + SCAFFOLD_HEIGHT / 2;
		GLabel word = new GLabel(HiddenWord, x, y);
		word.setFont("Times New Roman-36");

		/**
		 * removes old label of hidden word and adds new one after guessing another
		 * letter.
		 */

		if (getElementAt(x, y) != null) {
			remove(getElementAt(x, y));
		}

		add(word);

	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(String incorrectGuesses) {

		double x = getWidth() / 2 - BEAM_LENGTH;
		double y = getHeight() / 2 + SCAFFOLD_HEIGHT / 2 + HEAD_RADIUS;
		GLabel incorrectGuesss = new GLabel(incorrectGuesses, x, y);
		add(incorrectGuesss);

		if (incorrectGuesses.length() == 1)
			drawHead();

		if (incorrectGuesses.length() == 2)
			drawBody();

		if (incorrectGuesses.length() == 3)
			drawLeftArm();

		if (incorrectGuesses.length() == 4)
			drawRightArm();

		if (incorrectGuesses.length() == 5)
			drawLeftLeg();

		if (incorrectGuesses.length() == 6)
			drawRightLeg();

		if (incorrectGuesses.length() == 7)
			drawLeftFoot();

		if (incorrectGuesses.length() == 8)
			drawRightFoot();

	}

	/** drawing scaffold, body, head, arms, legs and feet */

	private void drawScaffold() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2 - BEAM_LENGTH;
		double y = YOffSet;
		double y1 = y + SCAFFOLD_HEIGHT;
		GLine Scaffold = new GLine(x, y, x, y1);
		add(Scaffold);

		double xBeam = getWidth() / 2 - BEAM_LENGTH;
		double x1Beam = getWidth() / 2;
		GLine Beam = new GLine(xBeam, y, x1Beam, y);
		add(Beam);

		double xRope = getWidth() / 2;
		double yRope = y + ROPE_LENGTH;
		GLine Rope = new GLine(xRope, y, xRope, yRope);
		add(Rope);
	}

	private void drawHead() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2 - HEAD_RADIUS;
		double y = YOffSet + ROPE_LENGTH;
		GOval Head = new GOval(x, y, 2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(Head);
	}

	private void drawBody() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS;
		double y1 = y + BODY_LENGTH;
		GLine Body = new GLine(x, y, x, y1);
		add(Body);
	}

	private void drawLeftArm() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2 - UPPER_ARM_LENGTH;
		double x1 = getWidth() / 2;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double y1 = y + LOWER_ARM_LENGTH;
		GLine upperArm = new GLine(x, y, x1, y);
		GLine lowerArm = new GLine(x, y, x, y1);
		add(upperArm);
		add(lowerArm);

	}

	private void drawRightArm() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2;
		double x1 = getWidth() / 2 + UPPER_ARM_LENGTH;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + ARM_OFFSET_FROM_HEAD;
		double y1 = y + LOWER_ARM_LENGTH;
		GLine upperArm = new GLine(x, y, x1, y);
		add(upperArm);
		GLine lowerArm = new GLine(x1, y, x1, y1);
		add(lowerArm);
	}

	private void drawLeftLeg() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x1 = getWidth() / 2 - HIP_WIDTH;
		double x = getWidth() / 2;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		double y1 = y + LEG_LENGTH;
		GLine leftHip = new GLine(x1, y, x, y);
		GLine leftLeg = new GLine(x1, y, x1, y1);
		add(leftLeg);
		add(leftHip);
	}

	private void drawRightLeg() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2;
		double x1 = getWidth() / 2 + HIP_WIDTH;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH;
		double y1 = y + LEG_LENGTH;
		GLine rightHip = new GLine(x, y, x1, y);
		GLine rightLeg = new GLine(x1, y, x1, y1);
		add(rightLeg);
		add(rightHip);
	}

	private void drawLeftFoot() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2 - HIP_WIDTH;
		double x1 = x - FOOT_LENGTH;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine leftFoot = new GLine(x1, y, x, y);
		add(leftFoot);
	}

	private void drawRightFoot() {
		double YOffSet = (getHeight() - SCAFFOLD_HEIGHT) / 2 - HEAD_RADIUS;

		double x = getWidth() / 2 + HIP_WIDTH;
		double x1 = x + FOOT_LENGTH;
		double y = YOffSet + ROPE_LENGTH + 2 * HEAD_RADIUS + BODY_LENGTH + LEG_LENGTH;
		GLine rightFoot = new GLine(x, y, x1, y);
		add(rightFoot);
	}

	/* Constants for the simple version of the picture (in pixels) */

	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
