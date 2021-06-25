package coreObjects;

/** Represents a FlashCard
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 25/6/21
 * @since 25/6/21
 *
 */
public class FlashCard {
	
	/** String for the front text of a flash card **/
	private String frontText;
	/** String for the back text of a flash card **/
	private String backText;
	/** int for the date of creation **/
	private int dateOfCreation;
	
	/** Constructor for a FlashCard object 
	 * 
	 * @param frontText String for the front text of a flash card
	 * @param backText String for the back text of a flash card
	 * @param dateOfCreation int for the date of creation of a flash card
	 */
	FlashCard(String frontText, String backText, int dateOfCreation){
		this.frontText = frontText;
		this.backText = backText;
		this.dateOfCreation = dateOfCreation;
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
	
	/** Getter method for the date of creation of a flash card
	 * 
	 * @return int for the date of creation of a flash card
	 */
	public int getDateOfCreation() {
		return dateOfCreation;
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
