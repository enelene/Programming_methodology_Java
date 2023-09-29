/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program wigetName implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	private JTextField personName;
	private JButton graph;
	private JButton clear;
	private NameSurferGraph gr;
	private NameSurferDataBase dataBase = new NameSurferDataBase(NAMES_DATA_FILE);
	private NameSurferEntry current;

	public void init() {
		// adds canvas
		gr = new NameSurferGraph();
		add(gr);

// adds label where is written name
		JLabel name = new JLabel("Name");
		add(name, SOUTH);
// adds textField
		personName = new JTextField(25);
		add(personName, SOUTH);
		personName.addActionListener(this);
// adds buttons graph and clear 
		graph = new JButton("Graph");
		add(graph, SOUTH);

		clear = new JButton("Clear");
		add(clear, SOUTH);

		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == clear) {
			gr.clear();
			gr.update();
		} else {

			// if its not clear button, then its graph or someone clicked enter and both
			// make same thing.

			String getName = personName.getText();
			getName = getName.toLowerCase();
			current = dataBase.findEntry(getName);
			
			// if we have data about that name (nameEntry)
			
			if (current != null) {
				gr.addEntry(current);
				gr.update();
			}
			personName.setText("");

		}

	}
}
