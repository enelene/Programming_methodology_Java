/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	private JTextField nameField;
	private JButton addButton;
	private JButton delete;
	private JButton lookUp;
	private JTextField status;
	private JTextField picture;
	private JTextField friendField;
	private JButton changeStatus;
	private JButton changePicture;
	private JButton addFriend;
	private FacePamphletDatabase dataBase = new FacePamphletDatabase();
	private FacePamphletProfile current;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {

// creates iterators on north
		JLabel label = new JLabel("Name");
		add(label, NORTH);
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add(nameField, NORTH);
		addButton = new JButton("Add");
		add(addButton, NORTH);
		delete = new JButton("Delete");
		add(delete, NORTH);
		lookUp = new JButton("Lookup");
		add(lookUp, NORTH);

// creates iterators on west
		status = new JTextField(TEXT_FIELD_SIZE);
		status.addActionListener(this);
		add(status, WEST);
		changeStatus = new JButton("Change Status");
		add(changeStatus, WEST);

		JLabel lab = new JLabel(EMPTY_LABEL_TEXT);
		add(lab, WEST);

		picture = new JTextField(TEXT_FIELD_SIZE);
		picture.addActionListener(this);
		add(picture, WEST);
		changePicture = new JButton("Change Picture");
		add(changePicture, WEST);

		JLabel lab1 = new JLabel(EMPTY_LABEL_TEXT);
		add(lab1, WEST);

		friendField = new JTextField(TEXT_FIELD_SIZE);
		friendField.addActionListener(this);
		add(friendField, WEST);
		addFriend = new JButton("Add Friend");
		add(addFriend, WEST);

		addActionListeners();

// adds GCanvas
		canvas = new FacePamphletCanvas();
		add(canvas);

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String name = nameField.getText();

		if (name.length() >= 1) {
			if (e.getSource() == addButton) {
				updateAdd(name);
			} else if (e.getSource() == delete) {
				updateDelete(name);
			} else if (e.getSource() == lookUp) {
				updateLookUp(name);
			}
			nameField.setText("");
		} else if (e.getSource() == status || e.getSource() == changeStatus) {
			String text = status.getText();
			updateStatusField(text);

		} else if (e.getSource() == picture || e.getSource() == changePicture) {
			String text = picture.getText();
			updateImageField(text);

		} else if (e.getSource() == friendField || e.getSource() == addFriend) {
			String friendName = friendField.getText();
			updateFriendField(friendName);

		}

	}

	/**
	 * after clicking button add new profile is created, if profile with that name
	 * already existed, than nothing changes.
	 */
	private void updateAdd(String name) {
		if (dataBase.containsProfile(name)) {
			FacePamphletProfile oldProfile = dataBase.getProfile(name);
			current = oldProfile;
			canvas.displayProfile(current);
			canvas.showMessage("A profile with name " + name + " already exists.");

		} else {
			current = new FacePamphletProfile(name);
			dataBase.addProfile(current);
			canvas.displayProfile(current);
			canvas.showMessage("New Profile Created");
		}
	}

	/**
	 * after clicking delete button , if profile exists than it will be deleted and
	 * its name will be removed from her/his friends friendList. if profile already
	 * did not exist, than program will show relevant message.
	 */

	private void updateDelete(String name) {
		if (dataBase.containsProfile(name)) {
			dataBase.deleteProfile(name);
			canvas.removeAll();
			canvas.showMessage("Profile of " + name + " deleted.");

		} else {
			canvas.removeAll();
			canvas.showMessage("profile of " + name + " does not exist.");

		}
		current = null;
	}

	/**
	 * after clicking button lookUp if profile with that name exists than it will be
	 * displayed, else it will show relevant message and everything will be removed
	 * from canvas, current profile after that is not chosen
	 */

	private void updateLookUp(String name) {
		if (dataBase.containsProfile(name)) {
			current = dataBase.getProfile(name);
			canvas.displayProfile(current);
			canvas.showMessage("Displaying " + name);

		} else {
			canvas.removeAll();
			canvas.showMessage("profile of " + name + " does not exist.");
			current = null;
		}
		nameField.setText("");
	}

	/**
	 * after clicking change status button or enter , if profile is chosen, than
	 * status will be updated, else it will show relevant message.
	 */

	private void updateStatusField(String text) {
		if (text.length() >= 1) {
			if (current != null) {
				current.setStatus(text);
				canvas.displayProfile(current);
				canvas.showMessage("Status updated to " + text);

			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to change Status");
			}
			status.setText("");
		}
	}

	/**
	 * after clicking change image button or enter, if profile is chosen, than the
	 * picture will be updated, else it will show relevant message. if image with
	 * given name does not exist than everything will be removed from canvas and
	 * again shows relevant message.
	 */

	private void updateImageField(String text) {
		if (text.length() >= 1) {
			if (current != null) {
				GImage image = null;
				try {
					image = new GImage(text);
					current.setImage(image);
					canvas.displayProfile(current);
					canvas.showMessage("picture Updated");
				} catch (ErrorException ex) {
					update();
					canvas.showMessage("Unable to open image file: " + text);
				}
			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a profile to change picture");
			}
			picture.setText("");
		}
	}

	/**
	 * after clicking add friend button or enter, if both current profile and
	 * friends profile exists than friend will be added, else it will show relevant
	 * message
	 */

	private void updateFriendField(String friendName) {

		if (friendName.length() >= 1) {
			FacePamphletProfile friend = dataBase.getProfile(friendName);
			if (current != null) {
				String currentName = current.getName();
				if (friend != null) {
					if (friendName.equals(currentName)) {
						update();
						canvas.showMessage("You cannot add yourself as friend");
					} else if (current.addFriend(friendName)) {
						current.addFriend(friendName);
						friend.addFriend(currentName);
						canvas.displayProfile(current);
						canvas.showMessage(friendName + " added as a friend");
					} else {
						update();
						canvas.showMessage(currentName + " already has " + friendName + " as a friend");
					}
				} else {
					update();
					canvas.showMessage(friendName + " does not exist");
				}
			} else {
				canvas.removeAll();
				canvas.showMessage("Please select a Profile to add Friend");
			}
			friendField.setText("");
		}

	}

	private void update() {
		canvas.removeAll();
		canvas.displayProfile(current);
	}

}
