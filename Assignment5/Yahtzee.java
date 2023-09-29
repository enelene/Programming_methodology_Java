/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	/**
	 * score matrix is matrix of players and their own categories , considering the
	 * fact that players start from 1 , in yahtzeeConstants first category is equal
	 * to one but in matrix index 0 also has a value, most of the time i use matrix
	 * i subtract player and categories (-1).
	 */

	private void playGame() {
		scoreMatrix = new int[nPlayers][N_CATEGORIES];
		makeScoringCategoriesNegative(scoreMatrix);
		startGame();

	}

	/**
	 * to check if category is already occupied, i check if there is written any
	 * score , in matrix, everything is 0 at first, so checking if category's point
	 * = 0 should be a good solution, but player occupy that category by scoring 0
	 * too, so we need something that player cannot score, for example negative
	 * numbers like -1 and fill with it only scoring categories.
	 */

	private void makeScoringCategoriesNegative(int[][] scoreMatrix) {
		for (int i = 0; i < nPlayers; i++) {
			Arrays.fill(scoreMatrix[i], -1);

			scoreMatrix[i][TOTAL - 1] = 0;
			scoreMatrix[i][UPPER_SCORE - 1] = 0;
			scoreMatrix[i][LOWER_SCORE - 1] = 0;
			scoreMatrix[i][UPPER_BONUS - 1] = 0;
		}

	}

	private void startGame() {
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			for (int player = 1; player <= nPlayers; player++) {

				rollFirstDice(player);
				rollSecondAndThirdDice(player);
				selectCategory(player);

			}
		}
		calculateResults();
		nameWinner();
	}

	private void rollFirstDice(int player) {

		display.printMessage(playerNames[player - 1] + "'s turn! Click" + " Roll Dice " + "button to roll the dice.");

		display.waitForPlayerToClickRoll(player);

		for (int i = 0; i < N_DICE; i++) {
			int diceCount = rgen.nextInt(1, 6);
			diceResults[i] = diceCount;
		}

		display.displayDice(diceResults);

	}

	/**
	 * after first roll, players can choose which dice they want to roll again, or
	 * if they do not , just click roll again button twice and diceResults will not
	 * change
	 * 
	 */
	private void rollSecondAndThirdDice(int player) {
		for (int k = 0; k < 2; k++) {
			display.printMessage("select the dice you wish to re-roll and click " + " roll again.");
			display.waitForPlayerToSelectDice();
			for (int i = 0; i < N_DICE; i++) {
				if (display.isDieSelected(i)) {
					int diceCount = rgen.nextInt(1, 6);
					diceResults[i] = diceCount;
				}

			}
			display.displayDice(diceResults);
		}
		display.printMessage("select a category for this roll.");
	}

	/**
	 * important point is to sort array of diceResult from lowest to biggest, it
	 * makes easier checking categories.
	 */
	private void selectCategory(int player) {
		Arrays.sort(diceResults);
		category = display.waitForPlayerToSelectCategory();
		while (categoryIsOcuppied(player, category)) {
			display.printMessage("its already used please select another one ");
			category = display.waitForPlayerToSelectCategory();
		}

		updatescoreMatrix(player);
		calculateScores(player);

	}

	private boolean categoryIsOcuppied(int player, int category) {
		if (scoreMatrix[player - 1][category - 1] == -1) {
			return false;
		}
		return true;
	}

	/**
	 * updates point in the category, which player has just chosen and also the
	 * category of total scores
	 */

	private void updatescoreMatrix(int player) {

		int score = score(player, category);
		scoreMatrix[player - 1][category - 1] = score;

		scoreMatrix[player - 1][TOTAL - 1] += score;
		int totalScore = scoreMatrix[player - 1][TOTAL - 1];

		display.updateScorecard(category, player, score);
		display.updateScorecard(TOTAL, player, totalScore);

	}

	/**
	 * for checking small and large straight i create arrayList of dice results, but
	 * add only the elements that are not the same. large and small straight are
	 * different from each other with only their length.
	 */
	private int checkSmallAndLargeStraight() {
		int score = 0;
		ArrayList<Integer> finalDiceResults = new ArrayList<>();
		int counter = 0;
		for (int i : diceResults) {
			if (finalDiceResults.indexOf(i) == -1) {
				finalDiceResults.add(i);
			}
		}
		for (int i = 0; i < finalDiceResults.size() - 1; i++) {
			if (finalDiceResults.get(i + 1) - finalDiceResults.get(i) == 1) {
				counter++;
			}
		}
		if (counter == 4 && category == LARGE_STRAIGHT) {
			score = 40;
		} else if (counter >= 3 && category == SMALL_STRAIGHT) {
			score = 30;
		}
		return score;

	}

	/**
	 * in yahtzeeConstants from one to six, categories are equal to their dice
	 * scores.
	 */
	private int checkUpperCategories() {
		int score = 0;
		for (int i = 0; i < N_DICE; i++) {
			if (diceResults[i] == category) {
				score += diceResults[i];
			}
		}
		return score;
	}

	/**
	 * in HashMap calculator keys are numbers from 1-6, because its every possbible
	 * dice result and their values present how many identical dice rolled
	 */
	private int checkMiddleCategories() {
		int score = 0;
		Map<Integer, Integer> calculator = new HashMap<>();
		for (int i = 1; i <= 6; i++) {
			calculator.put(i, 0);
		}
		for (int i : diceResults) {
			calculator.replace(i, calculator.get(i) + 1);
		}
		if ((category == THREE_OF_A_KIND && identical(calculator, 3))
				|| (category == FOUR_OF_A_KIND && identical(calculator, 4)) || category == CHANCE) {
			for (int i = 0; i < N_DICE; i++) {
				score += diceResults[i];
			}
		}
		return score;

	}

	private boolean identical(Map<Integer, Integer> calculator, int sameDice) {
		for (int i = 1; i <= calculator.size(); i++) {
			if (calculator.get(i) >= sameDice) {
				return true;
			}
		}
		return false;

	}

	/**
	 * here i create HashMap once again and check if map set contains enough
	 * identical dices to fit yahtzee's category or fullHouse
	 */
	private int checkFullHouseAndYahtzee() {
		int score = 0;
		Map<Integer, Integer> calculator = new HashMap<>();
		for (int i = 1; i <= N_DICE + 1; i++) {
			calculator.put(i, 0);
		}
		for (int j : diceResults) {
			calculator.replace(j, calculator.get(j) + 1);
		}
		for (int i : calculator.keySet()) {
			if (calculator.containsValue(5) && category == YAHTZEE) {
				score = 50;
			} else if (category == FULL_HOUSE && (calculator.containsValue(3) && calculator.containsValue(2))) {
				score = 25;
			}
		}

		return score;
	}

	/**
	 * after checking everything returns score for players in category which they
	 * have chosen
	 */

	private int score(int player, int category) {

		int RoundScore = 0;

		if (category <= SIXES) {
			RoundScore = checkUpperCategories();

		} else if ((category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND || category == CHANCE)) {
			RoundScore = checkMiddleCategories();

		} else if (category == FULL_HOUSE || category == YAHTZEE) {
			RoundScore = checkFullHouseAndYahtzee();

		} else if (category == SMALL_STRAIGHT || category == LARGE_STRAIGHT) {
			RoundScore = checkSmallAndLargeStraight();

		}

		return RoundScore;

	}

	/**
	 * here i calculate upper and lower score and also upper bonus, it will display
	 * after every player will fill their categories.
	 */
	private void calculateScores(int player) {
		int upperScore = 0;
		int lowerScore = 0;

		for (int i = ONES; i <= SIXES; i++) {
			upperScore += scoreMatrix[player - 1][i - 1];
		}
		for (int i = THREE_OF_A_KIND; i <= CHANCE; i++) {
			lowerScore += scoreMatrix[player - 1][i - 1];
		}

		scoreMatrix[player - 1][UPPER_SCORE - 1] = upperScore;
		scoreMatrix[player - 1][LOWER_SCORE - 1] = lowerScore;

	}

	private void calculateResults() {

		for (int i = 1; i <= nPlayers; i++) {
			display.updateScorecard(UPPER_SCORE, i, scoreMatrix[i - 1][UPPER_SCORE - 1]);
			display.updateScorecard(LOWER_SCORE, i, scoreMatrix[i - 1][LOWER_SCORE - 1]);

			// bonus is only written if upper score is equal or more than 63

			if (scoreMatrix[i - 1][UPPER_SCORE - 1] >= 63) {
				scoreMatrix[i - 1][UPPER_BONUS - 1] = 35;
			}
			// total score contains bonus too

			scoreMatrix[i - 1][TOTAL - 1] += scoreMatrix[i - 1][UPPER_BONUS - 1];
			display.updateScorecard(UPPER_BONUS, i, scoreMatrix[i - 1][UPPER_BONUS - 1]);
			display.updateScorecard(TOTAL, i, scoreMatrix[i - 1][TOTAL - 1]);

		}

	}

	// to find out which player the winner is

	private void nameWinner() {
		String winnersName = "";
		int winner = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (winner < scoreMatrix[i][TOTAL - 1]) {
				winner = scoreMatrix[i][TOTAL - 1];
				winnersName = playerNames[i];
			}
			display.printMessage(
					"congratulations, " + winnersName + ",you're the winner with a total score of " + winner);
		}
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] diceResults = new int[N_DICE];
	private int category;
	private int[][] scoreMatrix;
}
