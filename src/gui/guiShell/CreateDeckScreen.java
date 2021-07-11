package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;

import core.coreLogic.DeckManager;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Represents a Screen to create a new Deck
 * 
 * @author Hugo Phibbs
 *
 */
public class CreateDeckScreen {

	private JFrame frame;
	/** TextField for entering the name of a new Deck */
	private JTextField textFieldName;
	/** TextField for entering the description of a new Deck */
	private JTextField textFieldDescription;
	/** DeckManager object for this application */
	private DeckManager deckManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateDeckScreen window = new CreateDeckScreen();
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
	public CreateDeckScreen() {
		initialize();
	}

	public CreateDeckScreen(DeckManager deckManager) {
		this.deckManager = deckManager;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		createDetailsPanel();
		createContinuePanel();
		createMiscComponents();
	}

	/**
	 * Creates components contains in panelDeckDetails
	 * 
	 */
	private void createDetailsPanel() {
		JPanel panelDeckDetails = new JPanel();
		panelDeckDetails.setBounds(12, 53, 213, 157);
		frame.getContentPane().add(panelDeckDetails);
		panelDeckDetails.setLayout(null);

		textFieldName = new JTextField();
		textFieldName.setBounds(102, 21, 85, 22);
		panelDeckDetails.add(textFieldName);
		textFieldName.setColumns(10);

		textFieldDescription = new JTextField();
		textFieldDescription.setBounds(5, 74, 196, 70);
		panelDeckDetails.add(textFieldDescription);
		textFieldDescription.setColumns(10);

		JLabel lblName = new JLabel("Deck name:");
		lblName.setBounds(5, 24, 85, 16);
		panelDeckDetails.add(lblName);

		JLabel lblDescription = new JLabel("Description:");
		lblDescription.setBounds(5, 56, 92, 16);
		panelDeckDetails.add(lblDescription);
	}

	/**
	 * Creates components contains in panelContinue
	 * 
	 */
	private void createContinuePanel() {
		JPanel panelContinue = new JPanel();
		panelContinue.setBounds(237, 53, 185, 157);
		frame.getContentPane().add(panelContinue);
		panelContinue.setLayout(null);

		JButton btnCreateDeck = new JButton("Create Deck");
		btnCreateDeck.setBounds(30, 119, 128, 25);
		panelContinue.add(btnCreateDeck);
		btnCreateDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createDeck();
			}
		});

		JTextPane textPaneErrorMsg = new JTextPane();
		textPaneErrorMsg.setBounds(17, 13, 156, 93);
		panelContinue.add(textPaneErrorMsg);
	}

	/**
	 * Creates various components that don't belong in a panel
	 * 
	 */
	private void createMiscComponents() {
		JLabel lblTitle = new JLabel("Creating a deck");
		lblTitle.setBounds(154, 24, 96, 16);
		frame.getContentPane().add(lblTitle);
	}

	private void createDeck() {
		// TODO implement
	}
}
