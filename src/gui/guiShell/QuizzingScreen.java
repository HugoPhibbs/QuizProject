package gui.guiShell;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import core.coreLogic.FlashCardQuiz;
import gui.guiLogic.QuizzingScreenLogic;
import java.awt.Font;
import java.awt.Color;

/**
 * Represents a Screen to quiz a user
 * 
 * @author Hugo Phibbs
 *
 */
public class QuizzingScreen {

	JFrame frame;

	/** JBUtton that finishes a quiz when pressed */
	private JButton btnFinishQuiz;
	/** JBUtton that handles event of a FlashCard being 'ok' */
	private JButton btnFlashCardOk;
	/** JButton for when a user wants to see a FlashCard again */
	private JButton btnFlashCardAgain;
	/** JBUtton to flip the current FlashCard */
	private JButton btnFlipFlashCard;
	/** JTextPane to show the text of the current side of a Flashcard */
	private JTextPane textPaneCurrentSide;

	/** QuizzingScreenLogic object to control logic operations of this Screen */
	private QuizzingScreenLogic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizzingScreen window = new QuizzingScreen(null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor for QuizzingScreen.
	 * 
	 * @param quizzingScreenLogic QuizzingScreenLogic object that controls this
	 *                            Screen
	 */
	public QuizzingScreen(QuizzingScreenLogic quizzingScreenLogic) {
		// super("Quizzing", quizzingScreenLogic, false);
		super(); // Delete later for Screen, use above
		this.logic = quizzingScreenLogic;
		// initialize(); // remove comment to design screen
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);

		configFrame();
		createComponents();
	}

	// @Override

	// @Override
	protected void configFrame() {
		frame.setBounds(100, 100, 493, 367);
	}

	// *********************** Creating Components ************************ //

	// @Override
	private void createComponents() {
		createContentPanel();
		createMiscComponents();
		createOptionsPanel();
	}

	/**
	 * Creates components contained in the options panel
	 * 
	 */
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(85, 220, 307, 74);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);

		btnFlashCardOk = new JButton("Ok");
		btnFlashCardOk.setForeground(new Color(50, 205, 50));
		btnFlashCardOk.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFlashCardOk.setBounds(171, 37, 97, 25);
		panelOptions.add(btnFlashCardOk);

		btnFlashCardAgain = new JButton("Again");
		btnFlashCardAgain.setForeground(Color.RED);
		btnFlashCardAgain.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFlashCardAgain.setBounds(38, 37, 97, 25);
		panelOptions.add(btnFlashCardAgain);

		btnFlipFlashCard = new JButton("Flip FlashCard");
		btnFlipFlashCard.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFlipFlashCard.setBounds(84, 6, 140, 25);
		panelOptions.add(btnFlipFlashCard);

		addPanelOptionsBtnListeners();
	}

	/** Creates and fills panelContent with components */
	private void createContentPanel() {
		JPanel panelContent = new JPanel();
		panelContent.setBounds(84, 95, 307, 119);
		frame.getContentPane().add(panelContent);
		panelContent.setLayout(null);

		textPaneCurrentSide = new JTextPane();
		textPaneCurrentSide.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textPaneCurrentSide.setBounds(5, 6, 297, 107);
		panelContent.add(textPaneCurrentSide);
	}

	/**
	 * Creates various components that aren't contained in a panel
	 * 
	 */
	private void createMiscComponents() {
		JLabel lblCurrentDeck = new JLabel(lblCurrentDeckText());
		lblCurrentDeck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCurrentDeck.setBounds(104, 59, 283, 30);
		frame.getContentPane().add(lblCurrentDeck);

		btnFinishQuiz = new JButton("Finish Quiz");
		btnFinishQuiz.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFinishQuiz.setBounds(379, 300, 97, 25);
		frame.getContentPane().add(btnFinishQuiz);
		addBtnFinishQuizListener();
	}

	/**
	 * Finds the text that should be entered in lblCurrentDeck
	 * 
	 * @return String as described
	 */
	private String lblCurrentDeckText() {
		return String.format("Currently being quizzed on %s", logic.deckName());
	}

	// ***************** Adding Listeners to components ********************* //

	/** Adds Action Listeners to buttons in panelOptions */
	private void addPanelOptionsBtnListeners() {
		btnFlashCardOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.flashCardOk();
			}
		});

		btnFlashCardAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.flashCardAgain();
			}
		});

		btnFlipFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.flashCardFlip();
			}
		});
	}

	/** Adds an Action listener to btnFinishQuiz */
	private void addBtnFinishQuizListener() {
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.finishQuiz();
			}
		});
	}

	// *********** Getter methods ************ //

	/**
	 * Getter method for textPaneCurrentSide
	 * 
	 * @return JTextPane object
	 */
	public JTextPane getTextPaneCurrentSide() {
		return textPaneCurrentSide;
	}

	// ***************************
	// Remove bellow once Screen is extended

	/**
	 * Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}

}
