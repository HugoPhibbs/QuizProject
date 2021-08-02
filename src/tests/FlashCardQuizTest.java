package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.coreObjects.Deck;
import core.coreObjects.FlashCard;
import core.quizLogic.FlashCardQuiz;
import core.quizLogic.QuizFinishedException;

class FlashCardQuizTest {

	Deck testDeck1;
	FlashCardQuiz testFlashCardQuiz;
	FlashCard testCard1;
	FlashCard testCard2;
	FlashCard testCard3;
	FlashCard testCard4;

	@BeforeEach
	void setUp() throws Exception {
		// Create a test deck
		Deck testDeck1 = new Deck("testNameOne", "");

		// Create testFlashCards
		testCard1 = new FlashCard("A", "B");
		testCard2 = new FlashCard("C", "D");
		testCard3 = new FlashCard("E", "F");
		testCard4 = new FlashCard("G", "H");
		testCard1.setNextReviewDate(LocalDate.now()); // Will be due
		testCard2.setNextReviewDate(LocalDate.now().plusDays(10)); // Won't be due
		ArrayList<FlashCard> testCardsToQuiz = new ArrayList<FlashCard>(
				Arrays.asList(testCard1, testCard2, testCard3, testCard4));
		testDeck1.setFlashCards(testCardsToQuiz);

		// Create testFlashCardQuiz
		testFlashCardQuiz = new FlashCardQuiz(testDeck1, null);

		// Set FlashCards to quiz directly to avoid randomness with
		// Deck.cardsToQuiz(int)
		testFlashCardQuiz.setCardsToQuiz(testCardsToQuiz);

		// Set the initialQueue to avoid using startQuiz(int)
		testFlashCardQuiz.setInitialQueue(testCardsToQuiz);
	}

	@Test
	void startQuizTest() {

	}

	@Test
	void endQuizTest() {

	}

	@Test
	void summaryTest() {

	}

	@Test
	void nextFlashCardTest() {
	}

	@Test
	void quizIsFinishedTest() {

	}

	@Test
	void updateCurrentFlashCardTest() {

		// Test with a non-empty initial queue
		testFlashCardQuiz.updateCurrentFlashCard();
		assertEquals(testCard1, testFlashCardQuiz.getCurrentFlashCard());

		// Test with an empty initial queue and a partially filled again and final queue
		testFlashCardQuiz.setFinalQueue(new ArrayList<FlashCard>(Arrays.asList(testCard3, testCard4)));
		testFlashCardQuiz.setInitialQueue(new ArrayList<FlashCard>()); // empty
		testFlashCardQuiz.setAgainQueue(new ArrayList<FlashCard>(Arrays.asList(testCard4)));
		testFlashCardQuiz.updateCurrentFlashCard();
		assertEquals(testCard3, testFlashCardQuiz.getCurrentFlashCard());

		// Test with an empty final and initialQueue, and a non-empty againQueue
		testFlashCardQuiz.setFinalQueue(new ArrayList<FlashCard>());
		testFlashCardQuiz.updateCurrentFlashCard();
		assertEquals(testCard4, testFlashCardQuiz.getCurrentFlashCard());

		// Test with all empty queues
		testFlashCardQuiz.setAgainQueue(new ArrayList<FlashCard>());
		testFlashCardQuiz.updateCurrentFlashCard();
		assertEquals(null, testFlashCardQuiz.getCurrentFlashCard());
	}

	@Test
	void flipCurrentFlashCardTest() {
		testFlashCardQuiz.setCurrentFlashCard(testCard1);

		// Test with current side being FRONT
		assertEquals(testCard1.getBackText(), testFlashCardQuiz.flipCurrentFlashCard());

		// Test with current side being BACK
		assertEquals(testCard1.getFrontText(), testFlashCardQuiz.flipCurrentFlashCard());
	}

	@Test
	void flashCardOkTest() {
		testFlashCardQuiz.setCurrentFlashCard(testCard1);
		testFlashCardQuiz.setFinalQueue(new ArrayList<FlashCard>(Arrays.asList(testCard1, testCard3)));

		// Test with the currentQueue being finalQueue
		testFlashCardQuiz.setCurrentQueue("FINAL");
		testFlashCardQuiz.setCurrentFlashCard(testCard4);
		Queue<FlashCard> finalQueueExpected = new LinkedList<FlashCard>(testFlashCardQuiz.getFinalQueue());
		testFlashCardQuiz.flashCardOk();
		assertEquals(finalQueueExpected, testFlashCardQuiz.getFinalQueue());

		// Test with currentQueue being initialQueue
		testFlashCardQuiz.setCurrentQueue("INITIAL");
		testFlashCardQuiz.setCurrentFlashCard(testCard4);
		testFlashCardQuiz.flashCardOk(); // testCard4 is new
		finalQueueExpected.add(testCard4);
		assertEquals(finalQueueExpected, testFlashCardQuiz.getFinalQueue());

		// Test with currentQueue being againQueue and a card that is due
		testFlashCardQuiz.setAgainQueue(new ArrayList<FlashCard>(Arrays.asList(testCard1, testCard3)));
		testFlashCardQuiz.setCurrentQueue("AGAIN");
		testFlashCardQuiz.setCurrentFlashCard(testCard1);
		testFlashCardQuiz.flashCardOk();
		finalQueueExpected.add(testCard1);
		assertEquals(finalQueueExpected, testFlashCardQuiz.getFinalQueue()); // testCard1 is due

		// Test with a card that is not new, and the current queue isn't againQueue
		testFlashCardQuiz.setCurrentFlashCard(testCard1);
		testFlashCardQuiz.setCurrentQueue("INITIAL");
		testFlashCardQuiz.flashCardOk();
		assertEquals(finalQueueExpected, testFlashCardQuiz.getFinalQueue());
		// TODO add tests for when stats is implemented
	}

	@Test
	void setCurrentQueueTest() {
		// Test normally
		Queue<FlashCard> finalQueue = testFlashCardQuiz.getFinalQueue();
		testFlashCardQuiz.setCurrentQueue("FINAL");
		assertEquals(finalQueue, testFlashCardQuiz.getCurrentQueue());
		Queue<FlashCard> againQueue = testFlashCardQuiz.getAgainQueue();
		testFlashCardQuiz.setCurrentQueue("AGAIN");
		assertEquals(againQueue, testFlashCardQuiz.getCurrentQueue());
		Queue<FlashCard> initialQueue = testFlashCardQuiz.getInitialQueue();
		testFlashCardQuiz.setCurrentQueue("INITIAL");
		assertEquals(initialQueue, testFlashCardQuiz.getCurrentQueue());

		// Test with invalid input
		assertThrows(IllegalArgumentException.class, () -> {
			testFlashCardQuiz.setCurrentQueue("again");
		});
	}

	@Test
	void changeCurrentFlashCardSideTest() {
		testFlashCardQuiz.updateCurrentFlashCard(); // So it isn't null
		// Test with the current side being FRONT
		testFlashCardQuiz.changeCurrentFlashCardSide();
		assertEquals("BACK", testFlashCardQuiz.getCurrentFlashCardSide());
		// Test with the current side being BACK
		testFlashCardQuiz.changeCurrentFlashCardSide();
		assertEquals("FRONT", testFlashCardQuiz.getCurrentFlashCardSide());
		// Test with an invalid side
		testFlashCardQuiz.setCurrentFlashCardSide("back");
		assertThrows(IllegalStateException.class, () -> {
			testFlashCardQuiz.changeCurrentFlashCardSide();
		});
	}

	@Test
	void currentFlashCardSideText() throws QuizFinishedException {
		testFlashCardQuiz.updateCurrentFlashCard();
		// Test with the current side being FRONT
		assertEquals("A", testFlashCardQuiz.currentFlashCardSideText());
		// Test with the current side being BACK
		testFlashCardQuiz.changeCurrentFlashCardSide();
		assertEquals("B", testFlashCardQuiz.currentFlashCardSideText());
		// Check when currentFlashCard is updated
		testFlashCardQuiz.nextFlashCard();
		assertEquals("C", testFlashCardQuiz.currentFlashCardSideText());
		// Test with an illegal currentFlashCardSide
		testFlashCardQuiz.setCurrentFlashCardSide("front");
		assertThrows(IllegalStateException.class, () -> {
			testFlashCardQuiz.currentFlashCardSideText();
		});
	}

	@Test
	void canStartQuizTest() {
		// Test with a non empty cardsToQuiz
		assertTrue(testFlashCardQuiz.canStartQuiz());
		// Test with empty cardsToQuiz
		testFlashCardQuiz.setCardsToQuiz(new ArrayList<FlashCard>());
		assertFalse(testFlashCardQuiz.canStartQuiz());
	}

}
