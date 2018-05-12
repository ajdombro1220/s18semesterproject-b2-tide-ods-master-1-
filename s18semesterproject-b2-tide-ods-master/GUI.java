package edu.buffalo.cse116;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
/**
 * Class that will display our GUI for players to interact with the game.
 * @author 
 *
 */
public class GUI {
/**
 * Various Components needed to display information as well as add functionality.
 */
	private static Board currentGame;
	
	private static JFrame mainFrame;
	
	private static JPanel topPanel;
	
	private static JPanel menuPanel;
	private JMenuBar menuBar;
	private JMenu File;
	private JMenuItem start2PCommand;
	private JMenuItem start3PCommand;
	private JMenuItem exitCommand;
	private JMenuItem easterEgg;
	private JMenu Info;
	private JMenuItem howToPlay;
	private JMenuItem credits;
	
	private static JPanel countPanel;
	private static JLabel redCount;
	private static JLabel blueCount;
	private static JLabel greenCount;
	
	private static JPanel locationPanel;
	private static Location[] locations;
	private static JButton[] locationButtons;
	private static JLabel[] codenameLabels;
	private static JLabel[] assignmentLabels;
	
	private static JButton revealButton;
	private static JButton passButton;	
	private static JLabel clueLabel;
	private static JLabel countLabel;
	private static JLabel turnLabel;	
	private static JTextField inputField;
	private static JComboBox<Integer> inputCount;
	private static JButton inputButton;
	private static JPanel inputPanel;	
	private static Integer[] countChoices = {1,2,3,4,5,6,7,8,9};
	
	/**
	 * The constructor for this class. When this is run, it displays the starting screen before any interaction occurs.
	 * @param filename
	 */
	
	public GUI(String filename) {
		mainFrame = new JFrame("Codenames!");
		mainFrame.setSize(1600, 750);
		menuPanel = new JPanel();
		mainFrame.setLayout(new BorderLayout());
		
		topPanel = new JPanel();		
		start2PCommand = new JMenuItem("Start Codenames!: Two-Team");
		start3PCommand = new JMenuItem("Start Codenames!: Three-Team");
		exitCommand = new JMenuItem("EXIT");
		easterEgg = new JMenuItem("                ");
		start2PCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentGame = new Board(filename);
				GUI.setUp2PGame();
			}
		});
		start3PCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentGame = new Board(filename);
				GUI.setUp3PGame();
			}
		});
		exitCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}			
		});
		easterEgg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentGame.easterEgg();
			}
		});
		howToPlay = new JMenuItem("How to play Codenames");
		credits = new JMenuItem("Credits");
		howToPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.openHowToPlay("https://en.wikipedia.org/wiki/Codenames_(board_game)#Rules");
			}
		});
		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GUI.openCredits("C:\\Users\\Jason Sandoval\\Documents\\projectCredits.txt");
			}
		});
		Info = new JMenu("Info");
		Info.add(howToPlay);
		Info.add(credits);
		File = new JMenu("File");
		File.add(start2PCommand);
		File.add(start3PCommand);
		File.add(exitCommand);
		File.add(easterEgg);
		menuBar = new JMenuBar();
		menuBar.add(File);
		menuBar.add(Info);
		menuPanel.add(menuBar);
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.add(menuPanel);
		mainFrame.add(topPanel, BorderLayout.NORTH);
		
		clueLabel = new JLabel(" Clue:    ");
		countLabel = new JLabel(" Count:    ");
		turnLabel = new JLabel("Start a new game!");
		turnLabel.setFont(new Font("gah", Font.BOLD, 16));
		clueLabel.setFont(new Font("olg", Font.BOLD, 16));
		countLabel.setFont(new Font("cholg", Font.BOLD, 16));
		turnLabel.setForeground(Color.WHITE);
		clueLabel.setForeground(Color.WHITE);
		countLabel.setForeground(Color.WHITE);
		revealButton = new JButton("Reveal");
		revealButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				for(int i=0; i<assignmentLabels.length; i++) {
					assignmentLabels[i].setVisible(true);
				}
			}
		});
		revealButton.setEnabled(false);
		inputPanel = new JPanel();
		inputField = new JTextField("Input clue here", 20);		
		inputField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				inputField.selectAll();
			}
			@Override
			public void focusLost(FocusEvent arg0) {	
			}			
		});
		inputField.setEditable(true);		
		inputCount = new JComboBox<Integer>(countChoices);
		inputButton = new JButton("Enter");
		passButton = new JButton("Pass");
		inputButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String clue = inputField.getText();
				clue = clue.trim();
				int count = (int)inputCount.getSelectedItem();
				String[] checkForMultiWords = clue.split(" ");
				if(checkForMultiWords.length>1) {
					JOptionPane.showMessageDialog(mainFrame, "Please enter a proper word", "Alert", JOptionPane.WARNING_MESSAGE);
				}
				else if(clue.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "Please enter a proper word", "Alert", JOptionPane.WARNING_MESSAGE);
				}
				else if(checkNumericInput(clue)) {
					JOptionPane.showMessageDialog(mainFrame, "Please enter a proper word", "Alert", JOptionPane.WARNING_MESSAGE);
				}
				else {
					clueLabel.setText(" Clue: " + "\""+clue.toUpperCase()+"\"");
					countLabel.setText(" Count: "+count);
					if(currentGame.IfPlayer1Turn()) {
						currentGame.startTurnP1(clue, count);
					}else if(currentGame.IfPlayer2Turn()) {
						currentGame.startTurnP2(clue, count);
					}else if(currentGame.IfPlayer3Turn()) {
						currentGame.startTurnP3(clue, count);
					}
				}
			}
		});
		passButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentGame.pass();
			}
		});
		inputField.setEnabled(false);
		inputCount.setEnabled(false);
		inputButton.setEnabled(false);
		passButton.setEnabled(false);
		inputPanel.add(revealButton);
		inputPanel.add(inputField);
		inputPanel.add(inputCount);
		inputPanel.add(inputButton);
		inputPanel.add(passButton);
		inputPanel.add(turnLabel);
		inputPanel.add(clueLabel);
		inputPanel.add(countLabel);
		inputPanel.setBackground(Color.MAGENTA);
		mainFrame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
				
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void setUp2PGame() {
		currentGame.start();
		countPanel = new JPanel();
		redCount = new JLabel();
		blueCount = new JLabel();
		redCount.setForeground(Color.red);
		blueCount.setForeground(Color.blue);
		redCount.setFont(new Font("", Font.BOLD, 16));
		blueCount.setFont(new Font("", Font.BOLD, 16));
		countPanel.add(redCount);
		countPanel.add(blueCount);
		countPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		countPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.add(countPanel);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(Color.LIGHT_GRAY);
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(0, 5, 5, 5));
		locations = new Location[25];
		codenameLabels = new JLabel[25];
		assignmentLabels = new JLabel[25];
		locationButtons = new JButton[25];
		for(int i=0; i<currentGame.getLocations().size(); i++) {
			Location x = currentGame.getLocations().get(i);
			locations[i] = x;
			codenameLabels[i] = new JLabel(x.getCard().getCodename());
			assignmentLabels[i] = new JLabel(x.getCard().getRole());
			codenameLabels[i].setFont(new Font("big", Font.ITALIC, 14));
			assignmentLabels[i].setFont(new Font("brig", Font.ITALIC, 14));
			codenameLabels[i].setForeground(Color.WHITE);
			assignmentLabels[i].setForeground(Color.WHITE);
			codenameLabels[i].setAlignmentX((float) 0.5);
			assignmentLabels[i].setAlignmentX((float) 0.5);
			codenameLabels[i].setVisible(false);
			assignmentLabels[i].setVisible(false);
			locationButtons[i] = new JButton("Select");
			locationButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					currentGame.pickCard(x.getCoordX(), x.getCoordY());
					Location[][] board = currentGame.getBoard();
					if(board[x.getCoordX()][x.getCoordY()].getCard().getRole().equals("Assassin")) {
						new AssassinPickSound();
					}
				}				
			});
			locationButtons[i].setAlignmentX((float) 0.5);
			locationButtons[i].setEnabled(false);
			x.add(codenameLabels[i]);
			x.add(locationButtons[i]);
			x.add(assignmentLabels[i]);
			x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
			x.setBackground(Color.BLACK);
			locationPanel.add(x);
		}
		locationPanel.setBackground(Color.WHITE);
		mainFrame.add(locationPanel, BorderLayout.CENTER);
		GUI.update2P();
	}
	
	public static void setUp3PGame() {
		currentGame.startThree();
		countPanel = new JPanel();
		redCount = new JLabel();
		blueCount = new JLabel();
		greenCount = new JLabel();
		redCount.setForeground(Color.red);
		blueCount.setForeground(Color.blue);
		greenCount.setForeground(Color.GREEN);
		redCount.setFont(new Font("", Font.BOLD, 16));
		blueCount.setFont(new Font("", Font.BOLD, 16));
		greenCount.setFont(new Font("", Font.BOLD, 16));
		countPanel.add(redCount);
		countPanel.add(blueCount);
		countPanel.add(greenCount);
		countPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		countPanel.setBackground(Color.LIGHT_GRAY);
		topPanel.add(countPanel);
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(Color.LIGHT_GRAY);
		locationPanel = new JPanel();
		locationPanel.setLayout(new GridLayout(0, 5, 5, 5));
		locations = new Location[25];
		codenameLabels = new JLabel[25];
		assignmentLabels = new JLabel[25];
		locationButtons = new JButton[25];
		for(int i=0; i<currentGame.getLocations().size(); i++) {
			Location x = currentGame.getLocations().get(i);
			locations[i] = x;
			codenameLabels[i] = new JLabel(x.getCard().getCodename());
			assignmentLabels[i] = new JLabel(x.getCard().getRole());
			codenameLabels[i].setFont(new Font("big", Font.ITALIC, 14));
			assignmentLabels[i].setFont(new Font("brig", Font.ITALIC, 14));
			codenameLabels[i].setForeground(Color.WHITE);
			assignmentLabels[i].setForeground(Color.WHITE);
			codenameLabels[i].setAlignmentX((float) 0.5);
			assignmentLabels[i].setAlignmentX((float) 0.5);
			codenameLabels[i].setVisible(false);
			assignmentLabels[i].setVisible(false);
			locationButtons[i] = new JButton("Select");
			locationButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Location[][] board = currentGame.getBoard();
					Location l = board[x.getCoordX()][x.getCoordY()];
					if(l.getCard().getRole().equals("Assassin")) {
						new AssassinPickSound();
						if(currentGame.getAss()>1) {
							if(currentGame.IfPlayer1Turn()) {
								JOptionPane.showMessageDialog(mainFrame, "Red Team is eliminated for picking the first Assassin!", "Eliminated", JOptionPane.ERROR_MESSAGE);
							}
							else if(currentGame.IfPlayer2Turn()) {
								JOptionPane.showMessageDialog(mainFrame, "Blue Team is eliminated for picking the first Assassin!", "Eliminated", JOptionPane.ERROR_MESSAGE);
							}
							else if(currentGame.IfPlayer3Turn()) {
								JOptionPane.showMessageDialog(mainFrame, "Green Team is eliminated for picking the first Assassin!", "Eliminated", JOptionPane.ERROR_MESSAGE);
							}
						}	
					}
					currentGame.pickCard(x.getCoordX(), x.getCoordY());
				}			
			});
			locationButtons[i].setAlignmentX((float) 0.5);
			locationButtons[i].setEnabled(false);
			x.add(codenameLabels[i]);
			x.add(locationButtons[i]);
			x.add(assignmentLabels[i]);
			x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
			x.setBackground(Color.BLACK);
			locationPanel.add(x);
		}
		locationPanel.setBackground(Color.WHITE);
		mainFrame.add(locationPanel, BorderLayout.CENTER);
		GUI.update3P();
	}
	
	/**
	 * Method that updates the components of the UI to display the game's current state to the player. Whenever an interaction is made and the game progresses, 
	 * this method should be called.
	 *
	 */
	public static void update2P() {
		redCount.setText("Red Agents Remaining: "+currentGame.getRed());
		blueCount.setText("Blue Agents Remaining: "+currentGame.getBlue());
		if(currentGame.IfPlayer1Turn() && currentGame.getModcount()==0 && currentGame.getWinner()==null) {
			for(int i=0; i<currentGame.getLocations().size(); i++) {
				codenameLabels[i].setVisible(true);
			}
			turnLabel.setText("*Red Spymaster's Turn*");
			revealButton.setEnabled(true);
			inputField.setEnabled(true);
			inputCount.setEnabled(true);
			inputButton.setEnabled(true);
			passButton.setEnabled(true);
			inputPanel.setBackground(Color.RED);
			JOptionPane.showMessageDialog(mainFrame, "Welcome to Codenames! Red Team goes first, it is now the Spymaster's turn to pick a clue and a count." + "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "First Turn", JOptionPane.INFORMATION_MESSAGE);		
		}
		else if(currentGame.IfPlayer2Turn() && currentGame.getModcount()==1 && currentGame.getWinner()==null) {
			turnLabel.setText("*Blue Spymaster's Turn*");
			clueLabel.setText(" Clue:    ");
			countLabel.setText(" Count:    ");
			inputPanel.setBackground(Color.BLUE);
			JOptionPane.showMessageDialog(mainFrame, "Red spymaster forfeited the first turn! Blue Spymaster may now choose a clue and count.", "First Turn", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(currentGame.IfPlayer1Turn() && currentGame.getModcount()>0 && currentGame.getWinner()==null) {
			if(currentGame.getTurns()==0) {
				turnLabel.setText("*Red Spymaster's Turn*");
				clueLabel.setText(" Clue:    ");
				countLabel.setText(" Count:    ");
				inputField.setEnabled(true);
				revealButton.setEnabled(true);
				inputCount.setEnabled(true);
				inputButton.setEnabled(true);
				passButton.setEnabled(true);
				inputPanel.setBackground(Color.RED);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					locationButtons[i].setEnabled(false);
					if(locations[i].getCard().isRevealed()) {
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Its the RED spymaster's turn!"+ "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "Spymaster", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				turnLabel.setText("*Red Teams' Turn*");
				countLabel.setText(" Count: "+currentGame.getTurns());
				revealButton.setEnabled(false);
				inputField.setEnabled(false);
				inputCount.setEnabled(false);
				inputButton.setEnabled(false);
				passButton.setEnabled(true);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					assignmentLabels[i].setVisible(false);
					locationButtons[i].setEnabled(true);
					if(locations[i].getCard().isRevealed()) {
						locationButtons[i].setEnabled(false);
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Red Team pick a Location!", "WOW", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(currentGame.IfPlayer2Turn() && currentGame.getModcount()>0 && currentGame.getWinner()==null) {
			if(currentGame.getTurns()==0) {
				turnLabel.setText("*Blue Spymaster's Turn*");
				clueLabel.setText(" Clue:    ");
				countLabel.setText(" Count:    ");
				revealButton.setEnabled(true);
				inputField.setEnabled(true);
				inputCount.setEnabled(true);
				inputButton.setEnabled(true);
				passButton.setEnabled(true);
				inputPanel.setBackground(Color.BLUE);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					locationButtons[i].setEnabled(false);
					if(locations[i].getCard().isRevealed()) {
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Its the BLUE spymaster's turn!"+ "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "Spymaster", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				turnLabel.setText("*Blue Teams' Turn*");
				countLabel.setText(" Count: "+currentGame.getTurns());
				revealButton.setEnabled(false);
				inputField.setEnabled(false);
				inputCount.setEnabled(false);
				inputButton.setEnabled(false);
				passButton.setEnabled(true);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					assignmentLabels[i].setVisible(false);
					locationButtons[i].setEnabled(true);
					if(locations[i].getCard().isRevealed()) {
						locationButtons[i].setEnabled(false);
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Blue Team pick a Location!", "WOW", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(currentGame.getWinner() != null) {
			inputField.setEnabled(false);
			inputCount.setEnabled(false);
			inputButton.setEnabled(false);
			passButton.setEnabled(false);
			if(currentGame.getWinner().equals(currentGame.getPlayer1())) {
				turnLabel.setText("All of Red Teams' Locations were picked!!!");
				inputPanel.setBackground(Color.RED);
				locationPanel.removeAll();
				JLabel winnerLabel = new JLabel("RED TEAM WINS!!!");
				winnerLabel.setFont(new Font("win", Font.BOLD, 64));
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setForeground(Color.magenta);
				JLabel winnerPic = new JLabel(new ImageIcon("C:\\Users\\Jason Sandoval\\Downloads\\hertzWin.jpg"));
				winnerPic.setHorizontalAlignment(JLabel.CENTER);
				JLabel hertzApproves = new JLabel("Hertz Approves");
				hertzApproves.setFont(new Font("hertz", Font.BOLD, 44));
				hertzApproves.setHorizontalAlignment(JLabel.CENTER);
				hertzApproves.setForeground(Color.MAGENTA);
				locationPanel.add(winnerLabel);
				locationPanel.add(winnerPic);
				locationPanel.add(hertzApproves);
				locationPanel.setLayout(new GridLayout(0,1));
				locationPanel.setBackground(Color.RED);
				if(currentGame.assPicked()) {
					turnLabel.setText("Blue picked the Assassin!!!");
				}
				JOptionPane.showMessageDialog(mainFrame, "Red Team WINS", "Champ", JOptionPane.INFORMATION_MESSAGE);
			}
			if(currentGame.getWinner().equals(currentGame.getPlayer2())){
				turnLabel.setText("All of Blue Teams' Locations were picked!!!");
				inputPanel.setBackground(Color.BLUE);
				locationPanel.removeAll();
				JLabel winnerLabel = new JLabel("BLUE TEAM WINS!!!");
				winnerLabel.setFont(new Font("win", Font.BOLD, 64));
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setForeground(Color.magenta);
				JLabel winnerPic = new JLabel(new ImageIcon("C:\\Users\\Jason Sandoval\\Downloads\\hertzWin.jpg"));
				winnerPic.setHorizontalAlignment(JLabel.CENTER);
				JLabel hertzApproves = new JLabel("Hertz Approves");
				hertzApproves.setFont(new Font("hertz", Font.BOLD, 44));
				hertzApproves.setHorizontalAlignment(JLabel.CENTER);
				hertzApproves.setForeground(Color.MAGENTA);
				locationPanel.add(winnerLabel);
				locationPanel.add(winnerPic);
				locationPanel.add(hertzApproves);
				locationPanel.setLayout(new GridLayout(0,1));
				locationPanel.setBackground(Color.BLUE);
				if(currentGame.assPicked()) {
					turnLabel.setText("Red picked the Assassin!!!");
				}
				JOptionPane.showMessageDialog(mainFrame, "Blue Team WINS", "Champ", JOptionPane.INFORMATION_MESSAGE);
		}	
		}			
	}
	
	public static void update3P() {
		redCount.setText("Red Agents Remaining: "+currentGame.getRed());
		blueCount.setText("Blue Agents Remaining: "+currentGame.getBlue());
		greenCount.setText("Green Agents Remaining: "+currentGame.getGreen());
		if(currentGame.IfPlayer1Turn() && currentGame.getModcount()==0 && currentGame.getWinner()==null) {
			for(int i=0; i<currentGame.getLocations().size(); i++) {
				codenameLabels[i].setVisible(true);
			}
			turnLabel.setText("*Red Spymaster's Turn*");
			revealButton.setEnabled(true);
			inputField.setEnabled(true);
			inputCount.setEnabled(true);
			inputButton.setEnabled(true);
			passButton.setEnabled(true);
			inputPanel.setBackground(Color.RED);
			JOptionPane.showMessageDialog(mainFrame, "Welcome to Codenames! Red Team goes first, it is now the Spymaster's turn to pick a clue and a count." + "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "First Turn", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(currentGame.IfPlayer2Turn() && currentGame.getModcount()==1 && currentGame.getWinner()==null) {
			turnLabel.setText("*Blue Spymaster's Turn*");
			clueLabel.setText(" Clue:    ");
			countLabel.setText(" Count:    ");
			inputPanel.setBackground(Color.BLUE);
			JOptionPane.showMessageDialog(mainFrame, "Red spymaster forfeited the first turn! Blue Spymaster may now choose a clue and count.", "First Turn", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(currentGame.IfPlayer3Turn() && currentGame.getModcount()==2 && currentGame.getWinner()==null) {
			turnLabel.setText("*Green Spymaster's Turn*");
			clueLabel.setText(" Clue:    ");
			countLabel.setText(" Count:    ");
			inputPanel.setBackground(Color.GREEN);
			JOptionPane.showMessageDialog(mainFrame, "Blue spymaster forfeited the first turn! Green Spymaster may now choose a clue and count.", "First Turn", JOptionPane.INFORMATION_MESSAGE);
		}
		else if(currentGame.IfPlayer1Turn() && currentGame.getModcount()>0 && currentGame.getWinner()==null) {
			if(currentGame.getTurns()==0) {
				turnLabel.setText("*Red Spymaster's Turn*");
				clueLabel.setText(" Clue:    ");
				countLabel.setText(" Count:    ");
				inputField.setEnabled(true);
				revealButton.setEnabled(true);
				inputCount.setEnabled(true);
				inputButton.setEnabled(true);
				passButton.setEnabled(true);
				inputPanel.setBackground(Color.RED);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					locationButtons[i].setEnabled(false);
					if(locations[i].getCard().isRevealed()) {
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Its the RED spymaster's turn!"+ "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "Spymaster", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				turnLabel.setText("*Red Teams' Turn*");
				countLabel.setText(" Count: "+currentGame.getTurns());
				revealButton.setEnabled(false);
				inputField.setEnabled(false);
				inputCount.setEnabled(false);
				inputButton.setEnabled(false);
				passButton.setEnabled(true);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					assignmentLabels[i].setVisible(false);
					locationButtons[i].setEnabled(true);
					if(locations[i].getCard().isRevealed()) {
						locationButtons[i].setEnabled(false);
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Red Team pick a Location!", "WOW", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(currentGame.IfPlayer2Turn() && currentGame.getModcount()>0 && currentGame.getWinner()==null) {
			if(currentGame.getTurns()==0) {
				turnLabel.setText("*Blue Spymaster's Turn*");
				clueLabel.setText(" Clue:    ");
				countLabel.setText(" Count:    ");
				revealButton.setEnabled(true);
				inputField.setEnabled(true);
				inputCount.setEnabled(true);
				inputButton.setEnabled(true);
				passButton.setEnabled(true);
				inputPanel.setBackground(Color.BLUE);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					locationButtons[i].setEnabled(false);
					if(locations[i].getCard().isRevealed()) {
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Its the BLUE spymaster's turn!"+ "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "Spymaster", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				turnLabel.setText("*Blue Teams' Turn*");
				countLabel.setText(" Count: "+currentGame.getTurns());
				revealButton.setEnabled(false);
				inputField.setEnabled(false);
				inputCount.setEnabled(false);
				inputButton.setEnabled(false);
				passButton.setEnabled(true);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					assignmentLabels[i].setVisible(false);
					locationButtons[i].setEnabled(true);
					if(locations[i].getCard().isRevealed()) {
						locationButtons[i].setEnabled(false);
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Blue Team pick a Location!", "WOW", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(currentGame.IfPlayer3Turn() && currentGame.getModcount()>0 && currentGame.getWinner()==null) {
			if(currentGame.getTurns()==0) {
				turnLabel.setText("*Green Spymaster's Turn*");
				clueLabel.setText(" Clue:    ");
				countLabel.setText(" Count:    ");
				revealButton.setEnabled(true);
				inputField.setEnabled(true);
				inputCount.setEnabled(true);
				inputButton.setEnabled(true);
				passButton.setEnabled(true);
				inputPanel.setBackground(Color.GREEN);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					locationButtons[i].setEnabled(false);
					if(locations[i].getCard().isRevealed()) {
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Its the GREEN spymaster's turn!"+ "\n" + "When the spymaster is ready, click the reveal button to see the assignments.", "Spymaster", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				turnLabel.setText("*Green Teams' Turn*");
				countLabel.setText(" Count: "+currentGame.getTurns());
				revealButton.setEnabled(false);
				inputField.setEnabled(false);
				inputCount.setEnabled(false);
				inputButton.setEnabled(false);
				passButton.setEnabled(true);
				for(int i=0; i<locations.length; i++) {
					codenameLabels[i].setVisible(true);
					assignmentLabels[i].setVisible(false);
					locationButtons[i].setEnabled(true);
					if(locations[i].getCard().isRevealed()) {
						locationButtons[i].setEnabled(false);
						codenameLabels[i].setVisible(false);
						assignmentLabels[i].setVisible(true);
						if(locations[i].getCard().getRole().equals("RedAgent")) {
							locations[i].setBackground(Color.RED);
						}
						else if(locations[i].getCard().getRole().equals("BlueAgent")) {
							locations[i].setBackground(Color.BLUE);
						}
						else if(locations[i].getCard().getRole().equals("GreenAgent")) {
							locations[i].setBackground(Color.GREEN);
						}
						else if(locations[i].getCard().getRole().equals("Assassin")) {
							locations[i].setBackground(Color.ORANGE);
						}
						else if(locations[i].getCard().getRole().equals("InnocentBystander")) {
							locations[i].setBackground(Color.DARK_GRAY);
						}
					}
				}
				JOptionPane.showMessageDialog(mainFrame, "Green Team pick a Location!", "WOW", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else if(currentGame.getWinner()!=null) {
			inputField.setEnabled(false);
			inputCount.setEnabled(false);
			inputButton.setEnabled(false);
			passButton.setEnabled(false);
			if(currentGame.getWinner().equals(currentGame.getPlayer1())) {
				turnLabel.setText("All of Red Teams' Locations were picked!!!");
				inputPanel.setBackground(Color.RED);
				locationPanel.removeAll();
				JLabel winnerLabel = new JLabel("RED TEAM WINS!!!");
				winnerLabel.setFont(new Font("win", Font.BOLD, 64));
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setForeground(Color.magenta);
				JLabel winnerPic = new JLabel(new ImageIcon("C:\\Users\\Jason Sandoval\\Downloads\\hertzWin.jpg"));
				winnerPic.setHorizontalAlignment(JLabel.CENTER);
				JLabel hertzApproves = new JLabel("Hertz Approves");
				hertzApproves.setFont(new Font("hertz", Font.BOLD, 44));
				hertzApproves.setHorizontalAlignment(JLabel.CENTER);
				hertzApproves.setForeground(Color.MAGENTA);
				locationPanel.add(winnerLabel);
				locationPanel.add(winnerPic);
				locationPanel.add(hertzApproves);
				locationPanel.setLayout(new GridLayout(0,1));
				locationPanel.setBackground(Color.RED);
				if(currentGame.assPicked()) {
					turnLabel.setText("The last Assassin was picked!!!");
				}
				JOptionPane.showMessageDialog(mainFrame, "Red Team WINS", "Champ", JOptionPane.INFORMATION_MESSAGE);
			}
			if(currentGame.getWinner().equals(currentGame.getPlayer2())) {
				turnLabel.setText("All of Blue Teams' Locations were picked!!!");
				inputPanel.setBackground(Color.BLUE);
				locationPanel.removeAll();
				JLabel winnerLabel = new JLabel("BLUE TEAM WINS!!!");
				winnerLabel.setFont(new Font("win", Font.BOLD, 64));
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setForeground(Color.magenta);
				JLabel winnerPic = new JLabel(new ImageIcon("C:\\Users\\Jason Sandoval\\Downloads\\hertzWin.jpg"));
				winnerPic.setHorizontalAlignment(JLabel.CENTER);
				JLabel hertzApproves = new JLabel("Hertz Approves");
				hertzApproves.setFont(new Font("hertz", Font.BOLD, 44));
				hertzApproves.setHorizontalAlignment(JLabel.CENTER);
				hertzApproves.setForeground(Color.MAGENTA);
				locationPanel.add(winnerLabel);
				locationPanel.add(winnerPic);
				locationPanel.add(hertzApproves);
				locationPanel.setLayout(new GridLayout(0,1));
				locationPanel.setBackground(Color.BLUE);
				if(currentGame.assPicked()) {
					turnLabel.setText("The last Assassin was picked!!!");
				}
				JOptionPane.showMessageDialog(mainFrame, "Blue Team WINS", "Champ", JOptionPane.INFORMATION_MESSAGE);
			}
			if(currentGame.getWinner().equals(currentGame.getPlayer3())){
				turnLabel.setText("All of Green Teams' Locations were picked!!!");
				inputPanel.setBackground(Color.GREEN);
				locationPanel.removeAll();
				JLabel winnerLabel = new JLabel("GREEN TEAM WINS!!!");
				winnerLabel.setFont(new Font("win", Font.BOLD, 64));
				winnerLabel.setHorizontalAlignment(JLabel.CENTER);
				winnerLabel.setForeground(Color.magenta);
				JLabel winnerPic = new JLabel(new ImageIcon("C:\\Users\\Jason Sandoval\\Downloads\\hertzWin.jpg"));
				winnerPic.setHorizontalAlignment(JLabel.CENTER);
				JLabel hertzApproves = new JLabel("Hertz Approves");
				hertzApproves.setFont(new Font("hertz", Font.BOLD, 44));
				hertzApproves.setHorizontalAlignment(JLabel.CENTER);
				hertzApproves.setForeground(Color.MAGENTA);
				locationPanel.add(winnerLabel);
				locationPanel.add(winnerPic);
				locationPanel.add(hertzApproves);
				locationPanel.setLayout(new GridLayout(0,1));
				locationPanel.setBackground(Color.GREEN);
				if(currentGame.assPicked()) {
					turnLabel.setText("The last Assassin was picked!!!");
				}
				JOptionPane.showMessageDialog(mainFrame, "Green Team WINS", "Champ", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public static void openHowToPlay(String file) {
		try {
			java.awt.Desktop d = java.awt.Desktop.getDesktop();
			
			java.net.URI url = new java.net.URI(file);
			d.browse(url);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void openCredits(String file) {
		JFrame frame = new JFrame("Credits");
		JPanel panel = new JPanel();
		try {
			for(String line : Files.readAllLines(Paths.get(file))) {
				JLabel label = new JLabel(line);
				label.setFont(new Font("basic", Font.PLAIN, 16));
				panel.add(label);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public boolean checkNumericInput(String input) {
		boolean numberInvolved = false;
		char[] letters = input.toCharArray();
		for(char c : letters) {
			if(inputCheckHelper(c)) {
				numberInvolved = true;
			}
		}
		return numberInvolved;
	}
	
	public boolean inputCheckHelper(char a) {
		String s = Character.toString(a);
		boolean isNumber = false;
		try {
			int numericInput = Integer.parseInt(s);
			isNumber = true;
		}catch(Exception e) {
			return isNumber;
		}
		return isNumber;
	}
}
	

