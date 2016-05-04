import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class SwingClass {
	private JFrame mainFrame;
	
	private JPanel northPanel;
	private JPanel topNorthPanel;
	private JPanel bottomNorthPanel;
	
	private JPanel southPanel;
	private JPanel topSouthPanel;
	private JPanel bottomSouthPanel;
	
	private JButton buttonChoose;
	private JButton buttonNext;
	private JButton buttonPrevious;
	
	private JMenuBar menuBar;
	private JMenu mainMenu;
	
	private JComboBox comboBox;
	
	private JRadioButton previousRadioButton;
	private JRadioButton nextRadioButton;
	
	File[] files;
	String[] options;
	
	private String directory;
	private ArrayList<ImageClass> images;
	private ArrayList<JButton> thumbnails;
	private int currentImageIndex;
	
	public SwingClass () {
		mainFrame = new JFrame("Project 1: Image Viewer by Felipe Podolan Oliveira");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new BorderLayout());
		northPanel = new JPanel(new BorderLayout());
		topNorthPanel = new JPanel();
		bottomNorthPanel = new JPanel();
		southPanel = new JPanel(new BorderLayout());
		topSouthPanel = new JPanel();
		bottomSouthPanel = new JPanel();
		
		buttonChoose = new JButton("Select Directory");
		buttonChoose.setPreferredSize(new Dimension(150,30));
		bottomSouthPanel.add(buttonChoose);
		
		
		buttonNext = new JButton("Next");
		buttonNext.setPreferredSize(new Dimension(100,30)); 
		mainFrame.getContentPane().add(buttonNext, BorderLayout.LINE_END);
		
		buttonPrevious = new JButton("Previous");
		buttonPrevious.setPreferredSize(new Dimension(100,30)); 
		mainFrame.getContentPane().add(buttonPrevious, BorderLayout.LINE_START);
		
		comboBox = new JComboBox();
		
		images = new ArrayList<ImageClass> ();
		thumbnails = new ArrayList<JButton> ();
		
		buttonChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				//opens file chooser
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileChooser.showOpenDialog(null);
				
				//if user clicks select
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					//get path to directory selected and save at directory
					directory = fileChooser.getSelectedFile().getAbsolutePath() + File.separator;
					
					//return a list of files filtered by the filtering method below
					files = importImages(fileChooser);
				
					//sets currentImageIndex to 0
					currentImageIndex = 0;
					
					//remove current image
					removeCurrentImage();
					
					//clear images list
					images.clear();
				
					//add new pictures to images list
					for (int i = 0; i < files.length; i++) {
						ImageClass imageLabel = new ImageClass();
						ImageIcon icon = new ImageIcon(directory + files[i].getName());
						imageLabel.setIcon(icon);
						images.add(imageLabel);
					}
					
					//remove thumbnails
					removeThumbnails();
					
					thumbnails.clear();
					
					//add thumbnails to thumbnails list
					for (int i = 0; i < images.size(); i++) {
						ImageIcon icon = (ImageIcon) images.get(i).getIcon();
						Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_FAST );
						icon.setImage(img);
						JButton thumbnail = new JButton(icon);
						thumbnails.add(thumbnail);
						topSouthPanel.add(thumbnail);
					}
					
					//add listener to each thumbnail
					thumbnailsAction();
					
					//creates comboBox
					comboBoxCreator();
					
					//loads current image
					loadCurrentImage();
					
					SwingUtilities.updateComponentTreeUI(mainFrame);
				}
			}
		});
		
		southPanel.add(topSouthPanel, BorderLayout.CENTER);
		southPanel.add(bottomSouthPanel, BorderLayout.PAGE_END);
		
		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				nextAction();
			}
		});
		
		buttonPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				previousAction();
			}
		});
		
		menuBar = new JMenuBar();
		mainMenu = new JMenu();
		mainMenu.setText("Main Menu");
		JMenuItem previousItem = new JMenuItem();
		previousItem.setText("Previous");
		JMenuItem nextItem = new JMenuItem();
		nextItem.setText("Next");
		
		previousItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				previousAction();
			}
		});
		
		nextItem.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent arg0 ) {
				nextAction();
			}
		});
		
		mainMenu.add(previousItem);
		mainMenu.add(nextItem);
		menuBar.add(mainMenu);
		
		topNorthPanel.add(menuBar);
		
		previousRadioButton = new JRadioButton("Previous");
		nextRadioButton = new JRadioButton("Next");
		
		previousRadioButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				previousAction();
			}
		});
		nextRadioButton.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				nextAction();
			}
		});
		
		topNorthPanel.add(previousRadioButton);
		topNorthPanel.add(nextRadioButton);
		
		northPanel.add(topNorthPanel, BorderLayout.PAGE_START);
		northPanel.add(bottomNorthPanel, BorderLayout.PAGE_END);
		
		mainFrame.getContentPane().add(northPanel, BorderLayout.PAGE_START);
		mainFrame.getContentPane().add(southPanel, BorderLayout.PAGE_END);
						
		mainFrame.setVisible(true);
		
		currentImageIndex = 0;
	}
	
	public File[] importImages(JFileChooser fileChooser) {
		return fileChooser.getSelectedFile().listFiles(new FilenameFilter() {
			@Override
	        public boolean accept(File dir, String name) {
	            return name.endsWith(".jpg") || name.endsWith(".jpeg") ||
	            		name.endsWith(".png") || name.endsWith(".gif");
			}
		});
	}
	
	public void removeThumbnails() {
		if (!thumbnails.isEmpty()){
			for (int i = 0; i < thumbnails.size(); i++) {
				topSouthPanel.remove(thumbnails.get(i));
			}
		}
	}
	
	public void thumbnailsAction(){
		for(int i = 0; i < thumbnails.size(); i++) {
			thumbnails.get(i).addActionListener( new ThumbnailListener(i)  {
				public void actionPerformed( ActionEvent e ) {
					removeCurrentImage();
					setCurrentImageIndex(this.getIndex());
					loadCurrentImage();
				}
			});
		}
	}
	
	public void removeCurrentImage(){
		if(!images.isEmpty()){
			for (int i = 0; i < images.size(); i++) {
				mainFrame.getContentPane().remove(images.get(i));
			}
		}
	}
	
	public void loadCurrentImage(){
		if (!images.isEmpty()) {
		mainFrame.getContentPane().add(images.get(currentImageIndex), BorderLayout.CENTER);
		}
		SwingUtilities.updateComponentTreeUI(mainFrame);
	}
	
	public void nextAction() {
		int nextIndex = currentImageIndex + 1;
		if(nextIndex < images.size()){
			removeCurrentImage();
			currentImageIndex++;
			loadCurrentImage();
		}
	}
	
	public void previousAction(){
		int nextIndex = currentImageIndex - 1;
			if(nextIndex >= 0){
			removeCurrentImage();
			currentImageIndex--;
			loadCurrentImage();
		}
	}
	
	public void setCurrentImageIndex(int currentImageIndex) {
		this.currentImageIndex = currentImageIndex;
	}
	
	public void comboBoxCreator() {
		options = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			options[i] = files[i].getName();
		}
		if(comboBox.getItemCount() != 0) {
			bottomNorthPanel.remove(comboBox);
		}
		comboBox = new JComboBox(options);
		comboBox.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				JComboBox cb = (JComboBox)e.getSource();
		        String name = (String)cb.getSelectedItem();
		        for(int i = 0; i < options.length; i++) {
		        	if(files[i].getName().equals(name)) {
		        		removeCurrentImage();
		        		setCurrentImageIndex(i);
		        		loadCurrentImage();
		        	}
		        }
			}
		});
		bottomNorthPanel.add(comboBox);
	}
	
}
