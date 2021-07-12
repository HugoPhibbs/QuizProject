package gui.guiLogic;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import core.coreLogic.FlashCardQuiz;
import core.coreObjects.Deck;
import core.coreObjects.User;
import gui.guiShell.EditDeckScreen;
import gui.guiShell.MainScreen;
import gui.guiShell.Screen;

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
 * @version 10/7/21
 * @since 10/7/21
 */
public class MainScreenLogic extends ScreenLogic implements Updateable {

	/**
	 * AppEnvironment object for this application
	 */
	AppEnvironment appEnvironment;
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
		super(null); // Won't need to go back to setupScreen
		this.appEnvironment = appEnvironment;
		this.user = appEnvironment.getUser();
		this.deckManager = appEnvironment.getDeckManager();
	}

	public void createScreen() {
		screen = new MainScreen(this);
		// screen.show();
	}

	// ********************* Helpers for creating components ***********************

	/**
	 * Finds and returns the column titles for tableDecks
	 * 
	 * @returns String[] array containing column titles for Deck details
	 */
	public String[] decksTableHeaders() {
		return Deck.infoArrayHeaders();
	}

	/**
	 * Finds and returns the row content for tableDecks
	 * 
	 * @returns String[][] nested array containing info on decks contained in this
	 *          application's DeckManager
	 */
	public String[][] decksTableDetails() {
		return appEnvironment.getDeckManager().deckCollectionInfo();
	}

	// ***************** Methods to swtich the current screen ******************* //
	/**
	 * Refreshes the content of MainScreen, does this by re-initializing tableDecks
	 */
	public void refresh() {
		screen.clearPanel(screen.getPanelViewDecks());
		screen.updatePanelViewDecks();
	}

	/**
	 * Handles creating a new screen to create a flash card
	 * <p>
	 * Takes logical code away from MainScreen
	 * <p>
	 * Despite it creating an EditFlashCardScreen, that handles both editing and
	 * creating a FlashCard, this method can only create an EditFlashCardScren for
	 * the later case, as this is only intended to be used by MainScreen
	 * 
	 * @param chosenDeck Deck object that a user has chosen to add a FlashCard to
	 */
	public void createFlashCard() {
		// TODO use chosenDeck!
		EditFlashCardScreenLogic editFlashCardScreenLogic = new EditFlashCardScreenLogic(null, chosenDeck, deckManager,
				this);
		editFlashCardScreenLogic.createScreen();
		// editFlashCardScreenLogic.switchScreens();
	}

	/**
	 * Creates a new quizzing Screen,
	 * 
	 * @throws IllegalArgumentException if deckName isn't the name of any deck in
	 *                                  the deckManager of this AppEnviornment
	 */
	public void newQuiz() {
		FlashCardQuiz newQuiz = new FlashCardQuiz(chosenDeck, user.getUserStats());
		// QuizzingScreen quizzingScreen = new QuizzingScreen(chosenDeck);
		// switchScreens(quizzingScreen);

	}

	/**
	 * Handles creating a new EditDeckScreen
	 * <p>
	 * Takes logical code away from MainScreen
	 * 
	 * @param chosenDeck Deck object to be edited
	 */
	public void editDeck() {
		EditDeckScreenLogic editDeckScreenLogic = new EditDeckScreenLogic(this, this, deckManager, chosenDeck);
		editDeckScreenLogic.createScreen();
		// switchScreen(editDeckScreen);
	}

	public void createDeck() {
		CreateDeckScreenLogic createDeckScreenLogic = new CreateDeckScreenLogic(this, deckManager);
		createDeckScreenLogic.createScreen();
		// createDeckScreenLogic.switchScreens();
	}

	/**
	 * Updates panelDecks to have up to date information of decks. I.e. if a deck is
	 * editted or deleted, a deck is created, or any other changes to Decks that
	 * affect their name or size
	 */
	public void decksChanged() {
		screen.updatePanelViewDecks();
	}

	// TODO make this a general method in ScreenLogic?
	/**
	 * Finds the name of the currently chosen Deck from the deck
	 * 
	 * @return Deck object that has been chosen
	 */
	private void updateChosenDeck() {
		JTable tableDecks = screen.getTableDecks();
		int chosenRow = tableDecks.getSelectedRow();
		String deckName = tableDecks.getModel().getValueAt(chosenRow, 0).toString();
		chosenDeck = appEnvironment.getDeckManager().findDeck(deckName);
	}

	/**
	 * Handles selection of row from decksTable
	 * <p>
	 * Makes sure that the changing of a selection only counts as one event, with if
	 * statement
	 * 
	 * @param lse ListSelection that occurred to select a Deck from tableDecks
	 */
	public void deckSelected(ListSelectionEvent lse) {
		// Bellow lines ensures that changing selection of a row counts as one change,
		// not two
		if (!lse.getValueIsAdjusting()) {
			updateChosenDeck();
			screen.toggleButton(screen.getBtnStartQuiz(), true);
			screen.toggleButton(screen.getBtnEditDeck(), true);
			screen.toggleButton(screen.getBtnNewFlashCard(), true);
		}
	}

	@Override
	public void receivedUpdate() {
		// TODO Auto-generated method stub

	}

}
