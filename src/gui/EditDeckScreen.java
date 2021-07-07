package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import coreLogic.DeckManager;
import coreObjects.Deck;

public class EditDeckScreen {
	
	JFrame frame;
	private JTable tableFlashCards;
	private JTextField textFieldName;
	private Deck deck;
	private DeckManager deckManager;
	private JTextField textFieldDescription;

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
	
	public EditDeckScreen(Deck deck, DeckManager deckManager) {
		this.deck = deck;
		this.deckManager = deckManager;
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 900, 900);
		frame.getContentPane().setLayout(null);
		
		JPanel panelFlashCards = new JPanel();
		panelFlashCards.setBounds(63, 101, 742, 493);
		frame.getContentPane().add(panelFlashCards);
		panelFlashCards.setLayout(null);
		
		tableFlashCards = new JTable();
		tableFlashCards.setBounds(12, 39, 718, 441);
		panelFlashCards.add(tableFlashCards);
		
		JLabel lblFlashCards = new JLabel("FlashCards");
		lblFlashCards.setBounds(309, 13, 86, 16);
		panelFlashCards.add(lblFlashCards);
		
		JLabel lblTitle = new JLabel("Editing <deckName> ");
		lblTitle.setBounds(341, 60, 138, 28);
		frame.getContentPane().add(lblTitle);
		
		JPanel panelFlashCardOptions = new JPanel();
		panelFlashCardOptions.setBounds(367, 622, 182, 129);
		frame.getContentPane().add(panelFlashCardOptions);
		panelFlashCardOptions.setLayout(null);
		
		JButton btnAddFlashCard = new JButton("Add FlashCard");
		btnAddFlashCard.setBounds(12, 13, 160, 28);
		panelFlashCardOptions.add(btnAddFlashCard);
		
		JButton btnEditFlashCard = new JButton("Edit FlashCard");
		btnEditFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEditFlashCard.setBounds(12, 54, 160, 25);
		panelFlashCardOptions.add(btnEditFlashCard);
		
		JButton btnDeleteFlashCard = new JButton("Delete FlashCard");
		btnDeleteFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDeleteFlashCard.setBounds(12, 92, 160, 25);
		panelFlashCardOptions.add(btnDeleteFlashCard);
		
		JPanel panelDetails = new JPanel();
		panelDetails.setBounds(63, 622, 296, 129);
		frame.getContentPane().add(panelDetails);
		panelDetails.setLayout(null);
		
		JLabel lblName = new JLabel("Deck name:");
		lblName.setBounds(12, 13, 124, 16);
		panelDetails.add(lblName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(138, 10, 146, 22);
		panelDetails.add(textFieldName);
		textFieldName.setColumns(10);
		
		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(12, 60, 272, 56);
		panelDetails.add(textFieldDescription);
		textFieldDescription.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(12, 42, 110, 16);
		panelDetails.add(lblDescription);
		
		JPanel panelContinue = new JPanel();
		panelContinue.setBounds(561, 622, 244, 129);
		frame.getContentPane().add(panelContinue);
		panelContinue.setLayout(null);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnContinue.setBounds(129, 90, 103, 25);
		panelContinue.add(btnContinue);
		
		JTextPane textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(12, 13, 220, 64);
		panelContinue.add(textPaneErrorMsg);
		
		JButton btnDeleteDeck = new JButton("Delete Deck");
		btnDeleteDeck.setBounds(12, 90, 103, 25);
		panelContinue.add(btnDeleteDeck);
		btnDeleteDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAddFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});


	}
}
