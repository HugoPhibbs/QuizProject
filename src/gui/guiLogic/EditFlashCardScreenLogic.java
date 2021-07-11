package gui.guiLogic;

import core.coreLogic.DeckManager;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import gui.guiShell.EditFlashCardScreen;
import gui.guiShell.Screen;

/**
 * Handles logic for EditFlashCardScreen
 * 
 * @author Hugo Phibbs
 * @version 10/7/21
 * @since 10/7/21
 */
public class EditFlashCardScreenLogic extends ScreenLogic {

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
	 * Constructor for EditFlashCardScreenLogic
	 * <p>
	 * Sets attributes and creates a new EditFlashCardScreen
	 * 
	 * @param currentFlashCard    FlashCard object that is currently being editted,
	 *                            null if a FlashCard is being created
	 * @param currentDeck         Deck object that a user wants to add a FlashCard
	 *                            to
	 * @param deckManager         DeckManager object for this application
	 * @param parentLogic         ScreenLogic object that is the parent ScreenLogic
	 *                            to this object
	 * @param editFlashCardScreen EditFlashCardScreen object that is being
	 *                            controlled by this class.
	 */
	public EditFlashCardScreenLogic(FlashCard currentFlashCard, Deck currentDeck, DeckManager deckManager,
			ScreenLogic parentLogic) {
		super(parentLogic);
		this.currentFlashCard = currentFlashCard;
		this.currentDeck = currentDeck;
		this.deckManager = deckManager;
		handleEditingOrCreating();
	}

	/**
	 * Implementation of abstract method for ScreenLogic Creates a new
	 * EditFlashCardScreen and sets it as the Screen for this class.
	 */
	protected void createScreen() {
		this.screen = new EditFlashCardScreen(this);
		// screen.show();
	}

	// *************** Logic methods for EditFlashCardScreen ************ //

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

		// TODO Set combo box to have current deck to be selected
	}

	/**
	 * Returns the Deck's that can be selected from when choosing which Deck a
	 * FlashCard belongs to
	 * 
	 * @return Deck[] containing deck objects as described in
	 *         deckManager.deckArray(Deck)
	 */
	public Deck[] deckArray() {
		return (deckManager.deckArray(currentDeck));
	}

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
	 * Handles pressing of "CONTINUE" button, when creating a flashCard
	 * 
	 */
	public void createFlashCard() {
		currentFlashCard.setText(screen.frontText(), screen.backText());
		currentDeck.addFlashCard(currentFlashCard);
	}

	/**
	 * Handles what happens when a user presses continue
	 * <p>
	 * Diverts flow to either create a FlashCard or edit a FlashCard.
	 * <p>
	 * Handles any exceptions if they are thrown
	 */
	public void onContinuePressed() {
		try {
			if (isCreating) {
				createFlashCard();
			} else {
				editFlashCard();
			}
			// screen.switchToParent()
		}
		// Catch any exceptions to do with executing above code
		catch (Exception e) {
			screen.displayError(e.getMessage());
		}
	}

	/**
	 * Handles pressing of "CONTINUE" button, when editing a flashCard
	 * 
	 */
	public void editFlashCard() {
		currentDeck.editFlashCard(currentFlashCard, screen.frontText(), screen.backText());
	}

	public String currentFlashCardFrontText() {
		return currentFlashCard.getFrontText();
	}

	public String currentFlashCardBackText() {
		return currentFlashCard.getBackText();
	}

	public boolean getIsCreating() {
		return isCreating;
	}
}
