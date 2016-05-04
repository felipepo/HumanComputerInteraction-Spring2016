package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javax.swing.Timer;
import javax.swing.border.*;

@SuppressWarnings({ "serial", "restriction" })
public class ManImage extends JPanel{
	JButton[] letterButton = new JButton[26];
	private JLabel hangLabel = new JLabel();
	private ImageIcon[] loop = new ImageIcon[9];
	Timer timer;
	JLabel timerLabel;
	JLabel scoreLabel;
	JLabel puzzelLabel;
	JPanel puzzelBoard = new JPanel();
	JPanel scoreBoard;
	MediaPlayer mediaPlayerGood = null;
	MediaPlayer mediaPlayerBad = null;
	
	StringBuilder solution = new StringBuilder("");
	StringBuilder dash = new StringBuilder("");
	
	int total = 0;
	int timeLimit = 0;
	int imageIndex = 0;
	int index = 0;
	String gender = "M";
	boolean soundG = false;
	boolean soundB = false;
	int mode = 1;
	String song = "";
	String noise = "";
	Puzzle[] puzzelArray;
	
	public ManImage(int speed){
		//initialize letters,timer, and hangMan image
		timer = new Timer(1000, new TimerListener());
		this.setLayout(new BorderLayout());
		
		for (int i = 0; i < 26; i++){
			ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + File.separator+ "letters" + File.separator + i + ".png");
			icon.setImage( icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH ) );
			letterButton[i] = new JButton(icon);
			letterButton[i].setBackground(Color.white);
			letterButton[i].setBorder(LineBorder.createBlackLineBorder());
		}

		for(int i = 0 ; i < loop.length ; i++){
			loop[i] = new ImageIcon("man1/pic" + gender + i + ".png");
		}
		
		//create score and timer panel in north panel
		JPanel pN = new JPanel();
		pN.setLayout(new GridLayout(2,1));
		
		scoreBoard = new JPanel();
		scoreBoard.setLayout(new GridLayout(1,2));
		scoreBoard.setBorder(BorderFactory.createEmptyBorder(10,30,0,30));
		
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
		
		scoreBoard.add(scoreLabel);
		scoreBoard.add(new JLabel(" "));
		scoreBoard.add(timerLabel);
		pN.add(scoreBoard);

		//creates puzzle label in north panel
		puzzelLabel = new JLabel();
		puzzelBoard.setLayout(new FlowLayout());
		puzzelLabel.setFont(new Font("SanSerif",Font.BOLD,46));
		puzzelBoard.add(puzzelLabel);

		//sets puzzle and initial level
		setSpeed(0);
		setMode(mode);
		
		pN.add(puzzelBoard);

		//creates button panel in south
		JPanel pS = new JPanel();
		pS.setLayout(new GridLayout(2,13));
		
		JPanel fixer = new JPanel();
		fixer.setLayout(new GridLayout(3,1));
		
		
		for(int i=0; i<26;i++){
			pS.add(letterButton[i]);
		}
	
		//add north and south panels
		ImageIcon icon = loop[0];
		icon.setImage( icon.getImage().getScaledInstance(
				500, 350, Image.SCALE_SMOOTH ) );	
		hangLabel.setIcon(icon);
		
		JPanel pC = new JPanel();
		pC.setLayout(new GridLayout(1,1));
		pC.add(hangLabel);
		
		this.add(pN, BorderLayout.NORTH);
		this.add(pS, BorderLayout.SOUTH);
		this.add(pC, BorderLayout.CENTER);
				
		for (int i = 0; i < 26; i++){
			letterButton[i].addActionListener(new LetterSelectedAction());			
		}
	}

	//hint return functions
	public String getHint1(){
		return puzzelArray[index].getHint1();
	}
	public String getHint2(){
		return puzzelArray[index].getHint2();
	}
	public String translation(){
		return puzzelArray[index].getTranslation();
	}
	public String getLanguage(){
		return puzzelArray[index].getLanguage();
	}
	
	//setters
	public void setMode(int m){
		if(m == 1){
			timeLimit = 60;
			timer.start();
		}
		else{
			if(timeLimit > 0){
				timer.stop();
			}
			timeLimit = 0;
			timerLabel.setText("  Time: " + timeLimit);
		}
		buttonsVisible();
		resetPuzzelLabel();
	}
	public void setSpeed(int speed){
 		buttonsVisible();
 		timeLimit = 60;
 		
 		if(speed == 3){
			readFile("hangNova.txt");
 		}
 		else if(speed == 2){
			readFile("hangHard.txt");
		}
 		else if(speed == 1){
			readFile("hangMed.txt");
		}
		else if(speed == 0){
			readFile("hangEasy.txt");	
		}
  	}
	public void setGender(String g){
		for(int i = 0 ; i < loop.length ; i++){			
			loop[i] = new ImageIcon("man1/pic" + g + i + ".png");
		}
		changeImage();
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
	
	//set buttons as visible or invisible
	public void buttonsVisible(){
		for(int i=0;i<26;i++){
			letterButton[i].setVisible(true);
		}
		imageIndex = 0;
	}
	public void buttonsInvisible(){
		for(int i=0;i<26;i++){
			letterButton[i].setVisible(false);
		}
	}
	
	//reads file for selected game level
	public void readFile(String fileName) {
		try{
			File inFile = new File(fileName);
			Scanner fin = new Scanner(inFile);
			puzzelArray = new Puzzle[40];

			String p, d, h1, h2, t, l;
			int i = 0;

			while(fin.hasNext()){
				p = fin.nextLine();
				d = fin.nextLine();
				h1 = fin.nextLine();
				h2 = fin.nextLine();
				t = fin.nextLine();
				l = fin.nextLine();
				if(fin.hasNext()){
					fin.nextLine();
				}
				puzzelArray[i] = new Puzzle (p,d,h1,h2,t,l);
				i++;
			}
			fin.close();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		resetPuzzelLabel();
	}
	
	//resets to new puzzle label	
	public void resetPuzzelLabel(){
		index = (int)(Math.random() * 40);
		solution = new StringBuilder(puzzelArray[index].getSolution());
		dash = new StringBuilder(puzzelArray[index].getDashes());
		puzzelLabel.setText(dash.toString());
	}
	
	//checking puzzle and updating puzzle label	
	public void checkLetter(String lett) {
		if( solution.indexOf(lett) == -1){
			if(mediaPlayerBad != null){
				mediaPlayerBad.play();
				mediaPlayerBad.seek( new Duration(0));
			}
			if(imageIndex < loop.length-2){
				imageIndex++;
				if(imageIndex == loop.length-2){
					puzzelLabel.setText(solution.toString());
					updateScore(-5);
					buttonsInvisible();
				}				
				changeImage();
			}
		}
		else{
			if(mediaPlayerGood != null){
				mediaPlayerGood.play();
				mediaPlayerGood.seek( new Duration(0));
			}
			List<Integer> letters = new ArrayList<Integer>();
			for(int i = 0; i<solution.length(); i++){
				if(solution.indexOf(lett, i) != -1){
					letters.add(solution.indexOf(lett, i));
				}
			}
			for(int i=0; i < letters.size(); i++){
				dash.replace(letters.get(i), letters.get(i)+1, lett); 
			}
			
			puzzelLabel.setText(dash.toString());

			if(dash.indexOf("-") == -1 && imageIndex < loop.length-2){
				imageIndex = 8;
				updateScore(20);
				changeImage();
				buttonsInvisible();
			}
		}
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

	//updates score label
	public void updateScore(int number){
		total += number;
		scoreLabel.setText("  Score: " + total);
	}
	
	//completely resets game board
	public void reset(int modeOnReset){
		imageIndex = 0;
		buttonsVisible();
		setMode(modeOnReset);
		changeImage();
		resetPuzzelLabel();
	}
	
	public void changeImage(){
		ImageIcon icon = loop[imageIndex];
		icon.setImage( icon.getImage().getScaledInstance(
				500, 350, Image.SCALE_SMOOTH ) );
		hangLabel.setIcon(icon);
 	}
	
	//listener classes
	private class TimerListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if( timeLimit > 0){
				timeLimit--;
			}
			else{
				timer.stop();
				if(imageIndex != 8){
					puzzelLabel.setText(solution.toString());
					imageIndex = 7;
					repaint();
					updateScore(-5);
					buttonsInvisible();
				}
			}
			timerLabel.setText("  Time: " + timeLimit);
		}
	}
	
	private class LetterSelectedAction implements ActionListener {
		public void actionPerformed( ActionEvent e ){
			stopSound();
			
			if(	e.getSource().equals(letterButton[0])){
				checkLetter("A");
				letterButton[0].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[1])){
				checkLetter("B");
				letterButton[1].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[2])){
				checkLetter("C");
				letterButton[2].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[3])){
				checkLetter("D");
				letterButton[3].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[4])){
				checkLetter("E");
				letterButton[4].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[5])){
				checkLetter("F");
				letterButton[5].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[6])){
				checkLetter("G");
				letterButton[6].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[7])){
				checkLetter("H");
				letterButton[7].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[8])){
				checkLetter("I");
				letterButton[8].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[9])){
				checkLetter("J");
				letterButton[9].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[10])){
				checkLetter("K");
				letterButton[10].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[11])){
				checkLetter("L");
				letterButton[11].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[12])){
				checkLetter("M");
				letterButton[12].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[13])){
				checkLetter("N");
				letterButton[13].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[14])){
				checkLetter("O");
				letterButton[14].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[15])){
				checkLetter("P");
				letterButton[15].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[16])){
				checkLetter("Q");
				letterButton[16].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[17])){
				checkLetter("R");
				letterButton[17].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[18])){
				checkLetter("S");
				letterButton[18].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[19])){
				checkLetter("T");
				letterButton[19].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[20])){
				checkLetter("U");
				letterButton[20].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[21])){
				checkLetter("V");
				letterButton[21].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[22])){
				checkLetter("W");
				letterButton[22].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[23])){
				checkLetter("X");
				letterButton[23].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[24])){
				checkLetter("Y");
				letterButton[24].setVisible(false);
			}
			if(	e.getSource().equals(letterButton[25])){
				checkLetter("Z");
				letterButton[25].setVisible(false);
			}
		}
	}
}