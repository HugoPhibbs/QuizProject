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
	private JLabel lblConfigSessionError;
	/** JPanel to contain components relating to entering a user name for a session */
	private JPanel panelEnterName;
	/** JPanel to contain components relating to entering a directory for loading or creating a session */
	private JPanel panelEnterDirectory;
	/** JButton to enter a directory to create or load a session to */
	private JButton btnEnterDirectory;

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
	
		createComponents();
	}

	// ******************** Creating Components  ******************* //

	/** Calls methods to create components for this Screen */
	private void createComponents() {
		createMiscComponents();
		createEnterNamePanel();
		createConfigSessionPanel();
		createEnterDirectoryPanel();
	}

	/** Creates and fills and Panel with components relating to entering a 
	 * working directory for this application */
	private void createEnterDirectoryPanel(){
		panelEnterDirectory = new JPanel();
		panelEnterDirectory.setBounds(24, 46, 365, 75);
		frame.getContentPane().add(panelEnterDirectory);
		panelEnterDirectory.setLayout(null);
		
		textFieldEnterDirectory = new JTextField();
		textFieldEnterDirectory.setBounds(43, 22, 286, 22);
		panelEnterDirectory.add(textFieldEnterDirectory);
		textFieldEnterDirectory.setColumns(10);
		
		JLabel lblEnterDirectory = new JLabel("Please enter a working directory!");
		lblEnterDirectory.setBounds(91, 0, 186, 15);
		panelEnterDirectory.add(lblEnterDirectory);
		lblEnterDirectory.setFont(new Font("Tahoma", Font.PLAIN, 11));

		btnEnterDirectory = new JButton("Enter");
		btnEnterDirectory.setBounds(139, 56, 69, 17);
		panelEnterDirectory.add(btnEnterDirectory);

		addBtnEnterDirectoryListener();
	}

	/** Creates and fill a Panel with components relating to creating or loading a session */
	private void createConfigSessionPanel(){
		JPanel panelConfigSession = new JPanel();
		panelConfigSession.setBounds(49, 191, 352, 33);
		frame.getContentPane().add(panelConfigSession);
		panelConfigSession.setLayout(null);
		
		btnCreateSession = new JButton("Create Session");
		btnCreateSession.setBounds(12, 12, 139, 17);
		panelConfigSession.add(btnCreateSession);
		btnCreateSession.setEnabled(false);
		
		btnLoadSession = new JButton("Load Session");
		btnLoadSession.setBounds(173, 12, 148, 17);
		panelConfigSession.add(btnLoadSession);
		btnLoadSession.setEnabled(false);

		addConfigSessionBtnListeners();
	}

	/** Creates and fills Panel for entering a user's name into to load or create a session */
	private void createEnterNamePanel(){
		panelEnterName = new JPanel();
		panelEnterName.setBounds(127, 132, 139, 58);
		frame.getContentPane().add(panelEnterName);
		panelEnterName.setLayout(null);
		
		JLabel lblEnterName = new JLabel("Please enter your name!");
		lblEnterName.setBounds(12, 0, 129, 23);
		panelEnterName.add(lblEnterName);
		lblEnterName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		textFieldEnterName = new JTextField();
		textFieldEnterName.setBounds(12, 24, 119, 22);
		panelEnterName.add(textFieldEnterName);
		textFieldEnterName.setColumns(10);
	}
	
	/** Creates miscelanous components that aren't contained in a Panel */
	private void createMiscComponents(){
		JLabel lblWelcome = new JLabel("Hello, welcome to Quizzer!");
		lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblWelcome.setBounds(85, 7, 263, 46);
		frame.getContentPane().add(lblWelcome);
		
		lblConfigSessionError= new JLabel();
		lblConfigSessionError.setForeground(Color.RED);
		lblConfigSessionError.setFont(new Font("Dialog", Font.PLAIN, 7));
		lblConfigSessionError.setBounds(166, 222, 139, 16);
		frame.getContentPane().add(lblConfigSessionError);

		btnContinue = new JButton("Continue");
		btnContinue.setBounds(312, 241, 119, 17);
		frame.getContentPane().add(btnContinue);
		btnContinue.setEnabled(false);
		addBtnContinueListener();
	}

	// ****************** Adding Listeners to Components ********************* //

	/** Adds an action listener to btnEnterDirectory */
	private void addBtnEnterDirectoryListener(){
		btnEnterDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				directoryEntered();
			}
		});
	}

	/** Adds Action Listeners to buttons relating to loading and creatig a session */
	private void addConfigSessionBtnListeners(){
		btnCreateSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSession();;
			}
		});

		btnLoadSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadSession();
			}
		});
	}

	/** Adds an Action Listener to btnContinue */
	private void addBtnContinueListener() {
		btnContinue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onContinue();
			}
		});
	}

	// *********************** Handling Listener Events ************************* //

	/** Handles the entering of a Directory */
	private void directoryEntered(){
		setup = new Setup(textFieldEnterDirectory.getText());
		btnCreateSession.setEnabled(true );
		btnLoadSession.setEnabled(true);
	}

	/** Handles pressing of btnCreateSession */
	private void createSession(){
		try {
			setup.createSession(userName());
			btnContinue.setEnabled(true);
		}
		catch (IllegalArgumentException iae){
			displayConfigSessionError(iae.getMessage());
		}
	}

	/** Handles pressing of btnLoadSession 
	 * Tries to load a session, displays an error in lblConfigSessionError if FileNotFoundException is caught */
	private void loadSession(){
		try{
			setup.loadSession(userName());
			btnContinue.setEnabled(true);
		}
		catch (FileNotFoundException fnfe){
			displayConfigSessionError(fnfe.getMessage());
		}
	}

	/** Handles pressing of btnContinue */
	private void onContinue(){
		setup.onSetupFinished();
		frame.dispose(); // TODO implement this into Screen later, when design is finished!
	}

	/** Displays an error to user relating to configurating a session
	 * , that is; loading or creating
	 * 
	 * @param msg String for error message to be displayed
	 */
	private void displayConfigSessionError(String msg){
		lblConfigSessionError.setText(msg);
	}

	/** Returns the user name currently entered to load a session for 
	 * 
	 * @return String for the user name currently entered 
	 */
	private String userName(){
		return textFieldEnterName.getText();
	}
}
