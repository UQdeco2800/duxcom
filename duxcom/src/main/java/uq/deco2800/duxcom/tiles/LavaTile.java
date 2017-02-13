package uq.deco2800.duxcom.tiles;

import uq.deco2800.duxcom.buffs.OnFire;
import uq.deco2800.duxcom.dataregisters.DataRegisterManager;
import uq.deco2800.duxcom.dataregisters.LiveTileDataClass;

/**
 * Bubbling puddle of Lava
 * <p>
 * Created by jay-grant on 13/10/2016.
 */
public class LavaTile extends LiveTile {

    private static final LiveTileDataClass liveTileDataClass =
            DataRegisterManager.getLiveTileRegister().getData(LiveTileType.LAVA);

    public LavaTile(int x, int y) {
        super(LiveTileType.LAVA, x, y);
        setupLiveTile(liveTileDataClass);

        addEffect(new OnFire(15, 3));
    }

    @Override
    public String getSyncString() {
        return "LAVA";
    }
}