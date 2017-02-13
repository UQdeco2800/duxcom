package uq.deco2800.duxcom.interfaces.gameinterface;

import uq.deco2800.duxcom.annotation.MethodContract;
import uq.deco2800.duxcom.interfaces.AbstractDisplayManager;
import uq.deco2800.duxcom.interfaces.RenderOrder;
import uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler.*;

/**
 * Handles the loading of different graphic screens
 * to prevent contention of the GameRender class
 *
 * Created by liamdm on 16/08/2016.
 */
public class GameDisplayManager extends AbstractDisplayManager<GameDisplays> {

    /**
     * Initiates all of the GraphicsHandlers needed for a game to render
     *
     * @param display the display to use
     */
    @MethodContract(precondition = {"display != null"})
    public GameDisplayManager(GameDisplays display) {
        super(display);

        // Render visibility/fog of war
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FLOOR, new VisionGraphicsHandler());

        // Render the map
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FLOOR, new GameMapGraphicsHandler());

        // Render the ability range of the currently selected ability
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new AbilityRangeGraphicsHandler());

        // Render the movement range of the current hero
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new MovementRangeGraphicsHandler());

        // Render the movement range of the selected enemy
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new EnemyMovementRangeGraphicsHandler());
        
        // Render the movement path of the hero to the selected tile
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new MovementPathGraphicsHandler());

        // Render the current selection
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new CurrentSelectionGraphicsHandler());

        // Render LiveTile floors
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FLOOR, new LiveTileGraphicsHandler(true));

        // Render shadows
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FLOOR_SURFACE, new ShadowGraphicsHandler());
        
        // Render the entities
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.ENTITY, new EntityGraphicsHandler());

        // Render LiveTile entity level
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.ENTITY, new LiveTileGraphicsHandler(false));

        // Render the mini-map
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.OVERLAY, new MiniMapGraphicsHandler());

        // Render the enemy turn graphics
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FRONT_OVERLAY, new EnemyTurnGraphicsHandler());

        // Render the glow around the current hero
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new CurrentHeroGraphicsHandler());

        // Render the darkness opacity
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.BACKGROUND, new DarknessGraphicsHandler());

        // Render the messages
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.OVERLAY, new MessageGraphicsHandler());

        // Render loot range of dead enenmies
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new LootRangeGraphicsHandler());

        // Render loot range of chests
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.SELECTION, new ChestRangeGraphicsHandler());

        // Render the multiplayer countdown
        addHandler(GameDisplays.MAIN_MAP, RenderOrder.FRONT_OVERLAY, new CountdownGraphicsHandler());

    }

}
