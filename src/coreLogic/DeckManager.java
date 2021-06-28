package coreLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.FlashCard;

/** Class to manage a collection of decks for a quiz application.
 * Manages CRUD actions do with a collection of Decks
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 *
 */
public class DeckManager {
	
	/** ArrayList<Deck> containing the decks of this DeckManager */
	private ArrayList<Deck> deckCollection = new ArrayList<Deck>();
	
	/** Constructor for a DeckManager object
	 * 
	 */
	public DeckManager() {}
	
	/** Adds a deck to the collection of a DeckManager
	 * Only adds a deck if a deck with the same name doesn't already
	 * exist
	 * 
	 * @param deck Deck to be added to the collection
	 * @return boolean if deck was added or not
	 */
	public boolean addDeck(Deck deck) {
		if (this.containsDeck(deck)) {
			// Deck is already contained in deckManager
			return false;
		}
		else {
			return deckCollection.add(deck);
		}
	}
	
	/** Removes the deck with name deckName from the collection of
	 * a deckManager. 
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
	
	/** Renames a deck with name deckName with a new name.
	 * 
	 * Checks if deckName is appropriate, throws an Exception if not.
	 * 
	 * @param deckName String for the name of the Deck to be renamed
	 * @param newDeckName String for the new name of a deck
	 * @return boolean true if deck was renamed.
	 * @throws IllegalArgumentException if deckName doesn't belong to a deck
	 * in this colection or newDeckName isn't valid
	 */
	public boolean renameDeck(String deckName, String newDeckName) {
		if (!containsDeck(deckName)) {
			throw new IllegalArgumentException("Deck with given name doesn't exist in this collection!");
		}
		else if (containsDeck(newDeckName)) {
			throw new IllegalArgumentException("New name is the same name as a pre-existing deck!");
		}
		else {
			Deck foundDeck = findDeck(deckName);
			foundDeck.setName(newDeckName);
			return true;
		}
	}
	
	/** Creates a new deck with a name deckName and the given description
	 * then adds this new Deck to the collection of this DeckManager.  
	 * 
	 * Checks if the deckName is appropriate, throws an exception if not. 
	 * 
	 * @param deckName String for the new name of a Deck to be created
	 * @param description String for the description of a new Deck
	 * @return boolean true if deck was created and added to the DeckManager collection
	 * @throws IllegalArgumentExceptionn if a deck with deckName already exists in the collection
	 * or deckName isn't valid as per CheckValidInput.nameIsValid(String)
	 */
	public boolean createDeck(String deckName, String description) {
		if (containsDeck(deckName)) {
			throw new IllegalArgumentException("Deck in collection already the same name as this new name!");
		}
		else if (!CheckValidInput.nameIsValid(deckName)) {
			throw new IllegalArgumentException("Deck name isn't valid!");
		}
		else {
			LocalDate currentDate = LocalDate.now();
			Deck newDeck = new Deck(deckName, description, currentDate);
			addDeck(newDeck);
			return true;
		}
	}
	
	public boolean deckNameIsValid() {
		// TODO implement!
		return true;
	}
	
	public boolean addCardToDeck(FlashCard flashCard, String deckName) {
		Deck foundDeck = findDeck(deckName);
		// TODO implement
		// Finds the deck with name deckName
		// and adds flashCard to it
		// Returns true if operation was successful or not, ie deck with deckName
		// Was found and not a duplicate flashCard already in deck
		return false;
	}
	
	public boolean changeFlashCardDeck(FlashCard flashCard, String deckName, String newDeckName) {
		// Changes which deck a flash card belongs to
		// Need to check that a flash card doesnt already already exist in deck with name
		// new deckName
		return true;
	}
	
	/** Method that returns if a DeckManager contains a
	 * a Deck in it's collection. 
	 * 
	 * Overloaded with DeckManager.containsDeck(String)
	 * 
	 * @param deck Deck to be checked if it is contained in DeckManager
	 * @return boolean if deck was found
	 */
	public boolean containsDeck(Deck deck) {
		return (findDeck(deck.getName()) != null);
	}
	
	/** Method that returns if a DeckManager contains a
	 * Deck with the name DeckName in it's collection
	 * 
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
	 * @return ArrayList<Deck> for the deckCollection of a DeckManager object
	 */
	public ArrayList<Deck> getDeckCollection(){
		return deckCollection;
	}
	
	/** Method to set the deck collection of a DeckManager
	 * WARNING - USE WISELY, make sure that the deckCollection is already 
	 * prechecked, ie no duplicate names for decks, and deck are valid aswell etc
	 * 
	 * @param deckCollection ArrayList<Deck> to be set as the collection
	 */
	public void setDeckCollection(ArrayList<Deck> deckCollection){
		this.deckCollection = deckCollection;
	}
}
