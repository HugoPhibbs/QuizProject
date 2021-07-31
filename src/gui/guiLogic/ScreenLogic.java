package gui.guiLogic;

import core.coreLogic.AppEnvironment;
import gui.guiShell.Screen;

/**
 * Abstract class to be implemted by classes to manipulate Screen classes
 * 
 * @author Hugo Phibbs
 */
public abstract class ScreenLogic {

    /**
     * Screen object that is being manipulated by this class. Note that this is kept
     * general to Screen to force structure for ScreenLogic implementations, So that
     * methods for this class that involve a Screen object will work.
     * <p>
     * To note is that a class that extends ScreenLogic will have it's own Screen
     * object that has all the methods for especially modifying the Screen. If the
     * ScreenLogic extended classes didn't have that, there would be no way to
     * access methods particular to that Screen extendation. Eg fore EditFlashCard
     * Screen, you would be able use the method getBtnFinish()
     */
    private Screen screen;
    /**
     * ScreenLogic object that belongs to the parent screen of the screen that this
     * ScreenLogic object is controlling
     */
    private ScreenLogic parentLogic;
    /** AppEnvironment object for this application */
    private AppEnvironment appEnvironment;

    /**
     * Constructor for ScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object to be set as this object's parent
     * @param appEnvironment AppEnvironment application for the current Application session
     */
    public ScreenLogic(ScreenLogic parentScreenLogic, AppEnvironment appEnvironment) {
        this.parentLogic = parentScreenLogic;
        this.appEnvironment = appEnvironment;
    }

    // @Override
    // public String toString() {
    // return String.format("ScreenLogic: (parentLogic: %s, screen: %s)",
    // parentLogic, screen);
    // }

    // *********** Hiding and closing Screens *********** //

    /**
     * Abstract method for creating Screen for this ScreenLogic class
     * <p>
     * Allows a user to initialize the value of screen for this class.
     * <p>
     * Likely place where user sets the Screen object for this class (ScreenLogic0
     */
    public abstract void createScreen();

    /**
     * Handles closing of this screen
     */
    public void handleClosing() {
        closeScreen();
        showParent();
    }

    /**
     * Abstract method to handle closing of this screen In most cases, this means
     * that the current ScreenLogic needs to update the state of it's dependent
     * ScreenLogic objects
     * <p>
     * All closeScreen() implementations should by default call deleteScreen(),
     * along with any special operations that need to be done (may be none) eg
     * update dependent ScreenLogic classes, close down gui etc
     */
    protected abstract void closeScreen();

    /**
     * Handles deleting the screen of this ScreenLogic's Screen
     */
    public void deleteScreen() {
        appEnvironment.getGuiManager().closeScreen(screen);
    }

    /**
     * Hides the Screen object for this class
     */
    public void hideScreen() {
        screen.hide();
    }

    // ********** Showing Screens ************ /

    /**
     * Shows the Screen object for this class.
     * <p>
     * First checks if the Screen can be opened as per
     * appEnvironment.canOpenNewScreen()
     */
    public void showScreen() {
        if (appEnvironment.getGuiManager().newScreen(this)) {
            screen.show();
        }
    }

    /**
     * Handles creating and showing a Screen for inputted ScreenLogic
     * <p>
     * Then calls method to hide this Screen
     * <p>
     * Screen is not the same Screen as this object's
     * 
     * @param screenLogic ScreenLogic object whose Screen will be shown
     */
    public void handleNewScreen(ScreenLogic screenLogic) {
        screenLogic.createAndShowScreen();
        hideScreen();
    }

    /**
     * Handles creating and showing the Screen for this class
     */
    public void createAndShowScreen() {
        createScreen();
        showScreen();
    }

    // ************* Switching Between Screens *********************** //

    /**
     * Handles switching to the parent of this class
     */
    public void switchToParent() {
        if (parentLogic == null) {
            throw new IllegalStateException("This screen doesn't have a parent that can be switched to!");
        } else {
            screen.hide();
            // parentLogic.getScreen().show();
        }
    }

    /**
     * Shows the parent Screen for this class
     */
    protected void showParent() {
        if (parentLogic != null) {
            parentLogic.showScreen();
        }
    }

    // ************** Setter and Getter methods ****************** //

    /**
     * Returns the name of the class object that extends this Class
     * 
     * @return String for the name of a class as described
     */
    public String className() {
        return getClass().getName();
    }

    /**
     * Getter method for the Screen of this class
     * 
     * @return Screen object belonging to this ScreenLogic class
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Getter method for appEnivironment
     * 
     * @return AppEnvironment object as described
     */
    public AppEnvironment getAppEnvironment() {
        return appEnvironment;
    }

    /**
     * Setter method for screen
     * 
     * @param screen Screen object to be set
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
