package coreLogic;

import java.io.Serializable;
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.User;
import gui.EditDeckScreen;
import gui.EditFlashCardScreen;
import gui.QuizzingScreen;
import gui.Screen;

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
	
	/** Constructor for AppEnvironment
	 * <p>
	 * Includes a parameter for a User
	 * 
	 * @param user User object for this AppEnvironment
	 */
	public AppEnvironment(User user) {
		this.user = user;
	}
	
	/** Handles creating a new screen to quiz a user
	 * <p>
	 * Takes logical code away from MainScreen
	 * 
	 * @param chosenDeck Deck object to be quizzed on
	 */
	public void newQuizzingScreen(Deck chosenDeck) {
		FlashCardQuiz newQuiz = newQuiz(chosenDeck);
		QuizzingScreen flashCardQuizScreen = new QuizzingScreen(newQuiz);
		// flashCardQuizScreen.show() // TODO remove later once Screen has been fully implemented
	}
	
	/** Handles creating a new EditDeckScreen
	 * <p>
	 * Takes logical code away from MainScreen
	 * 
	 * @param chosenDeck Deck object to be edited
	 */
	public void newEditDeckScreen(Deck chosenDeck) {
		EditDeckScreen editDeckScreen = new EditDeckScreen(chosenDeck, deckManager);
		// editDeckScreen.show() // TODO remove later once Screen has been fully implemented
	}
	
	/** Handles creating a new screen to create a flash card
	 * <p>
	 * Takes logical code away from MainScreen
	 * <p>
	 * Despite it creating an EditFlashCardScreen, that handles both editing and creating a FlashCard, this
	 * method can only create an EditFlashCardScren for the later case, as this is only intended to be used 
	 * by MainScreen
	 * 
	 * @param chosenDeck Deck object that a user has chosen to add a FlashCard to
	 * @param mainScreen Screen object that is the parent to the to be created EditFlashCardScreen
	 */
	public void newEditFlashCardScreen(Deck chosenDeck, Screen mainScreen) {
		EditFlashCardScreen editFlashCardScreen = new EditFlashCardScreen(null, deckManager, chosenDeck, mainScreen);
		// editFlashCardScreen.show() // TODO remove later once Screen has been fully implemented
	}
	
	/** Creates a new quiz and returns and instance of FashCardQuiz
	 * for a deck with name deckName, and for a User user
	 * <p>
	 * 
	 * @param quizDeck Deck object that is to be quizzed on
	 * @return FlashCardQuiz object for a new quiz
	 * @throws IllegalArgumentException if deckName isn't the name of 
	 * any deck in the deckManager of this AppEnviornment
	 */
	private FlashCardQuiz newQuiz(Deck quizDeck) {
		if (deckManager.containsDeck(quizDeck)) {
			FlashCardQuiz newQuiz = new FlashCardQuiz(quizDeck, user.getUserStats());
		    return newQuiz;
		}
		else {
			throw new IllegalArgumentException("No deck with name deckName in this collection!");
		}
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
