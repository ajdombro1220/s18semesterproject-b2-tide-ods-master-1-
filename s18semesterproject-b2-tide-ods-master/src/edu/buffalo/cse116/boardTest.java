package edu.buffalo.cse116;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class boardTest {

	private ArrayList<String> low;
	private String _file;
	
	/* need to make a filename that can just be passed thru, not as a parameter here */
	@Before
	public void setlist(String filename) {
		_file = filename;
		try 
		{
		List<String> allNames = Files.readAllLines(Paths.get(filename));
		for(String s : allNames) 
			{
			low.add(s);
			}
		}
		catch(IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	public void testBoard() {
	Board n = new Board();
	assertEquals(25,n.getSize());
	}
	
	@Test
	public void testList() {
		Board n = new Board();
		assertTrue(low.equals(n.readFile(_file)));
		
	}
	
	@Test
	public void testPickNamesRandom() {
		Board n = new Board();
		boolean test = true;
		String[] a = n.pickNames(low);
		String[] b = n.pickNames(low);
		if (!a.equals(b)) {
			test = false;
		}
		
		assertFalse(test);
		
	}
	@Test
	public void testAmtCards(){
		Board n = new Board();
		n.setAssignment(n.pickNames(n.readFile(_file)));
		assertEquals(9, n.getRed());
		assertEquals(8,n.getBlue());
		assertEquals(7,n.getByst());
		assertEquals(1,n.getAss());
	}
	
	
	@Test
	public void testCardComplete() {
		
		Board n = new Board();
		
		assertTrue(allGood());
	}
	
	@Test
	public void testClue() {
		Board n = new Board();
		String[] a = n.pickNames(n.readFile(_file));
		/* card 0,0 in the _locales 2d array would be equal to the a[4] */
		assertTrue(n.getCard(0,4).equals());
		
		
	}
	
	
	@Test
	public void testEverything() {
		Board n = new Board();
		
		int aa = LocList.size();
		pickCard([][]);
		int ab = LocList.size();
		assertNotEquals(aa,ab);
		int ac = locList.size();
		pickCard([][]);
		ad = locList.size();
		assertEqualsac,ad);
		
	
	}
	
	@Test
	public void testWin() {
		Board n = new Board();
		assertTrue(person.blue.getWinVal());
		assertFalse(person.red.getWinVal());
		
	}
	
	@Test
	public void TestWhichLost() {
		Board n = new Board();
		assertTrue(person.blue.getWinVal());
		assertFalse(person.red.getWinVal());
	}
	
}


