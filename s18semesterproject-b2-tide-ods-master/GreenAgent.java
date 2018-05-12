package edu.buffalo.cse116;

public class GreenAgent extends Card {
	
/**
 * Creates a new Card with the BlueAgent role	
 */
	public GreenAgent() {
		super("GreenAgent", "");
		
	}
	
	@Override
	public String getRole() {
		return this.person;
	}

}
