package gui.guiLogic;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import gui.guiShell.EditFlashCardScreen;

/**
 * Handles logic for EditFlashCardScreen
 * <p>
 * When a FlashCard is editted or created, it notifies any dependent Updateable
 * objects on changes to the Application state
 * 
 * @author Hugo Phibbs
 * @version 13/7/21
 * @since 10/7/21
 */
public class EditFlashCardScreenLogic extends ScreenLogic implements Updater {

	/**
	 * boolean value for if a FlashCard object is being created or editted
	 */
	boolean isCreating;
	/**
	 * EditFlashCardScreen object that is being controlled by this class
	 */
	EditFlashCardScreen screen;
	/**
	 * Deck object for what the current FlashCard that is being editted or created
	 * belongs to
	 */
	Deck currentDeck;
	/**
	 * DeckManager object that holds all the Decks for this application
	 */
	DeckManager deckManager;
	/**
	 * FlashCard object that is being editted
	 */
	FlashCard currentFlashCard;
	/**
	 * Dependent Updateable object to be updated when a FlashCard is finished being
	 * editted
	 */
	Updateable updateable;

	/**
	 * Constructor for EditFlashCardScreenLogic
	 * <p>
	 * Sets attributes and creates a new EditFlashCardScreen
	 * 
	 * @param updateable       Updateable object that is dependent on this Screen
	 *                         for changes
	 * @param currentFlashCard FlashCard object that is currently being editted,
	 *                         null if a FlashCard is being created
	 * @param currentDeck      Deck object that a user wants to add a FlashCard to
	 * @param appEnvironment   AppEnivronment object for this application
	 * @param parentLogic      ScreenLogic object that is the parent ScreenLogic to
	 *                         this object
	 */
	public EditFlashCardScreenLogic(Updateable updateable, FlashCard currentFlashCard, Deck currentDeck,
			AppEnvironment appEnvironment, ScreenLogic parentLogic) {
		super(parentLogic, appEnvironment);
		this.updateable = updateable;
		this.currentFlashCard = currentFlashCard;
		this.currentDeck = currentDeck;
		this.deckManager = appEnvironment.getDeckManager();
		handleEditingOrCreating();
	}

	// ****************** Creating and closing the Screen ******************* //

	/**
	 * Implementation of abstract method for ScreenLogic
	 * <p>
	 * Creates a new EditFlashCardScreen and sets it as the Screen for this class.
	 */
	public void createScreen() {
		screen = new EditFlashCardScreen(this);
		super.setScreen(screen);
	}

	/**
	 * Handles the closing of this screen
	 */
	public void closeScreen() {
		update();
		deleteScreen();
	}

	// *************** Handling editing or creating a FlashCard ************ //

	/**
	 * Handles whether a user is creating or editing a FlashCard
	 * <p>
	 * Checks if the current flash card is null, as this indicates whether a
	 * FlashCard is being edited or created
	 */
	private void handleEditingOrCreating() {
		if (currentFlashCard == null) {
			isCreating();
		} else {
			isEditing();
		}
	}

	/**
	 * Adjusts components for when a FlashCard is being created, not editted
	 * 
	 */
	private void isCreating() {
		isCreating = true;
		currentFlashCard = new FlashCard("", "");
	}

	/**
	 * Adjusts components for when a FlashCard is being editted, not created
	 *
	 */
	private void isEditing() {
		isCreating = false;
	}

	// ****************** Handling Listener events ******************* //

	/**
	 * Handles when a user enters a text for the front or back of a FlashCard, If
	 * both the front text and back text fields aren't empty then it enables
	 * btnFinish of the screen
	 */
	public void textEntered() {
		if (!screen.frontText().equals("") && !screen.backText().equals("")) {
			screen.toggleComponent(screen.getBtnFinish(), true);
		} else {
			screen.toggleComponent(screen.getBtnFinish(), false);
		}
	}

	/**
	 * Handles what happens when a user presses finish
	 * <p>
	 * Diverts flow to either create a FlashCard or edit a FlashCard.
	 * <p>
	 * Handles any exceptions if they are thrown
	 */
	public void onFinishedPressed() {
		try {
			if (isCreating) {
				createFlashCard();
			} else {
				editFlashCard();
			}
			handleClosing();
		}
		// Catch any exceptions to do with executing above code
		catch (Exception e) {
			screen.displayError(e.getMessage());
		}
	}

	/**
	 * Handles pressing of "FINISH" button, when creating a flashCard
	 * 
	 */
	public void createFlashCard() {
		currentFlashCard.setText(screen.frontText(), screen.backText());
		currentDeck.addFlashCard(currentFlashCard);
	}

	/**
	 * Handles pressing of "CONTINUE" button, when editing a flashCard
	 * 
	 */
	public void editFlashCard() {
		currentDeck.editFlashCard(currentFlashCard, screen.frontText(), screen.backText());
	}

	// ******************* General Helper methods ******************** //

	/**
	 * Returns a String presentation of what is currently being done to a flash card
	 * 
	 * @return String as specified
	 */
	public String operation() {
		if (isCreating) {
			return "Creating";
		} else {
			return "Editing";
		}
	}

	/**
	 * Returns the name of Decks that can be selected from when choosing which Deck
	 * a FlashCard belongs to
	 * 
	 * @return String[] containing deck objects as described in
	 *         deckManager.deckArray(Deck)
	 */
	public String[] deckArray() {
		return (deckManager.deckNameArray(currentDeck));
	}

	/**
	 * Finds the front text of the FlashCard that is being editted
	 * 
	 * @return String for the front text of currentFlashCard
	 */
	public String currentFlashCardFrontText() {
		return currentFlashCard.getFrontText();
	}

	/**
	 * Finds the back text of the FlashCard that is being edited
	 * 
	 * @return String for the back text of currentFlashCard
	 */
	public String currentFlashCardBackText() {
		return currentFlashCard.getBackText();
	}

	/**
	 * Getter method for isCreating
	 * 
	 * @return boolean value for isCreating
	 */
	public boolean getIsCreating() {
		return isCreating;
	}

	// ************* Methods to update Updateable objects *************** //

	/**
	 * Updates dependent Updateable objects
	 */
	@Override
	public void update() {
		updateable.receiveUpdate();
	}
}
