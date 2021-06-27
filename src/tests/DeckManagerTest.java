package tests;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coreLogic.DeckManager;
import coreObjects.Deck;

class DeckManagerTest {
	
	DeckManager testDeckManager;
	Deck testDeck1;
	Deck testDeck2;
	Deck testDeck3;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		// Create test objects needed
		testDeck1 = new Deck("testNameOne", "", null);
		testDeck2 = new Deck("testNameTwo", "", null);
		testDeck3 = new Deck("testNameThree", "", null);
		
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
		Deck testDeck4 = new Deck("testNameFour", "", null);
		
		assertEquals(true, testDeckManager.addDeck(testDeck4));
		assertEquals(false, testDeckManager.addDeck(testDeck1)); // testDeck1 already added
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
		assertThrows(IllegalArgumentException.class, () -> {testDeckManager.renameDeck("testDeckFour", "");});
		
		// Test with a new name that clashes with a deck that is already in the collection
		assertThrows(IllegalArgumentException.class, () -> {testDeckManager.renameDeck("testDeckOne", "testDeckTwo");});
	}
	
	@Test
	public void createDeckTest() {
		// Test normally
		assertEquals(true, testDeckManager.createDeck("testNameFour", ""));
		
		// Test with a deck name that clashes with one already added
		assertThrows(IllegalArgumentException.class, () -> {testDeckManager.createDeck("testNameOne", "");});
		
		// Test with a deck name that is invalid
		assertThrows(IllegalArgumentException.class, () -> {testDeckManager.createDeck("te  stmameOne", "");});
	}

}
