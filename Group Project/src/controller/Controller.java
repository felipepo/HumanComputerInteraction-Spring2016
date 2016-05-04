package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import model.Database;
import view.GameLibFrame;
import view.GameView;
import view.InfoView;
import view.ThumbnailsView;

public class Controller {
	ThumbnailsView thumbnailsView;
	InfoView infoView;
	GameView gameView;
	Database model;
	GameLibFrame frame;
	
	public Controller(ThumbnailsView thumbnailsView, InfoView infoView, Database model) {
		setThumbnailsView(thumbnailsView);
		setInfoView(infoView);
		setModel(model);
		frame = new GameLibFrame();
		frame.getContentPane().add(thumbnailsView);
		frame.pack();
	}
	
	public class SelectGameListener implements ActionListener{
		private Integer gameIndex;

		public SelectGameListener(int gameIndex) {
			super();
			this.gameIndex = gameIndex;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(thumbnailsView != null)
				frame.remove(thumbnailsView);
			InfoView infoView = new InfoView(gameIndex);
			setInfoView(infoView);
			frame.add(infoView);
			frame.revalidate();
			frame.repaint();
			frame.pack();
		}
		
	}
	
	public class ReturnToMainListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(infoView != null)
				frame.remove(infoView);
			if(gameView != null)
				frame.remove(gameView);
			ThumbnailsView thumbnailsView = new ThumbnailsView();
			setThumbnailsView(thumbnailsView);
			frame.getContentPane().add(thumbnailsView);
			frame.revalidate();
			frame.repaint();
			frame.pack();
		}
		
	}
	
	public class PlayButtonListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			int gameID = infoView.getGameID();
			frame.remove(infoView);
			gameView = new GameView(gameID);
			gameView.revalidate();
			gameView.repaint();
			setGameView(gameView);
			frame.getContentPane().add(gameView);
			frame.revalidate();
			frame.repaint();
			frame.pack();
			if(gameID == 3) {
				frame.setSize(400, 400);
			}
		}
	}
	
	
	/**
	 * @param thumbnailsView the thumbnailsView to set
	 */
	public void setThumbnailsView(ThumbnailsView thumbnailsView) {
		for(int i = 0; i < 4; i++) {
			thumbnailsView.registerSelectedGameListener(new SelectGameListener(i) , i);
		}
		thumbnailsView.setModel(model);
		this.thumbnailsView = thumbnailsView;
	}
	/**
	 * @param infoView the infoView to set
	 */
	public void setInfoView(InfoView infoView) {
		infoView.registerReturnToMainListener(new ReturnToMainListener());
		infoView.registerPlayButtonListener(new PlayButtonListener());
		infoView.setModel(model);
		this.infoView = infoView;
	}
	
	public void setGameView(GameView gameView) {
		gameView.registerReturnToMainButton(new ReturnToMainListener());
		//gameView.setModel(model);
		this.gameView = gameView;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(Database model) {
		this.model = model;
	}
	
	
}
