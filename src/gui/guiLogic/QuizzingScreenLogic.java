package gui.guiLogic;

import core.coreLogic.AppEnvironment;
import core.coreLogic.FlashCardQuiz;
import core.coreLogic.QuizFinishedException;
import core.coreObjects.Deck;
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
	 * @param AppEnvironment    AppEnvironment object for this application
	 */
	public QuizzingScreenLogic(FlashCardQuiz flashCardQuiz, ScreenLogic parentScreenLogic, Updateable updateable,
			Deck chosenDeck, AppEnvironment appEnvironment) {
		super(parentScreenLogic, appEnvironment);
		this.flashCardQuiz = flashCardQuiz;
		this.deck = chosenDeck;
	}

	// ********** Starting and ending a quiz ********** //

	/**
	 * Handles starting a Quiz
	 */
	public void startQuiz() {
		flashCardQuiz.startQuiz(10); // TODO make maxNewCards adaptable!
		updateFlashCardText();
	}

	/**
	 * Handles when a completed is done to completion by a user, and wasn't stopped
	 * early.
	 * <p>
	 * A key point here is that setting if progress is saved is set to true, when
	 * the pop up to close the screen is summoned
	 */
	public void quizCompleted() {
		configFlashCardButtons(false);
		showText(flashCardQuiz.summary());
		screen.setWillSave(true);
		flashCardQuiz.endQuiz();
		// TODO make text pane non editable
	}

	/**
	 * Handles when a user wants to end a quiz. Since this just calls the dialogue
	 * box to close the screen, the user is notified if closing this screen will
	 * save any progress.
	 */
	public void finishQuiz() {
		screen.confirmQuit();
	}

	// ********** Creating and closing the Screen *************** //

	@Override
	public void createScreen() {
		screen = new QuizzingScreen(this);
		screen.initialize();
		super.setScreen(screen);
		configFlashCardButtons(true);
	}

	@Override
	public void closeScreen() {
		deleteScreen();
	}

	// ****************** Handling Listener events **************** //

	/**
	 * Handles pressing of btnFlipFlashCard
	 */
	public void flashCardFlip() {
		showText(flashCardQuiz.flipCurrentFlashCard());
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

	// *********** Methods to show the next FlashCard to a user ************* //

	/**
	 * Handles showing the next FlashCard to a user.
	 * <p>
	 * Common tasks that are completed by both flashCardOk() and flashCardAgain(),
	 * so it is packaged into a single shared method
	 */
	public void handleNextFlashCard() {
		try {
			flashCardQuiz.nextFlashCard();
			updateFlashCardText();
			// TODO. implement more methods here if needed, may need to do more things here
		} catch (QuizFinishedException qfe) {
			quizCompleted();
		}
	}

	/**
	 * Handles showing the next FlashCard in the content pane
	 * <p>
	 * The text that is shown to the user first, either frontText or backText is
	 * decided by FlashCardQuiz, however, by default it is FRONT
	 */
	public void updateFlashCardText() {
		showText(flashCardQuiz.currentFlashCardSideText());
	}

	/**
	 * Handles showing the text of currentFlashCard that is wished to be seen,
	 * either the front or back text
	 * 
	 * @param text String for the text to be displayed in textPaneCurrentSide
	 */
	public void showText(String text) {
		screen.getTextPaneCurrentSide().setText(text);
	}

	// *********** General helper methods ***************** //

	/**
	 * Finds the name of the deck that is currently being quizzed on
	 * 
	 * @return String for the name of the Deck that is currently being quizzed on
	 */
	public String deckName() {
		return deck.getName();
	}

	/**
	 * Handles enabling or disabling buttons to do with interacting flashcards for
	 * this quiz
	 * 
	 * @param setting boolean value for if the buttons should be enabled or not
	 */
	public void configFlashCardButtons(boolean setting) {
		screen.getBtnFlashCardAgain().setEnabled(setting);
		screen.getBtnFlashCardFlipFlashCard().setEnabled(setting);
		screen.getBtnFlashCardOk().setEnabled(setting);
	}

	// ********** Updating Updateable objects *********** //

	@Override
	public void update() {
		updateable.receiveUpdate();
	}

}
