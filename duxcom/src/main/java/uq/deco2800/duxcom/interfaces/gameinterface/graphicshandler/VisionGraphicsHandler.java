package uq.deco2800.duxcom.interfaces.gameinterface.graphicshandler;

import javafx.scene.canvas.GraphicsContext;
import uq.deco2800.duxcom.interfaces.GraphicsHandler;
import uq.deco2800.duxcom.maps.mapgen.MapAssembly;

/**
 * Renders the fog of war.
 * 
 * Created by jake-stevenson on 3/09/16
 */
public class  VisionGraphicsHandler extends GraphicsHandler {

    @Override
    public void render(GraphicsContext graphicsContext) {
        // Do nothing as nothing needs to be rendered for this handler
    }

    @Override
    public boolean needsUpdating() {
        return currentGameManager.isVisionChanged();
    }

	@Override
	public void updateGraphics(GraphicsContext graphicsContext) {

		// Map dimension information.
        MapAssembly map = currentGameManager.getMap();
        int mapWidth = map.getWidth();
        int mapHeight = map.getHeight();

		int[][] vision = new int[mapWidth][mapHeight];
		map.updateVisibilityArray();
        // Generate visibility array.
		if (map.getVisibilityArray() != null) {
			vision = map.getVisibilityArray();
		}

        // For each co-ordinate on the map...
        for (int i = 0;	i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                if(!map.canSelectPoint(i, j)){
                    continue;
                }
                // If the tile is visible to a hero entity...
                if (vision[i][j] == 1){
                    // Update the hidden status of the tile
                    map.getTile(i, j).setHidden(false);
                }  else {
                	map.getTile(i,j).setHidden(true);
				}
            }
        }
        currentGameManager.setVisionChanged(false);
    }
}
