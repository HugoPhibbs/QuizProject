package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import setup.Setup;

/**
 * JUnit testing class for Setup
 * 
 * @author Hugo Phibbs
 * 
 */
class SetupTest {

	Setup testSetup;

	@BeforeEach
	void setUp() throws Exception {
		String testWorkingDirectory = "C:\\SetupTests";
		testSetup = new Setup(testWorkingDirectory);

		clearDirectory(testWorkingDirectory);
	}

	/**
	 * Clears a working directory to make sure testing directory is clear before
	 * every test
	 * 
	 * @param directory String for the directory folder that will be cleared
	 */
	void clearDirectory(String directory) {
		File fileDirectory = new File(directory);
		for (File subFile : fileDirectory.listFiles()) {
			subFile.delete();
		}
	}

	@Test
	void sessionFilePathTest() {
		String testName = "Hugo";
		assertEquals("C:\\SetupTests\\HugoQuizSession.ser", testSetup.sessionFilePath(testName));
	}

	@Test
	void createSessionTest() {
		// Create a session with a name that doesn't already exist
		String testName = "Tom";
		testSetup.createSession(testName);
		assertEquals(testSetup.getAppEnvironment().getUser().getName(), testName);
		assertTrue(Setup.sessionExists(testSetup.sessionFilePath(testName)));

		// Create a session with a name that already exists
		assertThrows(IllegalArgumentException.class, () -> {
			testSetup.createSession(testName);
		});
	}

	@Test
	void deleteSessionTest() {
		// Test with a session that doesn't exist
		String testName = "Morty";
		String testSessionFilePath = testSetup.sessionFilePath(testName);
		assertFalse(Setup.sessionExists(testSessionFilePath));
		assertFalse(testSetup.deleteSession(testName));

		// Test with a session that does exist
		testSetup.createSession(testName);
		assertTrue(testSetup.deleteSession(testName));
		testSessionFilePath = testSetup.sessionFilePath(testName);
		assertFalse(Setup.sessionExists(testSessionFilePath));
	}

	@Test
	void loadSessionTest() throws FileNotFoundException {
		// Test with a session that doesn't exist
		String testName = "Louis";
		assertThrows(FileNotFoundException.class, () -> {
			testSetup.loadSession(testName);
		});

		// Test with a session that does exist
		testSetup.createSession(testName);
		assertTrue(testSetup.loadSession(testName));
		assertEquals(testName, testSetup.getAppEnvironment().getUser().getName());
	}

	@Test
	void sessionExistsTest() {
		// Test with a session that doesn't exist
		String testName1 = "John";
		String testSessionFilePath1 = testSetup.sessionFilePath(testName1);
		assertFalse(Setup.sessionExists(testSessionFilePath1));

		// Test with a session that already exists
		String testName2 = "Rudy";
		testSetup.createSession(testName2);
		assertTrue(Setup.sessionExists(testSetup.sessionFilePath(testName2)));
	}
}
