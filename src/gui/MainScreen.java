package gui;

import java.awt.EventQueue;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import coreLogic.AppEnvironment;
import coreLogic.FlashCardQuiz;
import coreObjects.Deck;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainScreen{
	
	JFrame frame;
	JTable tableDecks;
	AppEnvironment appEnvironment;
	JPanel panelTable;
	Deck chosenDeck = null;
	JButton btnEditDeck;
	JButton btnStartQuiz;
	
	
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
		this.panelTable = new JPanel();
		panelTable.setBounds(114, 48, 408, 292);
		frame.getContentPane().add(panelTable);
		panelTable.setLayout(null);
		
		JLabel lblSelectDeck = new JLabel("Select a Deck!");
		lblSelectDeck.setBounds(91, 25, 148, 9);
		panelTable.add(lblSelectDeck);
		
		createDecksTable();
	}
	
	
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(114, 379, 408, 28);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);
		
		btnEditDeck = new JButton("Edit Deck");
		btnEditDeck.setBounds(7, 7, 90, 17);
		panelOptions.add(btnEditDeck);
		btnEditDeck.setEnabled(false); // Disabled until a deck is selected
		btnEditDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editDeck();
			}
		});

		btnStartQuiz = new JButton("Start new Quiz!");
		btnStartQuiz.setBounds(104, 7, 153, 17);
		panelOptions.add(btnStartQuiz);
		btnStartQuiz.setEnabled(false); // Disabled until a deck is selected
		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newQuiz();
			}
		});
		
		JButton btnNewFlashCard = new JButton("New flash card");
		btnNewFlashCard.setBounds(264, 7, 137, 17);
		panelOptions.add(btnNewFlashCard);
		btnNewFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newFlashCard();
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
		panelTable.add(sp);
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
		// Commented out chosenDeck = chosenDeck();
		btnStartQuiz.setEnabled(true);
		btnEditDeck.setEnabled(true);
	}
	
	/** Returns the name of the currently chosen deck from decksTable
	 * <p>
	 * Finds the name from the deck and then finds the matching Deck object based on this name
	 * 
	 * @return Deck object that has been chosen 
	 */
	private Deck chosenDeck() {
		int chosenRow = tableDecks.getSelectedRow();
		String deckName = tableDecks.getModel().getValueAt(chosenRow,  0).toString();
		Deck chosenDeck = appEnvironment.getDeckManager().findDeck(deckName);
		return chosenDeck;
	}
	
	public void newFlashCard() {
		// Creates a new edit FlashCard screen
		EditFlashCardScreen flashCardScreen = new EditFlashCardScreen();
	}
	
	public void newQuiz() {
		FlashCardQuiz flashCardQuiz = new FlashCardQuiz(null, null);
	}
	
	public void editDeck() {
		
	}
}
