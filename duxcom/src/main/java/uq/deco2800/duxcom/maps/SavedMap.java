package uq.deco2800.duxcom.maps;

import uq.deco2800.duxcom.tiles.Tile;
import uq.deco2800.duxcom.tiles.TileType;
import uq.deco2800.duxcom.util.TurnTickable;

/**
 * 
 * 
 * @author abhijagtap
 *
 */
public class SavedMap extends AbstractGameMap implements TurnTickable {
	
	public SavedMap(String name, int width, int height){
		super.name = name;
		super.mapType = MapType.SAVED;
		super.setSize(width, height);
		
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < height; x++) {
				tiles.set(x, y, new Tile(TileType.DT_GRASS_DARK_1));
			}
		}
	}
}
