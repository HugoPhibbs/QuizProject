package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import core.coreLogic.DeckManager;
import gui.guiLogic.CreateDeckScreenLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Represents a Screen to create a new Deck
 * 
 * @author Hugo Phibbs
 *
 */
public class CreateDeckScreen {

	private JFrame frame;
	/** TextField for entering the name of a new Deck */
	private JTextField textFieldName;
	/** TextField for entering the description of a new Deck */
	private JTextField textFieldDescription;
	/** TextPane to display an error to a */
	private JTextPane textPaneErrorMsg;
	/** CrateDeckScreenLogic object to manipulate this Screen */
	private CreateDeckScreenLogic logic;
	/** JButton to press to create a new Deck */
	JButton btnCreateDeck;

	/**
	 * Create the application.
	 */
	public CreateDeckScreen(CreateDeckScreenLogic createDeckScreenLogic) {
		this.logic = createDeckScreenLogic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createFrame();
		createDetailsPanel();
		createContinuePanel();
		createMiscComponents();
	}

	private void createFrame() {
		// TODO remove later
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

	}

	// @Override
	protected void configFrame() {
		frame.setBounds(100, 100, 450, 300);
	}
	// ******************** Creating Components ************************ //

	/**
	 * Creates components contains in panelDeckDetails
	 * 
	 */
	private void createDetailsPanel() {
		JPanel panelDeckDetails = new JPanel();
		panelDeckDetails.setBounds(12, 53, 213, 157);
		frame.getContentPane().add(panelDeckDetails);
		panelDeckDetails.setLayout(null);

		textFieldName = new JTextField();
		textFieldName.setBounds(102, 21, 85, 22);
		panelDeckDetails.add(textFieldName);
		textFieldName.setColumns(10);

		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(5, 74, 196, 70);
		panelDeckDetails.add(textFieldDescription);
		textFieldDescription.setColumns(10);

		JLabel lblName = new JLabel("Deck name:");
		lblName.setBounds(5, 24, 85, 16);
		panelDeckDetails.add(lblName);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(5, 56, 92, 16);
		panelDeckDetails.add(lblDescription);
		addTextFieldNameListener();
	}

	/**
	 * Creates components contains in panelContinue
	 * 
	 */
	private void createContinuePanel() {
		JPanel panelContinue = new JPanel();
		panelContinue.setBounds(237, 53, 185, 157);
		frame.getContentPane().add(panelContinue);
		panelContinue.setLayout(null);

		btnCreateDeck = new JButton("Create Deck");
		btnCreateDeck.setBounds(30, 119, 128, 25);
		panelContinue.add(btnCreateDeck);
		btnCreateDeck.setEnabled(false);

		textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(17, 13, 156, 93);
		panelContinue.add(textPaneErrorMsg);

		addBtnCreateDeckListener();
	}

	/**
	 * Creates various components that don't belong in a panel
	 * 
	 */
	private void createMiscComponents() {
		JLabel lblTitle = new JLabel("Creating a deck");
		lblTitle.setBounds(154, 24, 96, 16);
		frame.getContentPane().add(lblTitle);
	}

	// ********** Adding Listeners to Components **************** //

	/**
	 * Adds an Action Listener to btnCreateDeck
	 */
	private void addBtnCreateDeckListener() {
		btnCreateDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.createDeck();
			}
		});
	}

	private void addTextFieldNameListener() {
		textFieldName.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				logic.nameChanged();
			}

			public void removeUpdate(DocumentEvent e) {
				logic.nameChanged();
			}

			public void insertUpdate(DocumentEvent e) {
				logic.nameChanged();
			}
		});
	}

	// *************** Other General Methods ********************** //

	/**
	 * Displays an error message to a user
	 * 
	 * @param msg String for the error message to be displayed
	 */
	public void displayError(String msg) {
		textPaneErrorMsg.setText(msg);
	}

	public JTextField getTextFieldDescription() {
		return textFieldDescription;
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public JButton getBtnCreateDeck() {
		return btnCreateDeck;
	}

	// *******************************************
	// Remove methods bellow once Screen has been implemented

	/**
	 * Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}

	/**
	 * Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
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

}
