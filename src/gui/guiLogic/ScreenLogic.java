package gui.guiLogic;

import gui.guiShell.Screen;

/**
 * Abstract class to be implemted by classes to manipulate Screen classes
 * 
 * @author Hugo Phibbs
 */
public abstract class ScreenLogic {

    /**
     * Screen object that is being manipulated by this class
     */
    Screen screen;
    /**
     * ScreenLogic object that belongs to the parent screen of the screen that this
     * ScreenLogic object is controlling
     */
    ScreenLogic parentLogic;

    public ScreenLogic(ScreenLogic parentScreenLogic) {
        this.parentLogic = parentScreenLogic;

    }

    // public ScreenLogic(ScreenLogic parentScreenLogic) {
    // this.parentScreenLogic = parentScreenLogic;
    // }

    /**
     * Abstract method for creating Screen for this ScreenLogic class
     * <p>
     * Allows a user to initialize the value of screen for this class.
     */
    public abstract void createScreen();

    /**
     * Handles deleting the screen of this ScreenLogic class
     */
    public void deleteScreen() {
        screen.quit();
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
}
