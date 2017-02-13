package uq.deco2800.duxcom.interfaces;

/**
 * The order to render interfaces
 *
 * Created by liamdm on 17/08/2016.
 */
public enum RenderOrder {
    // The very back
    BACKGROUND,
    // The ground itself
    FLOOR,
    // Surface of the ground, eg for rendering grass
    FLOOR_SURFACE,
    // Selection markers
    SELECTION,
    // The character level
    ENTITY,
    // The front of the map infront of characters
    DARKNESS,
    //The changing darkness in front of the map
    FRONT_MAP,
    // An overlay infront of the game
    OVERLAY,
    // Very front of screen
    FRONT_OVERLAY
}
