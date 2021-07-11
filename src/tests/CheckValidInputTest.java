package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

import core.coreLogic.CheckValidInput;

/**
 * JUnit testing class for CheckValidInputClass
 * 
 * @author Hugo Phibbs
 * @version 7/7/2021
 * @since 15/4/2021
 */

class CheckValidInputTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void nameIsValidTest() {
		assertEquals(true, CheckValidInput.nameIsValid("German"));
		assertEquals(true, CheckValidInput.nameIsValid("Math Questions"));
		assertEquals(false, CheckValidInput.nameIsValid("")); // too few characters
		assertEquals(false, CheckValidInput.nameIsValid("mathquestionsssssssss")); // too many characters
		assertEquals(false, CheckValidInput.nameIsValid("the%")); // special character
		assertEquals(false, CheckValidInput.nameIsValid("23jamie")); // numbers present
	}
}