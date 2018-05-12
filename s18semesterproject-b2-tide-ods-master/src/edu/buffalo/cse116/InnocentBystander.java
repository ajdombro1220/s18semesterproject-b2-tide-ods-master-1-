package edu.buffalo.cse116;

public class InnocentBystander extends Card {

/**
 * Creates a new Card with the InnocentBystander role.	
 */
	public InnocentBystander() {
		super("InnocentBystander", "");
		
	}
	
	@Override
	public String getRole() {
		return this.person;
	}
}
