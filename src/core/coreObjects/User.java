package core.coreObjects;

import java.io.Serializable;

import core.coreLogic.CheckValidInput;
import core.stats.UserStats;

/**
 * Represents a user of the quiz application.
 * 
 * @author Hugo Phibbs
 * @version 15/7/21
 * @since 27/6/21
 *
 */
public class User implements Serializable {

	/**
	 * Name of a User
	 * <p>
	 * Abides by name requirements specified in CheckValidInput
	 */
	private String name;
	/**
	 * UserStats for this User
	 */
	private UserStats userStats = new UserStats();

	/**
	 * Constructor for a User object
	 * 
	 * @param name String for the name of a User
	 */
	public User(String name) {
		setName(name);
	}

	// *************** Getter methods *********************** //

	/**
	 * Getter method for userStats
	 * 
	 * @return the userStats
	 */
	public UserStats getUserStats() {
		return userStats;
	}

	/**
	 * Getter method for the name of a user
	 * 
	 * @return String for the name of a User
	 */
	public String getName() {
		return name;
	}

	// *************** Setter methods *********************** //

	/**
	 * Setter method for userStats
	 * 
	 * @param userStats the userStats to set
	 */
	public void setUserStats(UserStats userStats) {
		this.userStats = userStats;
	}

	/**
	 * Setter method for the name of a user
	 * <p>
	 * Checks if a name is valid, otherwise throws an exception
	 * 
	 * @param name String for the name to be set
	 */
	public void setName(String name) {
		if (!CheckValidInput.nameIsValid(name)) {
			String msg = String.format(CheckValidInput.getVALID_NAME_REQUIREMENTS(), "User");
			throw new IllegalArgumentException(msg);
		} else {
			this.name = name;
		}
	}

}
