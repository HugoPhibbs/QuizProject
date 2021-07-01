package tests;

import static org.junit.jupiter.api.Assertions.*; 

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import coreObjects.Deck;
import coreObjects.FlashCard;

/** JUnit testing class for Deck
 * 
 * @author Hugo Phibbs
 *
 */
class DeckTest {
	
	Deck testDeck1;
	Deck testDeck2;
	Deck testDeck3;
	FlashCard testCard1;
	FlashCard testCard2;
	FlashCard testCard3;
	FlashCard testCard4;
	FlashCard testCard5;
	FlashCard testCard6;
	FlashCard testCard7;
	FlashCard testCard8;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		testDeck1 = new Deck("testNameOne", "");
		testDeck2 = new Deck("testNameOne", "");
		testDeck3 = new Deck("testNameTwo", "");
		testCard1 = new FlashCard("A", "B");
		testCard2 = new FlashCard("C", "D");
		testCard3 = new FlashCard("E", "F");
		testCard4 = new FlashCard("G", "H");
		testCard5 = new FlashCard("I", "J");
		testCard6 = new FlashCard("K", "L");
		testCard7 = new FlashCard("M", "N");
		testCard8 = new FlashCard("A", "B");
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equalsTest() {
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
		// Test normally
		assertEquals(true, testDeck1.addFlashCard(testCard1));
		// Test with a duplicate card already in deck
		assertEquals(false, testDeck1.addFlashCard(testCard8));
	}
	
	@Test
	public void containsTest() {
		testDeck1.addFlashCard(testCard1);
		testDeck1.addFlashCard(testCard2);
		
		// With a card already in the deck, with contains(Deck)
		assertEquals(true, testDeck1.contains(testCard1));
		// With a card that is not in the deck
		assertEquals(false, testDeck1.contains(testCard3));
	}
	
	@Test
	public void flashCardsToQuizTest() {
		testCard1.setNextReviewDate(LocalDate.of(2021, 6, 25));
		testCard2.setNextReviewDate(LocalDate.of(2021, 6, 26));
		
		// Test with a deck with more than max new cards
		testCard6.setNextReviewDate(LocalDate.of(2021, 6, 27));
		FlashCard[] testCardArray1 = {testCard1, testCard2, testCard3,  testCard4, testCard5, testCard6, testCard7};
		testDeck1.setFlashCards(new ArrayList<FlashCard>(Arrays.asList(testCardArray1)));
		ArrayList<FlashCard> cardsToQuiz = testDeck1.flashCardsToQuiz(3, LocalDate.of(2021, 6, 26));
		assertEquals(5, cardsToQuiz.size());
		
		// Test with a deck with no new cards
		FlashCard[] testCardArray2 = {testCard1, testCard2, testCard6};
		testDeck1.setFlashCards(new ArrayList<FlashCard>(Arrays.asList(testCardArray2)));
		assertEquals(2, testDeck1.flashCardsToQuiz(5, LocalDate.of(2021, 6, 26)).size());
	
		// Test with an empty deck
		assertEquals(0, testDeck2.flashCardsToQuiz(5, null).size());
	}
	
	@Test
	public void setNameTest() {
		// Check that it throws an error with an invalid name
		assertThrows(IllegalArgumentException.class, () -> {testDeck1.setName("G  erman");});
		// Check that it works normally with a correct name
		testDeck1.setName("testNameTwo");
		assertEquals("testNameTwo", testDeck1.getName());
	}
}
