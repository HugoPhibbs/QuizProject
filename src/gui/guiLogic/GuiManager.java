package gui.guiLogic;

import java.io.Serializable;
import java.util.ArrayList;

import gui.guiShell.Screen;

/**
 * Class to handle Screens for the GUI
 * <p>
 * Makes sure that there is only one Screen of each type currently being shown
 * to a user
 * <p>
 * NOTE: interaction with this class should only be done via AppEnvironment!
 *
 * @author Hugo Phibbs
 */
public class GuiManager implements Serializable {

    /**
     * ArrayList to containing the Screens that currently exist in this application
     * <p>
     * Is enforced in newScreen(Screen) that there can't be duplicate entries in
     * existingScreens
     */
    private ArrayList<Screen> existingScreens = new ArrayList<Screen>();

    /**
     * Constructor for GuiManager
     */
    public GuiManager() {
    }

    /**
     * Overloaded method with newScreen(Screen)
     * <p>
     * Finds result of calling newScreen(screenLogic.getScreen())
     *
     * @param screenLogic ScreenLogic object that a new Screen is being created for
     *
     * @return boolean value if the new Screen for screenLogic can be shown
     */
    public boolean newScreen(ScreenLogic screenLogic) {
        return newScreen(screenLogic.getScreen());
    }

    /**
     * Handles a new screen being created by a ScreenLogic class.
     * <p>
     * Makes sure that other Screens of the same type as newScreen are closed before
     * adding a Screen
     * <p>
     * If newScreen has already been added to GuiManager, then true is returned, but
     * newScreen is added to existingScreens
     * 
     * @param newScreen Screen object that has been created
     * 
     * @return boolean value if newScreen can be shown to a user or not
     */
    private boolean newScreen(Screen newScreen) {
        closeSameScreens(newScreen);
        if (screenAlreadyAdded(newScreen)) {
            return true;
        } else if (canShowScreen(newScreen)) {
            addScreen(newScreen);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks if a Screen has already been added to this GuiManager
     * <p>
     * This is helpful when a Screen that is wished to be shown has already been
     * added. For example, if EditDeckScreenLogic wants to show it's parentLogic's
     * Screen and it has already been addded to this GuiManager
     * 
     * @param screen Screen object to check if it has already been added
     * @return boolean value as described
     */
    public boolean screenAlreadyAdded(Screen screen) {
        return (existingScreens.contains(screen));
    }

    /**
     * Handles adding a Screen to this GuiManager
     *
     * @param newScreen Screen to be added
     *
     * @return boolean true as specified by Collection.add
     */
    private boolean addScreen(Screen newScreen) {
        return (existingScreens.add(newScreen));
    }

    // ************ Showing Screens *************** //

    /**
     * Overridden method of canShowScreen(Screen)
     * <p>
     * Returns result of canShowScreen(screenLogic.getScreen())
     *
     * @param screenLogic ScreenLogic object to detirmine if it's Screen can be
     *                    shown
     * @return boolean if inputted screenLogic's Screen can be shown or not
     */
    public boolean canShowScreen(ScreenLogic screenLogic) {
        return canShowScreen(screenLogic.getScreen());
    }

    /**
     * Handles if a Screen can be shown to a user by making sure that it doesnt
     * match with any other Screens that currently exist.
     * <p>
     * i.e. you can't show a MainScreen if one is already being shown to a user!
     *
     * @param newScreen Screen object to be checked if it can be shown
     *
     * @return boolean value if a Screen can be shown or not
     */
    public boolean canShowScreen(Screen newScreen) {
        for (Screen existingScreen : existingScreens) {
            if (!canShowScreenHelper(existingScreen, newScreen)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method for canShowScreen(Screen)
     * <p>
     * Does the actual checking part, best to separate into a method to make for
     * easy refactoring
     * <p>
     * If the existingScreen is the same object as newScreen, then the newScreen can
     * be shown by default. Otherwise, if existingScreen and newScreen are not the
     * same class, then newScreen can be shown
     * </p>
     *
     * @param existingScreen Screen object that is already contained in GuiManager that is to be compared to newScreen
     * @param newScreen Screen object that is to be checked if it can be shown
     *
     * @return boolean value if newScreen can be shown or not
     */
    public boolean canShowScreenHelper(Screen existingScreen, Screen newScreen) {
        if (existingScreen == newScreen) {
            return true;
        } else {
            return (!existingScreen.isSameClass(newScreen));
        }
    }

    // **************** Closing Screens ******************* //

    /**
     * Handles closing all the Screens for the GUI.
     * <p>
     * Closes Screens in reverse order that they were created. In order to close
     * child Screens before Parent Screens
     * <p>
     * User is not asked if they would like to close each screen individually, they
     * are all disposed of automatically.
     */
    public void closeAllScreens() {
        existingScreens.forEach(Screen::quit);
        existingScreens.clear();
    }

    /**
     * Handles closing a Screen
     * <p>
     * Does not ask user to confirm the closing of screen
     *
     * @param screen Screen object to be closed
     * @return boolean value if screen was closed or not, for this to happen, screen
     *         has to be contained in existingScreens
     */
    public boolean closeScreen(Screen screen) {
        if (existingScreens.remove(screen)) {
            screen.quit();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Closes any Screens in existingScreens which are the same class as newScreen.
     * <p>
     * For example, if you wanted to create a new CreateDeckScreen, then it would
     * close all Screens in existingScreens that were also CreateDeckScreens
     * <p>
     * Just as an overkill, has capability to close more than 1 Screen that is the
     * 'same' as newScreen. Even though newScreen(Screen) doesn't allow duplicate
     * entries in existingScreens anyways
     *
     * @param newScreen Screen instance that is to be checked against other screens
     *                  in existingScreens
     *
     * @return boolean if all of the 'same' Screens as newScreen were closed
     */
    private boolean closeSameScreens(Screen newScreen) {
        for (Screen existingScreen : existingScreens) {
            if (!canShowScreenHelper(existingScreen, newScreen)) {
                if (!existingScreen.confirmQuit()) {
                    return false; // Stops closing other screens
                }
            }
        }
        return true;
    }

    // ************* Getter and Setter methods *************** //

    /**
     * Getter method for existingScreens
     *
     * @return ArrayList of the current screens that are active in this application
     */
    public ArrayList<Screen> getExistingScreens() {
        return existingScreens;
    }
}
