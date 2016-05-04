package view;
import java.awt.Dimension;

import javax.swing.JFrame;

import model.Database;

public class GameLibFrame extends JFrame {
	
	
	Database model;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GameLibFrame() {
		super("Game Library");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 500);
		//setResizable(false);
		setVisible(true);
	}
	
}
