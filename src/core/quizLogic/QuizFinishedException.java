package core.quizLogic;

/**
 * Exception to be thrown when a quiz is finished
 * <p>
 * Made into an individual method because it is quite specific from other Exceptions
 * 
 * @author Hugo Phibbs
 *
 */
public class QuizFinishedException extends Exception {

	/**
	 * Constructor for QuizFinishedException
	 */
    QuizFinishedException() {
    }

    /**
	 * Constructor for QuizFinishedException
	 * 
	 * @param msg String for the error message to be displayed to a user
	 */
    QuizFinishedException(String msg) {
        super(msg);
    }
}
