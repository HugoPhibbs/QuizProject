package tests;

import static org.junit.jupiter.api.Assertions.*;

import core.coreLogic.AppEnvironment;
import core.coreObjects.Deck;
import gui.guiLogic.EditDeckScreenLogic;
import gui.guiLogic.EditFlashCardScreenLogic;
import gui.guiLogic.MainScreenLogic;
import gui.guiShell.MainScreen;
import gui.guiShell.Screen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import setup.Setup;

/**
 * Testing class for Screen
 * 
 * @author Hugo Phibbs
 *
 */
public class ScreenTest {
    MainScreenLogic testMainScreenLogic;
    EditDeckScreenLogic testEditDeckScreenLogic;

    @BeforeEach
    void beforeEach() {

        Setup testSetup = new Setup("C:\\SetupTests");
        testSetup.deleteSession("testName");
        testSetup.createNewSession("testName");
        AppEnvironment testAppEnvironment = testSetup.getAppEnvironment();
        testAppEnvironment.onStartUp();

        Deck testDeck = new Deck("testDeckName", null);
        testAppEnvironment.getDeckManager().addDeck(testDeck);

        testMainScreenLogic = new MainScreenLogic(testAppEnvironment);
        testMainScreenLogic.createScreen();

        EditFlashCardScreenLogic testEditFlashCardScreenLogic = new EditFlashCardScreenLogic(testMainScreenLogic, null,
                testDeck, testAppEnvironment, testMainScreenLogic);
        testEditFlashCardScreenLogic.createScreen();

        testEditDeckScreenLogic = new EditDeckScreenLogic(testMainScreenLogic, testMainScreenLogic, testAppEnvironment,
                testDeck);
        testEditDeckScreenLogic.createScreen();
    }

    @Test
    void isSameClassTest() {
        // Test with two Screens that are different classes
        Screen testMainScreen = testMainScreenLogic.getScreen();
        Screen testEditDeckScreen = testEditDeckScreenLogic.getScreen();
        assertFalse(testMainScreen.isSameClass(testEditDeckScreen));
        // Test with two Screens that are the same class
        Screen testMainScreen2 = new MainScreen(testMainScreenLogic);
        assertTrue(testMainScreen.isSameClass(testMainScreen2));

    }
}
