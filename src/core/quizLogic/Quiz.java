package core.quizLogic;

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
 * Class to contain logic to do with a Quiz.
 * <p>
 * This is intended to be used by FlashCardQuiz and MultipleChoiceQuiz. They
 * both essentially use the same mechanisms when prioritizing FlashCards
 * <p>
 * * The current state of a quiz can be reflected with 3 queues, the
 * initialQueue, the againQueue and the finalQueue.
 * <p>
 * How the quiz algorithm works: <br>
 * - The user sees cards from one of the queues at once as long as they aren't
 * empty. However the priority is (in order) initial, final, again. the
 * algorithm will tend to switch between the final and again queues <br>
 * - At the start of the quiz, a user will see all the cards from initialQueue
 * until this queue is empty. If the initialQueue is empty, then FlashCards are
 * taken from the finalQueue or againQueue, in order for what ever one isn't
 * empty <br>
 * - For each card that they see from any queue, they can either press AGAIN or
 * OK.<br>
 * <p>
 * Pressing OK: <br>
 * - While on the initial or again queue: If a user presses OK for a
 * getCurrentFlashCard() that is new, this FlashCard is added to the final
 * queue, <br>
 * - If the card isn't new or the current queue is the final queue (has been
 * seen at-least once before) then nothing happens <br>
 * <p>
 * Pressing AGAIN <br>
 * - No matter if a card is new or not, if AGAIN is pressed, then
 * getCurrentFlashCard() is added both to the final and again queues. <br>
 * <p>
 * Stats<br>
 * At each point in the quiz, statistics from the quiz are added to an instance
 * of QuizStats.
 * 
 * @author Hugo Phibbs
 * @version 2/8/21
 * @since 2/8 21
 */
public abstract class Quiz {
    /**
     * Deck that is currently being quizzed on
     */
    private Deck deck;
    /**
     * FlashCard for the current flash card for this quiz
     */
    private FlashCard currentFlashCard;
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
     * Constructor for a Quiz object
     * 
     * @param deck      Deck object that is currently being quizzed on
     * @param userStats UserStats object for this application
     */
    public Quiz(Deck deck, UserStats userStats) {
        this.deck = deck;
        this.userStats = userStats;
    }

    // *************** Starting a Quiz ***************** //

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
        cardsToQuiz = deck.flashCardsToQuiz(maxNewCards, currentDate);

        if (canStartQuiz()) {
            initialQueue.addAll(cardsToQuiz);
            updateCurrentFlashCard();
        } else {
            throw new IllegalStateException("There are no cards in this Deck to be quizzed on!");
        }
    }

    // public void startQuiz(int maxNewCards) throws IllegalStateException {}

    /**
     * Determines if a quiz can be started or not.
     * <p>
     * Depending on the class that extends this Class, the implementation of this
     * Quiz will obviously be different
     * 
     * @return boolean value if quiz can be started or not
     */
    public abstract boolean canStartQuiz();

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
    }

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
            quizFinished();
        }
    }

    /**
     * Handles the state of a quiz being finished
     */
    private void quizFinished() {
        currentFlashCard = null;
        currentQueue = null;
        quizIsFinished = true;
    }

    /**
     * Handles request of seeing the next FlashCard for this Quiz.
     * <p>
     * If the quiz is finished, then the currentFlashCard is null. This will be
     * dealt with in the GUI.
     * 
     * @throws QuizFinishedException if the quiz is finished, i.e. there are no more
     *                               cards to see for this quiz
     */
    public void nextFlashCard() throws QuizFinishedException {
        updateCurrentFlashCard();
        if (quizIsFinished) {
            throw new QuizFinishedException();
        }
        // resetCurrentFlashCardSide(); // Should be handled by FlashCardQuiz
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
    public boolean getQuizIsFinished() {
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
     * @return Queue of the initialQueue of this flashCardQuiz
     */
    public Queue<FlashCard> getInitialQueue() {
        return initialQueue;
    }

    /**
     * Getter method for the againQueue of this FlashCardQuiz
     * 
     * @return Queue of the againQueue of this flashCardQuiz
     */
    public Queue<FlashCard> getAgainQueue() {
        return againQueue;
    }

    /**
     * Getter method for the finalQueue of this FlashCardQuiz
     * 
     * @return Queue of the finalQueue of this flashCardQuiz
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
     * Deck object belonging to this Quiz object
     * 
     * @return Deck object as described
     */
    public Deck getDeck() {
        return deck;
    }

    // **************** Setter Methods ***************** //

    /**
     * Setter method for cardsToQuiz
     * <p>
     * ONLY USED FOR TESTING
     * 
     * @param cardsToQuiz ArrayList to be set as cardsToQuiz
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
     * Setter method for the initial queue of this Quiz.
     * <p>
     * Instead of taking a LinkedList implementation of a queue, it takes an
     * ArrayList as a parameter, and sets this to the initialQueue using
     * Collections.addAll(Object)
     * 
     * @param initialQueueList ArrayList to be converted into a linked list queue
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
     * @param againQueueList ArrayList to be converted into a linked list queue
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
     * @param finalQueueList ArrayList to be converted into a linked list queue
     */
    public void setFinalQueue(ArrayList<FlashCard> finalQueueList) {
        finalQueue.clear();
        finalQueue.addAll(finalQueueList);
    }
}
