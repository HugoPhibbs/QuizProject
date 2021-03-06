package core.coreObjects;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents a FlashCard
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 26/6/21
 * @since 25/6/21
 *
 */
public class FlashCard implements Serializable {

	/** String for the front text of a flash card */
	private String frontText;
	/** String for the back text of a flash card */
	private String backText;
	/** LocalDate for the next review date of this flashcard */
	private LocalDate nextReviewDate = null;
	/** int for the number of times this card has been reviewed */
	private int timesReviewed = 0;

	/**
	 * Constructor for a FlashCard object
	 * 
	 * @param frontText String for the front text of a flash card
	 * @param backText  String for the back text of a flash card
	 */
	public FlashCard(String frontText, String backText) {
		this.frontText = frontText;
		this.backText = backText;
	}

	// **************** Methods to check for equality ****************** //

	/**
	 * Determines if an object is equal to this FlashCard object.
	 * <p>
	 * If the object is an instance of a FlashCard, then it compares the front and
	 * back text with this FlashCard.
	 * <p>
	 * Used when adding a flashcard to a deck to make sure effectively duplicate
	 * FlashCards aren't added to the same deck
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof FlashCard) {
			FlashCard flashCard = (FlashCard) object;
			return (hasText(flashCard.getFrontText(), flashCard.getBackText()));
		} else {
			return false;
		}
	}

	/**
	 * Finds if a FlashCard has the same front text and back text as those that are
	 * inputed.
	 * 
	 * @param frontText String for the front text to be checked
	 * @param backText  String for the front text to be checked
	 * @return boolean if a FlashCard has the same front text and back text as those
	 *         that are inputed
	 */
	public boolean hasText(String frontText, String backText) {
		return (this.frontText.equals(frontText)) && (this.backText.equals(backText));
	}

	// **************** Methods for Quizzing ********************* //

	/**
	 * Sets the next review date of a flash card based on how many times that it has
	 * been seen
	 * <p>
	 * Intervals between reviews gradually increase in size, and are measured in
	 * days time
	 * <p>
	 * Called once a quiz ends.
	 * 
	 */
	public void updateNextReviewDate() {
		// The interval (days) for this card next to be seen in a quiz
		int reviewInterval = 0;
		switch (timesReviewed) {
			case 0:
				reviewInterval = 1;
				nextReviewDate = LocalDate.now(); // Create this as it hasn't been created before
				break;
			case 1:
				reviewInterval = 3;
				break;
			case 2:
				reviewInterval = 5;
				break;
			case 3:
				reviewInterval = 8;
				break;
			case 4:
				reviewInterval = 14;
				break;
			case 5:
				// Reviewed adequete number of times, no more reviews needed.
				reviewInterval = (int) Double.POSITIVE_INFINITY;
		}
		nextReviewDate = nextReviewDate.plusDays(reviewInterval);
		timesReviewed += 1;
	}

	/**
	 * Method to decide if this card is due to be be quizzed.
	 * <p>
	 * Always returns false if a card hasn't been seen. As this functionality is
	 * already covered in Deck.cardsToQuiz(int)
	 * 
	 * @param currentDate LocalDate object for the current date
	 * @return boolean for if a card should be quizzed or not
	 */
	public boolean isDue(LocalDate currentDate) {
		if (isNew()) {
			return false;
		} else if (currentDate.equals(nextReviewDate)) {
			// Card is due
			return true;
		} else {
			// Card is over due
			return (currentDate.isAfter(nextReviewDate));
		}
	}

	/**
	 * Method that determines if a FlashCard is new or not- i.e. hasn't been seen
	 * before
	 * 
	 * @return boolean value if a FlashCard is new or not
	 */
	public boolean isNew() {
		return (nextReviewDate == null && timesReviewed == 0);
	}

	// ************** Methods for representing FlashCards ***************** //
	/**
	 * Returns a String representation of a FlashCard object
	 * 
	 */
	@Override
	public String toString() {
		return String.format("FlashCard: (Front: %s; Back: %s)", frontText, backText);
	}

	/**
	 * Provides an Array representation of this FlashCard in the format {frontText,
	 * backText}
	 *
	 * @return String[] array representation of this FlashCard
	 */
	public String[] infoArray() {
		return new String[] { frontText, backText };
	}

	/**
	 * Provides an Array represenation details of the content that is returned by
	 * infoArray
	 * <p>
	 * Intended to be used to form column titles, hence the method name
	 * 
	 * @return String[] representation the format of the result of infoArray()
	 */
	public static String[] infoArrayHeaders() {
		return new String[] { "Front Text", "Back Text" };
	}

	// ****************** Getter methods ********************* //

	/**
	 * Getter method for the front text of a flash card
	 * 
	 * @return String for the front text of a flash card
	 */
	public String getFrontText() {
		return frontText;
	}

	/**
	 * Getter method for the back text of a flash card
	 * 
	 * @return String for the back text of a flash card
	 */
	public String getBackText() {
		return backText;
	}

	/**
	 * Getter method for the next review date of a flash card
	 * 
	 * @return LocalDate for the next review date of a flash card
	 */
	public LocalDate getNextReviewDate() {
		return nextReviewDate;
	}

	/**
	 * Getter method for the number of times this card has been reviewed
	 * 
	 * @return int value as described
	 */
	public int getTimesReviewed() {
		return timesReviewed;
	}

	// ******************* Setter methods *************** //

	/**
	 * Setter method for the next review date of a flash card
	 * 
	 * @param nextReviewDate LocalDate for the next review date to be set
	 */
	public void setNextReviewDate(LocalDate nextReviewDate) {
		this.nextReviewDate = nextReviewDate;
	}

	/**
	 * Setter method for the front text of a flash card
	 * 
	 * @param frontText String to be set as the front text of a flash card
	 */
	public void setFrontText(String frontText) {
		this.frontText = frontText;
	}

	/**
	 * Setter method for both the front and back text of a FlashCard
	 * 
	 * @param frontText String for the front text of a FlashCard
	 * @param backText  String for the back text of a FlashCard
	 */
	public void setText(String frontText, String backText) {
		this.frontText = frontText;
		this.backText = backText;
	}

	/**
	 * Setter method for the back text of a flash card
	 * 
	 * @param backText String to be set as the back text of a flash card
	 */
	public void setBackText(String backText) {
		this.backText = backText;
	}

	/**
	 * Setter method for the number of times this flash card has been reviewed
	 * 
	 * @param timesReviewed int for the number of times to be set
	 */
	public void setTimesReviewed(int timesReviewed) {
		this.timesReviewed = timesReviewed;
	}

}
