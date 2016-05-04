package view;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Database;

public class ThumbnailsView extends JLayeredPane {

	/**
	 * 
	 */
	private Database model;
	private static final long serialVersionUID = 1L;
	private final int NUMBER_OF_GAMES;
	private GridBagConstraints layoutConst;
	private ArrayList<JButton> thumbnailList;
	File[] list;
	boolean transparent;
	InfoView info;

	public ThumbnailsView(){
		super();
		
		transparent = false;
		//This is going to change when we have a game library class (number of games must be in a single place)
		NUMBER_OF_GAMES = 3;
		
		setLayout(new GridBagLayout());
		
		//get the Assets folder
		String directory = System.getProperty("user.dir") + File.separator + "Assets" + File.separator;
		File folder = new File(directory);
		
		//get all images from file make sure the image correspond to the game ID
		list = folder.listFiles(new FilenameFilter() {
			@Override
	        public boolean accept(File dir, String name) {
	            return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
	            		name.endsWith(".png") || name.endsWith(".gif");
			}
		});
		

		thumbnailList = new ArrayList<JButton>();
		
		for(int i = 0; i < list.length; i++) {
			ImageIcon icon = new ImageIcon(list[i].getAbsolutePath());
			Image img = icon.getImage().getScaledInstance(150, 150, Image.SCALE_FAST );
			icon.setImage(img);
			JButton button  = new JButton(icon);
			thumbnailList.add(button);
		}
		
		for(int i = 0; i < thumbnailList.size(); i++) {
			thumbnailList.get(i).setOpaque(false);
			thumbnailList.get(i).setOpaque(false);
			thumbnailList.get(i).setOpaque(false);
			ShadowBorder border = new ShadowBorder();
			thumbnailList.get(i).setBorder(border);
		}
		
		//each row has 3 thumbnails
		int row = 0;
		int column = 0;
		for(int i = 0; i < thumbnailList.size(); i++) {
			if(i % 2 == 0) {
				row ++;
				column = 0;
			}
			
			layoutConst = new GridBagConstraints();   //removes all of the constrains given so far
			
			//adding position constrains
			layoutConst.gridx = column;
			layoutConst.gridy = row;
			
			// 10 pixels of padding around component
			layoutConst.insets = new Insets(20, 20, 20, 20);
			//adds thumbnails to panel
			add(thumbnailList.get(i), layoutConst);
			column ++;
		}
		
	}

	
	/*public void paintComponent (Graphics g)
    { 
		if(transparent == true) {
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f)); // become transparent
	        super.paintComponent(g);
		}
		else {
			((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1.0f)); // become transparent
			super.paintComponent(g);
		}
     } */
	
	public void setModel(Database model) {
		this.model = model;
	}
	
	public void registerSelectedGameListener(ActionListener selectedGameListener, int index) {
		thumbnailList.get(index).addActionListener(selectedGameListener);
	}
	

}
