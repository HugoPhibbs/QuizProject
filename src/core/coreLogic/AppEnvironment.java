package core.coreLogic;

import java.io.Serializable;
import core.coreObjects.User;

/**
 * Class to manage the environment of the application. Holds all the objects
 * necessary for the application to run
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

	/**
	 * Constructor for AppEnvironment
	 * <p>
	 * Includes a parameter for a User
	 * 
	 * @param user User object for this AppEnvironment
	 */
	public AppEnvironment(User user) {
		this.user = user;
	}

	/**
	 * Returns the DeckManager for this AppEnvironment.
	 * 
	 * @return the deckManager
	 */
	public DeckManager getDeckManager() {
		return deckManager;
	}

	/**
	 * Sets the DeckManager for this AppEnvironment.
	 * 
	 * @param deckManager the deckManager to set
	 */
	public void setDeckManager(DeckManager deckManager) {
		this.deckManager = deckManager;
	}

	/**
	 * Getter method for a User
	 * 
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter method for a User
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

}
