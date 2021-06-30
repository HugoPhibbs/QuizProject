package coreLogic;

import java.time.LocalDate;  
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import coreObjects.Deck;
import coreObjects.FlashCard;

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
	
	/** FlashCard for the current flash card for this quiz */
	private FlashCard currentFlashCard;
	
	/** Current side of currentFlashCard being shown to a user */
	private String currentFlashCardSide = "FRONT";
	
	private Queue<FlashCard> initialQueue = new LinkedList<FlashCard>();
	
	private Queue<FlashCard> againQueue = new LinkedList<FlashCard>();
	
	private Queue<FlashCard> finalQueue = new LinkedList<FlashCard>();
	
	private String currentQueue;
	
	/** ArrayList<FlashCard> for all the cards to be quizzed on throughout a quiz */
	private ArrayList<FlashCard> cardsToQuiz;
	
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
		initialQueue.addAll(cardsToQuiz);
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
	 * in this quiz.
	 * <p>
	 * FlashCards will only have their nextReviewDates updated if the quiz finishes!
	 * 
	 */
	private void updateQuizFlashCards() {
		for (FlashCard flashCard : cardsToQuiz) {
			flashCard.updateNextReviewDate();
		}
	}
	
	/** Handles request of pressing NEXT in the GUI. 
	 * <p>
	 * If the quiz is finished, then the currentFlashCard is null. 
	 * This will be dealt with in the GUI. 
	 * 
	 * @return FlashCard object that is next, i.e. the currentFlashCard
	 */
	public FlashCard nextFlashCard() {
		updateCurrentFlashCard();
		resetCurrentFlashCardSide();
		return currentFlashCard;
	}
	
	/** Checks if a quiz is finished or not
	 * Does this by seeing if currentFlashCard is equal to null or not
	 * 
	 * @return boolean value if a quiz is finished or not
	 */
	public boolean quizFinished() {
		return (currentFlashCard == null);
	}
	
	/** Updates the value of currentFlashCard attribute
	 * <p>
	 * currentFlashCard is set to null if there are no more cards to be quizzed on
	 * 
	 */
	public void updateCurrentFlashCard() {
		if (!initialQueue.isEmpty()) {
			currentFlashCard = initialQueue.remove();
			currentQueue = "INITIAL";
		}
		else if (!finalQueue.isEmpty()) {
			currentFlashCard  =  finalQueue.remove();
			currentQueue = "FINAL";
		}
		else if (!againQueue.isEmpty()) {
			currentFlashCard = againQueue.remove();
			currentQueue = "AGAIN";
		}
		else {
			// No more cards quiz finished
			currentFlashCard = null;
			currentQueue = null;
		}
	}
	
	/** Handles request of wanting to flip a flash card. If the current flash card has it's front showing. 
	 * <p>
	 * By default the currentFlashCard side is set to "FRONT" every time a new card is seen
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
	
	/** Resets the currentFlashCardSide to "FRONT"
	 * <p> 
	 * Put it into a separate method to make it more clear to read
	 */
	public void resetCurrentFlashCardSide() {
		currentFlashCardSide = "FRONT";
	}
	
	
	private void flashCardAgain() {
		// TODO update val of quizStats
		againQueue.add(currentFlashCard);
		finalQueue.add(currentFlashCard);
	}
	
	/** Handles request of pressing OK in GUI
	 * <p>
	 * Adds the currentFlashCard to the finalQueue only if the current Queue
	 * isn't the final queue and the currentFlashCard is new. 
	 */
	private void flashCardOk() {
		// TODO update val of quizStats
		if (currentFlashCard.isNew() && !currentQueue.equals("FINAL")) {
			finalQueue.add(currentFlashCard);
		}		
	}
}
