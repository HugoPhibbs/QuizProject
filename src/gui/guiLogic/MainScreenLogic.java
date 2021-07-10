package gui.guiLogic;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import core.coreLogic.FlashCardQuiz;
import core.coreObjects.Deck;
import core.coreObjects.User;
import gui.guiShell.CreateDeckScreen;
import gui.guiShell.EditDeckScreen;
import gui.guiShell.EditFlashCardScreen;
import gui.guiShell.MainScreen;
import gui.guiShell.QuizzingScreen;

/** Class to handle logic for MainScreen
 * <p>
 * Allows MainScreen to act more like a shell, with this class handling logic to make MainScreen actually work behind the scenes
 * <p>
 * Handles any logic that does not directly relate to components of MainScreen, i.e methods that could easily be extrapolated to a
 * new logic class with minimal refactoring
 * 
 * @author Hugo Phibbs
 * @version 10/7/21
 * @since 10/7/21
 */
public class MainScreenLogic {

    /** AppEnvironment object for this application */
    AppEnvironment appEnvironment;
    /** DeckManager object belonging to appEnvironment */
    DeckManager deckManager;
    /** User object belonging to appEnvironment */
    User user;
    /** MainScreen object that is being controlled */
    MainScreen mainScreen;

    public MainScreenLogic(AppEnvironment appEnvironment){
        this.appEnvironment = appEnvironment;
        this.user = appEnvironment.getUser();
        this.deckManager = appEnvironment.getDeckManager();
    }

    // ********************* Helpers for creating components *********************** //

    /** Finds and returns the column titles for tableDecks
	 * 
	 * @returns String[] array containing column titles for Deck details
	 */
	public String[] decksTableHeaders(){
		return Deck.infoArrayHeaders();
	}

    /** Finds and returns the row content for tableDecks
	 * 
	 * @returns String[][] nested array containing info on decks contained in this application's DeckManager
	 */
	public String[][] decksTableDetails(){
		// TODO uncomment bellow when class fully implemented
		// return appEnvironment.getDeckManager().deckCollectionInfo();
		return new String[][] {
			{"testName1", "3", "5"}, 
			{"testName2", "4", "8"}, 
		};
    }
    
    // ********************** Methods to swtich the current screen ************************ // 

    /** Handles creating a new screen to create a flash card
	 * <p>
	 * Takes logical code away from MainScreen
	 * <p>
	 * Despite it creating an EditFlashCardScreen, that handles both editing and creating a FlashCard, this
	 * method can only create an EditFlashCardScren for the later case, as this is only intended to be used 
	 * by MainScreen
	 * 
	 * @param chosenDeck Deck object that a user has chosen to add a FlashCard to
	 */
	public void editFlashCard(Deck chosenDeck) {
		EditFlashCardScreen editFlashCardScreen = new EditFlashCardScreen(null, null, null, null);
		// switchScreens(editFlashCardScreen);
	}

    /** Creates a new quizzing Screen, 
	 * 
	 * @param quizDeck Deck object that is to be quizzed on
	 * @throws IllegalArgumentException if deckName isn't the name of 
	 * any deck in the deckManager of this AppEnviornment
	 */
	public void newQuiz(Deck quizDeck) {
		FlashCardQuiz newQuiz = new FlashCardQuiz(quizDeck, user.getUserStats());
        QuizzingScreen quizzingScreen = new QuizzingScreen(newQuiz);
        // switchScreens(quizzingScreen);
		
	}
	
	/** Handles creating a new EditDeckScreen
	 * <p>
	 * Takes logical code away from MainScreen
	 * 
	 * @param chosenDeck Deck object to be edited
	 */
	public void editDeck(Deck chosenDeck) {
		EditDeckScreen editDeckScreen = new EditDeckScreen(chosenDeck, deckManager);
        // switchScreen(editDeckScreen);
	}

    public void createDeck(){
		CreateDeckScreen createDeckScreen = new CreateDeckScreen(deckManager);
        // switchScreens(createDeckScreen);
	}

    /** Handles showing of a new screen, and hides this MainScreen
     * 
     * @param newScreen Screen object to be switched to
     */
    private void swtichScreens(Screen newScreen){
        newScreen.show();
        // mainScreen.hide();
    }
}
