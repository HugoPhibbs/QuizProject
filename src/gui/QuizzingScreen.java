package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextPane;

import coreLogic.FlashCardQuiz;

/** Represents a Screen to quiz a user
 * 
 * @author Hugo Phibbs
 *
 */
public class QuizzingScreen {
	
	JFrame frame;
	/** FlashCardQuiz object holding objects necessary for this QuizzingScreen */
	FlashCardQuiz flashCardQuiz;
	private JButton btnFinishQuiz;

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
		this.flashCardQuiz = flashCardQuiz;
	}
	
	/** Creates components contained in the options panel
	 * 
	 */
	private void createOptionsPanel() {
		JPanel panelOptions = new JPanel();
		panelOptions.setBounds(157, 405, 516, 122);
		frame.getContentPane().add(panelOptions);
		panelOptions.setLayout(null);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flashCardOk();
			}
		});
		btnOk.setBounds(407, 63, 97, 25);
		panelOptions.add(btnOk);
		
		JButton btnAgain = new JButton("AGAIN");
		btnAgain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flashCardAgain();
			}
		});
		btnAgain.setBounds(12, 63, 97, 25);
		panelOptions.add(btnAgain);
		
		JButton btnFlipFlashCard = new JButton("Flip FlashCard");
		btnFlipFlashCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flashCardFlip();
			}
		});
		btnFlipFlashCard.setBounds(186, 13, 140, 25);
		panelOptions.add(btnFlipFlashCard);
		
	}
	
	/** Creates components for the content panel
	 * 
	 */
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
		btnFinishQuiz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finishQuiz();
			}
		});
		btnFinishQuiz.setBounds(761, 515, 97, 25);
		frame.getContentPane().add(btnFinishQuiz);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 886, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		createContentPanel();
		createMiscComponents();
		createOptionsPanel();
	}
	
	private void flashCardFlip() {
		// TODO implement
	}
	
	private void flashCardAgain() {
		// TODO implement
	}
	
	private void flashCardOk() {
		// TODO implement
	}
	
	private void finishQuiz() {
		// TODO implement
	}
}
