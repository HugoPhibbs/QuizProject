package coreLogic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coreObjects.User;
import gui.MainScreen;

/** Class to start the Quiz Application
 * contains the main Method to start the application
 * <p>
 * Coordinates the starting of the application, checks if there is an existing
 * Serialized file for info from a previous session. Other wise creates a new
 * application
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 29/6/21
 * @since 25/6/21
 *
 */
public class Setup {
	
	/** Method to start the application
	 */
	public static void main(String[] args) {
		// TODO implement
		// Creates a Setup Screen to handle starting of application
	}
	
	/** Loads a session saved for a User with name userName
	 * <p>
	 * 
	 * @param userName String for a User's name, that a user wants to load a session for
	 * @throws IllegalArgumentException if the session doesn't exist
	 */
	public static void loadPreviousSession(String userName) throws IllegalArgumentException, IOException {
		// TODO implement
		String sessionFileName = sessionFileName(userName);
		try {
			FileInputStream fileIn = new FileInputStream(sessionFileName);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			AppEnvironment appEnvironment = (AppEnvironment) objIn.readObject();
			objIn.close();
			fileIn.close();
			onSetupFinished(appEnvironment);
		}
		catch (FileNotFoundException fnfe ) {
			fnfe.printStackTrace();
		} 
		catch (IOException i) {
	         i.printStackTrace();
	         return;
		} 
		catch (ClassNotFoundException c) {
	         System.out.println("AppEnvironment class not found!");
	         c.printStackTrace();
	         return;
	    }
	}
	
	/** Deletes a session saved for a User with name userName
	 * 
	 * @param userName String for a User's name, that a user wants to delete a session for
	 */
	public static void deleteSession(String userName) {
		String sessionFileName = sessionFileName(userName);
		try {
			FileInputStream fileIn = new FileInputStream(sessionFileName);
		}
		catch (FileNotFoundException fnfe ) {
			fnfe.printStackTrace();
		}
	}
	
	/** Creates a session for a user
	 * 
	 * @param userName String for the name of a User object that a user wants to create a session for
	 */
	public static void createSession(String userName) {
		// TODO implement
		String sessionFileName = sessionFileName(userName);
		if (!canLoadSession(sessionFileName)) {
			try {
				FileOutputStream fileOut = new FileOutputStream(sessionFileName);
				ObjectOutputStream objOut= new ObjectOutputStream(fileOut);
				
				// Create a new AppEnvironment instance
				AppEnvironment appEnvironment = new AppEnvironment();
				appEnvironment.setUser(new User("userName"));
				
				// Write AppEnvironment to file
				objOut.writeObject(appEnvironment);
				objOut.close();
				fileOut.close();
				
				onSetupFinished(appEnvironment);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else {
			throw new IllegalArgumentException("Session already exists under this name!");
		}
	}
	
	/** Returns the file that a serialized session
	 *  would be saved under using userName
	 * 
	 * @param userName String for a user's name
	 * @return String for the name of a file 
	 */
	private static String sessionFileName(String userName) {
		return String.format("%sQuizSession.txt", userName);
	}
	
	/** Finds out if a session can be loaded that was saved for a userName as per file defined by
	 * sessionFileName(String)
	 * <p>
	 * Attempts to load a file, if a FileNotFoundException is caught, the returns false
	 * 
	 * @param userName String for the name a User's session to see if it exists
	 */
	@SuppressWarnings("resource")
	private static boolean canLoadSession(String sessionName) {
		 try {
			new FileInputStream("/tmp/employee.ser");
			return true; // If reaches here, exception wasn't thrown
		} catch (FileNotFoundException fnfe) {
			return false;
		}
	}
	
	/** Handles the finishing of the setup of the application
	 * 
	 * @param appEnvironment AppEnvironment object for the application
	 */
	private static void onSetupFinished(AppEnvironment appEnvironment) {
		// Creates and shows a new MainScreen with inputted appEnvironment
		MainScreen mainScreen = new MainScreen(appEnvironment);
		mainScreen.show();
	}
}
