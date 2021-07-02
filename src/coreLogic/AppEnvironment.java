package coreLogic;

import java.io.Serializable;
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.User;

/** Class to manage the environment of the application. 
 * Holds all the objects necessary for the application to run
 * <p>
 * Is the class that is saved under serialization for saving a session
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 * 
 */
public class AppEnvironment implements Serializable {

	/** Deck manager holding all the Decks for this application */
	private DeckManager deckManager = new DeckManager();
	/** User object for this AppEnvironment */
	private User user = new User();
	
	/** Creates a new quiz and returns and instance of FashCardQuiz
	 * for a deck with name deckName, and for a User user
	 * <p>
	 * Used by GUI
	 * 
	 * @param deckName String for the name of a deck that is to be quizzed on
	 * @return FlashCardQuiz object for a new quiz
	 * @throws IllegalArgumentException if deckName isn't the name of 
	 * any deck in the deckManager of this AppEnviornment
	 */
	public FlashCardQuiz newQuiz(String deckName) {
		if (deckManager.containsDeck(deckName)) {
			Deck quizDeck = deckManager.findDeck(deckName);
			FlashCardQuiz newQuiz = new FlashCardQuiz(quizDeck, user.getUserStats());
		    return newQuiz;
		}
		else {
			throw new IllegalArgumentException("No deck with name deckName in this collection!");
		}
	}
	
	/** Allows a user to see what decks they have in the GUI
	 * returns an ArrayList of Deck objects for the deck collection of
	 * this AppEnvironment's deckManager
	 * <p>
	 * This can then be parsed by GUI into a JTable, from there a user can select a Deck to edit
	 * 
	 * @return ArrayList of Deck objects, as explained above. 
	 */
	public ArrayList<Deck> viewDecks() {
		return (deckManager.getDeckCollection());
	}
	
	/** Constructor for AppEnvironment
	 * <p>
	 * Includes a parameter for a User
	 * 
	 * @param user User object for this AppEnvironment
	 */
	public AppEnvironment(User user) {
		this.user = user;
	}

	/** Returns the DeckManager for this AppEnvironment.
	 * 
	 * @return the deckManager
	 */
	public DeckManager getDeckManager() {
		return deckManager;
	}

	/** Sets the DeckManager for this AppEnvironment.
	 * 
	 * @param deckManager the deckManager to set
	 */
	public void setDeckManager(DeckManager deckManager) {
		this.deckManager = deckManager;
	}

	/** Getter method for a User
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/** Setter method for a User
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
