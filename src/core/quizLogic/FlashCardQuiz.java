package core.quizLogic;

import core.coreObjects.Deck;
import core.stats.UserStats;

/**
 * Represents a FlashCardQuiz.
 * <p>
 * This extension of Quiz is created to mimic the behavior of if you were
 * playing flashcards with yourself to self study
 * 
 * @author Hugo Phibbs
 * @version 2/7/21
 * @since 25/6/21
 * 
 */
public class FlashCardQuiz extends Quiz {

	/**
	 * Current side of getCurrentFlashCard() being shown to a user
	 */
	private String currentFlashCardSide = "FRONT";

	/**
	 * Constructor for FlashCardQuiz
	 * 
	 * @param deck      Deck object for this quiz
	 * @param userStats UserStats object for this Application.
	 */
	public FlashCardQuiz(Deck deck, UserStats userStats) {
		super(deck, userStats);
	}

	/**
	 * Handles request of seeing the next FlashCard for this Quiz.
	 * <p>
	 * Overrides nextFlashCard() in Quiz, this is to make it clearer in the GUI what
	 * is going on, and because this method also needs to call
	 * resetCurrentFlashCardSide
	 * 
	 * @throws QuizFinishedException if the quiz is finished, i.e. there are no more
	 *                               cards to see for this quiz
	 */
	@Override
	public void nextFlashCard() throws QuizFinishedException {
		super.nextFlashCard();
		resetCurrentFlashCardSide();
	}

	// **********o***** Starting a Quiz ***************** //

	/**
	 * Finds if a quiz can be started. Does this by checking if the cards to be
	 * quizzed on for the current deck isn't empty
	 * <p>
	 * Public for testing
	 * 
	 * @return boolean value if a quiz can be started or not
	 */
	public boolean canStartQuiz() {
		return (!getCardsToQuiz().isEmpty());
	}

	// ******* Dealing with the current side of the current FlashCard ******** //

	/**
	 * Resets the currentFlashCardSide to "FRONT"
	 * <p>
	 * Put it into a separate method to make it more clear to read
	 */
	private void resetCurrentFlashCardSide() {
		currentFlashCardSide = "FRONT";
	}

	/**
	 * Handles request of wanting to flip a flash card.
	 * <p>
	 * Note: by default the getCurrentFlashCard() side is set to "FRONT" every time
	 * a new card is seen
	 * <p>
	 * Called after button pressed in GUI to flip the current flash card
	 * 
	 * @return String for the text of the side of getCurrentFlashCard() that a user
	 *         wants to see
	 */
	public String flipCurrentFlashCard() {
		changeCurrentFlashCardSide();
		return currentFlashCardSideText();
	}

	/**
	 * Finds the current text that should be displayed to a user
	 * 
	 * @return String for either the front or back text of getCurrentFlashCard()
	 */
	public String currentFlashCardSideText() {
		switch (currentFlashCardSide) {
			case "FRONT":
				return getCurrentFlashCard().getFrontText();
			case "BACK":
				return getCurrentFlashCard().getBackText();
			default:
				throw new IllegalStateException("Current FlashCard side isn't valid!");
		}
	}

	/**
	 * Handles changing the getCurrentFlashCard() side, either from FRONT -> BACK or
	 * BACK -> FRONT.
	 */
	public void changeCurrentFlashCardSide() {
		switch (currentFlashCardSide) {
			case "FRONT":
				currentFlashCardSide = "BACK";
				break;
			case "BACK":
				currentFlashCardSide = "FRONT";
				break;
			default:
				throw new IllegalStateException("Current FlashCard side isn't valid!");
		}
	}

	// ***************** Getter methods ********************** //

	/**
	 * Getter method for currentFlashCardSide
	 * 
	 * @return String as described
	 */
	public String getCurrentFlashCardSide() {
		return currentFlashCardSide;
	}

	// **************** Setter Methods ***************** //

	/**
	 * Setter method for the currentFlashCardSide of this quiz
	 * 
	 * @param currentFlashCardSide String to be set as the currentFlashcardSide
	 */
	public void setCurrentFlashCardSide(String currentFlashCardSide) {
		this.currentFlashCardSide = currentFlashCardSide;
	}
}