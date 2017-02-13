package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * A cave ceiling that floats 3 tiles above the ground
 * <p>
 * Created by jay-grant on 19/10/2016.
 */
public class CaveThree extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.CAVE_THREE);

    public CaveThree(int x, int y) {
        super(LiveTileType.CAVE_THREE, x, y);
        setupLiveTile(liveTileDataClass);
    }

    @Override
    public String getSyncString() {
        return "CAVE_THREE";
    }
}
