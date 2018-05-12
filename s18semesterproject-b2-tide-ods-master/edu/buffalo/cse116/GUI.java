package edu.buffalo.cse116;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import ActionListeners.locationSelectionHandler;
import ActionListeners.menuStartListener;

public class GUI {

	private static Board currentGame;
	private static JFrame mainFrame;
	private JPanel menuPanel;
	private JMenuBar menuBar;
	private JMenu File;
	private JMenuItem startCommand;
	private JMenuItem exitCommand;
	private JPanel locationPanel;
	private JPanel[] locations;
	private JButton[] locationButtons;
	private static JLabel[] codenameLabels;
	private static JLabel[] assignmentLabels;
	private JButton passButton;
	
	private static JOptionPane RedStartNotification;
	private JOptionPane BlueStartNotification;
	private static JOptionPane RedSpymasterCode;
	private static JOptionPane RedSpymasterCount;
	private JOptionPane BlueSpymasterCode;
	private JOptionPane BlueSpymasterCount;
	
	private static JLabel clueLabel;
	private static JLabel countLabel;
	private static JLabel turnLabel;
	private static JPanel dataPanel;
	
	public GUI(String filename) {
		currentGame = new Board(filename);
		mainFrame = new JFrame("Codenames!");
		mainFrame.setSize(1600, 750);
		menuPanel = new JPanel();
		mainFrame.setLayout(new BorderLayout());
		
		startCommand = new JMenuItem("Start a new game of Codenames!");
		exitCommand = new JMenuItem("Exit the game");
		startCommand.addActionListener(new menuStartListener());
		exitCommand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.dispose();
			}			
		});
		File = new JMenu("File");
		File.add(startCommand);
		File.add(exitCommand);
		menuBar = new JMenuBar();
		menuBar.add(File);
		menuPanel.add(menuBar);
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		menuPanel.setBackground(Color.LIGHT_GRAY);
		mainFrame.add(menuPanel, BorderLayout.NORTH);
		
		dataPanel = new JPanel();
		clueLabel = new JLabel();
		countLabel = new JLabel();
		turnLabel = new JLabel();
		turnLabel.setFont(new Font("gah", Font.BOLD, 16));
		clueLabel.setFont(new Font("olg", Font.BOLD, 16));
		countLabel.setFont(new Font("cholg", Font.BOLD, 16));
		dataPanel.add(turnLabel);
		dataPanel.add(clueLabel);
		dataPanel.add(countLabel);
		mainFrame.add(dataPanel, BorderLayout.SOUTH);
		
		currentGame.start();
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
			locationButtons[i].addActionListener(new locationSelectionHandler());
			locationButtons[i].setAlignmentX((float) 0.5);
			x.add(codenameLabels[i]);
			x.add(locationButtons[i]);
			x.add(assignmentLabels[i]);
			x.setLayout(new BoxLayout(x, BoxLayout.Y_AXIS));
			x.setBackground(Color.BLACK);
			locationPanel.add(x);
		}
		locationPanel.setBackground(Color.WHITE);
		mainFrame.add(locationPanel, BorderLayout.CENTER);
				
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void update() {
		if(currentGame.IfPlayer1Turn() && currentGame.getModcount()==0) {
			RedStartNotification = new JOptionPane();
			RedStartNotification.showMessageDialog(mainFrame, "It is now the Red Teams' Turn");
			for(int i=0; i<25; i++) {
				codenameLabels[i].setVisible(true);
				assignmentLabels[i].setVisible(true);
			}
			dataPanel.setBackground(Color.red);
			turnLabel.setText("*Red Teams Turn*");
			RedSpymasterCode = new JOptionPane();
			RedSpymasterCount = new JOptionPane();
			String clue = (String) RedSpymasterCode.showInputDialog(mainFrame, "Red Spymaster, please enter a clue.", "Enter Clue", JOptionPane.INFORMATION_MESSAGE, null, null, "Type clue here");
			String count = (String) RedSpymasterCount.showInputDialog(mainFrame, "Red Spymaster, please enter a number", "Enter Count", JOptionPane.INFORMATION_MESSAGE, null, null, "Type number here");
			checkInputRed(clue, count);
		}
		if(currentGame.IfPlayer2Turn() && currentGame.getModcount()==0) {
			
		}
		if(currentGame.IfPlayer1Turn() && currentGame.getModcount()>0) {
			if(currentGame.getTurns()==0) {
				
			}
			else {
				
			}
		}
		if(currentGame.IfPlayer2Turn() && currentGame.getModcount()>0) {
			if(currentGame.getTurns()==0) {
				
			}
			else {
				
			}
		}
		if(currentGame.getWinner() != null) {
			
		}
	}
	
	public static void checkInputRed(String clue, String count) {
		try {
			int x = Integer.parseInt(count);
			if(x>0) {
				clueLabel.setText(" Clue: " + "\""+ clue + "\"");
				countLabel.setText(" Turns Left: " + count);
				currentGame.startTurnP1(clue, x);
			}
			else {
				String newCount = (String) RedSpymasterCount.showInputDialog(mainFrame, "Try again, please enter a valid number", "Enter Count", JOptionPane.INFORMATION_MESSAGE, null, null, "Type number here");
				checkInputRed(clue, newCount);
			}
		}catch(NumberFormatException e) {
			String nouCount = (String) RedSpymasterCount.showInputDialog(mainFrame, "Try again, please enter a valid number", "Enter Count", JOptionPane.INFORMATION_MESSAGE, null, null, "Type number here");
			checkInputRed(clue, nouCount);
		}
	}
}
