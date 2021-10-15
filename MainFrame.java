/*
 * Kal Young
 * 10/10/21
 * Object Oriented Software Development
 * Project 2: GUI Project w/ TweetCollection
 */

package project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
public class MainFrame {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Sentiment Analysis");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 500);
		MainPanel panel = new MainPanel();
		frame.getContentPane().add(panel);
		// Rewrites the file after the program is done executing
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				panel.doWrite();
			}
		});
		
		frame.pack();
		frame.setVisible(true);
	}
}

