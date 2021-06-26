package coreObjects;

import java.time.LocalDate;

/** Represents a FlashCard
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 26/6/21
 * @since 25/6/21
 *
 */
public class FlashCard {
	
	/** String for the front text of a flash card */
	private String frontText;
	/** String for the back text of a flash card */
	private String backText;
	/** LocalDate for the next review date of this flashcard */
	private LocalDate nextReviewDate = null;
	
	
	/** Constructor for a FlashCard object 
	 * 
	 * @param frontText String for the front text of a flash card
	 * @param backText String for the back text of a flash card
	 */
	public FlashCard(String frontText, String backText){
		this.frontText = frontText;
		this.backText = backText;
	}
	
	/** Method to decide if this card is due to be be quizzed.
	 * Always returns false if a card hasnt been seen. As this functionality is
	 * already covered in Deck.cardsToQuiz(int)
	 * 
	 * @return boolean for if a card should be quizzed or not
	 */
	public boolean isDue(LocalDate currentDate) {
		if (nextReviewDate == null) {
			// Card is new
			return false;
		}
		else if (currentDate.equals(nextReviewDate)) {
			// Card is due
			return true;
		}
		else {
			// Card is over due
			return (currentDate.isAfter(nextReviewDate));
		}
	}
	
	/** Getter method for the front text of a flash card
	 * 
	 * @return String for the front text of a flash card
	 */
	public String getFrontText() {
		return frontText;
	}
	
	/** Getter method for the back text of a flash card
	 * 
	 * @return String for the back text of a flash card
	 */
	public String getBackText() {
		return backText;
	}
	
	/** Getter method for the next review date of a flash card
	 * 
	 * @return LocalDate for the next review date of a flash card
	 */
	public LocalDate getNextReviewDate() {
		return nextReviewDate;
	}
	
	/** Setter method for the next review date of a flash card
	 * 
	 * @param nextReviewDate LocalDate for the next review date to be set
	 */
	public void setNextReviewDate(LocalDate nextReviewDate) {
		this.nextReviewDate = nextReviewDate;
	}
	
	/** Setter method for the front text of a flash card
	 * 
	 * @param frontText String to be set as the front text of a flash card
	 */
	public void setFrontText(String frontText) {
		this.frontText = frontText;
	}
	
	/** Setter method for the back text of a flash card
	 * 
	 * @param backText String to be set as the back text of a flash card
	 */
	public void setBackText(String backText) {
		this.backText = backText;
	}
	
}
