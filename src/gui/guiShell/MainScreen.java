package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.coreLogic.AppEnvironment;
import core.coreObjects.Deck;
import gui.guiLogic.MainScreenLogic;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Represents the main screen of the application
 * <p>
 * Central part of the application, displays decks to a user, along with options
 * to start a new quiz, edit decks and creating new FlashCards
 * 
 * @author Hugo Phibbs
 *
 */
public class MainScreen {

	JFrame frame;

	/**
	 * JTable to hold any decks belonging to a user for this application
	 */
	JTable tableDecks;
	/**
	 * JPanel to select any elements relating to displaying of decks to a user
	 */
	JPanel panelViewDecks;
	/**
	 * JButton to edit a chosen Deck
	 */
	JButton btnEditDeck;
	/**
	 * JButton to start a quiz for a chosen Deck
	 */
	JButton btnStartQuiz;
	/**
	 * JButton to add a new FlashCard to the chosen deck
	 */
	JButton btnNewFlashCard;
	/**
	 * JButton to create a new Deck
	 */
	JButton btnCreateDeck;
	/**
	 * MainScreenLogic object to handle any logic concerned with Screen
	 */
	MainScreenLogic logic;

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
	public MainScreen(MainScreenLogic mainScreenLogic) {
		// TODO Implement!
		super();
		this.logic = mainScreenLogic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 492);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		createComponents();
	}

	/** Calls methods to create components for this Screen */
	private void createComponents() {
		createTablePanel();
		createOptionsPanel();
	}

	/**
	 * Create and fill panel for components relating to displaying a Deck collection
	 * to a user
	 */
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

	/**
	 * Creates Table to store info on decks
	 * <p>
	 * User can then select a row from the table to quiz themselves on a deck
	 * 
	 */
	public void createDecksTable() {
		tableDecks = new JTable(logic.decksTableDetails(), logic.decksTableHeaders());
		tableDecks.setBounds(7, 24, 253, 144);
		JScrollPane sp = new JScrollPane(tableDecks);
		sp.setBounds(7, 49, 394, 236);
		panelViewDecks.add(sp);

		// Set the table so it can't be edited
		tableDecks.setDefaultEditor(Object.class, null);

		addDecksTableListener();
	}

	/**
	 * Create and fill Panel with components relating to main application options
	 */
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(114, 353, 408, 71);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);

		btnEditDeck = new JButton("Edit Deck");
		btnEditDeck.setBounds(12, 7, 90, 25);
		panelOptions.add(btnEditDeck);
		btnEditDeck.setEnabled(false); // Disabled until a deck is selected

		btnNewFlashCard = new JButton("Add FlashCard");
		btnNewFlashCard.setBounds(107, 7, 137, 25);
		panelOptions.add(btnNewFlashCard);
		btnNewFlashCard.setEnabled(false); // Disabled until a deck is selected

		btnCreateDeck = new JButton("Create Deck");
		btnCreateDeck.setBounds(256, 7, 140, 25);
		panelOptions.add(btnCreateDeck);

		btnStartQuiz = new JButton("Start new Quiz!");
		btnStartQuiz.setBounds(107, 40, 137, 25);
		panelOptions.add(btnStartQuiz);
		btnStartQuiz.setEnabled(false); // Disabled until a deck is selected

		addOptionsPanelBtnListeners();
	}

	// ******************** Adding Listeners to Components *************************
	// //

	/** Creates action listeners to the buttons in panelOptions */
	private void addOptionsPanelBtnListeners() {
		btnEditDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.editDeck();
			}
		});

		btnNewFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.createFlashCard();
			}
		});

		btnCreateDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.createDeck();
			}
		});

		btnStartQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.newQuiz();
			}
		});

	}

	/** Adds a seletion listener to tableDecks */
	private void addDecksTableListener() {
		ListSelectionModel selectionModel = tableDecks.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				logic.deckSelected(lse);
			}
		});
	}

	// ********************* Getter methods ************************** //

	public JButton getBtnEditDeck() {
		return btnEditDeck;
	}

	public JButton getBtnStartQuiz() {
		return btnStartQuiz;
	}

	public JButton getBtnNewFlashCard() {
		return btnNewFlashCard;
	}

	public JTable getTableDecks() {
		return tableDecks;
	}

	// *************** Methods to remove once Screen implemented ****** //
	public void toggleButton(JButton btn, boolean setting) {
		btn.setEnabled(setting);
	}

}
