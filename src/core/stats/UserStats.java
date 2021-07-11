package core.stats;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Overall user statistics for a user's quiz history.
 * <p>
 * Holds statistics for all the quizzes that a User has ever done
 * <p>
 * Creates graphs if requested representing trends from it's ArrayList of
 * QuizStats entries
 * 
 * @author Tom Berry
 * @author Hugo Phibbs
 * @version 29/06/2021
 * @since 28/07/2021
 *
 */
public class UserStats implements Serializable {

	/**
	 * ArrayList containing QuizStats for all the quizzes that a User has ever
	 * completed
	 */
	ArrayList<QuizStats> quizStatsEntries = new ArrayList<QuizStats>();

	/** Adds a QuizStats entry to quizStatsEntries */
	public void addQuizStatsEntry(QuizStats quizStats) {
		quizStatsEntries.add(quizStats);

		// TODO implement further - may be further things needed idk
	}

	public void createGraphs() {
		// TODO implement
		/*
		 * May want to split this up into different methods eg
		 * createAverageCorrectGraph(), createAverageWrongGraph()
		 */
	}

}
