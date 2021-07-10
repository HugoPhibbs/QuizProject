package setup;

import gui.guiShell.SetupScreen;

/** Class to start the application
 * <p>
 * Creates a new SetupScreen and and Setup object to setup the application
 * 
 * @author Hugo Phibbs
 * @version 7/7/21
 * @since 30/6/21
 *
 */
public class Main {
	
	
	/** Method to start the application
	 * <p>
	 * Creates a new setupScreen and then shows it to a user
	 * @param args String[] arguments, none needed to start the application
	 */
	public static void main(String[] args) {
		SetupScreen setupScreen = new SetupScreen();
		// setupScreen.show(); // TODO Remove later when setupScreen implements Screen
	}

}
