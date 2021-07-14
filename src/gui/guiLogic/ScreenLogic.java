package gui.guiLogic;

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

    /**
     * Constructor for ScreenLogic
     * 
     * @param parentScreenLogic ScreenLogic object to be set as this object's parent
     */
    public ScreenLogic(ScreenLogic parentScreenLogic) {
        this.parentLogic = parentScreenLogic;
    }

    // *********** Creating, deleting and closing the Screen *********** //

    /**
     * Abstract method for creating Screen for this ScreenLogic class
     * <p>
     * Allows a user to initialize the value of screen for this class.
     */
    public abstract void createScreen();

    /**
     * Abstract method to handle closing of this screen In most cases, this means
     * that the current ScreenLogic needs to update the state of it's dependent
     * ScreenLogic objects
     */
    public abstract void closeScreen();

    /**
     * Handles deleting the screen of this ScreenLogic class
     */
    public void deleteScreen() {
        screen.quit();
    }

    /**
     * Hides the Screen object for this class
     */
    public void hideScreen() {
        screen.hide();
    }

    /**
     * Shows the Screen object for this class
     */
    public void showScreen() {
        screen.show();
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
     * Handles switching to the screen that this class is controlling
     */
    public void switchScreens() {
        screen.show();
        if (parentLogic != null) {
            // parentLogic.getScreen().hide();
        }
    }

    /**
     * Hides the parent Screen for this class
     */
    protected void hideParent() {
        parentLogic.hideScreen();
    }

    /**
     * Shows the parent Screen for this class
     */
    protected void showParent() {
        parentLogic.showScreen();
    }

    // ************** Setter and getter methods ****************** //

    /**
     * Getter method for the Screen of this class
     * 
     * @return Screen object belonging to this ScreenLogic class
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Setter method for the Screen of this object
     * 
     * @param screen
     */
    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
