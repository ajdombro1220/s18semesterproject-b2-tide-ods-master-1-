package edu.buffalo.cse116;

public class BlueAgent extends Card {
	
/**
 * Creates a new Card with the BlueAgent role	
 */
	public BlueAgent() {
		super("BlueAgent", "");
		
	}
	
	@Override
	public String getRole() {
		return this.person;
	}

}
