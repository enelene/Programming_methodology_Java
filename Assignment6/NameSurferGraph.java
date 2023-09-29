/*
   * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */

	public NameSurferGraph() {
		addBackground();
		addComponentListener(this);
		// You fill in the rest //
	}

	// draws only the Background with margins and decades
	private void addBackground() {

		// horizontal two line
		int xH1 = 0;
		int xH2 = getWidth();
		int yH1 = GRAPH_MARGIN_SIZE;
		int yH2 = getHeight() - yH1;
		add(new GLine(xH1, yH1, xH2, yH1));
		add(new GLine(xH1, yH2, xH2, yH2));

		// 11 vertical line and label of decades
		for (int i = 0; i < NDECADES; i++) {
			int distance = getWidth() / NDECADES;
			int dec = START_DECADE + (i * 10);

			int x = i * distance;
			int xLabel = 4 + i * distance;
			int y = 0, y1 = getHeight();

			add(new GLine(x, y, x, y1));

			GLabel decade = new GLabel("" + dec + "");
			int yLabel = (int) getHeight() - (int) decade.getDescent();
			add(decade, xLabel, yLabel);

		}

	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 * 
	 * entries are stored in the arrayList "entries" and after calling this method,
	 * arrayList is empty
	 */
	public void clear() {
		entries.removeAll(entries);
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is drawn by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		if (!entries.contains(entry)) {
			entries.add(entry);
		}
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		addBackground();
		drawGraph(entries);

	}

	// this method actually draws graph

	private void drawGraph(ArrayList<NameSurferEntry> entries) {

		int distance = getWidth() / NDECADES;
		int diagram = (getHeight() - GRAPH_MARGIN_SIZE * 2);

		for (int i = 0; i < entries.size(); i++) {

			// get info about this entry and write name on the diagram

			NameSurferEntry thisEntry = entries.get(i);
			String updatedName = writeName(thisEntry);

			/**
			 * draws Diagram with lines and labels, y coordinate depends on the rank in that
			 * decade
			 */
			for (int j = 0; j < NDECADES - 1; j++) {
				String labelInfo = updatedName + thisEntry.getRank(j);
				String lastLabelInfo = updatedName + thisEntry.getRank(NDECADES - 1);

				int xOffSet = j * distance;
				int xEndOfDecade = xOffSet + distance;

				int y = GRAPH_MARGIN_SIZE + diagram * thisEntry.getRank(j) / MAX_RANK;
				int y2 = GRAPH_MARGIN_SIZE + diagram * thisEntry.getRank(j + 1) / MAX_RANK;
				// for last label
				int y3 = GRAPH_MARGIN_SIZE + diagram * thisEntry.getRank(NDECADES - 1) / MAX_RANK;

				/**
				 * in case that this name was not in top 1000 names that decade, y should be the
				 * lowest and label should say * and not 0
				 */
				if (thisEntry.getRank(j) == 0) {
					y = getHeight() - GRAPH_MARGIN_SIZE;
					labelInfo = updatedName + "*";
				}
				if (thisEntry.getRank(NDECADES - 1) == 0) {
					y3 = getHeight() - GRAPH_MARGIN_SIZE;
					lastLabelInfo = updatedName + "*";
				}
				if (thisEntry.getRank(j + 1) == 0) {
					y2 = getHeight() - GRAPH_MARGIN_SIZE;
				}

				GLine line = new GLine(xOffSet, y, xEndOfDecade, y2);

				GLabel label = new GLabel(labelInfo);
				int yLabel = y - (int) label.getDescent();

				// j < NDECADES -1 does not consider last label, so i add that by myself

				GLabel lastLabel = new GLabel(lastLabelInfo);
				int yLabel1 = y3 - (int) lastLabel.getDescent();

				SetColor(i, line, label, lastLabel);
				add(lastLabel, (NDECADES - 1) * distance + 3, yLabel1);
				add(label, xOffSet + 3, yLabel);
				add(line);

			}

		}

	}

	// name should start with upper case

	private String writeName(NameSurferEntry thisEntry) {
		String name = thisEntry.getName();
		String updatedName = name.substring(0, 1).toUpperCase() + name.substring(1);
		return updatedName;
	}


	 // after 4 name entries , colors will repeat
	private void SetColor(int i, GLine line, GLabel label, GLabel lastLabel) {


		if (i % 4 == 0) {
			line.setColor(Color.BLACK);
			label.setColor(Color.BLACK);
			lastLabel.setColor(Color.BLACK);
		} else if (i % 4 == 1) {
			line.setColor(Color.RED);
			label.setColor(Color.RED);
			lastLabel.setColor(Color.RED);
		} else if (i % 4 == 2) {
			line.setColor(Color.BLUE);
			label.setColor(Color.BLUE);
			lastLabel.setColor(Color.BLUE);
		} else if (i % 4 == 3) {
			line.setColor(Color.MAGENTA);
			label.setColor(Color.MAGENTA);
			lastLabel.setColor(Color.MAGENTA);
		}
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private ArrayList<NameSurferEntry> entries = new ArrayList<>();
}
