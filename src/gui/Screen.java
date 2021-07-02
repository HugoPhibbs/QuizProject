package gui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import setup.Setup;

/** Represents a Screen object for GUI
 * 
 * @author Hugo Phibbs and Jordan Vegar
 * @version 25/6/21
 * @since 18/5/21
 * 
 */
public abstract class Screen {
	
	/** Frame object for this screen **/
	JFrame frame;
	
	/** Empty Constructor for Screen, used until extensions of Screen need full constructor
	 * 
	 */
	Screen(){
		// TODO Delete later!
	}
	
	protected Screen(String title) {
		this.frame = new JFrame();
		frame.setTitle(title);
		frame.getContentPane().setBackground(new Color(255, 222, 173));
		setFrameCharacteristics();
	}
	
	/** Sets the characteristics of the frame that are common to all screens. */
	private void setFrameCharacteristics() {
		// Prevent the user from quiting immediately when quit is clicked.
		// Code copied from Rocket Manager Example
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmQuit();
			}
		});
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	}
	
	/** This is where the bounds of your frame will be set and methods which create the components
	 * will be called. Is called at the end of the constructor method.
	 *  */
	protected abstract void initialize();
	
	/** Disposes of the Screen's frame.
	 * 
	 */
	public void quit() {
		frame.dispose();
	}
	
	/** Makes the screen visible to the user.
	 * 
	 */
	public void show() {
		frame.setVisible(true);
	}
	
	/** Displays a Dialog to allow the user to confirm whether they would like to quit
	 * 
	 * @return Boolean true if would like to quit, else false
	 */
	protected void confirmQuit() {
		int selection = JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?",
                "Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

         if (selection == JOptionPane.YES_OPTION) {
        	 quit();
         }
	}
}
