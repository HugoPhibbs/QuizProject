package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.coreLogic.DeckManager;
import core.coreObjects.Deck;
import core.coreObjects.FlashCard;

/**
 * JUnit testing class for DeckManager
 * 
 * @author Hugo Phibbs
 *
 */
class DeckManagerTest {

	DeckManager testDeckManager;
	Deck testDeck1;
	Deck testDeck2;
	Deck testDeck3;
	FlashCard testCard1;
	FlashCard testCard2;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		// Create test objects needed
		testDeck1 = new Deck("testNameOne", "");
		testDeck2 = new Deck("testNameTwo", "");
		testDeck3 = new Deck("testNameThree", "");

		testCard1 = new FlashCard("testFrontOne", "testBackOne");
		testCard2 = new FlashCard("testFrontTwo", "testBackTwo");

		testDeckManager = new DeckManager();
		testDeckManager.addDeck(testDeck1);
		testDeckManager.addDeck(testDeck2);
	}

	@Test
	public void findDeckTest() {

		assertEquals(testDeck1, testDeckManager.findDeck("testNameOne"));
		assertEquals(null, testDeckManager.findDeck("testNameFour")); // Deck with name doesn't exist
	}

	@Test
	public void containsDeckTest() {
		// Test with containsDeck(Deck)
		assertEquals(true, testDeckManager.containsDeck(testDeck1));
		assertEquals(false, testDeckManager.containsDeck(testDeck3));

		// Test with containsDeck(String)
		assertEquals(true, testDeckManager.containsDeck("testNameOne"));
		assertEquals(false, testDeckManager.containsDeck("testNameThree"));
	}

	@Test
	public void addDeckTest() {
		Deck testDeck4 = new Deck("testNameFour", "");

		assertEquals(true, testDeckManager.addDeck(testDeck4));
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.addDeck(testDeck1);
		}); // testDeck1 already added
	}

	@Test
	public void removeDeckTest() {
		assertEquals(true, testDeckManager.removeDeck("testNameOne"));
		assertEquals(false, testDeckManager.removeDeck("testNameOne")); // testDeck1 already removed
		assertEquals(false, testDeckManager.removeDeck("testNameFour"));
	}

	@Test
	public void renameDeckTest() {
		// Test with a deck that is already in the DeckManager collection
		assertEquals(true, testDeckManager.renameDeck("testNameOne", "German"));
		assertEquals("German", testDeckManager.findDeck("German").getName());

		// Test with a deck that isn't in the deckManager collection
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.renameDeck("testNameFour", "");
		});

		// Test with a new name that clashes with a deck that is already in the
		// collection
		assertEquals(false, testDeckManager.renameDeck("German", "testNameTwo"));
	}

	@Test
	public void createDeckTest() {
		// Test normally
		assertEquals(true, testDeckManager.createDeck("testNameFour", ""));

		// Test with a deck name that clashes with one already added
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.createDeck("testNameOne", "");
		});

		// Test with a deck name that is invalid
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.createDeck("te  stmameOne", "");
		});
	}

	@Test
	public void changeFlashCardDeckTest() {

		// Test normally
		testDeck1.addFlashCard(testCard1);
		assertEquals(true, testDeckManager.changeFlashCardDeck(testCard1, "testNameOne", "testNameTwo"));
		assertEquals(false, testDeck1.contains(testCard1)); // Check FlashCard was removed from source Deck
		assertEquals(true, testDeck2.contains(testCard1)); // Check FlashCard was added to destination Deck
		// Test with flash card that already exists in another Deck
		testDeck1.addFlashCard(testCard2);
		testDeck2.addFlashCard(testCard2);
		assertEquals(false, testDeckManager.changeFlashCardDeck(testCard2, "testNameOne", "testNameTwo"));
		// Test with a source deck that doesn't exist in the collection
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.changeFlashCardDeck(testCard2, "testNameFour", "testNameThree");
		});
		// Test with a destination deck that doesn't exist in the collection
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.changeFlashCardDeck(testCard2, "testNameOne", "testNameFour");
		});
		// Test with a source deck that doesn't contain a FlashCard
		FlashCard testCard4 = new FlashCard("testFrontFour", "testBackFour");
		assertThrows(IllegalArgumentException.class, () -> {
			testDeckManager.changeFlashCardDeck(testCard4, "testNameOne", "testNameTwo");
		});
	}

	@Test
	public void deckNameArrayTest() {
		// Test with a deckManager with only one deck
		testDeckManager.removeDeck(testDeck2);
		String[] result = testDeckManager.deckNameArray(testDeck1);
		assertEquals(1, result.length);
		assertEquals("testNameOne", result[0]);
		// Test with a deckManager with multiple decks
		testDeckManager.addDeck(testDeck2);
		testDeckManager.addDeck(testDeck3);
		result = testDeckManager.deckNameArray(testDeck2);
		assertEquals(3, result.length);
		assertEquals(testDeck2.getName(), result[0]);
	}
}