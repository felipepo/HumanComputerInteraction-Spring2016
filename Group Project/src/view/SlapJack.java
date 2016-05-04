package view;



import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class SlapJack extends JPanel{
	private String[] cardChoices = {"Traditional", "Catwomen", "Video Game"};
	SlapImage cardsPanel;
	String cardSelection = "cards1/pic";
	int soundNumberG = 0;
	int soundNumberB = 0;
	int speed = 1300;
	int mode = 1;
	boolean speedChanged = false;
	boolean modeChanged = false;
	
	public SlapJack(){
		cardsPanel = new SlapImage(cardSelection, speed);
		this.setBackground(Color.white);

		//create north title panel
		JPanel panelN = new JPanel();
		panelN.setLayout(new GridLayout(2,1));
		JLabel title = new JLabel("SLAP JACK", JLabel.CENTER);
		panelN.add(title);
		title.setFont(new Font("SanSerif",Font.BOLD,40));
		panelN.setBackground(Color.white);
		JLabel title1 = new JLabel("Click the Cards: +1 Face Cards  |  +2 Jacks  |  -2 All Others" , JLabel.CENTER);
		title1.setFont(new Font("SanSerif",Font.BOLD,16));
		panelN.add(title1);

		//create east panel
		JPanel panelE1 = new JPanel();
		panelE1.setLayout(new GridLayout(15,1));
		panelE1.setBackground(Color.white);
		//panelE1.setPreferredSize(new Dimension(200, 0));

		JButton newGame = new JButton();
		newGame.setText("New Game");
		panelE1.add(newGame);
		
		newGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setMode(mode);
				cardsPanel.setClickable(true);
				cardsPanel.clearTotal();
			}
		});
		
		panelE1.add(new JLabel(" "));
		JLabel difLabel = new JLabel("Difficulty Level");
		difLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		panelE1.add(difLabel);
		JRadioButton superN = new JRadioButton();
		superN.setText("Super Nova");
		JRadioButton fast = new JRadioButton();
		fast.setText("Hard");
		JRadioButton medium = new JRadioButton();
		medium.setText("Medium");
		JRadioButton slow = new JRadioButton();
		slow.setText("Easy");
		JRadioButton verySlow = new JRadioButton();
		verySlow.setText("Easiest");

		ButtonGroup group = new ButtonGroup();
		group.add(superN);
		group.add(fast);
		group.add(medium);
		group.add(slow);
		group.add(verySlow);

		panelE1.add(superN);
		panelE1.add(fast);
		panelE1.add(medium);
		panelE1.add(slow);
		panelE1.add(verySlow);

		superN.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setSpeed(300);
			}
		});
		fast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setSpeed(500);
			}
		});
		medium.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setSpeed(700);
			}
		});
		slow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setSpeed(1000);
			}
		});
		verySlow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cardsPanel.setSpeed(1300);
			}
		});
		verySlow.setSelected(true);
		
		JLabel imageLabel = new JLabel("Card Image");
		imageLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		panelE1.add(imageLabel);

		JComboBox<String> dropBox = new JComboBox<>(cardChoices);
		panelE1.add(dropBox);

		dropBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int index = dropBox.getSelectedIndex();
		  		if(index != (-1)){
		  			if(index == 0){
		  				cardSelection = "cards1/pic";
		  				cardsPanel.cardStack(cardSelection);
		  			}
		  			else if(index == 1){
						cardSelection = "cards2/pic";
						cardsPanel.cardStack(cardSelection);
					}
					else{
						cardSelection = "cards3/pic";
						cardsPanel.cardStack(cardSelection);
					}
				}
		  	}
		});

		panelE1.add(new JLabel(" "));
		panelE1.add(new JLabel(" "));
		JButton settings = new JButton();
		settings.setText(" Settings ");
		panelE1.add(settings);
		panelE1.add(new JLabel(" "));
		panelE1.add(new JLabel(" "));
		
		//east panel 2 settings panel
		JPanel panelE2 = new JPanel();
		//panelE2.setPreferredSize(new Dimension(200, 0));
		panelE2.setLayout(new GridLayout(13,2));
		panelE2.setBackground(Color.white);

		JLabel modeLabel = new JLabel("Mode");
		modeLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		JRadioButton practice = new JRadioButton();
		practice.setText("Practice");
		JRadioButton play = new JRadioButton();
		play.setText("Timed");
		play.setSelected(true);
		
		ButtonGroup modeGroup = new ButtonGroup();
		modeGroup.add(practice);
		modeGroup.add(play);

		panelE2.add(modeLabel);
		panelE2.add(new JLabel(" "));
		panelE2.add(practice);
		panelE2.add(play);
		
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
		
		JLabel soundLabel = new JLabel("Sounds");
		soundLabel.setFont(new Font("SanSerif",Font.BOLD,26));
		panelE2.add(soundLabel);
		panelE2.add(new Label(" "));
		JLabel goodLabel1 = new JLabel("(Hit Sound)");
		panelE2.add(goodLabel1);
		panelE2.add(new Label(" "));
				
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
		
		panelE2.add(none);
		panelE2.add(good1);
		panelE2.add(good2);
		panelE2.add(good3);
		
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
		
		JLabel missLabel = new JLabel(" (Miss Sound) ");
		panelE2.add(missLabel);
		panelE2.add(new JLabel(" "));
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
		
		panelE2.add(none2);
		panelE2.add(miss1);
		panelE2.add(miss2);
		panelE2.add(miss3);

		JButton save = new JButton();
		save.setText(" Save ");
		JButton cancel = new JButton();
		cancel.setText(" Cancel ");
		
		panelE2.add(new JLabel(" "));
		panelE2.add(new JLabel(" "));
		panelE2.add(new JLabel(" "));
		panelE2.add(new JLabel(" "));
		panelE2.add(new JLabel(" "));
		panelE2.add(new JLabel(" "));
		panelE2.add(save);
		panelE2.add(cancel);

		CardLayout layout = new CardLayout();
		JPanel eastHolder = new JPanel(layout);
		eastHolder.add(panelE1, "1");
        eastHolder.add(panelE2, "2");

        save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				cardsPanel.setBadSound(soundNumberB);
				cardsPanel.setGoodSound(soundNumberG);
				if(modeChanged == true){
					cardsPanel.setMode(mode);
				}
				modeChanged = false;
				layout.show(eastHolder,"1");
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				layout.show(eastHolder,"1");
			}
    	});
		settings.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				layout.show(eastHolder,"2");
			}
    	});

		this.setLayout(new BorderLayout(5, 10));
		this.add(panelN, BorderLayout.NORTH);
		this.add(eastHolder, BorderLayout.EAST);
		this.add(cardsPanel, BorderLayout.CENTER);
	}
} //end of extended JPanel class