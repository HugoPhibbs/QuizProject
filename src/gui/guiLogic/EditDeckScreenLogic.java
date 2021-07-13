package gui.guiLogic;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

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

    Updateable updateable;

    public EditDeckScreenLogic(ScreenLogic parentScreenLogic, Updateable updateable, DeckManager deckManager,
            Deck deck) {
        super(parentScreenLogic);
        this.updateable = updateable;
        this.deck = deck;
        this.deckManager = deckManager;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createScreen() {
        screen = new EditDeckScreen(this);
        screen.show();
        super.setScreen(null);
    }

    /**
     * Handles the closing of an EditDeckScreen instance. Updates any dependents and
     * then closes this screen
     */
    public void closeScreen() {
        update();
        screen.quit();
    }

    // ******************* Handling Listener Events ******************** //

    public void flashCardSelected(ListSelectionEvent lse) {
        // Bellow lines ensures that changing selection of a row counts as one change,
        // not two
        if (!lse.getValueIsAdjusting()) {
            screen.toggleComponent(screen.getBtnEditFlashCard(), true);
            screen.toggleComponent(screen.getBtnDeleteFlashCard(), true);
            updateChosenFlashCard();
        }
    }

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
        // TODO implement!
        try {
            editDeckName();
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
     * Finds the text that is currently entered in textFielEnterName for the Screen
     * that this class is controlling
     * 
     * @return String as described
     */
    private String enteredDeckName() {
        return screen.getTextFieldName().getText();
    }

    // ******************* Methods for Managing FlashCards ******************* //

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
                deckManager, this);
        editFlashCardScreenLogic.createScreen();
    }

    /**
     * Deletes chosenFlashCard from the current Deck
     */
    public void deleteFlashCard() {
        deck.removeFlashCard(chosenFlashCard);
        screen.updatePanelFlashCards();
    }

    // ******************* Helper methods *********************** //

    /**
     * Finds the name of the deck that is currently being editted
     * 
     * @return String for the name of the deck that is currently being editted.
     */
    public String deckName() {
        return deck.getName();
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

    // ************ Methods to update EditDeckScreen ******************** //

    /**
     * Acts on recieving an update from an Updater object Instead of refereshing
     * affected components, it creates a brand new screen, as this is simpler than
     * configuring all of the components of and EditFlashCardScreen instance to be
     * asnew
     */
    @Override
    public void receiveUpdate() {
        screen.quit();
        createScreen();
    }

    @Override
    public void update() {
        updateable.receiveUpdate();
    }

}
