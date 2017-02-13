package uq.deco2800.duxcom.coop.exceptions;

/**
 * An unexpected incorrect game state
 * Created by liamdm on 17/10/2016.
 */
public class UnexpectedIncorrectGameState extends RuntimeException {
    public UnexpectedIncorrectGameState(String message) {
        super(message);
    }
}
