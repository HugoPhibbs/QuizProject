package coreObjects;

import java.time.LocalDate; 
import java.util.ArrayList;
import java.util.Collections;

/** Represents a Deck. Contains FlashCard objects
 * 
 * @author Hugo Phibbs 
 * @author Tom Berry
 * @version 26/6/21
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
	 * @param dateOfCreation int for the date of creation of a deck
	 */
	public Deck(String name, String description, LocalDate dateOfCreation){
		this.name = name;
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
	
	/** Determines if two deck objects are equal to each other. 
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
	
	/** Method for adding a card to a deck
	 * 
	 * @param flashCard FlashCard object to be added to a deck
	 * @returns boolean if a flashCard was added or not
	 */
	public boolean addCard(FlashCard flashCard) {
		if (!cards.contains(flashCard)) {
			return (cards.add(flashCard));
		}
		else {
			return false;
		}
	}
	
	/** Method for removing a card from a deck
	 * 
	 * @param flashCard FlashCard object to be removed from a deck
	 * @return boolean if the flashCard was removed from cards, ie it was found or not. 
	 */
	public boolean removeCard(FlashCard flashCard) {
		return (cards.remove(flashCard));
	}
	
	/** Returns the cards that a user is to be quizzed on. Shuffles the deck before choosing new cards. 
	 * This ensures that new cards are in random order, but cards that are due are always added to be
	 * quizzed. 
	 * 
	 * @param maxNewCards int for the max number of new cards that a user wants to see
	 * @param currentDate LocalDate object for the current date
	 * @return ArrayList<FlashCard> for the cards to be quizzed on
	 */
	public ArrayList<FlashCard> cardsToQuiz(int maxNewCards, LocalDate currentDate){
		ArrayList<FlashCard> cardsToQuiz = new ArrayList<FlashCard>();
		
		Collections.shuffle(cards);
		int newCardsAdded = 0;
		
		for (FlashCard flashCard: cards) {
			if (flashCard.isNew() && newCardsAdded < maxNewCards) {
				// Card is new, and added new cards is less than max
				cardsToQuiz.add(flashCard);
				newCardsAdded += 1;
			}
		    else if (flashCard.isDue(currentDate)) {
				cardsToQuiz.add(flashCard);
			}
		}
		return cardsToQuiz;
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
	
	/** Setter method for the name of a Deck
	 * 
	 * @param name String for the name of a deck to be set
	 */
	public void setName(String name) {
		this.name = name;
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
