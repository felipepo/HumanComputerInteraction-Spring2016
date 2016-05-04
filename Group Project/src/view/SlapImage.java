package view;



import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings({ "serial", "restriction" })
public class SlapImage extends JPanel{
	JToggleButton pause;
	private ImageIcon[] loop = new ImageIcon[52];
	Timer cardSwitcher;
	Timer timer;
	JLabel timerLabel;
	JLabel scoreLabel;
	JLabel puzzelLabel;
	JPanel puzzelBoard = new JPanel();
	JPanel scoreBoard;
	MediaPlayer mediaPlayerGood = null;
	MediaPlayer mediaPlayerBad = null;
	
	private int cardImage = 0;
	int total = 0;
	int timeLimit = 60;
	boolean soundG = false;
	boolean soundB = false;
	int mode = 1;
	String song = "";
	String noise = "";
	int speed = 1300;
	boolean clickable = true;
	
	public SlapImage(String selection, int speed){	
		//initialize timers and card image
		this.addMouseListener(new MouseHandler());
		this.setLayout(new BorderLayout());
		
		timer = new Timer(1000, new TimerListener());
		timer.start();
		setSpeed(speed);
		cardStack(selection);
		
		//create score and timer panel in north panel
		JPanel pN = new JPanel();
		pN.setLayout(new GridLayout(1,3));
		pN.setBorder(BorderFactory.createEmptyBorder(10,30,0,30));
						
		scoreLabel = new JLabel("  Score: " + total);
		scoreLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		scoreLabel.setBackground(Color.white);
		scoreLabel.setOpaque(true);
		
		scoreLabel.setBorder(LineBorder.createBlackLineBorder());

		timerLabel = new JLabel("  Time: " + timeLimit);
		timerLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		timerLabel.setBackground(Color.white);
		timerLabel.setOpaque(true);
		timerLabel.setBorder(LineBorder.createBlackLineBorder());
		
		pN.add(scoreLabel);
		pN.add(new JLabel(" "));
		pN.add(timerLabel);
		
		//create pause button in south
		pause = new JToggleButton();
		pause.setText("Pause");
		
		pause.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pause.isSelected() == true){
					cardSwitcher.stop();
					timer.stop();
					pause.setText("Resume");
					clickable = false;
				}
				else{
					pause.setText("Pause");
					clickable = true;
					cardSwitcher.restart();
					if(mode == 1){
						timer.start();
					}
				}
			}
    	});				
		
		//add panels to main
		this.add(pN, BorderLayout.NORTH);
		this.add(pause, BorderLayout.SOUTH);
	}
	
	//setters
	public void cardStack(String selection){
		for(int cardImage = 0 ; cardImage < loop.length ; cardImage++){
			loop[cardImage] = new ImageIcon(selection + cardImage + ".png");
		}
 	}
	
	public void setSpeed(int speed){
		if(cardSwitcher != null){
			cardSwitcher.stop();
		}
 		cardSwitcher = new Timer(speed, new TimerCardListener());
		if(mode == 0 || timer.isRunning() == true){
			cardSwitcher.start();
		}
 	}	
	public void setMode(int m){
		mode = m;
 		if(m == 1){
			timeLimit = 60;
			timer.start();
			cardSwitcher.start();
		}
		else{
			if(timeLimit > 0){
				timer.stop();
			}
			timeLimit = 0;			
			timerLabel.setText("  Time: " + timeLimit);
			cardSwitcher.start();
		}
		clearTotal();
	}
	public void setGoodSound(int soundFile){
 		@SuppressWarnings("unused")
		JFXPanel fxPanel = new JFXPanel();
 		if(soundFile == 0){
 			mediaPlayerGood = null;
		}
 		else{
 			soundG = true;
 			if(soundFile == 1){
 				noise = "hit1.mp3";
 			}
 			else if(soundFile == 2){
 				noise = "hit2.mp3";
 			}
 			else if(soundFile == 3){
 				noise = "hit3.mp3";
 			}
 			Media song = new Media(new File(noise).toURI().toString());
 			mediaPlayerGood = new MediaPlayer(song);
 		}
   	}
 	public void setBadSound(int soundFile){
		@SuppressWarnings("unused")
		JFXPanel fxPanel2 = new JFXPanel();
 		if(soundFile == 0){
 			mediaPlayerBad = null;
		}
 		else{
 			soundB = true;
 			if(soundFile == 1){
 				noise = "miss1.mp3";
 			}
 			else if(soundFile == 2){
 				noise = "miss2.mp3";
 			}
 			else if(soundFile == 3){
 				noise = "miss3.mp3";
 			}
 			Media song = new Media(new File(noise).toURI().toString());
 			mediaPlayerBad = new MediaPlayer(song);
 		}
   	}
 	public void setClickable(boolean go){
		clickable = go; 
	}
	
	//check for hit and update total
 	private int checkForHit(){
		total += getCardValue();
		return total;
	}
	public int getCardValue(){
		if(cardImage < 4) {
			stopSound();
			if(mediaPlayerGood != null){
				mediaPlayerGood.play();
				mediaPlayerGood.seek( new Duration(0));
			}
			return 2;
		}
		else if(cardImage < 12){
			stopSound();
			if(mediaPlayerGood != null){
				mediaPlayerGood.play();
				mediaPlayerGood.seek( new Duration(0));
			}
			return 1;
		}
		else {
			stopSound();
			if(mediaPlayerBad != null){
				mediaPlayerBad.play();
				mediaPlayerBad.seek( new Duration(0));
			}
			return -2;
		}
 	}
	public void clearTotal(){
		total = 0;
		scoreLabel.setText("  Score: " + total);
	}
  	
 	//stops playing sound if another click is made
 	private void stopSound(){
 		if(mediaPlayerBad != null && mediaPlayerBad.getCurrentTime().toSeconds() > 0.0){
 			mediaPlayerBad.stop();
 		}
 		if(mediaPlayerGood != null && mediaPlayerGood.getCurrentTime().toSeconds() > 0.0){
 			mediaPlayerGood.stop();
 		}
 	}	
 
 	//this piece of code was from an old slapJack game
 	//redraws card image
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Dimension d = getSize();
		cardImage = (int)(Math.random()*loop.length);
		g.drawImage(loop[cardImage].getImage(), 150, 150,
			d.width/2, d.height/2, this);
 	}
	
	//listener classes
	private class TimerCardListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if( timeLimit > 0){
				timeLimit--;
			}
			else{
				timer.stop();
				cardSwitcher.stop();
				clickable = false;
			}
			timerLabel.setText("  Time: " + timeLimit);
		}
	}
 	private class MouseHandler extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			if(clickable == true){
				scoreLabel.setText("  Score: " + checkForHit());
			}
		}
	} 
}