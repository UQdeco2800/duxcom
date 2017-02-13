package uq.deco2800.duxcom.exceptions;

/**
 * Exception to be thrown when a register is accessed with an invalid key
 *
 * @author Alex McLean
 */
public class InvalidRegisterAccessException extends RuntimeException {
    public InvalidRegisterAccessException(String message)
    {
        super(message);
    }
}