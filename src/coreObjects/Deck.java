package coreObjects;

import java.time.LocalDate;  
import java.util.ArrayList;
import java.util.Collections;

import coreLogic.CheckValidInput;

/** Represents a Deck. Contains FlashCard objects
 * 
 * @author Hugo Phibbs 
 * @author Tom Berry
 * @version 28/6/21
 * @since 25/6/21
 * 
 */
public class Deck {
	
	/** String for the name of this deck **/
	private String name;
	/** String for the description of a deck. E.g. a short note on what this deck contains**/
	private String description;
	/** ArrayList containing the cards for this deck **/
	private ArrayList<FlashCard> cards = new ArrayList<FlashCard>();
	/** LocalDate object for the date of creation of this deck **/
	private LocalDate dateOfCreation;
	
	
	/** Constructor for a Deck object
	 * 
	 * @param name String for the name of a deck
	 * @param description String for a brief description of a Deck
	 * @param dateOfCreation int for the date of creation of a deck
	 */
	public Deck(String name, String description, LocalDate dateOfCreation){
		setName(name); // Checks for valid name
		this.description = description;
		this.dateOfCreation = dateOfCreation;
	}
	
	/** Returns a String representation of a Deck object
	 * 
	 */
	@Override
	public String toString() {
		return String.format("Deck has name: %s, containing %d cards", name, this.size());
	}
	
	/** Determines if two Deck objects are equal to each other. 
	 * Checks if their name and size are equal.
	 * 
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof Deck) {
			Deck deck = (Deck) object;
			return (deck.getName().equals(this.name) && 
					deck.size() == this.size());
		}
		return false;
	}
	
	/** Adds a FlashCard to a deck. 
	 * <p>
	 * Will not add a FlashCard to a deck if a FlashCard with same front and back text
	 * as the new flashCard is the same.
	 * 
	 * @param flashCard FlashCard object to be added to a deck
	 * @return boolean if a flashCard was added or not
	 */
	public boolean addFlashCard(FlashCard flashCard) {
		if (!contains(flashCard)) {
			return (cards.add(flashCard));
		}
		else {
			return false;
		}
	}
	
	/** Checks if a deck contains a given FlashCard object
	 * 
	 * @param flashCard FlashCard object to be checked if it is contained in Deck
	 * @return boolean if a deck contains flashCard or not
	 */
	public boolean contains(FlashCard flashCard) {
		return (cards.contains(flashCard));
	}
	
	/** Removes a FlashCard from a deck
	 * 
	 * @param flashCard FlashCard object to be removed from a deck
	 * @return boolean if the flashCard was removed from cards, ie it was found or not. 
	 */
	public boolean removeFlashCard(FlashCard flashCard) {
		return (cards.remove(flashCard));
	}
	
	/** Returns the FlashCards that a user is to be quizzed on. Shuffles the deck before choosing new cards. 
	 * This ensures that new cards are in random order, but cards that are due are always added to be
	 * quizzed. 
	 * <p>
	 * Returned nested ArrayList has format [initialQueue, againQueue, finalQueue].
	 * <p>
	 * New cards are added both to the initial card queue and the final card queue.
	 * <p>
	 * Final queue only contains new cards, as cards that have already been viewed by default
	 * don't appear in the final queue, this is just part of the learning algorithm. 
	 * 
	 * @param maxNewCards int for the max number of new cards that a user wants to see
	 * @param currentDate LocalDate object for the current date
	 * @return nested ArrayList containing the cards to be quizzed on
	 */
	public ArrayList<ArrayList<FlashCard>> flashCardsToQuiz(int maxNewCards, LocalDate currentDate){
		
		ArrayList<ArrayList<FlashCard>> flashCardsToQuiz = createFlashCardsToQuizTable();
		
		Collections.shuffle(cards);
		int newCardsAdded = 0;
		
		for (FlashCard flashCard: cards) {
			if (flashCard.isNew() && newCardsAdded < maxNewCards) {
				// Card is new, and added new cards is less than max
				// Add to inital queue and final queue
				flashCardsToQuiz.get(0).add(flashCard);
				flashCardsToQuiz.get(2).add(flashCard);
				newCardsAdded += 1;
			}
		    else if (flashCard.isDue(currentDate)) {
		    	// Otherwise if the flashCard is due
		    	flashCardsToQuiz.get(0).add(flashCard);
			}
		}
		return flashCardsToQuiz;
	}
	
	/** Fills an ArrayList with 3 rows of ArrayList<FlashCard>, and returns it.
	 *  Used locally by cardsToQuiz(int, LocalDate)
	 *  <p>
	 *  Table is in format [initialQueue, againQueue, FinalQueue]
	 * 
	 * @return ArrayList<ArrayList<FlashCard>> table for cards to quiz
	 */
	private ArrayList<ArrayList<FlashCard>> createFlashCardsToQuizTable() {
		ArrayList<ArrayList<FlashCard>> flashCardsToQuiz = new ArrayList<ArrayList<FlashCard>>();
		for (int i = 0; i < 3; i++) {
			flashCardsToQuiz.add(new ArrayList<FlashCard>());
		}
		return flashCardsToQuiz;
	}
	
	/** Method that returns the size of a deck. 
	 * Again borrows functionality from ArrayList, as this will often be called
	 * and is cleaner than Deck.getCards.size()
	 * Keep as a method and not an attribute, as having it as an attribute means
	 * that it need to be constantly updated
	 * 
	 * @return int for the size of deck
	 */
	public int size() {
		return cards.size();
	}
	
	/** Getter method for the name of a deck
	 * 
	 * @return String for the name of deck
	 */
	public String getName() {
		return name;
	}
	
	/** Getter method for the date of creation of deck
	 * 
	 * @return LocalDate for the date of creation of a deck
	 */
	public LocalDate getDateOfCreation() {
		return dateOfCreation;
	}
	
	/** Getter method for the cards of deck
	 * 
	 * @return ArrayList<FlashCard> representation of the flash cards that a deck has
	 */
	public ArrayList<FlashCard> getCards() {
		return cards;
	}
	
	/** Getter method for the description of a deck
	 * 
	 * @return String for the description of a deck
	 */
	public String getDescription() {
		return description;
	}
	
	/** Setter method for the name of a Deck. 
	 * Checks if a name is valid before setting. 
	 * 
	 * @param name String for the name of a deck to be set
	 * @throws IllegalArgumentException if name isnt' valid, see CheckValidInput.nameIsValid(String)
	 */
	public void setName(String newName) {
		if (!CheckValidInput.nameIsValid(newName)) {
			String msg = String.format(CheckValidInput.getVALID_NAME_REQUIREMENTS());
			throw new IllegalArgumentException(msg);
		}
		else {
			this.name = newName;
		}
	}
	
	/** Setter method for the description of a Deck
	 * 
	 * @param description String for the description to be set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** Setter method for the cards of a deck. 
	 * ONLY TO BE USED FOR TESTING
	 * 
	 * @param cards ArrayList<FlashCard> to be set as the cards for this deck
	 */
	public void setCards(ArrayList<FlashCard> cards) {
		this.cards = cards;
	}
}
