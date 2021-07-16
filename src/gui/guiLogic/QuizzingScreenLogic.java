package gui.guiLogic;

import core.coreLogic.FlashCardQuiz;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import gui.guiShell.QuizzingScreen;

/**
 * Class to control QuizzingScreen
 * 
 * @author Hugo Phibbs
 * @version 16/7/21
 * @since 10/7/21
 */
public class QuizzingScreenLogic extends ScreenLogic implements Updater {

	/**
	 * FlashCardQuiz object for this current quiz
	 */
	private FlashCardQuiz flashCardQuiz;
	/**
	 * QuizzingScreen object that this class is controlling
	 */
	private QuizzingScreen screen;
	/**
	 * Updateable object that this class updates upon closing of QuizzingScreen
	 */
	private Updateable updateable;
	/**
	 * Deck object that is currently being quizzed on
	 */
	private Deck deck;

	/**
	 * Constructor for QuizzingScreenLogic.
	 * <p>
	 * Along with assigning class attributes, it aslo starts the quiz and create the
	 * Screen
	 * 
	 * @param flashCardQuiz     FlashCardQuiz object for the current quiz
	 * @param parentScreenLogic ScreenLogic class that controlled the Screen that
	 *                          created this Screen
	 * @param updateable        Updateable object that this class updates upon
	 *                          closing of Quizzing Screen
	 * @param chosenDeck        Deck object that is currently being quizzed on
	 */
	public QuizzingScreenLogic(FlashCardQuiz flashCardQuiz, ScreenLogic parentScreenLogic, Updateable updateable,
			Deck chosenDeck) {
		super(parentScreenLogic);
		this.flashCardQuiz = flashCardQuiz;
		this.deck = chosenDeck;
		createScreen();
		startQuiz();
	}

	public void startQuiz() {
		flashCardQuiz.startQuiz(10); // TODO make maxNewCards adaptable!
		showNextFlashCard();
	}

	// ********** Creating and closing the Screen *************** //

	@Override
	public void createScreen() {
		screen = new QuizzingScreen(this);
		screen.initialize();
		// TODO Auto-generated method stub

	}

	@Override
	public void closeScreen() {
		// TODO Auto-generated method stub

	}

	// ****************** Handling Listener events **************** //

	public void flashCardFlip() {
		// TODO implement
		updateFlashCardText(flashCardQuiz.flipCurrentFlashCard());
	}

	/**
	 * Handles pressing of btnFlashCardOk on QuizzingScreen
	 * <p>
	 * Updates changing state of flashCardQuiz
	 */
	public void flashCardAgain() {
		flashCardQuiz.flashCardAgain();
		handleNextFlashCard();
	}

	/**
	 * Handles pressing of btnFlashCardOk on QuizzingScreen
	 * <p>
	 * Updates changing state of flashCardQuiz
	 */
	public void flashCardOk() {
		flashCardQuiz.flashCardOk();
		handleNextFlashCard();
	}

	public void finishQuiz() {
		// TODO implement
	}

	// *********** Methods to show the next FlashCard to a user ************* //

	/**
	 * Handles showing the next FlashCard to a user.
	 * <p>
	 * Common tasks that are completed by both flashCardOk() and flashCardAgain(),
	 * so it is packaged into a single shared method
	 */
	public void handleNextFlashCard() {
		flashCardQuiz.nextFlashCard();
		showNextFlashCard();
		// TODO. implement more methods here if needed, may need to do more things here
	}

	/**
	 * Handles showing the next FlashCard in the content pane
	 * <p>
	 * The text that is shown to the user first, either frontText or backText is
	 * decided by FlashCardQuiz, however, by default it is FRONT
	 */
	public void showNextFlashCard() {
		updateFlashCardText(flashCardQuiz.currentSideText());
	}

	/**
	 * Handles showing the text of currentFlashCard that is wished to be seen,
	 * either the front or back text
	 * 
	 * @param text String for the text to be displayed in textPaneCurrentSide
	 */
	public void updateFlashCardText(String text) {
		screen.getTextPaneCurrentSide().setText(text);
	}

	// *********** General helper methods ***************** //

	public String deckName() {
		return deck.getName();
	}

	// ********** Updating Updateable objects *********** //

	@Override
	public void update() {
		updateable.receiveUpdate();
	}

}
