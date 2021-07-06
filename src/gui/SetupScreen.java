package gui;

import java.awt.EventQueue; 

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import setup.Setup;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPanel;

/** Screen for setting up the application
 * <p>
 * Handles selecting the working/saving directory of this application and either creating
 * or loading a previous saved session for a name of a user.
 * 
 * @author Hugo Phibbs
 *
 */
public class SetupScreen {

	private JFrame frame;
	
	/** JTextField for entering the working directory of this application */
	private JTextField textFieldEnterDirectory;
	/** Setup class for starting this application */
	private Setup setup;
	/** JButton to close from this screen and progress to the main screen, only progresses if it is permissable */
	private JButton btnContinue;
	/** JBUtton to load a session for the name of a user */
	private JButton btnLoadSession;
	/** JButton to create a session for the name of a user */
	private JButton btnCreateSession;
	/** JTextField to enter the name of a user */
	private JTextField textFieldEnterName;
	/** JLabel to display any errors to a user */
	private JLabel lblNameError;
	private JPanel panel_1;
	private JPanel panel_2;

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
		
		lblNameError= new JLabel();
		lblNameError.setForeground(Color.RED);
		lblNameError.setFont(new Font("Dialog", Font.PLAIN, 7));
		lblNameError.setBounds(166, 222, 139, 16);
		frame.getContentPane().add(lblNameError);
		
		JPanel panel = new JPanel();
		panel.setBounds(49, 191, 352, 33);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		this.btnCreateSession = new JButton("Create Session");
		btnCreateSession.setBounds(12, 12, 139, 17);
		panel.add(btnCreateSession);
		btnCreateSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textFieldEnterName.getText();
				setup.createSession(username);
				btnContinue.setEnabled(true);
			}
		});
		btnCreateSession.setEnabled(false);
		
		this.btnLoadSession = new JButton("Load Session");
		btnLoadSession.setBounds(173, 12, 148, 17);
		panel.add(btnLoadSession);
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
		btnLoadSession.setEnabled(false);
		
		panel_1 = new JPanel();
		panel_1.setBounds(127, 132, 139, 58);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblEnterName = new JLabel("Please enter your name!");
		lblEnterName.setBounds(12, 0, 129, 23);
		panel_1.add(lblEnterName);
		lblEnterName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textFieldEnterName = new JTextField();
		textFieldEnterName.setBounds(12, 24, 119, 22);
		panel_1.add(textFieldEnterName);
		textFieldEnterName.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setBounds(24, 46, 365, 75);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("Enter");
		btnNewButton.setBounds(139, 56, 69, 17);
		panel_2.add(btnNewButton);
		
		textFieldEnterDirectory = new JTextField();
		textFieldEnterDirectory.setBounds(43, 22, 286, 22);
		panel_2.add(textFieldEnterDirectory);
		textFieldEnterDirectory.setColumns(10);
		
		JLabel lblEnterDirectory = new JLabel("Please enter a working directory!");
		lblEnterDirectory.setBounds(91, 0, 186, 15);
		panel_2.add(lblEnterDirectory);
		lblEnterDirectory.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setup = new Setup(textFieldEnterDirectory.getText());
				btnCreateSession.setEnabled(true );
				btnLoadSession.setEnabled(true);
			}
		});
		
	}
	
	private void createButtons() {
		
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
