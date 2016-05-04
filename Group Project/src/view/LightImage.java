package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class LightImage extends JPanel{
	boolean lineClicked = false;
	int index = 0;
	JLabel[] pins = new JLabel[400];
	ImageIcon[] pinColors = new ImageIcon[9];
	ImageIcon blankPins = new ImageIcon();
	ImageIcon[] artPics = new ImageIcon[10];
	JLabel[] artLabels = new JLabel[10];
	JPanel[] picPanels = new JPanel[11];
	String[] descriptions = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
	CardLayout layout = new CardLayout();
	JPanel eastPanelHolder = new JPanel(layout);
	
	public LightImage(){
		//make color icons
		for(int i = 0; i < 9; i++){
			pinColors[i] = new ImageIcon(System.getProperty("user.dir") + File.separator +"pins"+File.separator +"pic" + (i) + ".png");
		}
		
		//make empty screen
		picPanels[0] = new JPanel();
		picPanels[0].setLayout(new GridLayout(20,20));

		for(int i = 0; i < 400; i++){
			pins[i] = new JLabel("");
			pins[i].setBackground(Color.black);
			pins[i].setIcon(pinColors[index]);
			picPanels[0].add(pins[i]);
		}

		for(int i = 0; i < 400; i++){
			pins[i].addMouseListener(new ActionPerformed());
		}

		/*make other 10 pre-made art screens by putting labels
		on panels*/
		for(int i = 1; i < 11; i++){
			picPanels[i] = new JPanel();
			picPanels[i].setPreferredSize(new Dimension(300,300));
			artLabels[i-1] = new JLabel();
			
			ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + File.separator +"pins"+File.separator +"art" + (i-1) + ".png");
			icon.setImage( icon.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH ) );
			
			artLabels[i-1].setIcon(icon);
			picPanels[i].add(artLabels[i-1]);
		}
		
		//add 11 panels to card layout
		for(int i = 0; i<11; i++){
			eastPanelHolder.add(picPanels[i], descriptions[i]);
		}
		this.add(eastPanelHolder);	
	}

	//setters
	public void setColor(int color){
		index = color;
 	}
	
	public void setLineClicked(boolean click){
		lineClicked = click;
 	}
	
	public void setView(int cardValue){
		layout.show(eastPanelHolder, descriptions[cardValue]);
 	}
	
	public void reset(){
		for(int i = 0; i < 400; i++){
			pins[i].setIcon(pinColors[0]);
		}
 	}
	
	//Listeners
	private class ActionPerformed implements MouseListener{
		public void mouseEntered(MouseEvent e){
			if(lineClicked == true){
				((JLabel)e.getSource()).setIcon(pinColors[index]);
			}
		}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){
			((JLabel)e.getSource()).setIcon(pinColors[index]);
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
	}
}