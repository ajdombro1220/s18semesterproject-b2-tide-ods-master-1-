package edu.buffalo.cse116;

import javax.swing.JPanel;

/**
 * This class stores a card and a codename in a Location which is used on the game's main board,
 * and also contains useful setters and getters to help in game when players pick up cards.
 * @author Jason Sandoval
 *
 */

public class Location extends JPanel {
	
	private Card card;
	private Integer[] coordinates;
	
/**
 * Creates a new Location instance without any parameters or fields being initialized.	
 */
	public Location() {
		super();
	}
	
	public void setCoordinates(int x, int y) {
		coordinates = new Integer[2];
		coordinates[0] = x;
		coordinates[1] = y;
	}
	
/**
 * Sets the card to this location, which will either be an assassin, blue, red, or bystander
 * card.	
 * @param p
 */
	public void setCard(String team, String codename) {
		 card = new Card(team,codename);
	}

	public void setCard(Card c) {
		card = c;
	}
/**
 * 	Gets the card at this Location.
 * @return
 */
	public Card getCard() {
		return card;
	}
	
	/**
	 * These methods return the x and y coordinates of this instances location on the 5x5 board 
	 * 
	 */
	public int getCoordX() {
		return coordinates[0];
	}
	
	public int getCoordY() {
		return coordinates[1];
	}

}
