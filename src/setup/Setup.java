package setup;

import java.io.File; 
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import coreLogic.AppEnvironment;
import coreObjects.User;
import gui.MainScreen;

/** Class to start the Quiz Application
 * <p>
 * Coordinates the starting of the application, checks if there is an existing
 * Serialized file for info from a previous session. Other wise creates a new
 * application
 * 
 * @author Hugo Phibbs
 * @version 30/6/21
 * @since 25/6/21
 *
 */
public class Setup {
	
	/** String for the absolute working directory for this application, where session files are stored for each user */
	private String workingDirectory;
	/** AppEnvironment object for this application */
	private AppEnvironment appEnvironment;
	
	/** Constructor for Setup class
	 * <p>
	 * Sets the working directory for this application, i.e. where to load and write files for serialization.
	 * 
	 * @param sessionsDirectory
	 */
	public Setup(String workingDirectory){
		this.workingDirectory = workingDirectory;
	}	

	// ************************ Loading a Session ***************************** // 
	
	/** Loads a session saved for a User with name userName
	 * <p>
	 * Rethrows FileNotFoundException, this can then be handled by the GUI
	 * 
	 * @param userName String for a User's name, that a user wants to load a session for
	 * @return boolean if a session was successfully loaded
	 */
	public boolean loadSession(String userName) throws FileNotFoundException {
		String sessionFilePath = sessionFilePath(userName);
		try {
			return loadSessionHelper(sessionFilePath);
		}
		catch (FileNotFoundException fnfe) {
			throw new FileNotFoundException(String.format("Session for %s doesn't exist!", userName));
		} 
		catch (IOException ioe) {
	        ioe.printStackTrace();
	        return false;
		} 
		catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
	        return false;
	    }
	}
	
	/** Helper Method for loadSession(String)
	 * <p>
	 * Does the actual loading of a file part 
	 *  
	 * @param sessionFilePath String for the file path of a session
	 * @return boolean value if a session was succesfully loaded
	 * @throws FileNotFoundException If a session wasn't found
	 * @throws IOException If an I/O exception occurs
	 * @throws ClassNotFoundException If a class isn't found
	 */
	private boolean loadSessionHelper(String sessionFilePath) 
		throws FileNotFoundException, IOException, ClassNotFoundException {

		FileInputStream fileIn = new FileInputStream(sessionFilePath);
		ObjectInputStream objIn = new ObjectInputStream(fileIn);
		appEnvironment = (AppEnvironment) objIn.readObject();
		objIn.close();
		fileIn.close();
		
		return true;
	}

	// *********************** Deleting a Session **************************** // 
	
	/** Deletes a session saved for a User with name userName
	 * 
	 * @param userName String for a User's name, that a user wants to delete a session for
	 * @return boolean if the session was successfully deleted
	 */
	public boolean deleteSession(String userName) {
		String sessionFilePath = sessionFilePath(userName);
		File file = new File(sessionFilePath);
		return file.delete();
	}

	// *********************** Creating a Session *************************** // 
	
	/** Creates a session for a user
	 * 
	 * @param userName String for the name of a User object that a user wants to create a session for
	 */
	public void createSession(String userName) {
		String sessionFilePath = sessionFilePath(userName);
		if (!sessionExists(sessionFilePath)) {
			try {
				createSessionHelper(sessionFilePath, userName);
			}
			catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		else {
			throw new IllegalArgumentException("Session already exists under this name!");
		}
	}
	
	/** Helper method for createSession(String)
	 * <p>
	 * Does the actual writing to a new file part
	 *
	 * @param sessionFilePath String for the file path of a session to be created
	 * @param userName String for the name of a user to create a Session for
	 */
	private void createSessionHelper(String sessionFilePath, String userName) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(sessionFilePath);
		ObjectOutputStream objOut= new ObjectOutputStream(fileOut);
		
		appEnvironment = new AppEnvironment(new User(userName));
		
		objOut.writeObject(appEnvironment);
		objOut.close();
		fileOut.close();
		
	}

	// ************************* Other General Methods ************************* //
	
	/** Returns the file directory that a serialized session
	 *  would be saved under using userName
	 * 
	 * @param userName String for a user's name
	 * @return String for the name of a file 
	 */
	public String sessionFilePath(String userName) {
		return String.format("%s\\%sQuizSession.ser", workingDirectory, userName);
	}
	
	/** Finds out if a session can be loaded that was saved for a userName as per file defined by
	 * sessionFilePath(String)
	 * <p>
	 * 
	 * @param userName String for the name a User's session to see if it exists
	 */
	public static boolean sessionExists(String sessionFilePath) {
		File newFile = new File(sessionFilePath);
		return newFile.exists();
	}
	
	/** Handles the finishing of the setup of the application
	 * <p>
	 * Creates a new MainScreen
	 * 	 
	 * @param appEnvironment AppEnvironment object for the application
	 */
	public void onSetupFinished() {
		MainScreen mainScreen = new MainScreen(appEnvironment);
		//mainScreen.show(); // TODO remove later once Screen fully implemented
	}
	
	/** Getter method for the AppEnvironment object for this Setup class
	 * 
	 * @return AppEnvironment object for this Setup class
	 */
	public AppEnvironment getAppEnvironment(){
		return appEnvironment;
	}
}
