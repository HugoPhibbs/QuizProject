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

public class EditDeckScreen {
	
	JFrame frame;
	private JTable table;
	private JTextField textField;

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

	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize() {
		frame = new JFrame();
		frame.getContentPane().setLayout(null);
		frame.setBounds(100, 100, 900, 900);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(63, 127, 742, 467);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		table = new JTable();
		table.setBounds(12, 13, 718, 441);
		panel.add(table);
		
		JLabel lblNewLabel = new JLabel("Editing <deckName> ");
		lblNewLabel.setBounds(335, 86, 138, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(367, 622, 182, 129);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Add FlashCard");
		btnNewButton_1.setBounds(12, 13, 160, 28);
		panel_1.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("Edit FlashCard");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(12, 54, 160, 25);
		panel_1.add(btnNewButton);
		
		JButton btnNewButton_3 = new JButton("Delete FlashCard");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_3.setBounds(12, 92, 160, 25);
		panel_1.add(btnNewButton_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(63, 622, 296, 129);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Change Deck name");
		lblNewLabel_1.setBounds(97, 13, 124, 16);
		panel_2.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(12, 37, 272, 22);
		panel_2.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_4 = new JButton("Delete Deck");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_4.setBounds(12, 91, 272, 25);
		panel_2.add(btnNewButton_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(561, 622, 244, 129);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("Continue");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setBounds(135, 90, 97, 25);
		panel_3.add(btnNewButton_2);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(12, 13, 220, 64);
		panel_3.add(textPane);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});


	}
}
