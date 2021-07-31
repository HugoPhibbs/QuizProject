package core.coreLogic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import core.stats.QuizStats;
import core.stats.UserStats;

/**
 * Represents a FlashCardQuiz.
 * <p>
 * The current state of a quiz can be reflected with 3 queues, the initialQueue,
 * the againQueue and the finalQueue.
 * <p>
 * How the quiz algorithm works: <br>
 * - The user sees cards from one of the queues at once as long as they aren't
 * empty. However the priority is initial<-final<-again. the algorithm will tend
 * to switch between the final and again queues <br>
 * - At the start of the quiz, a user will see all the cards from initialQueue
 * until this queue is empty. If the initialQueue is empty, then FlashCards are
 * taken from the finalQueue or againQueue, in order for what ever one isn't
 * empty <br>
 * - For each card that they see from any queue, they can either press AGAIN or
 * OK.<br>
 * <p>
 * Pressing OK: <br>
 * - While on the initial or again queue: If a user presses OK for a
 * currentFlashCard that is new, this FlashCard is added to the final queue,
 * <br>
 * - If the card isn't new or the current queue is the final queue (has been
 * seen at-least once before) then nothing happens <br>
 * <p>
 * Pressing AGAIN <br>
 * - No matter if a card is new or not, if AGAIN is pressed, then
 * currentFlashCard is added both to the final and again queues. <br>
 * <p>
 * Stats<br>
 * At each point in the quiz, statistics from the quiz are added to an instance
 * of QuizStats.
 * 
 * @author Hugo Phibbs
 * @version 2/7/21
 * @since 25/6/21
 * 
 */
public class FlashCardQuiz {

	/**
	 * Deck that is currently being quizzed on
	 */
	private Deck deck;
	/**
	 * FlashCard for the current flash card for this quiz
	 */
	private FlashCard currentFlashCard;
	/**
	 * Current side of currentFlashCard being shown to a user
	 */
	private String currentFlashCardSide = "FRONT";
	/**
	 * Queue for cards that have not yet been viewed in this quiz. <br>
	 * Cards are added and removed from the initialQueue only once
	 */
	private Queue<FlashCard> initialQueue = new LinkedList<FlashCard>();
	/**
	 * Queue for cards that are to be viewed again for this quiz
	 */
	private Queue<FlashCard> againQueue = new LinkedList<FlashCard>();
	/**
	 * Queue for cards on their final view for this quiz
	 */
	private Queue<FlashCard> finalQueue = new LinkedList<FlashCard>();
	/**
	 * Queue that the currentFlashCard was taken from. Merely acts as a pointer, it
	 * isn't actually operated on i.e. operations are used with it
	 */
	private Queue<FlashCard> currentQueue;
	/**
	 * ArrayList<FlashCard> for all the cards to be quizzed on throughout a quiz
	 */
	private ArrayList<FlashCard> cardsToQuiz;
	/**
	 * Instance of QuizStats for current quiz
	 */
	private QuizStats quizStats = new QuizStats();
	/**
	 * Boolean value to keep track of if if the quiz is finished or not
	 */
	private Boolean quizIsFinished = false;
	/**
	 * UserStats object for this this quiz application is updated at the end of the
	 * quiz
	 */
	private UserStats userStats;
	/**
	 * int keeping track of how many times 'again' has been selected each quiz
	 */
	private int numAgain = 0;
	/**
	 * int keeping track of how many times 'final' has been selected each quiz
	 */
	private int numFinal = 0;

	/**
	 * Constructor for FlashCardQuiz
	 * 
	 * @param deck Deck object for this quiz
	 */
	public FlashCardQuiz(Deck deck, UserStats userStats) {
		this.deck = deck;
		this.userStats = userStats;
	}

	// **********o***** Starting a Quiz ***************** //

	/**
	 * Starts a new Quiz
	 * 
	 * @param maxNewCards int for the max number of new cards that a User wants to
	 *                    see
	 * @throws IllegalStateException if a quiz cannot be started, as per
	 *                               canStartQuiz()
	 */
	public void startQuiz(int maxNewCards) throws IllegalStateException {
		if (maxNewCards < 0) {
			throw new IllegalArgumentException("maxNewCards must be a positive integer!");
		}

		// TODO implement stats functionality
		// LocalTime startTime = LocalTime.now();

		LocalDate currentDate = LocalDate.now();
		this.cardsToQuiz = deck.flashCardsToQuiz(maxNewCards, currentDate);

		if (canStartQuiz()) {
			initialQueue.addAll(cardsToQuiz);
			updateCurrentFlashCard();
		} else {
			throw new IllegalStateException("There are no cards in this Deck to be quizzed on!");
		}
	}

	/**
	 * Finds if a quiz can be started. Does this by checking if the cards to be
	 * quizzed on for the current deck isn't empty
	 * 
	 * @return boolean value if a quiz can be started or not
	 */
	public boolean canStartQuiz() {
		return (cardsToQuiz.size() != 0);
	}

	// ****************** Ending a quiz ******************* //

	/**
	 * Ends a quiz.
	 * 
	 * Saves the progress made in this quiz only if the quiz was done completion
	 */
	public void endQuiz() {
		if (quizIsFinished) {
			LocalTime endTime = LocalTime.now();
			reviewAllQuizFlashCards();
			userStats.addQuizStatsEntry(quizStats);
		}
	}

	/**
	 * Creates a summary msg for this quiz
	 * 
	 * @return String for the summary of this quiz
	 */
	public String summary() {
		String msg = String.format("You are done quizzing %s for today, you saw %d cards!", deck.getName(),
				cardsToQuiz.size());
		return msg;
	}

	// ********* Methods to deal with the current flash card ********** //

	/**
	 * Updates the value of currentFlashCard attribute
	 * <p>
	 * currentFlashCard is set to null if there are no more cards to be quizzed on
	 * <p>
	 * Does this based on the state of the quiz.
	 */
	public void updateCurrentFlashCard() {
		if (!initialQueue.isEmpty()) {
			currentFlashCard = initialQueue.remove();
			setCurrentQueue("INITIAL");
		} else if (!finalQueue.isEmpty()) {
			currentFlashCard = finalQueue.remove();
			setCurrentQueue("FINAL");
		} else if (!againQueue.isEmpty()) {
			currentFlashCard = againQueue.remove();
			setCurrentQueue("AGAIN");
		} else {
			// No more cards quiz finished
			currentFlashCard = null;
			currentQueue = null;
			quizIsFinished = true;
		}
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
	 * Note: by default the currentFlashCard side is set to "FRONT" every time a new
	 * card is seen
	 * <p>
	 * Called after button pressed in GUI to flip the current flash card
	 * 
	 * @return String for the text of the side of currentFlashCard that a user wants
	 *         to see
	 */
	public String flipCurrentFlashCard() {
		changeCurrentFlashCardSide();
		return currentFlashCardSideText();
	}

	/**
	 * Finds the current text that should be displayed to a user
	 * 
	 * @return String for either the front or back text of currentFlashCard
	 */
	public String currentFlashCardSideText() {
		switch (currentFlashCardSide) {
			case "FRONT":
				return currentFlashCard.getFrontText();
			case "BACK":
				return currentFlashCard.getBackText();
			default:
				throw new IllegalStateException("Current FlashCard side isn't valid!");
		}
	}

	/**
	 * Handles changing the currentFlashCard side, either from FRONT -> BACK or BACK
	 * -> FRONT.
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

	// ************ Handling requests from a user ************* //

	/**
	 * Handles result of pressing AGAIN button in GUI
	 * <p>
	 * Adds the current flash card to both the final and again queues
	 * 
	 */
	public void flashCardAgain() {
		// TODO update val of quizStats
		numAgain++;

		againQueue.add(currentFlashCard);
		finalQueue.add(currentFlashCard);
	}

	/**
	 * Handles request of pressing OK in GUI
	 * <p>
	 * Adds the currentFlashCard to the final Queue. <br>
	 * Does this if: <br>
	 * Firstly: currentQueue is not currently finalQueue <br>
	 * AND either: (currentFlashCard is new), or (currentFlashCard isn't new but the
	 * currentQueue is againQueue)
	 * <p>
	 * Otherwise does nothing
	 */
	public void flashCardOk() {
		// TODO update val of quizStats
		numFinal++;

		if (currentQueue != finalQueue) {
			if (currentFlashCard.isNew()) {
				finalQueue.add(currentFlashCard);
			} else if (!currentFlashCard.isNew() && currentQueue == againQueue) {
				finalQueue.add(currentFlashCard);
			}
		}
		// Otherwise do nothing
	}

	/**
	 * Handles request of seeing the next FlashCard for this Quiz.
	 * <p>
	 * If the quiz is finished, then the currentFlashCard is null. This will be
	 * dealt with in the GUI.
	 * 
	 * @return FlashCard object that is next, i.e. the currentFlashCard
	 * 
	 * @throws QuizFinishedException if the quiz is finished, i.e. there are no more
	 *                               cards to see for this quiz
	 */
	public FlashCard nextFlashCard() throws QuizFinishedException {
		updateCurrentFlashCard();
		if (quizIsFinished) {
			throw new QuizFinishedException();
		}
		resetCurrentFlashCardSide();
		return currentFlashCard;
	}

	// ***************** Methods to finish a quiz ****************** //

	/**
	 * Updates all the review dates of the cards that have been seen once the quiz
	 * is over
	 * <p>
	 * Iterates over uniqueCards as these are all the cards that have been quizzed
	 * on in this quiz.
	 * <p>
	 * FlashCards will only have their nextReviewDates updated if the quiz finishes!
	 * 
	 */
	public void reviewAllQuizFlashCards() {
		for (FlashCard flashCard : cardsToQuiz) {
			flashCard.updateNextReviewDate();
		}
	}

	// ***************** Getter methods ********************** //

	/**
	 * Checks if a quiz is finished or not.
	 * <p>
	 * 
	 * @return boolean value if a quiz is finished or not
	 */
	public boolean quizIsFinished() {
		return quizIsFinished;
	}

	/**
	 * Getter method for cardsToQuiz
	 * 
	 * @return ArrayList for cards to quiz for this quiz
	 */
	public ArrayList<FlashCard> getCardsToQuiz() {
		return cardsToQuiz;
	}

	/**
	 * Getter method for the currentFlashCard for this quiz
	 * 
	 * @return FlashCard object for the currentFlashCard for this quiz
	 */
	public FlashCard getCurrentFlashCard() {
		return currentFlashCard;
	}

	/**
	 * Getter method for the initialQueue of this FlashCardQuiz
	 * 
	 * @return Queue<FlashCard> of the initialQueue of this flashCardQuiz
	 */
	public Queue<FlashCard> getInitialQueue() {
		return initialQueue;
	}

	/**
	 * Getter method for the againQueue of this FlashCardQuiz
	 * 
	 * @return Queue<FlashCard> of the againQueue of this flashCardQuiz
	 */
	public Queue<FlashCard> getAgainQueue() {
		return againQueue;
	}

	/**
	 * Getter method for the finalQueue of this FlashCardQuiz
	 * 
	 * @return Queue<FlashCard> of the finalQueue of this flashCardQuiz
	 */
	public Queue<FlashCard> getFinalQueue() {
		return finalQueue;
	}

	/**
	 * Getter method for the current queue of a quiz
	 * 
	 * @return Queue for the currentQueue of this quiz
	 */
	public Queue<FlashCard> getCurrentQueue() {
		return currentQueue;
	}

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
	 * Setter method for cardsToQuiz
	 * <p>
	 * ONLY USED FOR TESTING
	 * 
	 * @param cardsToQuiz ArrayList<FlashCard> to be set as cardsToQuiz
	 */
	public void setCardsToQuiz(ArrayList<FlashCard> cardsToQuiz) {
		this.cardsToQuiz = cardsToQuiz;
	}

	/**
	 * Setter method for the current queue
	 * <p>
	 * Takes a String, and sets the value of currentQueue according this String.
	 * Name of String is the name of the queue that is to be set as the currentQueue
	 * 
	 * @param queue ArrayList of FlashCard objects to be added to the current Queue
	 * @throws IllegalArgumentException if queue isn't either "INITIAL", "FINAL" or
	 *                                  "AGAIN"
	 */
	public void setCurrentQueue(String queue) {
		switch (queue) {
			case "INITIAL":
				currentQueue = initialQueue;
				break;
			case "AGAIN":
				currentQueue = againQueue;
				break;
			case "FINAL":
				currentQueue = finalQueue;
				break;
			default:
				throw new IllegalArgumentException("String queue must be either 'INITIAL', 'AGAIN' or 'FINAL'");
		}
	}

	/**
	 * Setter method for the current FlashCard of this quiz
	 * 
	 * @param currentFlashCard FlashCard object to be set as the currentFlashcard
	 */
	public void setCurrentFlashCard(FlashCard currentFlashCard) {
		this.currentFlashCard = currentFlashCard;
	}

	/**
	 * Setter method for the currentFlashCardSide of this quiz
	 * 
	 * @param currentFlashCardSide String to be set as the currentFlashcardSide
	 */
	public void setCurrentFlashCardSide(String currentFlashCardSide) {
		this.currentFlashCardSide = currentFlashCardSide;
	}

	/**
	 * Setter method for the initial queue of this Quiz.
	 * <p>
	 * Instead of taking a LinkedList implementation of a queue, it takes an
	 * ArrayList as a parameter, and sets this to the initialQueue using
	 * Collections.addAll(Object)
	 * 
	 * @param initialQueueList ArrayList<FlashCard> to be converted into a linked
	 *                         list queue
	 */
	public void setInitialQueue(ArrayList<FlashCard> initialQueueList) {
		initialQueue.clear();
		initialQueue.addAll(initialQueueList);
	}

	/**
	 * Setter method for the again queue of this Quiz.
	 * <p>
	 * Instead of taking a LinkedList implementation of a queue as a parameter, it
	 * takes an ArrayList as a parameter, and sets this to the againQueue using
	 * Collections.addAll(Object)
	 * 
	 * @param againQueueList ArrayList<FlashCard> to be converted into a linked list
	 *                       queue
	 */
	public void setAgainQueue(ArrayList<FlashCard> againQueueList) {
		againQueue.clear();
		againQueue.addAll(againQueueList);
	}

	/**
	 * Setter method for the final queue of this Quiz.
	 * <p>
	 * Instead of taking a LinkedList implementation of a queue, it takes an
	 * ArrayList as a parameter, and sets this to the finalQueue using
	 * Collections.addAll(Object)
	 * 
	 * @param initialQueueList ArrayList<FlashCard> to be converted into a linked
	 *                         list queue
	 */
	public void setFinalQueue(ArrayList<FlashCard> finalQueueList) {
		finalQueue.clear();
		finalQueue.addAll(finalQueueList);
	}
}
