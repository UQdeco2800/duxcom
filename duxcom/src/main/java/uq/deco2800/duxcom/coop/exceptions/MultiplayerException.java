package uq.deco2800.duxcom.coop.exceptions;

/**
 * Thrown when a multiplayer error occurs.
 *
 * Created by liamdm on 8/10/2016.
 */
public class MultiplayerException extends Exception{
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * The error code thrown
     */
    public enum ErrorCode{
        /**
         * Credentials were not stored in login manager
         */
        NO_CREDENTIALS,
        /**
         * Rest client did not work
         */
        REST_CLIENT_FAILED,
        /**
         * Credentials were invalid
         */
        CREDENTIAL_INVALID,
        /**
         * Client could not be created
         */
        CLIENT_CREATION_FAILURE,
        /**
         * Game session does not exist
         */
        NO_SESSION
    }

    private final ErrorCode errorCode;

    public MultiplayerException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public MultiplayerException(ErrorCode errorCode, Throwable throwable) {
        super(throwable);
        this.errorCode = errorCode;
    }


}
