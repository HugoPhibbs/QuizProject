package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import coreLogic.Setup;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class SetupScreen {

	private JFrame frame;
	private JTextField textFieldEnterDirectory;
	private Setup setup;
	private JButton btnContinue;
	private JButton btnLoadSession;
	private JButton btnCreateSession;
	private JTextField textFieldEnterName;
	private JLabel lblNameError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SetupScreen window = new SetupScreen();
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
	public SetupScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setFont(new Font("Dialog", Font.BOLD, 12));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		createButtons();
		createLabels();
	}
	
	private void createLabels() {
		JLabel lblWelcome = new JLabel("Hello, welcome to Quizzer!");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWelcome.setBounds(85, 7, 263, 46);
		frame.getContentPane().add(lblWelcome);
		
		textFieldEnterDirectory = new JTextField();
		textFieldEnterDirectory.setBounds(62, 74, 286, 22);
		frame.getContentPane().add(textFieldEnterDirectory);
		textFieldEnterDirectory.setColumns(10);
		
		JLabel lblEnterDirectory = new JLabel("Please enter a working directory!");
		lblEnterDirectory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterDirectory.setBounds(119, 52, 186, 15);
		frame.getContentPane().add(lblEnterDirectory);
		
		textFieldEnterName = new JTextField();
		textFieldEnterName.setBounds(165, 157, 66, 22);
		frame.getContentPane().add(textFieldEnterName);
		textFieldEnterName.setColumns(10);
		
		JLabel lblEnterName = new JLabel("Please enter your name!");
		lblEnterName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblEnterName.setBounds(143, 127, 129, 23);
		frame.getContentPane().add(lblEnterName);
		
		lblNameError= new JLabel();
		lblNameError.setForeground(Color.RED);
		lblNameError.setFont(new Font("Dialog", Font.PLAIN, 7));
		lblNameError.setBounds(166, 222, 139, 16);
		frame.getContentPane().add(lblNameError);
		
	}
	
	private void createButtons() {
		
		JButton btnNewButton = new JButton("Enter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setup = new Setup(textFieldEnterDirectory.getText());
				btnCreateSession.setEnabled(true );
				btnLoadSession.setEnabled(true);
			}
		});
		btnNewButton.setBounds(165, 103, 69, 17);
		frame.getContentPane().add(btnNewButton);
		
		this.btnCreateSession = new JButton("Create Session");
		btnCreateSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldEnterName.getText();
				setup.createSession(username);
				btnContinue.setEnabled(true);
			}
		});
		btnCreateSession.setBounds(62, 193, 139, 17);
		frame.getContentPane().add(btnCreateSession);
		btnCreateSession.setEnabled(false);
		
		this.btnLoadSession = new JButton("Load Session");
		btnLoadSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName = textFieldEnterName.getText();
				try{
					setup.loadSession(userName);
					btnContinue.setEnabled(true);
				}
				catch (FileNotFoundException fnfe){
					lblNameError.setText(String.format("Session for '%s' cannot be found!", userName));
				}
				
			}
		});
		btnLoadSession.setBounds(219, 193, 148, 17);
		frame.getContentPane().add(btnLoadSession);
		btnLoadSession.setEnabled(false);
		
		this.btnContinue = new JButton("Continue");
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setup.onSetupFinished();
				frame.dispose(); // TODO implement this into Screen later, when design is finished!
			}
		});
		btnContinue.setBounds(312, 241, 119, 17);
		frame.getContentPane().add(btnContinue);
		btnContinue.setEnabled(false);
	}
}
