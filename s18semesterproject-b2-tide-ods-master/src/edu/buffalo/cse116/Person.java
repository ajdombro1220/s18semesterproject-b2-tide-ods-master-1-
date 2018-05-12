package edu.buffalo.cse116;

/**This class creates new instances of a player called Person, which holds the player's team, whether it is their turn
*or not, and whether they have won the game or not. Also contains helpful methods to access and change these values.
*@author Jason Sandoval
*/

public class Person {
	
	private String team;
	private boolean turn;
	private boolean winner;
	
/**
 * Creates a new person with a team value, (either "Red" or "Blue")	and sets winner to false since a new Person indicates the
 * start of a game.
 * @param team (Red or Blue)
 */	
	public Person(String team) {
		this.team = team;
		winner = false;
	}
	
/**
 * Sets whether it is this player's turn or not.	
 * @param True or False
 */
	public void setTurn(boolean a) {
		turn = a;
	}
	
/**
 * When the game ends, this method can be used to declare the winner and loser	
 * @param True or False
 */
	public void setWinner(boolean a) {
		winner = a;
	}
	
/**
 * 	Getter method to check what this player's current team is.
 * @return this current player's team
 */
	public String getTeam() {
		return team;
	}
	
/**
 * Getter method to check whether it is this player's turn or not.	
 * @return whether or not it is this player's turn.
 */
	public boolean getTurn() {
		return turn;
	}
	
/**
 * Checks if this player won the game or not	
 * @return True if the player is a winner, false if otherwise.
 */
	public boolean isWinner() {
		return winner;
	}

}
