package CoreClasses;

import java.util.ArrayList;

/** Represents a Deck. Contains FlashCard objects
 * 
 * @author Hugo Phibbs 
 * @author Tom Berry
 * @version 25/6/21
 * @since 25/6/21
 * 
 */
public class Deck {
	
	/** String for the name of this deck **/
	private String name;
	/** ArrayList containing the cards for this deck **/
	private ArrayList<FlashCard> cards = new ArrayList<FlashCard>();
	/** int for the date of creation of this deck **/
	private int dateOfCreation;
	
	
	/** Constructor for a Deck object
	 * 
	 * @param name String for the name of a deck
	 * @param dateOfCreation int for the date of creation of a deck
	 */
	Deck(String name, int dateOfCreation){
		this.name = name;
		this.dateOfCreation = dateOfCreation;
	}
	
	/** Method for adding a card to a deck
	 * All it does is add to the cards ArrayList. Made it into a method because
	 * this will often be called and is cleaner than a Deck.getCards.addCard(card)
	 * 
	 * @param flashCard FlashCard object to be added to a deck
	 */
	public void addCard(FlashCard flashCard) {
		cards.add(flashCard);
	}
	
	/** Method for removing a card from a deck
	 * All it does is remove a card from the cards ArrayList. Made it into a method because
	 * this will often be called and is cleaner than a Deck.getCards.removeCard(card)
	 * 
	 * @param flashCard FlashCard object to be removed from a deck
	 */
	public void removeCard(FlashCard flashCard) {
		cards.remove(flashCard);
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
	 * @return int for the date of creation of a deck
	 */
	public int getDateOfCreation() {
		return this.dateOfCreation;
	}
	
	/** Getter method for the cards of deck
	 * 
	 * @return ArrayList<FlashCard> representation of the flash cards that a deck has
	 */
	public ArrayList<FlashCard> getCards() {
		return this.cards;
	}
	
	/** Method that returns the size of a deck. 
	 * Again borrows functionality from ArrayList, as this will often be called
	 * and is cleaner than Deck.getCards.size()
	 * 
	 * @return int for the size of deck
	 */
	public int size() {
		return cards.size();
	}
	
	/** Setter method for the name of a deck
	 * 
	 * @param name String for the name of a deck to be set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
