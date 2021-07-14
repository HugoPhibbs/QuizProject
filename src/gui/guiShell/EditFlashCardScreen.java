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

/**
 * Represents a Screen to edit a FlashCard object
 * <p>
 * Handles both editing a FlashCard that has already been created or creating an
 * entirely new FlashCard
 * 
 * @author Hugo Phibbs
 */
public class EditFlashCardScreen {

	JFrame frame;

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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditFlashCardScreen window = new EditFlashCardScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 */
	private EditFlashCardScreen() {
		/*
		 * TODO dummy constructor for testing GUI, always create an EditFlashCardScreen
		 * using one of the two constructors bellow
		 */
		super();
		initialize();
	}

	/**
	 * Constructor for an EditFlashCardScreen
	 * <p>
	 * For creating a new FlashCard, currentFlashCard should be set to null
	 *
	 * @param editFlashCardScreenLogic EditFlashCardScreenLogic object to control
	 *                                 this Screen
	 */
	public EditFlashCardScreen(EditFlashCardScreenLogic editFlashCardScreenLogic) {
		super();
		this.logic = editFlashCardScreenLogic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		createFrame();
		createMiscComponents();
		createChangeTextPanel();
		createChooseDeckPanel();
	}

	/**
	 * Handles creating the JFrame for this Screen
	 */
	private void createFrame() {
		frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		show();
	}

	// @Override
	protected void configFrame() {
		frame.setBounds(100, 100, 450, 300);
	}

	// ***************** Creating Components ************************* //

	/**
	 * Creates various components all around the frame that don't belong in a
	 * particular Panel
	 */
	private void createMiscComponents() {

		btnFinish = new JButton("Finish");
		btnFinish.setBounds(336, 230, 95, 28);
		frame.getContentPane().add(btnFinish);
		btnFinish.setEnabled(!logic.getIsCreating());
		addFinishBtnListener();

		lblAction = new JLabel(String.format("%s a flash card!", logic.operation()));
		lblAction.setBounds(161, 36, 148, 28);
		frame.getContentPane().add(lblAction);

		textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(335, 156, 96, 45);
		frame.getContentPane().add(textPaneErrorMsg);
	}

	/**
	 * Create components relating to changing the text of a flashCard
	 * 
	 */
	private void createChangeTextPanel() {
		JPanel panelChangeText = new JPanel();
		panelChangeText.setBounds(105, 71, 223, 56);
		frame.getContentPane().add(panelChangeText);
		panelChangeText.setLayout(null);

		textFieldFrontText = new JTextField(logic.currentFlashCardFrontText());
		textFieldFrontText.setBounds(7, 7, 106, 27);
		panelChangeText.add(textFieldFrontText);
		textFieldFrontText.setColumns(10);

		JLabel lblFrontText = new JLabel("Front Text");
		lblFrontText.setBounds(17, 41, 77, 9);
		panelChangeText.add(lblFrontText);

		textFieldBackText = new JTextField(logic.currentFlashCardFrontText());
		textFieldBackText.setBounds(120, 7, 95, 27);
		panelChangeText.add(textFieldBackText);
		textFieldBackText.setColumns(10);

		JLabel lblBackText = new JLabel("Back Text");
		lblBackText.setBounds(130, 41, 85, 9);
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
		panelChooseDeck.setBounds(140, 156, 138, 45);
		frame.getContentPane().add(panelChooseDeck);
		panelChooseDeck.setLayout(null);

		// Keep track of if combo box selection has changed.
		JComboBox<Object> comboBoxSelectDeck = new JComboBox<Object>(logic.deckArray());
		comboBoxSelectDeck.setBounds(19, 24, 95, 14);
		panelChooseDeck.add(comboBoxSelectDeck);
		// TODO whenever a deck is selected, currentDeck is updated!

		JLabel lblChooseDeck = new JLabel("Choose Deck");
		lblChooseDeck.setBounds(19, 7, 85, 14);
		panelChooseDeck.add(lblChooseDeck);
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

	// *****************************************************
	// Remove bellow methods later.

	/**
	 * Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
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
	 * Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
	}

}
