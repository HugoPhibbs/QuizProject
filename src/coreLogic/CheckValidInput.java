package coreLogic;

/** Class to containing methods to check if input 
 * from a user is valid 
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 *
 */
public class CheckValidInput {
	
	private static String VALID_NAME_REQUIREMENTS = 
			"%s name must have no more than 2 consequtive white spaces"
			+ "and be between 1 and 15 characters long";

	/** Checks if an inputed name is valid.
	 * Name must have no more than 2 consecutive white spaces, and be between 1 and 15 chars long
	 * 
	 * @param name String for the name to be checked
	 * @return boolean if the inputed name is valid
	 */
	public static boolean nameIsValid(String name) {
				
		boolean prevWhiteSpace = false;
		
		for (int i=0; i < name.length(); i++) {
			if (name.charAt(i) == ' ') {
				if (prevWhiteSpace) {
					return false;
				}
				prevWhiteSpace = true;
			}
			else if (!Character.isLetter(name.charAt(i))) {
				return false;
			}
			else {
				prevWhiteSpace = false;
			}
		}
		if (name.length() < 1 || name.length() > 15) {
			return false;
		}
		return true;
	}

	/** Getter method for VALID_NAME_REQUIREMENTS
	 * 
	 * @return String for VALID_NAME_REQUIREMENTS
	 */
	public static String getVALID_NAME_REQUIREMENTS() {
		return VALID_NAME_REQUIREMENTS;
	}
}
