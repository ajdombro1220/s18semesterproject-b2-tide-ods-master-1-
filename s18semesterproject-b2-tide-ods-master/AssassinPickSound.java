package edu.buffalo.cse116;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AssassinPickSound {
	public AssassinPickSound() {
		  JFrame frame = new JFrame("Why would you do that?");
	      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	      frame.setVisible(true);
	      frame.add(new JLabel(new ImageIcon("feelsbadman.png")));
	      frame.pack();
	      try {
	    	  File soundFile = new File("babycry.wav");
	    	  AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	    	  Clip clip = AudioSystem.getClip();
	    	  clip.open(audioIn);
	    	  clip.start();
	      } catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } catch (LineUnavailableException e) {
	         e.printStackTrace();
	      }
	   }	
}
