package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import gui.guiLogic.CreateDeckScreenLogic;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Represents a Screen to create a new Deck
 * 
 * @author Hugo Phibbs
 *
 */
public class CreateDeckScreen extends Screen {
	/**
	 * TextField for entering the name of a new Deck
	 */
	private JTextField textFieldName;
	/**
	 * TextField for entering the description of a new Deck
	 */
	private JTextField textFieldDescription;
	/**
	 * TextPane to display an error to a
	 */
	private JTextPane textPaneErrorMsg;
	/**
	 * CrateDeckScreenLogic object to manipulate this Screen
	 */
	private CreateDeckScreenLogic logic;
	/**
	 * JButton to press to create a new Deck
	 */
	private JButton btnCreateDeck;

	/**
	 * Create the application.
	 */
	public CreateDeckScreen(CreateDeckScreenLogic createDeckScreenLogic) {
		super("Creating a deck", createDeckScreenLogic, false);
		this.logic = createDeckScreenLogic;
		initialize();
	}

	@Override
	public void initialize() {
		createComponents();
		configFrame();
	}

	@Override
	protected void configFrame() {
		frame.setBounds(100, 100, 450, 300);
	}
	// ******************** Creating Components ************************ //

	@Override
	protected void createComponents() {
		createDetailsPanel();
		createContinuePanel();
		createMiscComponents();
	}

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

	/**
	 * Adds a Document Listener to textFieldName
	 */
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

	/**
	 * Getter method for textFieldDescription
	 * 
	 * @return JTextField object for textFieldDescription
	 */
	public JTextField getTextFieldDescription() {
		return textFieldDescription;
	}

	/**
	 * Getter method for textFieldName
	 * 
	 * @return JTextField object for textFieldName
	 */
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	/**
	 * Getter method for btnCreateDeck
	 * 
	 * @return JTextField object for btnCreateDeck
	 */
	public JButton getBtnCreateDeck() {
		return btnCreateDeck;
	}

}
