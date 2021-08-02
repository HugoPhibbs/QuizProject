package core.quizLogic;

import java.util.ArrayList;
import java.util.Collections;

import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import core.stats.UserStats;

/**
 * Class to contain logic for a Multiple choice quiz
 * <p>
 * By default, the front side of the FlashCard is the prompt and the answer is
 * the backside of the same FlashCard
 */
public class MultipleChoiceQuiz extends Quiz {

    /**
     * Array to contain the possible FlashCards that a user can choose for their
     * answer to a quiz
     */
    FlashCard[] possibleChoices;

    public MultipleChoiceQuiz(Deck deck, UserStats userStats) {
        super(deck, userStats);
    }

    /**
     * As per Quiz.canStartQuiz(), checks if a multiple choice quiz can be started
     * or not.
     * <p>
     * In this case, cardsToQuiz has to contain more than 4 FlashCards, this is so
     * there are enough FlashCards to fill possible answer buttons on the quizzing
     * Screen. If there were repeating content on the buttons, guessing the correct
     * answer would be much easier to do!
     * 
     * @return boolean value if a quiz can be started or not
     */
    @Override
    public boolean canStartQuiz() {
        return (getCardsToQuiz().size() >= 4);
    }

    /**
     * Returns a String for the text that should be displayed as a prompt for a
     * multiple choice question
     * 
     * @return String as described
     */
    private String currentFlashCardText() {
        return getCurrentFlashCard().getFrontText();
    }

    /**
     * Updates possibleChoices.
     * <p>
     * Gets an ArrayList of length 3 from randomFlashCards(). Then adds
     * currentFlashCard to this ArrayList, and shuffles this ArrayList. Then sets
     * this as possibleChoices
     */
    private void updatepossibleChoices() {
        ArrayList<FlashCard> randomFlashCards = randomFlashCards();
        randomFlashCards.add(getCurrentFlashCard());
        Collections.shuffle(randomFlashCards);
        possibleChoices = (FlashCard[]) randomFlashCards.toArray();
    }

    /**
     * Finds an ArrayList containing FlashCards of length 3. The 3 FlashCards are
     * randomly chosen from the Deck that is currently being quizzed on.
     * <p>
     * The current FlashCard for this quiz is not included in this ArrayList. <br>
     * And there are no repeating elements in the returned ArrayList
     * 
     * @return ArrayList as described
     */
    @SuppressWarnings("unchecked")
    private ArrayList<FlashCard> randomFlashCards() {
        ArrayList<FlashCard> flashCards = (ArrayList<FlashCard>) getDeck().getFlashCards().clone();
        flashCards.remove(getCurrentFlashCard());
        Collections.shuffle(flashCards);
        return (ArrayList<FlashCard>) flashCards.subList(0, 3); // Take first 3 elements
    }

    /**
     * Creates a String Array containing the backText of all the FlashCards in
     * possibleChoices
     * 
     * @return String[] as described
     */
    private String[] possibleChoicesToString() {
        String[] resultArray = new String[4];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = possibleChoices[i].getBackText();
        }
        return resultArray;
    }

    /**
     * Checks if the button that a user has pressed is the correct answer.
     * answerIndex should correlate to an integer in [0, 3] which is the index of a
     * FlashCard stored in possibleFlashCards.
     * 
     * @param answerIndex int for the index of the chosen FlashCard from
     *                    possibleChoices
     * @return boolean value if possibleChoices[answerIndex] is the currentFlashCard
     */
    private boolean isCorrectAnswer(int answerIndex) {
        if (answerIndex < 0 || answerIndex > 3) {
            throw new IllegalArgumentException("answerIndex should be an integer in range [0, 3]!");
        }
        return possibleChoices[answerIndex] == getCurrentFlashCard();
    }

    /**
     * Handles an answer from a user to a multiple choice question
     * 
     * @param answerIndex int for the index of the FlashCard that they chosen as the
     *                    answer for a multiple choice question. This corresponds to
     *                    the button that they press within the GUI
     * @return String for the result of a user's answer. As per
     *         answerResultToString(boolean)
     */
    private String handleAnswer(int answerIndex) {
        if (isCorrectAnswer(answerIndex)) {
            flashCardOk();
            return answerFeedback(true);
        } else {
            flashCardAgain();
            return answerFeedback(false);
        }
    }

    /**
     * Creates and returns the message that is to be displayed to a user in response
     * to an answer to a question
     * 
     * @param isCorrectAnswer boolean if the user got the current question right or
     *                        not
     * @return String feedback message as described
     */
    private String answerFeedback(boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            return "Correct answer!";
        } else {
            String msg = String.format("Incorrect answer!.\nThe answer should be %s instead!",
                    getCurrentFlashCard().getBackText());
            return msg;
        }
    }
}
