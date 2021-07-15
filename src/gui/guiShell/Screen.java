package gui.guiShell;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import gui.guiLogic.ScreenLogic;

/**
 * Represents a Screen object for GUI.
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 15/7/21
 * @since 18/5/21
 * 
 */
public abstract class Screen {

	/**
	 * Frame object for this screen
	 **/
	JFrame frame;
	/**
	 * Screen object that is the parent Screen for this Screen
	 */
	Screen parent;
	/**
	 * ScreenLogic Class for this class
	 */
	ScreenLogic logic;
	/**
	 * Boolean value if a changes that a user has made on a Screen will be saved or
	 * not, if they quit the Screen
	 * <p>
	 * This decides the JOptionPane text, telling a user that their changes will be
	 * saved or not, if they were to quit the current Screen
	 */
	boolean willSave;

	/**
	 * Constructor for a Screen
	 * 
	 * @param title    String for the title of a Screen
	 * @param logic    ScreenLogic object that controls this Screen
	 * @param willSave boolean value for if the changes that a user makes on this
	 *                 Screen will be saved, if they were to suddenly quit the
	 *                 Screen
	 */
	protected Screen(String title, ScreenLogic logic, boolean willSave) {
		this.logic = logic;
		this.willSave = willSave;
		createFrame(title);
	}

	/**
	 * This is where the bounds of your frame will be set and methods which create
	 * the components will be called. Is called at the end of the constructor
	 * method.
	 */
	public abstract void initialize();

	// **************** Methods for the Frame ****************** //

	/**
	 * Method to set any special characteristics of the Frame e.g. size
	 */
	protected abstract void configFrame();

	/**
	 * Handles creating a frame for this Screen
	 */
	private void createFrame(String title) {
		this.frame = new JFrame();
		frame.setTitle(title);
		setFrameCharacteristics();
	}

	/** Sets the characteristics of the frame that are common to all screens. */
	private void setFrameCharacteristics() {
		// Prevent the user from quiting immediately when quit is clicked.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmQuit();
			}
		});

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
	}

	/**
	 * Displays a Dialog to allow the user to confirm whether they would like to
	 * quit
	 * 
	 * @return Boolean true if would like to quit, else false
	 */
	protected void confirmQuit() {
		int selection = JOptionPane.showConfirmDialog(frame, quitMessage(), "Quit?", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (selection == JOptionPane.YES_OPTION) {
			logic.closeScreen();
		}
	}

	/**
	 * Finds the quit message that should be displayed to a user, depending on
	 * whether any changes that they have made will be saved or not
	 * 
	 * @return String quit message as described
	 */
	private String quitMessage() {
		String msg = "Are you sure you want to quit? \n Your changes will %s saved";
		if (willSave) {
			return String.format(msg, "be");
		} else {
			return String.format(msg, "not");
		}
	}

	// ************* General helper methods ******************** //

	/**
	 * Calls methods to create components for this Screen
	 */
	protected abstract void createComponents();

	public abstract void displayError(String msg);
	// TODO make displayError concrete, ie have an attribute in all Screen
	// implemenations called textPaneError?

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

	/**
	 * Disables or enables a JComponent according to parameter setting
	 * 
	 * @param component JComponent to be enabled or not
	 * @param setting   Boolean value for if a JButton is to be enabled or not
	 */
	public void toggleComponent(JComponent component, boolean setting) {
		component.setEnabled(setting);
	}

	/**
	 * Clears an Implementation of Container, this is so the Container implemenation
	 * can be used again with different components. Useful for objects such as
	 * JFrames and JPanels
	 * 
	 * @param container Container to be cleared
	 */
	public void clearContainer(Container container) {
		container.removeAll();
		container.revalidate();
		container.repaint();
	}

	/**
	 * Removes a top level component from the frame of this screen
	 * <p>
	 * For example a Panel or a title label
	 * 
	 * @param component JComponent to be removed
	 */
	public void removeComponent(JComponent component) {
		frame.remove(component);
	}

	/**
	 * Disables editting of entries for a JTable
	 * 
	 * @param table JTable to have editting disabled
	 */
	public void disableTableEditting(JTable table) {
		table.setDefaultEditor(Object.class, null);
	}

	// ***************** Getter Methods ************************** //

	/**
	 * Getter method for the JFrame object for this Screen
	 * 
	 * @return JFrame object
	 */
	public JFrame getFrame() {
		return frame;
	}

}
