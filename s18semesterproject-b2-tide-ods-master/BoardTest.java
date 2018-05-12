package edu.buffalo.cse116;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/*
 * Class that tests the method in Board.java.
 */
public class BoardTest {

	private ArrayList<String> _names;
	private String _file;
	
	// reads the file and stores the names in an ArrayList
	@Before
	public void setlist() {
		_file = "C:\\Users\\Jason Sandoval\\git\\s18semesterproject-b2-tide-ods\\src\\edu\\buffalo\\cse116\\Dummy.txt";
		Board a = new Board(_file);
		a.readFile();
		_names = a.getAllCodenames();
	}
	
	
	
	@Test
	public void testBoard() {
		Board n = new Board(_file);
		assertEquals("There should be 25 Location instances", 25, n.getSize());
	}
	
	@Test
	public void testReadFile() {
		Board n = new Board(_file);
		n.readFile();
		assertTrue("readFile() should get the same result as the method in @Before", _names.equals(n.getAllCodenames()));
	}
	
	@Test
	public void testPickNames() {
		Board n = new Board(_file);
		n.readFile();
		n.pickCodenames(n.getAllCodenames());
		assertEquals("The list containing the codenames to be used in the game should be of size 25.", 25, n.getCodenames().size());
		Board m = new Board(_file);
		m.readFile();
		m.pickCodenames(m.getAllCodenames());
		assertFalse("This method should generate codenames randomly, but after running two instances the lists were equal", n.getCodenames().equals(m.getCodenames()));
	}
	@Test
	public void testSetAssignment(){
		Board n = new Board(_file);
		n.setAssignment();
		assertEquals("There should be 9 red cards", 9, n.getRed());
		assertEquals("There should be 8 blue cards", 8, n.getBlue());
		assertEquals("There should be 7 bystander cards", 7, n.getByst());
		assertEquals("There should be 1 assassin card", 1, n.getAss());
		Board m = new Board(_file);
		m.setAssignment();
		assertFalse("Locations should be randomized", m.getLocations().equals(n.getLocations()));
	}
	
	
	@Test
	public void testStart() {
		Board n = new Board(_file);
		n.start();
		assertTrue("The red team should have the first turn, but they didn't", n.getPlayer1().getTurn());
		for(int i=0; i<25; i++) {
			Location l = n.getLocations().get(i);
			assertNotNull("Each location instance on board must be assigned a card", l.getCard());
			assertNotNull("Each location instance on board must be assigned a codename", l.getCard().getCodename());
			assertFalse("Each location instance should not start revealed", l.getCard().isRevealed());
		}
	}
	
	@Test
	public void testCheckClue() {
		Board n = new Board(_file);
		n.start();
		ArrayList<Location> ab = n.getLocations();
		String c1 = "fghdfskjlgfsdk";
		String c2 = ab.get(0).getCard().getCodename();
		String c3 = ab.get(24).getCard().getCodename();
		String c4 = "";
		ab.get(9).getCard().setRevealed();
		String c5 = ab.get(9).getCard().getCodename();
		assertTrue("Should return true when the clue is not found on the board", n.checkClue(c1));
		assertFalse("Should return false when the clue is contained in one of the location's codenames", n.checkClue(c2));
		assertFalse("Should return false when the clue is contained in one of the location's codenames",n.checkClue(c3));
		assertTrue("Should return true when the clue is not found on the board", n.checkClue(c4));
		assertTrue("Should return true if clue equals a codename that is revealed on the board", n.checkClue(c5));
	}
	
	@Test
	public void testValidFirstTurnP1() {
		Board n = new Board(_file);
		n.start();
		assertTrue("Red team should have the first turn of the game", n.whoseTurn().equals("Red Turn"));
		n.startTurnP1("ooglybooglyboo", 4);
		assertEquals("the counter should be set to the input provided by the spymaster", 4, n.getTurns());
		assertTrue("The clue should be set to the input provided by the spymaster", "ooglybooglyboo".equals(n.getClue()));
	}
	
	@Test
	public void testInvalidFirstTurnP1() {
		Board n = new Board(_file);
		n.start();
		assertTrue("Red team should have the first turn of the game", n.whoseTurn().equals("Red Turn"));
		String x = n.getCodenames().get(5);
		n.startTurnP1(x, 9);
		assertFalse("Red team turn should be forfeited when an unrevealed codename is chosen as the clue", n.whoseTurn().equals("Red Turn"));
		assertTrue("It should be blue teams turn if red provides invalid clue", n.whoseTurn().equals("Blue Turn"));
		assertEquals("The turn counter should be reset when a team loses their turn", 0, n.getTurns());
		assertEquals("The clue should be reset when a team loses their turn", "", n.getClue());
	}
	
	@Test
	public void testP2FirstTurn() {
		Board n = new Board(_file);
		n.start();
		String x = n.getCodenames().get(11);
		n.startTurnP1(x, 6);
		n.startTurnP2("oanclladjf", 7);
		assertTrue("It should be blue teams turn after inputting a valid clue", n.whoseTurn().equals("Blue Turn"));
		assertEquals("Turn counter does not equal input", 7, n.getTurns());
		assertEquals("Clue does not equal input", "oanclladjf", n.getClue());
	}
	
	@Test
	public void testRegTurnP1() {
		Board n = new Board(_file);
		n.start();
		n.startTurnP1("vandsladf", 3);
		n.pickCard(2, 4);
		Location[][] temp = n.getBoard();
		Location picked = temp[2][4];
		if(picked.getCard().getRole().equals("RedAgent")) {
			assertEquals("It should still be reds turn after picking a red card", "Red Turn", n.whoseTurn());
			assertEquals("There should only be 2 turns left after a valid pick", 2, n.getTurns());
			assertTrue("The location picked should be revealed", picked.getCard().isRevealed());
		}
		if(picked.getCard().getRole().equals("BlueAgent") || picked.getCard().getRole().equals("InnocentBystander")) {
			assertEquals("It should be blues turn after picking a non-red card", "Blue Turn", n.whoseTurn());
			assertEquals("The turncounter should be reset when red picks invalid card", 0, n.getTurns());
			assertEquals("The clue should be reset after not picking a red card", "", n.getClue());
			assertTrue("The location picked should be revealed", picked.getCard().isRevealed());			
		}
		if(picked.getCard().getRole().equals("Assassin")) {
			assertFalse("When a winner is declared red shouldnt be able to take take a turn", n.getPlayer1().getTurn());
			assertFalse("When a winner is declared blue shouldnt be able to take a turn", n.getPlayer2().getTurn());
			assertNotNull("There should be a winner when the assassin card is picked", n.getWinner());
			Person winner = n.getWinner();
			assertTrue("Player 2 should be the winner when red picks an assassin", winner.equals(n.getPlayer2()));
		}
	}
	
	@Test
	public void testRegTurnP2() {
		Board n = new Board(_file);
		n.start();
		String invalid = n.getCodenames().get(0);
		n.startTurnP1(invalid, 4590);
		n.startTurnP2("fhadskl", 6);
		n.pickCard(4, 1);
		Location[][] temp = n.getBoard();
		Location picked = temp[4][1];
		if(picked.getCard().getRole().equals("BlueAgent")) {
			assertEquals("It should still be blues turn after picking a blue card", "Blue Turn", n.whoseTurn());
			assertEquals("There should only be 5 turns left after a valid pick", 5, n.getTurns());
			assertTrue("The location picked should be revealed", picked.getCard().isRevealed());
		}
		if(picked.getCard().getRole().equals("RedAgent") || picked.getCard().getRole().equals("InnocentBystander")) {
			assertEquals("It should be reds turn after picking a non-blue card", "Red Turn", n.whoseTurn());
			assertEquals("The turncounter should be reset when blue picks invalid card", 0, n.getTurns());
			assertEquals("The clue should be reset after not picking a blue card", "", n.getClue());
			assertTrue("The location picked should be revealed", picked.getCard().isRevealed());			
		}
		if(picked.getCard().getRole().equals("Assassin")) {
			assertFalse("When a winner is declared red shouldnt be able to take take a turn", n.getPlayer1().getTurn());
			assertFalse("When a winner is declared blue shouldnt be able to take a turn", n.getPlayer2().getTurn());
			assertNotNull("There should be a winner when the assassin card is picked", n.getWinner());
			Person winner = n.getWinner();
			assertTrue("Player 1 should be the winner when blue picks the assassin", winner.equals(n.getPlayer1()));
		}
	}
	
	@After
	public void resetNames() {
		_names = new ArrayList<>();
	}
}


