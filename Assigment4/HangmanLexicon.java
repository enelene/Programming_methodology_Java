/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class HangmanLexicon {

	private ArrayList<String> hangmanLexicon = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return hangmanLexicon.size();
	}

	/** Returns the word at the specified index. */

	public String getWord(int index) {
		String words = hangmanLexicon.get(index);
		return words;
	}

	public HangmanLexicon() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("HangmanLexicon.txt"));

			while (true) {
				String line = reader.readLine();
				if (line == null)
					break;
				hangmanLexicon.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
