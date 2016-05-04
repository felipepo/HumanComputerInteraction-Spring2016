package model;
import javax.swing.JPanel;

public class Game {
	
	static int score;
	int id;
	String description;
	
	public Game(int id, String description) {
		setId(id);
		setDescription(description);
	}
	
	/**
	 * @return the score
	 */
	public static int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public static void setScore(int score) {
		Game.score = score;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
