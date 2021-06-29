package coreObjects;

import java.io.Serializable;

import coreLogic.UserStats;

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
		this.name = name;
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
	 * 
	 * @param name String for the name to be set
	 */
	public void setName(String name) {
		this.name = name;
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
