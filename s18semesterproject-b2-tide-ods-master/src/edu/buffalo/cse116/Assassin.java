package edu.buffalo.cse116;

public class Assassin extends Card {

/**
 * Creates a new Card with the Assassin role.	
 */
	public Assassin() {
		super("Assassin", "");
	}
	
	@Override
	public String getRole() {
		return this.person;
	}
	

}
