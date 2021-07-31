package gui.guiLogic;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import core.coreLogic.FlashCardQuiz;
import core.coreObjects.Deck;
import core.coreObjects.User;
import gui.guiShell.EditFlashCardScreen;
import gui.guiShell.MainScreen;

/**
 * Class to handle logic for MainScreen
 * <p>
 * Allows MainScreen to act more like a shell, with this class handling logic to
 * make MainScreen actually work behind the scenes
 * <p>
 * Handles any logic that does not directly relate to components of MainScreen,
 * i.e methods that could easily be extrapolated to a new logic class with
 * minimal refactoring
 * 
 * @author Hugo Phibbs
 * @version 13/7/21
 * @since 10/7/21
 */
public class MainScreenLogic extends ScreenLogic implements Updateable {

	/**
	 * DeckManager object belonging to appEnvironment
	 */
	DeckManager deckManager;
	/**
	 * User object belonging to appEnvironment
	 */
	User user;
	/**
	 * MainScreen object that is being controlled
	 */
	MainScreen screen;
	/**
	 * Deck object representing the currently selected deck from tableDeck, null if
	 * no Deck is chosen
	 */
	Deck chosenDeck = null;

	/**
	 * Constructor for MainScreenLogic
	 * 
	 * @param appEnvironment AppEnvironment object for this application
	 */
	public MainScreenLogic(AppEnvironment appEnvironment) {
		super(null, appEnvironment); // Won't need to go back to setupScreen
		this.deckManager = appEnvironment.getDeckManager();
		this.user = appEnvironment.getUser();
	}

	// *************** Creating and closing the Screen **************** //

	/**
	 * Handles creating a new MainScreen instance for this class
	 */
	public void createScreen() {
		screen = new MainScreen(this);
		screen.initialize();
		super.setScreen(screen);
		configScreenBtns(false);
	}

	/**
	 * Handles closing of this screen
	 * <p>
	 * Quits this screen, and saves the progress of this app via AppEnvironment
	 */
	public void closeScreen() {
		getAppEnvironment().closeDown();
	}

	// @Override
	// private void onQuit() {

	// }

	// ***************** Methods to create new Screens ******************* //

	/**
	 * Handles creating a new screen to create a flash card
	 * <p>
	 * Takes logical code away from MainScreen
	 * <p>
	 * Despite it creating an EditFlashCardScreen, that handles both editing and
	 * creating a FlashCard, this method can only create an EditFlashCardScreen for
	 * the later case, as this is only intended to be used by MainScreen
	 *
	 */
	public void createFlashCard() {
		EditFlashCardScreenLogic editFlashCardScreenLogic = new EditFlashCardScreenLogic(this, null, chosenDeck,
				getAppEnvironment(), this);
		handleNewScreen(editFlashCardScreenLogic);
	}

	/**
	 * Creates a new quizzing Screen,
	 * 
	 * @throws IllegalArgumentException if deckName isn't the name of any deck in
	 *                                  the deckManager of this AppEnviornment
	 */
	public void newQuiz() {
		FlashCardQuiz newQuiz = new FlashCardQuiz(chosenDeck, user.getUserStats());
		QuizzingScreenLogic quizzingScreenLogic = new QuizzingScreenLogic(newQuiz, this, this, chosenDeck,
				getAppEnvironment());
		newQuizzingScreen(quizzingScreenLogic);
	}

	/**
	 * Handles creating a new QuizzingScreen
	 * <p>
	 * Since a quizzing screen should not be shown unless the quiz can actually
	 * start, this function is used to handle this, unlike other screens which are
	 * simply created and then shown
	 * 
	 * @param quizzingScreenLogic QuizzingScreenLogic object to create a new
	 *                            QuizzingScreen for
	 */
	private void newQuizzingScreen(QuizzingScreenLogic quizzingScreenLogic) {
		quizzingScreenLogic.createScreen();
		try {
			quizzingScreenLogic.startQuiz();
			quizzingScreenLogic.showScreen();
		} catch (IllegalStateException ise) {
			screen.displayError(ise.getMessage());
		}
	}

	/**
	 * Handles creating a new EditDeckScreen
	 * <p>
	 * Takes logical code away from MainScreen
	 * 
	 * @param chosenDeck Deck object to be edited
	 */
	public void editDeck() {
		EditDeckScreenLogic editDeckScreenLogic = new EditDeckScreenLogic(this, this, getAppEnvironment(), chosenDeck);
		handleNewScreen(editDeckScreenLogic);
	}

	/**
	 * Handles creating a new CreateDeckScreen
	 */
	public void createDeck() {
		CreateDeckScreenLogic createDeckScreenLogic = new CreateDeckScreenLogic(this, getAppEnvironment(), this);
		handleNewScreen(createDeckScreenLogic);
	}

	// *********** Methods to handle Listener events ********** //

	/**
	 * Handles selection of row from decksTable
	 * <p>
	 * Makes sure that the changing of a selection only counts as one event, with if
	 * statement
	 * 
	 * @param lse ListSelection that occurred to select a Deck from tableDecks
	 */
	public void deckSelected(ListSelectionEvent lse) {
		// Make sure a selection counts as one event, not two
		if (!lse.getValueIsAdjusting()) {
			updateChosenDeck();
		}
		configScreenBtns(true);
	}

	/**
	 * Finds the name of the currently chosen Deck from the deck
	 *
	 */
	private void updateChosenDeck() {
		JTable tableDecks = screen.getTableDecks();
		int chosenRow = tableDecks.getSelectedRow();
		String deckName = tableDecks.getModel().getValueAt(chosenRow, 0).toString();
		chosenDeck = getAppEnvironment().getDeckManager().findDeck(deckName);
	}

	// ****************** Helpers for filling decksTable *********************** //

	/**
	 * Finds and returns the column titles for tableDecks
	 * 
	 * @return String[] array containing column titles for Deck details
	 */
	public String[] decksTableHeaders() {
		return Deck.infoArrayHeaders();
	}

	/**
	 * Finds and returns the row content for tableDecks
	 * 
	 * @return String[][] nested array containing info on decks contained in this
	 *         application's DeckManager
	 */
	public String[][] decksTableDetails() {
		return getAppEnvironment().getDeckManager().deckCollectionInfo();
	}

	// *********** Methods for refreshing the Screen and this class ********** //

	/**
	 * Resets the components for this screen
	 * <p>
	 * Instead of creating an entirely new Screen, it disables affectected buttons
	 * and resets tableDecks in the Screen
	 */
	@Override
	public void receiveUpdate() {
		resetPanelViewDecks();
		configScreenBtns(false);
		screen.displayError("");
		chosenDeck = null; // null until another deck is selected
	}

	/**
	 * Refreshes MainScreen's panelViewDecks, does this by removing components from
	 * panelViewDecks, and then refilling it with relevant info
	 */
	private void resetPanelViewDecks() {
		screen.clearContainer(screen.getPanelViewDecks());
		screen.updatePanelViewDecks();
	}

	/**
	 * Handles enabling or disabling any buttons on MainScreen that may be enabled
	 * or disabled.
	 * 
	 * @param setting boolean value for if affected MainScreen buttons should be
	 *                enabled or not
	 */
	public void configScreenBtns(boolean setting) {
		screen.toggleComponent(screen.getBtnStartQuiz(), setting);
		screen.toggleComponent(screen.getBtnNewFlashCard(), setting);
		screen.toggleComponent(screen.getBtnEditDeck(), setting);
	}

}
