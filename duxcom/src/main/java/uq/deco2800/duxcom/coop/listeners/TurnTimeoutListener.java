package uq.deco2800.duxcom.coop.listeners;

/**
 * Listens on a turn ended event
 *
 * Created by liamdm on 18/10/2016.
 */
public abstract class TurnTimeoutListener {
    public abstract void onTurnTimeout(String user);
}
