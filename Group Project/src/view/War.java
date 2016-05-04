package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.*;

@SuppressWarnings("serial")
public class War extends JPanel{
    
	ImageIcon dealerImage;
	ImageIcon userImage;
	String cardSelection = System.getProperty("user.dir") + File.separator+ "cards4"+File.separator+"pic";
	int userScore = 0;
	
	
	
    public War(){
    	setLayout(new BorderLayout());
    	Random random = new Random();
        setBackground(Color.white);
        
        dealerImage = new ImageIcon(cardSelection + 52 + ".png");
        userImage = new ImageIcon(cardSelection + 52 + ".png");
        
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2,1));
        titlePanel.setBackground(Color.white);
        
        JLabel title = new JLabel("War!", JLabel.CENTER);
        title.setFont(new Font("SanSerif", Font.BOLD, 36));
        titlePanel.add(title);
        
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new BorderLayout(3,3));
        gamePanel.setBackground(Color.yellow);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        
        JLabel scoreLabel = new JLabel("0");
        topPanel.add(scoreLabel, BorderLayout.NORTH);
        
        JButton nextCard = new JButton();
        nextCard.setText("Attack!");
        topPanel.add(nextCard, BorderLayout.CENTER);
               
        gamePanel.add(topPanel, BorderLayout.NORTH);
        
        JLabel userCardLabel = new JLabel();
        userCardLabel.setIcon(userImage);
        
        JLabel userLabel = new JLabel("User's Card");
        JLabel dealerLabel = new JLabel("Dealer's Card");
        
        JLabel dealerCardLabel = new JLabel();
        dealerCardLabel.setIcon(dealerImage);
                
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BorderLayout());
        
        labelPanel.add(dealerLabel, BorderLayout.EAST);
        labelPanel.add(userLabel, BorderLayout.WEST);
        
        gamePanel.add(dealerCardLabel, BorderLayout.EAST);
        //gamePanel.add(dealerLabel);
        gamePanel.add(userCardLabel, BorderLayout.WEST);
        //gamePanel.add(userLabel);
        
        gamePanel.add(labelPanel, BorderLayout.SOUTH);
        
        nextCard.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int dealerCard = random.nextInt(52);
				int userCard = random.nextInt(52);
				dealerImage = new ImageIcon(cardSelection + dealerCard + ".png");
				dealerCardLabel.setIcon(dealerImage);
				userImage = new ImageIcon(cardSelection + userCard + ".png");
				userCardLabel.setIcon(userImage);
				if(dealerCard % 4 > userCard % 4){
					userScore--;
				} else if(dealerCard % 4 < userCard % 4){
					userScore++;
				}
				
				scoreLabel.setText("Score: " + userScore);
				
				if(userScore >= 20){
					// Tell the user they won
					JOptionPane.showMessageDialog(null, "You win!");
				} else if(userScore <= -20){
					JOptionPane.showMessageDialog(null, "You lose!");
				}
			}
        	
        });
        
        add(gamePanel, BorderLayout.CENTER);
    }
}