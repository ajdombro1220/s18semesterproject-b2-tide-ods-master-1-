package edu.buffalo.cse116;
import java.io.File;
/**This class creates new instances of the board and acts as an interface, directing the game.
 *It tracks the values of each Location tile on the board, as well as changes and sets the values. 
 *It also starts the game and ends it.
 *
 *@author Andrew Dombrowski
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Board{
	
	
// Holds the 25 Location instances.
	private ArrayList<Location> _locales;
	
// The 5x5 array that will to display the board in the GUI.
	private Location [][] _board;
	
// Tells you how many red cards are still on the board.
	private int _redCount;
	
// Tells you how many blue cards are still on the board.
	private int _blueCount;
	
// Tells you how many bystander cards are still on the board.
	private int _bystCount;
	
// Tells you how many assassin cards are still on the board.
	private int _assCount;
	
// The red player.
	private Person _player1;
	
// The blue player.
	private Person _player2;
	
// Used to get the index of the Locations in _locales to put in the 5x5 array
	private int _counter;
	
// Counts how many turns a team has remaining.
	private int _turnCounter;
	
// File which contains the words needed to choose codenames from.
	private String _file;
	
// List of codenames (Strings) for this board instance
	private ArrayList<String> _codenames;
	
// List of all possible codenames written in from a file
	private ArrayList<String> _allCodenames;
	
// String that holds the value of the clue provided by whoevers team is currently taking their turn.
	private String _clue;
	
	private Person winner;
// Is 0 if game is new
	private int modcount;
	
	
	
	public Board(String filename) {
		_file = filename;
		_player1 = new Person("Red");
		_player2 = new Person("Blue");
		_redCount = 0;
		_bystCount = 0;
		_blueCount = 0;
		_assCount = 0;
		_clue = "";
		_locales = new ArrayList<Location>();
		for(int i=0; i<25; i++) {
			_locales.add(new Location());
		}
		_codenames = new ArrayList<String>();
		 _board = new Location [5][5];
		 _counter = 0;
		 _turnCounter = 0;
		 modcount = 0;
		 winner = null;
	}
	
	
	/* reads file and stores the words that will be used to give the codenames in an arraylist.
	 * 
	 * @param filename. URL for to access the file.
	 * @return an ArrayList of type String holding words to set as codenames.
	 */
	public void readFile() {
		ArrayList<String> listWords = new ArrayList<String>();
			try {
				for (String word : (Files.readAllLines(Paths.get(_file)))) {
					listWords.add(word);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		_allCodenames = listWords;
	}
	
	
	/*generates a list of 25 codenames from a list of words
	 * @parameters ArrayList of type String
	 * @return ArrayList of type String with a size of 25 */
	public void pickCodenames(ArrayList<String> list) {
		ArrayList<String> codeNames = new ArrayList<String>();
		Collections.shuffle(list);
		if(list != null) {
			for (int j=0; j<25 ; j++) {
				String words = list.get(j);
				codeNames.add(words);
			}
		}
		_codenames = codeNames;
	}
	/* sets all locations on board to their cards and assigns the cards their roles.
	 * 
	 * @parameters assignments. A string of the codenames randomly selected from a list using the method above.
	 * @return a list of the 25 assignments, in order from left to right, row by row (will flesh out a different,
	 *  	more efficient return when thought of for testing,
	 *  	but this is ultimately a setter method and could be void.)
	 */
	public void setAssignment()  {
		readFile();
		pickCodenames(_allCodenames);
		for(int i=0; i<_locales.size(); i++) {
			if(i==0) {
				Card a = new Card("Assassin", _codenames.get(i));
				Location l = _locales.get(i);
				l.setCard(a);
				_locales.set(i, l);
				_assCount++;
			}
			if(i<10 && i!=0) {
				Card r = new Card("RedAgent", _codenames.get(i));
				Location l = _locales.get(i);
				l.setCard(r);
				_locales.set(i, l);
				_redCount++;
			}
			if(i<18 && i>=10) {
				Card b = new Card("BlueAgent", _codenames.get(i));
				Location l = _locales.get(i);
				l.setCard(b);
				_locales.set(i, l);
				_blueCount++;
			}
			if(i<25 && i>=18) {
				Card s = new Card("InnocentBystander", _codenames.get(i));
				Location l = _locales.get(i);
				l.setCard(s);
				_locales.set(i, l);
				_bystCount++;
			}
		}
		Collections.shuffle(_locales);
	}
	/* Places the cards on the board, using counter as an index until the 2D array is filled and sets it equal to _board
	 * @parameters none
	 * @return a 2D Array of type Location that will act as the randomly generated playing board
	 */
	public Location[][] placeOnBoard() {
		Location[][] board = new Location [5][5];
		for (int j=0;j<5;j++){
			for(int i=0;i<5;i++) {
				board[j][i]= _locales.get(_counter);
				_locales.get(_counter).setCoordinates(j, i);
				_counter++;
			}
		}
		_board = board;
		return board;
		
	}
	
	/* sets red to start the game, initialized the game */
	
	public void start() {
		setAssignment();
		placeOnBoard();
		_player1 = new Person("Red");
		_player2 = new Person("Blue");
		_player1.setTurn(true);
		_player2.setTurn(false);
	}
	/* changes the turn from the start conditions. If the counts of any of the valid agents reaches 0, 
	 * the method will instead try to determine if the Assassin was chosen and not already called by pickCard, and naming the opposing player the 
	 * winner, and if that also is not the case, it calls on win() to determine whose count has reached 0 at the end of the turn;
	 */
	public void startTurnP1(String clue, int count) {
		if(_player1.getTurn()) {
			_turnCounter = count;
			_clue = clue;
			modcount++;
			for(int i=0; i<_locales.size(); i++) {
				if(clue.equals(_codenames.get(i)) && _locales.get(i).getCard().isRevealed()==false) {
					_player1.setTurn(false);
					_player2.setTurn(true);
					_turnCounter = 0;
					_clue = "";
				}
			}
		}
		GUI.update();
	}
	
	public void startTurnP2(String clue, int count) {
		if(_player2.getTurn()) {
			_turnCounter = count;
			_clue = clue;
			modcount++;
			for(int i=0; i<_locales.size(); i++) {
				if(clue.equals(_codenames.get(i)) && _locales.get(i).getCard().isRevealed()==false) {
					_player2.setTurn(false);
					_player1.setTurn(true);
					_turnCounter = 0;
					_clue = "";
				}
			}
		}
	}
	
	public void pass() {
		_turnCounter = 0;
		_clue = "";
		modcount++;
		if(_player1.getTurn()) {
			_player2.setTurn(true);
			_player1.setTurn(false);
		}else {
			_player1.setTurn(true);
			_player2.setTurn(false);
		}
	}
	
	
	
	/*checks clue. if the clue is another word on the board, it will return false.
	 * If it is not a word on the board or a word that has been revealed, it will return true.
	 * 
	 * @parameters clue. A String that is the clue wanting to be given
	 * @returns true or false 
	 * */
	public boolean checkClue(String clue) {
		boolean legal = true;
		for(Location location : _locales) {
			if(location.getCard().isRevealed()==false) {
				if(location.getCard().getCodename().equals(clue)) {
					legal = false;
				}
			}
		}
		return legal;
	}
	
	/*method for a player to pick a card when they give a board location. the card switches its revealed to true and if it's team matches,
	 *  that team "gets" the card, else it stays revealed. If the card revealed is the Assassin, call assLose().
	 *  
	 * @parameters i,j. Two int vales for coordinates on the 2D array of locations
	 * @returns
	 */
	public void pickCard(int i, int j) {
		if(_turnCounter>0) {
			modcount++;
			Location picked = _board[i][j];
			picked.getCard().setRevealed();
			if(picked.getCard().getRole().equals("Assassin")) {
				_assCount--;
				if(_player1.getTurn()) {
					_player1.setWinner(false);
					_player2.setWinner(true);
					winner = _player2;
				}
				if(_player2.getTurn()) {
					_player1.setWinner(true);
					_player2.setWinner(false);
					winner = _player2;
				}
				endGame();
			}
			if(picked.getCard().getRole().equals("RedAgent")) {
				_redCount--;
				if(_player1.getTurn()) {
					_turnCounter--;	
					if(_turnCounter==0) {
						_player1.setTurn(false);
						_player2.setTurn(true);
						_clue = "";
					}
				}
				else {
					_player1.setTurn(true);
					_player2.setTurn(false);
					_turnCounter = 0;
					_clue = "";
				}
				checkIfGameOver();
			}
			if(picked.getCard().getRole().equals("BlueAgent")) {
				_blueCount--;
				if(_player2.getTurn()) {
					_turnCounter--;
					if(_turnCounter==0) {
						_player1.setTurn(true);
						_player2.setTurn(false);
						_clue = "";
					}
				}
				else {
					_player2.setTurn(true);
					_player1.setTurn(false);
					_turnCounter = 0;
					_clue = "";
				}
				checkIfGameOver();
			}
			if(picked.getCard().getRole().equals("InnocentBystander")) {
				_bystCount--;
				if(_player1.getTurn()) {
					_player1.setTurn(false);
					_player2.setTurn(true);
				}
				else {
					_player2.setTurn(false);
					_player1.setTurn(true);
				}
				_turnCounter = 0;
				_clue = "";
			}
		}
		
	}
	
	/* sees if a player has lost to the Assassin. uses int as a return to test for correct values.
	 * 
	 * @parameters none
	 * @returns -1 or 1 indicating if red or blue lost correspondingly.
	 *
	 * sees if a player has won. uses int as a return to test for correct values.
	 * 
	 * @parameters none
	 * @returns 1 or -1 indicating if red or blue won correspondingly.
	 */
	
	
	
	public void endGame() {
		_player1.setTurn(false);
		_player2.setTurn(false);
		_turnCounter = 0;
	}

	public void checkIfGameOver() {
		if(_redCount == 0) {
			_player1.setWinner(true);
			winner = _player1;
			endGame();
		}
		if(_blueCount == 0) {
			_player2.setWinner(true);
			winner = _player2;
			endGame();
		}
	}
	

	/* getters and setters */
	
	public int getSize() {
		return _locales.size();
	}
	public int getRed() {
		return _redCount;
	}
	public void setRed(int i) {
	_redCount = i;
	}
	public int getByst() {
	return _bystCount;
	}
	public void setByst(int i) {
	_bystCount = i;
	}
	public int getBlue() {
	return _blueCount;
	}
	public void setBlue(int i) {
	_blueCount = i;
	}
	public int getAss() {
	return _assCount;
	}
	public void setAss(int i) {
	_assCount = i;
	}
	public void setCounter(int i) {
	_counter = i;
	}
	public int getCounter() {
	return _counter;
	}
	public int getTurns() {
		return _turnCounter;
	}
	public void setTurns(int _turns) {
		this._turnCounter = _turns;
	}
	public String whoseTurn() {
		if(_player1.getTurn()) {
			return "Red Turn";
		}
		if(_player2.getTurn()) {
			return "Blue Turn";
		}
		return "Game is Over";
	}
	public String getClue() {
		return _clue;
	}
	public Person getPlayer1() {
		return _player1;
	}
	public Person getPlayer2() {
		return _player2;
	}
	public boolean IfPlayer1Turn() {
		return _player1.getTurn();
	}
	public boolean IfPlayer2Turn() {
		return _player2.getTurn();
	}
	public ArrayList<String> getCodenames(){
		return _codenames;
	}
	public ArrayList<Location> getLocations(){
		return _locales;
	}
	public ArrayList<String> getAllCodenames(){
		return _allCodenames;
	}
	public Person getWinner() {
		return winner;
	}
	public Location[][] getBoard(){
		return _board;
	}
	public int getModcount() {
		return modcount;
	}
	
}
