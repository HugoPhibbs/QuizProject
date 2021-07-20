package gui.guiLogic;

import java.io.FileNotFoundException;

import gui.guiShell.SetupScreen;
import setup.Setup;

/**
 * Class to manipulate SetupScreen components and to react to actions made with
 * SetupScreen
 * 
 * @author Hugo Phibbs
 * @version 13/7/21
 * @since 9/7/21
 */
public class SetupScreenLogic extends ScreenLogic {

	/**
	 * Setup object to set up this application
	 */
	private Setup setup;
	/**
	 * SetupScreen object that this class is controlling
	 */
	private SetupScreen screen;

	/**
	 * Constuctor for SetupScreenLogic
	 * <p>
	 * Since this screen doesn't have any parent, call to constructor has null for
	 * parentLogic
	 */
	public SetupScreenLogic() {
		super(null, null);
	}

	// ************** Creating and closing the Screen ********************** //

	@Override
	public void createScreen() {
		screen = new SetupScreen(this);
		screen.initialize();
		super.setScreen(screen);
		// screen.show();
	}

	@Override
	public void closeScreen() {
		deleteScreen();
	}

	@Override
	public void showScreen() {
		screen.show();
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
			setup.createNewSession(screen.userName());
			screen.toggleComponent(screen.getBtnContinue(), true);
			screen.toggleComponent(screen.getBtnLoadSession(), false);
		} catch (IllegalArgumentException iae) {
			screen.displayError(iae.getMessage());
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
			screen.displayError(fnfe.getMessage());
		}
	}

	/** Handles pressing of btnContinue */
	public void onContinue() {
		setup.getAppEnvironment().onStartUp();
		MainScreenLogic mainScreenLogic = new MainScreenLogic(setup.getAppEnvironment());
		mainScreenLogic.createScreen();
		mainScreenLogic.showScreen();
		closeScreen();
	}

	/**
	 * Handles a change of value in textFieldEnterName from SetupScreen
	 * 
	 */
	public void nameChanged() {
		screen.displayError("");
		enableConfigSessionButtons();
	}

	// **************** Helper methods ************** //

	/**
	 * Handles enabling JButtons to to create or load a session
	 */
	private void enableConfigSessionButtons() {
		screen.toggleComponent(screen.getBtnCreateSession(), true);
		screen.toggleComponent(screen.getBtnLoadSession(), true);
	}

}
