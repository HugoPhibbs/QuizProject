package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import coreObjects.FlashCard;

class FlashCardTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}
	
	@Test
	public void isDueTest() {
		FlashCard testCard1 = new FlashCard("", "");
		FlashCard testCard2 = new FlashCard("", "");
		FlashCard testCard3 = new FlashCard("", "");
		
		LocalDate testDueDate1 = LocalDate.of(2021, 6, 25);
		testCard1.setNextReviewDate(testDueDate1);
		
		LocalDate testDueDate2 = LocalDate.of(2021, 6, 26);
		testCard2.setNextReviewDate(testDueDate2);
		
		LocalDate testDueDate3 = LocalDate.of(2021, 6, 24);
		testCard3.setNextReviewDate(testDueDate3);
		
		LocalDate testCurrentDate = LocalDate.of(2021, 6, 25);
		
		assertEquals(true, testCard1.isDue(testCurrentDate));
		assertEquals(false, testCard2.isDue(testCurrentDate));
		assertEquals(true, testCard3.isDue(testCurrentDate));
	}
}
 	