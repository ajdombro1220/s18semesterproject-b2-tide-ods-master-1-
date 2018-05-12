package edu.buffalo.cse116;

public class RedAgent extends Card{

/**
 * Creates a new Card with the RedAgent role.	
 */
	public RedAgent() {
		super("RedAgent", "");
		
	}
	
	@Override
	public String getRole() {
		return this.person;
	}
	
}
