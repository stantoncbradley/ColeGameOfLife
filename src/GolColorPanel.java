/***************************************************
 * Conway's Game of Life Cell Automation Game
 * 
 * Stanton Cole Bradley
 * stantoncbradley@gmail.com
 * (573) 270-1954
 * 
 * November 11, 2013
 * 
 * JPanel ColorPanel
 ***************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GolColorPanel extends JPanel {

	//********** GUI Controls *************
	
	private JButton resetButton, cycleButton, runButton;
	private JLabel generationsLived = new JLabel("Generations Lived: ");
	
	//********** Game variables ***********
	
	private int cycleCount = 0;
	
	private int numRows = 60;
	private int numCol = 60;
	private int sizeRow = 600 / numRows;
	private int sizeCol = 600 / numCol;
	
	private boolean oldCell[][] = new boolean[numCol][numRows];
	private boolean newCell[][] = new boolean[numCol][numRows];
	
	private boolean toggle;			//toggle access to changing cells
	private boolean toggleRun;		//toggle run button on/off
	
	public javax.swing.Timer timer;
	
	//********** Method constructor *********
	
	public GolColorPanel() {
		
		ActionListener listener = new lifeListener();
		addMouseListener(new PanelListener());
		
		setLayout(null);
		
		resetButton = new JButton("New Game");
		resetButton.setBounds(50, 625, 100, 25);
		resetButton.addActionListener(listener);
		
		cycleButton = new JButton("Cycle");
		cycleButton.setBounds(150, 625, 80, 25);
		cycleButton.addActionListener(listener);
		
		runButton = new JButton("Run");
		runButton.setBounds(230, 625, 80, 25);
		runButton.addActionListener(listener);
		
		generationsLived.setBounds(350, 625, 200, 25);
		
		add(resetButton);
		add(cycleButton);
		add(runButton);
		add(generationsLived);
		
		timer = new javax.swing.Timer(300, new MoveListener());

		newGame();
	}
	
	//********** Paint Component ****************
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCol; col++)
			{
				oldCell[col][row] = newCell[col][row];
				//make copy of array to read when cycling
				
				if (newCell[col][row] == true)
					g.setColor(Color.blue);
				else
					g.setColor(Color.white);
				g.fillRect(col * sizeCol, row * sizeRow, sizeCol, sizeRow);	
				//fill rectangle(x,y,width,height) blue/white depending on cell status
			}

		generationsLived.setText("Generations lived: " + cycleCount);
	}
	
	//************ Timer Component ***********
	
	private class MoveListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			lifeCycle();
		}
	}
	
	//************* Button Listener ***********
	
	private class lifeListener implements ActionListener {

		public void actionPerformed(ActionEvent event)
		{
			Object source = event.getSource();
			if (source == resetButton)
				newGame();
			
			if (source == cycleButton)
				lifeCycle();
			
			if (source == runButton)
			{
				if (toggleRun)
				{
					toggleRun = false;
					runButton.setText("Run");
					timer.stop();
				}
				else
				{
					toggleRun = true;
					runButton.setText("Stop");
					timer.start();
				}
			}
		}
	}
	
	//************** Panel Listener ***************
	
	private class PanelListener extends MouseAdapter {
		
		public void mousePressed(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			int col = x / sizeCol;
			int row = y / sizeRow;
			
			if (y < 600 && toggle)
			{
				newCell[col][row] = !oldCell[col][row];
				//click cell to toggle cell status
				repaint();
			}
		}
	}
	
	//************* New Game Method ******************
	
	private void newGame() {
		
		toggleRun = false;		//turn off timer
		runButton.setText("Run");
		timer.stop();
		
		toggle = true;		//enable board editing
		
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCol; col++)
				newCell[col][row] = false;		//reset cells to dead
		cycleCount = 0;
		repaint();
	}
	
	//************* Life Cycle Method ****************
	
	private void lifeCycle() {
		
		int neighborCount;
		boolean stale = true;		//determine if board is static
		toggle = false;
		
		for (int row = 0; row < numRows; row++)
			for (int col = 0; col < numCol; col++)
			{
				neighborCount = 0;
				if (col > 0)
				{
					if (row > 0 && oldCell[col-1][row-1])
						neighborCount++;
					if (oldCell[col-1][row])
						neighborCount++;
					if (row < (numRows-1) && oldCell[col-1][row+1])
						neighborCount++;
				}
				if (row > 0 && oldCell[col][row-1])
					neighborCount++;
				if (row < (numRows-1) && oldCell[col][row+1])
					neighborCount++;
				if (col < (numCol-1))
				{
					if (row > 0 && oldCell[col+1][row-1])
						neighborCount++;
					if (oldCell[col+1][row])
						neighborCount++;
					if (row < (numRows-1) && oldCell[col+1][row+1])
						neighborCount++;
				}
				if (neighborCount < 2 || neighborCount > 3)
					newCell[col][row] = false;
				if (neighborCount == 3)
					newCell[col][row] = true;
				if (newCell[col][row] != oldCell[col][row])
					stale = false;
			}
		
		if (stale)
			timer.stop();
		else
		{	cycleCount++;
			repaint();
		}
	}
}

