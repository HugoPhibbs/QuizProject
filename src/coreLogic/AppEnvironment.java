package coreLogic;

import java.io.Serializable;

import coreObjects.User;

/** Class to manage the environment of the application. 
 * Holds all the objects necessary for a the application
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 * 
 */
public class AppEnvironment implements Serializable {

	private DeckManager deckManager = new DeckManager();
	private User user = new User();
	
	public AppEnvironment() {
		
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
