package view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import model.Database;

public class InfoView extends JLayeredPane {
	
	private GridBagConstraints layoutConst;
	JButton returnButton;
	JButton playButton;
	private int gameID;
	Database model;
	JLabel descriptionLabel;

	public InfoView(Integer ID) {
		gameID = ID;
		Database model = new Database();
		
		//setPreferredSize(new Dimension(300, 300));
		setLayout(new GridBagLayout());
		
		ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + File.separator + "Assets" + File.separator + ID.toString() + ".png");
		Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_FAST );
		icon.setImage(img);
		JLabel imgLabel = new JLabel();
		imgLabel.setIcon(icon);
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		//layoutConst.weightx = 3;
		layoutConst.gridheight = 2;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(imgLabel, layoutConst);
		
		descriptionLabel = new JLabel(model.getGameList().get(gameID).getDescription());
		JTextArea description = new JTextArea(15,30);
		description.setWrapStyleWord(true);
		description.setLineWrap(true);
		description.setEditable(false);
		JScrollPane descriptionPane = new JScrollPane(description);
		description.setText(model.getGameList().get(gameID).getDescription());
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 1;
		layoutConst.gridy = 0;
		layoutConst.gridwidth = 3;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(descriptionPane, layoutConst);
		
		//get the buttons folder
		String directory = System.getProperty("user.dir") + File.separator + "Assets" + File.separator + "Buttons" + File.separator;
		File folder = new File(directory);
		
		//icon = new ImageIcon(directory + File.separator + "return.png");
		//returnButton = new JButton(icon);
		returnButton = new JButton("Return");
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(returnButton, layoutConst);
		
		playButton = new JButton("Play Game");
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 2;
		layoutConst.gridy = 1;
		layoutConst.insets = new Insets(10, 10, 10, 10);
		add(playButton, layoutConst);
		
		
	}
	/*public void paintComponent (Graphics g) { 
        ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f)); // turn on opacity
        int x = 20;
        int y = 20;
        int width = getWidth() - 40;
        int height = getHeight() - 40;
	    g.setColor(Color.WHITE);
	    g.fillRect(x, y, width, height);
     } */
	
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	public int getGameID() {
		return gameID;
	}
	
	public void setModel(Database model) {
		this.model = model;
	}
	
	public void registerReturnToMainListener(ActionListener returnToMainListener) {
		returnButton.addActionListener(returnToMainListener);
	}
	
	public void registerPlayButtonListener(ActionListener playButtonListener) {
		playButton.addActionListener(playButtonListener);
	}

}
