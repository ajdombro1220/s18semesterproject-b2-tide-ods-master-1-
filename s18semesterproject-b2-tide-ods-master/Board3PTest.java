package edu.buffalo.cse116;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Board3PTest {

	private String _file;
	
	@Before
	public void setFilename() {
		_file = "Gamefile.txt";
	}
	
	@Test
	public void testStart3P() {
		Board n = new Board(_file);
		n.startThree();
		assertEquals("Starting a new game should select 25 words to be used on the board",25,n.getCodenames().size());
		assertEquals("Starting a new game should create 25 location instances",25,n.getLocations().size());
		int red = 0;
		int blue = 0;
		int green = 0;
		int inno = 0;
		int assass = 0;
		for(Location l : n.getLocations()) {
			assertFalse("There should be no revealed locations at the start of the game",l.getCard().isRevealed());
			if(l.getCard().getRole().equals("Assassin")) {
				assass++;
			}
			if(l.getCard().getRole().equals("RedAgent")) {
				red++;
			}
			if(l.getCard().getRole().equals("BlueAgent")) {
				blue++;
			}
			if(l.getCard().getRole().equals("GreenAgent")) {
				green++;
			}
			if(l.getCard().getRole().equals("InnocentBystander")) {
				inno++;
			}
		}
		assertEquals("There should be 2 assassin locations at the start of a 3-P game",2,assass);
		assertEquals("There shuold be 6 red agents at the start of a 3-P game", 6,red);
		assertEquals("There should be 5 blue agents at the start of a 3-P game", 5, blue);
		assertEquals("There should be 5 green agents at the start of a 3-P game", 5, green);
		assertEquals("There should be 7 innocent bystanders at the start of a 3-P game", 7, inno);
	}
	
	@Test
	public void testFirstTurns() {
		Board n = new Board(_file);
		n.startThree();
		assertTrue("Player 1(red team) should always have first turn",n.IfPlayer1Turn());
		n.nextTurn();
		assertTrue("It should be blues turn after red passes their turn",n.IfPlayer2Turn());
		n.nextTurn();
		assertTrue("It should be greens turn after blue passes their turn",n.get_player3().getTurn());
		n.nextTurn();
		assertTrue("It should be reds turn after green passes ther turn",n.IfPlayer1Turn());
		n.startTurnP1("RANDOMCLUE", 5);
		assertTrue("It should still be reds turn after the spymaster inputs a valid clue and count",n.IfPlayer1Turn());
		assertEquals("The red spymasters clue should be available while its still reds turn","RANDOMCLUE",n.getClue());
		assertEquals("The red spymasters count should be available and unchanged while its still reds turn",5,n.getTurns());
		n.nextTurn();
		assertTrue("It should be blues turn after red passes their turn",n.IfPlayer2Turn());
		assertEquals("Clue should be reset when a team passes","",n.getClue());
		assertEquals("Count should be reset when a team passes",0,n.getTurns());
		String invalidClue = n.getCodenames().get(0);
		n.startTurnP2(invalidClue, 7);
		assertTrue("It should be greens turn after blue inputs an invalid clue",n.get_player3().getTurn());
		assertEquals("Clue should be reset after an invalid clue","",n.getClue());
		assertEquals("Count should be reset after an invalid clue",0,n.getTurns());
		n.startTurnP3("CHINANUMBAWAN", 2);
		assertTrue("It should still be greens turn after spymaster puts a valid clue",n.get_player3().getTurn());
		assertEquals("The clue should be available while its still greens turn","CHINANUMBAWAN",n.getClue());
		assertEquals("The count should be available and unchanged while its still greens turn",2,n.getTurns());
	}
	
	@Test
	public void testPickValidCardMidGame() {
		Board n = new Board(_file);
		n.startThree();
		int red1X = 0; int blue1x = 0; int green1x = 0;
		int red1Y = 0; int blue1y = 0; int green1y = 0;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("RedAgent")) {
				red1X = l.getCoordX();
				red1Y = l.getCoordY();
			}
			if(l.getCard().getRole().equals("BlueAgent")) {
				blue1x = l.getCoordX();
				blue1y = l.getCoordY();
			}
			if(l.getCard().getRole().equals("GreenAgent")) {
				green1x = l.getCoordX();
				green1y = l.getCoordY();
			}
		}
		n.startTurnP1("randomclue", 2);
		n.pickCard(red1X, red1Y);
		assertTrue("It should still be reds turn after picking a valid card since count was >1",n.IfPlayer1Turn());
		assertEquals("Since the count was 2 and a valid card was picked, count should decrement by 1",1,n.getTurns());
		assertEquals("The number of red cards left should decrease when one is picked",5,n.getRed());
		Location[][] board = n.getBoard();
		Location loc1 = board[red1X][red1Y];
		assertTrue("The picked card should be revealed",loc1.getCard().isRevealed());
		n.nextTurn();
		n.startTurnP2("superrandom", 3);
		n.pickCard(blue1x, blue1y);
		assertTrue("It should still be blues turn after picking a valid card since count was >1",n.IfPlayer2Turn());
		assertEquals("Count should decrement by 1 when a valid card is picked and count is >1",2,n.getTurns());
		assertEquals("The number of blue cards left should decrease when one is picked",4,n.getBlue());
		Location[][] board2 = n.getBoard();
		Location loc2 = board2[blue1x][blue1y];
		assertTrue("The picked card should be revealed",loc2.getCard().isRevealed());
		n.nextTurn();
		n.startTurnP3("awesomeclue", 1);
		n.pickCard(green1x, green1y);
		assertEquals("The number of green cards left should decrease when one is picked",4,n.get_greenCount());
		assertTrue("Even though green picked a valid card, the count was 1, so the turn should change", n.IfPlayer1Turn());
		assertEquals("The clue should be reset when the turns change","",n.getClue());
		assertEquals("The count should be reset when the turns change",0,n.getTurns());
		Location[][] board3 = n.getBoard();
		Location loc3 = board3[green1x][green1y];
		assertTrue("The picked card should be revealed",loc3.getCard().isRevealed());
	}
	
	@Test
	public void testPickBadCardMidGame() {
		Board n = new Board(_file);
		n.startThree();
		int red1x = 0; int blue1x = 0; int green1x = 0; int inno1x = 0;
		int red1y = 0; int blue1y = 0; int green1y = 0; int inno1y = 0;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("RedAgent")) {
				red1x = l.getCoordX();
				red1y = l.getCoordY();
			}
			if(l.getCard().getRole().equals("BlueAgent")) {
				blue1x = l.getCoordX();
				blue1y = l.getCoordY();
			}
			if(l.getCard().getRole().equals("GreenAgent")) {
				green1x = l.getCoordX();
				green1y = l.getCoordY();
			}
			if(l.getCard().getRole().equals("InnocentBystander")) {
				inno1x = l.getCoordX();
				inno1y = l.getCoordY();
			}
		}
		n.startTurnP1("legalclue", 5);
		n.pickCard(green1x, green1y);
		assertTrue("It should be blues turn after red picks an invalid location",n.IfPlayer2Turn());
		assertEquals("The clue should be reset after picking an invalid location","",n.getClue());
		assertEquals("The count should be reset after picking an invalid location",0,n.getTurns());
		n.startTurnP2("cardiB", 4);
		n.pickCard(inno1x, inno1y);
		assertTrue("It should be greens turn after blue picks an invalid location",n.get_player3().getTurn());
		assertEquals("The number of bystanders left should decrement when one is picked",6,n.getByst());
		n.startTurnP3("misskeesha", 6);
		n.pickCard(red1x, red1y);
		assertTrue("It should be reds turn after green picks an invalid card",n.IfPlayer1Turn());
	}
	
	@Test
	public void testAssassinElimination1() {
		Board n = new Board(_file);
		n.startThree();
		int assassX = 0; int blueX = 0;
		int assassY = 0; int blueY = 0;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("Assassin")) {
				assassX = l.getCoordX();
				assassY = l.getCoordY();
			}
			if(l.getCard().getRole().equals("BlueAgent")) {
				blueX = l.getCoordX();
				blueY = l.getCoordY();
			}
		}
		n.nextTurn();
		n.startTurnP2("fajitas", 9);
		n.pickCard(assassX, assassY);
		assertTrue("It should be greens turn after blue picks the assassin",n.get_player3().getTurn());
		assertEquals("The number of assassins left should decrement when one is picked",1,n.getAss());
		Location[][] board = n.getBoard();
		Location loc1 = board[assassX][assassY];
		assertTrue("When the first assassin is picked it should be revealed", loc1.getCard().isRevealed());
		n.nextTurn();
		n.nextTurn();
		assertTrue("Since blue was eliminated it should be greens turn after red passes",n.get_player3().getTurn());
		n.nextTurn();
		n.startTurnP1("juicyjslob", 4);
		n.pickCard(blueX, blueY);
		assertTrue("It should be greens turn after red picks an invalid card since blue is eliminated",n.get_player3().getTurn());
	}
	
	@Test
	public void testAssassinElimination2() {
		Board n = new Board(_file);
		n.startThree();
		int assassX = 0; int blueX = 0;
		int assassY = 0; int blueY = 0;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("Assassin")) {
				assassX = l.getCoordX();
				assassY = l.getCoordY();
			}
			if(l.getCard().getRole().equals("BlueAgent")) {
				blueX = l.getCoordX();
				blueY = l.getCoordY();
			}
		}
		n.startTurnP1("tortillas", 3);
		n.pickCard(assassX, assassY);
		assertTrue("It should be blues turn after red picks the assassin",n.IfPlayer2Turn());
		assertEquals("The number of assassins left should decrement when one is picked",1,n.getAss());
		Location[][] board = n.getBoard();
		Location loc1 = board[assassX][assassY];
		assertTrue("When the first assassin is picked it should be revealed", loc1.getCard().isRevealed());
		n.nextTurn();
		n.nextTurn();
		assertTrue("Since red was eliminated it should be blues turn after green passes",n.IfPlayer2Turn());
		n.nextTurn();
		n.startTurnP3("juicyjslob", 4);
		n.pickCard(blueX, blueY);
		assertTrue("It should be blues turn after greed picks an invalid card since red is eliminated",n.IfPlayer2Turn());
	}
	
	@Test
	public void testAssassinEndGame1() {
		Board n = new Board(_file);
		n.startThree();
		Location assassin1 = null;
		Location assassin2 = null;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("Assassin")) {
				if(assassin1==null) {
					assassin1 = l;
				}else {
					assassin2 = l;
				}
			}
		}
		n.startTurnP1("ilovebadbitches", 4);
		int x = assassin1.getCoordX();
		int y = assassin1.getCoordY();
		n.pickCard(x, y);
		assertTrue("It should be blues turn when red picks an assassin",n.IfPlayer2Turn());
		n.nextTurn();
		String illegal = assassin2.getCard().getCodename();
		n.startTurnP3(illegal, 3);
		assertTrue("It should be blues turn when green inputs invalid clue since red is eliminated",n.IfPlayer2Turn());
		n.startTurnP2("superclue", 3);
		int i = assassin2.getCoordX();
		int j = assassin2.getCoordY();
		n.pickCard(i, j);
		assertEquals("Since all assassins have been picked the number of assassins left should be 0",0,n.getAss());
		assertNotNull("There should be a winner if both assassin were picked",n.getWinner());
		assertTrue("Green should be the winner since blue and red picked the assassins",n.getWinner().equals(n.get_player3()));
	}
	
	@Test
	public void testAssassinEndGame2() {
		Board n = new Board(_file);
		n.startThree();
		Location assassin1 = null;
		Location assassin2 = null;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("Assassin")) {
				if(assassin1==null) {
					assassin1 = l;
				}else {
					assassin2 = l;
				}
			}
		}
		n.startTurnP1("ilovebadbitches", 4);
		int x = assassin1.getCoordX();
		int y = assassin1.getCoordY();
		n.pickCard(x, y);
		assertTrue("It should be blues turn when red picks an assassin",n.IfPlayer2Turn());
		n.nextTurn();
		String illegal = assassin2.getCard().getCodename();
		n.startTurnP3(illegal, 3);
		assertTrue("It should be blues turn when green inputs invalid clue since red is eliminated",n.IfPlayer2Turn());
		n.nextTurn();
		n.startTurnP3("superclue", 3);
		int i = assassin2.getCoordX();
		int j = assassin2.getCoordY();
		n.pickCard(i, j);
		assertEquals("Since all assassins have been picked the number of assassins left should be 0",0,n.getAss());
		assertNotNull("There should be a winner if both assassin were picked",n.getWinner());
		assertTrue("Green should be the winner since blue and red picked the assassins",n.getWinner().equals(n.getPlayer2()));
	}
	
	@Test
	public void testRegularWin1() {
		Board n = new Board(_file);
		n.startThree();
		Location[] greens = new Location[5];
		Location green1 = null;
		Location green2 = null;
		Location green3 = null;
		Location green4 = null;
		Location green5 = null;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("GreenAgent")) {
				if(green1==null) {
					green1 = l;
					greens[0] = green1;
				}
				else if(green2==null) {
					green2 = l;
					greens[1] = green2;
				}
				else if(green3==null) {
					green3 = l;
					greens[2] = green3;
				}
				else if(green4==null) {
					green4 = l;
					greens[3] = green4;
				}
				else if(green5==null) {
					green5 = l;
					greens[4] = green5;
				}
			}
		}
		n.nextTurn();
		n.nextTurn();
		n.startTurnP3("winnerrrrr", 6);
		for(int i=0; i<greens.length; i++) {
			int x = greens[i].getCoordX();
			int y = greens[i].getCoordY();
			n.pickCard(x, y);
			assertTrue("It should still be greens turn after picking a valid location", n.get_player3().getTurn());
		}
		assertNotNull("There should be a winner if a player picks all their coresponding locations",n.getWinner());
		assertTrue("Green should be the winner since all green cards were revealed",n.getWinner().equals(n.get_player3()));
	}
	
	@Test
	public void testRegularWin2() {
		Board n = new Board(_file);
		n.startThree();
		Location[] reds = new Location[6];
		Location red1 = null;
		Location red2 = null;
		Location red3 = null;
		Location red4 = null;
		Location red5 = null;
		Location red6 = null;
		for(Location l : n.getLocations()) {
			if(l.getCard().getRole().equals("RedAgent")) {
				if(red1==null) {
					red1 = l;
					reds[0] = red1;
				}
				else if(red2==null) {
					red2 = l;
					reds[1] = red2;
				}
				else if(red3==null) {
					red3 = l;
					reds[2] = red3;
				}
				else if(red4==null) {
					red4 = l;
					reds[3] = red4;
				}
				else if(red5==null) {
					red5 = l;
					reds[4] = red5;
				}
				else if(red6==null) {
					red6 = l;
					reds[5] = red6;
				}
			}
		}
		n.startTurnP1("winnerrrrr", 7);
		for(int i=0; i<reds.length; i++) {
			int x = reds[i].getCoordX();
			int y = reds[i].getCoordY();
			n.pickCard(x, y);
			assertTrue("It should still be reds turn after picking a valid location",n.IfPlayer1Turn());
		}
		assertNotNull("There should be a winner if a player picks all their coresponding locations",n.getWinner());
		assertTrue("Green should be the winner since all green cards were revealed",n.getWinner().equals(n.getPlayer1()));
	}
}

