package gui;

import java.awt.EventQueue;  

import javax.swing.JFrame;
import javax.swing.JTextField;

import coreObjects.Deck;
import coreObjects.FlashCard;
import coreLogic.DeckManager;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

/** Represents a Screen to edit a FlashCard object
 * <p>
 * Handles both editing a FlashCard that has already been created or creating an entirely new FlashCard
 * 
 * @author Hugo Phibbs
 */
public class EditFlashCardScreen {
	
	JFrame frame;
	
	/** Screen object for the parent of this screen, useful for when a user wants to go back to the previous screen
	 * As there are multiple different Screens that create a new EditFlashCardScreen*/
	Screen parent;
	
	/** Text field for the front text of the FlashCard that is being edited */
	JTextField textFieldBackText;
	/** Text field for the back text of the FlashCard that is being edited */
	JTextField textFieldFrontText;
	/** Text pane to display any messages for errors that may occur */
	JTextPane textPaneErrorMsg;
	/** Label describing the current operation being done with regards to a FlashCard */
	JLabel lblAction;
	
	/** Keeps track of whether we are creating or editing a FlashCard */
	boolean isCreating;
	/** FlashCard object that we are currently editing, set to null if we are creating a FlashCard */
	FlashCard currentFlashCard = null;
	/** DeckManager object for this application, useful when viewing what Decks to put a FlashCard in */
	DeckManager deckManager;
	/** Deck that the FlashCard object that is being editted belongs to, set to null if a FlashCard is being created */
	Deck currentDeck = null;
	
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
		/* TODO dummy constructor for testing GUI, always create an EditFlashCardScreen using
		 * one of the two constructors bellow
		 */
		super();
		initialize();
	}
	
	/** Constructor for an EditFlashCardScreen
	 * <p>
	 * For creating a new FlashCard, currentFlashCard should be set to null
	 * 
	 * @param flashCard FlashCard object that is being edited, null if a new FlashCard is being created
	 * @param deckManager DeckManager object for this application
	 * @param currentDeck Deck object that this FlashCard currently belongs to
	 */
	public EditFlashCardScreen(FlashCard currentFlashCard, DeckManager deckManager, Deck currentDeck, Screen parent) {
		super();
		initialize();
		this.parent = parent;
		this.deckManager = deckManager;
		this.currentDeck = currentDeck;
		this.currentFlashCard = currentFlashCard;
		
		if (currentFlashCard == null) {
			isCreating();
		}
		else {
			isEditing();
		}
		
		setTextFields();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createMiscComponents();
		createChangeTextPanel();
		createChooseDeckPanel();
		
	}
	
	/** Creates various components all around the frame
	 * <p> 
	 * Didn't belong in any other method, so create them here
	 * 
	 */
	private void createMiscComponents() {
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onContinuePressed();
			}
		});
		btnContinue.setBounds(336, 230, 95, 28);
		frame.getContentPane().add(btnContinue);
		btnContinue.setEnabled(!isCreating);
		
		lblAction = new JLabel("%s a flash card!");
		lblAction.setBounds(161, 36, 148, 28);
		frame.getContentPane().add(lblAction);
		
		JTextPane textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(335, 156, 96, 45);
		frame.getContentPane().add(textPaneErrorMsg);
	}
	
	/** Create components relating to changing the text of a flashCard
	 * 
	 */
	private void createChangeTextPanel() {
		JPanel panelChangeText = new JPanel();
		panelChangeText.setBounds(105, 71, 223, 56);
		frame.getContentPane().add(panelChangeText);
		panelChangeText.setLayout(null);
		
		textFieldFrontText = new JTextField();
		textFieldFrontText.setBounds(7, 7, 106, 27);
		panelChangeText.add(textFieldFrontText);
		textFieldFrontText.setColumns(10);
		textFieldFrontText.setText(currentFlashCard.getFrontText());
		
		JLabel lblFrontText = new JLabel("Front Text");
		lblFrontText.setBounds(17, 41, 77, 9);
		panelChangeText.add(lblFrontText);
		
		textFieldBackText = new JTextField();
		textFieldBackText.setBounds(120, 7, 95, 27);
		panelChangeText.add(textFieldBackText);
		textFieldBackText.setColumns(10);
		
		JLabel lblBackText = new JLabel("Back Text");
		lblBackText.setBounds(130, 41, 85, 9);
		panelChangeText.add(lblBackText);
	}
	
	/** Creates components relating to choosing which deck a flashcard belongs to
	 * 
	 */
	private void createChooseDeckPanel() {
		JPanel panelChooseDeck = new JPanel();
		panelChooseDeck.setBounds(140, 156, 138, 45);
		frame.getContentPane().add(panelChooseDeck);
		panelChooseDeck.setLayout(null);
		
		// Keep track of if combo box selection has changed. 
		// JComboBox<Object> comboBoxSelectDeck = new JComboBox<Object>(deckArray());
		String[] testArray = new String[] {"testDeck1", "testDeck2"};
		JComboBox<Object> comboBoxSelectDeck = new JComboBox<Object>(testArray);
		comboBoxSelectDeck.setBounds(19, 24, 95, 14);
		panelChooseDeck.add(comboBoxSelectDeck);
		
		JLabel lblChooseDeck = new JLabel("Choose Deck");
		lblChooseDeck.setBounds(19, 7, 85, 14);
		panelChooseDeck.add(lblChooseDeck);

	}
	
	/** Returns the Deck's that can be selected from when choosing which Deck a FlashCard belongs to
	 * <p>
	 * If a FlashCard is currently being edited, then the currentDeck is put to the front of the Array,
	 * So if a user is just editing a FlashCard to change the front and back text, then they don't have to worry
	 * about which deck to put it in, as this will already be preselected
	 * 
	 * @return Deck[] containing deck objects as described. 
	 */
	private Deck[] deckArray(){
		ArrayList<Deck> deckCollection = deckManager.getDeckCollection();
		if (!isCreating) {
			deckCollection.add(0, currentDeck); // Set the current deck to be top of list
		}
		return (Deck[]) deckCollection.toArray();
	}
	
	
	
	/** Adjusts components for when a FlashCard is being editted, not created
	 * <p>
	 * 
	 */
	private void isEditing() {
		isCreating = false;
		changeActionLabel("Editing");
		
		// Set combo box to have current deck to be selected
	}
	
	/** Adjusts components for when a FlashCard is being created, not editted
	 * 
	 */
	private void isCreating() {
		isCreating = true;
		currentFlashCard = new FlashCard("", "");
		changeActionLabel("Creating");
	}
	
	/** Sets the text of the TextFields to display the current front and back
	 * text of the FlashCard that is being created or edited
	 */
	private void setTextFields() {
		textFieldFrontText.setText(currentFlashCard.getFrontText());
		textFieldBackText.setText(currentFlashCard.getBackText());
	}
	
	/** Adjusts the text of the action label for a user
	 * 
	 * @param operation String for what a user is doing, either "Creating" or "Editing"
	 */
	private void changeActionLabel(String operation) {
		lblAction.setText(String.format(lblAction.getText(), operation));
	}
	
	
	/** Handles what happens when a user presses continue
	 * <p>
	 * Diverts flow to either create a FlashCard or edit a FlashCard. 
	 * <p>
	 * Handles any exceptions if they are thrown
	 */
	private void onContinuePressed() {
		try {
			if (isCreating) {
				createFlashCard();
			}
			else {
				editFlashCard();
			}
			// If above statements didn't throw an error, bellow will be executed
			// close()
		}
		// Catch any exceptions to do with methods above
		catch (Exception e) {
			textPaneErrorMsg.setText(e.getMessage());
		}
	}
	
	
	/** Handles pressing of "CONTINUE" button, when editing a flashCard
	 * 
	 */
	private void editFlashCard() {
		currentDeck.editFlashCard(currentFlashCard, 
				textFieldFrontText.getText(), 
				textFieldBackText.getText());		
    }
	
	/** Handles pressing of "CONTINUE" button, when creating a flashCard
	 * 
	 */
	private void createFlashCard() {
		currentFlashCard.setFrontText(textFieldFrontText.getText());
		currentFlashCard.setFrontText(textFieldBackText.getText());
		
		currentDeck.addFlashCard(currentFlashCard);
	}
}
