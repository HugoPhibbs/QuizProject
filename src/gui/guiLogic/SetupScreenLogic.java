package gui.guiLogic;

import java.io.FileNotFoundException;

import gui.guiShell.MainScreen;
import gui.guiShell.SetupScreen;
import setup.Setup;

/**
 * Class to manipulate SetupScreen components and to react to actions made with
 * SetupScreen
 * 
 * @author Hugo Phibbs
 * 
 */
public class SetupScreenLogic extends ScreenLogic {

	/** Setup object to set up this application */
	Setup setup;
	/** SetupScreen object that this class is controlling */
	SetupScreen screen;

	public SetupScreenLogic() {
		super(null);
	}

	/**
	 * Creates an new SetupScreen
	 */
	public void createScreen() {
		screen = new SetupScreen(this);
	}

	// *********************** Handling Listener Events ************************* //

	/**
	 * Handles the entering of a Directory
	 * 
	 * @param workingDirectory String for the directory that this application is
	 *                         using to save session files to
	 */
	public void directoryEntered(String workingDirectory) {
		setup = new Setup(workingDirectory);
		screen.toggleComponent(screen.getTextFieldEnterName(), true);
	}

	/** Handles pressing of btnCreateSession */
	public void createSession() {
		try {
			setup.createSession(screen.userName());
			screen.toggleComponent(screen.getBtnContinue(), true);
			screen.toggleComponent(screen.getBtnLoadSession(), false);
		} catch (IllegalArgumentException iae) {
			screen.displayConfigSessionError(iae.getMessage());
		}
	}

	/**
	 * Handles pressing of btnLoadSession Tries to load a session, displays an error
	 * in lblConfigSessionError if FileNotFoundException is caught
	 */
	public void loadSession() {
		try {
			setup.loadSession(screen.userName());
			screen.toggleComponent(screen.getBtnContinue(), true);
			screen.toggleComponent(screen.getBtnCreateSession(), false);
		} catch (FileNotFoundException fnfe) {
			screen.displayConfigSessionError(fnfe.getMessage());
		}
	}

	/** Handles pressing of btnContinue */
	public void onContinue() {
		MainScreenLogic mainScreenLogic = new MainScreenLogic(setup.getAppEnvironment());
		mainScreenLogic.createScreen();
		screen.dispose(); // TODO implement this into Screen later, when design is finished!
	}

	/**
	 * Handles a change of value in textFieldEnterName from SetupScreen
	 * 
	 */
	public void nameChanged() {
		screen.displayConfigSessionError("");
		enableConfigSessionButtons();
	}

	/**
	 * Handles enabling JButtons to to create or load a session
	 */
	private void enableConfigSessionButtons() {
		screen.toggleComponent(screen.getBtnCreateSession(), true);
		screen.toggleComponent(screen.getBtnLoadSession(), true);
	}

}
