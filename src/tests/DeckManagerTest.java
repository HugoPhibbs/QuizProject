package tests;

import static org.junit.jupiter.api.Assertions.*; 

import org.junit.jupiter.api.BeforeAll;
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
		testDeck1 = new Deck("testName1", "", null);
		testDeck2 = new Deck("testName2", "", null);
		testDeck3 = new Deck("testName3", "", null);
		
		testDeckManager = new DeckManager();
		testDeckManager.addDeck(testDeck1);
		testDeckManager.addDeck(testDeck2);
	}
	
	@Test
	public void findDeckTest() {
		
		assertEquals(testDeck1, testDeckManager.findDeck("testName1"));
		assertEquals(null, testDeckManager.findDeck("testName4"));
	}
	
	@Test
	public void containsDeckTest() {
		// Test with containsDeck(Deck)
		assertEquals(true, testDeckManager.containsDeck(testDeck1));
		assertEquals(false, testDeckManager.containsDeck(testDeck3));
		
		// Test with containsDeck(String)
		assertEquals(true, testDeckManager.containsDeck("testName1"));
		assertEquals(false, testDeckManager.containsDeck("testName3"));
	}
	
	@Test
	public void addDeckTest() {
		Deck testDeck4 = new Deck("testName4", "", null);
		
		assertEquals(true, testDeckManager.addDeck(testDeck4));
		assertEquals(false, testDeckManager.addDeck(testDeck1)); // testDeck1 already added
	}
	
	@Test
	public void removeDeckTest() {
		assertEquals(true, testDeckManager.removeDeck("testName1"));
		assertEquals(false, testDeckManager.removeDeck("testName1")); // testDeck1 already removed
		assertEquals(false, testDeckManager.removeDeck("testName4")); 
	}
	
	@Test
	public void renameDeckTest() {
		// Test with a deck that is already contained
		assertEquals(true, testDeckManager.renameDeck("testName1", "German"));
		assertEquals("German", testDeckManager.findDeck("German").getName());
		
		// Test with a deck that is 
	}

}
