package ActionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.buffalo.cse116.GUI;

public class menuStartListener implements ActionListener {
	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		GUI g = new GUI("C:\\Users\\Jason Sandoval\\Downloads\\GameWords.txt");
		g.update();		
	}

}
