package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import coreObjects.Deck;
import coreObjects.FlashCard;

class DeckTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	public void equalsTest() {
		Deck testDeck1 = new Deck("testNameOne", "", null);
		Deck testDeck2 = new Deck("testNameOne", "", null);
		Deck testDeck3 = new Deck("testNameTwo", "", null);
		
		FlashCard testCard1 = new FlashCard("A", "B");
		testDeck1.addFlashCard(testCard1);
		testDeck2.addFlashCard(testCard1);
		
		// Test with two equal decks
		assertEquals(true, testDeck1.equals(testDeck2));
		// Test with two in-equal decks
		assertEquals(false, testDeck1.equals(testDeck3));
		// Test with another object type
		assertEquals(false, testDeck1.equals("testString"));
	}
	
	@Test
	public void addFlashCardTest() {
		FlashCard testCard1 = new FlashCard("A", "B");
		FlashCard testCard2 = new FlashCard("A", "B");
		
		Deck testDeck = new Deck("testName", "", null);
		// Test normally
		assertEquals(true, testDeck.addFlashCard(testCard1));
		// Test with a duplicate card already in deck
		assertEquals(false, testDeck.addFlashCard(testCard2));
	}
	
	@Test
	public void containsTest() {
		FlashCard testCard1 = new FlashCard("A", "B");
		FlashCard testCard2 = new FlashCard("C", "D");
		FlashCard testCard3 = new FlashCard("E", "F");
		
		Deck testDeck1 = new Deck("testName", "", null);
		testDeck1.addFlashCard(testCard1);
		testDeck1.addFlashCard(testCard2);
		
		// With a card already in the deck, with contains(Deck)
		assertEquals(true, testDeck1.contains(testCard1));
		// With a card that is not in the deck
		assertEquals(false, testDeck1.contains(testCard3));
	}
	
	@Test
	public void cardsToQuizTest() {
		FlashCard testCard1 = new FlashCard("A", "B");
		testCard1.setNextReviewDate(LocalDate.of(2021, 6, 25));
		FlashCard testCard2 = new FlashCard("C", "D");
		testCard2.setNextReviewDate(LocalDate.of(2021, 6, 26));
		FlashCard testCard3 = new FlashCard("E", "F");
		FlashCard testCard4 = new FlashCard("G", "H");
		FlashCard testCard5 = new FlashCard("I", "J");
		FlashCard testCard6 = new FlashCard("K", "L");
		testCard6.setNextReviewDate(LocalDate.of(2021, 6, 27));
		FlashCard testCard7 = new FlashCard("M", "N");
		FlashCard[] testCardArray1 = {testCard1, testCard2,testCard3,  testCard4, testCard5, testCard6, testCard7};
		
		Deck testDeck1 = new Deck("testName", "", null);
		testDeck1.setCards(new ArrayList<FlashCard>(Arrays.asList(testCardArray1)));
		
		// Test with a deck with more than max new cards
		ArrayList<ArrayList<FlashCard>> cardsToQuiz = testDeck1.flashCardsToQuiz(3, LocalDate.of(2021, 6, 26));
		assertEquals(5, cardsToQuiz.get(0).size());
		assertEquals(3, cardsToQuiz.get(2).size());
		
		// Test with a deck with no new cards
		FlashCard[] testCardArray2 = {testCard1, testCard2, testCard6};
		testDeck1.setCards(new ArrayList<FlashCard>(Arrays.asList(testCardArray2)));
		assertEquals(2, testDeck1.flashCardsToQuiz(5, LocalDate.of(2021, 6, 26)).get(0).size());
	
		// Test with an empty deck
		Deck testDeck2 = new Deck("testName", "", null);
		assertEquals(0, testDeck2.flashCardsToQuiz(5, null).get(0).size());
	}
	
	@Test
	public void setNameTest() {
		
		Deck testDeck1 = new Deck("testName", "", null);
		// Check that it throws an error with an invalid name
		assertThrows(IllegalArgumentException.class, () -> {testDeck1.setName("G  erman");});
		// Check that it works normally with a correct name
		testDeck1.setName("testNameTwo");
		assertEquals("testNameTwo", testDeck1.getName());
	}
}
