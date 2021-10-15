/*
 * Kal Young
 * 10/10/21
 * Object Oriented Software Development
 * Project 2: GUI Project w/ TweetCollection
 */

package project2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.*;

import sentimentanalysis.Tweet;
import sentimentanalysis.TweetCollection;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

public class MainPanel extends JPanel {

	private TweetCollection tc;
	private JComboBox dropDown;
	private JLabel lblTweetData;
	private JLabel lblGuess;
	private JLabel lblPolarityGuess;
	private JLabel lblPrediction;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JFrame frame;
	private JTextField textField_5;
	private int polGuess;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	protected Icon icon;
	private Tweet t;
	private String filename;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	// -----------------------------------------------------------------
	// Sets up the panel, including the timer for the animation.
	// -----------------------------------------------------------------

	public MainPanel() {
		
		// copy files from JAR to system
		try {
			ResourceUtils.copyToDisk("/sentimentanalysis/testProcessed.txt", true);
			ResourceUtils.copyToDisk("/sentimentanalysis/trainingProcessed.txt", true);
			ResourceUtils.copyToDisk("/project2/t2rsz_twitter-small-icon-29.jpg", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		filename = "./sentimentanalysis/testProcessed.txt";

		setForeground(Color.BLACK);
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(SystemColor.activeCaption);

		tc = new TweetCollection(filename, 1600);

		// Setting Dimensions

		setPreferredSize(new Dimension(800, 500));
		setLayout(null);

		// Scroll Panel

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(29, 77, 185, 328);
		add(scrollPane);

		// Text Area

		JTextArea textArea = new JTextArea();
		textArea.setBackground(Color.WHITE);
		textArea.setText(tc.toString());
		scrollPane.setViewportView(textArea);
		textArea.setForeground(Color.BLACK);

		// Top Label

		JLabel lblCollectionOfTweets = new JLabel("Collection of Tweets");
		lblCollectionOfTweets.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblCollectionOfTweets.setBounds(29, 39, 185, 30);
		add(lblCollectionOfTweets);

		// Big Label

		JLabel lblTweetPrediction = new JLabel("TWEET PREDICTION");
		lblTweetPrediction.setFont(new Font("Stencil", Font.ITALIC, 26));
		lblTweetPrediction.setBounds(416, 22, 303, 30);
		add(lblTweetPrediction);

		// Enter USRNM

		JLabel lblEnterUsername = new JLabel("Enter Username:");
		lblEnterUsername.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEnterUsername.setBounds(262, 76, 126, 13);
		add(lblEnterUsername);

		// Tweet Data

		JLabel lblTweetData = new JLabel("Tweet Data: ");
		lblTweetData.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTweetData.setBounds(224, 179, 566, 30);
		add(lblTweetData);

		// Text Field

		textField = new JTextField();
		textField.setBounds(392, 75, 303, 19);
		add(textField);
		textField.setColumns(10);

		// Button

		JButton btnSearchId = new JButton("SEARCH  ID");
		btnSearchId.setForeground(Color.BLACK);
		btnSearchId.setBackground(Color.WHITE);
		btnSearchId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Variable Declaration

				Set<Long> ids = tc.getTweetIdsByUser(textField.getText());
				String[] idtext = new String[ids.size()];
				int i = 0;

				// Iterate to find IDs

				for (Long long1 : ids) {
					idtext[i] = long1.toString();

					i++;
				}

				dropDown.setModel(new DefaultComboBoxModel(idtext));

				if (idtext.length > 0) {
					Tweet t = tc.getTweetById(Long.parseLong(idtext[0]));
					lblTweetData.setText("Tweet Data: " + t.toString());
				}
			}
		});
		btnSearchId.setBounds(453, 104, 185, 30);
		add(btnSearchId);

		// Combo Box

		dropDown = new JComboBox();
		dropDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String id = (String) dropDown.getSelectedItem();
				Tweet t = tc.getTweetById(Long.parseLong(id));

				lblTweetData.setText("Tweet Data: " + t.toString());
			}
		});

		dropDown.setBounds(477, 144, 138, 21);
		add(dropDown);

		// Image

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./project2/t2rsz_twitter-small-icon-29.jpg"));
		lblNewLabel.setBounds(729, 10, 53, 78);
		add(lblNewLabel);

		// ****** TWEET PREDICTION ******

		JLabel lblTweetPrediction_1 = new JLabel("Enter Text to Predict:");
		lblTweetPrediction_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblTweetPrediction_1.setBounds(262, 246, 150, 13);
		add(lblTweetPrediction_1);

		textField_5 = new JTextField();
		textField_5.setBounds(262, 269, 433, 19);
		add(textField_5);
		textField_5.setColumns(10);

		// Get Prediction Button

		JButton btnGetPrediction = new JButton("GET PREDICTION");
		btnGetPrediction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int polarity = 0;

				// Radio Buttons Selected

				if (radioButton.isSelected()) {
					radioButton.setSelected(true);

				}

				else if (radioButton_1.isSelected()) {
					radioButton_1.setSelected(true);
				}

				else if (radioButton_2.isSelected()) {
					radioButton_2.setSelected(true);
				}

				// For loop here
				for (long tid : tc.getAllTweetIds()) {
					Tweet tweet = tc.getTweetById(tid);
					if(tweet.getText().equals(textField_5.getText())) {
						t = tweet;
						break;
					}
				}

				// Display Correct Guess

				lblPrediction.setText("Prediction: " + t.toString());

				if (polarity == polGuess) {
					lblGuess.setText("Guess: Correct");
				} else {
					lblGuess.setText("Guess: Incorrect");
				}
			}
		});
		btnGetPrediction.setBounds(407, 352, 165, 30);
		add(btnGetPrediction);

		// Labels
		
		lblPolarityGuess = new JLabel("Polarity Guess:");
		lblPolarityGuess.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPolarityGuess.setBounds(262, 298, 103, 22);
		add(lblPolarityGuess);

		lblPrediction = new JLabel("Prediction: ?");
		lblPrediction.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrediction.setBounds(262, 392, 1001, 23);
		add(lblPrediction);
		
		lblGuess = new JLabel("Guess: ?");
		lblGuess.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGuess.setBounds(262, 416, 770, 19);
		add(lblGuess);

		// Polarity Box

		JTextPane txtpnNegative = new JTextPane();
		txtpnNegative.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnNegative.setText("0 = Negative\r\n2 = Neutral\r\n4 = Positive");
		txtpnNegative.setBounds(643, 301, 103, 59);
		add(txtpnNegative);

		// Radio Buttons

		radioButton = new JRadioButton("0");
		radioButton.setSelected(true);
		buttonGroup.add(radioButton);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				polGuess = 0;
			}
		});
		radioButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radioButton.setBounds(407, 303, 38, 21);
		add(radioButton);

		radioButton_1 = new JRadioButton("2");
		radioButton_1.setSelected(true);
		buttonGroup.add(radioButton_1);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				polGuess = 2;
			}
		});
		radioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radioButton_1.setBounds(470, 303, 38, 21);
		add(radioButton_1);

		radioButton_2 = new JRadioButton("4");
		radioButton_2.setSelected(true);
		buttonGroup.add(radioButton_2);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				polGuess = 4;
			}
		});
		radioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		radioButton_2.setBounds(534, 303, 38, 21);
		add(radioButton_2);

		// ****** MENU BAR ******

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 101, 22);
		add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		// Menu Item asking for selection of file

		JMenuItem mntmSelectFile = new JMenuItem("Select File");
		mntmSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Source:
				// https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html

				Object[] possibilities = { "testProcessed", "trainingProcessed" };
				String fileselect = (String) JOptionPane.showInputDialog(frame,
						"Select which file you would like to use: \n", "Customized Dialog", JOptionPane.PLAIN_MESSAGE,
						icon, possibilities, "testProcessed");

				if ((fileselect != null) && (fileselect.length() > 0)) {
					filename = fileselect;
					tc = new TweetCollection("./sentimentanalysis/" + filename + ".txt", 1600);
					textArea.setText(tc.toString());
				}
			}
		});
		mnFile.add(mntmSelectFile);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		// Menu Item asking for addition of Tweet

		JMenuItem mntmAddTweet = new JMenuItem("Add Tweet");
		mntmAddTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Menu Item asking for addition of Tweet

				/* This code is definitely wrong...
				 * 
				 * Tweet addedmsg;
				 * 
				 * String addtweet = (String)JOptionPane.showInputDialog(frame,
				 * "Enter a tweet to add to the collection:\n",
				 * "Customized Dialog",JOptionPane.PLAIN_MESSAGE);
				 * 
				 * if((addtweet != null) && (addtweet.length() > 0)) { Tweet t =
				 * tc.addTweet(addedmsg); }
				 */
				
				// Could maybe make a separate frame to ask for four user inputs and call
				// addtweet... I don't have time to make this sadly
			}
		});
		mnEdit.add(mntmAddTweet);

		// Menu Item asking for removal of Tweet

		JMenuItem mntmRemoveTweet = new JMenuItem("Remove Tweet");
		mntmRemoveTweet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Create a dialog box here that would ask for user input, separate input with
				// comma splice or four separate user inputs then call addtweet... also didn't
				// have time to make this :(
			}
		});
		mnEdit.add(mntmRemoveTweet);

	}

	// Rewrites the file

	public void doWrite() {
		tc.rewriteFile();
	}
}