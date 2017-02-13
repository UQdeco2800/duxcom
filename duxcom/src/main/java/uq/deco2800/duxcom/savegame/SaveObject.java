package uq.deco2800.duxcom.savegame;

import java.util.List;

public interface SaveObject {
	
	/**
	 * 
	 * @return encoded string
	 */
    String encode();
	
	/**
	 * Sets the object's state to the given state
	 * @param state
	 * 			The state of the object to set to
	 */
    void decode(List<String> state);
}
