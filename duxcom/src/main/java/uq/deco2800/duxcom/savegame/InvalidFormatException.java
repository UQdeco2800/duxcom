package uq.deco2800.duxcom.savegame;

/**
 * An exception to indicate invalid saved file format
 * 
 * @author abhijagtap
 *
 */
@SuppressWarnings("serial")
public class InvalidFormatException extends RuntimeException{
	public InvalidFormatException() {
        super();
    }

    public InvalidFormatException(String s) {
        super(s);
    }
}


