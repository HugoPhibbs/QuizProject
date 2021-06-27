package coreObjects;

/** Represents a user of the quiz application.
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 *
 */
public class User {
	
	/** Name of a User */
	private String name;
	
	/** Getter method for the name of a user
	 * 
	 * @return String for the name of a User
	 */
	public String getName() {
		return name;
	}
	
	/** Setter method for the name of a user
	 * 
	 * @param name String for the name to be set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
