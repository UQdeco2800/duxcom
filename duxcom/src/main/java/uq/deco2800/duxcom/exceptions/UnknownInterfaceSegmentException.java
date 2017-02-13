package uq.deco2800.duxcom.exceptions;

/**
 * Exception to be thrown when a non-enumerated InterfaceSegment is accessed.
 * @author Alex McLean
 */
public class UnknownInterfaceSegmentException extends RuntimeException {

    /**
     * Exception to be thrown when an attempt is made at loading an unknown InterfaceSegment
     *
     * @param message the message of the Exception
     */
    public UnknownInterfaceSegmentException(String message) {
        super(message);
    }
}
