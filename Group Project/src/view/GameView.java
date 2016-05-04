package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GameView extends JPanel{
	
	private GridBagConstraints layoutConst;
	JButton returnButton;
	
	public GameView(int ID) {
		super();
		setLayout(new GridBagLayout());
		
		layoutConst = new GridBagConstraints();   //removes all of the constrains given so far
		//adding position constrains
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		
		returnButton = new JButton("Return");
		
		add(returnButton, layoutConst);
		
		layoutConst = new GridBagConstraints();   //removes all of the constrains given so far
		//adding position constrains
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		//layoutConst.weightx = 5;
		
		// 10 pixels of padding around component
		//layoutConst.insets = new Insets(20, 20, 20, 20);
		//adds game to panel
		switch(ID) {
		case 0:
			SlapJack game1 = new SlapJack();
			add(game1, layoutConst);
			break;
		case 1:
			HangMan game2 = new HangMan();
			add(game2, layoutConst);
			break;
		case 2:
			LightBright game3 = new  LightBright();
			add(game3, layoutConst);
			break;
		case 3:
			War game4 = new  War();
			add(game4, layoutConst);
			break;
		case 4:
		case 5:
		}
		
		
	}
	
	public void registerReturnToMainButton(ActionListener returnToMainListener) {
		returnButton.addActionListener(returnToMainListener);
	}
}
