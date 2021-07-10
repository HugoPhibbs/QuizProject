package core.stats;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

/** Quiz statistics for an individual quiz completed by the user.
 * 
 * @author Tom Berry
 * @author Hugo Phibbs
 * @version 28/06/2021
 * @since 28/07/2021
 *
 */
public class QuizStats implements Serializable {
	
	/** Number of times the user selected 'again' during a quiz. */
	private int numAgain;
	
	/** Number of times the user selected 'final' during a quiz. */
	private int numFinal;
	
	/** Percentage of total cards looked at during session that user selected 'final'. */
	private int percentCorrect;
	
	/** Time taken for the user to finish a quiz (in seconds). */
	private int timeTaken;
	 
	
	/** Empty constructor until this class is fully implemented
	 * need it for FlashCardQuiz
	 * TODO remove later
	 */
	public QuizStats() {}
	
	/** Constructor for a QuizStats object.
	 * 
	 * @param numAgain
	 * @param numFinal 
	 * @param percentCorrect
	 * @param timeTaken
	 */
	public QuizStats(int numAgain, int numFinal, int percentCorrect, int timeTaken) {
		this.numAgain = 
		this.numFinal = 
		this.percentCorrect = calcPercentCorrect(this.numAgain, this.numFinal);
		this.timeTaken = calcTimeTaken(startTime, endTime);
	}
	
	/** Method calculating percentage of cards user got correct during a quiz.
	 * 
	 * @param numAgain number of times user selected 'again' in a quiz.
	 * @param numFinal number of times user selected 'final' in a quiz.
	 */
	public int calcPercentCorrect(int numAgain, int numFinal) {
		return numFinal/(numAgain + numFinal);
	}
	
	/** Method calculating time taken for user to complete a quiz.
	 * 
	 * @param startTime time the user started the quiz.
	 * @param endTime time the user ended the quiz.
	 */
	public int calcTimeTaken(LocalTime startTime, LocalTime endTime) {
		Duration duration = Duration.between(startTime, endTime);
		return (int) duration.toSeconds();
	}
	
	//********************************* GETTERS AND SETTERS ****************************************
	
	/** Getter method for numAgain.
	 * 
	 * @return int representing number of times 'again' was selected during a quiz.
	 */
	public int getNumAgain() {
		return numAgain;
	}
	
	/** Getter method for numFinal.
	 * 
	 * @return int representing number of times 'final' was selected during a quiz.
	 */
	public int getNumFinal() {
		return numFinal;
	}
	
	/** Getter method for percentCorrect.
	 * 
	 * @return int representing percentage of total cards looked at during session that user selected 'final'.
	 */
	public int getPercentCorrect() {
		return percentCorrect;
	}
	
	/** Getter method for timeTaken.
	 * 
	 * @return int representing time taken to complete a quiz.
	 */
	public int getTimeTaken() {
		return timeTaken;
	}
	
	/** Setter method for numAgain.
	 * 
	 * @param numAgain int for the numAgain field to be set.
	 */
	public void setNumAgain(int numAgain) {
		this.numAgain = numAgain;
	}
	
	/** Setter method for numFinal.
	 * 
	 * @param numFinal int for the numFinal field to be set.
	 */
	public void setNumFinal(int numFinal) {
		this.numFinal = numFinal;
	}
	
	/** Setter method for percentCorrect.
	 * 
	 * @param percentCorrect int for the percentCorrect field to be set.
	 */
	public void setPercentCorrect(int percentCorrect) {
		this.percentCorrect = percentCorrect;
	}
	
	/** Setter method for timeTaken.
	 * 
	 * @param timeTaken int for the timeTaken field to be set.
	 */
	public void setTimeTaken(int timeTaken) {
		this.timeTaken = timeTaken;
	}
}
