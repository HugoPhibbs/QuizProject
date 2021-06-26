package coreLogic;

import java.time.LocalDate;
import java.util.ArrayList;

import coreObjects.*;

/** Represents a flash card quiz
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 26/5/21
 * @version 25/5/21
 * 
 */
public class FlashCardQuiz {
	
	/** Deck that is currently being quizzed on */
	private Deck deck;
	/** Flashcard for the current flash card for this quiz */
	private FlashCard currentFlashCard;
	/** Current side of currentFlashCard being shown to a user */
	private String currentFlashCardSide = "FRONT";
	/** ArrayList of all the unique FlashCards for this quiz */
	private ArrayList<FlashCard> uniqueCards;
	/** ArrayList<ArrayList<FlashCard>> of the current state of the quiz interms of cards 
	 * See Deck.cardsToQuiz(int, LocalDate) for more details*/
	private ArrayList<ArrayList<FlashCard>> cardsToQuiz;
	
	/** Constructor for FlashCardQuiz
	 * 
	 * @param deck Deck object for this quiz
	 */
	FlashCardQuiz(Deck deck){
		this.deck = deck;
	} 

	public void startQuiz(int maxNewCards) {
		// TODO implement
		
		// Starts a quiz 
		LocalDate currentDate = LocalDate.now();
		this.cardsToQuiz = deck.cardsToQuiz(maxNewCards, currentDate);
		this.uniqueCards = cardsToQuiz.get(0);
	}
	
	public void endQuiz() {
		// TODO implement
		
		// Ends a quiz

		updateQuizCards();
	}
	
	/** Updates all the review dates of the cards that have been
	 * seen once the quiz is over	
	 */
	private void updateQuizCards() {
		for (FlashCard flashCard : uniqueCards) {
			flashCard.updateNextReviewDate();
		}
	}
	
	private FlashCard nextFlashCard() {
		// TODO implement
		
		// Handles request of next card, after either "AGAIN" or "OK" was pressed
		// Returns the next Flash Card to be shown to a user
		
		// Current side needs to be set to FRONT
		return null;
	}
	
	/** Handles request of wanting to flip a flash card. If the current flash card has it's front showing. 
	 * Called after button pressed in GUI to flip the current flash card
	 * 
	 * @return String for the side of currentFlashCard that a user wants to see
	 */
	public String flipCurrentFlashCard() {
		if (currentFlashCardSide.equals("FRONT")) {
			currentFlashCardSide = "BACK";
			return currentFlashCard.getBackText();
		}
		else {
			currentFlashCardSide = "FRONT";
			return currentFlashCard.getFrontText();
		}
	}
	
	private void flashCardAgain() {
		// TODO implement
		
		// Handles request of card "AGAIN" button from GUI
	}
	
	private void flashCardOk() {
		// TODO implement
		
		// Handles request of card "OK" button from GUI
	}
}
