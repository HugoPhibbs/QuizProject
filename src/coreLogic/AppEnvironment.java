package coreLogic;

/** Class to manage the environment of the application. 
 * Holds all the objects necessary for a the application
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 * 
 */
public class AppEnvironment {

	private DeckManager deckManager = new DeckManager();

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
	
}
