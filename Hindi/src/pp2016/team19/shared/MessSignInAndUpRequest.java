package pp2016.team19.shared;

import java.io.Serializable;

/**
 * <h1>A message class for signing IN as well as signing Up transmitting to the server.</h1>
 * 
 * 
 * @author Goetz, Oliver, 5961343
 *
 */

public class MessSignInAndUpRequest extends Message implements Serializable {

	
	/**
	 * The attributes of the class.
	 * 
	 * @author Goetz, Oliver, 5961343
	 */
	private static final long serialVersionUID = -6043901602629877055L;
	
	// the username
	private String username;
	
	// the password
	private String password;
	
	/**
	 * The constructor of the class
	 * @author Goetz, Oliver, 5961343
	 * @param username typed in username
	 * @param password typed in password
	 * @param type the maintype of the message
	 * @param subType the subtype of the message
	 */
	
	public MessSignInAndUpRequest(String username, String password, int type, int subType) {
		super(type, subType);
		this.username = username;
		this.password = password;
	}
	
	
	/**
	 * Method, thats a username.
	 * @author Goetz, Oliver, 5961343
	 * @param username the username typed in the login/sign up frame
	 */
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * Gets the username.
	 * @author Goetz, Oliver, 5961343
	 * @return the username in the login/sign up frame
	 */
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Method, that sets a password
	 * @author Goetz, Oliver, 5961343
	 * @param password the password typed in the login/sign up frame
	 */
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	/**
	 * Gets the password.
	 * @author Goetz, Oliver, 5961343
	 * @return the password in the login/sign up frame
	 */
	
	public String getPassword() {
		return password;
	}

}
