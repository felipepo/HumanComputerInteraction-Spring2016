package view;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class LightBright extends JPanel {
	LightImage light;
	JButton[] colorOptions = new JButton[9];
	LineBorder clickedBorder = new LineBorder(Color.blue, 5, true);
	JLabel[]  artPics = new JLabel[10];
	
	public LightBright(){
		setBackground(Color.white);
		light = new LightImage();
		
		//Main title
		JPanel panelN = new JPanel();
		panelN.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("LITE-BRITE", JLabel.CENTER);	
		panelN.add(title);
		title.setFont(new Font("SanSerif",Font.BOLD,36));
		panelN.setBackground(Color.white);
		JLabel title1 = new JLabel("Pick a Color and Click the Board" , JLabel.CENTER);
		title1.setFont(new Font("SanSerif",Font.BOLD,16));
		panelN.add(title1);
		
		//East panel main panel
		JPanel panelE1 = new JPanel();
		panelE1.setLayout(new BoxLayout( panelE1, BoxLayout.Y_AXIS ) );
		panelE1.setBackground(Color.white);

		JPanel layoutFixer1 = new JPanel();
		layoutFixer1.setLayout(new GridLayout(6,1));
		layoutFixer1.setBackground(Color.white);
		
		JButton newGame = new JButton("Clear");
		layoutFixer1.add(newGame);
		
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				hideBorder();
				colorOptions[0].setBorder(new LineBorder(Color.blue, 5, true));
				light.setColor(0);
				light.reset();
			}
		});
		
		layoutFixer1.add(new JLabel(" "));
		JLabel pointLabel = new JLabel(" Draw ");
		pointLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		layoutFixer1.add(pointLabel);
		
		JPanel lineSelectPanel = new JPanel();
		lineSelectPanel.setLayout(new GridLayout(1,2));
		
		JRadioButton point = new JRadioButton("Point");
		JRadioButton line = new JRadioButton("Line");
		point.setSelected(true);
		
		ButtonGroup lineSelectGroup = new ButtonGroup();
		lineSelectGroup.add(point);
		lineSelectGroup.add(line);
		
		lineSelectPanel.add(point);
		lineSelectPanel.add(line);
		
		layoutFixer1.add(lineSelectPanel);
		
		point.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				light.setLineClicked(false);
			}
		});
		line.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				light.setLineClicked(true);
			}
		});
		
		layoutFixer1.add(new JLabel(" "));
		JLabel colorsLabel = new JLabel(" Select a Color ");
		colorsLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		layoutFixer1.add(colorsLabel);
		panelE1.add(layoutFixer1);
		
		//color select panel of 9 colors
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new GridLayout(3,3));
		
		for(int i = 0; i < 9; i++){
			colorOptions[i] = new JButton(new ImageIcon(System.getProperty("user.dir") + File.separator +"pins"+File.separator +"pic" + (i+9) + ".png"));
			colorOptions[i].setBorder(new LineBorder(Color.black, 1, true));	
			colorOptions[i].addActionListener(new ActionPerformed());
			colorPanel.add(colorOptions[i]);
		}	
		
		colorOptions[0].setBorder(new LineBorder(Color.blue, 5, true));
		panelE1.add(colorPanel);
		
		this.setLayout(new BorderLayout(5, 10));
		
		JPanel layoutFixer2 = new JPanel();
		layoutFixer2.setLayout(new GridLayout(2,1));
		layoutFixer2.setBackground(Color.white);
		layoutFixer2.add(new JLabel(" "));
		JButton gallary = new JButton("Art Ideas");
		layoutFixer2.add(gallary);		
		panelE1.add(layoutFixer2);
		
		//east panel 2 art gallery
		JPanel panelE2 = new JPanel();
		panelE2.setLayout(new GridLayout(7,2));
		panelE2.setBackground(Color.white);

		JLabel artLabel1 = new JLabel(" Ideas ");
		artLabel1.setFont(new Font("SanSerif",Font.BOLD,26));
						
		panelE2.add(artLabel1);
		panelE2.add(new JLabel(" "));
		
		for(int i = 0; i < 10; i++){
			artPics[i] = new JLabel("");
			
			panelE2.add(artPics[i]);
			artPics[i].addMouseListener(new LabelClickPerformed());
			
			ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + File.separator +"pins"+File.separator +"art" + (i) + ".png");
			icon.setImage( icon.getImage().getScaledInstance(
					80, 80, Image.SCALE_SMOOTH ) );
			
			artPics[i].setIcon(icon);
		}
		
		JButton returnHome = new JButton("Return");
		panelE2.add(returnHome);
				
		//panel holder for east panel
		CardLayout layout = new CardLayout();
		JPanel eastPanelHolder = new JPanel(layout);
		eastPanelHolder.add(panelE1, "1");
		eastPanelHolder.add(panelE2, "2");
		
		//listeners
		gallary.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				layout.show(eastPanelHolder,"2");
			}
		});
		returnHome.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				layout.show(eastPanelHolder,"1");
				light.setView(0);
			}
		});
		
		this.add(panelN, BorderLayout.NORTH);
		this.add(light, BorderLayout.CENTER);
		this.add(eastPanelHolder, BorderLayout.EAST);
	}
	
	//Listeners
	private class LabelClickPerformed implements MouseListener{
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseClicked(MouseEvent e){
			if(	e.getSource().equals(artPics[0])){
				light.setView(1);
			}
			if(	e.getSource().equals(artPics[1])){
				light.setView(2);
			}
			if(	e.getSource().equals(artPics[2])){
				light.setView(3);
			}
			if(	e.getSource().equals(artPics[3])){
				light.setView(4);
			}
			if(	e.getSource().equals(artPics[4])){
				light.setView(5);
			}
			if(	e.getSource().equals(artPics[5])){
				light.setView(6);
			}
			if(	e.getSource().equals(artPics[6])){
				light.setView(7);
			}
			if(	e.getSource().equals(artPics[7])){
				light.setView(8);
			}
			if(	e.getSource().equals(artPics[8])){
				light.setView(9);
			}
			if(	e.getSource().equals(artPics[9])){
				light.setView(10);
			}
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
	}
	private class ActionPerformed implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			hideBorder();
			
			if(	e.getSource().equals(colorOptions[0])){
				colorOptions[0].setBorder(clickedBorder);
				light.setColor(0);
			}
			if(	e.getSource().equals(colorOptions[1])){
				colorOptions[1].setBorder(clickedBorder);
				light.setColor(1);
			}
			if(	e.getSource().equals(colorOptions[2])){
				colorOptions[2].setBorder(clickedBorder);
				light.setColor(2);
			}
			if(	e.getSource().equals(colorOptions[3])){
				colorOptions[3].setBorder(clickedBorder);
				light.setColor(3);
			}
			if(	e.getSource().equals(colorOptions[4])){
				colorOptions[4].setBorder(clickedBorder);
				light.setColor(4);
			}
			if(	e.getSource().equals(colorOptions[5])){
				colorOptions[5].setBorder(clickedBorder);
				light.setColor(5);
			}
			if(	e.getSource().equals(colorOptions[6])){
				colorOptions[6].setBorder(clickedBorder);
				light.setColor(6);
			}
			if(	e.getSource().equals(colorOptions[7])){
				colorOptions[7].setBorder(clickedBorder);
				light.setColor(7);
			}
			if(	e.getSource().equals(colorOptions[8])){
				colorOptions[8].setBorder(clickedBorder);
				light.setColor(8);
			}
		}
	}
	//changes border of selected color
	public void hideBorder(){
		for(int i = 0; i < 9; i++){
			colorOptions[i].setBorder(new LineBorder(Color.black, 1, true));
		}
	}
}