package edu.buffalo.cse116;
/**
 * Card class that serves as a basis for all the different types of cards in the game, and
 * holds key information about the card that is used to play the game.
 * @author Jason Sandoval
 *
 */

public class Card {
	
	private boolean revealed;
	public String person;
	private String codename;
	
/**
 * Creates a new card instance that is not visible (face down) on the board, an gives it a team;	
 */
	public Card(String team, String name) {
		revealed = false;
		person = team;
		codename = name;
	}
	
/**
 * This method should be called when a card is picked.	
 */
	public void setRevealed() {
		revealed = true;
	}
	
/**
 * Checks to see if the card is faced up or down.	
 * @return True for faced up, false otherwise
 */
	public boolean isRevealed() {
		return revealed;
	}
/**
 * 	Sets the codename to be assigned to this current Location instance.
 * @param name
 */
	public void setCodename(String name) {
		codename = name;
	}
/**
 * Gets the codename at this Location.	
* @return codename
 */
	public String getCodename() {
			return codename;
	}
			
/**
 * Gets the current card's role, which will be one of four strings, "Assassin", "RedAgent", "BlueAgent",
 * or "InnocentBystander"	
 * @return
 */
	public String getRole() {return person;}
	

	
	}
