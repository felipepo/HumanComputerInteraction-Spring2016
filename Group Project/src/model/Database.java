package model;
import java.util.ArrayList;

public class Database {
	ArrayList<Game> gameList;
	
	public Database() {
		gameList = new ArrayList<Game>();
		for(int i = 0; i < 6; i++) {
			String description = "";
			switch (i) {
				case 0:
					description = "A 52-card deck is divided into face-down stacks as equally as possible between all players. "
							+ "One player removes the top card of his stack and places it face-up on the playing surface within "
							+ "reach of all players. The players take turns doing this in a clockwise manner until a Jack is "
							+ "placed on the pile. At this point, any and all players may attempt to slap the pile with the hand "
							+ "they used to place the card; whoever covers the stack with his hand first takes the pile, shuffles "
							+ "it, and adds it to the bottom of his stack. If another player puts their card over the Jack before "
							+ "it is slapped, the Jack and the cards underneath can't be taken by a player until the next Jack is "
							+ "revealed. When a player has run out of cards, he has one more chance to slap a jack and get back "
							+ "in the game, but if he fails, he is out. Gameplay continues with hands of this sort until one "
							+ "player has acquired all of the cards.In a popular variation with a regular deck, the person "
							+ "covering the cards must simultaneously say \"Slapjack!\" If the person fails to say this, he or "
							+ "she does not get the pile. Additionally, if the player covers the pile and says \"Slapjack\", "
							+ "and the card is not a jack, then the other players get to divide the pile evenly among themselves.";
					break;
				case 1:
					description = "The word to guess is represented by a row of dashes, representing each letter of the word. "
							+ "In most variants, proper nouns, such as names, places, and brands, are not allowed. If the guessing "
							+ "player suggests a letter which occurs in the word, the other player writes it in all its correct"
							+ " positions. If the suggested letter or number does not occur in the word, the other player draws "
							+ "one element of a hanged man stick figure as a tally mark. The player guessing the word may, at any"
							+ " time, attempt to guess the whole word. If the word is correct, the game is over and the guesser"
							+ " wins. Otherwise, the other player may choose to penalize the guesser by adding an element to the"
							+ " diagram. On the other hand, if the other player makes enough incorrect guesses to allow his"
							+ " opponent to complete the diagram, the game is also over, this time with the guesser losing."
							+ " However, the guesser can also win by guessing all the letters or numbers that appears in the word,"
							+ " thereby completing the word, before the diagram is completed.";
					break;
				case 2:
					description = "Lite-Brite is a toy that was originally marketed in 1967. It consists of a light box with small"
							+ " colored plastic pegs that fit into a panel and illuminate to create a lit picture, by either using"
							+ " one of the included templates or creating a 'freeform' image on a blank sheet of black paper."
							+ " There are eight peg colors: red, blue, orange, white (clear/colorless), green, yellow, pink, violet"
							+ " (purple).In the event that pegs were lost or damaged, Hasbro provided refills and/or new colors."
							+ " Color-by-letter templates were sold with the set so that children could create characters including"
							+ " Mickey Mouse, Scooby-Doo, and My Little Pony, among others.";
					break;
				case 3:
					description = "The objective of the game is to win all cards. The deck is divided evenly among the players, giving"
							+ " each a down stack. In unison, each player reveals the top card of their deck – this is a \"battle\" – "
							+ "and the player with the higher card takes both of the cards played and moves them to their stack. "
							+ "Aces are high, and suits are ignored. If the two cards played are of equal value, then there is "
							+ "a \"war\". Both players place the next card of their pile face down, depending on the variant, and then"
							+ " another card face-up. The owner of the higher face-up card wins the war and adds all six (or ten) cards"
							+ " on the table to the bottom of their deck. If the face-up cards are again equal then the battle repeats "
							+ "with another set of face-down/up cards. This repeats until one player's face-up card is higher than their"
							+ " opponent's.";
					break;
			}
			Game game = new Game(i, description);
			gameList.add(game);
		}
	}
	
	public ArrayList<Game> getGameList(){
		return gameList;
	}
	
}
