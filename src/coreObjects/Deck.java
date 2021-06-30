package coreObjects;

import java.io.Serializable;
import java.time.LocalDate;  
import java.util.ArrayList;
import java.util.Collections;

import coreLogic.CheckValidInput;

/** Represents a Deck. Contains FlashCard objects
 * <p>
 * Supports CRUD actions to do with FlashCards for a Deck
 * 
 * @author Hugo Phibbs 
 * @author Tom Berry
 * @version 28/6/21
 * @since 25/6/21
 * 
 */
public class Deck implements Serializable {
	
	/** String for the name of this deck **/
	private String name;
	/** String for the description of a deck. E.g. a short note on what this deck contains**/
	private String description;
	/** ArrayList containing the FlashCards for this deck **/
	private ArrayList<FlashCard> flashCards = new ArrayList<FlashCard>();
	/** LocalDate object for the date of creation of this deck **/
	private LocalDate dateOfCreation;
	
	
	/** Constructor for a Deck object
	 * 
	 * @param name String for the name of a deck
	 * @param description String for a brief description of a Deck
	 * @param dateOfCreation int for the date of creation of a deck
	 */
	public Deck(String name, String description){
		setName(name); // Checks for valid name
		this.description = description;
		this.dateOfCreation = LocalDate.now();
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
	
	//******************************** METHODS FOR CRUD OF FLASHCARDS ******************************

	/** Checks if a deck contains a given FlashCard object
	 * 
	 * @param flashCard FlashCard object to be checked if it is contained in Deck
	 * @return boolean if a deck contains flashCard or not
	 */
	public boolean contains(FlashCard flashCard) {
		return (flashCards.contains(flashCard));
	}
	
	/** Checks if a Deck contains a FlashCard with inputed front and back text
	 * <p>
	 * Creates a temporary FlashCard object and checks if this is in the flashCards for this deck
	 * 
	 * @param frontText String for the front text of a FlashCard to be check if it is contained in this deck
	 * @param backText String for the back text of a FlashCard to be check if it is contained in this deck
	 * @return boolean if the operation was completed
	 */
	public boolean contains(String frontText, String backText) {
		FlashCard tempFlashCard = new FlashCard(frontText, backText);
		return (contains(tempFlashCard));
	}
	
	/** Adds a FlashCard to a deck. 
	 * <p>
	 * Will not add a FlashCard to a deck if a FlashCard with same front and back text
	 * as the new flashCard is the same.
	 * 
	 * @param flashCard FlashCard object to be added to a deck
	 * @return boolean if a flashCard was added or not, false if deck already contains a FlashCard identical to flashCard
	 */
	public boolean addFlashCard(FlashCard flashCard) {
		if (!contains(flashCard)) {
			return (flashCards.add(flashCard));
		}	
		else {
			return false;
		}
	}
	
	/** Removes a FlashCard from a deck
	 * 
	 * @param flashCard FlashCard object to be removed from a deck
	 * @return boolean if the flashCard was removed from cards, i.e. it was found or not. 
	 */
	public boolean removeFlashCard(FlashCard flashCard) {
		return (flashCards.remove(flashCard));
	}

	/** Edits the front and back text of a flashCard object
	 * <p>
	 * Makes sure that another FlashCard doesn't already have the same front and back text that is 
	 * wished to be edited to for flashCard. Returns false if so. 
	 * 
	 * @param flashCard FlashCard object to be edited
	 * @param newFrontText String for the new proposed front text of flashCard
	 * @param newBackText String for the new proposed back text of flashCard
	 * @return boolean if the flashCard was edited or not.
	 * @throws IllegalArgumentException if: <br>
	 * - Variable flashCard isn't contained in this deck <br>
	 */
	public boolean editFlashCard(FlashCard flashCard, String newFrontText, String newBackText) {
		if (!contains(flashCard)) {
			throw new IllegalArgumentException("FlashCard object isn't contained in this deck!, Please check for bugs!");
		}
		else if (contains(newFrontText, newBackText)) {
			return false;
		}
		else {
			flashCard.setFrontText(newFrontText);
			flashCard.setBackText(newBackText);
			return true;
		}
	}
	
	//******************************** METHODS FOR QUIZZING *************************************************
	
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
		
		Collections.shuffle(flashCards);
		int newCardsAdded = 0;
		
		for (FlashCard flashCard: flashCards) {
			if (flashCard.isNew() && newCardsAdded < maxNewCards) {
				// Card is new, and added new cards is less than max
				// Add to initial queue and final queue
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
	
	//********************************* GETTERS AND SETTERS ****************************************
	
	/** Method that returns the size of a deck. 
	 * <p>
	 * Again borrows functionality from ArrayList, as this will often be called
	 * and is cleaner than Deck.getflashCards.size()
	 * <p>
	 * Keep as a method and not an attribute, as having it as an attribute means
	 * that it need to be constantly updated
	 * 
	 * @return int for the size of deck
	 */
	public int size() {
		return flashCards.size();
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
	public ArrayList<FlashCard> getFlashCards() {
		return flashCards;
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
	
	/** Setter method for the flashCards of a deck. 
	 * ONLY TO BE USED FOR TESTING
	 * 
	 * @param cards ArrayList<FlashCard> to be set as the cards for this deck
	 */
	public void setFlashCards(ArrayList<FlashCard> flashCards) {
		this.flashCards = flashCards;
	}
}
