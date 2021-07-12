package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import gui.guiLogic.EditDeckScreenLogic;

/**
 * Represents a Screen for editing a Deck
 * 
 * @author Hugo Phibbs
 * 
 */
public class EditDeckScreen {

	JFrame frame;
	/**
	 * JTable to contain FlashCards that are contained in a Deck User can select
	 * FlashCards from this Table to edit
	 */
	private JTable tableFlashCards;
	/** JTextField where a user can enter and edit the name of a Deck */
	private JTextField textFieldName;
	/** JTextField where a user can enter and edit the description of a Deck */
	private JTextField textFieldDescription;
	/** Text pane to display any errors that occur while editing a Deck */
	private JTextPane textPaneErrorMsg;
	/**
	 * JPanel to hold components relating to displaying FlashCards contained in a
	 * Deck
	 */
	JPanel panelFlashCards;
	/** JButton to add a new FlashCard to a Deck */
	JButton btnAddFlashCard;
	/** JButton to edit a FlashCard from a Deck */
	JButton btnEditFlashCard;
	/** JButton to delete a FlashCard from a Deck */
	JButton btnDeleteFlashCard;
	/** JButton to finish editing a Deck */
	JButton btnFinish;
	/** JButton to delete a Deck */
	JButton btnDeleteDeck;
	/** EditDeckScreenLogic object to control this screen */
	EditDeckScreenLogic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditDeckScreen window = new EditDeckScreen();
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
	public EditDeckScreen() {
		super();
		initialize();
	}

	public EditDeckScreen(EditDeckScreenLogic editDeckScreenLogic) {
		super();
		this.logic = editDeckScreenLogic;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 900, 900);
		frame.getContentPane().setLayout(null);

		createComponents();
	}

	// ******************** Creating Components ******************* //

	private void createComponents() {
		createFlashCardsPanel();
		createFlashCardOptionsPanel();
		createFinishPanel();
		createMiscComponents();
		createDeckDetailsPanel();
	}

	/**
	 * Create Panel and then fill it with components displaying FlashCards contained
	 * in a Deck
	 */
	private void createFlashCardsPanel() {
		panelFlashCards = new JPanel();
		panelFlashCards.setBounds(63, 101, 742, 493);
		frame.getContentPane().add(panelFlashCards);
		panelFlashCards.setLayout(null);

		updatePanelFlashCards();
	}

	/**
	 * Updates panelFlashCards by filling it with necessary components
	 */
	public void updatePanelFlashCards() {
		JLabel lblFlashCards = new JLabel("FlashCards");
		lblFlashCards.setBounds(309, 13, 86, 16);
		panelFlashCards.add(lblFlashCards);

		createTableFlashCards();
	}

	public void createTableFlashCards() {
		tableFlashCards = new JTable(logic.flashCardsTableDetails(), logic.flashCardsTableHeaders());
		tableFlashCards.setBounds(12, 39, 718, 441);

		JScrollPane sp = new JScrollPane(tableFlashCards);
		sp.setBounds(7, 49, 394, 236);
		panelFlashCards.add(sp);

		addFlashCardsTableListener();
	}

	/**
	 * Create Panel and fill it with components relating to Editing FlashCards in a
	 * Deck
	 */
	private void createFlashCardOptionsPanel() {
		JPanel panelFlashCardOptions = new JPanel();
		panelFlashCardOptions.setBounds(367, 622, 182, 129);
		frame.getContentPane().add(panelFlashCardOptions);
		panelFlashCardOptions.setLayout(null);

		btnAddFlashCard = new JButton("Add FlashCard");
		btnAddFlashCard.setBounds(12, 13, 160, 28);
		panelFlashCardOptions.add(btnAddFlashCard);

		btnEditFlashCard = new JButton("Edit FlashCard");
		btnEditFlashCard.setBounds(12, 54, 160, 25);
		panelFlashCardOptions.add(btnEditFlashCard);
		btnEditFlashCard.setEnabled(false);

		btnDeleteFlashCard = new JButton("Delete FlashCard");
		btnDeleteFlashCard.setBounds(12, 92, 160, 25);
		panelFlashCardOptions.add(btnDeleteFlashCard);
		btnDeleteFlashCard.setEnabled(false);

		adFlashCardPanelOptionsBtnListeners();
	}

	/**
	 * Create panel and fill it with components relating to editing the details of a
	 * Deck, i.e it's name and the description
	 */
	private void createDeckDetailsPanel() {
		JPanel panelDeckDetails = new JPanel();
		panelDeckDetails.setBounds(63, 622, 296, 129);
		frame.getContentPane().add(panelDeckDetails);
		panelDeckDetails.setLayout(null);

		JLabel lblName = new JLabel("Deck name:");
		lblName.setBounds(12, 13, 124, 16);
		panelDeckDetails.add(lblName);

		textFieldName = new JTextField(logic.deckName());
		textFieldName.setBounds(138, 10, 146, 22);
		panelDeckDetails.add(textFieldName);
		textFieldName.setColumns(10);

		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(12, 60, 272, 56);
		panelDeckDetails.add(textFieldDescription);
		textFieldDescription.setColumns(10);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(12, 42, 110, 16);
		panelDeckDetails.add(lblDescription);
	}

	/**
	 * Create JPanel and fill it with components relating to finish editing a deck
	 * Along with an option to delete a deck.
	 */
	private void createFinishPanel() {
		JPanel panelFinish = new JPanel();
		panelFinish.setBounds(561, 622, 244, 129);
		frame.getContentPane().add(panelFinish);
		panelFinish.setLayout(null);

		btnFinish = new JButton("Continue");
		btnFinish.setBounds(129, 90, 103, 25);
		panelFinish.add(btnFinish);

		textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(12, 13, 220, 64);
		panelFinish.add(textPaneErrorMsg);

		btnDeleteDeck = new JButton("Delete Deck");
		btnDeleteDeck.setBounds(12, 90, 103, 25);
		panelFinish.add(btnDeleteDeck);

		addFinishPanelBtnListeners();
	}

	/** Create Miscelaneous components that don't belong in any panelr */
	private void createMiscComponents() {
		JLabel lblTitle = new JLabel(logic.deckName());
		lblTitle.setBounds(341, 60, 138, 28);
		frame.getContentPane().add(lblTitle);
	}

	// ********************** Adding Listeners to Components
	// ************************ //

	/** Adds a Selection Listener to tableFlashCards */
	private void addFlashCardsTableListener() {
		ListSelectionModel selectionModel = tableFlashCards.getSelectionModel();
		selectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent lse) {
				logic.flashCardSelected(lse);
			}
		});
	}

	/** Adds Action Listeners to all of the buttons in panelFlashCardOptions */
	private void adFlashCardPanelOptionsBtnListeners() {
		btnAddFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.addFlashCard();
			}
		});

		btnEditFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.editFlashCard();
			}
		});

		btnDeleteFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.deleteFlashCard();
			}
		});
	}

	/** Adds Actions listeners onto buttons in panelFinish */
	private void addFinishPanelBtnListeners() {

		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.onContinue();
			}
		});

		btnDeleteDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.deleteDeck();
			}
		});
	}

	// *********************** Getter Methods *********************** //

	/**
	 * Getter method for btnEditFlashCard
	 * 
	 * @return JButton object for btnEditFlashCard
	 */
	public JButton getBtnEditFlashCard() {
		return btnEditFlashCard;
	}

	/**
	 * Getter method for btnDeleteFlashCard
	 * 
	 * @return JButton object for btnDeleteFlashCard
	 */
	public JButton getBtnDeleteFlashCard() {
		return btnDeleteFlashCard;
	}

	/**
	 * Getter method for tableFlashCards
	 * 
	 * @return JTable object for tableFlashCards
	 */
	public JTable getTableFlashCards() {
		return tableFlashCards;
	}

	/**
	 * Getter method for panelFlashCards
	 * 
	 * @return JPanel object for panelFlashCards
	 */
	public JPanel getPanelFlashCards() {
		return panelFlashCards;
	}

	/**
	 * Getter method for textFieldName
	 * 
	 * @return JTextField object for textFieldName
	 */
	public JTextField getTextFieldName() {
		return textFieldName;
	}

	// ******************************************************************
	// Remove methods bellow once screen is extended by this class.

	/**
	 * Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}

	public void displayError(String msg) {
		textPaneErrorMsg.setText(msg);
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

	public void clearContainer(Container container) {
		container.removeAll();
		container.revalidate();
		container.repaint();
	}

	/**
	 * Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
	}
}
