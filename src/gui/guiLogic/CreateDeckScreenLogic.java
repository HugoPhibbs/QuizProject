package gui.guiLogic;

import core.coreLogic.DeckManager;
import gui.guiShell.CreateDeckScreen;

/**
 * Handles logic to do with CreateDeckScreen
 * 
 * @author Hugo Phibbs
 * 
 */
public class CreateDeckScreenLogic extends ScreenLogic implements Updater {

    /** DeckManager object for this application */
    DeckManager deckManager;
    /** CreateDeckScreen object that is being controlled by this class */
    CreateDeckScreen screen;
    /** Updateable object dependent on this class */
    Updateable updateable;

    /**
     * Constructor for CreateDeckScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object for the parent screen of this
     *                          ScreenLogic object
     * @param deckManager       DeckManager object for this application
     */
    public CreateDeckScreenLogic(ScreenLogic parentScreenLogic, DeckManager deckManager, Updateable updateable) {
        super(parentScreenLogic);
        this.deckManager = deckManager;
        this.updateable = updateable;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void createScreen() {
        screen = new CreateDeckScreen(this);
        // screen.show();
        super.setScreen(null);

    }

    /**
     * Handles creating a new deck, if there is an error while creating, then an
     * error is shown on CreateDeckScreen
     */
    public void createDeck() {
        try {
            deckManager.createDeck(screen.nameText(), screen.descriptionText());
            closeScreen();
        } catch (IllegalArgumentException iae) {
            screen.displayError(iae.getMessage());
        }
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        updateable.receiveUpdate();
    }

    @Override
    public void closeScreen() {
        // TODO Auto-generated method stub
        update();
        screen.quit();
    }

}
