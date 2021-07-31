package gui.guiLogic;

import core.coreLogic.AppEnvironment;
import core.coreLogic.DeckManager;
import gui.guiShell.CreateDeckScreen;

/**
 * Handles logic to do with CreateDeckScreen
 * 
 * @author Hugo Phibbs
 * 
 */
public class CreateDeckScreenLogic extends ScreenLogic implements Updater {

    /**
     * DeckManager object for this application
     */
    DeckManager deckManager;
    /**
     * CreateDeckScreen object that is being controlled by this class
     */
    CreateDeckScreen screen;
    /**
     * Updateable object dependent on this class
     */
    Updatable updateable;

    /**
     * Constructor for CreateDeckScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object for the parent screen of this
     *                          ScreenLogic object
     * @param appEnvironment    AppEnvironment object for this application
     */
    public CreateDeckScreenLogic(ScreenLogic parentScreenLogic, AppEnvironment appEnvironment, Updatable updateable) {
        super(parentScreenLogic, appEnvironment);
        this.deckManager = appEnvironment.getDeckManager();
        this.updateable = updateable;
    }

    // **************** Creating and closing the Screen ******************* //

    @Override
    public void createScreen() {
        screen = new CreateDeckScreen(this);
        super.setScreen(screen);
    }

    @Override
    public void closeScreen() {
        update();
        deleteScreen();
    }

    // ******************* Handling Listener Events ********************** //

    /**
     * Handles creating a new deck, if there is an error while creating, then an
     * error is shown on CreateDeckScreen
     */
    public void createDeck() {
        try {
            deckManager.createDeck(nameText(), descriptionText());
            handleClosing();
        } catch (IllegalArgumentException iae) {
            screen.displayError(iae.getMessage());
        }
    }

    /**
     * Handles text being entered for a deck name by a user
     * <p>
     * Makes sure that if the name entered isn't empty before enabling the button
     * for a user to create a deck
     * 
     */
    public void nameChanged() {
        boolean btnSetting = false;
        if (!nameText().equals("")) {
            btnSetting = true;
        }
        screen.toggleComponent(screen.getBtnCreateDeck(), btnSetting);
    }

    // ************** General Helper Methods ******************* //

    /**
     * Finds the description text entered for a new Deck
     * 
     * @return String for the description of a new Decks
     */
    public String descriptionText() {
        return screen.getTextFieldDescription().getText();
    }

    /**
     * Finds the name entered for a new Deck
     * 
     * @return String for the name of a new Deck
     */
    public String nameText() {
        return screen.getTextFieldName().getText();
    }

    // ********** Methods to update Updateable Object ************* //

    @Override
    public void update() {
        updateable.receiveUpdate();
    }

}
