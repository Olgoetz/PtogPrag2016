package pp2016.team19.shared;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h1>Abstract Message Class providing timestamp, type, subType.</h1>
 * 
 * 	type = Message types, that belong to a set of actions such as Worldmanagement, or SignI/Out/Up/Off actions.
 * <p>
 * 
 * subtype = Message subtypes, that specifies a message type such as moveCharacter, attackMonster, openDoor etc.
 * <p>
 * 
 * It contains the constructor as well as getter and setter methods and a string method that overwrites the inherited method.
 * 
 * @author Goetz, Oliver, 596134
*/
public abstract class Message implements Serializable{
	
	/**
	 * The atttributes for the abstract Message class.
	 * @author Goetz, Oliver, 5961343
	 */
	private static final long serialVersionUID = -4125004909828171573L;
	
	// a timestamp
	private final long timestamp;
	
	// the maintype for each message
	private int type;
	
	// the subtype for each message
	private int subType;
	
	/**
	 * The construcotr of the class
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintype of the message
	 * @param subType  the subtype of the message
	 * 
	 */
	public Message(int type, int subType){
		this.timestamp = System.currentTimeMillis();
		this.type = type;
		this.subType = subType;

	}
	
	
	/**
	 * Method, that sets the maintype of the message.
	 * @author Goetz, Oliver, 5961343
	 * @param type the maintype of the message
	 * 
	 * 
	 */
	public void setType(int type){
		this.type = type;
	}
	
	/**
	 * Gets the maintype of the message
	 * @author Goetz, Oliver, 5961343
	 * @return the maintype of the message
	 * 
	 * 
	 */
	public int getType(){
		return type;
	}
	
	/**
	 * Method, that sets the subtype of the message
	 * @author Goetz, Oliver, 5961343
	 * @param subType the subtype of the message
	 * 
	 */
	public void setSubType(int subType){
		this.subType = subType;
	}
	
	/**
	 * @author Goetz, Oliver, 5961343
	 * @return the subtype
	 * 
	 */
	public int getSubType(){
		return subType;
	}

	
	/**
	 * 
	 * This method overwrites the inherited String-Method.
	 * It presents a timestap, the type as well as the subtype of the message sent to the server.
	 * 
	 * @author Goetz, Oliver, 5961343
	 * 
	 */
	public String toString() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-DD hh:mm:ss");
		return (dateFormat.format(new Date(this.timestamp))
				+ " Type: " + this.getType() + " Subtype: " + this.getSubType());
	}
	
}