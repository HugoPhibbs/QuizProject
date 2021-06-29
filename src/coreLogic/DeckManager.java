package coreLogic;

import java.time.LocalDate; 
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.FlashCard;

/** Class to manage a collection of decks for a quiz application.
 * Manages CRUD actions do with a collection of Decks. 
 * <p>
 * Acts as a controller to make sure that actions to do with Decks are permissable in the context of
 * other decks, for example if renaming a deck gives it the same name as another deck- which is obviously
 * not permissible. 
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 28/6/21
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
	 * <p>
	 * Checks if deckName is appropriate, throws an Exception if not.
	 * <p>
	 * @param deckName String for the name of the Deck to be renamed
	 * @param newDeckName String for the new name of a deck
	 * @return boolean true if deck was renamed.
	 * @throws IllegalArgumentException if: <br>
	 * - DeckName doesn't belong to a deck in this collection <br>
	 * - newDeckName isn't valid
	 */
	public boolean renameDeck(String deckName, String newDeckName) throws IllegalArgumentException {
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
	 * <p>
	 * Checks if the deckName is appropriate, throws an exception if not. 
	 * 
	 * @param deckName String for the new name of a Deck to be created
	 * @param description String for the description of a new Deck
	 * @return boolean true if deck was created and added to the DeckManager collection
	 * @throws IllegalArgumentException if: <br>
	 * - A deck with deckName already exists in the collection <br>
	 * - DeckName isn't valid as per CheckValidInput.nameIsValid(String) 
	 */
	public boolean createDeck(String deckName, String description) throws IllegalArgumentException {
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
	
	/** Adds flashCard to a destination Deck with name deckName
	 * 
	 * @param flashCard FlashCard object to be added to destination Deck
	 * @param deckName String for the name of the destination Deck
	 * @return boolean if operation was performed or not
	 * @throws IllegalArgumentException if:<br>
	 * - A FlashCard already exists identical to flashCard in destination deck <br>
	 * - Destination deck with name deckName doesn't exist in this collection
	 */
	public boolean addFlashCardToDeck(FlashCard flashCard, String deckName) throws IllegalArgumentException {
		if (!containsDeck(deckName)) {
			throw new IllegalArgumentException("Destination deck with name deckName doesn't exist in this collection");
		}
		else if (findDeck(deckName).contains(flashCard)) {
			throw new IllegalArgumentException("A flash card already exists identical to this new FlashCard in this deck");
		}
		else {
			Deck destDeck = findDeck(deckName);
			return (destDeck.addFlashCard(flashCard));
		}
	}
	
	/** Removes flashCard from the deck with name.	
	 * <p>
	 * Returns true if the flashCard was removed. 
	 * 
	 * @param flashCard FlashCard object to be removed from a Deck
	 * @param deckName String for the name of the deck that flashCard is to be removed from
	 * @return boolean if the flashCard was removed from the deck. 
	 * @throws IllegalArgumentException if: <br>
	 * - Deck with given deckName doesn't exist in this collection <br>
	 * - flashCard doesn't exist in a deck with name deckName
	 */
	public boolean removeFlashCardFromDeck(FlashCard flashCard, String deckName) throws IllegalArgumentException {
		if (!containsDeck(deckName)) {
			throw new IllegalArgumentException("Deck with name deckName doesn't exist in this collection");
		}
		else if (!findDeck(deckName).contains(flashCard)) {
			throw new IllegalArgumentException("Flash card doesn't exist in this deck");
		}
		else {
			Deck sourceDeck = findDeck(deckName);
			return (sourceDeck.removeFlashCard(flashCard));
		}
	}
	
	/** Changes which Deck a FlashCard belongs to
	 * <p>
	 * Makes sure that there is no duplicate FlashCard in the requested deck destination
	 * 
	 * @param flashCard FlashCard object to be moved
	 * @param deckName String for the name of the current deck of flashCard
	 * @param newDeckName String for the name of the destination deck for flashCard
	 * @return boolean if the operation was performed or not
	 * @throws IllegalArgumentException if: <br>
	 * - The source deck is not in this collection <br>
	 * - The location deck is not in this collection <br>
	 * - Location deck already has a flash card identical to the one wanting to be moved <br>
	 * - Source deck doesn't contain flashCard
	 */
	public boolean changeFlashCardDeck(FlashCard flashCard, String deckName, String newDeckName) throws IllegalArgumentException{
		// Changes which deck a flash card belongs to
		// Need to check that a flash card doesnt already already exist in deck with name
		// new deckName
		if (!containsDeck(deckName)) {
			throw new IllegalArgumentException("Source deck is not in this collection!");
		}
		else if (!containsDeck(newDeckName)) {
			throw new IllegalArgumentException("Location deck is not in this collection!");
		}
		else if (findDeck(newDeckName).contains(flashCard)) {
			throw new IllegalArgumentException("Location deck already has a flash card identical to the one wanting to be moved!");
		}
		else {
			// Otherwise transfer flashCard from the source Deck to the destination Deck
			removeFlashCardFromDeck(flashCard, deckName); // May throw error if source deck doesn't contain the flashCard
			return (addFlashCardToDeck(flashCard, newDeckName)); // Will return true
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
