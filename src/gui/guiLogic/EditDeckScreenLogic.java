package gui.guiLogic;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;

import core.coreLogic.DeckManager;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import gui.guiShell.EditDeckScreen;
import gui.guiShell.Screen;

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
    }

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

    public void editFlashCard() {
        // TODO implement

        // Create a new editFlashCardScreen
        // When the FlashCard is changed, call updatePanelFlashCards()
        EditFlashCardScreenLogic editFlashCardScreenLogic = new EditFlashCardScreenLogic(chosenFlashCard, deck,
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

    public void onContinue() {
        // TODO implement!

        updateable.receivedUpdate();
    }

    public void deleteDeck() {
        // TODO implement
    }

    public void addFlashCard() {
        // TODO implement
    }

    @Override
    public void receivedUpdate() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update() {
        // TODO Auto-generated method stub

    }
}
