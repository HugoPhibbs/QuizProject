package gui.guiLogic;

import core.coreLogic.DeckManager;
import gui.guiShell.CreateDeckScreen;

/**
 * Handles logic to do with CreateDeckScreen
 * 
 * @author Hugo Phibbs
 * 
 */
public class CreateDeckScreenLogic extends ScreenLogic {

    /** DeckManager object for this application */
    DeckManager deckManager;
    /** CreateDeckScreen object that is being controlled by this class */
    CreateDeckScreen screen;

    /**
     * Constructor for CreateDeckScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object for the parent screen of this
     *                          ScreenLogic object
     * @param deckManager       DeckManager object for this application
     */
    public CreateDeckScreenLogic(ScreenLogic parentScreenLogic, DeckManager deckManager) {
        super(parentScreenLogic);
        this.deckManager = deckManager;
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void createScreen() {
        screen = new CreateDeckScreen(this);
        // screen.show();

    }

    /**
     * Handles creating a new deck, if there is an error while creating, then an
     * error is shown on CreateDeckScreen
     */
    public void createDeck() {
        try {
            deckManager.createDeck(screen.nameText(), screen.descriptionText());
        } catch (IllegalArgumentException iae) {
            screen.displayError(iae.getMessage());
        }
    }
}
