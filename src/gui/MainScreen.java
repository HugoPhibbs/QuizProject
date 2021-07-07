package gui;

import java.awt.EventQueue;  

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coreLogic.AppEnvironment;
import coreObjects.Deck;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** Represents the main screen of the application
 * <p>
 * Central part of the application, displays decks to a user, along with options to
 * start a new quiz, edit decks and creating new FlashCards
 * 
 * @author Hugo Phibbs
 *
 */
public class MainScreen{
	
	JFrame frame;
	/** JTable to hold any decks belonging to a user for this application */
	JTable tableDecks;
	/** AppEnvironment object, holds all non-gui objects necessary for an instance of MainScreen */
	AppEnvironment appEnvironment;
	/** JPanel to select any elements relating to displaying of decks to a user */
	JPanel panelViewDecks;
	/** Deck object representing the currently selected deck from tableDeck, null if no Deck is chosen*/
	Deck chosenDeck = null;
	/** JButton to edit a chosen Deck */
	JButton btnEditDeck;
	/** JButton to start a quiz for a chosen Deck */
	JButton btnStartQuiz;
	/** JButton to add a new FlashCard to the chosen deck */
	JButton btnNewFlashCard;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainScreen window = new MainScreen(null);
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
	public MainScreen(AppEnvironment appEnvironment) {
		// TODO Implement!
		super();
		initialize();
		this.appEnvironment = appEnvironment;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createTablePanel();
		createOptionsPanel();
	}
	
	private void createTablePanel() {
		this.panelViewDecks = new JPanel();
		panelViewDecks.setBounds(114, 48, 408, 292);
		frame.getContentPane().add(panelViewDecks);
		panelViewDecks.setLayout(null);
		
		JLabel lblSelectDeck = new JLabel("Select a Deck!");
		lblSelectDeck.setBounds(91, 25, 148, 9);
		panelViewDecks.add(lblSelectDeck);
		
		createDecksTable();
	}
	
	
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(114, 353, 408, 71);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);
		
		btnEditDeck = new JButton("Edit Deck");
		btnEditDeck.setBounds(12, 7, 90, 25);
		panelOptions.add(btnEditDeck);
		btnEditDeck.setEnabled(false); // Disabled until a deck is selected
		btnEditDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDeck();
			}
		});
		
		btnNewFlashCard = new JButton("Add FlashCard");
		btnNewFlashCard.setBounds(107, 7, 137, 25);
		panelOptions.add(btnNewFlashCard);
		btnNewFlashCard.setEnabled(false); // Disabled until a deck is selected
		btnNewFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newFlashCard();
			}
		});	
		
		JButton btnCreateDeck = new JButton("Create Deck");
		btnCreateDeck.setBounds(256, 7, 140, 25);
		panelOptions.add(btnCreateDeck);
		btnCreateDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnStartQuiz = new JButton("Start new Quiz!");
		btnStartQuiz.setBounds(107, 40, 137, 25);
		panelOptions.add(btnStartQuiz);
		btnStartQuiz.setEnabled(false); // Disabled until a deck is selected
		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startQuiz();
			}
		});
	}
	
	/** Creates Table to store info on decks
	 * <p>
	 * User can then select a row from the table to quiz themselves on a deck
	 * 
	 */
	public void createDecksTable() {
		// Remove bellow line when screen fully implemented
		// String[][] decks = appEnvironment.getDeckManager().deckCollectionInfo();
		String[] headers = Deck.infoArrayHeaders();
		
		String[][] decks = {
				{"testName1", "3", "5"}, 
				{"testName2", "4", "8"}, 
		};
		
		// TODO make it so you can't edit table entries!
		
		tableDecks = new JTable(decks, headers);
		tableDecks.setBounds(7, 24, 253, 144);
		JScrollPane sp = new JScrollPane(tableDecks);
		sp.setBounds(7, 49, 394, 236);
		panelViewDecks.add(sp);
		ListSelectionModel selectionModel = tableDecks.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent lse) {
		    	// Bellow lines ensures that changing selection of a row counts as one change, not two
		    	if (!lse.getValueIsAdjusting()) {
		    		deckSelected();
		    	}
		    }
		});
		
		// Set the table so it can't be edited
		tableDecks.setDefaultEditor(Object.class, null);
	}
	
	/** Handles selection of row from decksTable
	 * <p>
	 *
	 */
	private void deckSelected() {
		updateChosenDeck();
		btnStartQuiz.setEnabled(true);
		btnEditDeck.setEnabled(true);
		btnNewFlashCard.setEnabled(true);
	}
	
	/** Finds the name of the currently chosen Deck from the deck
	 * 
	 * @return Deck object that has been chosen 
	 */
	private void updateChosenDeck() {
		int chosenRow = tableDecks.getSelectedRow();
		String deckName = tableDecks.getModel().getValueAt(chosenRow,  0).toString();
		chosenDeck = appEnvironment.getDeckManager().findDeck(deckName);
	}
	
	/** Handles when a user wants to create a new FlashCard for the chosen Deck
	 * <p>
	 * Passes functionality off to AppEnvironment
	 */
	public void newFlashCard() {
		// Creates a new edit FlashCard screen
		// TODO remove lines bellow until Screen is fully implemented
		// appEnvironment.newEditFlashCardScreen(null, chosenDeck, this);
		// this.hide() 
	}
	
	/** Handles when a user wants to start a new quiz for the chosen deck
	 * <p>
	 * Passes functionality off to AppEnvironment
	 */
	public void startQuiz() {
		appEnvironment.newQuizzingScreen(chosenDeck);
		// this.hide() // TODO remove until Screen is fully implemented
	}
	
	/** Handles when a user wants to edit the chosen deck
	 * <p>
	 * Passes functionality off to AppEnvironment
	 */
	public void editDeck() {
		appEnvironment.newEditDeckScreen(chosenDeck);
		// this.hide() // TODO remove until Screen is fully implemented
	}
	
	public void createDeck() {
		// TODO implement
	}
}
