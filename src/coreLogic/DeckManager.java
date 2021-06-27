package coreLogic;

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
	
	private ArrayList<Deck> deckCollection = new ArrayList<Deck>();
	
	public DeckManager() {
		
	}
	
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
	
	/** Finds the deck with the given name deckName. 
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
	 * Checks if a deck already exists with the name newDeckName.
	 * 
	 * @param deckName String for the name of the Deck to be renamed
	 * @param newDeckName String for the new name of a deck
	 * @return boolean if a deck with deckName was found or not, 
	 * @throws IllegalArgumentException if newDeckName isn't valid
	 */
	public boolean renameDeck(String deckName, String newDeckName) {
		// TODO implement throwing of InvalidNameException
		Deck foundDeck = findDeck(deckName);
		if (foundDeck == null) {
			return false;
		}
		else if (containsDeck(newDeckName)) {
			throw new IllegalArgumentException("New name is the same as a pre-existing deck!");
		}
		else {
			foundDeck.setName(newDeckName);
			return true;
		}
	}
	
	public boolean addCardToDeck(FlashCard flashCard, String deckName) {
		// TODO implement
		// Finds the deck with name deckName
		// and adds flashCard to it
		// Returns true if operation was successful or not, ie deck with deckName
		// Was found and not a duplicate flashCard already in deck
		return false;
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
	 * prechecked, ie no duplicate names for decks, and deck are valid aswell
	 * 
	 * @param deckCollection ArrayList<Deck> to be set as the collection
	 */
	public void setDeckCollection(ArrayList<Deck> deckCollection){
		this.deckCollection = deckCollection;
	}
}
