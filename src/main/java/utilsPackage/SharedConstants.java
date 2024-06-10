package utilsPackage;

import java.util.*;
import java.util.HashMap;


/**
 * The shared_state class represents a shared state object that can be used to store and Retrieve shared variable.
 */

public class SharedConstants {
	private String sharedVariable;
	private Map<String, String> sharedVariables = new HashMap<>();
	
	/**
	 *  Retrieves the value of the sharedVariable.
	 *  
	 *  @return The value of the sharedVariable.
	 */
	public String getSharedVariable() {return sharedVariable;}
	
	
	/**
	 *  Retrieves the shared variable map.
	 *  
	 *  @return The shared variables map
	 */
	public Map<String, String> getSharedVariables() {return sharedVariables;}
	
	/**
	 *  Retrieves the value of the sharedVariable associated with the given key.
	 *  @param key the key associated with the sharedVariables.
	 *  @return The value of the sharedVariable associated with the given key, Or null if the key is not found.
	 */
	public String getSharedVariable(String key) {return sharedVariables.get(key);}
	
	/**
	 *  sets the value of the shredVariable.
	 *  @param sharedVariable The value to be set for the sharedVariable.
	 
	 */
	public void setSharedVariable(String sharedVariable) {this.sharedVariable =sharedVariable;}
	
	/**
	 *  sets the value of the shredVariable associated with the given key.
	 *  
	 *  @param key   The key associated with the sharesVariable.
	 *  @param value The value to be set for the sharedVariable.
	 
	 */
	public void setSharedVariables(String key, String value) {this.sharedVariables.put(key, value);}
}
