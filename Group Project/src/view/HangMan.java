package view;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class HangMan extends JPanel {
	
	ManImage man;
	int speed = 0;
	int soundNumberG = 0;
	int soundNumberB = 0;
	int mode = 1;
	String gender = "M";
	boolean speedChanged = false;
	boolean modeChanged = false;
	
	JLabel h1 = new JLabel(" ", JLabel.CENTER);
	JLabel h2 = new JLabel(" ", JLabel.CENTER);
	JLabel transLabel = new JLabel(" ", JLabel.CENTER);
	JLabel langLabel = new JLabel(" ", JLabel.CENTER);

	public HangMan(){
		setBackground(Color.white);
		man = new ManImage(speed);
		
		//Main title
		JPanel panelN = new JPanel();
		panelN.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("HANGMAN", JLabel.CENTER);	
		panelN.add(title);
		title.setFont(new Font("SanSerif",Font.BOLD,36));
		panelN.setBackground(Color.white);
		JLabel title1 = new JLabel("Guess the Word or Phrase" , JLabel.CENTER);
		title1.setFont(new Font("SanSerif",Font.BOLD,16));
		panelN.add(title1);
		clearHints();
		
		//East panel main panel
		JPanel panelE1 = new JPanel();
		panelE1.setLayout(new GridLayout(15,0));
		panelE1.setBackground(Color.white);

		JButton newGame = new JButton();
		newGame.setText("New Game");
		panelE1.add(newGame);
		
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				man.reset(mode);
				clearHints();
			}
		});
		
		panelE1.add(new JLabel(" "));
		JButton hint1 = new JButton("Hint 1");
		panelE1.add(hint1);
		panelE1.add(h1);
		JButton hint2 = new JButton("Hint 2");
		panelE1.add(hint2);
		panelE1.add(h2);
		JButton translation = new JButton("Translation");
		panelE1.add(translation);
		panelE1.add(transLabel);
		JButton language = new JButton("Language");
		panelE1.add(language);
		panelE1.add(langLabel);

		hint1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(h1.getText().equals(" ")){
					h1.setText(man.getHint1());
					man.updateScore(-5);
				}
			}
		});
		hint2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(h2.getText().equals(" ")){
					h2.setText(man.getHint2());
					man.updateScore(-5);
				}
			}
		});
		translation.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(transLabel.getText().equals(" ")){
					transLabel.setText(man.translation());
					man.updateScore(-5);
				}
			}
		});
		language.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(langLabel.getText().equals(" ")){
					langLabel.setText(man.getLanguage());
					man.updateScore(-5);
				}
			}
		});

		panelE1.add(new JLabel(" "));
		panelE1.add(new JLabel(" "));
		panelE1.add(new JLabel(" "));
		JButton settings = new JButton();
		settings.setText(" Settings ");
		panelE1.add(settings);

		//east panel settings panel
		JPanel panelE2 = new JPanel();
		panelE2.setLayout(new GridLayout(13,1));
		panelE2.setBackground(Color.white);

		JLabel modeLabel = new JLabel("Mode");
		modeLabel.setFont(new Font("SanSerif",Font.BOLD,20));
		JRadioButton practice = new JRadioButton();
		practice.setText("Practice");
		JRadioButton play = new JRadioButton();
		play.setText("Timed");
		play.setSelected(true);

		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(practice);
		modeGroup.add(play);
		
		panelE2.add(modeLabel);
		
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new GridLayout(1,2));
		modePanel.add(practice);
		modePanel.add(play);
		
		panelE2.add(modePanel);
		
		practice.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mode = 0;
				modeChanged = true;
			}
    	});	
		play.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mode = 1;
				modeChanged = true;
			}
    	});	
		
		JPanel diffPanel = new JPanel();
		diffPanel.setLayout(new GridLayout(2,2));
		
		JLabel difLabel1 = new JLabel("Difficulty Level");
		difLabel1.setFont(new Font("SanSerif",Font.BOLD,20));
		JRadioButton superN = new JRadioButton();
		superN.setText("Super Nova");
		JRadioButton fast = new JRadioButton();
		fast.setText("Hard");
		JRadioButton medium = new JRadioButton();
		medium.setText("Medium");
		JRadioButton slow = new JRadioButton();
		slow.setText("Easy");
		slow.setSelected(true);
		
		ButtonGroup diffGroup = new ButtonGroup();
		diffGroup.add(superN);
		diffGroup.add(fast);
		diffGroup.add(medium);
		diffGroup.add(slow);
		
		panelE2.add(difLabel1);
		diffPanel.add(superN);
		diffPanel.add(fast);
		diffPanel.add(medium);
		diffPanel.add(slow);
		panelE2.add(diffPanel);
		
		
		superN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				speed = 3;
				speedChanged = true;
			}
		});
		fast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				speed = 2;
				speedChanged = true;
			}
		});
		medium.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				speed = 1;
				speedChanged = true;
			}
		});
		slow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				speed = 0;
				speedChanged = true;
			}
		});

		JLabel imageLabel1 = new JLabel("Hang Image"); 
		imageLabel1.setFont(new Font("SanSerif",Font.BOLD,20));
		panelE2.add(imageLabel1);
		JRadioButton male = new JRadioButton();
		male.setText("Man");
		JRadioButton female = new JRadioButton();
		female.setText("Woman");
		male.setSelected(true);
		
		ButtonGroup groupGender = new ButtonGroup();
		groupGender.add(male);
		groupGender.add(female);

		JPanel genderPanel = new JPanel();
		genderPanel.setLayout(new GridLayout(1,2));
		
		genderPanel.add(male);
		genderPanel.add(female);
		
		panelE2.add(genderPanel);
		
		male.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gender = "M";
			}
		});
		female.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				gender = "F";
			}
		});

		JLabel hitLabel = new JLabel(" Hit Sound ");
		hitLabel.setFont(new Font("SanSerif",Font.BOLD,20));
		panelE2.add(hitLabel);
		
		JRadioButton none = new JRadioButton();
		none.setText("None");
		JRadioButton good1 = new JRadioButton();
		good1.setText("Bell 1");
		JRadioButton good2 = new JRadioButton();
		good2.setText("Bell 2");
		JRadioButton good3 = new JRadioButton();
		good3.setText("Bell 3");
		none.setSelected(true);
		
		ButtonGroup goodSoundGroup = new ButtonGroup();
		goodSoundGroup.add(none);
		goodSoundGroup.add(good1);
		goodSoundGroup.add(good2);
		goodSoundGroup.add(good3);
		
		JPanel hitPanel = new JPanel();
		hitPanel.setLayout(new GridLayout(2,2));
		
		hitPanel.add(none);
		hitPanel.add(good1);
		hitPanel.add(good2);
		hitPanel.add(good3);
		
		panelE2.add(hitPanel);
		
		none.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberG = 0;
			}
    	});
		good1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberG = 1;
			}
    	});
		good2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberG = 2;
			}
    	});
		good3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberG = 3;
			}
    	});
		
		JLabel missLabel = new JLabel(" Miss Sound ");
		missLabel.setFont(new Font("SanSerif",Font.BOLD,20));
		panelE2.add(missLabel);
		JRadioButton none2 = new JRadioButton();
		none2.setText("None");
		JRadioButton miss1 = new JRadioButton();
		miss1.setText("Bong 1");
		JRadioButton miss2 = new JRadioButton();
		miss2.setText("Bong 2");
		JRadioButton miss3 = new JRadioButton();
		miss3.setText("Goat");
		none2.setSelected(true);
		
		none2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberB = 0;
			}
    	});
		miss1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberB = 1;
			}
    	});
		miss2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberB = 2;
			}
    	});
		miss3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				soundNumberB = 3;
			}
    	});
				
		ButtonGroup badSoundGroup = new ButtonGroup();
		badSoundGroup.add(none2);
		badSoundGroup.add(miss1);
		badSoundGroup.add(miss2);
		badSoundGroup.add(miss3);

		JPanel missPanel = new JPanel();
		missPanel.setLayout(new GridLayout(2,2));
		
		missPanel.add(none2);
		missPanel.add(miss1);
		missPanel.add(miss2);
		missPanel.add(miss3);

		panelE2.add(missPanel);
		
		JButton save = new JButton();
		save.setText(" Save ");
		JButton cancel = new JButton();
		cancel.setText(" Cancel ");

		panelE2.add(new JLabel(" "));
		
		JPanel savePanel = new JPanel();
		savePanel.setLayout(new GridLayout(1,2));
		
		savePanel.add(save);
		savePanel.add(cancel);
		
		panelE2.add(savePanel);
		
		//panel holder for east panel
		CardLayout layout = new CardLayout();
		JPanel eastPanelHolder = new JPanel(layout);
		eastPanelHolder.add(panelE1, "1");
		eastPanelHolder.add(panelE2, "2");
		
		settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				layout.show(eastPanelHolder,"2");
			}
    	});
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				man.setBadSound(soundNumberB);
				man.setGoodSound(soundNumberG);
				if(speedChanged == true){
					man.setSpeed(speed);
					clearHints();
				}
				if(modeChanged == true){
					man.setMode(mode);
					clearHints();
				}
				man.setGender(gender);
				speedChanged = false;
				modeChanged = false;
				layout.show(eastPanelHolder,"1");
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				layout.show(eastPanelHolder,"1");
			}
		});
		
		this.setLayout(new BorderLayout(5, 10));
		this.add(panelN, BorderLayout.NORTH);
		this.add(eastPanelHolder, BorderLayout.EAST);
		this.add(man, BorderLayout.CENTER);
	}

	public void clearHints(){
		String trans = man.translation();
		String lang = man.getLanguage();
		
		h1.setText(" ");
		h2.setText(" ");
		
		if(trans.equals("None")){
			transLabel.setText("None");	
		}
		else{
			transLabel.setText(" ");
		}
		if(lang.equals("English")){
			langLabel.setText("English");	
		}
		else{
			langLabel.setText(" ");
		}
	}
} //end of extended JPanel class