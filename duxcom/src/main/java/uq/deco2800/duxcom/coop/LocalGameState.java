package uq.deco2800.duxcom.coop;

/**
 * The local game state phases for creation of the map.
 *
 * Created by liamdm on 19/10/2016.
 */
public enum LocalGameState {
    /**
     * Waiting for the player order
     */
    WAITING_PLAYER_ORDER,
    /**
     * Admin distributing hero state
     */
    DISTRIBUTING_HERO_STATE,
    /**
     * Waiting for the hero state
     */
    WAITING_HERO_STATE,
    /**
     * Waiting for the clients to sync
     */
    WAITING_CLIENT_SYNC,
    /**
     * Waiting for the first player change message
     */
    WAITING_PLAYER_CHANGE,
    /**
     * Waiting the other players turns
     */
    WAITING_OTHER_PLAYER,
    /**
     * Waiting for your turn
     */
    WAITING_CURRENT_TURN,
    /**
     * Waiting for the game to end
     */
    WAITING_GAME_END
}
