package uq.deco2800.duxcom.coop.exceptions;

/**
 * Thrown when the game is in the incorrect state to be interacted with.
 *
 * Created by liamdm on 9/10/2016.
 */
public class IncorrectGameState extends Exception {
    public IncorrectGameState(String message) {
        super(message);
    }
}
