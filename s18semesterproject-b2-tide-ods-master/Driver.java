package edu.buffalo.cse116;

/**
 * Class that contains the main method for us to run our game.
 * @author
 *
 */

public class Driver {
	
	/**
	 * This string should be set to the path of the list of words used for the game.
	 */
	static String filename = "GameWords.txt";

	public static void main(String[] args) {
		new Board(filename);
	}

}
