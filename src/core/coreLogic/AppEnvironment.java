package core.coreLogic;

import java.io.Serializable;
import core.coreObjects.User;
import gui.guiLogic.GuiManager;
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
	private User user;
	/** Setup object that was used to create this application */
	private Setup setup;
	/** GuiManager for this application */
	private GuiManager guiManager;

	/**
	 * Constructor for AppEnvironment
	 * <p>
	 * Includes a parameter for a User
	 * 
	 * @param user User object for this AppEnvironment
	 * @param setup Setup object that was used to load this Application instance
	 */
	public AppEnvironment(User user, Setup setup) {
		this.user = user;
		this.setup = setup;
	}

	// ************ Starting an application ************* //

	/**
	 * Handles starting up a the Application whether it is for the first time or the
	 * second time
	 */
	public void onStartUp() {
		setGuiManager(new GuiManager());
	}

	// ************ Closing an application **************** //

	/**
	 * Handles closing down a Session Saves the session and then closes the GUI
	 */
	public void closeDown() {
		guiCloseDown();
		save();
	}

	/**
	 * Handles closing down the GUI
	 * <p>
	 * If the GUI was sucessfully reset, guiManager is set to null, ready to be
	 * initliazed again on the next start up of the application
	 * 
	 */
	private void guiCloseDown() {
		guiManager.closeAllScreens();
		setGuiManager(null);
	}

	/**
	 * Saves the current state of the app to the Setup object for this class.
	 * <p>
	 * Called when MainScreen is closed
	 */
	public void save() {
		setup.saveSession();
	}

	// ********** Setter and Getter methods **************** //

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
	 * Getter method for the setup object for AppEnvironment
	 * 
	 * @return Setup object
	 */
	public Setup getSetup() {
		return setup;
	}

	/**
	 * Getter method for the guiManager object for AppEnvironment
	 * 
	 * @return GuiManager object
	 */
	public GuiManager getGuiManager() {
		return guiManager;
	}

	/**
	 * Setter method for guiManager
	 * 
	 * @param guiManager GuiManager object to be set
	 */
	public void setGuiManager(GuiManager guiManager) {
		this.guiManager = guiManager;
	}
}
