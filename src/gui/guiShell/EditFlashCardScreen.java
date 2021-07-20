package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;

import gui.guiLogic.EditFlashCardScreenLogic;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.Font;

/**
 * Represents a Screen to edit a FlashCard object
 * <p>
 * Handles both editing a FlashCard that has already been created or creating an
 * entirely new FlashCard
 * 
 * @author Hugo Phibbs
 */
public class EditFlashCardScreen extends Screen {

	/**
	 * Text field for the front text of the FlashCard that is being edited
	 */
	JTextField textFieldBackText;
	/**
	 * Text field for the back text of the FlashCard that is being edited
	 */
	JTextField textFieldFrontText;
	/**
	 * Text pane to display any messages for errors that may occur
	 */
	JTextPane textPaneErrorMsg;
	/**
	 * Label describing the current operation being done with regards to a FlashCard
	 */
	JLabel lblAction;
	/**
	 * JButton to finish editing a FlashCard
	 */
	JButton btnFinish;
	/**
	 * EditFlashCardScreenLogic object to handle logic for this Screen
	 */
	EditFlashCardScreenLogic logic;

	/**
	 * Constructor for an EditFlashCardScreen
	 * <p>
	 * For creating a new FlashCard, currentFlashCard should be set to null
	 *
	 * @param editFlashCardScreenLogic EditFlashCardScreenLogic object to control
	 *                                 this Screen
	 */
	public EditFlashCardScreen(EditFlashCardScreenLogic editFlashCardScreenLogic) {
		super("Editting FlashCard", editFlashCardScreenLogic, false);
		this.logic = editFlashCardScreenLogic;
		initialize();
	}

	// *********** Configurating the Screen **************** //

	@Override
	public void initialize() {
		configFrame();
		createComponents();
	}

	@Override
	protected void createComponents() {
		createMiscComponents();
		createChangeTextPanel();
		createChooseDeckPanel();
		createFinishPanel();
	}

	@Override
	protected void configFrame() {
		frame.setBounds(100, 100, 391, 292);
	}

	// ***************** Creating Components ************************* //

	/**
	 * Creates various components all around the frame that don't belong in a
	 * particular Panel
	 */
	private void createMiscComponents() {
		lblAction = new JLabel(String.format("%s a flash card!", logic.operation()));
		lblAction.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAction.setBounds(78, 31, 237, 28);
		frame.getContentPane().add(lblAction);
	}

	/**
	 * Create components relating to changing the text of a flashCard
	 * 
	 */
	private void createChangeTextPanel() {
		JPanel panelChangeText = new JPanel();
		panelChangeText.setBounds(20, 76, 223, 159);
		frame.getContentPane().add(panelChangeText);
		panelChangeText.setLayout(null);

		textFieldFrontText = new JTextField(logic.currentFlashCardFrontText());
		textFieldFrontText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldFrontText.setBounds(5, 26, 210, 50);
		panelChangeText.add(textFieldFrontText);
		textFieldFrontText.setColumns(10);

		JLabel lblFrontText = new JLabel("Front Text");
		lblFrontText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblFrontText.setBounds(15, 6, 64, 14);
		panelChangeText.add(lblFrontText);

		textFieldBackText = new JTextField(logic.currentFlashCardFrontText());
		textFieldBackText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textFieldBackText.setBounds(5, 103, 210, 50);
		panelChangeText.add(textFieldBackText);
		textFieldBackText.setColumns(10);

		JLabel lblBackText = new JLabel("Back Text");
		lblBackText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblBackText.setBounds(15, 81, 85, 16);
		panelChangeText.add(lblBackText);

		addTextFieldFrontTextListener();
		addTextFieldBackTextListener();
	}

	/**
	 * Creates components relating to choosing which deck a flashcard belongs to
	 * 
	 */
	private void createChooseDeckPanel() {
		JPanel panelChooseDeck = new JPanel();
		panelChooseDeck.setBounds(248, 77, 116, 45);
		frame.getContentPane().add(panelChooseDeck);
		panelChooseDeck.setLayout(null);

		// Keep track of if combo box selection has changed.
		JComboBox<Object> comboBoxSelectDeck = new JComboBox<Object>(logic.deckArray());
		comboBoxSelectDeck.setFont(new Font("Tahoma", Font.PLAIN, 11));
		comboBoxSelectDeck.setBounds(9, 25, 95, 14);
		panelChooseDeck.add(comboBoxSelectDeck);
		// TODO whenever a deck is selected, currentDeck is updated!

		JLabel lblChooseDeck = new JLabel("Choose Deck");
		lblChooseDeck.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblChooseDeck.setBounds(19, 5, 85, 14);
		panelChooseDeck.add(lblChooseDeck);
	}

	/**
	 * Creates components contained in panelFinish. That is componenets relating to
	 * finish editting a FlashCard
	 */
	public void createFinishPanel() {
		JPanel panelFinish = new JPanel();
		panelFinish.setBounds(248, 128, 116, 107);
		frame.getContentPane().add(panelFinish);
		panelFinish.setLayout(null);

		textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(9, 6, 95, 58);
		panelFinish.add(textPaneErrorMsg);

		btnFinish = new JButton("Finish");
		btnFinish.setBounds(9, 73, 95, 28);
		panelFinish.add(btnFinish);
		btnFinish.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFinish.setEnabled(!logic.getIsCreating());

		addFinishBtnListener();
	}

	// ************ Adding Listeners to Components ********************* //

	/** Adds Action Listener to btnFinish */
	private void addFinishBtnListener() {
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.onFinishedPressed();
			}
		});
	}

	/** Adds listener to textFieldFront */
	public void addTextFieldFrontTextListener() {
		textFieldFrontText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				logic.textEntered();
			}

			public void removeUpdate(DocumentEvent e) {
				logic.textEntered();
			}

			public void insertUpdate(DocumentEvent e) {
				logic.textEntered();
			}
		});
	}

	/** Add listener to textFieldBackText */
	public void addTextFieldBackTextListener() {
		textFieldBackText.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				logic.textEntered();
			}

			public void removeUpdate(DocumentEvent e) {
				logic.textEntered();
			}

			public void insertUpdate(DocumentEvent e) {
				logic.textEntered();
			}
		});
	}

	// ************* General Helper Methods *************** //

	/**
	 * Displays an error message to a user
	 * 
	 * @param msg String for the error message to be displayed
	 */
	public void displayError(String msg) {
		textPaneErrorMsg.setText(msg);
	}

	/**
	 * Finds the text currently in textFieldFrontText
	 * 
	 * @return String for the text currently in textFieldFrontText
	 */
	public String frontText() {
		return textFieldFrontText.getText();
	}

	/**
	 * Finds the text currently in textFieldBackText
	 * 
	 * @return String for the text currently in textFieldBackText
	 */
	public String backText() {
		return textFieldBackText.getText();
	}

	// **************** Getter methods ****************** //

	/**
	 * Getter method for btnFinish
	 * 
	 * @return JButton btnFinish
	 */
	public JButton getBtnFinish() {
		return btnFinish;
	}
}
