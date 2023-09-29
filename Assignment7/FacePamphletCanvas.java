/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		message = new GLabel(msg);
		double x = (getWidth() - message.getWidth()) / 2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN - message.getDescent();
		message.setFont(MESSAGE_FONT);
		add(message, x, y);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		profileName = displayName(profile);
		displayStatus(profile);
		displayImage(profile);
		displayFriends(profile);

	}
	// displays name with blue letters

	private GLabel displayName(FacePamphletProfile profile) {

		String name = profile.getName();
		GLabel profileName = new GLabel(name);
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + profileName.getHeight();
		profileName.setColor(Color.BLUE);
		profileName.setFont(PROFILE_NAME_FONT);
		add(profileName, x, y + profileName.getDescent());

		return profileName;

	}
	
	// displays status

	private void displayStatus(FacePamphletProfile profile) {

		String status = profile.getStatus();

		double yPic = TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN + IMAGE_HEIGHT;

		GLabel profileStatus = new GLabel(status);
		double x = LEFT_MARGIN;
		double y1 = yPic + STATUS_MARGIN + profileStatus.getHeight();
		profileStatus.setFont(PROFILE_STATUS_FONT);
		add(profileStatus, x, y1);

	}

	/**
	 * displays image if it exists , else draws square and "no image" label written
	 * inside
	 */

	private void displayImage(FacePamphletProfile profile) {

		GImage image = profile.getImage();
		double x = LEFT_MARGIN;
		double y = TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN;

		if (image != null) {
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image, x, y);
		} else {
			GRect square = new GRect(x, y, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(square);
			GLabel noImage = new GLabel("No Image");
			double x1 = x + IMAGE_WIDTH / 2 - noImage.getWidth();
			double y1 = y + IMAGE_HEIGHT / 2 - noImage.getDescent() + noImage.getAscent();
			noImage.setFont(PROFILE_IMAGE_FONT);
			add(noImage, x1, y1);
		}

	}

	// draws friends label and writes down friends from current profiles friendList

	private void displayFriends(FacePamphletProfile profile) {
		double x = getWidth() / 2;
		double y = TOP_MARGIN + profileName.getHeight() + IMAGE_MARGIN;
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, x, y);
		Iterator<String> friends = profile.getFriends();
		for (int i = 1; friends.hasNext(); i++) {
			GLabel friend = new GLabel(friends.next());
			double y1 = y + label.getHeight() + i * friend.getAscent() + (i - 1) * friend.getHeight();
			friend.setFont(PROFILE_FRIEND_FONT);
			add(friend, x, y1);
		}

	}

	private GLabel profileName;
	private GLabel message;
}
