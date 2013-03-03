package com.bigstick.nlp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Launcher {
	public static void main( String[] args) {
		JFrame frame = new JFrame("BigStick NLP");
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add( new JLabel("we're running!"));
		panel.setPreferredSize(new Dimension(200,100));
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation( screenSize.width/2, screenSize.height/2);
		frame.setVisible(true);
	}
}
