package tests;

import static org.junit.jupiter.api.Assertions.*;

import gui.guiLogic.*;
import gui.guiShell.Screen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.coreLogic.AppEnvironment;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import setup.Setup;

/**
 * Testing class for GuiManager,
 * <p>
 * Due to some methods requiring direct input from user, eg closing Screens,
 * these methods will be tested in integration testing, not unit testing
 */
class GuiManagerTest {

	GuiManager testGuiManager;
	AppEnvironment testAppEnvironment;
	Setup testSetup;
	MainScreenLogic testMainScreenLogic;
	ScreenLogic testMainScreenLogic2;
	EditDeckScreenLogic testEditDeckScreenLogic;
	Deck testDeck;
	FlashCard testFlashCard;
	ScreenLogic testEditFlashCardScreenLogic;

	@BeforeEach
	void beforeEach() {
		// Create a Setup class to hold session for tests
		testSetup = new Setup("C:\\SetupTests");
		testSetup.deleteSession("testName");
		testSetup.createNewSession("testName");
		testAppEnvironment = testSetup.getAppEnvironment();
		testAppEnvironment.onStartUp();

		// Create a test GuiManager
		testGuiManager = testAppEnvironment.getGuiManager();

		// Create a test Deck object
		testDeck = new Deck("testDeckName", null);
		testAppEnvironment.getDeckManager().addDeck(testDeck);

		// Create a test FlashCard object
		testFlashCard = new FlashCard("", "");

		// Create a test EditFlashCardScreenLogic
		testEditFlashCardScreenLogic = new EditFlashCardScreenLogic(testMainScreenLogic, testFlashCard, testDeck,
				testAppEnvironment, testEditDeckScreenLogic);
		testEditFlashCardScreenLogic.createScreen();

		// Create a test MainScreenLogic
		testMainScreenLogic = new MainScreenLogic(testAppEnvironment);
		testMainScreenLogic.createScreen();
		testMainScreenLogic.showScreen(); // So it's added to testGuiManager
		testMainScreenLogic.hideScreen(); // Just show screen doesn't pop up in tests

		// Create another test MainScreenLogic
		testMainScreenLogic2 = new MainScreenLogic(testAppEnvironment);
		testMainScreenLogic2.createScreen();

		// Create a test EditFlashCardScreenLogic
		testEditDeckScreenLogic = new EditDeckScreenLogic(testMainScreenLogic, testMainScreenLogic, testAppEnvironment,
				testDeck);
		testEditDeckScreenLogic.createScreen();
		testEditDeckScreenLogic.showScreen();
		testEditFlashCardScreenLogic.hideScreen(); // Just show screen doesn't pop up in tests
	}

	@Test
	void beforeEachTest() {
		// Check that both editDeckScreen and mainScreen were added to testGuiManager
		assertEquals(2, testGuiManager.getExistingScreens().size());
	}

	@Test
	void newScreenTest() {
		// Test with a Screen that has already been added.
		assertTrue(testGuiManager.newScreen(testEditDeckScreenLogic));
		assertEquals(2, testGuiManager.getExistingScreens().size());
		// Test with a Screen that has not yet been added

		assertTrue(testGuiManager.newScreen(testEditFlashCardScreenLogic));
		assertEquals(3, testGuiManager.getExistingScreens().size());

		// Can't test with a new Screen that clashes with another screen that has
		// already been added
		// Because will need to confirm quitting clashing screens, which creates
		// concurrency modification error
		// Test with a new Screen that clashes with another screen that has already been
		// added
		// assertFalse(testGuiManager.newScreen(testMainScreenLogic2));
	}

	@Test
	void canShowScreenTest() {
		// Test with a Screen that clashes with one already added
		assertFalse(testGuiManager.canShowScreen(testMainScreenLogic2));
		// Test with a Screen that is already added to guiManager
		assertTrue(testGuiManager.canShowScreen(testMainScreenLogic));
		// Test with a Screen that doesn't clash with any Screens already added
		assertTrue(testGuiManager.canShowScreen(testEditFlashCardScreenLogic));

	}

	@Test
	void canShowScreenHelperTest() {
		// Setup before tests
		Screen testMainScreen1 = testMainScreenLogic.getScreen();
		Screen testEditDeckScreen = testEditDeckScreenLogic.getScreen();
		Screen testMainScreen2 = testMainScreenLogic2.getScreen();
		// Test with different Screens
		assertTrue(testGuiManager.canShowScreenHelper(testMainScreen1, testEditDeckScreen));
		// Test with Screens that are the same object
		assertTrue(testGuiManager.canShowScreenHelper(testMainScreen1, testMainScreen1));
		// Test with two screens that are the same type but not the same object
		assertFalse(testGuiManager.canShowScreenHelper(testMainScreen2, testMainScreen1));
	}

	@Test
	void closeScreenTest() {
		// Test with a screen that has been added
		assertTrue(testGuiManager.closeScreen(testMainScreenLogic.getScreen()));
		assertEquals(1, testGuiManager.getExistingScreens().size());
		// Test with a screen that has not been added
		assertFalse(testGuiManager.closeScreen(testMainScreenLogic2.getScreen()));
		assertEquals(1, testGuiManager.getExistingScreens().size());
	}

	@AfterEach
	void afterEach() {
		// Deletes sessions that have been created for testing
		testSetup.deleteSession("testName");
		testAppEnvironment.closeDown();
	}
}
