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
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditFlashCardScreen {
	
	JFrame frame;
	JTextField textFieldBackText;
	JTextField textFieldFrontText;
	JLabel lblErrorText;
	
	/** Keeps track of whether we are creating or editing a FlashCard */
	boolean isCreating = false;
	/** FlashCard object that we are currently editing, set to null if we are creating a FlashCard */
	FlashCard flashCard = null;
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
	 */
	public EditFlashCardScreen() {
		//super();
		initialize();
	}
	
	/** Constructor for creating a FlashCard
	 * 
	 * @param deckManager DeckManager object to be used for this screen
	 */
	public EditFlashCardScreen(DeckManager deckManager) {
		initialize();
		this.isCreating = true;
		this.deckManager = deckManager;
		flashCard = new FlashCard();
	}
	
	/** Constructor for editing a FlashCard
	 * 
	 * @param flashCard FlashCard object that is being edited
	 * @param deckManager DeckManager object for this application
	 * @param currentDeck Deck object that this FlashCard currently belongs to
	 */
	public EditFlashCardScreen(FlashCard flashCard, DeckManager deckManager, Deck currentDeck) {
		initialize();
		this.deckManager = deckManager;
		this.flashCard = flashCard;
		this.currentDeck = currentDeck;
		isEditing();
	}
	
	/** Adjusts components for when a FlashCard is being editted, not created
	 * <p>
	 * Default settings of components are for creating FlashCard objects
	 * 
	 */
	private void isEditing() {
		textFieldFrontText.setText(flashCard.getFrontText());
		textFieldBackText.setText(flashCard.getBackText());
		
		// Set combo box to have current deck to be selected
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createMiscLabels();
		createChangeTextPanel();
		createChooseDeckPanel();
		
	}
	
	/** Creates various labels all around the frame
	 * <p> 
	 * Didn't belong in any other method, so create them here
	 * 
	 */
	private void createMiscLabels() {
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isCreating) {
					createFlashCard();
				}
				else {
					editFlashCard();
				}
			}
		});
		btnContinue.setBounds(336, 230, 95, 28);
		frame.getContentPane().add(btnContinue);
		btnContinue.setEnabled(!isCreating);
		
		JLabel lblAction = new JLabel("<action>ing a flash card!");
		lblAction.setBounds(161, 36, 148, 28);
		frame.getContentPane().add(lblAction);
		
		lblErrorText = new JLabel("<Error Text>");
		lblErrorText.setForeground(Color.RED);
		lblErrorText.setBounds(336, 202, 95, 21);
		frame.getContentPane().add(lblErrorText);
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
		JComboBox comboBoxSelectDeck = new JComboBox();
		comboBoxSelectDeck.setBounds(19, 24, 95, 14);
		panelChooseDeck.add(comboBoxSelectDeck);
		
		JLabel lblChooseDeck = new JLabel("Choose Deck");
		lblChooseDeck.setBounds(19, 7, 85, 14);
		panelChooseDeck.add(lblChooseDeck);

	}
	
	/** Handles pressing of "CONTINUE" button, when editing a flashCard
	 * 
	 */
	private void editFlashCard() {
		try {
			currentDeck.contains()
		}
		catch (Exception e) {
			lblErrorText.setText(e.getMessage());
		}
		
	}
	
	/** Handles pressing of "CONTINUE" button, when creating a flashCard
	 * 
	 */
	private void createFlashCard() {
		try {
			FlashCard newFlashCard = new FlashCard();
			
		}
		catch (Exception e){
			lblErrorText.setText(e.getMessage());
		}
	}
}
