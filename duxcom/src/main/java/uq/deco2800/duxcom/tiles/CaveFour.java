package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * A cave ceiling that floats 4 tiles above the ground
 * <p>
 * Created by jay-grant on 19/10/2016.
 */
public class CaveFour extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.CAVE_FOUR);

    public CaveFour(int x, int y) {
        super(LiveTileType.CAVE_FOUR, x, y);
        setupLiveTile(liveTileDataClass);
    }

    @Override
    public String getSyncString() {
        return "CAVE_FOUR";
    }
}
