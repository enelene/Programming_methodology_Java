/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;

import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers giving
	 * the rank of that name for each decade.
	 */
	
	private String[] split;

	public NameSurferEntry(String line) {

		split = line.split(" ");

	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */

	// in line , first word is always name.
	public String getName() {

		String name = split[0];
		return name;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The decade
	 * value is an integer indicating how many decades have passed since the first
	 * year in the database, which is given by the constant START_DECADE. If a name
	 * does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {

		if (decade >= 0 && decade <= NDECADES) {
			int rank = Integer.parseInt(split[decade + 1]); // because split[0] stands for name
			return rank;
		} else
			return 0;
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a NameSurferEntry.
	 */
	public String toString() {

		int[] ranks = new int[NDECADES];
		for (int i = 1; i < split.length; i++) {
			ranks[i - 1] = Integer.parseInt(split[i]);
		}

		return (getName() + Arrays.toString(ranks));

	}
}
