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

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 886, 592);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(157, 405, 516, 122);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("OK");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(407, 63, 97, 25);
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("AGAIN");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(12, 63, 97, 25);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_1 = new JButton("Flip FlashCard");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(186, 13, 140, 25);
		panel.add(btnNewButton_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(157, 94, 516, 285);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("<current text side of FlashCard >");
		textPane.setBounds(84, 126, 341, 45);
		panel_1.add(textPane);
		
		JLabel lblNewLabel = new JLabel("Currently being quizzed on <deckName>");
		lblNewLabel.setBounds(283, 65, 233, 16);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Finish Quiz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(761, 515, 97, 25);
		frame.getContentPane().add(btnNewButton);
	}

}
