package gui.guiShell;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class MainScreen extends Screen {

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
	 * JButton to refresh the screen
	 */
	JButton btnRefresh;
	/**
	 * MainScreenLogic object to handle any logic concerned with Screen
	 */
	MainScreenLogic logic;

	/**
	 * Create the application.
	 */
	public MainScreen(MainScreenLogic mainScreenLogic) {
		super("Main options", mainScreenLogic);
		this.logic = mainScreenLogic;
	}

	@Override
	public void initialize() {
		configFrame();
		createComponents();
	}

	@Override
	public void configFrame() {
		frame.setBounds(100, 100, 720, 492);
	}

	// ******************** Creating Components ************** //

	/** Calls methods to create components for this Screen */
	protected void createComponents() {
		createTablePanel();
		createOptionsPanel();
	}

	/**
	 * Create and fill panel for components relating to displaying a Deck collection
	 * to a user
	 */
	public void createTablePanel() {
		this.panelViewDecks = new JPanel();
		panelViewDecks.setBounds(114, 48, 408, 292);
		frame.getContentPane().add(panelViewDecks);
		panelViewDecks.setLayout(null);

		updatePanelViewDecks();
	}

	/**
	 * Fills panelViewDecks with components, including a title label and a table to
	 * view decks
	 * <p>
	 * User can then select a row from the table to quiz themselves on a deck
	 * 
	 */
	public void updatePanelViewDecks() {
		JLabel lblSelectDeck = new JLabel("Select a Deck!");
		lblSelectDeck.setBounds(91, 25, 148, 9);
		panelViewDecks.add(lblSelectDeck);

		tableDecks = new JTable(logic.decksTableDetails(), logic.decksTableHeaders());
		tableDecks.setBounds(7, 24, 253, 144);
		JScrollPane sp = new JScrollPane(tableDecks);
		sp.setBounds(7, 49, 394, 236);
		panelViewDecks.add(sp);

		disableTableEditting(tableDecks);

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

		btnNewFlashCard = new JButton("Add FlashCard");
		btnNewFlashCard.setBounds(107, 7, 137, 25);
		panelOptions.add(btnNewFlashCard);

		btnCreateDeck = new JButton("Create Deck");
		btnCreateDeck.setBounds(256, 7, 140, 25);
		panelOptions.add(btnCreateDeck);

		btnStartQuiz = new JButton("Start new Quiz!");
		btnStartQuiz.setBounds(107, 40, 137, 25);
		panelOptions.add(btnStartQuiz);

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

	// *************** General Helper Methods ********************* //

	@Override
	public void displayError(String msg) {
		// TODO Auto-generated method stub

	}

	// ********************* Getter methods ************************** //

	/**
	 * Getter method for panelViewDecks
	 * 
	 * @return JPanel object for panelViewDecks
	 */
	public JPanel getPanelViewDecks() {
		return panelViewDecks;
	}

	/**
	 * Getter method for btnEditFlashCard
	 * 
	 * @return JButton object for btnEditFlashCard
	 */
	public JButton getBtnEditDeck() {
		return btnEditDeck;
	}

	/**
	 * Getter method for btnStartQuiz
	 * 
	 * @return JButton object for btnStartQuiz
	 */
	public JButton getBtnStartQuiz() {
		return btnStartQuiz;
	}

	/**
	 * Getter method for btnNewFlashCard
	 * 
	 * @return JButton object for btnNewFlashCard
	 */
	public JButton getBtnNewFlashCard() {
		return btnNewFlashCard;
	}

	/**
	 * Getter method for tableDecks
	 * 
	 * @return JTable object for tableDecks
	 */
	public JTable getTableDecks() {
		return tableDecks;
	}
}
