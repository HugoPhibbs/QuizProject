package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.coreObjects.FlashCard;

/**
 * JUnit testing class for FlashCard
 * 
 * @author Hugo Phibbs
 *
 */
class FlashCardTest {

	FlashCard testCard1;
	FlashCard testCard2;
	FlashCard testCard3;
	FlashCard testCard4;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		testCard1 = new FlashCard("A", "B");
		testCard2 = new FlashCard("A", "B");
		testCard3 = new FlashCard("A", "A");
		testCard4 = new FlashCard("B", "A");
	}

	@Test
	public void isDueTest() {
		LocalDate testDueDate1 = LocalDate.of(2021, 6, 25);
		testCard1.setNextReviewDate(testDueDate1);

		LocalDate testDueDate2 = LocalDate.of(2021, 6, 26);
		testCard2.setNextReviewDate(testDueDate2);

		LocalDate testDueDate3 = LocalDate.of(2021, 6, 24);
		testCard3.setNextReviewDate(testDueDate3);

		LocalDate testCurrentDate = LocalDate.of(2021, 6, 25);

		// Due date is equal to current date
		assertEquals(true, testCard1.isDue(testCurrentDate));
		// Due date is after current date
		assertEquals(false, testCard2.isDue(testCurrentDate));
		// Due date is before to current date
		assertEquals(true, testCard3.isDue(testCurrentDate));
		// Flash card is new, doesn't have a due date
		assertEquals(false, testCard4.isDue(testCurrentDate));
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void equalsTest() {

		String testString = new String("");

		assertEquals(true, testCard1.equals(testCard2));
		assertEquals(false, testCard2.equals(testCard3));
		assertEquals(false, testCard2.equals(testCard4));
		// Test with a different object type
		assertEquals(false, testCard3.equals(testString));
	}

	@Test
	public void updateNextReviewDateTest() {
		// Test with a card that hasn't been tested before
		LocalDate currentDate = LocalDate.now();
		LocalDate expectedNextReviewDate1 = currentDate.plusDays(1);
		testCard1.updateNextReviewDate();
		assertEquals(expectedNextReviewDate1, testCard1.getNextReviewDate());
		assertEquals(1, testCard1.getTimesReviewed()); // Check timesReviwed is updated

		// Test with a card that has been seen before
		testCard2.setNextReviewDate(currentDate);
		testCard2.setTimesReviewed(2);
		testCard2.updateNextReviewDate();
		LocalDate expectedNextReviewDate2 = currentDate.plusDays(5);
		assertEquals(expectedNextReviewDate2, testCard2.getNextReviewDate());
	}
}
