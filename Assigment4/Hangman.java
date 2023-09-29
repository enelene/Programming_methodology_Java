/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {
	private HangmanLexicon Words;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int guessCounter = 8;
	private String word = pickWord();
	private String HiddenWord = showHiddenWord();
	private String incorrectGuesses = "";
	private HangmanCanvas canvas;

	/** adds canvas on the right side and welcome text on the left */
	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
		
		println("welcome To Hangman!");
		notifyPlayer();

	}

	/** after every entered letter we should notify player */
	private void notifyPlayer() {
		println("The word now looks like this: " + HiddenWord);
		println("You have " + guessCounter + " guesses left.");
		canvas.displayWord(HiddenWord);
	}

	/** pick random word from lexicon */
	private String pickWord() {

		Words = new HangmanLexicon();
		int randomWord = rgen.nextInt(0, (Words.getWordCount() - 1));
		String pickedWord = Words.getWord(randomWord);
		return pickedWord;
	}

	/** show as much "-" as letters are in the word */
	private String showHiddenWord() {
		String result = "";
		for (int i = 0; i < word.length(); i++) {
			result += "-";

		}
		return result;
	}

	/** draws scaffold and shows hidden word on canvas and then we can start game */
	public void run() {

		canvas.reset();
		canvas.displayWord(HiddenWord);

		startGame();

	}

	/**
	 * player has 8 guesses, after that game is over and player has lost while
	 * player still has guesses we should check if the symbol is really the letter
	 * and nothing else.
	 * 
	 * Also, if player guessed the word, than hidden word is equal to the word that
	 * program chose from lexicon and player wins.
	 */
	private void startGame() {

		while (guessCounter > 0) {

			getAndCheckSymbol();

			if (HiddenWord.equals(word)) {
				println("you guessed the word " + word);
				println("You won");
				break;
			}

		}
		if (guessCounter == 0) {
			println("You're completely hung.");
			println("Yhe word was: " + word);
			println("You lose.");

		}

	}

	/**
	 * this method checks if symbol is really a letter, otherwise we notify player
	 * that the symbol they entered is invalid and give them another try
	 */
	private void getAndCheckSymbol() {
		String str = readLine("Your Guess: ");

		/**
		 * string length should be more than 0 , otherwise if player clicks enter
		 * without writing anything, program will crash
		 */
		if (str.length() > 0) {
			char ch = str.charAt(0);
			if (Character.isLetter(ch) && str.length() == 1) {
				ch = Character.toUpperCase(ch);
				checkInTheWord(ch);
			} else
				println("invalid symbol, try again");
		} else
			println("enter something before clicking enter.");

	}

	/**
	 * after we are sure that symbol that player entered was letter, we check that
	 * letter in the word and notify player their result
	 */
	private void checkInTheWord(char ch) {

		/** this happens when player could not guess the word */

		if (word.indexOf(ch) == -1) {

			incorrectGuesses += ch;
			canvas.noteIncorrectGuess(incorrectGuesses);
			guessCounter--;

		}

		for (int i = 0; i < word.length(); i++) {

			/** if players guess is correct we add that letter on the hidden word */

			if (ch == word.charAt(i)) {

				String preffix = HiddenWord.substring(0, i);
				String suffix = HiddenWord.substring(i + 1);
				HiddenWord = preffix + ch + suffix;

			}
		}
		notifyResult(ch);
	}

	/** notifies player if their guess was right or wrong */

	private void notifyResult(char ch) {
		if (word.indexOf(ch) == -1) {
			println("There are no " + ch + "'s in the word.");
		} else {
			println("THE GUESS IS CORRECT.");
		}
		notifyPlayer();
	}
}
