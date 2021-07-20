package gui.guiLogic;

import java.io.Serializable;
import java.util.ArrayList;

import gui.guiShell.Screen;

public class GuiManager implements Serializable {

    private ArrayList<Screen> currentScreens = new ArrayList<Screen>();

    public GuiManager() {
        // TODO implement!
    }

    public boolean canOpenNewScreen(ScreenLogic screenLogic) {
        return canOpenNewScreen(screenLogic.getScreen());
    }

    /**
     * Handles opening a new screen. Closes all other Screens in newScreen.
     * <p>
     * Then adds newScreen to currentScreens
     * 
     * @param newScreen Screen object to be opened
     * @return boolean if newScreen can be opened or not. That is, if all other
     *         Screens in currentScreens that were the same as newScreen were
     *         closed.
     */
    public boolean canOpenNewScreen(Screen newScreen) {
        closeSameScreens(newScreen);
        if (canOpenScreen(newScreen)) {
            currentScreens.add(newScreen);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Handles closing all the Screens for the GUI.
     * <p>
     * Closes Screens in reverse order that they were created. In order to close
     * child Screens before Parent Screens
     * 
     * @return boolean value if all Screens were closed.
     */
    public boolean closeAllScreens() {
        for (int i = currentScreens.size() - 1; i > -1; i--) {
            Screen currentScreen = currentScreens.get(i);
            if (!currentScreen.confirmQuit()) {
                return false;
            }
        }
        return true;
    }

    public boolean canOpenScreen(Screen newScreen) {
        for (Screen screen : currentScreens) {
            if (screen.getClass().equals(newScreen.getClass())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Closes any Screens in currentScreens which are the same class as newScreen.
     * <p>
     * For example, if you wanted to create a new CreateDeckScreen, then it would
     * close all Screens in currentScreens that were also CreateDeckScreens
     * 
     * @param newScreen Screen instance that is to be checked against other screens
     *                  in currentScreens
     */
    private void closeSameScreens(Screen newScreen) {
        currentScreens.removeIf(screen -> screen.getClass().equals(newScreen.getClass()));
        for (Screen screen : currentScreens) {
            if (screen.getClass().equals(newScreen.getClass())) {
                screen.confirmQuit();
            }
        }
    }
}

// Have creating new screens be implemented by guiManager or logic classes?
