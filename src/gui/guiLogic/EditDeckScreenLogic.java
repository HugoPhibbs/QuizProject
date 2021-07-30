package gui.guiLogic;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import gui.guiShell.EditDeckScreen;

/**
 * ScreenLogic class to control an EditDeckScreen object
 * 
 * @author Hugo Phibbs
 */
public class EditDeckScreenLogic extends ScreenLogic implements Updateable, Updater {

    /**
     * DeckManager object for this application
     */
    DeckManager deckManager;
    /**
     * Deck object that is being editted
     */
    Deck deck;
    /**
     * FlashCard object that has been selected, none if none selected
     */
    FlashCard chosenFlashCard = null;
    /**
     * EditDeckScreen object that this class controls
     */
    EditDeckScreen screen;
    /**
     * Updateable object that is updated when a Deck is edited
     */
    Updateable updateable;
    /**
     * AppEnvironment object for this application
     */
    AppEnvironment appEnvironment;

    /**
     * Constuctor for EditDeckScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object that created this object
     * @param updateable        Updateable object that this class updates upon
     *                          closing
     * @param appEnvironment    AppEnvironment object for this application
     * @param deck              Deck object that is currently being
     */
    public EditDeckScreenLogic(ScreenLogic parentScreenLogic, Updateable updateable, AppEnvironment appEnvironment,
            Deck deck) {
        super(parentScreenLogic, appEnvironment);
        this.updateable = updateable;
        this.deck = deck;
        this.appEnvironment = appEnvironment;
        this.deckManager = appEnvironment.getDeckManager();
    }

    @Override
    public void createScreen() {
        screen = new EditDeckScreen(this);
        super.setScreen(screen);
        configScreenBtns(false);
    }

    @Override
    public void closeScreen() {
        update();
        deleteScreen();
    }

    // ******************* Handling Listener Events ******************** //

    /**
     * Handles a FlashCard being selected on EditDeckScreen
     * 
     * @param lse ListSelectionEvent that was created from selecting a FlashCard
     */
    public void flashCardSelected(ListSelectionEvent lse) {
        // Bellow lines ensures that changing selection of a row counts as one change,
        // not two
        if (!lse.getValueIsAdjusting()) {
            screen.toggleComponent(screen.getBtnEditFlashCard(), true);
            screen.toggleComponent(screen.getBtnDeleteFlashCard(), true);
            updateChosenFlashCard();
        }
    }

    /**
     * Updates the value of the chosenFlashCard, whenever a user chooses a FlashCard
     * from the table, this method is called
     */
    private void updateChosenFlashCard() {
        JTable table = screen.getTableFlashCards();
        int selectedRow = table.getSelectedRow();
        String frontText = table.getModel().getValueAt(selectedRow, 0).toString();
        String backText = table.getModel().getValueAt(selectedRow, 1).toString();
        chosenFlashCard = deck.findFlashCard(frontText, backText);
        if (chosenFlashCard == null) {
            throw new IllegalStateException("chosenFlashCard is null, when it should be a FlashCard object!");
        }
    }

    /**
     * Handles pressing of btnDeleteDeck for the screen
     */
    public void deleteDeck() {
        deckManager.removeDeck(deck);
        closeScreen();
    }

    /**
     * Handles pressing of btnContinue for the screen
     */
    public void onContinue() {
        try {
            editDeckName();
            editDeckDescription();
            closeScreen();
        } catch (Exception e) {
            screen.displayError(e.getMessage());
        }
    }

    /**
     * Handles editting the name of the deck that is currently being editted.
     */
    private void editDeckName() {
        deckManager.renameDeck(deckName(), enteredDeckName());
    }

    /**
     * Handles editting the description of the Deck that is currently being editted
     */
    private void editDeckDescription() {
        deck.setDescription(enteredDeckDescription());
    }

    /**
     * Finds the text that is currently entered in textFieldDescription for the
     * Screen that this class is controlling
     * 
     * @return String as described
     */
    private String enteredDeckDescription() {
        return screen.getTextFieldDescription().getText();
    }

    /**
     * Finds the text that is currently entered in textFielEnterName for the Screen
     * that this class is controlling
     * 
     * @return String as described
     */
    private String enteredDeckName() {
        return screen.getTextFieldName().getText();
    }

    /**
     * Handles pressing of btnEditFlashCard for the screen
     */
    public void editFlashCard() {
        // When the FlashCard is changed, call updatePanelFlashCards()
        newEditFlashCardScreen(chosenFlashCard);
    }

    /**
     * Handles pressing of btnAddFlashCard for the screen
     */
    public void addFlashCard() {
        newEditFlashCardScreen(null);
    }

    /**
     * Creates a new EditFlashCardScreen object for the parameter flashCard.
     * <p>
     * If a new flashCard is wanted to be created, flashCard should be null
     * 
     * @param flashCard FlashCard object that is to have an EditFlashCardScreen
     *                  created for
     */
    private void newEditFlashCardScreen(FlashCard flashCard) {
        EditFlashCardScreenLogic editFlashCardScreenLogic = new EditFlashCardScreenLogic(this, flashCard, deck,
                appEnvironment, this);
        editFlashCardScreenLogic.createScreen();
        editFlashCardScreenLogic.showScreen();
        hideParent();
    }

    /**
     * Deletes chosenFlashCard from the current Deck
     */
    public void deleteFlashCard() {
        deck.removeFlashCard(chosenFlashCard);
        resetPanelFlashCards();
    }

    // ******************* Helper methods *********************** //

    /**
     * Finds the title for the Screen
     * 
     * @return String for the title of EditDeckScreen
     */
    public String title() {
        return String.format("Editing %s", deckName());
    }

    /**
     * Finds the name of the deck that is currently being editted
     * 
     * @return String for the name of the deck that is currently being editted.
     */
    public String deckName() {
        return deck.getName();
    }

    /**
     * Finds the description of the Deck that is currently being editted
     * 
     * @return String for the description of the deck that is currently being
     *         editted
     */
    public String deckDescription() {
        return deck.getDescription();
    }

    /**
     * Finds and returns the content to be shown in tableFlashCards for the Screen
     * <p>
     * Gets a nested array representation of all the FlashCards in the deck that is
     * currently being editted
     * 
     * @return String[][] array containing info as described
     */
    public String[][] flashCardsTableDetails() {
        return deck.flashCardsTableArray();
    }

    /**
     * Finds and returns the column headers for tableFlashCards for the Screen
     * 
     * @return Stringp[] array as described
     */
    public String[] flashCardsTableHeaders() {
        return FlashCard.infoArrayHeaders();
    }

    // ************** Methods to update EditDeckScreen ******************** //

    /**
     * Acts on recieving an update from an Updater object Instead of refereshing
     * affected components, it creates a brand new screen, as this is simpler than
     * configuring all of the components of and EditFlashCardScreen instance to be
     * asnew
     * <p>
     * Also shows the parent to this screen, which would have been hidden upon
     * creating another Screen from this Screen.
     */
    @Override
    public void receiveUpdate() {
        showParent();
        screen.quit();
        createScreen();
    }

    /**
     * Resets panelFlashCards belonging to the Screen. Does this by removing all
     * components from the panel and then filling it back up with necessary
     * components
     */
    public void resetPanelFlashCards() {
        screen.clearContainer(screen.getPanelFlashCards());
        screen.updatePanelFlashCards();
    }

    /**
     * Enables or diables specific buttons belonging to Screen, so that they can be
     * reset to normal when this Screen is refreshed, or enabled when a FlashCard is
     * selected from tableFlashCards belonging to the Screen
     * 
     * @param setting boolean value if buttons will be enabled or not
     */
    public void configScreenBtns(boolean setting) {
        screen.toggleComponent(screen.getBtnDeleteFlashCard(), setting);
        screen.toggleComponent(screen.getBtnEditFlashCard(), setting);
    }

    // ************* Methods to notify Updateable object ********************** //

    @Override
    public void update() {
        updateable.receiveUpdate();
    }
}
