package pp2016.team19.shared;

import java.io.Serializable;


/**
 * <h1>OpenDoorRequest Message to the server.</h1>
 * 
 * Client sends a requet to the server.
 * <p>
 * @author Goetz, Oliver, 5961343
 * 
 * 
 */

public class MessOpenDoorRequest extends Message implements Serializable {


	/**
	 * The attributes for the class.
	 * @author Goetz, Oliver, 5961343
	 */
	private static final long serialVersionUID = -6223977759661914096L;
	
	// true, if player has the key
	boolean openDoor;
	
	/**
	 * @author Goetz, Oliver, 5961343
	 * @param openDoor flag for the state of the door
	 * @param type the maintype (=1) of the message
	 * @param subType the subtype (=8) of the message
	 * 
	 */
	
	public MessOpenDoorRequest(boolean openDoor, int type,int subType) {
		super(type,subType);
		this.openDoor = openDoor;
	}
	
	/**
	 * Method, that sets the door on the state open.
	 * @author Goetz, Oliver, 5961343
	 * @param openDoor flag for the state of the door
	 * 
	 * 
	 */
	public void setOpenDoor(boolean openDoor) {
		this.openDoor = openDoor;
	}
	
	/**
	 * Method, that gets the state of the door.
	 * @author Goetz, Oliver, 5961343
	 * @return the state of the door
	 * 
	 * 
	 */
	
	public boolean getOpenDoor() {
		return openDoor;
	}
}
