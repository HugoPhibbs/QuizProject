package coreLogic;

import java.time.LocalDate; 
import java.util.ArrayList;

import coreObjects.Deck;
import coreObjects.FlashCard;
import coreObjects.User;

/** Represents a FlashCardQuiz.
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 28/6/21
 * @since 25/6/21
 * 
 */
public class FlashCardQuiz {
	
	/** Deck that is currently being quizzed on */
	private Deck deck;
	/** Flashcard for the current flash card for this quiz */
	private FlashCard currentFlashCard;
	/** Current side of currentFlashCard being shown to a user */
	private String currentFlashCardSide = "FRONT";
	/** ArrayList of all the unique FlashCards for this quiz
	 * Both all the new and due cards that are being tested on, with no duplicates.  */
	private ArrayList<FlashCard> uniqueCards;
	/** ArrayList<ArrayList<FlashCard>> of the current state of the quiz interms of cards 
	 * See Deck.cardsToQuiz(int, LocalDate) for more details*/
	private ArrayList<ArrayList<FlashCard>> cardsToQuiz;
	/** Instance of QuizStats for current quiz */
	private QuizStats quizStats = new QuizStats();
	/** UserStats object for this this quiz application is updated at the end of the quiz*/
	private UserStats userStats;
	
	/** Constructor for FlashCardQuiz
	 * 
	 * @param deck Deck object for this quiz
	 */
	FlashCardQuiz(Deck deck, UserStats userStats){
		this.deck = deck;
		this.userStats = userStats;
	} 
	
	/** Starts a new Quiz
	 * 
	 * @param maxNewCards int for the max number of new cards that a User wants to see 
	 */
	public void startQuiz(int maxNewCards) {
		// TODO implement
		
		// Starts a quiz 
		LocalDate currentDate = LocalDate.now();
		this.cardsToQuiz = deck.flashCardsToQuiz(maxNewCards, currentDate);
		this.uniqueCards = cardsToQuiz.get(0); // All cards that are being quizzed on, both new and due cards
	}
	
	public void endQuiz() {
		// TODO implement
		
		// Ends a quiz
		
		updateQuizFlashCards();
		userStats.addQuizStatsEntry(quizStats);
	}
	
	/** Updates all the review dates of the cards that have been
	 * seen once the quiz is over
	 * <p>
	 * Iterates over uniqueCards as these are all the cards that have been quizzed on
	 * 
	 */
	private void updateQuizFlashCards() {
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
	 * <p>
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
