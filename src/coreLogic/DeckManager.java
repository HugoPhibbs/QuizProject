package coreLogic;

import java.io.Serializable; 
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.FlashCard;

/** Class to manage a collection of decks for a quiz application.
 * Manages CRUD actions do with a collection of Decks. 
 * <p>
 * Acts as a controller to make sure that actions to do with Decks are permissible in the context of
 * other decks, for example if renaming a deck gives it the same name as another deck- which is obviously
 * not permissible. 
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 28/6/21
 * @since 27/6/21
 *
 */
public class DeckManager implements Serializable {
	
	/** ArrayList<Deck> containing the decks of this DeckManager */
	private ArrayList<Deck> deckCollection = new ArrayList<Deck>();
	
	/** Constructor for a DeckManager object
	 * 
	 */
	public DeckManager() {}
	
	/** Finds the deck with the given name deckName. Used internally for CRUD
	 * operations. 
	 * 
	 * @param deckName String for the name of the deck that is requested
	 * @return Deck with the name deckName, otherwise returns null
	 */
	public Deck findDeck(String deckName) {
		for (Deck deck: deckCollection) {
			if (deck.getName().equals(deckName)) {
				return deck;
			}
		}
		return null;
	}
	
	/** Adds a deck to the collection of a DeckManager
	 * <p>
	 * Only adds a deck if a deck with the same name doesn't already
	 * exist
	 * 
	 * @param deck Deck to be added to the collection
	 * @return boolean if deck was added or not
	 */
	public boolean addDeck(Deck deck) {
		if (containsDeck(deck)) {
			// Deck is already contained in deckManager
			return false;
		}
		else {
			return deckCollection.add(deck);
		}
	}
	
	/** Creates a new deck with a name deckName and the given description
	 * then adds this new Deck to the collection of this DeckManager.  
	 * <p>
	 * Checks if the deckName is appropriate, throws an exception if not. 
	 * <p>
	 * If the name of a new deck matches a Deck that has been already created, it returns false,
	 * otherwise true, as per DeckManager.addDeck(Deck)
	 * 
	 * @param deckName String for the new name of a Deck to be created
	 * @param description String for the description of a new Deck
	 * @return boolean result of addDeck(newDeck)
	 * @throws IllegalArgumentException if: <br>
	 * - DeckName isn't valid as per CheckValidInput.nameIsValid(String) 
	 */
	public boolean createDeck(String deckName, String description) throws IllegalArgumentException {
		if (!CheckValidInput.nameIsValid(deckName)) {
			throw new IllegalArgumentException("Deck name isn't valid!");
		}
		Deck newDeck = new Deck(deckName, description);
		return addDeck(newDeck);
	}
	
	/** Removes the deck with name deckName from the collection of
	 * a deckManager. 
	 * <p>
	 * If a deck with name deckName isn't found in the collection, it
	 * returns false, otherwise true. 
	 * 
	 * @param deckName String for the name of the deck to be removed
	 * @return boolean for if a deck with name deckName was removed.
	 */
	public boolean removeDeck(String deckName) {
		Deck foundDeck = findDeck(deckName);
		return (deckCollection.remove(foundDeck));
	}
	
	/** Renames a deck with name deckName with a new name.
	 * <p>
	 * Checks if deckName is appropriate, throws an Exception if not.
	 * <p>
	 * @param deckName String for the name of the Deck to be renamed
	 * @param newDeckName String for the new name of a deck
	 * @return boolean true if deck was renamed. False if a deck is already contained with name newDeckName, otherwise true
	 * @throws IllegalArgumentException if: <br>
	 * - DeckName doesn't belong to a deck in this collection <br>
	 */
	public boolean renameDeck(String deckName, String newDeckName) throws IllegalArgumentException {
		if (!containsDeck(deckName)) {
			throw new IllegalArgumentException("Deck with given name doesn't exist in this collection!");
		}
		else if (containsDeck(newDeckName)) {
			return false;
		}
		else {
			Deck foundDeck = findDeck(deckName);
			foundDeck.setName(newDeckName);
			return true;
		}
	}
	
	/** Changes which Deck a FlashCard belongs to
	 * <p>
	 * Does prechecks for exceptions before passing the actual changing Decks of flashCard to
	 * DeckManager.changeFlashCardDeckHelper(FlashCard, String, String)
	 * 
	 * @param flashCard FlashCard object to be moved
	 * @param sourceDeckName String for the name of the current deck of flashCard
	 * @param destDeckName String for the name of the destination deck for flashCard
	 * @return boolean if the operation was performed or not. See comment above
	 * @throws IllegalArgumentException if: <br>
	 * - The source deck is not in this collection <br>
	 * - The location deck is not in this collection <br>
	 * - The source deck does not contain flashCard <br>
	 */
	public boolean changeFlashCardDeck(FlashCard flashCard, String sourceDeckName, String destDeckName) throws IllegalArgumentException{
		if (!containsDeck(sourceDeckName)) {
			throw new IllegalArgumentException("Source deck is not in this collection!");
		}
		else if (!containsDeck(destDeckName)) {
			throw new IllegalArgumentException("Location deck is not in this collection!");
		}
		else if (!findDeck(sourceDeckName).contains(flashCard)) {
			throw new IllegalArgumentException("Source deck does not contain flashCard!");
		}
		return (changeFlashCardDeckHelper(flashCard, sourceDeckName, destDeckName));
	}
	
	/** Helper method for changeFlashCardDeck
	 * <p>
	 * Makes sure that there is no duplicate FlashCard in the requested deck destination,
	 * returns false if so. 
	 * <p>
	 * Otherwise does the actual changing deck part if it is permissible
	 * 
	 * @param flashCard FlashCard object to change Decks with names sourceDeckName to destDeckName
	 * @param sourceDeckName String for the name of the current Deck that flashCard belongs to
	 * @param destDeckName String for the name of the destination Deck that flashCard is wished to change to
	 * @return boolean if the switching of flashCard from the source deck to the destination deck was done
	 */
	private boolean changeFlashCardDeckHelper(FlashCard flashCard, String sourceDeckName, String destDeckName) {
		if (findDeck(destDeckName).contains(flashCard)) {
			return false;
		}
		else {
			Deck sourceDeck = findDeck(sourceDeckName);
			sourceDeck.removeFlashCard(flashCard);
			Deck destDeck = findDeck(destDeckName);
			return (destDeck.addFlashCard(flashCard)); // Will return true, needed to destDeck didn't contain flashCard before removing from sourceDeck anyways
		}
	}
	
	/** Method that returns if a DeckManager contains a
	 * a Deck in it's collection. 
	 * <p>
	 * Overloaded with DeckManager.containsDeck(String)
	 * 
	 * @param deck Deck to be checked if it is contained in DeckManager
	 * @return boolean value for if deck was found
	 */
	public boolean containsDeck(Deck deck) {
		return (containsDeck(deck.getName()));
	}
	
	/** Method that returns if a DeckManager contains a
	 * Deck with the name deckName in it's collection
	 * <p>
	 * Overloaded with DeckManager.containsDeck(Deck)
	 * 
	 * @param deckName String for the name of a Deck to check if it contained in DeckManager
	 * @return boolean if a deck with a name deckName was found
	 */
	public boolean containsDeck(String deckName) {
		return (findDeck(deckName) != null);
	}
	
	/** Getter method for the a deck collection of a DeckManager object
	 * 
	 * @return ArrayList of Deck objects for the deckCollection of a DeckManager object
	 */
	public ArrayList<Deck> getDeckCollection(){
		return deckCollection;
	}
	
	/** Method to set the deck collection of a DeckManager
	 * <p>
	 * WARNING - USE WISELY, make sure that the deckCollection is already 
	 * pre-checked, i.e. no duplicate names for Decks, and Deck names are valid aswell etc
	 * 
	 * @param deckCollection ArrayList of Deck objects to be set as the collection
	 */
	public void setDeckCollection(ArrayList<Deck> deckCollection){
		this.deckCollection = deckCollection;
	}
}
