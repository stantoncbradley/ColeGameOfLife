/***************************************************
 * Conway's Game of Life Cell Automation Game
 * 
 * Stanton Cole Bradley
 * stantoncbradley@gmail.com
 * (573) 270-1954
 * 
 * November 11, 2013
 * 
 * GUI JFrame
 ***************************************************/

import javax.swing.*;
import java.awt.*;

public class GoLGUI {

	public static void main(String[] args) {
		
		JFrame theGUI = new JFrame();
		theGUI.setTitle("Conway's Game of Life");
		theGUI.setBounds(100,100, 600, 700);
		theGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GolColorPanel board = new GolColorPanel();
		Container pane = theGUI.getContentPane();
		pane.add(board);
		theGUI.setVisible(true);
	}
}