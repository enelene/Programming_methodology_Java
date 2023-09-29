import acm.program.*;
import acm.util.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	private Map<String, NameSurferEntry> namesData;

	public NameSurferDataBase(String fileName) {
		namesData = new HashMap<String, NameSurferEntry>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			while (true) {
				String line = br.readLine();
				if (line == null) {
					break;
				}
				NameSurferEntry entry = new NameSurferEntry(line.toLowerCase());
				String name = entry.getName();
				namesData.put(name, entry);
			}

			br.close();
		}

		catch (IOException ex) {
			throw new ErrorException(ex);
		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	// searches name in hashMap
	public NameSurferEntry findEntry(String name) {
		if (namesData.containsKey(name)) {
			NameSurferEntry current = namesData.get(name);
			return current;
		} else {
			return null;
		}
	}

}
