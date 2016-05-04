package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;

public class CardImage extends JPanel{
	private int numImages = 52;
	private ImageIcon[] loop = new ImageIcon[numImages];
	private int cardImage=0;
	JToggleButton pause;
	Timer time;
	JTextField scoreBox = new JTextField();
	int total=0;

	public CardImage(String selection, int speed){
  		this.addMouseListener(new MouseHandler());
  		setSpeed(speed);

		this.setLayout(new BorderLayout());

		cardStack(selection);

		JPanel pN = new JPanel();
		pN.setLayout(new FlowLayout());

		JLabel scoreLabel = new JLabel("Score ");
		scoreLabel.setFont(new Font("SanSerif",Font.BOLD,36));
		scoreBox.setPreferredSize(new Dimension(40,27));
		pN.add(scoreLabel);
		pN.add(scoreBox);

		JButton reset = new JButton();
		reset.setText("Reset Score");
		pN.add(reset);

		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				scoreBox.setText(String.format("  " + "%2d",clearTotal()));
			}
    	});

		pause = new JToggleButton();
		pause.setText("Pause");

		this.add(pN, BorderLayout.NORTH);
		this.add(pause, BorderLayout.SOUTH);

		pause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pause.isSelected() == true){
					time.stop();
					pause.setText("Resume");
				}
				else{
					time.start();
					pause.setText("Pause");
				}
			}
    	});
	}

	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Dimension d = getSize();
		cardImage = (int)(Math.random()*loop.length);
		g.drawImage(loop[cardImage].getImage(), 90, 90,
			(int)(d.width/2.5), (int)(d.height/2.5), this);
 	}

 	public int getCardValue(){
		if(cardImage < 4) {
			return 2;
		}
		else if(cardImage < 12){
			return 1;
		}
		else {
			return -2;
		}
 	}

 	public void cardStack(String selection){
		for(int cardImage=0;cardImage<loop.length;cardImage++){
			String url = selection + cardImage + ".png";
			loop[cardImage] = new ImageIcon(url);
		}
 	}

 	public void setSpeed(int speed){
		if(time != null){
			time.stop();
		}
		time = new Timer(speed, new TimerListener());
		time.start();
 	}

 	private int checkForHit(){
			total += getCardValue();
			return total;
	}

	private int clearTotal(){
		total = 0;
		return total;
	}

 	private class MouseHandler extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			scoreBox.setText(String.format("  " + "%2d",checkForHit()));
		}
	} //end of MouseHandler class
}