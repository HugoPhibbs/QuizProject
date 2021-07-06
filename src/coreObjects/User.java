package coreObjects;

import java.io.Serializable;

import coreLogic.CheckValidInput;
import stats.UserStats;

/** Represents a user of the quiz application.
 * 
 * @author Hugo Phibbs
 * @author Tom Berry
 * @version 27/6/21
 * @since 27/6/21
 *
 */
public class User implements Serializable {
	
	/** Name of a User */
	private String name;
	/** UserStats for this User */
	private UserStats userStats = new UserStats();
	
	/** Constructor for a User object
	 * 
	 * @param name String for the name of a User
	 */
	public User(String name) {
		setName(name);
	}
	
	/** Constructor for a User object
	 * 
	 */
	public User() {
		/* TODO may want to remove other constructor if we aren't using a Users name in the application -
		 * add then remove name attribute as it won't be used
		 */
	}
	
	/** Getter method for the name of a user
	 * 
	 * @return String for the name of a User
	 */
	public String getName() {
		return name;
	}
	
	/** Setter method for the name of a user
	 * <p>
	 * Checks if a name is valid, otherwise throws an exception
	 * 
	 * @param name String for the name to be set
	 */
	public void setName(String name) {
		if (!CheckValidInput.nameIsValid(name)) {
			String msg = String.format(CheckValidInput.getVALID_NAME_REQUIREMENTS(), "User");
			throw new IllegalArgumentException(msg);
		}
		else {
			this.name = name;
		}
	}

	/** Getter method for userStats
	 * 
	 * @return the userStats
	 */
	public UserStats getUserStats() {
		return userStats;
	}

	/** Setter method for userStats
	 * 
	 * @param userStats the userStats to set
	 */
	public void setUserStats(UserStats userStats) {
		this.userStats = userStats;
	}

}
