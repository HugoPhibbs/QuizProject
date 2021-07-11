package gui.guiShell;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import setup.Setup;

/**
 * Represents a Screen object for GUI
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 25/6/21
 * @since 18/5/21
 * 
 */
public abstract class Screen {

	/** Frame object for this screen **/
	JFrame frame;

	/** Screen object that is the parent Screen for this Screen */
	Screen parent;

	/**
	 * Empty Constructor for Screen, used until extensions of Screen need full
	 * constructor
	 * 
	 */
	Screen() {
		// TODO Delete later!
	}

	protected Screen(String title) {
		this.frame = new JFrame();
		frame.setTitle(title);
		frame.getContentPane().setBackground(new Color(255, 222, 173));
		setFrameCharacteristics();
	}

	/** Sets the characteristics of the frame that are common to all screens. */
	private void setFrameCharacteristics() {
		// Prevent the user from quiting immediately when quit is clicked.
		// Code copied from Rocket Manager Example
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmQuit();
			}
		});

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}

	/**
	 * This is where the bounds of your frame will be set and methods which create
	 * the components will be called. Is called at the end of the constructor
	 * method.
	 */
	protected abstract void initialize();

	/**
	 * Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
	}

	/**
	 * Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Hides the screen from the user
	 * 
	 */
	public void hide() {
		frame.setVisible(false);
	}

	/**
	 * Displays a Dialog to allow the user to confirm whether they would like to
	 * quit
	 * 
	 * @return Boolean true if would like to quit, else false
	 */
	protected void confirmQuit() {
		int selection = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?", "Quit?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (selection == JOptionPane.YES_OPTION) {
			quit();
		}
	}

	/**
	 * Handles showing of a new screen, and hides this screen
	 * 
	 * @param newScreen Screen object to be switched to
	 */
	public void swtichToNewScreen(Screen newScreen) {
		newScreen.show();
		// this.hide();
	}

	/**
	 * Handles switching back to a parent screen
	 * 
	 */
	public void switchToParent() {
		if (parent == null) {
			throw new IllegalStateException("This screen doesn't have a parent screen to switch to!");
		} else {
			parent.show();
			this.hide();
		}
	}

	public abstract void displayError(String msg);
	// TODO make displayError concrete, ie have an attribute in all Screen implemenations called textPaneError?

	/**
	 * Disables or enables a JComponent according to parameter setting
	 * 
	 * @param component JComponent to be enabled or not
	 * @param setting   Boolean value for if a JButton is to be enabled or not
	 */
	public void toggleComponent(JComponent component, boolean setting) {
		component.setEnabled(setting);
	}

}
