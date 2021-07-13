package core.coreLogic;

import java.io.Serializable;
import core.coreObjects.User;
import setup.Setup;

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
	/** Setup object that was used to create this application */
	private Setup setup;

	/**
	 * Constructor for AppEnvironment
	 * <p>
	 * Includes a parameter for a User
	 * 
	 * @param user User object for this AppEnvironment
	 */
	public AppEnvironment(User user, Setup setup) {
		this.user = user;
		this.setup = setup;
	}

	/**
	 * Returns the DeckManager for this AppEnvironment.
	 * 
	 * @return DeckManager object
	 */
	public DeckManager getDeckManager() {
		return deckManager;
	}

	/**
	 * Sets the DeckManager for this AppEnvironment.
	 * 
	 * @param deckManager DeckManager to be set
	 */
	public void setDeckManager(DeckManager deckManager) {
		this.deckManager = deckManager;
	}

	/**
	 * Getter method for a User
	 * 
	 * @return User object
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setter method for a User
	 * 
	 * @param user User object to be set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Getter method for the Setup object for AppEnvironment
	 * 
	 * @return Setup object
	 */
	public Setup getSetup() {
		return setup;
	}

	/**
	 * Saves the current state of the app to the Setup object for this class.
	 * <p>
	 * Called when MainScreen is closed
	 */
	public void save() {
		setup.saveSession();
	}
}
