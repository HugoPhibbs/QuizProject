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

/** Represents a Screen to quiz a user
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
	
	/** QuizzingScreenLogic object to control logic operations of this Screen */
	private QuizzingScreenLogic logic;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuizzingScreen window = new QuizzingScreen();
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
	public QuizzingScreen() {
		super();
		initialize();
	}
	
	public QuizzingScreen(FlashCardQuiz flashCardQuiz) {
		super();
		initialize();
		logic = new QuizzingScreenLogic(flashCardQuiz, this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 886, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		createComponents();
	}

	// *********************** Creating Components ************************ // 

	/** Calls methods to create components for this Screen */ 
	private void createComponents(){
		createContentPanel();
		createMiscComponents();
		createOptionsPanel();
	}
	
	/** Creates components contained in the options panel
	 * 
	 */
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(157, 405, 516, 122);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);
		
		btnFlashCardOk = new JButton("OK");
		btnFlashCardOk.setBounds(407, 63, 97, 25);
		panelOptions.add(btnFlashCardOk);
		
		btnFlashCardAgain = new JButton("AGAIN");
		btnFlashCardAgain.setBounds(12, 63, 97, 25);
		panelOptions.add(btnFlashCardAgain);
		
		btnFlipFlashCard = new JButton("Flip FlashCard");
		btnFlipFlashCard.setBounds(186, 13, 140, 25);
		panelOptions.add(btnFlipFlashCard);

		addPanelOptionsBtnListeners();
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
	
	/** Creates and fills panelContent with components */
	private void createContentPanel() {
		JPanel panelContent = new JPanel();
		panelContent.setBounds(157, 94, 516, 285);
		frame.getContentPane().add(panelContent);
		panelContent.setLayout(null);
		
		JTextPane textPaneCurrentSide = new JTextPane();
		textPaneCurrentSide.setText("<current text side of FlashCard >");
		textPaneCurrentSide.setBounds(84, 126, 341, 45);
		panelContent.add(textPaneCurrentSide);
	}
	
	/** Creates various components that aren't contained in a panel
	 * 
	 */
	private void createMiscComponents() {
		JLabel lblCurrentDeck = new JLabel("Currently being quizzed on <deckName>");
		lblCurrentDeck.setBounds(283, 65, 233, 16);
		frame.getContentPane().add(lblCurrentDeck);
		
		btnFinishQuiz = new JButton("Finish Quiz");
		btnFinishQuiz.setBounds(761, 515, 97, 25);
		frame.getContentPane().add(btnFinishQuiz);
		addBtnFinishQuizListener();
	}

	private void addBtnFinishQuizListener(){
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logic.finishQuiz();
			}
		});
	}

	// ****************** Handling Listener Events ******************* // 
	

}
